package com.drops.waterdrop.ui.mine.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.OrderState;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.LazyLoadFragment;
import com.drops.waterdrop.ui.mine.activity.OrderDetailActivity;
import com.drops.waterdrop.ui.mine.adapter.OrderListAdapter;
import com.drops.waterdrop.ui.mine.event.UserInfoEvent;
import com.drops.waterdrop.ui.mine.presenter.OrderListPresenter;
import com.drops.waterdrop.ui.mine.view.IAllOrderListView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.OrderEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/6/28.
 */

public class AllOrderListFragment extends LazyLoadFragment<IAllOrderListView, OrderListPresenter> implements BaseQuickAdapter.OnItemClickListener, IAllOrderListView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private static final String TAG = "AllOrderListFragment";
    private static final String ARG_TITLE = "title_arg_key";
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private OrderListAdapter mAdapter;
    private View notDataView;
    private boolean orderChanged;

    public static AllOrderListFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        AllOrderListFragment fragment = new AllOrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        initEmptyPager();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new OrderListAdapter(0);
        mAdapter.setEmptyView(notDataView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mPresenter.setLoadMoreAdapter(mAdapter);

        mAdapter.setOnConfirmReceivedListener(new OrderListAdapter.OnConfirmReceivedListener() {
            @Override
            public void onConfirmReceived() {
                mPresenter.getOrderByStatus(OrderState.NO_RECEIVE, false, false);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getOrderByStatus(getOrderStatus(getTitle()), false, false);
    }

    @Override
    protected OrderListPresenter createPresenter() {
        return new OrderListPresenter((BaseActivity) getActivity());
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
            OrderDetailActivity.start(getContext(), mAdapter.getItem(position).getOrderId());
        }
    }

    @Override
    public void onGetOrderSucceed(List<OrderEntity.ResultsBean> ordersBeen) {
//        mAdapter.setNewData(ordersBeen);
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
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getOrderByStatus(getOrderStatus(getTitle()), true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        mPresenter.getOrderByStatus(getOrderStatus(getTitle()), false, true);
    }

    @Override
    public void setRefresh(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefreshLayout.setEnabled(b);
    }

    public OrderState getOrderStatus(String mTitle) {
        OrderState orderState = OrderState.All;
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
        } else if (TextUtils.equals(mTitle, getString(R.string.order_all))) {
            orderState = OrderState.All;
        }
        return orderState;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (orderChanged) {
            UserInfoEvent userInfoEvent = new UserInfoEvent();
//        userInfoEvent.orderState = getOrderStatus(getTitle());
//        userInfoEvent.orderNum = mAdapter.getData().size();
            userInfoEvent.type = Constants.TYPE_ORDER;
            userInfoEvent.notify = true;
            EventBus.getDefault().post(userInfoEvent);
        }

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(UserInfoEvent userInfoEvent) {
        if (userInfoEvent.notify) {
            orderChanged = true;
            mPresenter.getOrderByStatus(getOrderStatus(getTitle()), false, false);
        }
    }

}
