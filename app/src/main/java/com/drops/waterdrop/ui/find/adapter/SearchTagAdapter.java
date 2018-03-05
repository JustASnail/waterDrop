package com.drops.waterdrop.ui.find.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.netease.nim.uikit.model.HotSearchEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/3.
 */

public class SearchTagAdapter extends TagAdapter<HotSearchEntity.ResultsBean> {
    public SearchTagAdapter(List<HotSearchEntity.ResultsBean> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, HotSearchEntity.ResultsBean entity) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_tag, parent, false);
        TextView tvName = (TextView) inflate.findViewById(R.id.tv_name);
        tvName.setText(entity.getWord());
        return inflate;
    }
}
