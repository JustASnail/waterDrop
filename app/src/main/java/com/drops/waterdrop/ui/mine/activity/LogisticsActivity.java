package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.presenter.LogiticsPresenter;
import com.drops.waterdrop.ui.mine.view.ILogiticsView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.HZHLogiticsView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.LogiticsEntity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/7/8.
 */

public class LogisticsActivity extends BaseActivity<ILogiticsView, LogiticsPresenter> {
    public static final String ORDER_ID = "order_id";
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.logitics_view)
    HZHLogiticsView mLogiticsView;
    @Bind(R.id.tv_logitics_name)
    TextView tvLogiticsName;
    @Bind(R.id.tv_logitics_no)
    TextView tvLogiticsNo;
    @Bind(R.id.tv_logitics_phone)
    TextView tvLogiticsPhone;
    @Bind(R.id.iv_none)
    RelativeLayout mRLNone;
    private LogiticsEntity mEntity;

    public static void start(Context context, LogiticsEntity entity) {
        Intent starter = new Intent(context, LogisticsActivity.class);
        starter.putExtra(Constants.EXTRA_ENTITY,entity );
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        mEntity = (LogiticsEntity) getIntent().getSerializableExtra(Constants.EXTRA_ENTITY);
        initTitle();
    }

    @Override
    protected void initData() {

        if (!TextUtils.isEmpty(mEntity.getDeliveryNo())) {
            tvLogiticsName.setText(mEntity.getDeliveryCompany());
            tvLogiticsNo.setText(mEntity.getDeliveryNo());
            tvLogiticsPhone.setText(mEntity.getDeliveryPhone());
            GlideUtil.showImageView(this, R.drawable.img_qs_90x90, mEntity.getGoodPhoto(), ivAvatar);
            int deliveryStatus = mEntity.getDeliveryStatus();
            switch (deliveryStatus) {
                case 0:
                    tvStatus.setText("未发货");
                    break;
                case 1:
                    tvStatus.setText("运输中");
                    break;
                case 2:
                    tvStatus.setText("已签收");
                    break;
            }

            List<LogiticsEntity.LogisticsBean> logistics = mEntity.getLogistics();
            if (logistics != null && logistics.size() > 0) {
                mLogiticsView.setLogisticsDataList(logistics);
            }
        } else {
            mRLNone.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected LogiticsPresenter createPresenter() {
        return new LogiticsPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_logitics;
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "物流详情";
        options.isNeedNavigate = true;
        setMyToolbar(options);
    }

}
