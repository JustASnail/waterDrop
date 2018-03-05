package com.drops.waterdrop.ui.mine.presenter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.adapter.MyPostListAdapter;
import com.drops.waterdrop.ui.mine.view.IMyPostListView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.MyPostEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/3.
 */

public class MyPostListPresenter extends BasePresenter<IMyPostListView> {
    public int mCount;
    private BaseQuickAdapter mLoadMoreAdapter;
//    private BaseRequestBody mBody;
    private String mNextSearchStart;
    private int mRealTotalSize;
    private Map<String, Object> map;


    public MyPostListPresenter(BaseActivity context) {
        super(context);
    }

    public void getPostList(final boolean isRefresh,final boolean isLoadMore) {
        if (isLoadMore) {
//            mBody.setSearch_start(mNextSearchStart);
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
        }
        Observable<BaseResponse<MyPostEntity>> observable = HttpUtil.getInstance().sApi.getCreatePostList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<MyPostEntity>(mContext, !isRefresh) {
            @Override
            protected void _onNext(MyPostEntity entity) {
                List<MyPostEntity.ResultsBean> results = entity.getResults();
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
                } else if (isRefresh){
                    getView().setRefresh(false);

                }
            }
        });
    }

    public void setLoadMoreAdapter(MyPostListAdapter mAdapter) {
        mLoadMoreAdapter = mAdapter;
    }
}
