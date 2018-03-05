package com.drops.waterdrop.ui.store.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.store.adapter.BrandItemAdapter;
import com.drops.waterdrop.ui.store.view.IBrandItemView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.BrandItemEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/9/14.
 */

public class BrandItemPresenter extends BasePresenter<IBrandItemView>{
    public BrandItemPresenter(BaseActivity context) {
        super(context);
    }

    private Map<String, Object> map;
    private String mNextSearchStart;
    private int mRealTotalSize;
    private BrandItemAdapter mLoadMoreAdapter;

    public void getData(long brandId,final boolean isRefresh, final boolean isLoadmore) {
        if (isLoadmore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
            map.put(RequestParams.country_code, "86");
            map.put(RequestParams.brand_id, brandId);
        }

        Observable<BaseResponse<BrandItemEntity>> observable = HttpUtil.getInstance().sApi.getItemBrandData(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<BrandItemEntity>(mContext, !isLoadmore && !isRefresh) {

            @Override
            protected void _onNext(BrandItemEntity entity) {
                List<BrandItemEntity.ResultsBean> results = entity.getResults();
                if (results != null && results.size() > 0) {
                    mNextSearchStart = entity.getNextSearchStart();
                    if (isLoadmore) {
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

                if (isLoadmore) {
                    getView(). setRefreshEnable(true);
                }

                if (isRefresh) {
                    getView().setRefresh(false);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                if (isLoadmore) {
                    mLoadMoreAdapter.loadMoreFail();
                } else if (isRefresh){
                    getView().setRefresh(false);
                }
            }
        });
    }

    public void setLoadMoreAdapter(BrandItemAdapter mAdapter) {
        mLoadMoreAdapter = mAdapter;
    }
}
