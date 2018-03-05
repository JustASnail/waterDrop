package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.AddressEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/2.
 */

public interface IAddressManageView {
    void onGetAddressSucceed(List<AddressEntity.ResultsBean> addressEntity);
}
