package com.netease.nim.uikit.contact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.MyFriendEntity;

import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * Created by dengxiaolei on 2017/6/14.
 */

public class ContactAdapter extends IndexableAdapter<MyFriendEntity> {
    private LayoutInflater mInflater;
    private Context mContext;

    public ContactAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_my_friend, parent, false);
        return new IndexVH(view);
    }


    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_my_friend, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, final MyFriendEntity entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tvName.setText(entity.getFNickName());
        GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_50x50,entity.getFPhoto(), vh.ivHead);

    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tvName;
        HeadImageView ivHead;

        public ContentVH(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_nickname);
            ivHead = (HeadImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }
}
