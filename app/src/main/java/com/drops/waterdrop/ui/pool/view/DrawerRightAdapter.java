package com.drops.waterdrop.ui.pool.view;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.model.CategoryEntity;

/**
 * Created by dengxiaolei on 2017/7/2.
 */

public class DrawerRightAdapter extends BaseQuickAdapter<CategoryEntity.ResultsBean.LeafsBean, BaseViewHolder> {

    private int mCurrentPosition = -1;

    public DrawerRightAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_drawer_pool_category_right);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CategoryEntity.ResultsBean.LeafsBean item) {
        final TextView tvName = helper.getView(R.id.tv_name);
        setCurrentItem(tvName, item.getCategoryName(), helper.getAdapterPosition());
        tvName.setText(item.getCategoryName());
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPosition = helper.getAdapterPosition();
                if (mOnRightItemSelectedListener != null) {
                    mOnRightItemSelectedListener.onRightItemSelectedListener(helper.getAdapterPosition(), item);
                }
                notifyDataSetChanged();

            }
        });
    }

    private void setCurrentItem(TextView tvName, String item , int position) {
        if (mCurrentPosition == position) {
            tvName.setSelected(true);
        } else {
            tvName.setSelected(false);
        }
    }

    public interface OnRightItemSelectedListener{
        void onRightItemSelectedListener(int position, CategoryEntity.ResultsBean.LeafsBean content);
    }

    private OnRightItemSelectedListener mOnRightItemSelectedListener;

    public void setOnRightItemSelectedListener(OnRightItemSelectedListener rightItemSelectedListener) {
        mOnRightItemSelectedListener = rightItemSelectedListener;
    }

    public void setCurrentPosition(int currentPosition) {
        mCurrentPosition = currentPosition;
    }
}
