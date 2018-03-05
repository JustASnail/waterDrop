package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.presenter.UserInfoEditItemPresenter;
import com.drops.waterdrop.ui.mine.view.IUserInfoEditItemView;
import com.netease.nim.uikit.common.util.FastClickUtil;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/6/14.
 */

public class UserInfoEditItemActivity extends BaseActivity<IUserInfoEditItemView, UserInfoEditItemPresenter> implements IUserInfoEditItemView, View.OnClickListener {

    public static final String EXTRA_KEY = "EXTRA_KEY";
    public static final String EXTRA_DATA = "EXTRA_DATA";


    public static final int KEY_NICKNAME = 1;
    public static final int KEY_GENDER = 2;
    public static final int KEY_SIGNATURE = 3;
    public static final int KEY_LOCATION = 4;
    public static final int KEY_PHONE = 5;
    public static final int KEY_EMAIL = 6;
    public static final int KEY_ALIAS = 7;

    public static final int GENDER_MAN = 1;
    public static final int GENDER_WOMAN = 2;

    private int mKey;
    private String mData;

    private int mGender = GENDER_MAN;

    private View mView;
    EditText mEtName;
    EditText mEtSignature;
    TextView mTvLength;
    RelativeLayout mRlMan;
    RelativeLayout mRlWoman;
    ImageView mIvMan;
    ImageView mIvWoman;

    @Bind(R.id.ll_container)
    LinearLayout mLlContainer;
    private ImageView mIvClear;


    public static void start(Context context, int key, String data) {
        Intent starter = new Intent(context, UserInfoEditItemActivity.class);
        starter.putExtra(EXTRA_KEY, key);
        starter.putExtra(EXTRA_DATA, data);
        context.startActivity(starter);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mPresenter.parseIntent(getIntent());

    }

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.rightString = "保存";

        switch (mKey) {
            case KEY_NICKNAME:
                options.titleString = "昵称修改";

                mView = LayoutInflater.from(this).inflate(R.layout.layout_edit_name, null, false);
                mEtName = (EditText) mView.findViewById(R.id.et_name);
                mIvClear = (ImageView) mView.findViewById(R.id.iv_clear);
                if (!TextUtils.isEmpty(mData)) {
                    mEtName.setText(mData);
                    mEtName.setSelection(mEtName.length());
                }

                break;
            case KEY_GENDER:
                options.titleString = "性别修改";

                mView = LayoutInflater.from(this).inflate(R.layout.layout_edit_gender, null, false);
                mRlMan = (RelativeLayout) mView.findViewById(R.id.rl_man);
                mRlWoman = (RelativeLayout) mView.findViewById(R.id.rl_woman);
                mIvMan = (ImageView) mView.findViewById(R.id.man_check_icon);
                mIvWoman = (ImageView) mView.findViewById(R.id.woman_check_icon);
                if (!TextUtils.isEmpty(mData)) {
                    if (TextUtils.equals("男", mData)) {
                        setManShow();
                        mGender = GENDER_MAN;
                    } else {
                        setWomanShow();
                        mGender = GENDER_WOMAN;
                    }
                }
                break;
            case KEY_SIGNATURE:
                options.titleString = "签名修改";
                mView = LayoutInflater.from(this).inflate(R.layout.layout_edit_signature, null, false);
                mEtSignature = (EditText) mView.findViewById(R.id.edt_signature);
                mEtSignature.addTextChangedListener(mSignatureWatcher);
                mTvLength = (TextView) mView.findViewById(R.id.tv_text_length);
                if (!TextUtils.isEmpty(mData)) {
                    mEtSignature.setText(mData);
                    mEtSignature.setSelection(mEtSignature.length());
                    mTvLength.setText(mEtSignature.getText().toString().length() + "/30");
                }
                break;
            case KEY_LOCATION:

                break;
        }
        setMyToolbar(options);
        mLlContainer.addView(mView);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        if (mRlWoman != null && mRlMan != null) {
            mRlMan.setOnClickListener(this);
            mRlWoman.setOnClickListener(this);
        }
        if (mIvClear != null) {
            mIvClear.setOnClickListener(this);
        }
    }

    @Override
    protected UserInfoEditItemPresenter createPresenter() {
        return new UserInfoEditItemPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_info_edit_item;
    }


    @Override
    public void onParseIntentSucceed(int key, String data) {
        mKey = key;
        mData = data;
    }

    @Override
    protected void onRightTextClick() {
        super.onRightTextClick();

        switch (mKey) {
            case KEY_NICKNAME:
                mPresenter.updateNickName(mEtName.getText().toString().trim());
                break;
            case KEY_GENDER:
                mPresenter.updateGender(mGender);
                break;
            case KEY_SIGNATURE:
                mPresenter.updateSignature(mEtSignature.getText().toString());
                break;
        }

    }

    private TextWatcher mSignatureWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mTvLength.setText(s.length() + "/40");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        if (!FastClickUtil.isFastDoubleClick()) {
            switch (v.getId()) {
                case R.id.rl_man:
                    setManShow();
                    mGender = GENDER_MAN;
                    break;
                case R.id.rl_woman:
                    setWomanShow();
                    mGender = GENDER_WOMAN;
                    break;
                case R.id.iv_clear:
                    mEtName.setText("");
                    break;
            }
        }

    }

    private void setManShow() {
        mIvMan.setVisibility(View.VISIBLE);
        mIvWoman.setVisibility(View.GONE);
    }

    private void setWomanShow() {
        mIvMan.setVisibility(View.GONE);
        mIvWoman.setVisibility(View.VISIBLE);
    }
}
