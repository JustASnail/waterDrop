package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.MyTicketEntity;

import java.util.List;

/**
 * Created by HZH on 2017/7/5.
 */

public interface IMyTicketView {
    void onGetTicketsSuccess(List<MyTicketEntity.ResultsBean> resultsBean);
}
