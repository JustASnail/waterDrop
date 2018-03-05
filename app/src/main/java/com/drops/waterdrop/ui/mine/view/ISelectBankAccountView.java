package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.SeleBankAccountEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Smile on 2017/8/25.
 */

public interface ISelectBankAccountView {
    void onGetBindedCardList(List<SeleBankAccountEntity.ResultsBean> bindedCardList);
}
