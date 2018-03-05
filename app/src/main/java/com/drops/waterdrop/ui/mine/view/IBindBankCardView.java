package com.drops.waterdrop.ui.mine.view;

import com.netease.nim.uikit.model.BankCardNoListEntity;

import java.util.List;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/29 23:58
 */

public interface IBindBankCardView {

    void onSubmitSuccess();
    void onGetBankCardNos(List<BankCardNoListEntity.BankCardNo> bankCardNos);
    void onGetQiNiuImageKey(String url, String key);

}
