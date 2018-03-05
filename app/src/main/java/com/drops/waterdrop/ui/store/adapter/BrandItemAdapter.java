package com.drops.waterdrop.ui.store.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.NumberUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.BrandItemEntity;
import com.netease.nim.uikit.model.BrandListEntity;

/**
 * Created by Mr.Smile on 2017/9/18.
 */

public class BrandItemAdapter extends BaseQuickAdapter<BrandItemEntity.ResultsBean,BaseViewHolder>{
    public BrandItemAdapter(int layoutResId) {
        super(R.layout.item_brand_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandItemEntity.ResultsBean item) {
        TextView tvDesc = helper.getView(R.id.tv_good_desc);
        TextView tvPrice = helper.getView(R.id.tv_good_price);
        ImageView ivGood = helper.getView(R.id.iv_good);

        tvDesc.setText(item.getActTitle());
        String formatPrice = NumberUtil.Instance.formatPrice(item.getActPrice());
        tvPrice.setText(formatPrice);
        GlideUtil.showImageView(mContext, R.drawable.img_qs_90x90, item.getActPic(), ivGood);
    }
}
