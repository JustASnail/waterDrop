package com.drops.waterdrop.ui.other.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.other.presenter.ForgetPresenter;
import com.drops.waterdrop.ui.other.view.IForgetView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.CountDownTimerButton;
import com.netease.nim.uikit.common.util.FastClickUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/5/11.
 */

public class ForgetActivity extends BaseActivity<IForgetView, ForgetPresenter> implements View.OnClickListener {


    @Bind(R.id.edt_phone)
    EditText mEdtPhone;
    @Bind(R.id.edt_sms)
    EditText mEdtSms;
    @Bind(R.id.btn_getAuthCode)
    CountDownTimerButton mBtnGetAuthCode;
    @Bind(R.id.cib_eye)
    AppCompatCheckBox mCibEye;
    @Bind(R.id.edt_pwd)
    EditText mEdtPwd;
    @Bind(R.id.btn_reset)
    Button mBtnReset;
    @Bind(R.id.iv_delete)
    ImageView mIvDelete;

    public static void start(Context context) {
        Intent starter = new Intent(context, ForgetActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        MyToolBarOptions myToolBarOptions = new MyToolBarOptions();
        myToolBarOptions.titleString = "忘记密码";
        myToolBarOptions.isNeedNavigate = true;
        setMyToolbar(myToolBarOptions);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mBtnReset.setOnClickListener(this);
        mBtnGetAuthCode.setOnClickListener(this);

        mCibEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    mEdtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    mEdtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                String pwd = mEdtPwd.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    mEdtPwd.setSelection(pwd.length());
            }
        });

        mIvDelete.setOnClickListener(this);

    }

    @Override
    protected ForgetPresenter createPresenter() {
        return new ForgetPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_forget;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                if (!FastClickUtil.isFastDoubleClick()) {
                    if (checkRegisterContentValid()) {
                        mPresenter.reset(mEdtPhone, mEdtPwd, mEdtSms);
                    }
                }
                break;
            case R.id.btn_getAuthCode:
                if (!FastClickUtil.isFastDoubleClick()) {

                    mPresenter.getSmsCode(mBtnGetAuthCode, mEdtPhone);
                }
                break;
            case R.id.iv_delete:
                if (!TextUtils.isEmpty(mEdtPhone.getText().toString())) {
                    mEdtPhone.setText("");
                }
                break;
        }
    }


    private boolean checkRegisterContentValid() {
        // 帐号检查
        String account = mEdtPhone.getText().toString().trim();
        if (account.length() < 11) {
            ToastUtil.showShort("手机号码格式不正确");
            return false;
        }

        // 密码检查
        String password = mEdtPwd.getText().toString().trim();
        String regEx = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]{6,20})$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(mEdtPwd.getText());
        if (!matcher.matches()) {
            Toast.makeText(this, R.string.register_password_tip, Toast.LENGTH_SHORT).show();
            return false;
        }

        //验证码检查
        String sms = mEdtSms.getText().toString().trim();
        if (sms.length() < 6) {
            ToastUtil.showShort("验证码格式不正确");
            return false;
        }
        return true;
    }

}

