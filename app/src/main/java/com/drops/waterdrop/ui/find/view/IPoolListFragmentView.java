package com.drops.waterdrop.ui.find.view;

import com.netease.nim.uikit.model.StarListEntity;

/**
 * Created by dengxiaolei on 2017/6/27.
 */

public interface IPoolListFragmentView {
    void onGetDataSucceed(StarListEntity entity, boolean isRefresh);

    void onGetDataError(boolean isRefresh);
}
