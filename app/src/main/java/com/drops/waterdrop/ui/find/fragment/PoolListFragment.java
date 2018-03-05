package com.drops.waterdrop.ui.find.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.LazyLoadFragment;
import com.drops.waterdrop.ui.find.adapter.PoolListAdapter;
import com.drops.waterdrop.ui.find.presenter.PoolListFragmentPresenter;
import com.drops.waterdrop.ui.find.view.IPoolListFragmentView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.StarListEntity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/6/4.
 */

public class PoolListFragment extends LazyLoadFragment<IPoolListFragmentView, PoolListFragmentPresenter> implements IPoolListFragmentView, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener  {

    public static final String ARG_TYPE = "PAGE_TYPE";
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private PoolListAdapter mAdapter;

    private View notDataView;


    public static PoolListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        PoolListFragment fragment = new PoolListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        initEmptyPager();

        initAdapter();
    }

    private void initEmptyPager() {
        notDataView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    private void initAdapter() {
        mAdapter = new PoolListAdapter(0);
        mAdapter.setEmptyView(notDataView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

    }



    @Override
    protected void initData() {
        mPresenter.getData(false);
    }

    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        if (mAdapter != null) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);

            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!FastClickUtil.isFastDoubleClick()) {
//                        StarInfoActivity.start(getContext(), mAdapter.getData().get(position).getFUid());
                    }
                }
            });
        }
    }


    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getData(true);
    }

    @Override
    public void onLoadMoreRequested() {
        if (mPresenter.mCount < 100) {
            mAdapter.loadMoreEnd();
        }

    }

    @Override
    protected PoolListFragmentPresenter createPresenter() {
        return new PoolListFragmentPresenter((BaseActivity) getActivity());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_pool_list;
    }




    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!FastClickUtil.isFastDoubleClick()) {
//            mPresenter.onItemClick(position);
        }
    }

    @Override
    public void onGetDataSucceed(StarListEntity entity, boolean isRefresh) {
        if (mAdapter != null) {

            ArrayList<StarListEntity.FriendsBean> friendsBeen = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                StarListEntity.FriendsBean friendsBean = new StarListEntity.FriendsBean();
                friendsBeen.add(friendsBean);
            }
            mAdapter.setNewData(friendsBeen);

//            mAdapter.setNewData(entity.getFriends());
        }

        if (isRefresh) {
            mSwipeRefreshLayout.setRefreshing(false);
            mAdapter.setEnableLoadMore(true);
        }
    }

    @Override
    public void onGetDataError(boolean isRefresh) {
        if (isRefresh) {
            mSwipeRefreshLayout.setRefreshing(false);
            mAdapter.setEnableLoadMore(true);
        }
    }
}
