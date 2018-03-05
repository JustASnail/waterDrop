package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.NumberUtil;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.SearchPostEntity;

/**
 * Created by dengxiaolei on 2017/6/28.
 */

public class PostSearchAdapter extends BaseQuickAdapter<SearchPostEntity.ResultsBean, BaseViewHolder> {
    public PostSearchAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_search_pool_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchPostEntity.ResultsBean item) {
        HeadImageView ivAvatar = helper.getView(R.id.iv_avatar);
        GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_50x50, item.getCover(), ivAvatar);
        helper.setText(R.id.tv_name, item.getTipTitle());
        helper.setText(R.id.tv_number, NumberUtil.Instance.formatNumber(item.getBrowserNum()));
        helper.setText(R.id.tv_date, item.getCreateTimeShow());
    }
}
