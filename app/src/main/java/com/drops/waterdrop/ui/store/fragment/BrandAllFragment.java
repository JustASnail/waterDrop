package com.drops.waterdrop.ui.store.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.LazyLoadFragment;
import com.drops.waterdrop.ui.store.adapter.BrandListAdapter;
import com.drops.waterdrop.ui.store.adapter.InnerBannerViewHolder;
import com.drops.waterdrop.ui.store.presenter.BrandAllPresenter;
import com.drops.waterdrop.ui.store.view.IBrandAllView;
import com.drops.waterdrop.widget.storebanner.MZBannerView;
import com.drops.waterdrop.widget.storebanner.holder.MZHolderCreator;
import com.netease.nim.uikit.model.BrandListEntity;

import java.util.List;

import static com.drops.waterdrop.ui.mine.fragment.CollectionFragment.ARG_TITLE;

/**
 * Created by Mr.Smile on 2017/9/14.
 */

public class BrandAllFragment extends LazyLoadFragment<IBrandAllView, BrandAllPresenter> implements IBrandAllView,SwipeRefreshLayout.OnRefreshListener, BrandListAdapter.OnBrandClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private MZBannerView mBanner;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private BrandListAdapter.OnBrandClickListener onBrandClickListener;
    private BrandListAdapter mAdapter;

    @Override
    protected void initViews() {
        initSwipeRefresh();
        initRecyclerView();
    }

    private void initSwipeRefresh() {
        mSwipeRefresh = findView(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefresh.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        mRecyclerView = findView(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BrandListAdapter(0);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter.setOnBrandClickListener(this);
        mPresenter.setLoadMoreAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }

    private void initBanner(final List<BrandListEntity.BannersBean> banners) {
        mBanner = (MZBannerView) findView(R.id.banner);
        if (banners != null) {
            mBanner.setPages(banners, new MZHolderCreator<InnerBannerViewHolder>() {
                @Override
                public InnerBannerViewHolder createViewHolder() {
                    return new InnerBannerViewHolder();
                }
            });
            mBanner.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBanner != null) {
            mBanner.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.start();
        }
    }

    @Override
    protected void initData() {
        mPresenter.getData(false, false);
    }

    @Override
    protected BrandAllPresenter createPresenter() {
        return new BrandAllPresenter((BaseActivity) getActivity());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_brand_all;
    }

    public static BrandAllFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        BrandAllFragment fragment = new BrandAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBrandClickListener(int pos) {
        if (onBrandClickListener != null) {
            onBrandClickListener.onBrandClickListener(pos);
        }
    }

    public void setOnBrandClickListener(BrandListAdapter.OnBrandClickListener onBrandClickListener) {
        this.onBrandClickListener = onBrandClickListener;
    }

    @Override
    public void setRefresh(boolean b) {
        mSwipeRefresh.setRefreshing(b);
    }

    @Override
    public void onGetBanner(List<BrandListEntity.BannersBean> banners) {
        initBanner(banners);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefresh.setEnabled(b);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefresh.setEnabled(false);
        mPresenter.getData(false, true);
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getData(true,false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.setNewData(null);
            mAdapter = null;
        }
        Glide.get(getContext()).clearMemory();
        if (mBanner != null) {
            mBanner = null;
        }
    }
}
