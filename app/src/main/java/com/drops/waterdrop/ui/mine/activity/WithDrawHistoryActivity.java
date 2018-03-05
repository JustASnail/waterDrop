package com.drops.waterdrop.ui.mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.adapter.WithdrawHistoryAdapter;
import com.drops.waterdrop.ui.mine.presenter.WithDrawHistoryPresenter;
import com.drops.waterdrop.ui.mine.view.IWithDrawHistoryView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.model.WithdrawHistoryEntity;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/8/24.
 */

  public class WithDrawHistoryActivity extends BaseActivity<IWithDrawHistoryView,WithDrawHistoryPresenter> implements IWithDrawHistoryView,SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private View noneDataView;
    private WithdrawHistoryAdapter mAdapter;

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "提现记录";
        setMyToolbar(options);

        initWedgit();
    }

    private void initWedgit() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initEmptyPage();
        initAdapter();
    }

    @Override
    protected void initData() {
        mPresenter.getHistoryList(false, false);
//        mAdapter.setNewData(FakeData.getWithdrawList());
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        if (mAdapter != null) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);

            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(WithDrawHistoryActivity.this, WithdrawDetailActivity.class);
                    WithdrawHistoryEntity.ResultsBean item = (WithdrawHistoryEntity.ResultsBean) adapter.getItem(position);
                    intent.putExtra(Constants.EXTRA_ENTITY, item);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected WithDrawHistoryPresenter createPresenter() {
        return new WithDrawHistoryPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_account_detail;
    }

    private void initAdapter() {
        mAdapter = new WithdrawHistoryAdapter(0);
        mAdapter.setEmptyView(noneDataView);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.setLoadMoreAdapter(mAdapter);
    }


    private void initEmptyPage() {
        noneDataView = LayoutInflater.from(this).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        noneDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getHistoryList(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        mPresenter.getHistoryList(false, true);
    }

    @Override
    public void setRefresh(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefreshLayout.setEnabled(b);
    }
}
