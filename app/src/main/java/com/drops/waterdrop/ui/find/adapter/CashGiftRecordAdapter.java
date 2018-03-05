package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.CashGiftRecordEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dengxiaolei on 2017/6/28.
 */

public class CashGiftRecordAdapter extends BaseQuickAdapter<CashGiftRecordEntity.ResultsBean, BaseViewHolder> {


    public CashGiftRecordAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_gift_record);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CashGiftRecordEntity.ResultsBean item) {
        helper.setText(R.id.tv_gift_name, item.getGiftTitle());
        helper.setText(R.id.tv_gift_code, "兑换码：" + item.getExchangeCode());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        helper.setText(R.id.tv_gift_date, formatter.format(new Date(item.getExchangeTimestamp())));

        ImageView imgView = helper.getView(R.id.iv_gift_cover);
        GlideUtil.showImageView(mContext, R.drawable.img_qs_90x90, item.getGiftPhoto(), imgView);
    }



}
