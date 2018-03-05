package com.drops.waterdrop.ui.find.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.help.PayHelper;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.base.BaseWebViewActivity;
import com.drops.waterdrop.ui.find.activity.OrderConfirmationActivity;
import com.drops.waterdrop.ui.find.view.IOrderConfirmationView;
import com.drops.waterdrop.util.ToastUtil;
import com.google.gson.Gson;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.GoodsDetailEntity;
import com.netease.nim.uikit.model.GoodsInfoJson;
import com.netease.nim.uikit.model.OrderDetailEntity;
import com.netease.nim.uikit.model.WechatPayDetail;
import com.netease.nim.uikit.model.WujieTradeConstructEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/6/8.
 */

public class OrderConfirmationPresenter extends BasePresenter<IOrderConfirmationView> {

    private static final String PAY_TYPE_H5 = "h5";
    private static final String PAY_TYPE_YJF = "yjf";
    private static final String PAY_TYPE_WECHAT = "wechat";

    public int mOrderType;
    public GoodsDetailEntity mEntity;
    private boolean mHasDefaultAddress;
    private long mDropId;
    private long mTipId;
    private boolean crossBorder;
    private boolean fromWuji;
    public GoodsDetailEntity.GoodBean.GoodSku goodSku;

    public OrderConfirmationPresenter(BaseActivity context) {
        super(context);
    }

    public boolean isCrossBorder() {
        return crossBorder;
    }

    public void initAddressView() {
        getAddressList();
    }

    public void getAddressList() {
        Observable<BaseResponse<AddressEntity>> observable = HttpUtil.getInstance().sApi.getAddress(RequestBodyUtils.build(null));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity>(mContext) {
            @Override
            protected void _onNext(AddressEntity dataBean) {
                List<AddressEntity.ResultsBean> results = dataBean.getResults();
                if (results != null && results.size() > 0) {
                    getDefaultAddress(results);
                } else {
                    getView().onNoAddress();
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                getView().onNoAddress();

            }
        });
    }

    private void getDefaultAddress(List<AddressEntity.ResultsBean> results) {
        for (AddressEntity.ResultsBean result : results) {
            if (result.getDefaultFlag() == 1) {//是默认地址
                getView().setAddressView(result);

                mHasDefaultAddress = true;
            }
        }

        if (!mHasDefaultAddress) {
            getView().setAddressView(results.get(0));
        }
    }


    public void goOrder(long addressId, String num, boolean checked, int currentPostition, String billContent, String billCode, boolean isCompleteIdCard) {
        if (addressId < 0) {
            ToastUtil.showShort("请先添加地址");
            return;
        }

        if (isCrossBorder() && !isCompleteIdCard) {
            ToastUtil.showShort("为保证您的订单有效，请如实填写收货人的身份证信息！");
            return;
        }

        if (Integer.parseInt(num) <= 0) {
            ToastUtil.showShort("请填写正确的商品数量");
            return;
        }
        if (checked) {
            if (currentPostition == 1) {//公司发票
                if (TextUtils.isEmpty(billContent)) {
                    ToastUtil.showShort("请填写发票抬头");
                    return;
                }
                if (TextUtils.isEmpty(billCode)) {
                    ToastUtil.showShort("请填写税号");
                    return;
                }
            } else {//个人发票
                if (TextUtils.isEmpty(billContent)) {
                    ToastUtil.showShort("请填写发票抬头");
                    return;
                }
            }
        }

        String payType;
        if (isCrossBorder()) {
            payType = PAY_TYPE_H5;
        } else {
            payType = PAY_TYPE_WECHAT;
        }

        insertOrder(addressId, num, payType, checked, currentPostition, billContent, billCode);

    }

    private void insertOrder(long addressId, String num, String payType, boolean checked, int currentPostition, String billContent, String billCode) {
        if (!isCrossBorder() && !WaterDropApp.mWxApi.isWXAppInstalled()) {
            ToastUtil.showShort("您还未安装微信， 请先下载微信应用");
            return;
        }

        List<GoodsInfoJson> goodsInfoJsons = new ArrayList<>();
        GoodsInfoJson json = new GoodsInfoJson();
        json.setQuantity(Integer.valueOf(num));
        json.setDrop_id(mDropId);
        json.setTip_id(mTipId);
        json.setGood_id(mEntity.getGood().getGoodId());
        if (goodSku != null && !TextUtils.isEmpty(goodSku.getSkuId())) {
            long skuid = Long.parseLong(goodSku.getSkuId());
            if (skuid > 0) {
                json.setSku_id(skuid);
            }
        }
        goodsInfoJsons.add(json);
        Gson gson = new Gson();
        String s = gson.toJson(goodsInfoJsons);

        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.address_id, addressId);
        map.put(RequestParams.pay_info, payType);
        map.put(RequestParams.good_info_json, s);

        if (checked) {
            map.put(RequestParams.need_bill, 1);
            if (currentPostition == 1) {
                map.put(RequestParams.bill_type, 2);
                map.put(RequestParams.bill_tfn, billCode);
            } else {
                map.put(RequestParams.bill_type, 1);
            }
            map.put(RequestParams.bill_company, billContent);
        } else {
            map.put(RequestParams.need_bill, 0);
        }

        Observable<BaseResponse<OrderDetailEntity>> observable = HttpUtil.getInstance().sApi.insertOrder(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<OrderDetailEntity>(mContext) {
            @Override
            protected void _onNext(OrderDetailEntity entity) {
                if (entity == null)
                    return;

                String payInfo = entity.getPayInfo();

                if (TextUtils.isEmpty(payInfo))
                    return;

                long orderId = entity.getOrderId();
                switch (payInfo.toLowerCase()) {
                    case PAY_TYPE_YJF:
                    case PAY_TYPE_H5:
                        requestWujieTradeConstruct(orderId);
                        break;
                    case PAY_TYPE_WECHAT:
                        getWachatPayStr(orderId);
                        break;
                    default:
                        ToastUtil.showShort("当前的订单支付方式暂不支持");
                        break;
                }
//                if ("wechat".equalsIgnoreCase(entity.getPayInfo())){
//                    getWachatPayStr(orderId);
//                } else {
//                    requestWujieTradeConstruct(orderId);
//            }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void getWachatPayStr(long orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, orderId);
        Observable<BaseResponse<WechatPayDetail>> observable = HttpUtil.getInstance().sApi.getWechatPayStr(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<WechatPayDetail>(mContext, "正在支付...") {
            @Override
            protected void _onNext(WechatPayDetail wechatStr) {
                wechatPay(wechatStr);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void wechatPay(WechatPayDetail wechatStr) {
        if (mDropId == Constants.STORE_DROP_ID && mTipId == Constants.STORE_TIP_ID) {
            if (MyUserCache.isChinaPavilionActivityActive()) {
                WaterDropApp.PAY_FROM = Constants.PAY_FROM_CHINA_PAVILION;
            } else {
                WaterDropApp.PAY_FROM = Constants.PAY_FROM_CHINA_PAVILION_HOME;
            }
        } else if (mDropId == Constants.VIP_DROP_ID && mTipId == Constants.VIP_TIP_ID) {
            if (WaterDropApp.PAY_FROM_H5 == Constants.PAY_FROM_CHINA_PAVILION_H5) {
                WaterDropApp.PAY_FROM = Constants.PAY_FROM_CHINA_PAVILION_H5;
            } else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_VIP_AREA) {
                WaterDropApp.PAY_FROM = Constants.PAY_FROM_VIP_AREA;
            } else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_ORDER_LIST) {
                WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_LIST;
            } else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_ORDER_DETAIL) {
                WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_DETAIL;
            }
        } else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_TIP_DETAIL){
            WaterDropApp.PAY_FROM = Constants.PAY_FROM_TIP_DETAIL;
        }else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_ORDER_LIST) {
            WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_LIST;
        } else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_ORDER_DETAIL) {
            WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_DETAIL;
        }
        PayHelper.Instance.payWeChat(wechatStr);
    }

    public void parseIntent(Intent intent) {
        mOrderType = intent.getIntExtra(OrderConfirmationActivity.EXTRA_ORDER_TYPE, 1);
        mDropId = intent.getLongExtra(Constants.EXTRA_DROP_ID, 0);
        mTipId = intent.getLongExtra(Constants.EXTRA_TIP_ID, 0);
        goodSku = (GoodsDetailEntity.GoodBean.GoodSku) intent.getSerializableExtra(Constants.EXTRA_GOODSKU);
        mEntity = (GoodsDetailEntity) intent.getSerializableExtra(Constants.EXTRA_ENTITY);
        crossBorder = mEntity.getGood().isCrossBorder();
        fromWuji = mEntity.getGood().fromWuJi();
        if (mOrderType == Constants.ORDER_TYPE_DEFAULT) {
            getView().setIntegralViewHidden();
        } else if (mOrderType == Constants.ORDER_TYPE_ACTIVE) {
            getView().setIntegralViewShow();
        }
    }

    public String payMethod() {
        return crossBorder ? "无界支付" : "微信支付";
    }


    public void requestWujieTradeConstruct(long orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", orderId);
        Observable<BaseResponse<WujieTradeConstructEntity>> observable = HttpUtil.getInstance().sApi.getWujieTradeConstruct(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<WujieTradeConstructEntity>(mContext, true) {
            @Override
            protected void _onNext(WujieTradeConstructEntity entity) {
                if (entity != null && !TextUtils.isEmpty(entity.getWjPayWayUrl())) {
                    BaseWebViewActivity.startActivity(mContext, entity.getWjPayWayUrl(), mContext.getString(R.string.title_pay_h5));
                } else {
                    ToastUtil.showShort("获取支付地址失败");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

}
