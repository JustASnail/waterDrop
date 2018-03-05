package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.NumberUtil;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.PoolListEntity;

/**
 * Created by dengxiaolei on 2017/5/24.
 */

public class MyPoolListAdapter extends BaseQuickAdapter<PoolListEntity.ResultsBean, BaseViewHolder> {

    //是朋友的没有管理
    private  boolean isFriendPool;

    public MyPoolListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_pool_list);
    }

    public MyPoolListAdapter(@LayoutRes int layoutResId,boolean isFriendPool) {
        super(R.layout.item_pool_list);
        this.isFriendPool = isFriendPool;
    }

    @Override
    protected void convert(BaseViewHolder helper, PoolListEntity.ResultsBean item) {
        TextView tvTime = helper.getView(R.id.tv_time);
        tvTime.setText(item.getCreateTime());
        helper.setText(R.id.tv_title, item.getDropName());
        helper.setText(R.id.tv_content, item.getDropDesc());
        helper.setText(R.id.tv_focus_num, NumberUtil.Instance.formatNumber(item.getAttentionNum()));
        ImageView ivBg = helper.getView(R.id.iv_bg);
        GlideUtil.showImageView(mContext, R.drawable.img_qs_343x158, item.getHeadImg(), ivBg);

        HeadImageView ivAvatar = helper.getView(R.id.iv_avatar);
        PoolListEntity.ResultsBean.CreatorBean creator = item.getCreator();
        if (creator != null) {
            GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_33x33, creator.getPhoto(), ivAvatar);
            helper.setText(R.id.tv_nickname, creator.getNickName());
        }
    }

}
