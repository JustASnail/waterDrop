package com.drops.waterdrop.ui.mine.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.adapter.CollectionListAdapter;
import com.drops.waterdrop.ui.mine.view.ICollectionView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.CollectionSTEntry;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Mr.Smile
 */

public class MyCollectionPresenter extends BasePresenter<ICollectionView> {

    private String mNextSearchStart;
    private int mRealTotalSize;
    private CollectionListAdapter mLoadMoreAdapter;
    private Map<String, Object> map;

    public MyCollectionPresenter(BaseActivity context) {
        super(context);
    }

    //收藏水帖列表
    public void getCollectPostList(final boolean isRefresh,final boolean isLoadMore) {
        if (isLoadMore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
        }
        Observable<BaseResponse<CollectionSTEntry>> observable = HttpUtil.getInstance().sApi.getCollectionSTList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<CollectionSTEntry>(mContext, !isLoadMore && !isRefresh) {
            @Override
            protected void _onNext(CollectionSTEntry entity) {
                List<CollectionSTEntry.ResultsBean> results = entity.getResults();
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
                        getView().setRefreshEnable(true);
                    }
                }

                if (isLoadMore) {
                    getView().setRefreshEnable(true);
                }

                if (isRefresh) {
                    getView().setRefresh(false);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                if (isLoadMore) {
                    mLoadMoreAdapter.loadMoreFail();
                } else if (isRefresh) {
                    getView().setRefresh(false);

                }
            }
        });
    }
    public void setLoadMoreAdapter(CollectionListAdapter mAdapter) {
        mLoadMoreAdapter = mAdapter;
    }
}
