package com.drops.waterdrop.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.webview.DefaultWebChromeClient;
import com.drops.waterdrop.ui.base.webview.DefaultWebViewClient;
import com.drops.waterdrop.ui.base.webview.JsInteration;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;

import butterknife.Bind;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/08 16:54
 */

public class BaseWebViewActivity extends BaseActivity {

    public static void startActivity(Context context, String url){
        startActivity(context, url, null);
    }

    public static void startActivity(Context context, String url, String title){
        Intent intent = new Intent(context, BaseWebViewActivity.class);
        intent.putExtra(Constants.EXTRA_URL, url);
        if (!TextUtils.isEmpty(title))
            intent.putExtra(Constants.EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    @Bind(R.id.webview)
    WebView mWebView;

    private String url;
    private String title;

    @Override
    protected void initView() {
        parseDataFromIntent(getIntent());
        registerFinishReceiver(Constants.BR_PAY_H5_SUCC);
        initTitle();
        initWebView();
    }

    private void initTitle(){
        MyToolBarOptions options = getToolBarOptions();
        if (options == null){
            options = new MyToolBarOptions();
            options.isNeedNavigate = true;
            if (!TextUtils.isEmpty(title))
                options.titleString = title;
            options.onNavigateClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendBroadcast(new Intent(Constants.BR_PAY_H5_SUCC));
                }
            };
        }
        setMyToolbar(options);
    }

    private void initWebView(){
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(false);
        settings.setDisplayZoomControls(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            settings.setUseWideViewPort(true);
        }

        if (!TextUtils.isEmpty(getUserAgent())) {
            settings.setUserAgentString(settings.getUserAgentString() + " " + getUserAgent());
        } else {
            settings.setUserAgentString(settings.getUserAgentString() + " WaterDropAndroid");
        }

        mWebView.requestFocus();
        mWebView.requestFocusFromTouch();

        WebChromeClient webChromeClient = getWebChromeClient();
        mWebView.setWebChromeClient(webChromeClient == null ? new DefaultWebChromeClient(this) : webChromeClient);

        WebViewClient webViewClient = getWebViewClient();
        mWebView.setWebViewClient(webViewClient == null ? new DefaultWebViewClient(this) : webViewClient);

        mWebView.addJavascriptInterface(new JsInteration(this), "WaterDrop");

        mWebView.loadUrl(url);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sendBroadcast(new Intent(Constants.BR_PAY_H5_SUCC));
    }

    public MyToolBarOptions getToolBarOptions(){
        return null;
    }

    public String getUserAgent(){
        return null;
    }

    public WebChromeClient getWebChromeClient(){
        return null;
    }

    public WebViewClient getWebViewClient(){
        return null;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_base_webview;
    }

    private void parseDataFromIntent(Intent intent){
        url = intent.getStringExtra(Constants.EXTRA_URL);
        title = intent.getStringExtra(Constants.EXTRA_TITLE);
    }

    private void parseDataFromBundle(Bundle bundle){
        url = bundle.getString(Constants.EXTRA_URL);
        title = bundle.getString(Constants.EXTRA_TITLE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseDataFromIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.EXTRA_URL, url);
        outState.putString(Constants.EXTRA_TITLE, title);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        parseDataFromBundle(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (DialogMaker.isShowing()) {
            DialogMaker.dismissProgressDialog();
        }
        if (mWebView != null) {
            mWebView.clearHistory();
            mWebView.destroy();
        }
        super.onDestroy();
    }
}
