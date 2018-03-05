package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.presenter.SettingPresenter;
import com.drops.waterdrop.ui.mine.view.ISettingView;
import com.drops.waterdrop.ui.other.activity.ChangeActivity;
import com.netease.nim.uikit.common.util.FastClickUtil;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/6/9.
 */

public class SettingActivity extends BaseActivity<ISettingView, SettingPresenter> implements View.OnClickListener {
    @Bind(R.id.tv_logout)
    TextView mTvLogout;
    @Bind(R.id.tv_selfinfo)
    TextView mTvSelfInfo;
    @Bind(R.id.tv_change_pwd)
    TextView mTvChangePwd;
    @Bind(R.id.tv_about)
    TextView mTvAbout;
    @Bind(R.id.tv_invite_code)
    TextView mTvInviteCode;

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
       context.startActivity(starter);
    }


    @Override
    protected void initView() {
        initTitle();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleId = R.string.setting;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mTvLogout.setOnClickListener(this);
        mTvChangePwd.setOnClickListener(this);
        mTvSelfInfo.setOnClickListener(this);
        mTvAbout.setOnClickListener(this);
        mTvInviteCode.setOnClickListener(this);
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_setting;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_logout:
                mPresenter.logout();
                break;
            case R.id.tv_selfinfo:
                if (!FastClickUtil.isFastDoubleClick()) {
                    UserInfoActivity.start(SettingActivity.this);
                }
                break;
            case R.id.tv_change_pwd:
                if (!FastClickUtil.isFastDoubleClick()) {
                    ChangeActivity.start(SettingActivity.this);
                }
                break;
            case R.id.tv_about:
                if (!FastClickUtil.isFastDoubleClick()) {
                    startActivity(new Intent(this, AboutAvtivity.class));
                }
                break;
            case R.id.tv_invite_code:
                if (!FastClickUtil.isFastDoubleClick()) {
                    startActivity(new Intent(this, InviteCodeActivity.class));
                }
                break;
        }
    }
}
