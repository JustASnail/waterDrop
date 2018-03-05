package com.drops.waterdrop.ui.find.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.VideoModel;
import com.drops.waterdrop.video.VideoItemBaseHolder;
import com.netease.nim.uikit.common.util.GlideUtil;


/**
 * Created by GUO on 2015/12/3.
 */
public class RecyclerItemViewHolder extends VideoItemBaseHolder {

    public final static String TAG = "RecyclerView2List";

    protected Context context = null;

    FrameLayout listItemContainer;
    ImageView listItemBtn;
    ImageView imageView;


    public RecyclerItemViewHolder(Context context, View v) {
        super(v);
        this.context = context;
        imageView = new ImageView(context);
        listItemContainer = (FrameLayout) v.findViewById(R.id.list_item_container);
        listItemBtn = (ImageView) v.findViewById(R.id.list_item_btn);
    }

    public void onBind(final int position, final VideoModel videoModel) {

        //增加封面
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideUtil.showImageView(context, videoModel.getImgUrl(), imageView);

        listVideoUtil.addVideoPlayer(position, imageView, TAG, listItemContainer, listItemBtn);
        listItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecyclerBaseAdapter().notifyDataSetChanged();
                //listVideoUtil.setLoop(true);
                listVideoUtil.setPlayPositionAndTag(position, TAG);
                final String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
                //listVideoUtil.setCachePath(new File(FileUtils.getPath()));
                listVideoUtil.startPlay(videoModel.getVideoUrl());
            }
        });
    }

}





