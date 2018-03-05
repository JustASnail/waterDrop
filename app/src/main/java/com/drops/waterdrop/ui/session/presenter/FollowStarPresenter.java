package com.drops.waterdrop.ui.session.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.session.view.IFollowStarView;
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
 * Created by dengxiaolei on 2017/5/15.
 */

public class FollowStarPresenter extends BasePresenter<IFollowStarView> {

    public int mCount;

    public FollowStarPresenter(BaseActivity context) {
        super(context);
    }

    public void getStarDatas(final boolean isRefresh) {
        Map<String, Object> map = new HashMap<>();
        Observable<BaseResponse<StarListEntity>> observable = HttpUtil.getInstance().sApi.getSuperStars(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<StarListEntity>(mContext, !isRefresh) {
            @Override
            protected void _onNext(StarListEntity entity) {
                getView().onGetDataSuccess(entity.getFriends());
                mCount = entity.getPageSize();
                if (isRefresh) {
                    getView().setEnableLoadMore(true);
                    getView().setRefresh(false);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                if (isRefresh) {
                    getView().setEnableLoadMore(true);
                    getView().setRefresh(false);
                }
            }
        });
    }
}
