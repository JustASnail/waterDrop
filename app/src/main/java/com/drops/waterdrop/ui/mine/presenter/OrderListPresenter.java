package com.drops.waterdrop.ui.mine.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.OrderState;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.adapter.OrderListAdapter;
import com.drops.waterdrop.ui.mine.view.IAllOrderListView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.OrderEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/6/28.
 */

public class OrderListPresenter extends BasePresenter<IAllOrderListView> {
    private String mNextSearchStart;
    private int mRealTotalSize;
    private OrderListAdapter mLoadMoreAdapter;
    private HashMap<String, Object> map2;

    public OrderListPresenter(BaseActivity context) {
        super(context);
    }

    public void getOrderByStatus(OrderState orderState,final boolean isRefresh,final boolean isLoadMore) {
        if (isLoadMore) {
            map2.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map2 = new HashMap<>();
            map2.put(RequestParams.order_status, getOrderStatus(orderState));
        }
        Observable<BaseResponse<OrderEntity>> observable = HttpUtil.getInstance().sApi.getOrderList(RequestBodyUtils.build(map2));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<OrderEntity>(mContext,!isLoadMore && !isRefresh) {
            @Override
            protected void _onNext(OrderEntity dataBean) {
                if (dataBean != null) {
                    List<OrderEntity.ResultsBean> orders = dataBean.getResults();
                    if (orders != null && orders.size() > 0) {
                        mNextSearchStart = dataBean.getNextSearchStart();
                        if (isLoadMore) {
                            mRealTotalSize += orders.size();
                            mLoadMoreAdapter.addData(orders);
                            mLoadMoreAdapter.loadMoreComplete();
                        } else {
                            mRealTotalSize = orders.size();
                            mLoadMoreAdapter.setNewData(orders);
                        }

                        if (mRealTotalSize >= dataBean.getTotalSize()) {
                            mLoadMoreAdapter.loadMoreEnd();
                            getView().setRefreshEnable(true);
                        }
                    }

                    if (isLoadMore) {
                        getView().setRefreshEnable(true);
                    }

                    if (isRefresh) {
                        getView().setRefresh(false);
                    }
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                if (isLoadMore) {
                    mLoadMoreAdapter.loadMoreFail();
                } else if (isRefresh){
                    getView().setRefresh(false);
                }
            }
        });
    }

    private int getOrderStatus(OrderState orderStatus) {
        int i = -1;
        switch (orderStatus) {
            case All:
                i = -1;
                break;
            case NO_PAYMENT:
                i = 0;
                break;
            case NO_DISPATCH:
                i = 10;
                break;
            case NO_RECEIVE:
                i = 11;
                break;
            case NO_COMMENT:
                i = 20;
                break;
            case FINISH:
                i = 30;
                break;
            case EXCHANGE:
                i = 70;
                break;
            case CANCEL:
                i = 90;
                break;
            case DELETE:
                i = 99;
                break;
        }
        return i;
    }

    public void setLoadMoreAdapter(OrderListAdapter mAdapter) {
        mLoadMoreAdapter = mAdapter;
    }

}
