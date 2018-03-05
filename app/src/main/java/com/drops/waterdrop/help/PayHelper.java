package com.drops.waterdrop.help;

import android.content.Context;

import com.drops.waterdrop.app.WaterDropApp;
import com.netease.nim.uikit.model.WechatPayDetail;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.netease.nim.uikit.Constants.WEIXIN_APP_ID;

/**
 * Created by Wonder on 2016/8/7.
 */
public enum PayHelper {

    Instance;

    //微信API
    IWXAPI api;
    private Context context;

    private String json = null;

    PayHelper() {
    }

    //调用微信进行支付
    public void payWeChat(WechatPayDetail payDetail) {
        this.context = WaterDropApp.sContext;
        api = WXAPIFactory.createWXAPI(context, WEIXIN_APP_ID);
        api.registerApp(WEIXIN_APP_ID);
        String appId = payDetail.getAppid();
        String partnerId = payDetail.getPartnerid();
        String prepayId = payDetail.getPrepayid();
        String packageValue = payDetail.getAppPackage();
        String nonceStr = payDetail.getNoncestr();
        String timeStamp = String.valueOf(payDetail.getTimestamp());
        String sign = payDetail.getSign();

        PayReq request = new PayReq();
        request.appId = appId;
        request.partnerId = partnerId;
        request.prepayId = prepayId;
        request.packageValue = packageValue;
        request.nonceStr = nonceStr;
        request.timeStamp = timeStamp;
        request.sign = sign;
        api.sendReq(request);

    }


    //调用支付宝进行支付
/*
    public String payAliPay() {
        HashMap goodDesc = new HashMap();
        goodDesc.put("id", "123");
        HashMap map = new HashMap();
        map.put("appId", "2016072101650516");
        map.put("appenv", "222");
        map.put("goodsDesc", goodDesc);
        map.put("sellerId", "pay@1jia2.cn");
        map.put("sellerPhone", "13524829792");
        OKHttpUtil.Instance.enqueuePostJson("", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                if (code != 200) {
                    return;
                }
                json = response.body().string();
            }
        });
        return json;
    }
*/
}
