package com.drops.waterdrop.ui.session.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.google.gson.Gson;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.green_dao.SystemMessageDB;
import com.netease.nim.uikit.model.RecentMessageListEntity;

/**
 * Created by dengxiaolei on 2017/7/12.
 */

public class OrderMsgListAdapter extends BaseQuickAdapter<SystemMessageDB, BaseViewHolder> {
    public OrderMsgListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_order_msg);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemMessageDB item) {
//        String timeString = TimeUtil.getTimeShowString(item.getTime(), true);
        String data = item.getData();
        Gson gson = new Gson();
        RecentMessageListEntity.ResultsBean.DataBean dataBean = gson.fromJson(data, RecentMessageListEntity.ResultsBean.DataBean.class);
        helper.setText(R.id.tv_order_status, item.getTitle());
        if (dataBean != null) {
            ImageView ivHead = helper.getView(R.id.iv_head);
            GlideUtil.showImageView(mContext, R.drawable.img_qs_50x50, dataBean.getGoods().get(0).getGoodCovery(), ivHead);
            helper.setText(R.id.tv_content, dataBean.getGoods().get(0).getGoodName());
            helper.setText(R.id.tv_order_code, "订单编号：" + dataBean.getOrderId());
        }
        helper.setText(R.id.tv_date, item.getCreateTime());

        ImageView ivPoint = helper.getView(R.id.icon_point);
        if (item.getUnreadTag() == 0) {
            ivPoint.setVisibility(View.VISIBLE);
        } else {
            ivPoint.setVisibility(View.GONE);
        }

    }
}
