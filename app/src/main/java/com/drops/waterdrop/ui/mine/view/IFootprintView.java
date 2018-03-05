package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.FootprintShuiTangEntity;
import com.netease.nim.uikit.model.FootprintShuiTieEntity;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/7/3.
 */

public interface IFootprintView {
    void onGetFootprintShuiTieSucceed(List<FootprintShuiTieEntity.ResultsBean> resultsBeen);

    void onGetFootprintShuiTangSucceed(List<FootprintShuiTangEntity.ResultsBean> resultsBeen);

    void onCleanFootprintSuccess(int type);

    void setRefresh(boolean b);

    void setRefreshEnable(boolean b);
}
