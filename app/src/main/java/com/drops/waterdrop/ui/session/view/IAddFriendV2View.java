package com.drops.waterdrop.ui.session.view;

import com.netease.nim.uikit.model.LocalContactEntity;

import java.util.List;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/17 06:51
 */

public interface IAddFriendV2View {

    void setSelected(boolean isRegistered);

    void setData(List<LocalContactEntity> entities);

    boolean hasPermission();

    void showNoPermission();
}
