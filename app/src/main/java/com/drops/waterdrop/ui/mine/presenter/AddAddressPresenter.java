package com.drops.waterdrop.ui.mine.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.activity.AddAddressActivity;
import com.drops.waterdrop.ui.mine.activity.GetTicketActivity;
import com.drops.waterdrop.ui.mine.view.IAddAddressView;
import com.drops.waterdrop.util.AddressPickerViewUtil;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.media.picker.PickImageHelper;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.QiNiuUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.IdCardEntity;
import com.netease.nim.uikit.model.QiNiuTokensEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by HZH on 2017/7/5.
 */

public class AddAddressPresenter extends BasePresenter<IAddAddressView> {

    private AddressEntity.ResultsBean mAddress;
    private String mProv;
    private String mCity;
    private String mDistrict;
    public int mType;//1是新增地址 2是编辑
    private boolean mIsPickCity;
    public String mIdcardFront;//正面url
    public String mIdcardBack;//反面url

    public String mFrontFile;//正面照片
    public String mBackFile;//正面照片

    private String mIdCardNo;
    private String mUserName;
    private String mPhone;
    private String mDetail;

    private boolean mIsChecked;
    private boolean mIsEdit;


    public AddAddressPresenter(BaseActivity context) {
        super(context);
    }

    public void parseIntent(Intent intent) {
        mType = intent.getIntExtra(AddAddressActivity.EXTRA_TYPE, 1);
        mAddress = (AddressEntity.ResultsBean) intent.getSerializableExtra(Constants.EXTRA_ENTITY);

        if (mType == AddAddressActivity.TYPE_EDIT) {
            getView().initEditUI(mAddress);
            mIdcardFront = mAddress.getIdcardFront();
            mIdcardBack = mAddress.getIdcardBack();
            mFrontFile = "";
            mBackFile = "";
            mIsEdit = true;
        } else {
            getView().initAddUI();
            mIsEdit = false;
        }
    }


    public void saveAddress(String name, String phone, String area, String detail, boolean checked,
                            String idCardNumber) {

        mUserName = name;
        mPhone = phone;
        mDetail = detail;
        mIdCardNo = idCardNumber;
        mIsChecked = checked;

        if (!check(name, phone, area, detail, idCardNumber)) {
            return;
        }

        if (!TextUtils.isEmpty(mFrontFile) || !TextUtils.isEmpty(mBackFile)) {
            getQiNiuToken();
        } else {
            saveAddressNoIdCard(name, phone, detail, checked, idCardNumber);
        }


    }

    private void getQiNiuToken() {
        if (!TextUtils.isEmpty(mFrontFile) && !TextUtils.isEmpty(mBackFile)) {
            Map<String, Object> map = new HashMap<>();
            map.put(RequestParams.size, "2");
            map.put(RequestParams.type, 1);
            Observable<BaseResponse<QiNiuTokensEntity>> observable = HttpUtil.getInstance().sApi.getQiNiuToken(RequestBodyUtils.getBody(map));
            HttpUtil.getInstance().execute(observable, new ProgressSubscriber<QiNiuTokensEntity>(mContext) {
                @Override
                protected void _onNext(QiNiuTokensEntity dataBean) {
                    QiNiuTokensEntity.ResultsBean resultsBean = dataBean.getResults().get(0);
                    String attachNameFront = resultsBean.getAttachName();
                    String tokenFront = resultsBean.getToken();
                    QiNiuTokensEntity.ResultsBean resultsBean1 = dataBean.getResults().get(1);
                    String attachNameBack = resultsBean1.getAttachName();
                    String tokenBack = resultsBean1.getToken();
                    if (!TextUtils.isEmpty(attachNameFront) && !TextUtils.isEmpty(tokenFront)) {
                        putQiNiu(new File(mFrontFile), attachNameFront, tokenFront, new File(mBackFile), attachNameBack, tokenBack);
                    }
                }

                @Override
                protected void _onError(String message) {
                    ToastUtil.showShort(message);
                }
            });
        } else if (!TextUtils.isEmpty(mFrontFile)) {//只上传正面
            Map<String, Object> map = new HashMap<>();
            map.put(RequestParams.size, "1");
            map.put(RequestParams.type, 1);
            Observable<BaseResponse<QiNiuTokensEntity>> observable = HttpUtil.getInstance().sApi.getQiNiuToken(RequestBodyUtils.getBody(map));
            HttpUtil.getInstance().execute(observable, new ProgressSubscriber<QiNiuTokensEntity>(mContext) {
                @Override
                protected void _onNext(QiNiuTokensEntity dataBean) {
                    QiNiuTokensEntity.ResultsBean resultsBean = dataBean.getResults().get(0);
                    String attachNameFront = resultsBean.getAttachName();
                    String tokenFront = resultsBean.getToken();

                    if (!TextUtils.isEmpty(attachNameFront) && !TextUtils.isEmpty(tokenFront)) {

                        QiNiuUtil.Instance.putImg(new File(mFrontFile), attachNameFront, tokenFront, new QiNiuUtil.OnPutImgListener() {
                            @Override
                            public void onPutSucceed(String frontKey) {
                                if (mIsEdit) {
                                    updateAddressWithIdCard(frontKey, mIdcardBack);

                                } else {
                                    saveAddressWithIdCard(frontKey, mIdcardBack);
                                }
                            }

                            @Override
                            public void onPutError() {
                                ToastUtil.showShort("身份证上传失败");
                            }
                        });

                    }
                }

                @Override
                protected void _onError(String message) {
                    ToastUtil.showShort(message);
                }
            });

        } else if (!TextUtils.isEmpty(mBackFile)) {//只上传反面
            Map<String, Object> map = new HashMap<>();
            map.put(RequestParams.size, "1");
            map.put(RequestParams.type, 1);
            Observable<BaseResponse<QiNiuTokensEntity>> observable = HttpUtil.getInstance().sApi.getQiNiuToken(RequestBodyUtils.getBody(map));
            HttpUtil.getInstance().execute(observable, new ProgressSubscriber<QiNiuTokensEntity>(mContext) {
                @Override
                protected void _onNext(QiNiuTokensEntity dataBean) {
                    QiNiuTokensEntity.ResultsBean resultsBean = dataBean.getResults().get(0);
                    String attachNameFront = resultsBean.getAttachName();
                    String tokenFront = resultsBean.getToken();

                    if (!TextUtils.isEmpty(attachNameFront) && !TextUtils.isEmpty(tokenFront)) {

                        QiNiuUtil.Instance.putImg(new File(mBackFile), attachNameFront, tokenFront, new QiNiuUtil.OnPutImgListener() {
                            @Override
                            public void onPutSucceed(String backKey) {
                                if (mIsEdit) {
                                    updateAddressWithIdCard(mIdcardFront, backKey);

                                } else {
                                    saveAddressWithIdCard(mIdcardFront, backKey);
                                }
                            }

                            @Override
                            public void onPutError() {
                                ToastUtil.showShort("身份证上传失败");
                            }
                        });

                    }
                }

                @Override
                protected void _onError(String message) {
                    ToastUtil.showShort(message);
                }
            });

        } else {//正反面都不上传
            if (mIsEdit) {
                updateAddressWithIdCard(mIdcardFront, mIdcardBack);

            } else {
                saveAddressWithIdCard(mIdcardFront, mIdcardBack);
            }
        }
    }

    /**
     * 新增用户地址（没有）
     *
     * @param name
     * @param phone
     * @param checked
     * @param idCardNumber
     */
    private void saveAddressNoIdCard(String name, String phone, String detail, boolean checked, String idCardNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.name, name);
        map.put(RequestParams.mobile, phone);
        map.put(RequestParams.prov, mProv);
        map.put(RequestParams.city, mCity);
        map.put(RequestParams.district, mDistrict);
        map.put(RequestParams.detail, detail);
        map.put(RequestParams.default_flag, checked ? "1" : "0");
        if (!TextUtils.isEmpty(idCardNumber)) {
            map.put(RequestParams.idcard_no, idCardNumber);
        }


        Observable<BaseResponse<AddressEntity.ResultsBean>> observable = HttpUtil.getInstance().sApi.insertAddress(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity.ResultsBean>(mContext, "正在保存...") {
            @Override
            protected void _onNext(AddressEntity.ResultsBean addressInsertEntity) {
                ToastUtil.showShort("保存成功");
                getView().onSaveAddressSucceed();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });

    }

    /**
     * 新增用户地址（有身份证信息）
     */
    private void saveAddressWithIdCard(String frontKey, String backKey) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.name, mUserName);
        map.put(RequestParams.mobile, mPhone);
        map.put(RequestParams.prov, mProv);
        map.put(RequestParams.city, mCity);
        map.put(RequestParams.district, mDistrict);
        map.put(RequestParams.detail, mDetail);
        map.put(RequestParams.default_flag, mIsChecked ? "1" : "0");

        if (!TextUtils.isEmpty(mIdCardNo)) {
            map.put(RequestParams.idcard_no, mIdCardNo);
            map.put(RequestParams.idcard_front, frontKey);
            map.put(RequestParams.idcard_back, backKey);
        }


        Observable<BaseResponse<AddressEntity.ResultsBean>> observable = HttpUtil.getInstance().sApi.insertAddress(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity.ResultsBean>(mContext) {
            @Override
            protected void _onNext(AddressEntity.ResultsBean addressInsertEntity) {
                ToastUtil.showShort("保存成功");
                getView().onSaveAddressSucceed();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });

    }




    /**
     * 上传到七牛
     *
     * @param fileFront
     * @param attachNameFront
     * @param tokenFront
     * @param fileBack
     * @param attachNameBack
     * @param tokenBack
     */
    private void putQiNiu(File fileFront, String attachNameFront, String tokenFront, final File fileBack,
                          final String attachNameBack, final String tokenBack) {


        QiNiuUtil.Instance.putImg(fileFront, attachNameFront, tokenFront, new QiNiuUtil.OnPutImgListener() {
            @Override
            public void onPutSucceed(final String frontKey) {
                QiNiuUtil.Instance.putImg(fileBack, attachNameBack, tokenBack, new QiNiuUtil.OnPutImgListener() {
                    @Override
                    public void onPutSucceed(String backKey) {
                        if (mIsEdit) {
                            updateAddressWithIdCard(frontKey, backKey);
                        } else {
                            saveAddressWithIdCard(frontKey, backKey);
                        }
                    }

                    @Override
                    public void onPutError() {
                        DialogMaker.dismissProgressDialog();
                        ToastUtil.showShort("身份证上传失败");
                    }
                });
            }

            @Override
            public void onPutError() {
                DialogMaker.dismissProgressDialog();
                ToastUtil.showShort("身份证上传失败");
            }
        });


    }


    private boolean check(String name, String phone, String area, String address, String idCardNumber) {
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort("联系人不能为空");
            return false;
        }


        if (!NumberUtil.Instance.isMobileNo(phone)) {
            ToastUtil.showShort("手机号码格式输入不正确");
            return false;
        }

        if (TextUtils.isEmpty(area) || TextUtils.equals("请选择", area)) {
            ToastUtil.showShort("地区不能为空");
            return false;
        }

        if (TextUtils.isEmpty(address)) {
            ToastUtil.showShort("详细地址不能为空");
            return false;
        }

        if (!TextUtils.isEmpty(idCardNumber)) {//校验身份证
            if (TextUtils.isEmpty(mFrontFile) && TextUtils.isEmpty(mIdcardFront)) {
                ToastUtil.showShort("请上传身份证正面");
                return false;
            }
            if (TextUtils.isEmpty(mBackFile) && TextUtils.isEmpty(mIdcardBack)) {
                ToastUtil.showShort("请上传身份证反面");
                return false;
            }
        }


        return true;
    }

    private AddressPickerViewUtil addressPickerViewUtil;

    public void ShowPickerView() {// 弹出选择器
        if (addressPickerViewUtil == null) {
            addressPickerViewUtil = AddressPickerViewUtil.getInstance(mContext);
        }

        addressPickerViewUtil.ShowPickerView(new AddressPickerViewUtil.OnPickerListener() {

            @Override
            public void onPickerListener(String prov, String city, String district) {
                if (!TextUtils.isEmpty(prov)) {
                    mIsPickCity = true;
                    mProv = prov;
                    mCity = city;
                    mDistrict = district;
                }

                getView().onCityPicked(prov, city, district);
            }
        }, mContext);
    }

    /**
     * 修改
     * @param name
     * @param phone
     * @param area
     * @param detail
     * @param checked
     * @param idCardNumber
     */
    public void updateAddress(String name, String phone, String area, String detail, boolean checked, String idCardNumber) {

        mUserName = name;
        mPhone = phone;
        mDetail = detail;
        mIdCardNo = idCardNumber;
        mIsChecked = checked;

        if (!check(name, phone, area, detail, idCardNumber)) {
            return;
        }

        if (mAddress == null) {
            ToastUtil.showShort("地址异常， 请重新添加。");
        }
        if (!mIsPickCity) {
            mProv = mAddress.getProv();
            mCity = mAddress.getCity();
            mDistrict = mAddress.getDistrict();
        }


        if (TextUtils.isEmpty(idCardNumber)) {
            updateAddressNoIdCard();

        } else {
            getQiNiuToken();
        }


    }

    /**
     * 修改地址（有身份证）
     *
     * @param idcardFront
     * @param idcardBack
     */
    private void updateAddressWithIdCard(String idcardFront, String idcardBack) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.address_id, mAddress.getAddressId());
        map.put(RequestParams.name, mUserName);
        map.put(RequestParams.mobile, mPhone);
        map.put(RequestParams.prov, mProv);
        map.put(RequestParams.city, mCity);
        map.put(RequestParams.district, mDistrict);
        map.put(RequestParams.detail, mDetail);
        map.put(RequestParams.idcard_no, mIdCardNo);
        map.put(RequestParams.idcard_front, idcardFront);
        map.put(RequestParams.idcard_back, idcardBack);
        map.put(RequestParams.default_flag, mIsChecked ? "1" : "0");

        Observable<BaseResponse<AddressEntity.ResultsBean>> observable = HttpUtil.getInstance().sApi.updateAddress(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity.ResultsBean>(mContext) {
            @Override
            protected void _onNext(AddressEntity.ResultsBean addressInsertEntity) {
                ToastUtil.showShort("修改成功");
                getView().onSaveAddressSucceed();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });

    }


    /**
     * 修改地址（没有身份证）
     */
    private void updateAddressNoIdCard() {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.address_id, mAddress.getAddressId());
        map.put(RequestParams.name, mUserName);
        map.put(RequestParams.mobile, mPhone);
        map.put(RequestParams.prov, mProv);
        map.put(RequestParams.city, mCity);
        map.put(RequestParams.district, mDistrict);
        map.put(RequestParams.detail, mDetail);
        map.put(RequestParams.default_flag, mIsChecked ? "1" : "0");
        Observable<BaseResponse<AddressEntity.ResultsBean>> observable = HttpUtil.getInstance().sApi.updateAddress(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity.ResultsBean>(mContext) {
            @Override
            protected void _onNext(AddressEntity.ResultsBean addressInsertEntity) {
                ToastUtil.showShort("修改成功");
                getView().onSaveAddressSucceed();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    public void removeAddress() {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.address_id, mAddress.getAddressId());
        Observable<BaseResponse<AddressEntity.ResultsBean>> observable = HttpUtil.getInstance().sApi.removeAddress(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity.ResultsBean>(mContext, "正在删除...") {
            @Override
            protected void _onNext(AddressEntity.ResultsBean addressInsertEntity) {
                ToastUtil.showShort("删除成功");
                getView().onSaveAddressSucceed();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }


    /**
     * 根据用户姓名查找用户身份信息
     * @param userName
     */
    public void retrieveIdCard(String userName) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.name, userName);
        Observable<BaseResponse<IdCardEntity>> observable = HttpUtil.getInstance().sApi.retrieveIdCard(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<IdCardEntity>(mContext, false) {
            @Override
            protected void _onNext(IdCardEntity idCardEntity) {
                if (idCardEntity != null) {
                    mIdcardFront = idCardEntity.getIdcardFront();
                    mIdcardBack = idCardEntity.getIdcardBack();
                    mFrontFile = "";
                    mBackFile = "";
                    getView().updateUIForUserName(idCardEntity);
                }
            }

            @Override
            protected void _onError(String message) {
            }
        });
    }


    public void showFrontDialog() {
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.titleResId = R.string.pull_id_card_front;
        option.crop = true;
        option.multiSelect = false;
        option.cropOutputImageWidth = 720;
        option.cropOutputImageHeight = 450;
        PickImageHelper.pickImage(mContext, GetTicketActivity.PICK_ID_CARD_FRONT_REQUEST, option);
    }

    public void showBackDialog() {
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.titleResId = R.string.pull_id_card_back;
        option.crop = true;
        option.multiSelect = false;
        option.cropOutputImageWidth = 720;
        option.cropOutputImageHeight = 450;
        PickImageHelper.pickImage(mContext, GetTicketActivity.PICK_ID_CARD_BACK_REQUEST, option);

    }

}
