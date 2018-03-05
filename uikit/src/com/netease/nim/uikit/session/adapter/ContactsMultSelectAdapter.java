package com.netease.nim.uikit.session.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.ContactEntity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * Created by dengxiaolei on 2017/5/22.
 */

public class ContactsMultSelectAdapter extends IndexableAdapter<ContactEntity> {
    private LayoutInflater mInflater;
    private Context mContext;

    // 存储勾选框状态的map集合
    private Map<Long, Boolean> map = new HashMap<>();
    // 存储被勾选者的账号
    private List<Long> accounts = new ArrayList<>();


    public ContactsMultSelectAdapter(Context context) {
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
        View view = mInflater.inflate(R.layout.item_contacts_mult_select, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tvIndex.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(final RecyclerView.ViewHolder holder, final ContactEntity entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tvName.setText(entity.getName());
        GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_33x33, entity.getAvatar(), vh.ivHead);

        if (entity.isAdded()) {
//            vh.cbAdd.setVisibility(View.INVISIBLE);
            vh.cbAdd.setButtonDrawable(R.drawable.btn_yqhy_bkxz);
            vh.cbAdd.setEnabled(false);
        } else {
//            vh.cbAdd.setVisibility(View.VISIBLE);
            vh.cbAdd.setButtonDrawable(R.drawable.sele_check_contacts);

            vh.cbAdd.setEnabled(true);
        }

        //设置checkBox改变监听
        vh.cbAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Logger.d("群：" + holder.getAdapterPosition());
                //用map集合保存
                map.put(entity.getUid(), isChecked);
                if (mOnCheckBoxCheckedListener != null) {
                    mOnCheckBoxCheckedListener.onCheckChange();
                }
            }
        });
        // 设置CheckBox的状态
        if (map.get(entity.getUid()) == null) {
            map.put(entity.getUid(), false);
        }
        vh.cbAdd.setChecked(map.get(entity.getUid()));
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
        CheckBox cbAdd;

        public ContentVH(View itemView) {

            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivHead = (HeadImageView) itemView.findViewById(R.id.iv_head);
            cbAdd = (CheckBox) itemView.findViewById(R.id.cb_add);
        }
    }

    public List<Long> getSelectedAccounts() {
        accounts.clear();

        for (Map.Entry<Long, Boolean> m :map.entrySet())  {
            if (m.getValue()) {
                accounts.add(m.getKey());
            }
        }

        return accounts;
    }

    public interface OnCheckBoxCheckedListener{
        void onCheckChange();
    }

    private OnCheckBoxCheckedListener mOnCheckBoxCheckedListener;

    public void setOnCheckBoxCheckedListener(OnCheckBoxCheckedListener onCheckBoxCheckedListener) {
        mOnCheckBoxCheckedListener = onCheckBoxCheckedListener;
    }
}
