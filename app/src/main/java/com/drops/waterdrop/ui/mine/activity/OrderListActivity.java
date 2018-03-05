package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.model.OrderState;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.adapter.OrderListAdapter;
import com.drops.waterdrop.ui.mine.event.UserInfoEvent;
import com.drops.waterdrop.ui.mine.presenter.OrderListPresenter;
import com.drops.waterdrop.ui.mine.view.IAllOrderListView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.OrderEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/6/30.
 */

public class OrderListActivity extends BaseActivity<IAllOrderListView, OrderListPresenter> implements IAllOrderListView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    public static final String TITLE = "title";
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private OrderListAdapter mAdapter;
    private String mTitle;
    private View notDataView;

    public static void start(Context context, String title) {
        Intent starter = new Intent(context, OrderListActivity.class);
        starter.putExtra(TITLE, title);
        context.startActivity(starter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        UserInfoEvent userInfoEvent = new UserInfoEvent();
        userInfoEvent.orderNum = mAdapter.getData().size();
        userInfoEvent.orderState = getOrderStatus(mTitle);
        EventBus.getDefault().post(userInfoEvent);
    }

    @Override
    protected void initView() {
        initTitle();

        initList();
    }

    private void initList() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setHasFixedSize(true);
        if (mAdapter == null) {
            mAdapter = new OrderListAdapter(0);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initEmptyPager();
        mAdapter.setEmptyView(notDataView);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.setLoadMoreAdapter(mAdapter);
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        mTitle = getIntent().getStringExtra(TITLE);
        options.titleString = mTitle;;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        mPresenter.getOrderByStatus(getOrderStatus(mTitle), false, false);
    }

    public OrderState getOrderStatus(String mTitle) {
        OrderState orderState = OrderState.NO_PAYMENT ;
        if (TextUtils.equals(mTitle, getString(R.string.order_non_payment))) {
            orderState = OrderState.NO_PAYMENT;
        } else if (TextUtils.equals(mTitle, getString(R.string.order_unshipped))) {
            orderState = OrderState.NO_DISPATCH;
        } else if (TextUtils.equals(mTitle, getString(R.string.order_unreceive))) {
            orderState = OrderState.NO_RECEIVE;
        } else if (TextUtils.equals(mTitle, getString(R.string.order_uncomment))) {
            orderState = OrderState.NO_COMMENT;
        } else if (TextUtils.equals(mTitle, getString(R.string.order_exchange))) {
            orderState = OrderState.EXCHANGE;
        }
        return orderState;
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
                        OrderDetailActivity.start(OrderListActivity.this, mAdapter.getItem(position).getOrderId());
                    }
                }
            });
        }
    }

    @Override
    protected OrderListPresenter createPresenter() {
        return new OrderListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_order_list;
    }

    @Override
    public void onGetOrderSucceed(List<OrderEntity.ResultsBean> ordersBeen) {
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

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getOrderByStatus(getOrderStatus(mTitle),true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        mPresenter.getOrderByStatus(getOrderStatus(mTitle),false, true);
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
    protected void onDestroy() {
        super.onDestroy();
        UserInfoEvent userInfoEvent = new UserInfoEvent();
        userInfoEvent.type = Constants.TYPE_ORDER;
        userInfoEvent.orderNum = mAdapter.getData().size();
        userInfoEvent.orderState = OrderState.EXCHANGE;
        EventBus.getDefault().post(userInfoEvent);
    }
}