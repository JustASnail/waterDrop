package com.drops.waterdrop.ui.mine.presenter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.CityJsonBean;
import com.drops.waterdrop.model.TicketEntity;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.activity.GetTicketActivity;
import com.drops.waterdrop.ui.mine.view.IGetTicketView;
import com.drops.waterdrop.util.AddressPickerViewUtil;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.media.picker.PickImageHelper;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.QiNiuUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.MyTicketEntity;
import com.netease.nim.uikit.model.QiNiuTokensEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/6/4.
 */

public class GetTicketPresenter extends BasePresenter<IGetTicketView> {
    private static final String TAG = "GetTicketPresenter";
    private List<CityJsonBean> options1Items = new ArrayList<>();
    private List<List<String>> options2Items = new ArrayList<>();
    private List<List<List<String>>> options3Items = new ArrayList<>();

    public GetTicketPresenter(BaseActivity context) {
        super(context);
    }

    public void parseIntent(Intent intent) {
        boolean isGet = intent.getBooleanExtra(GetTicketActivity.EXTRA_IS_GET, false);
        MyTicketEntity.ResultsBean entity = (MyTicketEntity.ResultsBean) intent.getSerializableExtra(GetTicketActivity.EXTRA_ENTITY);

        getView().onIntent(isGet, entity);

    }

    private AddressPickerViewUtil addressPickerViewUtil;
    public void ShowPickerView() {// 弹出选择器
        if (addressPickerViewUtil == null)
            AddressPickerViewUtil.getInstance(mContext);

        addressPickerViewUtil.ShowPickerView(new AddressPickerViewUtil.OnPickerListener() {
                    @Override
                    public void onPickerListener(String prov, String city, String district) {
                        getView().onAddressPicker(prov,city,district);
                    }
                }, mContext);

    }


    public void showFrontDialog() {
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.titleResId = R.string.pull_id_card_front;
        option.crop = true;
        option.multiSelect = false;
        option.cropOutputImageWidth = 0;
        option.cropOutputImageHeight = 0;
        PickImageHelper.pickImage(mContext, GetTicketActivity.PICK_ID_CARD_FRONT_REQUEST, option);
    }

    public void showBackDialog() {
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.titleResId = R.string.pull_id_card_back;
        option.crop = true;
        option.multiSelect = false;
        option.cropOutputImageWidth = 0;
        option.cropOutputImageHeight = 0;
        PickImageHelper.pickImage(mContext, GetTicketActivity.PICK_ID_CARD_BACK_REQUEST, option);

    }

    public void onClickOk(String ticketid, String phone, String name, String idCard, String idCardFront, String idCardBack, String prov, String city, String district, String addressStr) {

        if (!check(phone, name, idCard, prov, addressStr)) {
            return;
        }
        getQiNiuToken(new File(idCardFront), new File(idCardBack),
                 ticketid,  phone,  name,  idCard,  prov,  city,  district,  addressStr);

    }

    private boolean check(String phone,String name, String idCard, String prov, String addressStr) {
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort("领票人不能为空");
            return false;
        }

        if (TextUtils.isEmpty(prov)) {
            ToastUtil.showShort("地址不能呢为空");
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort("手机号码不能为空");
            return false;
        }

        if (!NumberUtil.Instance.isMobileNo(phone)) {
            ToastUtil.showShort("手机号码格式不正确");
            return false;
        }

        if (TextUtils.isEmpty(idCard)) {
            ToastUtil.showShort("身份证号不能为空");
            return false;
        }

        if (NumberUtil.Instance.isIdCardNumber(idCard)) {
            ToastUtil.showShort("身份证号格式不正确");
            return false;
        }


        if (TextUtils.isEmpty(addressStr)) {
            ToastUtil.showShort("详细地址不能为空");
            return false;
        }
        return true;
    }

    private void getQiNiuToken(final File fileFront, final File fileBack,
                               final String ticketid, final String phone, final String name, final String idCard, final String prov, final String city, final String district, final String addressStr) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.size, "2");
        map.put(RequestParams.type, 1);
        Observable<BaseResponse<QiNiuTokensEntity>> observable = HttpUtil.getInstance().sApi.getQiNiuToken(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<QiNiuTokensEntity>(mContext, "正在更新...") {
            @Override
            protected void _onNext(QiNiuTokensEntity dataBean) {
                QiNiuTokensEntity.ResultsBean resultsBean = dataBean.getResults().get(0);
                String attachNameFront = resultsBean.getAttachName();
                String tokenFront = resultsBean.getToken();
                QiNiuTokensEntity.ResultsBean resultsBean1 = dataBean.getResults().get(1);
                String attachNameBack = resultsBean1.getAttachName();
                String tokenBack = resultsBean1.getToken();
                if (!TextUtils.isEmpty(attachNameFront) && !TextUtils.isEmpty(tokenFront)) {
                    putQiNiu(fileFront, attachNameFront, tokenFront,fileBack, attachNameBack, tokenBack,
                            ticketid,  phone,  name,  idCard,  prov,  city,  district,  addressStr);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void putQiNiu(File fileFront, String attachNameFront, String tokenFront,
                          final File fileBack, final String attachNameBack, final String tokenBack ,
                          final String ticketid, final String phone, final String name, final String idCard, final String prov, final String city, final String district, final String addressStr) {

        DialogMaker.showProgressDialog(mContext, "正在更新", null, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                QiNiuUtil.Instance.cancell();
                DialogMaker.dismissProgressDialog();
            }
        }).setCanceledOnTouchOutside(true);

        QiNiuUtil.Instance.putImg(fileFront, attachNameFront, tokenFront, new QiNiuUtil.OnPutImgListener() {
            @Override
            public void onPutSucceed(final String frontUrl) {
                QiNiuUtil.Instance.putImg(fileBack, attachNameBack, tokenBack, new QiNiuUtil.OnPutImgListener() {
                    @Override
                    public void onPutSucceed(String backUrl) {
                        receiveTicketFinal(backUrl, ticketid, idCard, frontUrl, name, prov, city, district, addressStr, phone);
                    }

                    @Override
                    public void onPutError() {
                        DialogMaker.dismissProgressDialog();
                        ToastUtil.showShort("更新失败");
                    }
                });
            }

            @Override
            public void onPutError() {
                DialogMaker.dismissProgressDialog();
                ToastUtil.showShort("更新失败");
            }
        });


    }

    private void receiveTicketFinal(String phone, String backUrl, String ticketid, String idCard, String frontUrl, String name, String prov, String city, String district, String addressStr) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.ticket_id, ticketid);
        map.put(RequestParams.id_number, idCard);
        map.put(RequestParams.id_number_photo_back, backUrl);
        map.put(RequestParams.id_number_photo_front, frontUrl);
        map.put(RequestParams.name, name);
        map.put(RequestParams.prov, prov);
        map.put(RequestParams.city, city);
        map.put(RequestParams.distinct, district);
        map.put(RequestParams.full_address, addressStr);
        map.put(RequestParams.phone, phone);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.receiveTicket(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object resultEntity) {
                getView().onReceiveTicketSuccess();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });

    }

}
