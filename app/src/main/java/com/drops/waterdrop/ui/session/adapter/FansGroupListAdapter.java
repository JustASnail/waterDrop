package com.drops.waterdrop.ui.session.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nimlib.sdk.team.model.Team;

/**
 * Created by dengxiaolei on 2017/7/11.
 */

public class FansGroupListAdapter extends BaseQuickAdapter<Team, BaseViewHolder> {
    public FansGroupListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_fans_group_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Team item) {
        HeadImageView ivAvatar = helper.getView(R.id.iv_avatar);
        GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_50x50, item.getIcon(), ivAvatar);
        helper.setText(R.id.tv_name, item.getName());
    }



}
