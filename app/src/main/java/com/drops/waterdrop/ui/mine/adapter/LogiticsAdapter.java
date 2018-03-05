package com.drops.waterdrop.ui.mine.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.model.LogiticsEntity;

/**
 * Created by Mr.Smile on 2017/7/8.
 */

public class LogiticsAdapter extends BaseQuickAdapter<LogiticsEntity.LogisticsBean,BaseViewHolder>{

    public LogiticsAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_logitics);
    }

    @Override
    protected void convert(BaseViewHolder helper, LogiticsEntity.LogisticsBean item) {

        TextView tvTime = helper.getView(R.id.tv_time);
        TextView tv_pos = helper.getView(R.id.tv_current_pos);
        tvTime.setText(item.getTime());
        tv_pos.setText(item.getContent());

        ImageView point = helper.getView(R.id.iv_point);
        View topLine = helper.getView(R.id.tv_up_line);
        View view = helper.getView(R.id.bottom_line);

        if (helper.getPosition() == 0) {
            TextView time = helper.getView(R.id.tv_time);
            time.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            TextView pos = helper.getView(R.id.tv_current_pos);
            pos.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            point.setBackground(mContext.getDrawable(R.mipmap.icon_wlxx_dl));
            topLine.setVisibility(View.INVISIBLE);
        }
    }
}
