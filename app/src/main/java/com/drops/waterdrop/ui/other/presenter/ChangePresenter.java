package com.drops.waterdrop.ui.other.presenter;

import android.content.Intent;
import android.widget.EditText;

import com.drops.waterdrop.im.help.LogoutHelper;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.other.activity.LoginActivity;
import com.drops.waterdrop.ui.other.view.IChangeView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.CountDownTimerButton;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by hzh on 2017/5/11.
 */

public class ChangePresenter extends BasePresenter<IChangeView> {
    private static final String TAG = "ChangePresenter";
    public ChangePresenter(BaseActivity context) {
        super(context);
    }

    public void getSmsCode(String phone,final CountDownTimerButton btnGetAuthCode) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.mobile, phone);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.sendSMSCode(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, "正在发送...") {
            @Override
            protected void _onNext(Object o) {
                ToastUtil.showShort("发送成功");
                onGetSMSCodeSucceed(btnGetAuthCode);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort("发送失败， 请重试");
            }
        });
    }

    public void onGetSMSCodeSucceed(CountDownTimerButton btnGetAuthCode) {
        btnGetAuthCode.startCountDown();
    }

    public void reset(final String phone, EditText pwd, EditText code) {
        final String pwdMd5Str =  MD5.getStringMD5(pwd.getText().toString().trim());

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.mobile, phone);
        map.put(RequestParams.new_password, pwdMd5Str);
        map.put(RequestParams.sms_code, code.getText().toString().trim());
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.resetUserPwd(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, "注册中...") {
            @Override
            protected void _onNext(Object object) {
                ToastUtil.showShort("修改成功");

                startLogin(phone);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort("重置失败， 请重试。");
            }
        });
    }

    private void startLogin(String phone) {
//        LoginActivity.start(mContext, phone);
        // 清理缓存&注销监听
        LogoutHelper.logout();
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("account_extra", phone);
        mContext.startActivity(intent);
        mContext.finish();
    }
}
