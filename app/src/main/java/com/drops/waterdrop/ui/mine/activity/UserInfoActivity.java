package com.drops.waterdrop.ui.mine.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.presenter.UserInfoEditItemPresenter;
import com.drops.waterdrop.ui.mine.presenter.UserInfoPresenter;
import com.drops.waterdrop.ui.mine.view.IUserInfoView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.media.picker.PickImageHelper;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nimlib.sdk.AbortableFuture;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.w3c.dom.Text;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * Created by dengxiaolei on 2017/6/2.
 */

public class UserInfoActivity extends BaseActivity<IUserInfoView, UserInfoPresenter> implements IUserInfoView, View.OnClickListener {
    @Bind(R.id.iv_head)
    HeadImageView mIvHead;
    @Bind(R.id.rl_head_img)
    RelativeLayout mRlHeadImg;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.rl_name)
    RelativeLayout mRlName;
    @Bind(R.id.tv_gender)
    TextView mTvGender;
    @Bind(R.id.rl_gender)
    RelativeLayout mRlGender;
    @Bind(R.id.tv_sign)
    TextView mTvSign;
    @Bind(R.id.rl_sign)
    RelativeLayout mRlSign;
    @Bind(R.id.tv_identical_real)
    TextView mTvIdentical;
    @Bind(R.id.rl_identical)
    RelativeLayout mRlIdentify;
    @Bind(R.id.rl_phone)
    RelativeLayout mRlPhone;
    @Bind(R.id.tv_phone)
    TextView mTvPhone;
    @Bind(R.id.tv_phone1)
    TextView mTvPhone1;
//    @Bind(R.id.tv_country)
//    TextView mTvCountry;
//    @Bind(R.id.rl_country)
//    RelativeLayout mRlCountry;

    private static final int PICK_AVATAR_REQUEST = 0x0E;
    private static final int AVATAR_TIME_OUT = 30000;

    // data
    AbortableFuture<String> uploadAvatarFuture;

    public static void start(Context context) {
        Intent starter = new Intent(context, UserInfoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleId = R.string.user_info;
        setMyToolbar(options);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserInfo();
    }

    @Override
    protected void initData() {
//        mPresenter.getUserInfo();
    }

    @Override
    protected void initListener() {
        mRlHeadImg.setOnClickListener(this);
        mRlName.setOnClickListener(this);
        mRlGender.setOnClickListener(this);
        mRlSign.setOnClickListener(this);
        String userMobile = MyUserCache.getUserMobile();
        if (TextUtils.isEmpty(userMobile)) {
            mTvPhone.setVisibility(View.VISIBLE);
            mRlPhone.setOnClickListener(this);
        } else {
            mRlPhone.setEnabled(false);
            mTvPhone1.setText(userMobile);
            mTvPhone1.setVisibility(View.VISIBLE);
            mTvPhone.setVisibility(View.GONE);
        }
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_info;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_head_img:
                requestCameraPermission();
                break;
            case R.id.rl_name:
                UserInfoEditItemActivity.start(this, UserInfoEditItemPresenter.KEY_NICKNAME, mTvName.getText().toString());
                break;
            case R.id.rl_gender:
                UserInfoEditItemActivity.start(this, UserInfoEditItemPresenter.KEY_GENDER, mTvGender.getText().toString());

                break;
            case R.id.rl_sign:
                UserInfoEditItemActivity.start(this, UserInfoEditItemPresenter.KEY_SIGNATURE, mTvSign.getText().toString());
                break;
            case R.id.rl_phone:
                startActivityForResult(new Intent(this,BindPhoneActivity.class),Constants.REQUEST_CODE_USER_INFO);
                break;
        }
    }

    private void showDialog() {
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.titleResId = R.string.set_head_image;
        option.crop = true;
        option.multiSelect = false;
        option.cropOutputImageWidth = 720;
        option.cropOutputImageHeight = 720;
        PickImageHelper.pickImage(this, PICK_AVATAR_REQUEST, option);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_AVATAR_REQUEST) {
            String path = data.getStringExtra(com.netease.nim.uikit.session.constant.Extras.EXTRA_FILE_PATH);
            mPresenter.updateAvatar(path);
        }

        if (resultCode == Activity.RESULT_OK && requestCode == Constants.REQUEST_CODE_USER_INFO) {
            String phone = data.getStringExtra(Constants.EXTRA_ENTITY);
            mTvPhone.setText(phone);
        }
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
                                ToastUtil.showShort("未全部授权，无法开启相机权限修改头像, 请开启相关权限！");
                            } else {
                                showDialog();

                            }
                        }
                    });

    }

    @Override
    public void updateUI(String photo, String nickName, String sexStr, String desc, String location) {
        GlideUtil.showImageViewToCircle(this, R.drawable.img_qs_50x50, photo, mIvHead);
        mTvName.setText(nickName);
        mTvGender.setText(sexStr);
        mTvSign.setText(desc);
//        mTvCountry.setText(location);

        String identify = MyUserCache.getUserIdentify();
        if (TextUtils.isEmpty(identify)) {
            mTvIdentical.setVisibility(View.GONE);
            mRlIdentify.setVisibility(View.GONE);
        } else {
            mTvIdentical.setText(identify);
        }
    }
}
