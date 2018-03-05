package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.widget.RoundRectImageView;
import com.netease.nim.uikit.common.util.GlideUtil;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/8.
 */

public class GoodsDetailsListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public GoodsDetailsListAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(R.layout.item_goods_details_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RoundRectImageView iv = helper.getView(R.id.iv_img);
        GlideUtil.showImageView(mContext, item, iv);
    }
}
