package com.drops.waterdrop.ui.pool.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by dengxiaolei on 2017/6/9.
 */

public class NotStartActiveActivity extends BaseActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, NotStartActiveActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle("敬请期待");

    }




    private void initTitle(String title) {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = title;
        setMyToolbar(options);
        StatusBarUtil.setColor(this, Color.BLACK);
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
        return R.layout.activity_not_start_active;
    }





}
