package com.drops.waterdrop.ui.session.view;

import com.netease.nim.uikit.model.LocalContactEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/13.
 */

public interface IAddFriendView {
    void setContactsList(List<LocalContactEntity> list);


    void showLoading();

    void setNoContacts();

    void dissMissLoading();
}
