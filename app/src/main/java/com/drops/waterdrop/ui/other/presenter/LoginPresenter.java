package com.drops.waterdrop.ui.other.presenter;

import android.text.TextUtils;
import android.widget.Toast;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.help.LoginHelper;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.other.view.ILoginView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.util.sys.UIUtils;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.RegisterEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;


/**
 * Created by dengxiaolei on 2017/4/24.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {



    public LoginPresenter(BaseActivity context) {
        super(context);
    }


    public void login(boolean isFromWeb) {
        final String account = getView().getEtPhone().getText().toString().trim();
        String pwd = getView().getEtPwd().getText().toString().trim();
        if (isFromWeb) {
            LoginHelper.Instance.login(account, pwd, mContext, isFromWeb);
        } else {
            LoginHelper.Instance.login(account, pwd, mContext);
        }
    }


    public void wxLogin() {
        if (!WaterDropApp.mWxApi.isWXAppInstalled()) {
            UIUtils.showToast("您还未安装微信客户端");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        WaterDropApp.mWxApi.sendReq(req);
    }


    public void regist(final String mobile, String smsCode, final String pwd, String qrCode) {
        if (!checkRegisterContentValid(mobile, pwd, smsCode)) return;
        final String pwdMd5Str = MD5.getStringMD5(pwd);

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.mobile, mobile);
        map.put(RequestParams.password, pwdMd5Str);
        map.put(RequestParams.sms_code, smsCode);
        map.put(RequestParams.invite_code, qrCode);
        Observable<BaseResponse<RegisterEntity>> observable = HttpUtil.getInstance().sApi.register(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<RegisterEntity>(mContext) {
            @Override
            protected void _onNext(RegisterEntity registerEntity) {
                ToastUtil.showShort("注册成功");
                LoginHelper.Instance.login(mobile, pwd, mContext);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });

    }

    private boolean checkRegisterContentValid(String account, String password, String sms) {
        // 帐号检查
        if (account.length() < 11) {
            ToastUtil.showShort("手机号码格式不正确");
            return false;
        }

        // 密码检查
        String regEx = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]{6,20})$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            Toast.makeText(mContext, R.string.register_password_tip, Toast.LENGTH_SHORT).show();
            return false;
        }

        //验证码检查
        if (sms.length() < 6) {
            ToastUtil.showShort("验证码格式不正确");
            return false;
        }
        return true;
    }


    public void getSmsCode(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showShort("手机号码不能为空");
            return;
        } else if (phoneNum.length() != 11) {
            ToastUtil.showShort("手机格式不正确");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.mobile, phoneNum);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.sendSMSCode(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, "正在发送...") {
            @Override
            protected void _onNext(Object o) {
                ToastUtil.showShort("发送成功");
                getView().onGetSMSCodeSucceed();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort("发送失败， 请重试");
            }
        });
    }
}
