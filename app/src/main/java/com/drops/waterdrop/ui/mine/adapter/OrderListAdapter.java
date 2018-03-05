package com.drops.waterdrop.ui.mine.adapter;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.help.PayHelper;
import com.drops.waterdrop.model.OrderState;
import com.drops.waterdrop.ui.base.BaseWebViewActivity;
import com.drops.waterdrop.ui.find.activity.GoodsDetailsActivity;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.drops.waterdrop.ui.mine.activity.CustomerServiceActivity;
import com.drops.waterdrop.ui.mine.activity.LogisticsActivity;
import com.drops.waterdrop.ui.mine.activity.ScoreActivity;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.LogiticsEntity;
import com.netease.nim.uikit.model.OrderEntity;
import com.netease.nim.uikit.model.WechatPayDetail;
import com.netease.nim.uikit.model.WujieTradeConstructEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by HZH on 2017/6/29.
 */

public class OrderListAdapter extends BaseQuickAdapter<OrderEntity.ResultsBean, BaseViewHolder> {

    private OrderState mOrderState;

    public OrderListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_order_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderEntity.ResultsBean item) {

        setOrderState(item.getStatus());

        setLayoutVisible(helper, item);
    }

    private void setLayoutVisible(BaseViewHolder helper, final OrderEntity.ResultsBean item) {
        TextView mTvDealStatus = helper.getView(R.id.tv_deal_status);
        TextView mTvPrice = helper.getView(R.id.tv_price);
        TextView country = helper.getView(R.id.tv_country);
        TextView goodNum = helper.getView(R.id.tv_goods_num);
        TextView goodName = helper.getView(R.id.tv_name);
        TextView goodNumBottom = helper.getView(R.id.tv_number);
        TextView totalPrice = helper.getView(R.id.tv_total_price);
        TextView spec = helper.getView(R.id.tv_specification);
        ImageView ivGoodIv = helper.getView(R.id.iv_order_icon);
        ImageView ivCountry = helper.getView(R.id.iv_country_pic);

        RelativeLayout mRlGoodsNumber = helper.getView(R.id.rl_goods_number);
        TextView mBottomTvEnd = helper.getView(R.id.tv_btn);    //最右边
        TextView mBottomTvStart = helper.getView(R.id.tv_btn_ckyt);    //最左边
        TextView mBottomTvRight = helper.getView(R.id.tv_btn_wlgz);    //右起第二个
        TextView mBottomTvLeft = helper.getView(R.id.tv_btn_lxkf);    //左起第二个
        RelativeLayout mRlBtn = helper.getView(R.id.rl_order_bottom);
        View dealStatus = helper.getView(R.id.layout_deal_status);
        View goodsDetails = helper.getView(R.id.layout_goods_details);

        TextView service1 = helper.getView(R.id.tv_service1);
        TextView service2 = helper.getView(R.id.tv_service2);
        TextView service3 = helper.getView(R.id.tv_service3);
        LinearLayout llService = helper.getView(R.id.ll_service);

        goodsDetails.setVisibility(View.VISIBLE);
        dealStatus.setVisibility(View.VISIBLE);

        boolean isWJOrder = false;
        boolean isPostWJOrder = false;
        String payInfo = "";
        int crossBorder = 0;
        List<OrderEntity.ResultsBean.GoodsBean> goods = item.getGoods();
        if (goods.size() > 0) {
            OrderEntity.ResultsBean.GoodsBean goodbean = goods.get(0);
            mTvPrice.setText(NumberUtil.Instance.formatPrice(goodbean.getUnitPrice()));
            spec.setText(goodbean.getGood().getGoodSkus().get(0).getSkuName());
            goodNum.setText("x" + goodbean.getQuantity());
            goodName.setText(goodbean.getGoodName());
            goodNumBottom.setText(goodbean.getQuantity() + "");
            totalPrice.setText("合计 ： " + item.getPrice() + "元");
            country.setText(goodbean.getGood().getCountryname() + "馆");
//            GlideUtil.showImageView(mContext, R.drawable.img_qs_108x70, goodbean.getGood().getCountrypic(), ivCountry);
            Glide.with(mContext).load(goodbean.getGood().getCountrypic())
                    .placeholder(R.drawable.img_qs_108x70)
                    .error(R.drawable.img_qs_108x70)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(ivCountry);

            GlideUtil.showImageView(mContext, R.drawable.img_qs_90x90, goodbean.getGoodCovery(), ivGoodIv);

            List<OrderEntity.ResultsBean.GoodsBean.GoodBean.ServiceBean> service = goodbean.getGood().getService();
            showService(service, llService, service1, service2, service3);

            String from = goodbean.getGood().getFrom();
            crossBorder = goodbean.getGood().getCrossBorder();
            isWJOrder = TextUtils.equals(from, "wujie");
            isPostWJOrder = TextUtils.equals(item.getFrom(), "wujie");
            payInfo = item.getPayInfo();

            final boolean finalIsPostWJOrder = isPostWJOrder;
            mBottomTvStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_LIST;
                    if (finalIsPostWJOrder) goToStoreActivity();
                    else goToOriginalPost(item);
                }
            });
        } else {
            //为了解决数据goods【】==0的时候，复用问题
            mTvPrice.setText("");
            spec.setText("");
            goodNum.setText("");
            goodName.setText("");
            goodNumBottom.setText("");
            totalPrice.setText("");
            country.setText("");
            Glide.with(mContext).load("")
                    .placeholder(R.drawable.img_qs_108x70)
                    .error(R.drawable.img_qs_108x70)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(ivCountry);

            GlideUtil.showImageView(mContext, R.drawable.img_qs_90x90, "", ivGoodIv);
        }

        final String finalPayInfo = payInfo;
        if (mOrderState == OrderState.NO_PAYMENT) {
//            mBottomTvStart.setVisibility(isWJOrder ? View.GONE : View.VISIBLE);
            mBottomTvEnd.setVisibility(View.VISIBLE);
            mTvDealStatus.setText("待付款");
            mBottomTvEnd.setText("立即付款");
            mBottomTvEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.equals("h5",finalPayInfo)  || TextUtils.equals("yjf",finalPayInfo)) {
                        payWuJie(item.getOrderId());
                    } else {
                        pay(item.getOrderId());
                    }
                }
            });

        } else if (mOrderState == OrderState.NO_DISPATCH) {
            mTvDealStatus.setText("待发货");
            mBottomTvStart.setVisibility(View.GONE);
//            if (isWJOrder) {
//                mBottomTvEnd.setVisibility(View.GONE);
//            } else {
//                mBottomTvEnd.setText("查看来源");
//                mBottomTvEnd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        goToOriginalPost(item);
//                    }
//                });
//            }
            mBottomTvEnd.setText("查看来源");
            final boolean finalIsPostWJOrder1 = isPostWJOrder;
            mBottomTvEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_LIST;
                    if (finalIsPostWJOrder1) {goToStoreActivity();}
                    else {goToOriginalPost(item);}
                }
            });

        } else if (mOrderState == OrderState.NO_RECEIVE) {
//            mBottomTvStart.setVisibility(isWJOrder ? View.GONE : View.VISIBLE);
            mBottomTvEnd.setVisibility(View.VISIBLE);
            mTvDealStatus.setText("待收货");
            mBottomTvEnd.setText("确认收货");
            mBottomTvEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmReceive(item);
                }
            });
            mBottomTvRight.setVisibility(View.VISIBLE);
            mBottomTvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLogisticsData(item);
                }
            });
        } else if (mOrderState == OrderState.NO_COMMENT) {
//            mBottomTvStart.setVisibility(isWJOrder ? View.GONE : View.VISIBLE);
            mTvDealStatus.setText("待评价");
            mBottomTvRight.setVisibility(View.VISIBLE);
            mBottomTvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLogisticsData(item);
                }
            });
            mBottomTvEnd.setVisibility(View.VISIBLE);
            mBottomTvEnd.setText("立即评价");
            mBottomTvEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ScoreActivity.start(mContext, item.getGoods().get(0));
                }
            });
        } else if (mOrderState == OrderState.FINISH) {
            mTvDealStatus.setText("交易完成");
//            mBottomTvStart.setVisibility(isWJOrder ? View.GONE : View.VISIBLE);
            mBottomTvRight.setVisibility(View.VISIBLE);
            mBottomTvRight.setText("物流跟踪");
            mBottomTvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLogisticsData(item);
                }
            });
            mBottomTvEnd.setVisibility(View.VISIBLE);
            reBuy(item, mBottomTvEnd,isPostWJOrder);
        } else if (mOrderState == OrderState.CANCEL) {
//            mBottomTvStart.setVisibility(isWJOrder ? View.GONE : View.VISIBLE);
            mTvDealStatus.setText("交易关闭");
            mBottomTvRight.setVisibility(View.GONE);
            mBottomTvEnd.setVisibility(View.VISIBLE);
            reBuy(item, mBottomTvEnd,isPostWJOrder);
        } else if (mOrderState == OrderState.EXCHANGE) {
//            mBottomTvStart.setVisibility(isWJOrder ? View.GONE : View.VISIBLE);
            mTvDealStatus.setText("退货/换货");
            mRlBtn.setVisibility(View.VISIBLE);
            mBottomTvEnd.setVisibility(View.VISIBLE);
            mBottomTvEnd.setText("联系客服");
            mBottomTvEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomerServiceActivity.start(mContext,item.getOrderId());
                }
            });
        }
    }

    private void payWuJie(long orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, orderId);
        Observable<BaseResponse<WujieTradeConstructEntity>> observable = HttpUtil.getInstance().sApi.getWujieTradeConstruct(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<WujieTradeConstructEntity>(mContext) {
            @Override
            protected void _onNext(WujieTradeConstructEntity entity) {BaseWebViewActivity.startActivity(mContext, entity.getWjPayWayUrl(), mContext.getString(R.string.title_pay_h5));
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void reBuy(final OrderEntity.ResultsBean item, TextView mBottomTvEnd, final Boolean isPostWJOrder) {
        mBottomTvEnd.setText("重新购买");
        mBottomTvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_LIST;
                if (isPostWJOrder) goToStoreActivity();
                else goToOriginalPost(item);
            }
        });
    }

    private void goToStoreActivity() {
//        mContext.startActivity(new Intent(mContext, StoreActivity.class));
        CommonWebActivity.startForStore(mContext);
    }

    private void getLogisticsData(final OrderEntity.ResultsBean item) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, item.getOrderId());
        Observable<BaseResponse<LogiticsEntity>> observable = HttpUtil.getInstance().sApi.getLogisticsInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<LogiticsEntity>(mContext) {
            @Override
            protected void _onNext(LogiticsEntity entity) {
                if (entity != null) {
                    List<OrderEntity.ResultsBean.GoodsBean> goods = item.getGoods();
                    if (goods != null && goods.size() > 0) {
                        entity.setGoodPhoto(goods.get(0).getGoodCovery());
                    }
                    LogisticsActivity.start(mContext, entity);
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

    private void goToOriginalPost(OrderEntity.ResultsBean item) {
        if (item != null
                && item.getGoods() != null && item.getGoods().size() > 0
                && item.getGoods().get(0) != null
                && item.getGoods().get(0).getDropTip() != null) {

            List<OrderEntity.ResultsBean.GoodsBean> goods = item.getGoods();
            OrderEntity.ResultsBean.GoodsBean.DropTipBean dropTip = goods.get(0).getDropTip();
            String goodId = goods.get(0).getGoodId();
            if (dropTip.getTipId() == Constants.STORE_TIP_ID && dropTip.getDropId() == Constants.STORE_DROP_ID) {
                GoodsDetailsActivity.start(mContext, goodId, Constants.STORE_TIP_ID, Constants.STORE_DROP_ID, Constants.STORE_TIP_TITLE);
            } else if (dropTip.getTipId() == Constants.VIP_TIP_ID && dropTip.getDropId() == Constants.VIP_DROP_ID) {
                GoodsDetailsActivity.start(mContext, goodId, Constants.VIP_TIP_ID, Constants.VIP_DROP_ID, Constants.VIP_TIP_TITLE);
            } else {
                CommonWebActivity.start(mContext, item.getGoods().get(0).getTipId(), dropTip.getTipUrl());
            }
        } else {
            ToastUtil.showShort(mContext.getResources().getString(R.string.good_unshelve));
        }
    }

    private void confirmReceive(final OrderEntity.ResultsBean item) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, item.getOrderId());
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.confirmReceived(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object wechatStr) {
                ToastUtil.showShort("确认收货成功");
                if (onConfirmReceivedListener != null) {
                    onConfirmReceivedListener.onConfirmReceived();
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void pay(long orderId) {
        if (!WaterDropApp.mWxApi.isWXAppInstalled()) {
            ToastUtil.showShort("您还未安装微信， 请先下载微信应用");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.order_id, orderId);
        Observable<BaseResponse<WechatPayDetail>> observable = HttpUtil.getInstance().sApi.getWechatPayStr(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<WechatPayDetail>(mContext) {
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
        WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_LIST;
        PayHelper.Instance.payWeChat(wechatStr);
    }

    private void setOrderState(int orderStatus) {
        if (orderStatus == 0) {
            mOrderState = OrderState.NO_PAYMENT;
        } else if (orderStatus == 10) {
            mOrderState = OrderState.NO_DISPATCH;
        } else if (orderStatus == 11) {
            mOrderState = OrderState.NO_RECEIVE;
        } else if (orderStatus == 20) {
            mOrderState = OrderState.NO_COMMENT;
        } else if (orderStatus == 30) {
            mOrderState = OrderState.FINISH;
        } else if (orderStatus == 70) {
            mOrderState = OrderState.EXCHANGE;
        } else if (orderStatus == 90) {
            mOrderState = OrderState.CANCEL;
        } else if (orderStatus == 99) {
            mOrderState = OrderState.DELETE;
        }
    }

    private void showService(List<OrderEntity.ResultsBean.GoodsBean.GoodBean.ServiceBean> service
            , LinearLayout mLlService, TextView mService1, TextView mService2, TextView mService3) {
        if (service != null && service.size() > 0) {
            mLlService.setVisibility(View.VISIBLE);
            if (service.size() == 1) {
                mService1.setText(service.get(0).getTitle());
                mService1.setVisibility(View.VISIBLE);
            } else if (service.size() == 2) {
                mService1.setText(service.get(0).getTitle());
                mService1.setVisibility(View.VISIBLE);

                mService2.setText(service.get(1).getTitle());
                mService2.setVisibility(View.VISIBLE);
            } else {
                mService1.setText(service.get(0).getTitle());
                mService1.setVisibility(View.VISIBLE);

                mService2.setText(service.get(1).getTitle());
                mService2.setVisibility(View.VISIBLE);

                mService3.setText(service.get(2).getTitle());
                mService3.setVisibility(View.VISIBLE);
            }
        } else {
            mLlService.setVisibility(View.GONE);
        }
    }

    private OnConfirmReceivedListener onConfirmReceivedListener;

    public void setOnConfirmReceivedListener(OnConfirmReceivedListener onConfirmReceivedListener) {
        this.onConfirmReceivedListener = onConfirmReceivedListener;
    }

    public interface OnConfirmReceivedListener {
        void onConfirmReceived();
    }
}
