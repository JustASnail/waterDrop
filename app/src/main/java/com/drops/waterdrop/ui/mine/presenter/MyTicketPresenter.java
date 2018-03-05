package com.drops.waterdrop.ui.mine.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.IMyTicketView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.MyTicketEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by HZH on 2017/7/5.
 */

public class MyTicketPresenter extends BasePresenter<IMyTicketView> {
    public MyTicketPresenter(BaseActivity context) {
        super(context);
    }

    public void getTicketList() {
        Map<String, Object> map = new HashMap<>();
        Observable<BaseResponse<MyTicketEntity>> observable = HttpUtil.getInstance().sApi.getMyTickets(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<MyTicketEntity>(mContext) {
            @Override
            protected void _onNext(MyTicketEntity resultEntity) {
                getView().onGetTicketsSuccess(resultEntity.getResults());
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}

