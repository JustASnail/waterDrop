package com.drops.waterdrop.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.util.sys.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.common.activity.UI;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class BaseActivity<V, T extends BasePresenter<V>> extends UI {

    public static final int BASIC_PERMISSION_REQUEST_CODE = 100;


    protected T mPresenter;

    //以下是所有Activity中可能会出现的控件
  /*
    @Nullable
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;*/
    @Nullable
    @Bind(R.id.app_bar_layout)
    protected AppBarLayout mAppBar;
    @Nullable
    @Bind(R.id.iv_left)
    protected ImageView mIvBack;
    @Nullable
    @Bind(R.id.tv_commn_title)
    protected TextView mTvTitle;
    @Nullable
    @Bind(R.id.iv_right)
    protected ImageView mIvRight;
    @Nullable
    @Bind(R.id.tv_right)
    protected TextView mTvRight;
/*    @Bind(R.id.flToolbar)
    public FrameLayout mToolbar;
    @Bind(R.id.ivToolbarNavigation)
    public ImageView mToolbarNavigation;
    @Bind(R.id.vToolbarDivision)
    public View mToolbarDivision;
    @Bind(R.id.llToolbarTitle)
    public AutoLinearLayout mLlToolbarTitle;
    @Bind(R.id.tvToolbarTitle)
    public TextView mToolbarTitle;
    @Bind(R.id.tvToolbarSubTitle)
    public TextView mToolbarSubTitle*/;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WaterDropApp.activities.add(this);

        //判断是否使用MVP模式
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);//因为之后所有的子类都要实现对应的View接口
        }

        init(savedInstanceState);

        //子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
        setContentView(provideContentViewId());
        ButterKnife.bind(this);

//        setupAppBarAndToolbar();

        //设置状态栏颜色
        StatusBarUtil.setColor(this, UIUtils.getColor(R.color.colorPrimaryDark), 0);
        initView();
        initData();
        initListener();
    }

    protected void initMyToolbar(boolean isNeedNavigate, String titleString, int imgRightId, String rightString) {

        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = isNeedNavigate;
        options.titleString = titleString;
        options.imgRightId = imgRightId;
        options.rightString = rightString;

        setMyToolbar(options);
    }

    public void setMyToolbar(MyToolBarOptions options){
        //如果该应用运行在android 5.0以上设备，设置标题栏的z轴高度
        if (mAppBar != null && Build.VERSION.SDK_INT > 21) {
            mAppBar.setElevation(0.0f);
        }

        if (options.titleId != 0) {
            mTvTitle.setText(options.titleId);
        }

        if (!TextUtils.isEmpty(options.titleString)) {
            mTvTitle.setText(options.titleString);
        }

        if (options.rightStringId != 0) {
            mTvRight.setVisibility(View.VISIBLE);
            mTvRight.setText(options.rightStringId);
            mTvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightTextClick();
                }
            });
        }

        if (!TextUtils.isEmpty(options.rightString)) {
            mTvRight.setVisibility(View.VISIBLE);
            mTvRight.setText(options.rightString);
            mTvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightTextClick();
                }
            });
        }

        if (options.imgRightId != 0) {
            mIvRight.setVisibility(View.VISIBLE);
            mIvRight.setImageResource(options.imgRightId);
            mIvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightImgClick();
                }
            });
        }

        if (options.isNeedNavigate) {
            mIvBack.setVisibility(View.VISIBLE);
            mIvBack.setOnClickListener(options.onNavigateClickListener != null ? options.onNavigateClickListener : new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            mIvBack.setVisibility(View.GONE);
        }

    }

    protected  void onRightImgClick(){
    }


    protected void onRightTextClick() {
    }

    /**
     * 设置AppBar和Toolbar
     */
/*
    private void setupAppBarAndToolbar() {
        //如果该应用运行在android 5.0以上设备，设置标题栏的z轴高度
        if (mAppBar != null && Build.VERSION.SDK_INT > 21) {
            mAppBar.setElevation(10.6f);
        }

        //如果界面中有使用toolbar，则使用toolbar替代actionbar
        //默认不是使用NoActionBar主题，所以如果需要使用Toolbar，需要自定义NoActionBar主题后，在AndroidManifest.xml中对指定Activity设置theme
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if (isToolbarCanBack()) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
            }
        }

      */
/*  mToolbarNavigation.setVisibility(isToolbarCanBack() ? View.VISIBLE : View.GONE);
        mToolbarDivision.setVisibility(isToolbarCanBack() ? View.VISIBLE : View.GONE);
        mToolbarNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mLlToolbarTitle.setPadding(isToolbarCanBack() ? 0 : 40, 0, 0, 0);*//*

    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        WaterDropApp.activities.remove(this);
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        for (BroadcastReceiver receiver : receiverList){
            unregisterReceiver(receiver);
        }

        ButterKnife.unbind(this);
    }

    //在setContentView()调用之前调用，可以设置WindowFeature(如：this.requestWindowFeature(Window.FEATURE_NO_TITLE);)
    protected void init(Bundle savedInstanceState) {
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    /**
     * 是否让Toolbar有返回按钮(默认可以，一般一个应用中除了主界面，其他界面都是可以有返回按钮的)
     */
    protected boolean isToolbarCanBack() {
        return true;
    }




    public void jumpToActivity(Intent intent) {
        startActivity(intent);
    }

    public void jumpToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }



    public void jumpToActivityAndClearTask(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void jumpToActivityAndClearTop(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private List<BroadcastReceiver> receiverList = new ArrayList<>();

    public void registerReceiver(BroadcastReceiver receiver, String action){
        receiverList.add(receiver);
        registerReceiver(receiver, new IntentFilter(action));
    }

    public void registerFinishReceiver(String name){
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        }, name);
    }

}
