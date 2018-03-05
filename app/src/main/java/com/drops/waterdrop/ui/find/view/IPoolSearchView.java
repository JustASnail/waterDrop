package com.drops.waterdrop.ui.find.view;

import com.netease.nim.uikit.model.HotSearchEntity;
import com.netease.nim.uikit.model.SearchPoolEntity;
import com.netease.nim.uikit.model.SearchPostEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/28.
 */

public interface IPoolSearchView {

    void onLoadHotSearchSucceed(List<HotSearchEntity.ResultsBean> list);

    void onLoadHotSearchError();

    void onLoadSearchError(String message);

    void onLoadPoolSearchSucceed(List<SearchPoolEntity.ResultsBean> results);

    void onLoadPostSearchSucceed(List<SearchPostEntity.ResultsBean> results);

    void notifyHistoryList();
}
