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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.other.presenter.RegistPresenter;
import com.drops.waterdrop.ui.other.view.IRegistView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.CountDownTimerButton;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/5/11.
 */

public class RegistActivity extends BaseActivity<IRegistView, RegistPresenter> implements View.OnClickListener {

    @Bind(R.id.myToolbar)
    RelativeLayout mMyToolbar;
    @Bind(R.id.rl_head)
    RelativeLayout mRlHead;
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
    @Bind(R.id.edt_qr_code)
    EditText mEdtQrCode;
    @Bind(R.id.iv_scan)
    ImageView mIvScan;
    @Bind(R.id.btn_regist)
    Button mBtnRegist;
    @Bind(R.id.rb_agreement)
    AppCompatCheckBox mRbAgreement;
    @Bind(R.id.tv_agreement)
    TextView mTvAgreement;

    public static final int SCAN_REQUEST_CODE = 100;


    public static void start(Context context) {
        Intent starter = new Intent(context, RegistActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        StatusBarUtil.setTransparentForImageView(this, mMyToolbar);
//        StatusBarUtil.setTranslucentDiff(this);

//        StatusBarUtil.setColor(this, Color.parseColor("#0EC7F0"), 0);
        MyToolBarOptions myToolBarOptions = new MyToolBarOptions();
        myToolBarOptions.titleString = "注册";
        myToolBarOptions.isNeedNavigate = true;
        setMyToolbar(myToolBarOptions);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mBtnRegist.setOnClickListener(this);
        mTvAgreement.setOnClickListener(this);
        mBtnGetAuthCode.setOnClickListener(this);
        mIvScan.setOnClickListener(this);

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

        mRbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mBtnRegist.setEnabled(true);
                } else {
                    mBtnRegist.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected RegistPresenter createPresenter() {
        return new RegistPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_regist;
    }


    @Override
    public void onClick(View v) {
        if (!FastClickUtil.isFastDoubleClick()) {
            switch (v.getId()) {
                case R.id.btn_regist:
                    if (checkRegisterContentValid()) {
                        mPresenter.regist(mEdtPhone, mEdtPwd, mEdtSms);
                    }
                    break;
                case R.id.tv_agreement:
                    UserAgreementActivity.start(RegistActivity.this);
                    break;
                case R.id.btn_getAuthCode:
                    mPresenter.getSmsCode(mBtnGetAuthCode, mEdtPhone);
                    break;
                case R.id.iv_scan:
                    mPresenter.checkScanPermissions();
                    break;
            }

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
        Matcher matcher = pattern.matcher(password);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRCodeActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            String qrCode = data.getStringExtra(Constants.INVITATION_CODE);
            mEdtQrCode.setText("二维码：" + qrCode);
        }
    }
}

