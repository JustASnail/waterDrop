package com.drops.waterdrop.ui.session.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.session.presenter.VerifyPresenter;
import com.drops.waterdrop.ui.session.view.IVerifyView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.AddFriendForUid;
import com.netease.nim.uikit.model.DropDetailsEntity;

import java.io.Serializable;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/5/17.
 */

public class VerifyActivity extends BaseActivity<IVerifyView, VerifyPresenter> implements IVerifyView{
    private static final String EXTRA_USER_INFO = "user_info";
    public static final int RESULT_CODE_TEAM = 100;
    public static final String EXTRA_ADD_TEAM_RESULT = "add_team_result";
    public static final int TEAM_SUCCESS = 1;
    public static final int TEAM_REVIEWED = 2;
    public static final int TEAM_ADDED = 3;
    public static final int TEAM_FAILED = 4;
    public static final int REQUSET_CODE_TEAM = 200;
    private static final int REQUEST_CODE = 111;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.img_head)
    HeadImageView mImgHead;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_account)
    TextView mTvAccount;
    @Bind(R.id.rl_user_info)
    RelativeLayout mRlUserInfo;
    @Bind(R.id.edt_verify_content)
    EditText mEdtVerifyContent;
    @Bind(R.id.tv_text_length)
    TextView mTvTextLength;
    @Bind(R.id.cv_sign)
    CardView mCvSign;
    private AddFriendForUid mUser;
    private DropDetailsEntity mPoolEntity;
    private String account;

    public static void start(Context context, AddFriendForUid user) {
        Intent starter = new Intent(context, VerifyActivity.class);
        starter.putExtra(EXTRA_USER_INFO, user);
        ((Activity)context).startActivityForResult(starter, REQUEST_CODE);
    }

    public static void startForResult(Context context, DropDetailsEntity user) {
        Intent starter = new Intent(context, VerifyActivity.class);
        starter.putExtra(EXTRA_USER_INFO, user);
        ((Activity) context).startActivityForResult(starter, REQUSET_CODE_TEAM);
    }

    private TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mTvTextLength.setText(s.length() + "/150");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void initView() {
        if (getIntent() != null) {
            Serializable serializableExtra = getIntent().getSerializableExtra(EXTRA_USER_INFO);
            if (serializableExtra instanceof AddFriendForUid) {
                mUser = (AddFriendForUid) serializableExtra;
            } else if (serializableExtra instanceof DropDetailsEntity) {
                mPoolEntity = (DropDetailsEntity) serializableExtra;
            }
        }

        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleId = R.string.verify;
        options.rightStringId = R.string.send;
        setMyToolbar(options);

        mRlUserInfo.setVisibility(View.VISIBLE);
        if (mUser != null) {
            mTvName.setText(mUser.getNickName());
            mTvAccount.setText(mUser.getUid() + "");
            GlideUtil.showImageViewToCircle(this, R.drawable.icon_default_head_60dp, mUser.getPhoto(), mImgHead);
            account = mUser.getUid() + "";
        } else if (mPoolEntity != null) {
            mTvName.setText(mPoolEntity.getDropName());
            mTvAccount.setText(mPoolEntity.getDropCode() + "");
            GlideUtil.showImageViewToCircle(this, R.drawable.icon_default_head_60dp, mPoolEntity.getDropPhoto(), mImgHead);
        }
        mEdtVerifyContent.setText("我是" + (TextUtils.isEmpty(MyUserCache.getUserNickName()) ? MyUserCache.getUserMobile() : MyUserCache.getUserNickName()) + ", hello~");
        mEdtVerifyContent.setSelection(mEdtVerifyContent.length());


        mTvTextLength.setText(mEdtVerifyContent.getText().toString().length() + "/150");


    }

    @Override
    protected void onRightTextClick() {
        if (mUser != null) {
            mPresenter.doAddFriend(mUser.getUid(), mEdtVerifyContent.getText().toString());
        } else if (mPoolEntity != null) {
            mPresenter.joinPool(mPoolEntity.getDropId(), mEdtVerifyContent.getText().toString());
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mEdtVerifyContent.addTextChangedListener(mWatcher);
    }


    @Override
    protected VerifyPresenter createPresenter() {
        return new VerifyPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_verify;
    }

    @Override
    public void onAddFriendSucceed() {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_FRIEND_UID, mUser.getUid());
        setResult(RESULT_OK, intent);
        finish();
    }
}
