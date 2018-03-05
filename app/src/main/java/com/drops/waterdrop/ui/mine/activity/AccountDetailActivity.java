package com.drops.waterdrop.ui.mine.activity;

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
import com.drops.waterdrop.ui.mine.adapter.AccountDetailAdapter;
import com.drops.waterdrop.ui.mine.presenter.AccountDetailPresenter;
import com.drops.waterdrop.ui.mine.view.IAccountDetailView;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/8/24.
 */

public class AccountDetailActivity extends BaseActivity<IAccountDetailView, AccountDetailPresenter> implements IAccountDetailView,SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private View noneDataView;
    private AccountDetailAdapter mAdapter;

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "账户明细";
        setMyToolbar(options);

        initWedgit();
    }

    private void initWedgit() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initEmptyPage();
        initAdapter();
    }

    @Override
    protected void initData() {
        mPresenter.getDetailList(false, false);
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        if (mAdapter != null) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        }
    }

    @Override
    protected AccountDetailPresenter createPresenter() {
        return new AccountDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_account_detail;
    }

    private void initAdapter() {
        mAdapter = new AccountDetailAdapter(0);
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
        mPresenter.getDetailList(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        mPresenter.getDetailList(false, true);
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
