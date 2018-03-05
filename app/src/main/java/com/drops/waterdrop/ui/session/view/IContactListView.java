package com.drops.waterdrop.ui.session.view;

import com.netease.nim.uikit.model.MyFriendEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/5/11.
 */

public interface IContactListView {
    void onGetFriendsSucceed(List<MyFriendEntity> friends);

    void initAdapter();

    void setUnreadCount(int unread);
}
