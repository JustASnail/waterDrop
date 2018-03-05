package com.drops.waterdrop.ui.find.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.find.view.IPoolDetailPageView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.event.PoolAttentionEvent;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.DropDetailsEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/5/23.
 */

public class PoolDetailPagePresenter extends BasePresenter<IPoolDetailPageView> {

    private long mDropId;
    private int mAttentionNum;

    public PoolDetailPagePresenter(BaseActivity context) {
        super(context);
    }

    public void getData(long dropId) {
        mDropId = dropId;
        HashMap<String, Object> map = new HashMap<>();
        map.put("drop_id", dropId);
        Observable<BaseResponse<DropDetailsEntity>> observable = HttpUtil.getInstance().sApi.getPoolDetals(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<DropDetailsEntity>(mContext) {
            @Override
            protected void _onNext(DropDetailsEntity entity) {
                getView().onGetDataSucceed(entity);
                mAttentionNum = entity.getAttentionNum();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                mContext.finish();
            }
        });
    }

    public void attentionPool(long dropId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, dropId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.attentionPool(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object entity) {
                getView().onAttentionSucceed();
                PoolAttentionEvent event = new PoolAttentionEvent();
                event.setAttention(true);
                event.setDropId(mDropId);
                mAttentionNum = mAttentionNum + 1;
                event.setAttentionNum(mAttentionNum);
                EventBus.getDefault().post(event);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    public void cancelAttention(long dropId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, dropId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.cancelAttentionPool(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object entity) {
                getView().onCancelAttentionSucceed();

                PoolAttentionEvent event = new PoolAttentionEvent();
                event.setAttention(false);
                event.setDropId(mDropId);
                mAttentionNum = mAttentionNum -1;
                event.setAttentionNum(mAttentionNum);
                EventBus.getDefault().post(event);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}
