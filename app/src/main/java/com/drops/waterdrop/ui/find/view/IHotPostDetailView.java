package com.drops.waterdrop.ui.find.view;

import com.netease.nim.uikit.model.PostEntity;

/**
 * Created by dengxiaolei on 2017/5/25.
 */

public interface IHotPostDetailView {
    void onGetDataSucceed(PostEntity entity);

    void insertCollectSucceed();

    void deleteCollectSucceed();
}
