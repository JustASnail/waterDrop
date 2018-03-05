package com.drops.waterdrop.ui.mine.presenter;

import android.content.Intent;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.help.PayHelper;
import com.drops.waterdrop.model.OrderState;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.base.BaseWebViewActivity;
import com.drops.waterdrop.ui.mine.view.IOrderDetailView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.LogiticsEntity;
import com.netease.nim.uikit.model.OrderDetailEntity2;
import com.netease.nim.uikit.model.OrderStatusEntity;
import com.netease.nim.uikit.model.WechatPayDetail;
import com.netease.nim.uikit.model.WujieTradeConstructEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

import static com.drops.waterdrop.ui.mine.activity.OrderDetailActivity.EXTRA_ORDER_ENTITY;

/**
 * Created by HZH on 2017/6/29.
 */

public class OrderDetailPresenter extends BasePresenter<IOrderDetailView> {

    private OrderState mOrderState;
    private OrderDetailEntity2 mOrderEntity;

    public OrderDetailPresenter(BaseActivity context) {
        super(context);
    }

    public void parseIntent(Intent intent) {
        long orderId = intent.getLongExtra(EXTRA_ORDER_ENTITY, 0);

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, orderId);
        Observable<BaseResponse<OrderDetailEntity2>> observable = HttpUtil.getInstance().sApi.getOrderDetail(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<OrderDetailEntity2>(mContext) {
            @Override
            protected void _onNext(OrderDetailEntity2 entity2) {
                mOrderEntity = entity2;
                getView().getOnParseIntent(entity2);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void setOrderType(int orderStatus) {
        switch (orderStatus) {
            case 0:
                mOrderState = OrderState.NO_PAYMENT;
                getView().setNoPaymentTypeView();
                break;
            case 10:
                mOrderState = OrderState.NO_DISPATCH;
                getView().setNoDispatchTypeView();
                break;
            case 11:
                mOrderState = OrderState.NO_RECEIVE;
                getView().setNoReceiveTypeView();
                break;
            case 20:
                mOrderState = OrderState.NO_COMMENT;
                getView().setUncommentTypeView();
                break;
            case 30:
                mOrderState = OrderState.FINISH;
                getView().setFinishTypeView();
                break;
            case 70:
                mOrderState = OrderState.EXCHANGE;
                getView().setExchangeType();
                break;
            case 90:
                mOrderState = OrderState.CANCEL;
                getView().setCancelType();
                break;
        }
    }

    public void initView() {
        if (mOrderEntity != null) {
            int orderStatus = mOrderEntity.getStatus();
            setOrderType(orderStatus);
        }
    }

    public void cancelOrder(long orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, orderId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.cancelOrder(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object dataBean) {

                ToastUtil.showShort("取消订单成功");
                getView().cancelOrderSuccess();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    public void pay(long orderId) {
        if (!WaterDropApp.mWxApi.isWXAppInstalled()) {
            ToastUtil.showShort("您还未安装微信， 请先下载微信应用");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, orderId);
        Observable<BaseResponse<WechatPayDetail>> observable = HttpUtil.getInstance().sApi.getWechatPayStr(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<WechatPayDetail>(mContext, "正在支付...") {
            @Override
            protected void _onNext(WechatPayDetail wechatStr) {
                wechatPay(wechatStr);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void wechatPay(WechatPayDetail wechatStr) {
        WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_DETAIL;
        PayHelper.Instance.payWeChat(wechatStr);
    }

    public void confirmReceived(long orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, orderId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.confirmReceived(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object wechatStr) {
                ToastUtil.showShort("确认收货成功");
                getView().confirmReceivedSuccess();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    public void checkPayed(long orderId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, orderId);
        Observable<BaseResponse<OrderStatusEntity>> observable = HttpUtil.getInstance().sApi.checkOrderStatus(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<OrderStatusEntity>(mContext) {
            @Override
            protected void _onNext(OrderStatusEntity entity) {
                int status = entity.getStatus();
                if (status == 10) {
                    getView().checkPayedSuccess();
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }


    public void getLogiticsInfo(Long orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, orderId);
        Observable<BaseResponse<LogiticsEntity>> observable = HttpUtil.getInstance().sApi.getLogisticsInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<LogiticsEntity>(mContext) {
            @Override
            protected void _onNext(LogiticsEntity entity) {
                if (entity != null) {
                    getView().onGetLogiticsInfoSuccess(entity);
                } else {
                    ToastUtil.showShort("暂无物流信息");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    public void payWuJie(long orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, orderId);
        Observable<BaseResponse<WujieTradeConstructEntity>> observable = HttpUtil.getInstance().sApi.getWujieTradeConstruct(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<WujieTradeConstructEntity>(mContext) {
            @Override
            protected void _onNext(WujieTradeConstructEntity entity) {
                BaseWebViewActivity.startActivity(mContext, entity.getWjPayWayUrl(), mContext.getString(R.string.title_pay_h5));
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}
