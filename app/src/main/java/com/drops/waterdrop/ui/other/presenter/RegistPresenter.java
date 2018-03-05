package com.drops.waterdrop.ui.other.presenter;

import android.Manifest;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.EditText;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.AppCache;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.app.preference.UserPreferences;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.other.activity.GuideActivity;
import com.drops.waterdrop.ui.other.activity.MainActivity;
import com.drops.waterdrop.ui.other.activity.QRCodeActivity;
import com.drops.waterdrop.ui.other.view.IRegistView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.util.sys.UIUtils;
import com.drops.waterdrop.widget.CountDownTimerButton;
import com.netease.nim.uikit.cache.DataCacheManager;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.RegisterEntity;
import com.netease.nim.uikit.model.UserInfoEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by dengxiaolei on 2017/5/11.
 */

public class RegistPresenter extends BasePresenter<IRegistView> {

    private AbortableFuture<LoginInfo> mLoginRequest;


    public RegistPresenter(BaseActivity context) {
        super(context);
    }

    public void getSmsCode(final CountDownTimerButton btnGetAuthCode, EditText edtPhone) {
        String phone = edtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort("手机号码不能为空");
            return;
        } else if (phone.length() != 11) {
            ToastUtil.showShort("手机格式不正确");
            return;
        }
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

    public void regist(EditText phone, EditText pwd, EditText code) {
        final String moblieStr = phone.getText().toString().trim();
        final String pwdMd5Str = MD5.getStringMD5(pwd.getText().toString().trim());

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.mobile, phone);
        map.put(RequestParams.password, pwdMd5Str);
        map.put(RequestParams.sms_code, code.getText().toString().trim());
        Observable<BaseResponse<RegisterEntity>> observable = HttpUtil.getInstance().sApi.register(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<RegisterEntity>(mContext, "注册中...") {
            @Override
            protected void _onNext(RegisterEntity registerEntity) {
                ToastUtil.showShort("注册成功");
                login(moblieStr, pwdMd5Str);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort("注册失败， 请重试。");
            }
        });

    }

    public void login(String moblieStr, String pwdMd5Str) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.mobile, moblieStr);
        map.put(RequestParams.password, pwdMd5Str);
        Observable<BaseResponse<UserInfoEntity>> observable = HttpUtil.getInstance().sApi.login(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<UserInfoEntity>(mContext, "正在登录...") {
            @Override
            protected void _onNext(UserInfoEntity userInfoEntity) {
                loginIM(userInfoEntity.getNeteaseAccid(), userInfoEntity.getNeteaseToken());

            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort("登陆失败， 请重新登录。");
                mContext.finish();
            }
        });
    }


    private void onLoginIMSuccess(LoginInfo loginInfo, String token) {
        mLoginRequest = null;

//        NimUIKit.setAccount(loginInfo.getAccount());
        DataCacheManager.buildDataCacheAsync();

        AppCache.setAccount(loginInfo.getAccount());

        MyUserCache.saveIMAccount(loginInfo.getAccount());
        MyUserCache.saveIMToken(token);


        // 初始化消息提醒配置
        initNotificationConfig();

        DialogMaker.dismissProgressDialog();

        WaterDropApp.app.startContactSyncService();

        startActivity();
    }

    /**
     * 初始化通知栏消息提醒
     */
    private void initNotificationConfig() {
        // 初始化消息提醒
        UserPreferences.setNotificationToggle(true);

        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = AppCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }

    /**
     * 判断是否是第一次进入 如果是第一次进入就进入引导页
     */
    private void startActivity() {
        Boolean isEnter = MyUserCache.getFirstEnter();
        if (!isEnter) {
            mContext.jumpToActivityAndClearTask(GuideActivity.class);
            MyUserCache.saveFirstEnter(true);
        } else {
            mContext.jumpToActivityAndClearTask(MainActivity.class);
        }
    }

    public void loginIM(String accid, final String token) {
        DialogMaker.showProgressDialog(mContext, null, UIUtils.getString(R.string.logining), true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                stopLogin();
                DialogMaker.dismissProgressDialog();
            }
        }).setCanceledOnTouchOutside(false);


        mLoginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(accid, token));
        mLoginRequest
                .setCallback(new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        LogUtil.d("注册", "登陆成功");

                        onLoginIMSuccess(loginInfo, token);
                    }

                    @Override
                    public void onFailed(int code) {
                        LogUtil.e("注册", "登陆失败:" + code);

                        if (code == 302 || code == 404) {
                            onLoginError("账号或密码错误");
                        } else {
                            onLoginError("登陆失败");
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        LogUtil.e("注册", "登陆失败:" + "登陆异常：" + exception);
                        onLoginError("无效输入");
                    }
                });

    }

    private void onLoginError(String error) {
        mLoginRequest = null;
        DialogMaker.dismissProgressDialog();
        UIUtils.showToast("登陆失败");
    }


    public void stopLogin() {
        if (mLoginRequest != null) {
            mLoginRequest.abort();
            mLoginRequest = null;
        }
    }

    public void checkScanPermissions() {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        rxPermissions.setLogging(true);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            ToastUtil.showShort("未全部授权，无法开启扫面功能。 请开启相关权限！");
                        } else {
                            jumpQRCodeActivity();
                        }
                    }
                });
    }


    public void jumpQRCodeActivity() {
        QRCodeActivity.startForResult(mContext);
    }


}
