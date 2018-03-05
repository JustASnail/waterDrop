package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.find.adapter.CashGiftRecordAdapter;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.CashGiftRecordEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.netease.nim.uikit.team.ui.DividerItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by dengxiaolei  2017/10/10.
 */

public class CashGiftRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @Bind(R.id.iv_left)
    ImageView mIvLeft;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private CashGiftRecordAdapter mAdapter;

    private Map<String, Object> map;
    private String mNextSearchStart;
    private int mRealTotalSize;


    public static void start(Context context) {
        Intent starter = new Intent(context, CashGiftRecordActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mAdapter = new CashGiftRecordAdapter(0);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);

        View emptyView = View.inflate(this, R.layout.empty_view, null);
        mAdapter.setEmptyView(emptyView);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider));
    }


    @Override
    protected void initData() {
        getRecord(false, false);
        //你麻痹

    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        if (mAdapter != null) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);

            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        CashGiftDetailActivity.start(CashGiftRecordActivity.this, mAdapter.getData().get(position));
                    }
                }
            });
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_cash_gift_record;
    }


    private void getRecord(final boolean isRefresh, final boolean isLoadMore) {
        if (isLoadMore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
        }

        final Observable<BaseResponse<CashGiftRecordEntity>> giftInfo = HttpUtil.getInstance().sApi.getGiftRecord(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(giftInfo, new ProgressSubscriber<CashGiftRecordEntity>(this, !isRefresh) {
            @Override
            protected void _onNext(CashGiftRecordEntity object) {
                List<CashGiftRecordEntity.ResultsBean> results = object.getResults();
                mNextSearchStart = object.getNextSearchStart();
                if (results != null && results.size() > 0) {

                    if (isLoadMore) {
                        mRealTotalSize += results.size();
                        mAdapter.addData(results);
                        mAdapter.loadMoreComplete();
                    } else {
                        mRealTotalSize = results.size();
                        mAdapter.setNewData(results);
                    }


                    if (mRealTotalSize >= object.getTotalSize()) {
                        mAdapter.loadMoreEnd();
                    }

                } else if (isLoadMore) {
                    mAdapter.loadMoreEnd();
                }

                if (isRefresh) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mAdapter.setEnableLoadMore(true);
                }

                if (isLoadMore) {
                    mSwipeRefreshLayout.setEnabled(true);
                }

            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                if (isLoadMore) {
                    mAdapter.loadMoreFail();
                    mSwipeRefreshLayout.setEnabled(true);

                } else if (isRefresh) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mAdapter.setEnableLoadMore(true);

                }
            }
        });
    }


    @OnClick(R.id.iv_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        getRecord(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        getRecord(false, true);
    }
}
