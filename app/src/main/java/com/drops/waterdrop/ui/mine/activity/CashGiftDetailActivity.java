package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.CashGiftRecordEntity;
import com.netease.nim.uikit.model.LogiticsEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by dengxiaolei on 2017/10/10.
 */

public class CashGiftDetailActivity extends BaseActivity {

    @Bind(R.id.iv_left)
    ImageView mIvLeft;
    @Bind(R.id.tv_consignee_name)
    TextView mTvConsigneeName;
    @Bind(R.id.tv_consignee_phone)
    TextView mTvConsigneePhone;
    @Bind(R.id.tv_consignee_address)
    TextView mTvConsigneeAddress;
    @Bind(R.id.iv_gift_cover)
    ImageView mIvGiftCover;
    @Bind(R.id.tv_gift_name)
    TextView mTvGiftName;
    @Bind(R.id.tv_gift_desc)
    TextView mTvGiftDesc;
    @Bind(R.id.tv_exchange_date)
    TextView mTvExchangeDate;
    @Bind(R.id.tv_gift_code)
    TextView mTvGiftCode;
    @Bind(R.id.btn_ok)
    Button mBtnOk;
    private CashGiftRecordEntity.ResultsBean mEntity;

    public static void start(Context context, CashGiftRecordEntity.ResultsBean entity) {
        Intent starter = new Intent(context, CashGiftDetailActivity.class);
        starter.putExtra(Constants.EXTRA_ENTITY, entity);
        context.startActivity(starter);
    }


    @Override
    protected void initView() {
        if (getIntent() != null) {
            mEntity = (CashGiftRecordEntity.ResultsBean) getIntent().getSerializableExtra(Constants.EXTRA_ENTITY);
        }

        if (mEntity != null) {
            updateUI();
        } else {
            finish();

        }
    }

    private void updateUI() {
        mTvConsigneeName.setText(mEntity.getDeliveryName());
        mTvConsigneeAddress.setText(mEntity.getDeliveryProv() + mEntity.getDeliveryCity() + mEntity.getDeliveryDistrict() + "  " + mEntity.getDeliveryAddress());
        mTvConsigneePhone.setText(mEntity.getDeliveryMobile());
        mTvGiftName.setText(mEntity.getGiftTitle());
        mTvGiftDesc.setText(mEntity.getGiftDesc());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        mTvExchangeDate.setText("兑换时间： " + formatter.format(new Date(mEntity.getExchangeTimestamp())));
        mTvGiftCode.setText("兑换码： " + mEntity.getExchangeCode());

        GlideUtil.showImageView(this, R.drawable.img_qs_90x90, mEntity.getGiftPhoto(), mIvGiftCover);
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
        return R.layout.activity_cash_gift_detail;
    }




    @OnClick({R.id.iv_left, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.btn_ok:
                getLogistics(mEntity.getGiftId() + "");
                break;
        }
    }

    private void getLogistics(String giftId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.gift_id, giftId);
        Observable<BaseResponse<LogiticsEntity>> observable = HttpUtil.getInstance().sApi.getGiftLogitics(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<LogiticsEntity>(this) {
            @Override
            protected void _onNext(LogiticsEntity entity) {
                if (entity != null) {
                    LogisticsActivity.start(CashGiftDetailActivity.this, entity);
                } else {
                    ToastUtil.showShort("暂无物流信息");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }


}