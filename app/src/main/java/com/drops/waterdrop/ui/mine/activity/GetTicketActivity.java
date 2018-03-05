package com.drops.waterdrop.ui.mine.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.presenter.GetTicketPresenter;
import com.drops.waterdrop.ui.mine.view.IGetTicketView;
import com.drops.waterdrop.util.KeyboardUtils;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.MyTicketEntity;
import com.netease.nim.uikit.session.constant.Extras;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * Created by dengxiaolei on 2017/6/4.
 */

public class GetTicketActivity extends BaseActivity<IGetTicketView, GetTicketPresenter> implements IGetTicketView, View.OnClickListener {
    public static final String EXTRA_IS_GET = "is_get";
    public static final String EXTRA_ENTITY = "entity";
    public static final int REQUEST_CODE_GET_TICKET = 109;
    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.et_id_card)
    EditText mEtIdCard;
    @Bind(R.id.id_card_notes)
    TextView mIdCardNotes;
    @Bind(R.id.id_card_front)
    TextView mIdCardFront;
    @Bind(R.id.iv_id_card_front)
    ImageView mIvIdCardFront;
    @Bind(R.id.tv_pull_front)
    TextView mTvPullFront;
    @Bind(R.id.rl_pull_front)
    RelativeLayout mRlPullFront;
    @Bind(R.id.id_card_back)
    TextView mIdCardBack;
    @Bind(R.id.iv_id_card_back)
    ImageView mIvIdCardBack;
    @Bind(R.id.tv_pull_back)
    TextView mTvPullBack;
    @Bind(R.id.rl_pull_back)
    RelativeLayout mRlPullBack;
    @Bind(R.id.tv_area)
    TextView mTvArea;
    @Bind(R.id.et_address)
    EditText mEtAddress;
    @Bind(R.id.btn_ok)
    Button mBtnOk;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_id_card)
    TextView mTvIdCard;
    @Bind(R.id.tv_area2)
    TextView mTvArea2;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tv_phone_num)
    TextView tvPhoneNum;
    private boolean mIsGet;//是否已经领取了
    private MyTicketEntity.ResultsBean mEntity;

    public static final int PICK_ID_CARD_FRONT_REQUEST = 100;
    public static final int PICK_ID_CARD_BACK_REQUEST = 101;
    private int mWhichDialog;
    private String frontImgPath;
    private String backImgPath;


    public static void start(Context context, boolean isGet, MyTicketEntity.ResultsBean entity) {
        Intent starter = new Intent(context, GetTicketActivity.class);
        starter.putExtra(EXTRA_IS_GET, isGet);
        starter.putExtra(EXTRA_ENTITY, entity);
        ((Activity) context).startActivityForResult(starter, REQUEST_CODE_GET_TICKET);
    }

    @Override
    protected void initView() {
        mPresenter.parseIntent(getIntent());
        initTitle();

        if (mIsGet) {
            setIsGetedLayout();
            mBtnOk.setVisibility(View.GONE);
        } else {
            setNotGetedLayout();

        }
    }

    private void setNotGetedLayout() {
        mTvName.setVisibility(View.GONE);
        mTvIdCard.setVisibility(View.GONE);
        mIvIdCardFront.setVisibility(View.GONE);
        mIvIdCardBack.setVisibility(View.GONE);
        mTvArea2.setVisibility(View.GONE);
        mTvAddress.setVisibility(View.GONE);
        tvPhoneNum.setVisibility(View.GONE);

        etPhone.setVisibility(View.VISIBLE);
        mEtName.setVisibility(View.VISIBLE);
        mEtIdCard.setVisibility(View.VISIBLE);
        mTvPullBack.setVisibility(View.VISIBLE);
        mTvPullFront.setVisibility(View.VISIBLE);
        mTvArea.setVisibility(View.VISIBLE);
        mEtAddress.setVisibility(View.VISIBLE);
        mBtnOk.setVisibility(View.VISIBLE);

        mTvArea.setOnClickListener(this);
        mTvPullFront.setOnClickListener(this);
        mTvPullBack.setOnClickListener(this);
        mTvPullFront.setOnClickListener(this);
        mTvPullBack.setOnClickListener(this);
        mBtnOk.setOnClickListener(this);
    }

    private void setIsGetedLayout() {

        mTvName.setVisibility(View.VISIBLE);
        mTvIdCard.setVisibility(View.VISIBLE);
        mIvIdCardFront.setVisibility(View.VISIBLE);
        mIvIdCardBack.setVisibility(View.VISIBLE);
        mTvArea2.setVisibility(View.VISIBLE);
        mTvAddress.setVisibility(View.VISIBLE);
        tvPhoneNum.setVisibility(View.VISIBLE);

        etPhone.setVisibility(View.GONE);
        mEtName.setVisibility(View.GONE);
        mEtIdCard.setVisibility(View.GONE);
        mTvPullBack.setVisibility(View.GONE);
        mTvPullFront.setVisibility(View.GONE);
        mTvArea.setVisibility(View.GONE);
        mEtAddress.setVisibility(View.GONE);

        MyTicketEntity.ResultsBean.AddressBean address = mEntity.getAddress();
        if (address != null) {
            mTvName.setText(address.getName());
            mTvIdCard.setText(address.getId_number());
            GlideUtil.showImageView(this, address.getId_number_photo_back(), mIvIdCardBack);
            GlideUtil.showImageView(this, address.getId_number_photo_front(), mIvIdCardFront);
            mTvArea2.setText(address.getUserArea());
            mTvAddress.setText(address.getUserFullAddress());
            tvPhoneNum.setText(mEntity.getPhone());
        }
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleId = R.string.get_ticket;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected GetTicketPresenter createPresenter() {
        return new GetTicketPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_get_ticket;
    }

    @Override
    public void onIntent(boolean isGet, MyTicketEntity.ResultsBean entity) {
        mIsGet = isGet;
        mEntity = entity;
    }


    @Override
    public void onAddressPicker(String prov, String city, String district) {
        if (mTvArea != null) {
            mTvArea.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            mTvArea.setTextColor(Color.BLACK);
            mTvArea.setText(prov + " " + city + " " + district);
        }
    }

    @Override
    public void onReceiveTicketSuccess() {
        //1表示已领取  0表示未领取
        mEntity.setIsReceived("1");
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_ENTITY, mEntity);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pull_front:
                mWhichDialog = 1;
                KeyboardUtils.hideSoftInput(this);
                requestCameraPermission();
                break;
            case R.id.tv_pull_back:
                mWhichDialog = 2;
                KeyboardUtils.hideSoftInput(this);
                requestCameraPermission();
                break;
            case R.id.tv_area:
                if (!FastClickUtil.isFastDoubleClick()) {
                    KeyboardUtils.hideSoftInput(this);
                    mPresenter.ShowPickerView();
                }
                break;
            case R.id.btn_ok:
                if (!FastClickUtil.isFastDoubleClick()) {
                    CharSequence text = mTvArea.getText();
                    if (!TextUtils.isEmpty(text)&&!TextUtils.equals(text,"请选择")) {
                        String[] area = text.toString().split(" ");
                        String prov = area[0];
                        String city = area[1];
                        String district = area[2];
                        mPresenter.onClickOk(mEntity.getTicketId(), etPhone.getText().toString().trim(), mEtName.getText().toString().trim(), mEtIdCard.getText().toString().trim()
                                , frontImgPath, backImgPath, prov, city, district, mEtAddress.getText().toString().trim());
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_ID_CARD_FRONT_REQUEST) {
                frontImgPath = data.getStringExtra(Extras.EXTRA_FILE_PATH);
                Bitmap bitmap = BitmapFactory.decodeFile(frontImgPath);
                mIvIdCardFront.setImageBitmap(bitmap);
                mIvIdCardFront.setVisibility(View.VISIBLE);

            }
            if (requestCode == PICK_ID_CARD_BACK_REQUEST) {
                backImgPath = data.getStringExtra(Extras.EXTRA_FILE_PATH);
                Bitmap bitmap = BitmapFactory.decodeFile(backImgPath);
                mIvIdCardBack.setImageBitmap(bitmap);
                mIvIdCardBack.setVisibility(View.VISIBLE);
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
