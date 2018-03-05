package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.CollectionSBEntry;
import com.netease.nim.uikit.model.CollectionSTEntry;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/2.
 */

public interface ICollectionView {

    void onGetCollectionSTSucceed(List<CollectionSTEntry.ResultsBean> dropTips);

    void onGetBuySTSucceed(List<CollectionSTEntry.ResultsBean> results);

    void setRefresh(boolean b);

    void setRefreshEnable(boolean b);
}
