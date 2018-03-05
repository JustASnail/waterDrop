package com.drops.waterdrop.ui.mine.presenter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.adapter.JinDouListAdapter;
import com.drops.waterdrop.ui.mine.view.IGoldenBeanView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.JinDouEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/6/26.
 */

public class GoldenBeanPresenter extends BasePresenter<IGoldenBeanView> {

    private BaseQuickAdapter mLoadMoreAdapter;
    private String mNextSearchStart;
    private int mRealTotalSize;
    private Map<String, Object> map;

    public GoldenBeanPresenter(BaseActivity context) {
        super(context);
    }

    public void getJinDouList(final boolean isRefresh, final boolean isLoadMore) {
        if (isLoadMore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
        }

        Observable<BaseResponse<JinDouEntity>> observable = HttpUtil.getInstance().sApi.getJinDouList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<JinDouEntity>(mContext, !isLoadMore && !isRefresh) {
            @Override
            protected void _onNext(JinDouEntity dataBean) {
                getView().onGetJinDouSuccess(dataBean.getTotalBeans());
                List<JinDouEntity.ResultsBean> results = dataBean.getResults();
                if (results != null && results.size() > 0) {
                    mNextSearchStart = dataBean.getNextSearchStart();
                    if (isLoadMore) {
                        mRealTotalSize += results.size();
                        mLoadMoreAdapter.addData(results);
                        mLoadMoreAdapter.loadMoreComplete();
                    } else {
                        mRealTotalSize = results.size();
                        mLoadMoreAdapter.setNewData(results);
                    }

                    if (mRealTotalSize >= dataBean.getTotalSize()) {
                        mLoadMoreAdapter.loadMoreEnd();
                    }
                } else if (isLoadMore) {
                    mLoadMoreAdapter.loadMoreEnd();
                }

                if (isRefresh) {
                    getView().setRefreshing(false);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                if (isLoadMore) {
                    mLoadMoreAdapter.loadMoreFail();
                } else if (isRefresh) {
                    getView().setRefreshing(false);

                }
            }
        });
    }

    public void setLoadMoreAdapter(JinDouListAdapter mAdapter) {
        mLoadMoreAdapter = mAdapter;
    }

}
