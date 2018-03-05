package com.drops.waterdrop.ui.store;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.ui.find.activity.GoodsDetailsActivity;
import com.drops.waterdrop.ui.find.activity.OrderConfirmationActivity;
import com.drops.waterdrop.ui.mine.activity.MemberCenterActivity;
import com.drops.waterdrop.ui.store.fragment.StoreSelfFragment;
import com.drops.waterdrop.util.ToastUtil;
import com.google.gson.Gson;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.media.picker.PickImageHelper;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.event.H5CameraClickEvent;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AppInfoEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.GoodsDetailEntity;
import com.netease.nim.uikit.model.JsInteractionParamsEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

import static com.drops.waterdrop.ui.find.activity.CommonWebActivity.PAGE_TAPY_GOODS_BUY;
import static com.drops.waterdrop.ui.find.activity.CommonWebActivity.PAGE_TAPY_GOODS_DETAIL;

/**
 * Created by Mr.Smile on 2017/8/23.
 */

public class StoreActivity extends AppCompatActivity {

    private static final String TAG = "StoreActivity";
    //无界商城地址
    public String mUrl = "http://www.qhwj123.com/wxIndex/weChatIndex.html";
//    public String mUrl = "http://sl.dzd.wddcn.com/vshop/index.html?sid=155269";
//    public String mUrl = "http://192.168.0.168:8092/banner-action.html";
    private TextView mTvTitle;
    private WebView mWebView;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mUnStoreUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        EventBus.getDefault().register(this);
        mUnStoreUrl = getIntent().getStringExtra(Constants.UNSTORE_URL);
        initView();
        if (TextUtils.isEmpty(mUnStoreUrl)) {
            mTvTitle.setText("商城");
            getPic();
//            initWebView(mUrl);
        } else {
            initWebView(mUnStoreUrl);
        }
    }

    private void getPic() {
        Observable<BaseResponse<AppInfoEntity>> observable = HttpUtil.getInstance().sApi.getAppInfo();
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AppInfoEntity>(this) {
            @Override
            protected void _onNext(AppInfoEntity entity) {
                List<AppInfoEntity.ResultsBean> results = entity.getResults();
                for (AppInfoEntity.ResultsBean resultsBean : results) {
                    if (resultsBean.getParamKey().equals(Constants.SHOP_URL)) {
                        mUrl = resultsBean.getParamValue();
                        initWebView(mUrl);
                    }
                }
            }

            @Override
            protected void _onError(String message) {
            }
        });
    }

    private void initWebView(String url) {
        mWebView.requestFocus();

        //设置WebViewClient类
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!DialogMaker.isShowing()) {
                    DialogMaker.showProgressDialog(StoreActivity.this, "Loading...", true);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                DialogMaker.dismissProgressDialog();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading: " + url);
                if (url.startsWith("mqqwpa")) {
                    if (isQQClientAvailable(StoreActivity.this)) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } else {
                        ToastUtil.showShort("你还未安装QQ客户端");
                    }
                }
                if (url.startsWith("tel")) {
                    requestCallPhonePermission(url);
                }
                if (!(url.startsWith("http") || url.startsWith("https"))) {
                    return true;
                }
                final PayTask task = new PayTask(StoreActivity.this);
                boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
                    @Override
                    public void onPayResult(final H5PayResultModel result) {
                        final String url = result.getReturnUrl();
                        if (!TextUtils.isEmpty(url)) {
                            StoreActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.loadUrl(url);
                                }
                            });
                        } else {
                            StoreActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG, "run: goback");
                                    mWebView.goBack();
                                }
                            });
                        }
                    }
                });

                /**
                 * 判断是否成功拦截
                 * 若成功拦截，则无需继续加载该URL；否则继续加载
                 */
                if (!isIntercepted) {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mFilePathCallback = filePathCallback;
                requestCameraPermission();
                return true;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
//                Log.d(TAG, "onReceivedTitle: " + title);
                if (!TextUtils.isEmpty(mUnStoreUrl)) {
                    mTvTitle.setText(title);
                }
            }
        });


        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(true); //隐藏原生的缩放控件
        webSettings.setUserAgentString("WaterDropAndroid");
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setDomStorageEnabled(true); //支持local storage
        mWebView.addJavascriptInterface(new JsInteration(), "WaterDrop");
        mWebView.loadUrl(url);
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_commn_title);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_left);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView = (WebView) findViewById(R.id.web_view);
    }

    public class JsInteration {
        @JavascriptInterface
        public void interaction(String arg) {
            try {
                String encode = URLDecoder.decode(arg, "UTF-8");
                Gson gson = new Gson();
                JsInteractionParamsEntity params = gson.fromJson(encode, JsInteractionParamsEntity.class);
                String action = params.getAction();
                if (TextUtils.equals("getUserInfoFromWJ", action)) {
                    onGetUserInfoFromWJ(params.getParams().getFun());
                } else if (TextUtils.equals("upLoadPhotoFromWJ", action)) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        requestCameraPermission();
                    }
                } else if (TextUtils.equals("buyMember", action)) {
                    MemberCenterActivity.startActivity(StoreActivity.this, MemberCenterActivity.TYPE_CLICK_EVENT_BUY);
                } else if (TextUtils.equals("activityMember", action)) {
                    MemberCenterActivity.startActivity(StoreActivity.this, MemberCenterActivity.TYPE_CLICK_EVENT_ACTIVE);
                } else if (TextUtils.equals("goodsBuy", action)) {
                    JsInteractionParamsEntity.ParamsBean params1 = params.getParams();
                    getGoodsData(params1.getGoodId(), params1.getDropId(), params1.getTipId(), params1.getTipTitle(), PAGE_TAPY_GOODS_BUY);
                }else if (TextUtils.equals("goodsDetail", action)) {
                    WaterDropApp.PAY_FROM_H5 = Constants.PAY_FROM_CHINA_PAVILION_H5;
                    JsInteractionParamsEntity.ParamsBean params1 = params.getParams();
                    getGoodsData(params1.getGoodId(), params1.getDropId(), params1.getTipId(), params1.getTipTitle(), PAGE_TAPY_GOODS_DETAIL);
                } else if (TextUtils.equals("getUserInfo", action)) {
                    onLoading(params.getParams().getFun());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getGoodsData(String goodsId, final long dropId, final long tipId, final String tipTitle, final int pageType) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.good_id, goodsId);
        Observable<BaseResponse<GoodsDetailEntity>> observable = HttpUtil.getInstance().sApi.getGoodsDetail(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<GoodsDetailEntity>(this) {
            @Override
            protected void _onNext(GoodsDetailEntity o) {
                if (o != null) {
                    if (pageType == PAGE_TAPY_GOODS_BUY) {
                        OrderConfirmationActivity.start(StoreActivity.this, o, dropId, tipId, Constants.ORDER_TYPE_DEFAULT);
                    } else {
                        GoodsDetailsActivity.start(StoreActivity.this, o, tipId, dropId, tipTitle);
                    }
                } else {
                    ToastUtil.showShort("此商品异常");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void onGetUserInfoFromWJ(final String fun) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.userToken, MyUserCache.getUserToken());
        map.put(RequestParams.uid, MyUserCache.getUserUid());
        map.put(RequestParams.nickname, MyUserCache.getUserNickName());
        map.put(RequestParams.mobile, MyUserCache.getUserMobile());
        map.put(RequestParams.wxunionid, MyUserCache.getUserUnionId());
        Gson gson = new Gson();
        final String json = gson.toJson(map);
        try {
            final String encode = URLEncoder.encode(json, "UTF-8");
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:" + fun + "('" + encode + "')");
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static final int PICK_AVATAR_REQUEST = 0x0E;

    private void requestCameraPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(false);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            if (mFilePathCallback != null) {
                                mFilePathCallback.onReceiveValue(null);
                                mFilePathCallback = null;
                            }

                            ToastUtil.showShort("未全部授权，无法开启相机和相册, 请开启相关权限！");
                        } else {
                            showDialog();
                        }
                    }
                });

    }

    private void requestCallPhonePermission(final String url) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(false);
        rxPermissions.request(Manifest.permission.CALL_PHONE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent intent = new Intent(); // 意图对象：动作 + 数据
                            intent.setAction(Intent.ACTION_CALL); // 设置动作
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        }
                    }
                });
    }

    private void showDialog() {
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.titleResId = R.string.set_image;
        option.crop = true;
        option.multiSelect = false;
        option.cropOutputImageWidth = 720;
        option.cropOutputImageHeight = 720;
        PickImageHelper.pickImage(this, PICK_AVATAR_REQUEST, option);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSystemMessageEvent(H5CameraClickEvent event) {
        final String imgPath = event.getImgPath();
        if (!TextUtils.isEmpty(imgPath)) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mWebView.evaluateJavascript("javascript:showPic" + "('" + imgPath + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
//                        Log.d(TAG, "onReceiveValue: " + value);
                    }
                });
            } else {
//                Log.d(TAG, "onSystemMessageEvent: notnull" + imgPath);
                Uri[] uri = {Uri.fromFile(new File(imgPath))};
                mFilePathCallback.onReceiveValue(uri);
                mFilePathCallback = null;
            }
        } else {
//            Log.d(TAG, "onSystemMessageEvent: null");
            mFilePathCallback.onReceiveValue(null);
            mFilePathCallback = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == StoreSelfFragment.PICK_AVATAR_REQUEST) {
            H5CameraClickEvent event = new H5CameraClickEvent();
            if (data == null) {
//                Log.d(TAG, "onSystemMessageEvent: null");
                mFilePathCallback.onReceiveValue(null);
                mFilePathCallback = null;
            } else {
                String imgPath = data.getStringExtra(com.netease.nim.uikit.session.constant.Extras.EXTRA_FILE_PATH);
                if (!TextUtils.isEmpty(imgPath)) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
                            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        mWebView.evaluateJavascript("javascript:showPic" + "('" + imgPath + "')", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
//                        Log.d(TAG, "onReceiveValue: " + value);
                            }
                        });
                    } else {
//                        Log.d(TAG, "onSystemMessageEvent: notnull" + imgPath);
                        Uri[] uri = {Uri.fromFile(new File(imgPath))};
                        mFilePathCallback.onReceiveValue(uri);
                        mFilePathCallback = null;
                    }
                } else {
//                    Log.d(TAG, "onSystemMessageEvent: null");
                    mFilePathCallback.onReceiveValue(null);
                    mFilePathCallback = null;
                }
            }
        }
    }

    private void onLoading(final String fun) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("userToken", MyUserCache.getUserToken());
        map.put("uid", MyUserCache.getUserUid());
        Gson gson = new Gson();
        final String json = gson.toJson(map);
        try {
            final String encode = URLEncoder.encode(json, "UTF-8");
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:" + fun + "('" + encode + "')");
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
