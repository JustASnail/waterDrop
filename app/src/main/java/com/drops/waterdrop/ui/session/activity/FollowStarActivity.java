package com.drops.waterdrop.ui.session.activity;

import android.content.Context;
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
import com.drops.waterdrop.ui.find.adapter.StarListAdapter;
import com.drops.waterdrop.ui.session.presenter.FollowStarPresenter;
import com.drops.waterdrop.ui.session.view.IFollowStarView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.StarListEntity;

import java.util.List;

import butterknife.Bind;


public class FollowStarActivity extends BaseActivity<IFollowStarView, FollowStarPresenter> implements IFollowStarView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private View notDataView;
    private View errorView;
    private StarListAdapter mAdapter;


    public static void start(Context context) {
        Intent starter = new Intent(context, FollowStarActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "关注的明星";
        setMyToolbar(options);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initEmptyPager();

//        initErrorPager();
        initAdapter();
    }

    @Override
    protected void initData() {
        mPresenter.getStarDatas(false);
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

                    }
                }
            });
        }
    }

    @Override
    protected FollowStarPresenter createPresenter() {
        return new FollowStarPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_follow_star;
    }


    private void initEmptyPager() {
        notDataView = LayoutInflater.from(this).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);


        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

  /*  private void initErrorPager() {
        errorView = LayoutInflater.from(this).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        ImageView ivNoNetwork = (ImageView) errorView.findViewById(R.id.iv_empty_icon);
        ivNoNetwork.setImageResource(R.mipmap.icon_pool_empty);
        TextView tvNoNetwork = (TextView) errorView.findViewById(R.id.tv_empty_desc);
        tvNoNetwork.setText("网络跑丢了， 请检查网络");

        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }*/

    private void initAdapter() {
        mAdapter = new StarListAdapter(R.layout.item_star_list, null);
        mAdapter.setEmptyView(notDataView);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getStarDatas(true);
    }

    @Override
    public void onLoadMoreRequested() {
        if (mPresenter.mCount < 100) {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onGetDataSuccess(List<StarListEntity.FriendsBean> entity) {
        if (mAdapter != null) {
            mAdapter.setNewData(entity);
        }
    }

    @Override
    public void setEnableLoadMore(boolean isEnable) {
        mAdapter.setEnableLoadMore(isEnable);
    }

    @Override
    public void setRefresh(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }
}
