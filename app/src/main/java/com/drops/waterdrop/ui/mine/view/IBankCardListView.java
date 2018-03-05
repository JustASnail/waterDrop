package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.BankCardListEntity;

import java.util.List;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/29 22:58
 */

public interface IBankCardListView {

    void onResponse(List<BankCardListEntity.BankCard> bankCards);
    void onDelBankCardSuccess();
}
