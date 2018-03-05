package com.drops.waterdrop.ui.find.view;

import com.netease.nim.uikit.model.StarListEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/10.
 */

public interface IStarListView {
    void onGetDataSuccess(List<StarListEntity.FriendsBean> entity);

    void setEnableLoadMore(boolean isEnable);

    void setRefresh(boolean b);
}
