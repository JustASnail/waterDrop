package com.drops.waterdrop.ui.mine.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.adapter.AccountDetailAdapter;
import com.drops.waterdrop.ui.mine.adapter.MyPostListAdapter;
import com.drops.waterdrop.ui.mine.view.IAccountDetailView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AccountDetaiEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.MyPostEntity;
import com.netease.nim.uikit.model.WithdrawHistoryEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/8/24.
 */

public class AccountDetailPresenter extends BasePresenter<IAccountDetailView> {
    private AccountDetailAdapter mLoadMoreAdapter;
    private HashMap<String, Object> map;
    private String mNextSearchStart;
    private int mRealTotalSize;

    public AccountDetailPresenter(BaseActivity context) {
        super(context);
    }

    public void getDetailList(final boolean isRefresh,final boolean isLoadMore) {
        if (isLoadMore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
            map.put(RequestParams.type, MyUserCache.getVipOrSuply());
        }
        Observable<BaseResponse<AccountDetaiEntity>> observable = HttpUtil.getInstance().sApi.getAccountDetailList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AccountDetaiEntity>(mContext, !isRefresh && !isLoadMore) {

            @Override
            protected void _onNext(AccountDetaiEntity entity) {
                List<AccountDetaiEntity.ResultsBean> results = entity.getResults();

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

    public void setLoadMoreAdapter(AccountDetailAdapter mAdapter) {
        mLoadMoreAdapter = mAdapter;
    }
}
