package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.StarListEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/5/16.
 */

public class StarListAdapter extends BaseQuickAdapter<StarListEntity.FriendsBean,BaseViewHolder> {
    public StarListAdapter(@LayoutRes int layoutResId, @Nullable List<StarListEntity.FriendsBean> data) {
        super(R.layout.item_star_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StarListEntity.FriendsBean item) {
        GlideUtil.showImageViewToCircle(mContext, R.drawable.icon_default_head_60dp, item.getFPhoto(), (HeadImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_pool_name, item.getFNickName());
        helper.setText(R.id.tv_pool_desc, item.getFUserDesc());
    }
}
