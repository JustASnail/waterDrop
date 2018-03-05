package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.GoodsScoreListEntity;

/**
 * Created by dengxiaolei on 2017/6/8.
 */

public class GoodsDetailsCommentAdapter extends BaseQuickAdapter<GoodsScoreListEntity.ResultsBean, BaseViewHolder> {


    public GoodsDetailsCommentAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_goods_details_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsScoreListEntity.ResultsBean item) {
        HeadImageView ivHead = helper.getView(R.id.iv_head);
        RatingBar ratingBar = helper.getView(R.id.rating_bar);
        GlideUtil.showImageViewToCircle(mContext,R.drawable.icon_default_head_60dp,  item.getUser().getPhoto(), ivHead);
        helper.setText(R.id.tv_name, item.getUser().getNickName());
        helper.setText(R.id.tv_date, item.getScoreTime());
        ratingBar.setRating(item.getScore());
    }
}
