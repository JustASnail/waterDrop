package com.drops.waterdrop.ui.mine.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.model.WithdrawHistoryEntity;

import java.io.Serializable;

import butterknife.Bind;

import static com.drops.waterdrop.util.sys.UIUtils.getColor;

/**
 * Created by Mr.Smile on 2017/8/25.
 */

public class WithdrawDetailActivity extends BaseActivity {

    @Bind(R.id.tv_sum)
    TextView tvSum;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_account)
    TextView tvAccount;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.tv_reason)
    TextView tvReason;
    @Bind(R.id.rl_reason)
    RelativeLayout rlReason;

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "提现详情";
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        WithdrawHistoryEntity.ResultsBean entity = (WithdrawHistoryEntity.ResultsBean) getIntent().getSerializableExtra(Constants.EXTRA_ENTITY);

        tvSum.setText(entity.getDealPrice() + "元");
        tvTime.setText(entity.getCreateTime());

        String cardNo = entity.getCardNo();
        String substring = cardNo.substring(cardNo.length() - 4);
        tvAccount.setText(entity.getBankName() + "(" + substring + ")");

        int status = entity.getStatus();
        switch (status) {
            case 0:
                tvStatus.setTextColor(Color.GRAY);
                tvStatus.setText("处理中");
                break;
            case 1:
                tvStatus.setTextColor(getResources().getColor(R.color.jindou_cut));
                tvStatus.setText("成功");
                break;
            case 90:
                tvStatus.setTextColor(getResources().getColor(R.color.jindou_add));
                tvStatus.setText("失败");
                rlReason.setVisibility(View.VISIBLE);
                tvReason.setText("这是失败原因");
                break;
        }
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
        return R.layout.activity_withdraw_detail;
    }
}
