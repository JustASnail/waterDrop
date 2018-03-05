package com.drops.waterdrop.ui.store.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.find.activity.GoodsDetailsActivity;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.BrandListEntity;

/**
 * Created by Mr.Smile on 2017/9/15.
 */

public class BrandListAdapter extends BaseQuickAdapter<BrandListEntity.BrandGoodsBean,BaseViewHolder>{

    public BrandListAdapter(int layoutResId) {
        super(R.layout.item_brand);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BrandListEntity.BrandGoodsBean item) {
        View rlBrand = helper.getView(R.id.rl_brand);
        ImageView ivBrand = helper.getView(R.id.iv_brand);
        TextView tvName = helper.getView(R.id.tv_brand_name);
        TextView tvIntroduce = helper.getView(R.id.tv_brand_intro);
        TextView tvNum = helper.getView(R.id.tv_brand_num);
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);

        GlideUtil.showImageViewFirst(mContext, R.mipmap.img_qs_50x50, item.getBrandPhoto(), ivBrand);
        tvName.setText(item.getBrandName());
        tvIntroduce.setText(item.getBrandDesc());
        tvNum.setText(item.getGoodSize() + "件");

        rlBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBrandClickListener != null) {
                    onBrandClickListener.onBrandClickListener(helper.getPosition());
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        BrandGoodAdapter brandGoodAdapter = new BrandGoodAdapter(0);
        brandGoodAdapter.setNewData(item.getGoods());
        recyclerView.setAdapter(brandGoodAdapter);
        brandGoodAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BrandListEntity.BrandGoodsBean.GoodsBean goodsBean = item.getGoods().get(position);
                GoodsDetailsActivity.start(mContext, goodsBean.getGoodId(), Constants.STORE_TIP_ID, Constants.STORE_DROP_ID, Constants.STORE_TIP_TITLE);
            }
        });
    }

    private OnBrandClickListener onBrandClickListener;

    public void setOnBrandClickListener(OnBrandClickListener onBrandClickListener) {
        this.onBrandClickListener = onBrandClickListener;
    }

    public interface OnBrandClickListener{
        void onBrandClickListener(int pos);
    }
}
