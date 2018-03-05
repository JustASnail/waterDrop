package com.drops.waterdrop.video;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

/**
 * Created by shuyu on 2016/12/3.
 */

public class VideoItemBaseHolder extends RecyclerView.ViewHolder {

    RecyclerView.Adapter recyclerBaseAdapter;

    public ListVideoUtil listVideoUtil;

    public VideoItemBaseHolder(View itemView) {
        super(itemView);
    }

    public RecyclerView.Adapter getRecyclerBaseAdapter() {
        return recyclerBaseAdapter;
    }

    public void setRecyclerBaseAdapter(RecyclerView.Adapter recyclerBaseAdapter) {
        this.recyclerBaseAdapter = recyclerBaseAdapter;
    }

    public ListVideoUtil getListVideoUtil() {
        return listVideoUtil;
    }

    public void setListVideoUtil(ListVideoUtil listVideoUtil) {
        this.listVideoUtil = listVideoUtil;
    }
}
