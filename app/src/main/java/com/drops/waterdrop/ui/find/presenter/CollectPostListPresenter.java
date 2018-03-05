package com.drops.waterdrop.ui.find.presenter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.find.view.ICollectPostListView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.PostForFriendCollectEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/7/10.
 */

public class CollectPostListPresenter extends BasePresenter<ICollectPostListView> {
    private String mNextSearchStart;
    private int mRealTotalSize;

    private BaseQuickAdapter mLoadMoreAdapter;
    private BaseRequestBody mBody;
    private Map<String, Object> map;

    public CollectPostListPresenter(BaseActivity context) {
        super(context);
    }

    public void getFriendCollectPosts(final boolean isRefresh, final boolean isLoadMore, long friendUid) {
        if (isLoadMore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
            map.put(RequestParams.friend_uid, friendUid);
        }

        Observable<BaseResponse<PostForFriendCollectEntity>> observable = HttpUtil.getInstance().sApi.getFriendCollectPostList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<PostForFriendCollectEntity>(mContext, !isRefresh) {
            @Override
            protected void _onNext(PostForFriendCollectEntity entity) {
                List<PostForFriendCollectEntity.ResultsBean> results = entity.getResults();
                if (results != null && results.size() > 0) {

                    mNextSearchStart = entity.getNextSearchStart();
                    if (isLoadMore) {
                        mRealTotalSize += results.size();
                        mLoadMoreAdapter.addData(results);
                        mLoadMoreAdapter.loadMoreComplete();
                    } else {
                        mRealTotalSize = results.size();
                        mLoadMoreAdapter.setNewData(results);
                    }

                    if (mRealTotalSize >= entity.getTotalSize()) {
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
