package com.netease.nim.uikit.session.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.model.PoolListBean;

/**
 * Created by dengxiaolei on 2017/6/1.
 */

public class PoolSelectAdapter extends BaseQuickAdapter<PoolListBean, BaseViewHolder> {
    public PoolSelectAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_contacts_select);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoolListBean item) {
        helper.setImageResource(R.id.iv_head, R.drawable.nim_avatar_group);
        helper.setText(R.id.tv_name, item.getName());
    }


}
