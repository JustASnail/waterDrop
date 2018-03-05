package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.StarInfoEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/5/23.
 */

public class TopPostAdapter extends BaseQuickAdapter<StarInfoEntity.DropTipsBean, BaseViewHolder> {


    public TopPostAdapter(@LayoutRes int layoutResId, @Nullable List<StarInfoEntity.DropTipsBean> data) {
        super(R.layout.item_hot_post, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final StarInfoEntity.DropTipsBean item) {
        ImageView ivHead = helper.getView(R.id.iv_img);
        HeadImageView ivUserHead = helper.getView(R.id.iv_head);

        GlideUtil.showImageView(mContext, item.getCover(), ivHead);
        StarInfoEntity.DropTipsBean.CreatorBean creator = item.getCreator();
        if (creator != null) {
            GlideUtil.showImageViewToCircle(mContext, R.drawable.icon_default_head_60dp, creator.getPhoto(), ivUserHead);
            helper.setText(R.id.tv_from_name, creator.getNickName());
        }

        helper.setText(R.id.tv_title, item.getTipTitle());
        helper.setText(R.id.tv_content, item.getTipContent());
        helper.setText(R.id.tv_like_num, item.getLikeNum() + "");
        ImageView ivLikeIcon = helper.getView(R.id.iv_like_icon);

        int status = item.getStatus();

        ivLikeIcon.setEnabled(status == 1);


        ivLikeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
