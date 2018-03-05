package com.drops.waterdrop.ui.find.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.find.view.IPoolListFragmentView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.StarListEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/6/27.
 */

public class PoolListFragmentPresenter extends BasePresenter<IPoolListFragmentView> {

    public int mCount = 0;


    public PoolListFragmentPresenter(BaseActivity context) {
        super(context);
    }

    public void getData(final boolean isRefresh) {
        Map<String, Object> map = new HashMap<>();

        Observable<BaseResponse<StarListEntity>> observable = HttpUtil.getInstance().sApi.getSuperStars(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<StarListEntity>(mContext, !isRefresh) {


            @Override
            protected void _onNext(StarListEntity entity) {
                mCount = entity.getPageSize();
                getView().onGetDataSucceed(entity, isRefresh);

            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                getView().onGetDataError(isRefresh);
            }
        });
    }
}
