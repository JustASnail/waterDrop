package com.drops.waterdrop.ui.session.view;

import com.netease.nim.uikit.model.StarListEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/5/15.
 */

public interface IFollowStarView {
    void onGetDataSuccess(List<StarListEntity.FriendsBean> entity);

    void setEnableLoadMore(boolean isEnable);

    void setRefresh(boolean b);
}
