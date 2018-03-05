package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.IdCardEntity;

/**
 * Created by dengxiaolei on 2017/6/2.
 */

public interface IAddAddressView {

    void onCityPicked(String prov, String city, String district);

    void onSaveAddressSucceed();

    void initEditUI(AddressEntity.ResultsBean address);

    void initAddUI();

    void updateUIForUserName(IdCardEntity idCardEntity);
}
