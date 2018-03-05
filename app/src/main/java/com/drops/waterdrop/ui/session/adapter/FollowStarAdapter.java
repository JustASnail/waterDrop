package com.drops.waterdrop.ui.session.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.StarListBean;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/5/15.
 */

public class FollowStarAdapter extends RecyclerView.Adapter<FollowStarAdapter.FollowStarViewHoler> {

    private List<StarListBean> mDatas;

    public FollowStarAdapter(List<StarListBean> datas) {
        this.mDatas = datas;
    }

    @Override
    public FollowStarViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_star_list, parent, false);


        return new FollowStarViewHoler(view);
    }

    @Override
    public void onBindViewHolder(FollowStarViewHoler holder, int position) {
        holder.mIvHeadImg.setImageResource(mDatas.get(position).HeaderImgId);
        holder.mTvName.setText(mDatas.get(position).name);
        holder.mTvDesc.setText(mDatas.get(position).sign);
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.size() == 0) {
            return 0;
        }
        return mDatas.size();
    }

    public class FollowStarViewHoler extends RecyclerView.ViewHolder {

        public ImageView mIvHeadImg;
        public TextView mTvName;
        public TextView mTvDesc;

        public FollowStarViewHoler(View itemView) {
            super(itemView);
            mIvHeadImg = (ImageView) itemView.findViewById(R.id.iv_head);
            mTvName = (TextView) itemView.findViewById(R.id.tv_pool_name);
            mTvDesc = (TextView) itemView.findViewById(R.id.tv_pool_desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListerner != null) {
                        mOnItemClickListerner.OnItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListerner {
        void OnItemClick(int position);
    }

    private OnItemClickListerner mOnItemClickListerner;

    public void setOnItemClickListerner(OnItemClickListerner onItemClickListerner) {
        mOnItemClickListerner = onItemClickListerner;
    }
}
