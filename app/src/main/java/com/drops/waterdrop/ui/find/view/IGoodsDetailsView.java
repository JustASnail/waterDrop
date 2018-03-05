package com.drops.waterdrop.ui.find.view;

import com.netease.nim.uikit.model.GoodsDetailEntity;

/**
 * Created by dengxiaolei on 2017/6/7.
 */

public interface IGoodsDetailsView {
    void updateUI();

    void onLoadGoodsDetailSucceed(GoodsDetailEntity o);

    void setBuyButtonEnable(boolean enable);
}
