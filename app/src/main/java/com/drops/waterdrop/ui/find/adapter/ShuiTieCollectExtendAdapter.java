package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.PostListEntity;

import java.util.List;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/05 23:50
 */

public class ShuiTieCollectExtendAdapter extends BaseQuickAdapter<PostListEntity.ResultsBean.CreatorBean, BaseViewHolder> {


    public ShuiTieCollectExtendAdapter(@Nullable List<PostListEntity.ResultsBean.CreatorBean> data) {
        super(R.layout.item_shuitie_collect_extend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PostListEntity.ResultsBean.CreatorBean item) {
        ImageView avatar = helper.getView(R.id.isce_img);
        GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_375x207, item.getPhoto(), avatar);
    }
}
