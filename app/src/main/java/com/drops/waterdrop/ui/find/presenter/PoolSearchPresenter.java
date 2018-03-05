package com.drops.waterdrop.ui.find.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.find.view.IPoolSearchView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.gen.SearchPoolHistoryDBDao;
import com.netease.nim.uikit.gen.SearchPostHistoryDBDao;
import com.netease.nim.uikit.green_dao.GreenDaoManager;
import com.netease.nim.uikit.green_dao.SearchPoolHistoryDB;
import com.netease.nim.uikit.green_dao.SearchPostHistoryDB;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.HotSearchEntity;
import com.netease.nim.uikit.model.SearchHistoryEntity;
import com.netease.nim.uikit.model.SearchPoolEntity;
import com.netease.nim.uikit.model.SearchPostEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/6/28.
 */

public class PoolSearchPresenter extends BasePresenter<IPoolSearchView> {

    public int mPageMode = Constants.POOL_SEARCH_MODE;
    public String mNextSearchStart;
    public int mPageSize;
    public int mTotalSize;
    public int mRealSize = 0;
    private String mPreWord;

    private BaseQuickAdapter loadMoreAdapter;

    public PoolSearchPresenter(BaseActivity context) {
        super(context);
    }


    public void parseIntent(Intent intent) {
        mPageMode = intent.getIntExtra(Constants.EXTRA_PAGE_MODE, Constants.POOL_SEARCH_MODE);
    }

    public void getHotSearch() {
        if (mPageMode == Constants.POST_SEARCH_MODE) {
            getPostHotSearch();
        } else {
            getPoolHotSearch();

        }
    }

    private void getPoolHotSearch() {
//        BaseRequestBody body = new BaseRequestBody();

        Map<String, Object> map = new HashMap<>();
        Observable<BaseResponse<HotSearchEntity>> observable = HttpUtil.getInstance().sApi.getHotSearchForPool(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<HotSearchEntity>(mContext) {


            @Override
            protected void _onNext(HotSearchEntity entity) {
                List<HotSearchEntity.ResultsBean> results = entity.getResults();
                if (results != null && results.size() > 0) {
                    getView().onLoadHotSearchSucceed(results);
                } else {
                    getView().onLoadHotSearchError();
                }
            }

            @Override
            protected void _onError(String message) {
                getView().onLoadHotSearchError();

            }
        });
    }

    private void getPostHotSearch() {
//        BaseRequestBody body = new BaseRequestBody();

        Map<String, Object> map = new HashMap<>();
        Observable<BaseResponse<HotSearchEntity>> observable = HttpUtil.getInstance().sApi.getHotSearchForPost(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<HotSearchEntity>(mContext) {


            @Override
            protected void _onNext(HotSearchEntity entity) {
                List<HotSearchEntity.ResultsBean> results = entity.getResults();
                if (results != null && results.size() > 0) {
                    getView().onLoadHotSearchSucceed(results);
                } else {
                    getView().onLoadHotSearchError();
                }
            }

            @Override
            protected void _onError(String message) {
                getView().onLoadHotSearchError();

            }
        });
    }


    public void search(String word, boolean isLoadMore) {
        if (mPageMode == Constants.POST_SEARCH_MODE) {
            searchForPost(word, isLoadMore);
        } else {
            searchForPool(word, isLoadMore);
        }
    }


    private void searchForPool(final String word, final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<>();
        if (!isLoadMore) {
            mPreWord = word;
            mNextSearchStart = "";
        }

        map.put(RequestParams.search_word, mPreWord);
        map.put(RequestParams.search_start, mNextSearchStart);
        Observable<BaseResponse<SearchPoolEntity>> observable = HttpUtil.getInstance().sApi.getSearchForPool(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<SearchPoolEntity>(mContext, !isLoadMore) {

            @Override
            protected void _onNext(SearchPoolEntity entity) {
                List<SearchPoolEntity.ResultsBean> results = entity.getResults();
                if (results != null) {
                    mNextSearchStart = entity.getNextSearchStart();
                    if (isLoadMore) {
                        mTotalSize += results.size();
                        loadMoreAdapter.addData(results);
                        loadMoreAdapter.loadMoreComplete();
                    } else {
                        mTotalSize = results.size();
                        getView().onLoadPoolSearchSucceed(results);
                    }

                    if (mTotalSize >= entity.getTotalSize()) {
                        loadMoreAdapter.loadMoreEnd();
                    }


                } else {
                    getView().onLoadSearchError("暂无相关信息");
                }

                SearchPoolHistoryDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSearchPoolHistoryDBDao();
                SearchPoolHistoryDB unique = dbDao.queryBuilder().where(SearchPoolHistoryDBDao.Properties.Name.eq(mPreWord)).unique();
                if (unique != null) {
                    dbDao.delete(unique);
                }

                SearchPoolHistoryDB db = new SearchPoolHistoryDB();
                db.setId(System.currentTimeMillis());
                db.setName(word);
                dbDao.insert(db);
                getView().notifyHistoryList();
            }

            @Override
            protected void _onError(String message) {
                if (isLoadMore) {
                    ToastUtil.showShort(message);
                    loadMoreAdapter.loadMoreFail();
                } else {
                    getView().onLoadSearchError(message);
                }

            }
        });
    }

    private void searchForPost(final String word, final boolean isLoadMore) {
//        BaseRequestBody body = new BaseRequestBody();
        Map<String, Object> map = new HashMap<>();
        if (!isLoadMore) {
            mPreWord = word;
            mNextSearchStart = "";
        }
        map.put(RequestParams.search_word, mPreWord);
        Observable<BaseResponse<SearchPostEntity>> observable = HttpUtil.getInstance().sApi.getSearchForPost(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<SearchPostEntity>(mContext, !isLoadMore) {

            @Override
            protected void _onNext(SearchPostEntity entity) {
                List<SearchPostEntity.ResultsBean> results = entity.getResults();
                if (results != null) {
                    mNextSearchStart = entity.getNextSearchStart();
                    if (isLoadMore) {
                        mTotalSize += results.size();
                        loadMoreAdapter.addData(results);
                        loadMoreAdapter.loadMoreComplete();
                    } else {
                        mTotalSize = results.size();
                        getView().onLoadPostSearchSucceed(results);
                    }

                    if (mTotalSize >= entity.getTotalSize()) {
                        loadMoreAdapter.loadMoreEnd();
                    }
                } else {
                    getView().onLoadSearchError("暂无相关信息");
                }


                SearchPostHistoryDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSearchPostHistoryDBDao();
                SearchPostHistoryDB unique = dbDao.queryBuilder().where(SearchPostHistoryDBDao.Properties.Name.eq(mPreWord)).unique();
                if (unique != null) {
                    dbDao.delete(unique);
                }

                SearchPostHistoryDB db = new SearchPostHistoryDB();
                db.setId(System.currentTimeMillis());
                db.setName(word);
                dbDao.insert(db);

                getView().notifyHistoryList();
            }

            @Override
            protected void _onError(String message) {
                if (isLoadMore) {
                    ToastUtil.showShort(message);
                    loadMoreAdapter.loadMoreFail();
                } else {
                    getView().onLoadSearchError(message);

                }

            }
        });
    }


    public List<SearchHistoryEntity> getSearchHistory() {
        ArrayList<SearchHistoryEntity> list = new ArrayList<>();

        if (mPageMode == Constants.POST_SEARCH_MODE) {
            SearchPostHistoryDBDao postHistoryDBDao = GreenDaoManager.getInstance().getmDaoSession().getSearchPostHistoryDBDao();
            List<SearchPostHistoryDB> postDBs = postHistoryDBDao.loadAll();
            for (SearchPostHistoryDB searchPostHistoryDB : postDBs) {
                if (TextUtils.isEmpty(searchPostHistoryDB.getName())) {
                    postHistoryDBDao.delete(searchPostHistoryDB);
                } else {
                    SearchHistoryEntity entity = new SearchHistoryEntity();
                    entity.setId(searchPostHistoryDB.getId());
                    entity.setName(searchPostHistoryDB.getName());

                    list.add(entity);
                }

            }
        } else {
            SearchPoolHistoryDBDao poolHistoryDBDao = GreenDaoManager.getInstance().getmDaoSession().getSearchPoolHistoryDBDao();
            List<SearchPoolHistoryDB> searchPoolHistoryDBs = poolHistoryDBDao.loadAll();
            for (SearchPoolHistoryDB searchPoolHistoryDB : searchPoolHistoryDBs) {
                if (TextUtils.isEmpty(searchPoolHistoryDB.getName())) {
                    poolHistoryDBDao.delete(searchPoolHistoryDB);
                } else {
                    SearchHistoryEntity entity = new SearchHistoryEntity();
                    entity.setId(searchPoolHistoryDB.getId());
                    entity.setName(searchPoolHistoryDB.getName());
                    list.add(entity);
                }

            }
        }
        Collections.reverse(list);
        return list;
    }

    public void deleteAllHistory() {
        if (mPageMode == Constants.POOL_SEARCH_MODE) {
            SearchPoolHistoryDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSearchPoolHistoryDBDao();
            dbDao.deleteAll();
        } else {
            SearchPostHistoryDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSearchPostHistoryDBDao();
            dbDao.deleteAll();
        }
    }


    public void setLoadMoreAdapter(BaseQuickAdapter loadMoreAdapter) {
        this.loadMoreAdapter = loadMoreAdapter;
    }
}
