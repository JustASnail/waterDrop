package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.GiftInfoEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

import static com.netease.nim.uikit.request_body.RequestParams.address_id;

/**
 * Created by dengxiaolei  2017/10/10.
 */

public class CashGiftAddressActivity extends BaseActivity {


    private static final int REQUST_CODE = 123;
    @Bind(R.id.layout_address)
    View mLayoutAddress;
    @Bind(R.id.address_icon)
    ImageView mAddressIcon;
    @Bind(R.id.tv_no_address)
    TextView mTvNoAddress;
    @Bind(R.id.tv_consignee_name)
    TextView mTvConsigneeName;
    @Bind(R.id.tv_consignee_phone)
    TextView mTvConsigneePhone;
    @Bind(R.id.tv_consignee_address)
    TextView mTvConsigneeAddress;
    @Bind(R.id.rl_address)
    RelativeLayout mRlAddress;
    @Bind(R.id.iv_gift_cover)
    ImageView mIvGiftCover;
    @Bind(R.id.tv_gift_name)
    TextView mTvGiftName;
    @Bind(R.id.tv_gift_content)
    TextView mTvGiftContent;
    @Bind(R.id.btn_ok)
    Button mBtnOk;
    private GiftInfoEntity mEntity;
    private AlertDialog.Builder mBuilder;

    private long mAddressId = -1;


    public static void start(Context context, GiftInfoEntity entity) {
        Intent starter = new Intent(context, CashGiftAddressActivity.class);
        starter.putExtra(Constants.EXTRA_ENTITY, entity);
        context.startActivity(starter);
    }


    @Override
    protected void initView() {
        if (getIntent() != null) {
            mEntity = (GiftInfoEntity) getIntent().getSerializableExtra(Constants.EXTRA_ENTITY);
        }

        if (mEntity == null) {
            finish();
        } else {
            updateUI();
        }


    }

    private void updateUI() {
        mTvGiftName.setText(mEntity.getGiftTitle());
        mTvGiftContent.setText("礼包内容： " + mEntity.getGiftDesc());
        GlideUtil.showImageView(this, R.drawable.img_qs_90x90, mEntity.getGiftPhoto(), mIvGiftCover);
        getAddressList();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_cash_gift_address;
    }


    private void cashGift(String code, long gift_id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.exchange_code, code);
        map.put(RequestParams.gift_id, gift_id);
        map.put(address_id, mAddressId);
        final Observable<BaseResponse<Object>> giftInfo = HttpUtil.getInstance().sApi.giftExchange(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(giftInfo, new ProgressSubscriber<Object>(this) {
            @Override
            protected void _onNext(Object object) {
                ToastUtil.showShort("兑换成功");
                finish();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void showMyDialog() {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(this);
            mBuilder.setMessage("确认兑换？");
            mBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cashGift(mEntity.getExchangeCode(), mEntity.getGiftId());
                    dialog.dismiss();
                }
            });

            mBuilder.setNegativeButton("取消", null);
            mBuilder.setCancelable(false);
        }

        mBuilder.show();
    }


    @OnClick({R.id.iv_left, R.id.layout_address, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.layout_address:
                AddressManageActivity.startForResult(this, REQUST_CODE);
                break;
            case R.id.btn_ok:
                if (mAddressId == -1) {
                    ToastUtil.showShort("请添加地址");
                } else {
                    showMyDialog();
                }
                break;
        }
    }

    public void onNoAddress() {
        mRlAddress.setVisibility(View.GONE);
        mTvNoAddress.setVisibility(View.VISIBLE);
        mAddressId = -1;

    }

    public void setAddressView(AddressEntity.ResultsBean entity) {
        mRlAddress.setVisibility(View.VISIBLE);
        mTvNoAddress.setVisibility(View.GONE);
        mTvConsigneeName.setText(entity.getName());
        mTvConsigneePhone.setText(entity.getMobile());
        mTvConsigneeAddress.setText(entity.getProv() + entity.getCity() + entity.getDistrict() + " " + entity.getDetail());
        mAddressId = entity.getAddressId();


    }

    public void getAddressList() {
        Observable<BaseResponse<AddressEntity>> observable = HttpUtil.getInstance().sApi.getAddress(RequestBodyUtils.build(null));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity>(this) {
            @Override
            protected void _onNext(AddressEntity dataBean) {
                List<AddressEntity.ResultsBean> results = dataBean.getResults();
                if (results != null && results.size() > 0) {
                    getDefaultAddress(results);
                } else {
                    onNoAddress();
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                onNoAddress();

            }
        });
    }

    private void getDefaultAddress(List<AddressEntity.ResultsBean> results) {
        for (AddressEntity.ResultsBean result : results) {
            if (result.getDefaultFlag() == 1) {//是默认地址
                setAddressView(result);

                mHasDefaultAddress = true;
            }
        }

        if (!mHasDefaultAddress) {
            setAddressView(results.get(0));
        }
    }

    private boolean mHasDefaultAddress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            AddressEntity.ResultsBean entity = (AddressEntity.ResultsBean) data.getSerializableExtra(Constants.EXTRA_ENTITY);
            if (entity != null) {
                setAddressView(entity);
            }
        }


    }

}
