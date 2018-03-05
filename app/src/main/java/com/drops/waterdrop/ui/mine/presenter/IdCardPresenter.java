package com.drops.waterdrop.ui.mine.presenter;

import android.text.TextUtils;

import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.activity.GetTicketActivity;
import com.drops.waterdrop.ui.mine.view.IIdCardEditView;
import com.drops.waterdrop.util.ToastUtil;
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
 * Created by dengxiaolei on 2017/9/8.
 */

public class IdCardPresenter extends BasePresenter<IIdCardEditView> {

    public String mFrontImgUrl;
    public String mReverseImgUrl;

    public String mFrontImgFile;
    public String mReverseImgFile;
    private String mIdCardNo;
    private String mUserName;

    public long mAddressId;

    public IdCardPresenter(BaseActivity context) {
        super(context);
    }

    /**
     * 根据用户姓名查找用户身份信息
     * @param userName
     */
    public void retrieveIdCard(String userName) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.name, userName);
        Observable<BaseResponse<IdCardEntity>> observable = HttpUtil.getInstance().sApi.retrieveIdCard(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<IdCardEntity>(mContext) {
            @Override
            protected void _onNext(IdCardEntity idCardEntity) {
                if (idCardEntity != null) {
                    mFrontImgUrl = idCardEntity.getIdcardFront();
                    mReverseImgUrl = idCardEntity.getIdcardBack();
                    mFrontImgFile = "";
                    mReverseImgFile = "";
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

    public void saveIdCardInfo(String name, String idCardNo) {
        if (!checkValidity(name, idCardNo)) return;

        mUserName = name;
        mIdCardNo = idCardNo;

        if (!TextUtils.isEmpty(mFrontImgFile) && !TextUtils.isEmpty(mReverseImgFile)) {
            put2ImgToQiNiu(mFrontImgFile, mReverseImgFile);
        }

        if (!TextUtils.isEmpty(mFrontImgFile) && TextUtils.isEmpty(mReverseImgFile)) {
            putFrontImgToQiNiu(mFrontImgFile);
        }

        if (TextUtils.isEmpty(mFrontImgFile) && !TextUtils.isEmpty(mReverseImgFile)) {
            putReverseImgToQiNiu(mReverseImgFile);
        }

        if (TextUtils.isEmpty(mFrontImgFile) && TextUtils.isEmpty(mReverseImgFile)) {
            saveIdCard(mFrontImgUrl, mReverseImgUrl);
        }

    }

    private void putFrontImgToQiNiu(final String frontPath) {
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

                    QiNiuUtil.Instance.putImg(new File(frontPath), attachNameFront, tokenFront, new QiNiuUtil.OnPutImgListener() {
                        @Override
                        public void onPutSucceed(String frontKey) {
                            saveIdCard(frontKey, mReverseImgUrl);
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

    }

    private void putReverseImgToQiNiu(final String reversePath) {
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

                    QiNiuUtil.Instance.putImg(new File(reversePath), attachNameFront, tokenFront, new QiNiuUtil.OnPutImgListener() {
                        @Override
                        public void onPutSucceed(String reverseKey) {
                            saveIdCard(mFrontImgUrl, reverseKey);
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

    }


    private void put2ImgToQiNiu(final String frontPath, final String reversePath) {
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
                final String attachNameBack = resultsBean1.getAttachName();
                final String tokenBack = resultsBean1.getToken();
                if (!TextUtils.isEmpty(attachNameFront) && !TextUtils.isEmpty(tokenFront)) {
                    QiNiuUtil.Instance.putImg(new File(frontPath), attachNameFront, tokenFront, new QiNiuUtil.OnPutImgListener() {
                        @Override
                        public void onPutSucceed(final String frontKey) {
                            QiNiuUtil.Instance.putImg(new File(reversePath), attachNameBack, tokenBack, new QiNiuUtil.OnPutImgListener() {
                                @Override
                                public void onPutSucceed(String backKey) {
                                  saveIdCard(frontKey, backKey);
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
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void saveIdCard(String frontKey, String backKey) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.name, mUserName);
        map.put(RequestParams.idcard_no, mIdCardNo);
        map.put(RequestParams.idcard_front, frontKey);
        map.put(RequestParams.idcard_back, backKey);
        map.put(RequestParams.address_id, mAddressId);
        Observable<BaseResponse<AddressEntity.ResultsBean>> observable = HttpUtil.getInstance().sApi.updateUserIdcard(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity.ResultsBean>(mContext) {
            @Override
            protected void _onNext(AddressEntity.ResultsBean idCardEntity) {
                if (idCardEntity != null) {
                    getView().onUpdateSucceed(idCardEntity);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private boolean checkValidity(String name, String idCardNo) {
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort("姓名不能为空");
            return false;
        }

        if (TextUtils.isEmpty(idCardNo)) {
            ToastUtil.showShort("身份证号码不能为空");
            return false;
        }

        if (TextUtils.isEmpty(mFrontImgUrl) && TextUtils.isEmpty(mFrontImgFile)) {
            ToastUtil.showShort("请上传身份证正面");
            return false;
        }

        if (TextUtils.isEmpty(mReverseImgUrl) && TextUtils.isEmpty(mReverseImgFile)) {
            ToastUtil.showShort("请上传身份证反面");
            return false;
        }


        return true;
    }
}
