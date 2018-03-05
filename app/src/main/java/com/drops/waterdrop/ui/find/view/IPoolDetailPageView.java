package com.drops.waterdrop.ui.find.view;

import com.netease.nim.uikit.model.DropDetailsEntity;

/**
 * Created by dengxiaolei on 2017/5/23.
 */

public interface IPoolDetailPageView {
    void onGetDataSucceed(DropDetailsEntity entity);

    void onAttentionSucceed();

    void onCancelAttentionSucceed();
}
