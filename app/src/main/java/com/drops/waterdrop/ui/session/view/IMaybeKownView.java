package com.drops.waterdrop.ui.session.view;

import com.netease.nim.uikit.model.RecommendFriendEntity;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/9/12.
 */

public interface IMaybeKownView {
    void onGetRecommendFriendSucceed(List<RecommendFriendEntity.ResultsBean> results);
}
