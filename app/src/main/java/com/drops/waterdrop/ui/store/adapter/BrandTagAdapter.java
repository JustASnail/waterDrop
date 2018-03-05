package com.drops.waterdrop.ui.store.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.netease.nim.uikit.model.BrandTagEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/9/15.
 */

public class BrandTagAdapter extends TagAdapter<BrandTagEntity.ResultsBean>{
    public BrandTagAdapter(List<BrandTagEntity.ResultsBean> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, BrandTagEntity.ResultsBean s) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand_tag, parent, false);
        TextView tvName = (TextView) inflate.findViewById(R.id.tv_name);
        tvName.setText(s.getBrandName());
        return inflate;
    }
}
