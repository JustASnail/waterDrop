package com.drops.waterdrop.ui.mine.adapter;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.TicketEntity;
import com.drops.waterdrop.ui.mine.activity.GetTicketActivity;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nim.uikit.model.MyTicketEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by HZH on 2017/7/5
 */

public class MyTicketListAdapter extends BaseQuickAdapter<MyTicketEntity.ResultsBean, BaseViewHolder> {
    public MyTicketListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_ticket_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyTicketEntity.ResultsBean item) {
        helper.setText(R.id.tv_ticket_type, item.getTicketName() + item.getTicketNum() + "张");
        helper.setText(R.id.tv_name, item.getActivityTitle());
        String time = TimeUtil.getTime(item.getCreateTime());
        helper.setText(R.id.tv_get_time, "获得时间： " + time);
        ImageView ivHead = helper.getView(R.id.iv_head_bg);
        GlideUtil.showImageView(mContext,R.drawable.img_qs_343x158, item.getActivityPicBig(), ivHead);
        TextView tvGet = helper.getView(R.id.tv_get);
        //1 已领取 0 未领取
        final boolean isGet = TextUtils.equals("1", item.getIsReceived());
        tvGet.setText(isGet ? "已领取" : "立即领取");
        tvGet.setEnabled(!isGet);

        tvGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    GetTicketActivity.start(mContext,isGet,item);
                }
            }
        });

    }

}
