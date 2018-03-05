package com.drops.waterdrop.ui.mine.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.IBankCardListView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BankCardListEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/29 22:56
 */

public class BankCardListPresenter extends BasePresenter<IBankCardListView> {

    public BankCardListPresenter(BaseActivity context) {
        super(context);
    }

    public void getBankCards(){
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.type, MyUserCache.getVipOrSuply());
        Observable<BaseResponse<BankCardListEntity>> observable = HttpUtil.getInstance().sApi.getBankCards(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<BankCardListEntity>(mContext, true) {
            @Override
            protected void _onNext(BankCardListEntity bankCardListEntity) {
                List<BankCardListEntity.BankCard> bankCards = bankCardListEntity.getResults();
                if (bankCards != null){
                    getView().onResponse(bankCards);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showLong(message);
            }
        });
    }

    public void delBankCard(final long cardid){
        Map<String, Object> map = new HashMap<>();
        map.put("card_id", cardid);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.delBankCard(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, true) {
            @Override
            protected void _onNext(Object o) {
                getView().onDelBankCardSuccess();
                long cardId = MyUserCache.getCardId();
                if (cardId == cardid) {
                    MyUserCache.saveBankNum("");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showLong(message);
            }
        });
    }
}
