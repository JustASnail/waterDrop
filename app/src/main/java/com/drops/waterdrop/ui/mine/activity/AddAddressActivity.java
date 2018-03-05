package com.drops.waterdrop.ui.mine.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.presenter.AddAddressPresenter;
import com.drops.waterdrop.ui.mine.view.IAddAddressView;
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
import rx.functions.Action1;

import static com.drops.waterdrop.ui.mine.activity.GetTicketActivity.PICK_ID_CARD_BACK_REQUEST;
import static com.drops.waterdrop.ui.mine.activity.GetTicketActivity.PICK_ID_CARD_FRONT_REQUEST;

/**
 * Created by dengxiaolei on 2017/6/2.
 */

public class AddAddressActivity extends BaseActivity<IAddAddressView, AddAddressPresenter> implements View.OnClickListener, IAddAddressView {
    public static final String EXTRA_TYPE = "type";
    public static final int REQUEST_CODE = 1;

    public static final int TYPE_ADD = 1;//新增地址
    public static final int TYPE_EDIT = 2;//编辑地址
    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.tv_area)
    TextView mTvArea;
    @Bind(R.id.et_address)
    EditText mEtAddress;
    @Bind(R.id.btn_ok)
    Button mButton;
    @Bind(R.id.switch_default)
    SwitchCompat mSwitchDefault;
    @Bind(R.id.et_id_card)
    EditText mEtIdCard;
    @Bind(R.id.tv_pull_front)
    TextView mTvPullFront;
    @Bind(R.id.tv_pull_reverse)
    TextView mTvPullReverse;
    @Bind(R.id.iv_id_card_front)
    ImageView mIvIdCardFront;
    @Bind(R.id.iv_id_card_back)
    ImageView mIvIdCardBack;

    private AlertDialog.Builder mBuilder;

    private int mWhichDialog;


    public static void start(Context context, int type, AddressEntity.ResultsBean entity) {
        Intent starter = new Intent(context, AddAddressActivity.class);
        starter.putExtra(EXTRA_TYPE, type);
        starter.putExtra(Constants.EXTRA_ENTITY, entity);
        ((Activity) context).startActivityForResult(starter, REQUEST_CODE);
    }


    @Override
    protected void initView() {

    }

    private void initTitle(int type) {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        if (type == 1) {
            options.titleId = R.string.add_address;
        } else {
            options.titleId = R.string.edit_address;
//            options.rightStringId = R.string.delete;
        }
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        mPresenter.parseIntent(getIntent());

    }

    @Override
    protected void initListener() {
        mSwitchDefault.setOnCheckedChangeListener(mOnCheckedChangeListener);
        mTvArea.setOnClickListener(this);
        mButton.setOnClickListener(this);

        mTvPullFront.setOnClickListener(this);
        mTvPullReverse.setOnClickListener(this);

        mEtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !TextUtils.isEmpty(mEtName.getText().toString().trim())) {
                    mPresenter.retrieveIdCard(mEtName.getText().toString().trim());
                }
            }
        });
    }

    @Override
    protected AddAddressPresenter createPresenter() {
        return new AddAddressPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_address;
    }

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                if (!FastClickUtil.isFastDoubleClick()) {
                    if (mPresenter.mType == 1) {
                        mPresenter.saveAddress(mEtName.getText().toString().trim(), mEtPhone.getText().toString().trim(),
                                mTvArea.getText().toString().trim(), mEtAddress.getText().toString().trim(), mSwitchDefault.isChecked()
                                , mEtIdCard.getText().toString().trim());

                    } else {
                        mPresenter.updateAddress(mEtName.getText().toString().trim(), mEtPhone.getText().toString().trim(),
                                mTvArea.getText().toString().trim(), mEtAddress.getText().toString().trim(), mSwitchDefault.isChecked(),
                                mEtIdCard.getText().toString().trim());
                    }
                }
                break;
            case R.id.tv_area:
                KeyboardUtils.hideSoftInput(this);
                mPresenter.ShowPickerView();
                break;
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
        }
    }

    @Override
    protected void onRightTextClick() {
        super.onRightTextClick();
//        showDialog();
    }


    @Override
    public void onCityPicked(String prov, String city, String district) {
        if (mTvArea != null) {
            mTvArea.setText(prov + city + district);
        }
    }

    @Override
    public void onSaveAddressSucceed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void initEditUI(AddressEntity.ResultsBean address) {
        initTitle(2);

        mEtName.setText(address.getName());
        mEtPhone.setText(address.getMobile());
        mTvArea.setText(address.getProv() + address.getCity() + address.getDistrict());
        if (!TextUtils.isEmpty(address.getDetail())) {
            mEtAddress.setText(address.getDetail());
        }
        mSwitchDefault.setChecked(address.getDefaultFlag() == 1);
        if (!TextUtils.isEmpty(address.getIdcardNo())) {
            mEtIdCard.setText(address.getIdcardNo());
        }
        if (!TextUtils.isEmpty(address.getIdcardFront())) {
            GlideUtil.showImageView(this, R.mipmap.img_qs_108x70, address.getIdcardFront(), mIvIdCardFront);
        }

        if (!TextUtils.isEmpty(address.getIdcardBack())) {
            GlideUtil.showImageView(this, R.mipmap.img_qs_108x70, address.getIdcardBack(), mIvIdCardBack);
        }

    }

    @Override
    public void initAddUI() {
        initTitle(1);

    }

    @Override
    public void updateUIForUserName(IdCardEntity idCardEntity) {
        mEtIdCard.setText(idCardEntity.getIdcardNo());
        GlideUtil.showImageView(this, R.mipmap.img_qs_108x70, idCardEntity.getIdcardFront(), mIvIdCardFront);
        GlideUtil.showImageView(this, R.mipmap.img_qs_108x70, idCardEntity.getIdcardBack(), mIvIdCardBack);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

/*
    public void showDialog() {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(AddAddressActivity.this);
            mBuilder.setTitle("温馨提示");
            mBuilder.setMessage("确定删除该地址吗？");
            mBuilder.setNegativeButton("取消", null);
            mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPresenter.removeAddress();
                }
            });
        }

        mBuilder.show();
    }
*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_ID_CARD_FRONT_REQUEST) {
                mPresenter.mFrontFile = data.getStringExtra(Extras.EXTRA_FILE_PATH);
                mPresenter.mIdcardFront = "";
//                Bitmap bitmap = BitmapFactory.decodeFile(mPresenter.mFrontFile);
//                mIvIdCardFront.setImageBitmap(bitmap);
                GlideUtil.showImageView(this, R.mipmap.img_qs_108x70, mPresenter.mFrontFile, mIvIdCardFront);

            }
            if (requestCode == PICK_ID_CARD_BACK_REQUEST) {
                mPresenter.mBackFile = data.getStringExtra(Extras.EXTRA_FILE_PATH);
                mPresenter.mIdcardBack = "";

//                Bitmap bitmap = BitmapFactory.decodeFile(mPresenter.mBackFile);
//                mIvIdCardBack.setImageBitmap(bitmap);
                GlideUtil.showImageView(this, R.mipmap.img_qs_108x70, mPresenter.mBackFile, mIvIdCardBack);

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
