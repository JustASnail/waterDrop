package com.drops.waterdrop.ui.other.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/6/28.
 */

public class UserAgreementActivity extends BaseActivity {
    @Bind(R.id.web_view)
    WebView mWebView;

    public static void start(Context context) {
        Intent starter = new Intent(context, UserAgreementActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle();

        mWebView.getSettings().setJavaScriptEnabled(true);//支持javascript
        mWebView.requestFocus();//触摸焦点起作用mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);//取消滚动条
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置允许js弹出alert对话框


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //设置WebViewClient类
        mWebView.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                DialogMaker.showProgressDialog(UserAgreementActivity.this, "Loading...", true);

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                DialogMaker.dismissProgressDialog();

            }
        });

//设置了Alert才会弹出，重新onJsAlert（）方法return true可以自定义处理信息
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//return super.onJsAlert(view, url, message, result);
                Toast.makeText(UserAgreementActivity.this, message, Toast.LENGTH_LONG).show();
                return true;
            }
        });

        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

//设置自适应屏幕，两者合用
//        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(true); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//

        mWebView.loadUrl(Constants.USER_AGREEMENT);
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "用户协议";
        setMyToolbar(options);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_agreement;
    }


    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
