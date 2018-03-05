package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.MyTicketEntity;

/**
 * Created by dengxiaolei on 2017/6/4.
 */

public interface IGetTicketView {
    void onIntent(boolean isGet, MyTicketEntity.ResultsBean entity);

    void onAddressPicker(String pron, String city, String distinct);

    void onReceiveTicketSuccess();
}
