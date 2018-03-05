package com.drops.waterdrop.ui.mine.presenter;

import android.content.Intent;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.activity.OrderDetailActivity;
import com.drops.waterdrop.ui.mine.view.IScoreView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.OrderEntity;
import com.netease.nim.uikit.model.PoolListEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/6/6.
 */

public class ScorePresenter extends BasePresenter<IScoreView> {

    private OrderEntity.ResultsBean.GoodsBean mOrderEntity;


    public ScorePresenter(BaseActivity context) {
        super(context);
    }

    public void parseIntent(Intent intent) {
        mOrderEntity = (OrderEntity.ResultsBean.GoodsBean) intent.getSerializableExtra(OrderDetailActivity.EXTRA_ORDER_ENTITY);
        getView().getOnParseIntent(mOrderEntity);
    }

    public void commitRating(int score, Long orderId, String goodId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.score, score);
        map.put(RequestParams.order_id, orderId);
        map.put(RequestParams.good_id, goodId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.markScore(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object entity) {
                getView().onCommitScoreSuccess();
                ToastUtil.showShort("评分成功");
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                getView().onCommitScoreSuccess();
            }
        });
    }
}
