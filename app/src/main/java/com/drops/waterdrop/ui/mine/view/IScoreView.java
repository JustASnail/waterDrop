package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.OrderEntity;

/**
 * Created by dengxiaolei on 2017/6/6.
 */

public interface IScoreView {
    void getOnParseIntent(OrderEntity.ResultsBean.GoodsBean orderEntity);

    void onCommitScoreSuccess();

}
