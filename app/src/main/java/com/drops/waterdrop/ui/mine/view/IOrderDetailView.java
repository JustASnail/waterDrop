package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.LogiticsEntity;
import com.netease.nim.uikit.model.OrderDetailEntity2;

/**
 * Created by HZH on 2017/6/29.
 */

public interface IOrderDetailView {
    void getOnParseIntent(OrderDetailEntity2 orderEntity);

    void setNoPaymentTypeView();

    void setNoDispatchTypeView();

    void setNoReceiveTypeView();

    void setFinishTypeView();

    void setExchangeType();

    void cancelOrderSuccess();

    void setUncommentTypeView();

    void confirmReceivedSuccess();

    void checkPayedSuccess();

    void setCancelType();

    void onGetLogiticsInfoSuccess(LogiticsEntity entity);
}
