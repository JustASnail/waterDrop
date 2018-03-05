package com.drops.waterdrop.ui.pool;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.model.CategoryEntity;

/**
 * Created by dengxiaolei on 2017/7/2.
 */

public class DrawerLeftAdapter extends BaseQuickAdapter<CategoryEntity.ResultsBean, BaseViewHolder> {

    private int mCurrentPosition = 0;

    public DrawerLeftAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_drawer_pool_category_left);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CategoryEntity.ResultsBean item) {
        final TextView tvName = helper.getView(R.id.tv_name);
        final ImageView ivTag = helper.getView(R.id.iv_tag);

        setCurrentItem(tvName, ivTag, helper.getAdapterPosition(), item.getCategoryName());
        helper.setOnClickListener(R.id.container, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPosition = helper.getAdapterPosition();
                notifyDataSetChanged();
                if (mLeftItemSelectedListener != null) {
                    mLeftItemSelectedListener.onLeftItemSelectedListener(mCurrentPosition, item);
                }
            }
        });
    }

    private void setCurrentItem(TextView tvName, ImageView ivTag, int position, String item) {
        if (mCurrentPosition == position) {
            tvName.setText(item);
            tvName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            ivTag.setVisibility(View.VISIBLE);
        } else {
            tvName.setText(item);
            tvName.setTextColor(mContext.getResources().getColor(R.color.color_d2d2d2));
            ivTag.setVisibility(View.GONE);
        }
    }

    public interface OnLeftItemSelectedListener{
        void onLeftItemSelectedListener(int position, CategoryEntity.ResultsBean item);
    }

    private OnLeftItemSelectedListener mLeftItemSelectedListener;

    public void setLeftItemSelectedListener(OnLeftItemSelectedListener leftItemSelectedListener) {
        mLeftItemSelectedListener = leftItemSelectedListener;
    }
}
