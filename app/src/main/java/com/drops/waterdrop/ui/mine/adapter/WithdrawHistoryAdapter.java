package com.drops.waterdrop.ui.mine.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.mine.activity.WithDrawActivity;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nim.uikit.model.WithdrawHistoryEntity;

import static com.drops.waterdrop.util.sys.UIUtils.getColor;

/**
 * Created by Mr.Smile on 2017/8/24.
 */

public class WithdrawHistoryAdapter extends BaseQuickAdapter<WithdrawHistoryEntity.ResultsBean,BaseViewHolder>{
    public WithdrawHistoryAdapter(int layoutResId) {
        super(R.layout.item_withdraw_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawHistoryEntity.ResultsBean item) {
        TextView sum = helper.getView(R.id.tv_num);
        TextView bankName = helper.getView(R.id.tv_bank_name);
        TextView time = helper.getView(R.id.tv_time);
        TextView accountStatus = helper.getView(R.id.tv_account_status);

        sum.setText(item.getDealPrice() + "");
        time.setText(item.getCreateTime());

        String cardNoLast4 = WithDrawActivity.getCardNoLast4(item.getCardNo());
        bankName.setText(item.getBankName() + cardNoLast4);

        int status = item.getStatus();
        switch (status) {
            case 0:
                accountStatus.setTextColor(Color.GRAY);
                accountStatus.setText("处理中");
                break;
            case 1:
                accountStatus.setTextColor(getColor(R.color.jindou_cut));
                accountStatus.setText("成功");
                break;
            case 90:
                accountStatus.setTextColor(getColor(R.color.jindou_add));
                accountStatus.setText("失败");
                break;
        }

    }

}
