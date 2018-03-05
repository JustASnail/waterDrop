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
import com.drops.waterdrop.ui.mine.adapter.CollectionListAdapter;
import com.drops.waterdrop.ui.mine.presenter.MyCollectionPresenter;
import com.drops.waterdrop.ui.mine.view.ICollectionView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.CollectionSTEntry;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/6/27.
 */

public class CollectionFragment extends LazyLoadFragment<ICollectionView, MyCollectionPresenter> implements BaseQuickAdapter.OnItemClickListener, ICollectionView, MyCollectionActivity.PrivateChangeListener, AddressManageAdapter.onUpdateListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, CollectionListAdapter.OnDeleteListener {

    private static final String TAG = "CollectionFragment";
    public static final String ARG_TITLE = "title_arg_key";


    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private CollectionListAdapter mAdapter;
    private View noneDataView;

    public static CollectionFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        CollectionFragment fragment = new CollectionFragment();
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
        mPresenter.getCollectPostList(false, false);
        mAdapter = new CollectionListAdapter(0);
        mAdapter.setEmptyView(noneDataView);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.setLoadMoreAdapter(mAdapter);


        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnUpdateListener(this);
        mAdapter.setOnDeleteListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

        ((MyCollectionActivity) getActivity()).addPrivateChangeListener(this);

    }

    @Override
    protected MyCollectionPresenter createPresenter() {
        return new MyCollectionPresenter((BaseActivity) getActivity());
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!FastClickUtil.isFastDoubleClick()) {
            CollectionSTEntry.ResultsBean resultsBean = mAdapter.getData().get(position);
            CommonWebActivity.start(getContext(), resultsBean.getTipId(),resultsBean.getTipUrl() );
        }
    }

    @Override
    public void onGetCollectionSTSucceed(List<CollectionSTEntry.ResultsBean> dropTips) {
    }

    @Override
    public void onGetBuySTSucceed(List<CollectionSTEntry.ResultsBean> results) {
    }

    @Override
    public void onPrivateSettingChange(boolean isPrivate, int tabPos) {
        Log.d(TAG, "onPrivateSettingChange: 收藏的");
        mAdapter.setPrivateMode(isPrivate);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdate() {
        mPresenter.getCollectPostList(false, false);
    }

    private void initEmptyPage() {
        noneDataView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        noneDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCollectPostList(false, false);
            }
        });
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getCollectPostList(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        mPresenter.getCollectPostList(false, true);
    }

    @Override
    public void setRefresh(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onDelete(int pos) {
        mAdapter.remove(pos);
        mAdapter.notifyDataSetChanged();
    }
}
