package com.drops.waterdrop.ui.mine.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.adapter.FootprintShuiTangAdapter;
import com.drops.waterdrop.ui.mine.adapter.FootprintShuiTieAdapter;
import com.drops.waterdrop.ui.mine.view.IFootprintView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.FootprintShuiTangEntity;
import com.netease.nim.uikit.model.FootprintShuiTieEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/3.
 */

public class FootprintPresenter extends BasePresenter<IFootprintView> {
    private String mNextSearchStart;
    private int mRealTotalSize;
    private FootprintShuiTieAdapter mLoadMorePostAdapter;
    private FootprintShuiTangAdapter mLoadMorePoolAdapter;
    private Map<String, Object> map;
    private Map<String, Object> map1;

    public FootprintPresenter(BaseActivity context) {
        super(context);
    }

    public void getShuiTangList(final boolean isRefresh,final boolean isLoadMore) {
        if (isLoadMore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
        }
        Observable<BaseResponse<FootprintShuiTangEntity>> observable = HttpUtil.getInstance().sApi.getFootprintShuiTangList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<FootprintShuiTangEntity>(mContext,!isLoadMore && !isRefresh) {
            @Override
            protected void _onNext(FootprintShuiTangEntity entity) {
                List<FootprintShuiTangEntity.ResultsBean> results = entity.getResults();
                if (results != null && results.size() > 0) {
                    mNextSearchStart = entity.getNextSearchStart();
                    if (isLoadMore) {
                        mRealTotalSize += results.size();
                        mLoadMorePoolAdapter.addData(results);
                        mLoadMorePoolAdapter.loadMoreComplete();
                    } else {
                        mRealTotalSize = results.size();
                        mLoadMorePoolAdapter.setNewData(results);
                    }

                    if (mRealTotalSize >= entity.getTotalSize()) {
                        mLoadMorePoolAdapter.loadMoreEnd();
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
            }
        });
    }

    public void getShuiTieList(final boolean isRefresh,final boolean isLoadMore) {
        if (isLoadMore) {
            map1.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map1 = new HashMap<>();
        }
        Observable<BaseResponse<FootprintShuiTieEntity>> observable = HttpUtil.getInstance().sApi.getFootprintShuiTieList(RequestBodyUtils.build(map1));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<FootprintShuiTieEntity>(mContext,!isLoadMore && !isRefresh) {
            @Override
            protected void _onNext(FootprintShuiTieEntity entity) {
                List<FootprintShuiTieEntity.ResultsBean> results = entity.getResults();
                if (results != null && results.size() > 0) {
                    mNextSearchStart = entity.getNextSearchStart();
                    if (isLoadMore) {
                        mRealTotalSize += results.size();
                        mLoadMorePostAdapter.addData(results);
                        mLoadMorePostAdapter.loadMoreComplete();
                    } else {
                        mRealTotalSize = results.size();
                        mLoadMorePostAdapter.setNewData(results);
                    }

                    if (mRealTotalSize >= entity.getTotalSize()) {
                        mLoadMorePostAdapter.loadMoreEnd();
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
                    mLoadMorePostAdapter.loadMoreFail();
                } else if (isRefresh){
                    getView().setRefresh(false);

                }
            }
        });
    }

    public void cleanFootprintByType(final int type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.footprint_type, type);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.cleanFootprint(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object resultEntity) {
                getView().onCleanFootprintSuccess(type);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    public void setLoadMoreAdapter(FootprintShuiTieAdapter mAdapter) {
        mLoadMorePostAdapter = mAdapter;
    }

    public void setPoolLoadMoreAdapter(FootprintShuiTangAdapter mAdapter) {
        mLoadMorePoolAdapter = mAdapter;
    }
}
