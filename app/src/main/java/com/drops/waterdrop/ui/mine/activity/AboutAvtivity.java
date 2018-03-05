package com.drops.waterdrop.ui.mine.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AppInfoEntity;
import com.netease.nim.uikit.model.BaseResponse;

import java.util.List;

import butterknife.Bind;
import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/10.
 */

public class AboutAvtivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.tv_version_name)
    TextView tvVersionName;
    @Bind(R.id.tv_invite_code)
    TextView tvInviteCode;               //官方微博
    @Bind(R.id.tv_about)
    TextView tvAbout;                //官方微博
    @Bind(R.id.iv_debug)
    ImageView ivDebug;


    public static void start(Context context) {
        Intent starter = new Intent(context, AboutAvtivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "关于水滴";
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        tvVersionName.setText("水滴无界v" + getVersionName(this));
        showDebugPic();
    }

    private void showDebugPic() {
        Observable<BaseResponse<AppInfoEntity>> observable = HttpUtil.getInstance().sApi.getAppInfo();
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AppInfoEntity>(this) {
            @Override
            protected void _onNext(AppInfoEntity entity) {
                List<AppInfoEntity.ResultsBean> results = entity.getResults();
                for (AppInfoEntity.ResultsBean resultsBean : results) {
                    String paramKey = resultsBean.getParamKey();
                    if (paramKey.equals(Constants.DEBUG_MODE)) {
                        String url = resultsBean.getParamValue();
                        if (TextUtils.equals(url, "1")) {
                            ivDebug.setVisibility(View.VISIBLE);
                            GlideUtil.showImageView(AboutAvtivity.this, url, ivDebug);
                        } else {
                            ivDebug.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });

    }

    @Override
    protected void initListener() {
        tvInviteCode.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_invite_code:
                OfficialWeiboActivity.start(this);
                break;
            case R.id.tv_about:
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", "DropsAPP");
                cm.setPrimaryClip(mClipData);
                ToastUtil.showShort("公众号已复制到剪贴板");
                break;
        }
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}
