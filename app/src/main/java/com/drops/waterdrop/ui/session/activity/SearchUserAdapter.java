package com.drops.waterdrop.ui.session.activity;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.SearchFriendEntity;

/**
 * Created by dengxiaolei on 2017/7/31.
 */

public class SearchUserAdapter extends BaseQuickAdapter<SearchFriendEntity.ResultsBean, BaseViewHolder> {
    public SearchUserAdapter(@LayoutRes int layoutResId) {
        super(R.layout.layout_user_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchFriendEntity.ResultsBean item) {

        ImageView ivHead = helper.getView(R.id.img_head);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvAccount = helper.getView(R.id.tv_account);

        Glide.with(mContext).load(item.getPhoto()).error(R.drawable.img_qs_60x60).into(ivHead);
        tvName.setText(item.getNickName());
        tvAccount.setText("水滴账号： "+item.getUid());
    }
}
