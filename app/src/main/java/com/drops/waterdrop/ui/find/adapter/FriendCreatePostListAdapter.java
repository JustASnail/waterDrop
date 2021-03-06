package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.MyPostEntity;

/**
 * Created by Mr.Smile on 2017/7/3.
 */

public class FriendCreatePostListAdapter extends BaseQuickAdapter<MyPostEntity.ResultsBean,BaseViewHolder>{

    public FriendCreatePostListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_pool_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyPostEntity.ResultsBean item) {
        HeadImageView ivAvatar = helper.getView(R.id.iv_avatar);
        TextView tvName = helper.getView(R.id.tv_nickname);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvNum = helper.getView(R.id.tv_focus_num);
        TextView tvContent = helper.getView(R.id.tv_content);
        TextView tvTime = helper.getView(R.id.tv_time);
        ImageView ivBg = helper.getView(R.id.iv_bg);

        GlideUtil.showImageView(mContext, item.getCover(), ivBg);
        MyPostEntity.ResultsBean.CreatorBean creator = item.getCreator();
        if (creator != null) {
            GlideUtil.showImageViewToCircle(mContext, R.drawable.icon_default_head_60dp, creator.getPhoto(), ivAvatar);
            tvName.setText(creator.getNickName());
        }

        tvTitle.setText(item.getTipTitle());
        tvNum.setText(item.getLikeNum() + "");
        tvContent.setText(item.getTipContent());
//        tvTime.setText(item.getCreator().getPhoto());
    }
}
