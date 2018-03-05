package com.netease.nim.uikit.session.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.ContactEntity;

import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * Created by dengxiaolei on 2017/5/22.
 */

public class ContactsSelectAdapter extends IndexableAdapter<ContactEntity> {
    private LayoutInflater mInflater;
private Context mContext;
    public ContactsSelectAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_contacts_select, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_contacts_select, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tvIndex.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, final ContactEntity entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tvName.setText(entity.getName());
        GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_33x33, entity.getAvatar(), vh.ivHead);

    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tvIndex;

        public IndexVH(View itemView) {
            super(itemView);
            tvIndex = (TextView) itemView.findViewById(R.id.tv_index);

        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tvName;
        HeadImageView ivHead;

        public ContentVH(View itemView) {

            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivHead = (HeadImageView) itemView.findViewById(R.id.iv_head);


        }
    }
}
