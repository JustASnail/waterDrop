package com.drops.waterdrop.ui.store.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.LazyLoadFragment;
import com.drops.waterdrop.ui.find.activity.GoodsDetailsActivity;
import com.drops.waterdrop.ui.store.adapter.BrandItemAdapter;
import com.drops.waterdrop.ui.store.presenter.BrandItemPresenter;
import com.drops.waterdrop.ui.store.view.IBrandItemView;
import com.drops.waterdrop.ui.store.view.SpaceItemDecoration;
import com.drops.waterdrop.widget.storebanner.MZBannerView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.custom.CustomAnimation;
import com.netease.nim.uikit.model.BrandItemEntity;

import static com.drops.waterdrop.ui.mine.fragment.CollectionFragment.ARG_TITLE;

/**
 * Created by Mr.Smile on 2017/9/14.
 */

public class BrandItemFragment extends LazyLoadFragment<IBrandItemView,BrandItemPresenter> implements IBrandItemView,SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "Smile";
    public static final String ARG_POS = "pos_arg_key";
    public static final String ARG_BRAND_ID = "brand_id_key";
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private BrandItemAdapter mAdapter;
    private View notDataView;


    @Override
    protected void initViews() {
        initRecyclerView();
        initSwipeRefresh();
    }

    private void initSwipeRefresh() {
        mSwipeRefresh = findView(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefresh.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        mRecyclerView = findView(R.id.recycler_view);
        initEmptyPager();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new BrandItemAdapter(0);
        mAdapter.setEmptyView(notDataView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(MZBannerView.dpToPx(8)));
        mAdapter.setOnItemClickListener(this);
        mAdapter.openLoadAnimation(new CustomAnimation());
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mPresenter.setLoadMoreAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mPresenter.getData(getBrandId(),false,false);
    }

    @Override
    protected BrandItemPresenter createPresenter() {
        return new BrandItemPresenter((BaseActivity) getActivity());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_brand_item;
    }

    public static BrandItemFragment newInstance(String title,int pos,long id) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_POS, pos);
        args.putLong(ARG_BRAND_ID, id);
        BrandItemFragment fragment = new BrandItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public int getArgPos() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getInt(ARG_POS);
        }
        return 0;
    }

    public long getBrandId() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getLong(ARG_BRAND_ID);
        }
        return 0;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BrandItemEntity.ResultsBean item = mAdapter.getItem(position);
        if (item != null)
        GoodsDetailsActivity.start(getContext(), item.getGoodId(), Constants.STORE_TIP_ID, Constants.STORE_DROP_ID, Constants.STORE_TIP_TITLE);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefresh.setEnabled(false);
        mPresenter.getData(getBrandId(),false, true);
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getData(getBrandId(),true,false);
    }

    @Override
    public void setRefresh(boolean b) {
        mSwipeRefresh.setRefreshing(b);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefresh.setEnabled(b);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.setNewData(null);
            mAdapter = null;
        }
        Glide.get(getContext()).clearMemory();
    }
}
