package com.drops.waterdrop.ui.session.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.green_dao.SystemMessageDB;

/**
 * Created by dengxiaolei on 2017/7/12.
 */

public class BalanceMsgListAdapter extends BaseQuickAdapter<SystemMessageDB, BaseViewHolder> {
    public BalanceMsgListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_balance_msg);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemMessageDB item) {

        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_date, item.getCreateTime());
        helper.setText(R.id.tv_content, item.getNote());

        ImageView ivPoint = helper.getView(R.id.icon_point);
        if (item.getUnreadTag() == 0) {
            ivPoint.setVisibility(View.VISIBLE);
        } else {
            ivPoint.setVisibility(View.GONE);
        }

    }
}
