package com.drops.waterdrop.ui.mine.presenter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.adapter.FocusPoolAdapter;
import com.drops.waterdrop.ui.mine.view.IFocusPoolView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.PoolListEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/4.
 */

public class FocusPoolPresenter extends BasePresenter<IFocusPoolView>{

    private BaseQuickAdapter mLoadMoreAdapter;
    private String mNextSearchStart;
    private int mRealTotalSize;
    private HashMap<String, Object> map;

    public FocusPoolPresenter(BaseActivity context) {
        super(context);
    }

    public void getFocusPoolList(final boolean isRefresh,final boolean isLoadMore) {
        if (isLoadMore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
        }
        Observable<BaseResponse<PoolListEntity>> observable = HttpUtil.getInstance().sApi.getFocusPoolList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<PoolListEntity>(mContext, !isRefresh) {
            @Override
            protected void _onNext(PoolListEntity entity) {
                List<PoolListEntity.ResultsBean> results = entity.getResults();
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

    public void setLoadMoreAdapter(FocusPoolAdapter mAdapter) {
        mLoadMoreAdapter = mAdapter;
    }

}
