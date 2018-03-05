package com.netease.nim.uikit.session.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.netease.nim.uikit.R;

import java.util.List;

import me.yokeyword.indexablerv.IndexableHeaderAdapter;

/**
 * Created by dengxiaolei on 2017/7/22.
 */

public class ContactsHeaderAdapter extends IndexableHeaderAdapter {
    public ContactsHeaderAdapter(String index, String indexTitle, List datas) {
        super(index, indexTitle, datas);
    }

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact_select_header, parent, false);
        return new ContactsHeaderAdapter.HeadContentVH(inflate);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
        ContactsHeaderAdapter.HeadContentVH vh = (ContactsHeaderAdapter.HeadContentVH) holder;

    }


    private class HeadContentVH extends RecyclerView.ViewHolder {
        RelativeLayout rlSelectGroup;

        public HeadContentVH(View itemView) {
            super(itemView);
            rlSelectGroup = (RelativeLayout) itemView.findViewById(R.id.rl_select_group);
        }
    }

  /*  public interface onGroupItemClickListener{
        void onGroupItemClick();
    }

    private onGroupItemClickListener;

    @Override
    public void setOnItemHeaderClickListener(OnItemHeaderClickListener listener) {
        super.setOnItemHeaderClickListener(listener);
    }*/
}
