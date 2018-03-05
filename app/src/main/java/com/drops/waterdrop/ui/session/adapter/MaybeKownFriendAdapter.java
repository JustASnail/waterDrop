package com.drops.waterdrop.ui.session.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.mine.activity.UserProfileActivity;
import com.drops.waterdrop.ui.session.activity.VerifyActivity;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.AddFriendForUid;
import com.netease.nim.uikit.model.RecommendFriendEntity;

/**
 * Created by dengxiaolei on 2017/9/12.
 */

public class MaybeKownFriendAdapter extends BaseQuickAdapter<RecommendFriendEntity.ResultsBean, BaseViewHolder> {
    public MaybeKownFriendAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_recommend_friend);
    }

    @Override
    protected void convert(BaseViewHolder helper, final RecommendFriendEntity.ResultsBean item) {
        helper.setText(R.id.tv_name, item.getNickName());
        HeadImageView headImageView = helper.getView(R.id.hiv_head);
        Button btnAdd = helper.getView(R.id.btn_add);
        GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_33x33, item.getPhoto(), headImageView);

        if (item.getUid() == MyUserCache.getUserUid()) {
            btnAdd.setEnabled(false);
            btnAdd.setText("已添加");
        } else {
            int status = item.getRelationStatus();
            if (status == 1) {
                btnAdd.setEnabled(false);
                btnAdd.setText("已添加");
            } else if (status == 2) {
                btnAdd.setEnabled(false);
                btnAdd.setText("待验证");
            } else {
                btnAdd.setEnabled(true);
                btnAdd.setText("添加");
            }
        }


        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileActivity.start(mContext, item.getUid());
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendForUid searchFriendEntity = new AddFriendForUid();
                searchFriendEntity.setNickName(item.getNickName());
                searchFriendEntity.setPhoto(item.getPhoto());
                searchFriendEntity.setRelationStatus(item.getRelationStatus());
                searchFriendEntity.setUid(item.getUid());
                VerifyActivity.start(mContext, searchFriendEntity);
            }
        });

    }
}
