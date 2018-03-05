package com.drops.waterdrop.ui.mine.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.ISelectBankAccountView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.SeleBankAccountEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/8/25.
 */

public class SeleBankAccountPresenter extends BasePresenter<ISelectBankAccountView>{
    public SeleBankAccountPresenter(BaseActivity context) {
        super(context);
    }

    public void getBankList(final boolean isRefresh, final boolean isLoadMore) {
        HashMap<String, Object> map = new HashMap<>();
        //1表示成功   0表示未成功  -1表示全部
        map.put(RequestParams.filter, 1);
        map.put(RequestParams.type, MyUserCache.getVipOrSuply());
        Observable<BaseResponse<SeleBankAccountEntity>> observable = HttpUtil.getInstance().sApi.getUserCardList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<SeleBankAccountEntity>(mContext, !isRefresh&&!isLoadMore) {
            @Override
            protected void _onNext(SeleBankAccountEntity entity) {
                List<SeleBankAccountEntity.ResultsBean> results = entity.getResults();

                getView().onGetBindedCardList(results);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}
