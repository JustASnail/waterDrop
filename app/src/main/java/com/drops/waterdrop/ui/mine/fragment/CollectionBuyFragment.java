package com.drops.waterdrop.ui.mine.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.LazyLoadFragment;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.drops.waterdrop.ui.mine.activity.MyCollectionActivity;
import com.drops.waterdrop.ui.mine.adapter.AddressManageAdapter;
import com.drops.waterdrop.ui.mine.adapter.CollectionBuyListAdapter;
import com.drops.waterdrop.ui.mine.adapter.CollectionListAdapter;
import com.drops.waterdrop.ui.mine.presenter.MyCollectionBuyPresenter;
import com.drops.waterdrop.ui.mine.view.ICollectionBuyView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.CollectionSTEntry;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/6/27.
 */

public class CollectionBuyFragment extends LazyLoadFragment<ICollectionBuyView, MyCollectionBuyPresenter> implements BaseQuickAdapter.OnItemClickListener, ICollectionBuyView, AddressManageAdapter.onUpdateListener, MyCollectionActivity.BuyPrivateChangeListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, CollectionListAdapter.OnDeleteListener {
    private static final String TAG = "CollectionFragment";
    private static final String ARG_TITLE = "title_arg_key";


    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private View noneDataView;
    private CollectionBuyListAdapter mBuyAdapter;

    public static CollectionBuyFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        CollectionBuyFragment fragment = new CollectionBuyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        initEmptyPage();
    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPresenter.getBuyPostList(false, false);
        mBuyAdapter = new CollectionBuyListAdapter(0);
        mBuyAdapter.setEmptyView(noneDataView);
        mRecyclerView.setAdapter(mBuyAdapter);
        mPresenter.setLoadMoreAdapter(mBuyAdapter);

        mBuyAdapter.setOnItemClickListener(this);
        mBuyAdapter.setOnUpdateListener(this);
        mBuyAdapter.setOnDeleteListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mBuyAdapter.setOnLoadMoreListener(this, mRecyclerView);

        ((MyCollectionActivity) getActivity()).setPrivateChangeListener(this);
    }

    @Override
    protected MyCollectionBuyPresenter createPresenter() {
        return new MyCollectionBuyPresenter((BaseActivity) getActivity());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.all_order_list_fragment;
    }

    public String getTitle() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getString(ARG_TITLE);
        }
        return "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!FastClickUtil.isFastDoubleClick()) {
            CollectionSTEntry.ResultsBean resultsBean = mBuyAdapter.getData().get(position);
            CommonWebActivity.start(getContext(), resultsBean.getTipId(), resultsBean.getTipUrl());
        }
    }

    @Override
    public void onGetCollectionSTSucceed(List<CollectionSTEntry.ResultsBean> dropTips) {
    }

    @Override
    public void onGetBuySTSucceed(List<CollectionSTEntry.ResultsBean> results) {
    }


    @Override
    public void onUpdate() {
        mPresenter.getBuyPostList(false, false);
    }

    @Override
    public void onBuyPrivateSettingChange(boolean isPrivate, int tabPos) {
        Log.d(TAG, "onPrivateSettingChange: 买过的");
        mBuyAdapter.setPrivateMode(isPrivate);
        mBuyAdapter.notifyDataSetChanged();
    }

    private void initEmptyPage() {
        noneDataView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        noneDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getBuyPostList(false, false);
            }
        });
    }

    @Override
    public void onRefresh() {
        mBuyAdapter.setEnableLoadMore(false);
        mPresenter.getBuyPostList(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        mPresenter.getBuyPostList(false, true);
    }

    @Override
    public void setRefresh(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefreshLayout.setEnabled(b);
    }

    @Override
    public void onDelete(int pos) {
        mBuyAdapter.remove(pos);
        mBuyAdapter.notifyDataSetChanged();
    }
}
