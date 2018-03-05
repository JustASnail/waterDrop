package com.drops.waterdrop.ui.other.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.other.presenter.LoginPresenter;
import com.drops.waterdrop.ui.other.view.ILoginView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.CountDownTimerButton;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.ClientType;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.Bind;
import rx.functions.Action1;

import static com.drops.waterdrop.R.id.tv_agreement;

/**
 * Created by dengxiaolei on 2017/4/24.
 */

public class LoginActivity extends BaseActivity<ILoginView, LoginPresenter> implements ILoginView, View.OnClickListener {

    private static final String KICK_OUT = "KICK_OUT";
    private static final String EXTRA_ACCOUNT = "account_extra";
    @Bind(R.id.iv_bg)
    ImageView mIvBg;
    @Bind(R.id.tv_type_login)
    TextView mTvTypeLogin;
    @Bind(R.id.iv_login_tag)
    ImageView mIvLoginTag;
    @Bind(R.id.tv_type_regist)
    TextView mTvTypeRegist;
    @Bind(R.id.iv_regist_tag)
    ImageView mIvRegistTag;
    @Bind(R.id.email)
    EditText mEmail;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.tv_forget_pwd)
    TextView mTvForgetPwd;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.tv_wx_login)
    TextView mTvWxLogin;
    @Bind(R.id.head_container)
    RelativeLayout mHeadContainer;
    @Bind(R.id.checkbox_show_pwd)
    AppCompatCheckBox mCheckboxShowPwd;
    @Bind(R.id.et_moblie_regist)
    EditText mEtMoblieRegist;
    @Bind(R.id.et_code_sms)
    EditText mEtCodeSms;
    @Bind(R.id.btn_getAuthCode)
    CountDownTimerButton mBtnGetAuthCode;
    @Bind(R.id.et_pwd_regist)
    EditText mEtPwdRegist;
    @Bind(R.id.checkbox_show_pwd_regist)
    AppCompatCheckBox mCheckboxShowPwdRegist;
    @Bind(R.id.tv_rq_code)
    TextView mTvQrCode;
    @Bind(R.id.iv_scan)
    ImageView mIvScan;
    @Bind(R.id.btn_regist)
    Button mBtnRegist;
    @Bind(R.id.checkbox_agreement)
    CheckBox mCheckboxAgreement;
    @Bind(tv_agreement)
    TextView mTvAgreement;
    @Bind(R.id.ll_login_btn)
    LinearLayout mLlLoginBtn;
    @Bind(R.id.ll_regist_btn)
    LinearLayout mLlRegistBtn;
    @Bind(R.id.layout_login)
    View mLayoutLogin;
    @Bind(R.id.layout_regist)
    View mLayoutRegist;

    private boolean canLoginOfEdt;

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.0f; //logo缩放比例

    private int mCurrentPage = 0;//0显示登陆。 1显示注册
    private boolean isFromWeb;


    public static void start(Context context, String phone) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        starter.putExtra(EXTRA_ACCOUNT, phone);
        context.startActivity(starter);
    }


    private void setAvailable() {
        if (canLoginOfEdt) {
            mBtnLogin.setEnabled(true);
        } else {
            mBtnLogin.setEnabled(false);
        }
    }


    public static void start(Context context) {
        start(context, false);
    }

    public static void start(Context context, boolean kickOut) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(KICK_OUT, kickOut);
        context.startActivity(intent);
    }


    @Override
    protected void initView() {
        StatusBarUtil.setTransparentForImageView(this, null);
        if (mCurrentPage == 0) {
            showLoginMode();
        } else {
            showRegistMode();
        }

        if (mEmail.getText().toString().trim().length() >= 11 && mEtPwd.getText().toString().trim().length() >= 6) {
            mBtnLogin.setEnabled(true);
        } else {
            mBtnLogin.setEnabled(false);
        }

        mCheckboxAgreement.setChecked(true);
        mBtnRegist.setEnabled(mCheckboxAgreement.isChecked());
    }


    @Override
    protected void initData() {
        String userAccount = MyUserCache.getUserMobile();
        if (!TextUtils.isEmpty(userAccount)) {
            mEmail.setText(userAccount);
            String pwd = mEmail.getText().toString();
            if (!TextUtils.isEmpty(pwd))
                mEmail.setSelection(pwd.length());
        }

        if (getIntent() != null && getIntent().getStringExtra(EXTRA_ACCOUNT) != null) {
            mEmail.setText(getIntent().getStringExtra(EXTRA_ACCOUNT));
            String pwd = mEmail.getText().toString();
            if (!TextUtils.isEmpty(pwd))
                mEmail.setSelection(pwd.length());
        }

        if (getIntent() != null && getIntent().getIntExtra(Constants.FROM_WEB_KEY, 0) == Constants.FROM_WEB_VALUE) {
            isFromWeb = true;
        }

//        requestBasicPermission(BASIC_PERMISSIONS);
        onParseIntent();
    }

    @Override
    protected void initListener() {
        mEmail.addTextChangedListener(mAccountWatcher);
        mEtPwd.addTextChangedListener(mPwdWatcher);
        mBtnLogin.setOnClickListener(this);

        mTvForgetPwd.setOnClickListener(this);

        mTvWxLogin.setOnClickListener(this);


        mLlLoginBtn.setOnClickListener(this);
        mLlRegistBtn.setOnClickListener(this);

        mCheckboxShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                String pwd = mEtPwd.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    mEtPwd.setSelection(pwd.length());
            }
        });


        mBtnRegist.setOnClickListener(this);
        mBtnGetAuthCode.setOnClickListener(this);
        mIvScan.setOnClickListener(this);
        mTvAgreement.setOnClickListener(this);

        mCheckboxAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBtnRegist.setEnabled(isChecked);
            }
        });


        mCheckboxShowPwdRegist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    mEtPwdRegist.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    mEtPwdRegist.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                String pwd = mEtPwdRegist.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    mEtPwdRegist.setSelection(pwd.length());
            }
        });


    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    private void onParseIntent() {
        if (getIntent().getBooleanExtra(KICK_OUT, false)) {
            int type = NIMClient.getService(AuthService.class).getKickedClientType();
            String client;
            switch (type) {
                case ClientType.Web:
                    client = "网页端";
                    break;
                case ClientType.Windows:
                    client = "电脑端";
                    break;
                case ClientType.REST:
                    client = "服务端";
                    break;
                default:
                    client = "移动端";
                    break;
            }
            EasyAlertDialogHelper.showOneButtonDiolag(LoginActivity.this, getString(R.string.kickout_notify),
                    getString(R.string.kickout_content), getString(R.string.ok), true, null);
        }
    }


    @Override
    public EditText getEtPhone() {
        return mEmail;
    }

    @Override
    public EditText getEtPwd() {
        return mEtPwd;
    }

    @Override
    public void onGetSMSCodeSucceed() {
        mBtnGetAuthCode.startCountDown();
    }


    @Override
    public void onClick(View v) {
        if (!FastClickUtil.isFastDoubleClick()) {
            switch (v.getId()) {
                case R.id.tv_forget_pwd:
                    ForgetActivity.start(this);
                    break;
                case R.id.tv_wx_login:
                    mPresenter.wxLogin();
                    break;

                case R.id.ll_login_btn:
                    switchLoginMode();
                    break;
                case R.id.ll_regist_btn:
                    switchRegistMode();

                    break;
                case R.id.btn_login:
                    mPresenter.login(isFromWeb);

                    break;
                case R.id.btn_regist:
                    String mobile = mEtMoblieRegist.getText().toString().trim();
                    String code = mEtCodeSms.getText().toString().trim();
                    String pwd = mEtPwdRegist.getText().toString().trim();
                    String qrCode = mTvQrCode.getText().toString().trim();

                    mPresenter.regist(mobile, code, pwd, qrCode);

                    break;
                case R.id.btn_getAuthCode:
                    String phoneNum = mEtMoblieRegist.getText().toString().trim();
                    mPresenter.getSmsCode(phoneNum);

                    break;
                case R.id.iv_scan:
                    requestCameraPermission();
                    break;
                case R.id.tv_agreement:
                    UserAgreementActivity.start(LoginActivity.this);
                    break;
            }

        }
    }


    private void switchRegistMode() {
        if (mCurrentPage == 1) {
            return;
        }
        mCurrentPage = 1;
        showRegistMode();
    }


    private void switchLoginMode() {
        if (mCurrentPage == 0) {
            return;
        }

        mCurrentPage = 0;
        showLoginMode();
    }

    private void showLoginMode() {
        mIvLoginTag.setVisibility(View.VISIBLE);
        mTvTypeLogin.setTextColor(Color.parseColor("#ffffff"));
        TextPaint tpLogin = mTvTypeLogin.getPaint();
        tpLogin.setFakeBoldText(true);
        mLayoutLogin.setVisibility(View.VISIBLE);


        mIvRegistTag.setVisibility(View.INVISIBLE);
        mTvTypeRegist.setTextColor(Color.parseColor("#B0B0B0"));
        TextPaint tpRegist = mTvTypeRegist.getPaint();
        tpRegist.setFakeBoldText(false);
        mLayoutRegist.setVisibility(View.GONE);

    }

    private void showRegistMode() {
        mIvRegistTag.setVisibility(View.VISIBLE);
        mTvTypeRegist.setTextColor(Color.parseColor("#ffffff"));
        TextPaint tpRegist = mTvTypeLogin.getPaint();
        tpRegist.setFakeBoldText(true);
        mLayoutRegist.setVisibility(View.VISIBLE);

        mIvLoginTag.setVisibility(View.INVISIBLE);
        mTvTypeLogin.setTextColor(Color.parseColor("#B0B0B0"));
        TextPaint tpLogin = mTvTypeRegist.getPaint();
        tpLogin.setFakeBoldText(false);
        mLayoutLogin.setVisibility(View.GONE);
    }


    private TextWatcher mAccountWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().trim().length() == 11 && mEtPwd.getText().toString().trim().length() >= 6) {
                canLoginOfEdt = true;
            } else {
                canLoginOfEdt = false;
            }
            setAvailable();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher mPwdWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().trim().length() >= 6 && mEmail.getText().toString().trim().length() == 11) {
                canLoginOfEdt = true;
            } else {
                canLoginOfEdt = false;
            }
            setAvailable();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRCodeActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            String qrCode = data.getStringExtra(Constants.INVITATION_CODE);
            mTvQrCode.setText(qrCode);
        }

    }


    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    private void requestCameraPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            ToastUtil.showShort("未全部授权，无法开启扫面功能, 请开启相关权限！");
                        } else {
                            QRCodeActivity.startForResult(LoginActivity.this);
                        }
                    }
                });
    }

}

