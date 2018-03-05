package com.drops.waterdrop.ui.mine.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.ILogiticsView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.LogiticsEntity;
import com.netease.nim.uikit.model.MyTicketEntity;
import com.netease.nim.uikit.model.OrderEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/8.
 */

public class LogiticsPresenter extends BasePresenter<ILogiticsView> {

    public LogiticsPresenter(BaseActivity context) {
        super(context);
    }

    public void getLogiticsInfo(Long orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, orderId);
        Observable<BaseResponse<LogiticsEntity>> observable = HttpUtil.getInstance().sApi.getLogisticsInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<LogiticsEntity>(mContext) {
            @Override
            protected void _onNext(LogiticsEntity entity) {
                if (entity != null) {
                    getView().onGetLogiticsInfoSuccess(entity);
                } else {
                    ToastUtil.showShort("暂无物流信息");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

}
