package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.PoolListEntity;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/7/4.
 */

public interface IFocusPoolView {

    void onGetDataSuccess(List<PoolListEntity.ResultsBean> results);

    void setEnableLoadMore(boolean b);

    void setRefresh(boolean b);

}
