package com.drops.waterdrop.ui.base.webview;

import android.content.Intent;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.drops.waterdrop.ui.base.BaseWebViewActivity;
import com.drops.waterdrop.ui.mine.activity.MyOrderPageActivity;
import com.google.gson.Gson;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.model.JsInteractionParamsEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/12 23:04
 */

public class JsInteration {

    private static final String ACTION_wjOrderPayFinish = "wjOrderPayFinish";

    private BaseWebViewActivity context;
    public JsInteration(BaseWebViewActivity context){
        this.context = context;
    }

    @JavascriptInterface
    public void interaction(String arg) {
        if (TextUtils.isEmpty(arg))
            return;

        String encode = null;
        try {
            encode = URLDecoder.decode(arg, "UTF-8");
            Gson gson = new Gson();
            JsInteractionParamsEntity params = gson.fromJson(encode, JsInteractionParamsEntity.class);
            String action = params.getAction();

            switch (action){
                case ACTION_wjOrderPayFinish:
                    MyOrderPageActivity.start(context, Constants.ALL);
                    context.sendBroadcast(new Intent(Constants.BR_PAY_H5_SUCC));
                    break;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
