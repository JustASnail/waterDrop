package com.drops.waterdrop.ui.session.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.session.view.IRecommendFriendView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.RecommendFriendEntity;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/9/12.
 */

public class RecommendFriendPresenter extends BasePresenter<IRecommendFriendView>{
    public RecommendFriendPresenter(BaseActivity context) {
        super(context);
    }

    public void getRecommendFriendData() {

        Observable<BaseResponse<RecommendFriendEntity>> ob = HttpUtil.getInstance().sApi.getRecommendFriend(RequestBodyUtils.build(null));
        HttpUtil.getInstance().execute(ob, new ProgressSubscriber<RecommendFriendEntity>(mContext) {
            @Override
            protected void _onNext(RecommendFriendEntity recommendFriendEntity) {
                if (recommendFriendEntity != null && recommendFriendEntity.getResults() != null) {
                    getView().onGetRecommendFriendSucceed(recommendFriendEntity.getResults());
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}
