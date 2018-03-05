package com.drops.waterdrop.ui.store.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.StoreHomePageEntity;
import com.netease.nim.uikit.model.StoreSelfEntity;

/**
 * Created by Mr.Smile on 2017/9/13.
 */

public class FunActiveListAdapter extends BaseQuickAdapter<StoreHomePageEntity.RecommendsBean, BaseViewHolder> {
    public FunActiveListAdapter(int layoutResId) {
        super(R.layout.fun_active_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreHomePageEntity.RecommendsBean item) {
        ImageView iv = helper.getView(R.id.iv_active);
        if (item.getPhotos() != null && item.getPhotos().size() > 0)
            GlideUtil.showImageView(mContext, R.mipmap.img_qs_343x120, item.getPhotos().get(0), iv);
    }
}
