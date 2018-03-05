package com.drops.waterdrop.ui.mine.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.model.AccountDetaiEntity;
import com.netease.nim.uikit.model.WithdrawHistoryEntity;

import static com.drops.waterdrop.util.sys.UIUtils.getColor;

/**
 * Created by Mr.Smile on 2017/8/24.
 */

public class AccountDetailAdapter extends BaseQuickAdapter<AccountDetaiEntity.ResultsBean,BaseViewHolder> {

    public AccountDetailAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_account_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountDetaiEntity.ResultsBean item) {
        TextView type = helper.getView(R.id.tv_account_type);
        TextView orderId = helper.getView(R.id.tv_order_id);
        TextView orderLeft = helper.getView(R.id.tv_order_id1);     //左边文字
        TextView orderRight = helper.getView(R.id.tv_order_id2);    //右边文字
        TextView differ = helper.getView(R.id.tv_differ);
        TextView time = helper.getView(R.id.tv_time);

        String price = item.getPrice();
        Double aDouble = Double.valueOf(price);
        differ.setTextColor(aDouble >= 0 ? getColor(R.color.jindou_add) : getColor(R.color.jindou_cut));
        differ.setText(price);

        time.setText(item.getCreateTime());
        type.setText(item.getTitle());

//        int identityType = item.getIdentityType();
//        if (identityType == 1) {
//            // 明细类型，1表示支付完成，2表示退款，3表示提现申请，4提现失败
//            int status = item.getType();
//            switch (status) {
//                case 1:
//                    show(orderId, orderLeft, orderRight);
//                    orderId.setText(item.getComment());
//                    type.setText("支付完成");
//                    break;
//                case 2:
//                    show(orderId, orderLeft, orderRight);
//                    orderId.setText(item.getComment());
//                    type.setText("退款成功");
//                    break;
//                case 3:
//                    type.setText("提现申请");
//                    break;
//                case 4:
//                    type.setText("提现失败");
//                    break;
//            }
//        } else {
//            type.setText(item.getComment());
//        }


    }

    private void show(TextView orderId, TextView orderLeft, TextView orderRight) {
        orderId.setVisibility(View.VISIBLE);
        orderLeft.setVisibility(View.VISIBLE);
        orderRight.setVisibility(View.VISIBLE);
    }


}
