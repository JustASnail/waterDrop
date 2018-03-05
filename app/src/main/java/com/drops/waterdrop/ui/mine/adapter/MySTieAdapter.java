package com.drops.waterdrop.ui.mine.adapter;

import android.support.annotation.LayoutRes;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.model.MySTieEntity;



/**
 * Created by Mr.Smile on 2017/6/28.
 */

public class MySTieAdapter extends BaseQuickAdapter<MySTieEntity.DropTipsBean,BaseViewHolder> {

    public MySTieAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_collection_st);
    }

    @Override
    protected void convert(BaseViewHolder helper, MySTieEntity.DropTipsBean item) {

    }

}
