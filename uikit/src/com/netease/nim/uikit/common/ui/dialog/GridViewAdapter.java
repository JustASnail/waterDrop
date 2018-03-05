package com.netease.nim.uikit.common.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.model.InterestChekedEntity;
import com.netease.nim.uikit.model.InterestEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/10.
 */

public class GridViewAdapter extends BaseAdapter {
    List<InterestEntity.ResultsBean> interests;
    private Context mContext;

    private List<InterestChekedEntity> entitys = new ArrayList<>();
    private List<InterestChekedEntity> selecteds = new ArrayList<>();

    public GridViewAdapter(List<InterestEntity.ResultsBean> interests, Context context) {
        this.interests = interests;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (null != interests) {
            return interests.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return interests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_grid_view_interest, null);
        final TextView tvContent = (TextView) inflate.findViewById(R.id.tv_content);
        tvContent.setText(interests.get(position).getCategoryName());
        final InterestChekedEntity entity = new InterestChekedEntity();
        entity.setCategoryId(interests.get(position).getCategoryId());
        entity.setContent(interests.get(position).getCategoryName());
        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.isSelected()) {
                    entity.setSelected(false);
                    tvContent.setSelected(false);
                    if (selecteds.contains(entity)) {
                        selecteds.remove(entity);
                    }
                } else {
                    if (selecteds.size() > 3) {
                        Toast.makeText(mContext, "最多只能选择3个", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    entity.setSelected(true);
                    tvContent.setSelected(true);
                    selecteds.add(entity);
                }


            }
        });
       /*
        InterestChekedEntity entity = new InterestChekedEntity();
        entity.setView(tvContent);
        entity.setPosition(position);
        entity.setContent(contents[position]);
        entitys.add(entity);*/
        return inflate;
    }

    public List<InterestChekedEntity> getSelecteds() {
        return selecteds;
    }
}
