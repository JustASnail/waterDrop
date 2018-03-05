package com.drops.waterdrop.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.drops.waterdrop.R;
import com.drops.waterdrop.help.LoginHelper;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.presenter.BindPhonePresenter;
import com.drops.waterdrop.ui.mine.view.IBindPhoneView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.CountDownTimerButton;
import com.netease.nim.uikit.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/7/20.
 */

public class BindPhoneActivity extends BaseActivity<IBindPhoneView, BindPhonePresenter> implements IBindPhoneView, View.OnClickListener {

    @Bind(R.id.et_phone_num)
    EditText etPhoneNum;
    @Bind(R.id.edt_sms)
    EditText edtSms;
    @Bind(R.id.cib_eye)
    AppCompatCheckBox cibEye;
    @Bind(R.id.edt_pwd)
    EditText edtPwd;
    @Bind(R.id.btn_reset)
    Button btnReset;
    @Bind(R.id.btn_getAuthCode)
    CountDownTimerButton timerButton;
    private int mPageMode;
    private String mAccount;
    private String mToken;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!TextUtils.isEmpty(mAccount) && !TextUtils.isEmpty(mToken)) {
            outState.putInt(Constants.EXTRA_PAGE_MODE, mPageMode);
            outState.putString(Constants.EXTRA_ACCOUNT, mAccount);
            outState.putString(Constants.EXTRA_TOKEN, mToken);
        }

    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        if (savedInstanceState != null) {
            mPageMode = savedInstanceState.getInt(Constants.EXTRA_PAGE_MODE, 0);
            mAccount = savedInstanceState.getString(Constants.EXTRA_ACCOUNT);
            mToken = savedInstanceState.getString(Constants.EXTRA_TOKEN);
        } else {
            mPageMode = getIntent().getIntExtra(Constants.EXTRA_PAGE_MODE, 0);
            mAccount = getIntent().getStringExtra(Constants.EXTRA_ACCOUNT);
            mToken = getIntent().getStringExtra(Constants.EXTRA_TOKEN);
        }
    }

    @Override
    protected void initView() {

        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "手机号绑定";
        if (mPageMode == Constants.EXTRA_PAGE_FOR_WECHAT) {
            options.rightString = "切换账号";
        } else {
            options.isNeedNavigate = true;
        }
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btnReset.setOnClickListener(this);
        timerButton.setOnClickListener(this);
        cibEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    edtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                String pwd = edtPwd.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    edtPwd.setSelection(pwd.length());
            }
        });
    }

    @Override
    protected BindPhonePresenter createPresenter() {
        return new BindPhonePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getAuthCode:
                String phone = etPhoneNum.getText().toString();
                if (checkPhone(phone)) {
                    mPresenter.getSmsCode(phone, timerButton);
                }
                break;
            case R.id.btn_reset:
                String password = edtPwd.getText().toString().trim();
                String phone1 = etPhoneNum.getText().toString();
                String code = edtSms.getText().toString().trim();
                if (checkRegisterContentValid(phone1, password, code)) {
                    mPresenter.bindPhone(phone1, password, code);
                }
                break;

        }
    }

    @Override
    protected void onRightTextClick() {
        super.onRightTextClick();
        mPresenter.logout();

    }

    public static boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort("您还未填写手机号");
            return false;
        } else if (phone.startsWith("1") && (phone.length() == 11)) {
            return true;
        } else {
            ToastUtil.showShort("手机号码格式不正确");
            return false;
        }
    }

    private boolean checkRegisterContentValid(String phone, String password, String code) {
        // 密码检查
        String regEx = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]{6,16})$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            Toast.makeText(this, R.string.register_password_tip, Toast.LENGTH_SHORT).show();
            return false;
        }

        checkPhone(phone);

        //验证码检查
        if (code.length() < 6) {
            ToastUtil.showShort("验证码格式不正确");
            return false;
        }
        return true;
    }

    @Override
    public void onBindSuccess(String phone1) {
        ToastUtil.showShort("绑定成功");
        if (mPageMode == Constants.EXTRA_PAGE_FOR_WECHAT) {
            LoginHelper.Instance.loginIM(BindPhoneActivity.this, mAccount, mToken);
        } else {
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_ENTITY, phone1);
            setResult(RESULT_OK, intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (mPageMode == Constants.EXTRA_PAGE_FOR_WECHAT) {
            moveTaskToBack(true);

        } else {
            super.onBackPressed();
        }
    }

}
