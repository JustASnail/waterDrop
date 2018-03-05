package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.AddressEntity;

import java.util.List;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/10/12 14:49
 */

public interface SelectAddressForSendVrView {

    void onGetAddress(List<AddressEntity.ResultsBean> results);
    void onNoAddress();
    void onSendSucc();
}
