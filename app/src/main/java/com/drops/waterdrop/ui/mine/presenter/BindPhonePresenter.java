package com.drops.waterdrop.ui.mine.presenter;

import com.drops.waterdrop.im.help.LogoutHelper;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.IBindPhoneView;
import com.drops.waterdrop.ui.other.activity.LoginActivity;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.CountDownTimerButton;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.VerifyPhoneEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/20.
 */

public class BindPhonePresenter extends BasePresenter<IBindPhoneView>{
    public BindPhonePresenter(BaseActivity context) {
        super(context);
    }

    public void getSmsCode(final String phone,final CountDownTimerButton btnGetAuthCode) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.mobile, phone);
        Observable<BaseResponse<VerifyPhoneEntity>> observable = HttpUtil.getInstance().sApi.verifyPhoneNum(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<VerifyPhoneEntity>(mContext) {
            @Override
            protected void _onNext(VerifyPhoneEntity entity) {
                if (entity.getRegisterStatus() == 0) {
                    sendMessage(phone, btnGetAuthCode);
                } else {
                    ToastUtil.showShort("该手机号码已被注册");
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });

    }

    private void sendMessage(String phone, final CountDownTimerButton btnGetAuthCode) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.mobile, phone);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.sendSMSCode(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, "正在发送...") {
            @Override
            protected void _onNext(Object o) {
                ToastUtil.showShort("发送成功");
                btnGetAuthCode.startCountDown();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort("发送失败， 请重试");
            }
        });
    }

    public void bindPhone(final String phone1, String password, String code) {
        String pwdMd5Str = MD5.getStringMD5(password);

        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.mobile, phone1);
        map.put(RequestParams.password, pwdMd5Str);
        map.put(RequestParams.sms_code, code);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.bindPhone(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object o) {
                getView().onBindSuccess(phone1);
                MyUserCache.saveUserMobile(phone1);

            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
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
                onLogout();
            }
        });
    }


    // 注销
    private void onLogout() {
        // 清理缓存&注销监听
        LogoutHelper.logout();
        // 启动登录
        LoginActivity.start(mContext);

        mContext.finish();
    }
}
