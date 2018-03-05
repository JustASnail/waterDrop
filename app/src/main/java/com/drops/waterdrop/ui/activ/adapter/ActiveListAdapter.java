package com.drops.waterdrop.ui.activ.adapter;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.ActiveEntity;
import com.netease.nim.uikit.common.util.GlideUtil;

/**
 * Created by dengxiaolei on 2017/6/5.
 */

public class ActiveListAdapter extends BaseQuickAdapter<ActiveEntity, BaseViewHolder> {


    public ActiveListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_active_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActiveEntity item) {
        helper.setText(R.id.tv_active_name, item.getActiveName());
        helper.setText(R.id.tv_active_data, item.getActiveData());
      /*  final RelativeLayout rl_head = helper.getView(R.id.rl_head_bg);
        Glide.with(mContext).load(item.getHeadImgBg()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
                rl_head.setBackground(bitmapDrawable);
            }
        });*/

        ImageView imageView = helper.getView(R.id.rl_head_bg);
        if (TextUtils.isEmpty(item.getHeadImgBg())) {
            imageView.setImageResource(item.getHeadImgBgId());
        } else {
            GlideUtil.showImageView(mContext, item.getHeadImgBg(), imageView);
        }
    }


}
