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
import android.widget.TextView;
import android.widget.Toast;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.other.presenter.ChangePresenter;
import com.drops.waterdrop.ui.other.view.IChangeView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.CountDownTimerButton;
import com.netease.nim.uikit.cache.MyUserCache;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;

/**
 * Created by hzh on 2017/5/11.
 */

public class ChangeActivity extends BaseActivity<IChangeView, ChangePresenter> implements View.OnClickListener {

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
    @Bind(R.id.tv_phone_num)
    TextView mTvPhone;
    private TextView mTitle;
    private String userMobile="";

    public static void start(Context context) {
        Intent starter = new Intent(context, ChangeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "密码修改";
        options.isNeedNavigate = true;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        userMobile = MyUserCache.getUserMobile();
        mTvPhone.setText(userMobile);
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
    }

    @Override
    protected ChangePresenter createPresenter() {
        return new ChangePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_change;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                if (checkRegisterContentValid()) {
                    mPresenter.reset( userMobile,mEdtPwd, mEdtSms);
                }
                break;
            case R.id.btn_getAuthCode:
                mPresenter.getSmsCode(userMobile,mBtnGetAuthCode);
                break;
        }
    }

    private boolean checkRegisterContentValid() {
        // 密码检查
        String password = mEdtPwd.getText().toString().trim();
        String regEx = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]{6,16})$";
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

