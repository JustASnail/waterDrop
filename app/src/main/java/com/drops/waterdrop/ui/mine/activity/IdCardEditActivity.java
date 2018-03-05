package com.drops.waterdrop.ui.mine.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.presenter.IdCardPresenter;
import com.drops.waterdrop.ui.mine.view.IIdCardEditView;
import com.drops.waterdrop.util.KeyboardUtils;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.IdCardEntity;
import com.netease.nim.uikit.session.constant.Extras;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

import static com.drops.waterdrop.ui.mine.activity.GetTicketActivity.PICK_ID_CARD_BACK_REQUEST;
import static com.drops.waterdrop.ui.mine.activity.GetTicketActivity.PICK_ID_CARD_FRONT_REQUEST;

/**
 * Created by dengxiaolei on 2017/9/8.
 */

public class IdCardEditActivity extends BaseActivity<IIdCardEditView, IdCardPresenter> implements IIdCardEditView {
    @Bind(R.id.et_id_card_no)
    EditText mEtIdCardNo;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.iv_id_card_front)
    ImageView mIvIdCardFront;
    @Bind(R.id.tv_pull_front)
    TextView mTvPullFront;
    @Bind(R.id.iv_id_card_back)
    ImageView mIvIdCardBack;
    @Bind(R.id.tv_pull_reverse)
    TextView mTvPullReverse;
    @Bind(R.id.btn_ok)
    Button mBtnOk;

    private int mWhichDialog;

    public static final int REQUEST_CODE = 111;
    private String mUserName;

    public static void startForResult(Context context, String userName, long addressId) {
        Intent starter = new Intent(context, IdCardEditActivity.class);
        starter.putExtra(Constants.EXTRA_CARD_USER_NAME, userName);
        starter.putExtra(Constants.EXTRA_ADDRESS_ID, addressId);
        ((Activity) context).startActivityForResult(starter, REQUEST_CODE);
    }

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "完善信息";
        setMyToolbar(options);

        if (getIntent() != null) {
            mUserName = getIntent().getStringExtra(Constants.EXTRA_CARD_USER_NAME);
            mPresenter.mAddressId = getIntent().getLongExtra(Constants.EXTRA_ADDRESS_ID, -1);
        }

    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(mUserName)) {
            mTvName.setText(mUserName);
            mPresenter.retrieveIdCard(mUserName);
        }
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected IdCardPresenter createPresenter() {
        return new IdCardPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_id_card_edit;
    }


    @OnClick({R.id.tv_pull_front, R.id.tv_pull_reverse, R.id.btn_ok})
    public void onViewClicked(View view) {
        if (!FastClickUtil.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.tv_pull_front:
                    mWhichDialog = 1;
                    KeyboardUtils.hideSoftInput(this);
                    requestCameraPermission();
                    break;
                case R.id.tv_pull_reverse:
                    mWhichDialog = 2;
                    KeyboardUtils.hideSoftInput(this);
                    requestCameraPermission();
                    break;
                case R.id.btn_ok:
                    mPresenter.saveIdCardInfo(mTvName.getText().toString().trim(), mEtIdCardNo.getText().toString().trim());
                    break;
            }

        }
    }

    @Override
    public void updateUIForUserName(IdCardEntity idCardEntity) {
        if (!TextUtils.isEmpty(idCardEntity.getIdcardNo())) {
            mEtIdCardNo.setText(idCardEntity.getIdcardNo());
            mEtIdCardNo.setSelection(idCardEntity.getIdcardNo().length());
        }
        GlideUtil.showImageView(this, R.mipmap.img_qs_108x70, idCardEntity.getIdcardFront(), mIvIdCardFront);
        GlideUtil.showImageView(this, R.mipmap.img_qs_108x70, idCardEntity.getIdcardBack(), mIvIdCardBack);
    }

    @Override
    public void onUpdateSucceed(AddressEntity.ResultsBean idCardEntity) {
        ToastUtil.showShort("保存成功");
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_ENTITY, idCardEntity);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_ID_CARD_FRONT_REQUEST) {
                mPresenter.mFrontImgFile = data.getStringExtra(Extras.EXTRA_FILE_PATH);
                mPresenter.mFrontImgUrl = "";
//                Bitmap bitmap = BitmapFactory.decodeFile(mPresenter.mFrontImgFile);
//                mIvIdCardFront.setImageBitmap(bitmap);
                GlideUtil.showImageView(this, R.mipmap.img_qs_108x70, mPresenter.mFrontImgFile , mIvIdCardFront);

            }
            if (requestCode == PICK_ID_CARD_BACK_REQUEST) {
                mPresenter.mReverseImgFile = data.getStringExtra(Extras.EXTRA_FILE_PATH);
                mPresenter.mReverseImgUrl = "";

//                Bitmap bitmap = BitmapFactory.decodeFile(mPresenter.mReverseImgFile);
//                mIvIdCardBack.setImageBitmap(bitmap);
                GlideUtil.showImageView(this, R.mipmap.img_qs_108x70, mPresenter.mReverseImgFile , mIvIdCardBack);

            }
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
                            ToastUtil.showShort("未全部授权，无法开启相机权限上传, 请开启相关权限！");
                        } else {
                            if (mWhichDialog == 1) {
                                mPresenter.showFrontDialog();

                            } else {
                                mPresenter.showBackDialog();

                            }

                        }
                    }
                });
    }

}
