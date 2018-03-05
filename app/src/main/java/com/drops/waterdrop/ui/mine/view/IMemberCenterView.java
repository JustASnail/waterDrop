package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.MemberActiveEntitiy;
import com.netease.nim.uikit.model.UserCenterEntity;
import com.netease.nim.uikit.model.VipAreaEntity;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/19 13:20
 */

public interface IMemberCenterView {

    void setMemberInfo(MemberActiveEntitiy entity);

    void setRefresh(boolean b);

    void onGetActiveData(VipAreaEntity.BannerBean banner);

    void setRefreshEnable(boolean b);
}
