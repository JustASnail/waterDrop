package com.drops.waterdrop.ui.find.activity;

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
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.activity.MemberCenterActivity;
import com.drops.waterdrop.ui.mine.activity.UserProfileActivity;
import com.drops.waterdrop.ui.store.fragment.StoreSelfFragment;
import com.drops.waterdrop.util.ShareUtils;
import com.drops.waterdrop.util.SinaUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.google.gson.Gson;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.media.picker.PickImageHelper;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.event.H5CameraClickEvent;
import com.netease.nim.uikit.event.OnTipColletionEvent;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AppInfoEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.GoodsDetailEntity;
import com.netease.nim.uikit.model.JsInteractionParamsEntity;
import com.netease.nim.uikit.model.LinkedMeModel;
import com.netease.nim.uikit.request_body.RequestParams;
import com.netease.nim.uikit.session.activity.ContactsSelectActivity;
import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.share.WbShareHandler;
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

import butterknife.Bind;
import rx.Observable;
import rx.functions.Action1;


/**
 * Created by dengxiaolei on 2017/7/7.
 */

public class CommonWebActivity extends BaseActivity {
    @Bind(R.id.web_view)
    WebView mWebView;
    private String mUrl;

    public static final int PAGE_TAPY_GOODS_DETAIL = 1;
    public static final int PAGE_TAPY_GOODS_BUY = 2;
    private long mDropId;
    private long mTipId;

    private String mTipTitle, mTipPhoto, mTipDesc;
    private String mCover;

    private WbShareHandler shareHandler;
    private BottomSheetDialog mBottomDialog;
    private String mTipShareUrl;
    private String mDropName;
    private String mTipUrl;
    private boolean mIsRefreshCollectionStatus;
    private ValueCallback<Uri[]> mFilePathCallback;
    private int mH5Type = 0;
    private TextView mTvTitle;
    private boolean mIsWuJieStore;
    private boolean mIsActive;


    public static void start(Context context, long tipId, String tipUrl) {
        Intent starter = new Intent(context, CommonWebActivity.class);
        starter.putExtra(Constants.EXTRA_TIP_ID, tipId);
        starter.putExtra(Constants.EXTRA_TIP_URL, tipUrl);
        starter.putExtra(Constants.EXTRA_IS_WU_JIE, false);
        starter.putExtra(Constants.EXTRA_IS_ACTIVE, false);


        WaterDropApp.tipId = tipId;
        WaterDropApp.tipUrl = tipUrl;
        context.startActivity(starter);
    }

    public static void startOfHome(Context context, long tipId, String tipUrl, boolean isRefreshCollectionStatus) {
        Intent starter = new Intent(context, CommonWebActivity.class);
        starter.putExtra(Constants.EXTRA_TIP_ID, tipId);
        starter.putExtra(Constants.EXTRA_TIP_URL, tipUrl);
        starter.putExtra(Constants.EXTRA_TIP_COLLECTION_STATUS, isRefreshCollectionStatus);
        starter.putExtra(Constants.EXTRA_IS_WU_JIE, false);
        starter.putExtra(Constants.EXTRA_IS_ACTIVE, false);

        WaterDropApp.tipId = tipId;
        WaterDropApp.tipUrl = tipUrl;
        context.startActivity(starter);
    }

    public static void startOfActive(Context context, String tipUrl) {
        Intent starter = new Intent(context, CommonWebActivity.class);
        starter.putExtra(Constants.EXTRA_TIP_URL, tipUrl);
        starter.putExtra(Constants.EXTRA_IS_ACTIVE, true);
        starter.putExtra(Constants.EXTRA_IS_WU_JIE, false);
        WaterDropApp.tipUrl = tipUrl;
        context.startActivity(starter);
    }

    public static void startForStore(Context context) {
        Intent starter = new Intent(context, CommonWebActivity.class);
        starter.putExtra(Constants.EXTRA_IS_WU_JIE, true);
        context.startActivity(starter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            mIsWuJieStore = intent.getBooleanExtra(Constants.EXTRA_IS_WU_JIE, false);
            if (mIsWuJieStore) {//无界商城
                getStoreUrl();

            } else {

                mIsActive = intent.getBooleanExtra(Constants.EXTRA_IS_ACTIVE, false);
                long tipId = intent.getLongExtra(Constants.EXTRA_TIP_ID, 0);
                mTipUrl = intent.getStringExtra(Constants.EXTRA_TIP_URL);
                mIsRefreshCollectionStatus = intent.getBooleanExtra(Constants.EXTRA_TIP_COLLECTION_STATUS, false);

                if (mIsActive) {//活动
                    mUrl = mTipUrl;
                } else {
                    mUrl = mTipUrl + "?tip_id=" + tipId;
                }

                if (mWebView != null) {
                    mWebView.loadUrl(mUrl);

                } else {
                    mH5Type = Constants.H5_TYPE_POST;
                    initView();
                }
            }
        }

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        long tipId = 0;
        if (savedInstanceState != null) {
            mTipId = savedInstanceState.getLong(Constants.EXTRA_TIP_ID);
        }

        if (getIntent() != null) {
            Intent intent = getIntent();
            mIsWuJieStore = intent.getBooleanExtra(Constants.EXTRA_IS_WU_JIE, false);
            if (mIsWuJieStore) {
                    //处理境外馆逻辑
                    getStoreUrl();

            } else {
                mIsActive = intent.getBooleanExtra(Constants.EXTRA_IS_ACTIVE, false);

                tipId = intent.getLongExtra(Constants.EXTRA_TIP_ID, 0);
                mTipUrl = intent.getStringExtra(Constants.EXTRA_TIP_URL);
                mIsRefreshCollectionStatus = intent.getBooleanExtra(Constants.EXTRA_TIP_COLLECTION_STATUS, false);
                if (mIsActive) {
                    mUrl = mTipUrl;
                } else {
                    mUrl = mTipUrl + "?tip_id=" + tipId;
                }
            }
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(Constants.EXTRA_TIP_ID, mTipId);
    }

    @Override
    protected void initView() {
        initTitle();

        mWebView.requestFocus();
        //设置WebViewClient类
        mWebView.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!DialogMaker.isShowing()) {
                    DialogMaker.showProgressDialog(CommonWebActivity.this, "Loading...", true);
                }
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                } else {
                    if(!flag_get_deviceid){
                    onLoadUserInfoAt19();
                    }
                }

                DialogMaker.dismissProgressDialog();

                if (mWebView.getUrl().equals("about:blank") && mWebView.canGoBack()) {
                    mWebView.goBack();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                if (url.startsWith("mqqwpa")) {
                    if (isQQClientAvailable(CommonWebActivity.this)) {
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
                final PayTask task = new PayTask(CommonWebActivity.this);
                boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
                    @Override
                    public void onPayResult(final H5PayResultModel result) {
                        final String url = result.getReturnUrl();
                        if (!TextUtils.isEmpty(url)) {
                            CommonWebActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.loadUrl(url);
                                }
                            });
                        } else {
                            CommonWebActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
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
                mTvTitle.setText(mH5Type == Constants.H5_TYPE_STORE ? "商城" : title);
            }
        });

        mWebView.clearCache(true);
        mWebView.clearHistory();
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);

//        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(true); //隐藏原生的缩放控件
        webSettings.setUserAgentString("WaterDropAndroid");
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//
        webSettings.setDomStorageEnabled(true);
        //if (false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setUseWideViewPort(true);
        }

        mWebView.addJavascriptInterface(new JsInteration(), "WaterDrop");

        mWebView.loadUrl(mUrl);
        Logger.d("水帖地址：" + mUrl);
//        mWebView.loadUrl("http://api.waterdrop.xin/drops_wechat/app_h5/tip-detail.html?tip_id=14999170589341");
//        mWebView.loadUrl("https://www.baidu.com");

    }

    private void onLoadUserInfoAt19() {
        Logger.d("水帖获取用户信息：小于21版本");
        final String fun = "getUserInfo";
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

                    mWebView.evaluateJavascript("javascript:" + fun + "('" + encode + "')", new ValueCallback<String>() {

                        @Override
                        public void onReceiveValue(String value) {
                            if(value!=null){
                                flag_get_deviceid=true;
                            }
                        }});
//                    flag_get_deviceid=true;
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void initTitle() {
        ImageView ivRight = (ImageView) findViewById(R.id.iv_right);
        mTvTitle = (TextView) findViewById(R.id.tv_commn_title);
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        switch (mH5Type) {
            case Constants.H5_TYPE_POST:
            case Constants.H5_TYPE_ACTIVE:
                options.imgRightId = R.mipmap.icon_st_fx;
                break;
            case Constants.H5_TYPE_STORE:
                ivRight.setVisibility(View.GONE);
                break;
        }
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
        return R.layout.activity_post_details_for_h5;
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

    private boolean isRun = false;
    public class JsInteration {
        @JavascriptInterface
        public void interaction(String arg) {//商品详情
            if (TextUtils.isEmpty(arg)) return;

            try {
                String encode = URLDecoder.decode(arg, "UTF-8");
                Gson gson = new Gson();
                JsInteractionParamsEntity params = gson.fromJson(encode, JsInteractionParamsEntity.class);
                String action = params.getAction();

                com.orhanobut.logger.Logger.d("水贴详情：" + "h5调用本地方法：" + encode);

                if (TextUtils.equals("goodsBuy", action)) {
                    WaterDropApp.PAY_FROM = Constants.PAY_FROM_TIP_DETAIL;
                    mDropId = params.getParams().getDropId();
                    com.orhanobut.logger.Logger.d("水贴详情：" + "水塘id：" + mDropId);
                    onGoodsBuyClick(params.getParams().getGoodId());

                } else if (TextUtils.equals("goodsDetail", action)) {
                    WaterDropApp.PAY_FROM_H5 = Constants.PAY_FROM_CHINA_PAVILION_H5;
                    mDropId = params.getParams().getDropId();

                    onGetTipDetail(params.getParams());

                    com.orhanobut.logger.Logger.d("水贴详情：" + "水塘id：" + mDropId);

                    onGoodsDetailClick(params.getParams().getGoodId());

                } else if (TextUtils.equals("userInfo", action)) {

                    onCreatorClick(params.getParams().getUid());

                } else if (TextUtils.equals("dropInfo", action)) {
                    mDropId = params.getParams().getDropId();
                    com.orhanobut.logger.Logger.d("水贴详情：" + "水塘id：" + mDropId);

                    onFromPoolClick(params.getParams().getDropId());

                } else if (TextUtils.equals("shareDetail", action)) {
                    mDropId = params.getParams().getDropId();
                    com.orhanobut.logger.Logger.d("水贴详情：" + "水塘id：" + mDropId);
                    onGetTipDetail(params.getParams());

                    // TODO: 2017/9/11 临时 测试用
//                    if (!isRun){
//                        isRun = true;
//                        onGoodsBuyClick("201709121138528287141878");
//                    }

                } else if (TextUtils.equals("getUserInfo", action)) {
                    onLoading(params.getParams().getFun());
                } else if (TextUtils.equals("postCollection", action)) {

                    onColletionIconClick(params.getParams().getBrowserNum(), params.getParams().getCollectStatus());

                } else if (TextUtils.equals("showAlert", action)) {
                    String msg = params.getParams().getMsg();
                    ToastUtil.showShort(msg);

                }else if (TextUtils.equals("getUserInfoFromWJ", action)) {
                    onGetUserInfoFromWJ(params.getParams().getFun());
                } else if (TextUtils.equals("upLoadPhotoFromWJ", action)) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        requestCameraPermission();
                    }
                } else if (TextUtils.equals("buyMember", action)) {
                    MemberCenterActivity.startActivity(CommonWebActivity.this, MemberCenterActivity.TYPE_CLICK_EVENT_BUY);
                } else if (TextUtils.equals("activityMember", action)) {
                    MemberCenterActivity.startActivity(CommonWebActivity.this, MemberCenterActivity.TYPE_CLICK_EVENT_ACTIVE);
                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


    }

    private void onColletionIconClick(int browserNum, int collectStatus) {
        if (mIsRefreshCollectionStatus) {
            OnTipColletionEvent event = new OnTipColletionEvent();
            event.setBrowserNum(browserNum);
            event.setCollectStatus(collectStatus);
            EventBus.getDefault().post(event);
        }
    }

    private void onLoading(final String fun) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        Logger.d("水帖获取用户信息：大于21版本");

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

    private boolean flag_get_deviceid;


    private void onGetTipDetail(JsInteractionParamsEntity.ParamsBean params) {
        mTipPhoto = params.getCover();
        mTipTitle = params.getTipTitle();
        mTipDesc = params.getTipDes();
        mTipId = params.getTipId();
        mDropName = params.getDropName();

        WaterDropApp.tipId = mTipId;
        com.orhanobut.logger.Logger.d("水贴详情：" + "水贴标题：" + mTipTitle);
        com.orhanobut.logger.Logger.d("水贴详情：" + "水贴id：" + mTipId);
        com.orhanobut.logger.Logger.d("水贴详情：" + "水塘名字：" + mDropName);
    }

    private void onFromPoolClick(long dropId) {
        PoolDetailPageActivity.start(this, dropId);
    }

    private void onCreatorClick(long uid) {
        UserProfileActivity.start(this, uid);
    }

    private void onGoodsDetailClick(String goodId) {
        getGoodsData(goodId, PAGE_TAPY_GOODS_DETAIL);
    }

    private void onGoodsBuyClick(String goodId) {
        getGoodsData(goodId, PAGE_TAPY_GOODS_BUY);
    }


    /**
     * 获取商品详情
     */
    private void getGoodsData(String goodsId, final int pageType) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.good_id, goodsId);
        Observable<BaseResponse<GoodsDetailEntity>> observable = HttpUtil.getInstance().sApi.getGoodsDetail(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<GoodsDetailEntity>(this) {
            @Override
            protected void _onNext(GoodsDetailEntity o) {
                if (o != null) {
                    if (pageType == PAGE_TAPY_GOODS_BUY) {
                        goOrder(o);
                    } else {
                        goGoodsDetail(o);

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

    private void goOrder(GoodsDetailEntity entity) {
        OrderConfirmationActivity.start(this, entity, mDropId, mTipId, Constants.ORDER_TYPE_DEFAULT);
    }

    private void goGoodsDetail(GoodsDetailEntity entity) {
        GoodsDetailsActivity.start(this, entity, mTipId, mDropId, mTipTitle);
    }

    @Override
    protected void onRightImgClick() {
        super.onRightImgClick();
        onMoreIconClick();
    }


    private void shareFriend() {
        if (mTipId > 0 && !TextUtils.isEmpty(mTipTitle)) {
            ContactsSelectActivity.startForTip(this, mTipId, mTipTitle, mTipPhoto, mTipDesc, mDropName, mTipUrl);
        }
    }


    private void onMoreIconClick() {
        if (mBottomDialog == null) {
            View inflate = View.inflate(this, R.layout.dialog_share_layout, null);
            mBottomDialog = new BottomSheetDialog(this);
            mBottomDialog.setContentView(inflate);
            mBottomDialog.setCancelable(true);
            mBottomDialog.setCanceledOnTouchOutside(false);
            inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomDialog.dismiss();
                }
            });

            inflate.findViewById(R.id.tv_friend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareFriend();
                    mBottomDialog.dismiss();
                }
            });

            inflate.findViewById(R.id.tv_wechat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(mUrl) && !TextUtils.isEmpty(mTipTitle) && !TextUtils.isEmpty(mTipPhoto)) {
                        LinkedMeModel linedModel = new LinkedMeModel();
                        linedModel.setTipId(mTipId);
                        linedModel.setTipUrl(mTipUrl);
                        ShareUtils.generateUrl(CommonWebActivity.this, 1, "TIP", true, mUrl, mTipTitle, mTipPhoto, Constants.POOL_DESC, linedModel);

                    }
              /*          ShareUtils.share2WxWebPage(CommonWebActivity.this, true
                                , mUrl
                                , mTipTitle
                                , mTipPhoto
                                , Constants.POST_DESC);*/


                    mBottomDialog.dismiss();
                }
            });
            inflate.findViewById(R.id.tv_wechat_moment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(mUrl) && !TextUtils.isEmpty(mTipTitle) && !TextUtils.isEmpty(mTipPhoto)) {
                        LinkedMeModel linedModel = new LinkedMeModel();
                        linedModel.setTipId(mTipId);
                        linedModel.setTipUrl(mTipUrl);
                        ShareUtils.generateUrl(CommonWebActivity.this, 1, "TIP", false, mUrl, mTipTitle, mTipPhoto, Constants.POOL_DESC, linedModel);
                    }
                    /*ShareUtils.share2WxWebPage(CommonWebActivity.this, false
                            , mUrl
                            , mTipTitle
                            , mTipPhoto
                            , Constants.POST_DESC);*/


                    mBottomDialog.dismiss();
                }
            });
            inflate.findViewById(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInstanlled = SinaUtil.isInstalled(CommonWebActivity.this, "com.sina.weibo");
                    if (isInstanlled) {
                   /*     WBEntryActivity.start(CommonWebActivity.this
                                , mUrl
                                , mTipTitle
                                , mTipPhoto
                                , Constants.POST_DESC);*/
                        LinkedMeModel linedModel = new LinkedMeModel();
                        linedModel.setTipId(mTipId);
                        linedModel.setTipUrl(mTipUrl);
                        ShareUtils.generateUrl(CommonWebActivity.this, 2, "TIP", false, mUrl, mTipTitle, mTipPhoto, Constants.POOL_DESC,linedModel);

                    } else {
                        ToastUtil.showShort("请先安装新浪微博客户端");
                    }
                    mBottomDialog.dismiss();
                }
            });
        }

        mBottomDialog.show();
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
        EventBus.getDefault().unregister(this);
    }

    private void getStoreUrl() {
        Observable<BaseResponse<AppInfoEntity>> observable = HttpUtil.getInstance().sApi.getAppInfo();
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AppInfoEntity>(this) {
            @Override
            protected void _onNext(AppInfoEntity entity) {
                List<AppInfoEntity.ResultsBean> results = entity.getResults();
                for (AppInfoEntity.ResultsBean resultsBean : results) {
                    if (resultsBean.getParamKey().equals(Constants.SHOP_URL)) {
                        mUrl = resultsBean.getParamValue();
                        mH5Type = Constants.H5_TYPE_STORE;
                        initView();
                    }
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

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
        PickImageHelper.pickImage(this, StoreSelfFragment.PICK_AVATAR_REQUEST, option);
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == StoreSelfFragment.PICK_AVATAR_REQUEST) {
            H5CameraClickEvent event = new H5CameraClickEvent();
            if (data == null) {
//                Log.d(TAG, "onSystemMessageEvent: null。。");
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
}
