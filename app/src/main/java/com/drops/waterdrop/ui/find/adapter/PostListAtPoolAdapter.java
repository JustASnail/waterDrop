package com.drops.waterdrop.ui.find.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.NumberUtil;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.DropDetailsEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/6.
 */

public class PostListAtPoolAdapter extends BaseMultiItemQuickAdapter<DropDetailsEntity.TipsBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PostListAtPoolAdapter(List<DropDetailsEntity.TipsBean> data) {
        super(data);
        addItemType(1, R.layout.pool_post_type1);
        addItemType(3, R.layout.pool_post_type2);
        addItemType(2, R.layout.pool_post_type3);
    }

    @Override
    protected void convert(BaseViewHolder helper, DropDetailsEntity.TipsBean item) {
        helper.setText(R.id.tv_name, item.getTipTitle());
        helper.setText(R.id.tv_number, NumberUtil.Instance.formatNumber(item.getBrowserNum()));
        helper.setText(R.id.tv_date, item.getCreateTimeShow());

        switch (helper.getItemViewType()) {
            case 1:
                List<DropDetailsEntity.PhotoBean> photos1 = item.getPhotos();
                ImageView ivImg = helper.getView(R.id.iv_img);
                HeadImageView ivCreatorAvatar = helper.getView(R.id.iv_creator_avatar);
                GlideUtil.showImageView(mContext, R.drawable.img_qs_90x90, item.getCover(), ivImg);
                String creatorPhoto = "";
                if (item.getCreator() != null) {
                    helper.setText(R.id.tv_creator_name, item.getCreator().getNickName());
                    creatorPhoto = item.getCreator().getPhoto();
                }
                GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_33x33, creatorPhoto, ivCreatorAvatar);
                break;
            case 3:

                ImageView ivImgg1 = helper.getView(R.id.iv_img1);
                ImageView ivImgg2 = helper.getView(R.id.iv_img2);
                ImageView ivImgg3 = helper.getView(R.id.iv_img3);


                List<DropDetailsEntity.PhotoBean> photos2 = item.getPhotos();
                if (photos2 != null && photos2.size() >= 3) {
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  photos2.get(0).getPhoto(), ivImgg1);
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  photos2.get(1).getPhoto(), ivImgg2);
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  photos2.get(2).getPhoto(), ivImgg3);
                } else if (photos2 != null && photos2.size() >= 2) {
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  photos2.get(0).getPhoto(), ivImgg1);
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  photos2.get(1).getPhoto(), ivImgg2);
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  photos2.get(1).getPhoto(), ivImgg3);
                } else if (photos2 != null && photos2.size() >= 1) {
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  photos2.get(0).getPhoto(), ivImgg1);
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  photos2.get(0).getPhoto(), ivImgg2);
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  photos2.get(0).getPhoto(), ivImgg3);
                } else {
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  item.getCover(), ivImgg1);
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  item.getCover(), ivImgg2);
                    GlideUtil.showImageView(mContext,R.drawable.img_qs_60x60,  item.getCover(), ivImgg3);
                }

                break;
            case 2:

                ImageView ivImggg = helper.getView(R.id.iv_img);
                GlideUtil.showImageView(mContext, R.drawable.img_qs_343x168, item.getCover(), ivImggg);
                break;
        }
    }


}
