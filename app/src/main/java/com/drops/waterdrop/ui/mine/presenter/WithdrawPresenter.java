package com.drops.waterdrop.ui.mine.presenter;


import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.IWithdrawView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/8/24.
 */

public class WithdrawPresenter extends BasePresenter<IWithdrawView>{
    public WithdrawPresenter(BaseActivity context) {
        super(context);
    }

    public void withdraw(Long cardId,Double price) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.card_id, cardId);
        map.put(RequestParams.withdraw_price, price);
        map.put(RequestParams.type, MyUserCache.getVipOrSuply());
        final Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.withdraw(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object o) {
                getView().onWithdraw();
                ToastUtil.showShort("提交成功，我们将为您尽快处理！");
            }

            @Override
            protected void _onError(String message) {
                if (message.equals("银行卡信息有误")) {
                    ToastUtil.showShort("小主，该银行卡不存在或已被删除");
                } else {
                    ToastUtil.showShort(message);
                }
            }
        });
    }
}
