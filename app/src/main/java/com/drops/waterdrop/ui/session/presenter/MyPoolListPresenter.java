package com.drops.waterdrop.ui.session.presenter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.session.view.IMyPoolListView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.PoolListEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.netease.nimlib.sdk.team.model.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by HZH on 2017/7/5.
 */

public class MyPoolListPresenter extends BasePresenter<IMyPoolListView> {

    public int mCount;

    private String mNextSearchStart;
    private int mRealTotalSize;

    private BaseQuickAdapter mLoadMoreAdapter;
    private BaseRequestBody mBody;
    private Map<String, Object> map;
    private Map<String, Object> map1;
    private Map<String, Object> map2;


    public MyPoolListPresenter(BaseActivity context) {
        super(context);
    }

    public void registerTeamUpdateObserver(boolean register) {
        if (register) {
            TeamDataCache.getInstance().registerTeamDataChangedObserver(teamDataChangedObserver);
        } else {
            TeamDataCache.getInstance().unregisterTeamDataChangedObserver(teamDataChangedObserver);
        }
    }

    TeamDataCache.TeamDataChangedObserver teamDataChangedObserver = new TeamDataCache.TeamDataChangedObserver() {
        @Override
        public void onUpdateTeams(List<Team> teams) {
//            getPoolListDatas(false);
        }

        @Override
        public void onRemoveTeam(Team team) {
//            getPoolListDatas(false);

        }
    };


    public void getPoolListDatas(final boolean isRefresh, final boolean isLoadMore) {
        if (isLoadMore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
        }
        Observable<BaseResponse<PoolListEntity>> observable = HttpUtil.getInstance().sApi.getCreatePoolList(RequestBodyUtils.build(map));
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

    /**
     * 好友关注的水塘列表
     *
     * @param isRefresh
     * @param friendUid
     */
    public void getFriendFocusPool(final boolean isRefresh, final boolean isLoadMore, Long friendUid) {
        if (isLoadMore) {
            map1.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map1 = new HashMap<>();
            map1.put(RequestParams.friend_uid, friendUid);
        }

        Observable<BaseResponse<PoolListEntity>> observable = HttpUtil.getInstance().sApi.getFriendAttentionPoolList(RequestBodyUtils.build(map1));
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

    /**
     * 好友创建的的水塘列表
     *
     * @param isRefresh
     * @param friendUid
     */
    public void getFriendCreatePool(final boolean isRefresh, final boolean isLoadMore, Long friendUid) {
        if (isLoadMore) {
            map2.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map2 = new HashMap<>();
            map2.put(RequestParams.friend_uid, friendUid);
        }

        Observable<BaseResponse<PoolListEntity>> observable = HttpUtil.getInstance().sApi.getFriendCreatePoolList(RequestBodyUtils.build(map2));
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

    public void setLoadMoreAdapter(BaseQuickAdapter loadMoreAdapter) {
        mLoadMoreAdapter = loadMoreAdapter;
    }
}
