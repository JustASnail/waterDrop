package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.PostEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/5/23.
 */

public class GoodsAdapter extends BaseQuickAdapter<PostEntity.GoodsBean, BaseViewHolder> {
    public GoodsAdapter(@LayoutRes int layoutResId, @Nullable List<PostEntity.GoodsBean> data) {
        super(R.layout.item_goods_list, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PostEntity.GoodsBean item) {
        helper.setText(R.id.tv_goods_name, item.getActTitle());
        HeadImageView ivHead = helper.getView(R.id.iv_goods_icon);
        GlideUtil.showImageViewToCircle(mContext, R.drawable.icon_default_head_60dp, item.getActPic(), ivHead);
    }
}
