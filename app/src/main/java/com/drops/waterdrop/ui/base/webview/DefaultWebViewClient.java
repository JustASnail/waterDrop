package com.drops.waterdrop.ui.base.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.netease.nim.uikit.common.ui.dialog.DialogMaker;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/12 22:33
 */

public class DefaultWebViewClient extends WebViewClient {

    private Context context;
    public DefaultWebViewClient(Context context){
        this.context = context;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (!DialogMaker.isShowing()) {
            DialogMaker.showProgressDialog(context, "Loading...", true);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        DialogMaker.dismissProgressDialog();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.e("daohen","shouldOverrideUrlLoading="+url);
        if (url.startsWith("http://") || url.startsWith("https://")) {
            view.loadUrl(url);
        }
        return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        handler.proceed();
    }
}
