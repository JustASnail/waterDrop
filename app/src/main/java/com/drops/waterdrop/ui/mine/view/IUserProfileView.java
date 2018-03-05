package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.FriendDetailEntity;

/**
 * Created by dengxiaolei on 2017/6/29.
 */

public interface IUserProfileView {

    void onGetDataSucceed(FriendDetailEntity entity);

    void onAddFriendSucceed();
}
