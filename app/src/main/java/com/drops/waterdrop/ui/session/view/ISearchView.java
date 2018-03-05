package com.drops.waterdrop.ui.session.view;

import com.netease.nim.uikit.model.AddFriendForUid;
import com.netease.nim.uikit.model.SearchFriendEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/4/26.
 */

public interface ISearchView {
    void onShowProgress();

    void onDismissProgress();

    void onQueryFailed();

    void onQuerySuccess(AddFriendForUid user);

    void onQueryResultNull();

    void onSearchSuccess(List<SearchFriendEntity.ResultsBean> searchFriendEntity);
}
