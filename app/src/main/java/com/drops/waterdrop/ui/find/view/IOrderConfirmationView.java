package com.drops.waterdrop.ui.find.view;

import com.netease.nim.uikit.model.AddressEntity;

/**
 * Created by dengxiaolei on 2017/6/8.
 */

public interface IOrderConfirmationView {
    void onNoAddress();

    void setAddressView(AddressEntity.ResultsBean entity);

    void setIntegralViewHidden();

    void setIntegralViewShow();
}
