package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.OrderEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/4.
 */

public interface IAllOrderListView {
    void onGetOrderSucceed(List<OrderEntity.ResultsBean> ordersBeen);

    void setRefresh(boolean b);

    void setRefreshEnable(boolean b);
}