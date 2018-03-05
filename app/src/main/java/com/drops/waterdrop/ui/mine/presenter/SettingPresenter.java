package com.drops.waterdrop.ui.mine.presenter;

import android.content.Intent;

import com.drops.waterdrop.im.help.LogoutHelper;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.ISettingView;
import com.drops.waterdrop.ui.other.activity.LoginActivity;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/6/9.
 */

public class SettingPresenter extends BasePresenter<ISettingView> {
    public SettingPresenter(BaseActivity context) {
        super(context);
    }

    // 注销
    private void onLogout() {
        // 清理缓存&注销监听
        LogoutHelper.logout();
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        mContext.finish();
/*
        LogoutHelper.logout();
        // 启动登录
        LoginActivity.start(mContext);

        mContext.finish();
        WaterDropApp.finishActivityclass(MainActivity.class);*/
    }

    public void logout() {
        Map<String, Object> map = new HashMap<>();
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.logout(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object o) {
                onLogout();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort("退出失败！");
            }
        });
    }
}
