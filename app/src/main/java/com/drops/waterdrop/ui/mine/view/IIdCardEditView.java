package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.IdCardEntity;

/**
 * Created by dengxiaolei on 2017/9/8.
 */

public interface IIdCardEditView {
    void updateUIForUserName(IdCardEntity idCardEntity);

    void onUpdateSucceed(AddressEntity.ResultsBean idCardEntity);
}
