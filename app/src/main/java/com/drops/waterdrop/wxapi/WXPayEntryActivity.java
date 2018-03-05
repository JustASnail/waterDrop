package com.drops.waterdrop.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.drops.waterdrop.ui.find.activity.PayResultActivity;
import com.netease.nim.uikit.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by young chan on 2016/7/29
 * 微信支付回调界面，该界面必须在wxapi目录下，以便通知用户支付结果
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.getType()) {
            case ConstantsAPI.COMMAND_PAY_BY_WX:
                jumpOrderStatusPage(resp.errCode);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void jumpOrderStatusPage(int code) {

        if (code == PayCode.SUCCEED.getCode()) {
            PayResultActivity.start(this, true);
        } else if (code == PayCode.CANCEL.getCode()) {
            PayResultActivity.start(this, false);

        } else if (code == PayCode.ERROR.getCode()) {

        }
        finish();
    }

    private enum PayCode {
        SUCCEED(0), ERROR(-1), CANCEL(-2);
        private int code;

        PayCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}