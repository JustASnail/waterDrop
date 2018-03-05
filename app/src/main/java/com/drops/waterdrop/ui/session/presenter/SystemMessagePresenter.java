package com.drops.waterdrop.ui.session.presenter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.uikit.cache.MyUserCache;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.session.view.ISystemMessageView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.ConfirmedFriendEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/5/17.
 */

public class SystemMessagePresenter extends BasePresenter<ISystemMessageView> {

    private String mNextSearchStart;
    private BaseRequestBody mBody;

    private int mRealTotalSize;

    private BaseQuickAdapter mLoadMoreAdapter;
    private Map<String, Object> map;

    public SystemMessagePresenter(BaseActivity context) {
        super(context);
    }

    public void getNewsFriends(final boolean isRefresh, final boolean isLoadMore) {
        if (isLoadMore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
        }
        Observable<BaseResponse<ConfirmedFriendEntity>> observable = HttpUtil.getInstance().sApi.getNotConfirmedFriends(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<ConfirmedFriendEntity>(mContext, !isRefresh) {
            @Override
            protected void _onNext(ConfirmedFriendEntity confirmedFriendEntity) {
                List<ConfirmedFriendEntity.ResultsBean> results = confirmedFriendEntity.getResults();
                mNextSearchStart = confirmedFriendEntity.getNextSearchStart();
                if (results != null && results.size() > 0) {

                    if (isLoadMore) {
                        mRealTotalSize += results.size();
                        mLoadMoreAdapter.addData(results);
                        mLoadMoreAdapter.loadMoreComplete();
                    } else {
                        mRealTotalSize = results.size();
                        mLoadMoreAdapter.setNewData(results);
                    }


                    if (mRealTotalSize >= confirmedFriendEntity.getTotalSize()) {
                        mLoadMoreAdapter.loadMoreEnd();
                    }

                } else if (isLoadMore) {
                    mLoadMoreAdapter.loadMoreEnd();
                }

                if (isRefresh) {
                    getView().setRefreshing(false);
                    mLoadMoreAdapter.setEnableLoadMore(true);
                }

                if (isLoadMore) {
                    getView().setRefreshEnable(true);
                }


            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                if (isLoadMore) {
                    mLoadMoreAdapter.loadMoreFail();
                    getView().setRefreshEnable(true);

                } else if (isRefresh) {
                    getView().setRefreshing(false);
                    mLoadMoreAdapter.setEnableLoadMore(true);

                }
            }
        });
    }

    public void setLoadMoreAdapter(BaseQuickAdapter loadMoreAdapter) {
        mLoadMoreAdapter = loadMoreAdapter;
    }
}
