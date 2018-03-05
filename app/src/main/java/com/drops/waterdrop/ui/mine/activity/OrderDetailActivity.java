package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.drops.waterdrop.ui.find.activity.GoodsDetailsActivity;
import com.drops.waterdrop.ui.mine.event.UserInfoEvent;
import com.drops.waterdrop.ui.mine.presenter.OrderDetailPresenter;
import com.drops.waterdrop.ui.mine.view.IOrderDetailView;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.LogiticsEntity;
import com.netease.nim.uikit.model.OrderDetailEntity2;
import com.netease.nim.uikit.model.OrderEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;

/**
 * Created by HZH on 2017/6/29.
 */

public class OrderDetailActivity extends BaseActivity<IOrderDetailView, OrderDetailPresenter> implements IOrderDetailView, View.OnClickListener {
    private static final String TAG = "OrderDetailActivity";
    public static final String EXTRA_ORDER_ENTITY = "order_entity";
    @Bind(R.id.tv_order_top_status)
    TextView mTvTopStatus;
    @Bind(R.id.rl_address)
    RelativeLayout mLayoutAddress;
    @Bind(R.id.tv_consignee_name)   //名字
            TextView mTvConsigneeName;
    @Bind(R.id.tv_consignee_phone)  //电话
            TextView mTvConsigneePhone;
    @Bind(R.id.tv_consignee_address)    //地址
            TextView mTvConsigneeAddress;
    @Bind(R.id.tv_country)
    TextView mTvCountry;
    @Bind(R.id.iv_country_pic)
    ImageView mIvCountry;
    @Bind(R.id.iv_order_icon)
    ImageView mIvGoodPic;
    @Bind(R.id.tv_name)     //商品名
            TextView mTvName;
    @Bind(R.id.tv_price)
    TextView mTvPrice;
    @Bind(R.id.tv_goods_num)
    TextView mTvGoodsNum;
    @Bind(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @Bind(R.id.tv_freight)      //运费
            TextView mTvFreight;
    @Bind(R.id.tv_real_payment)
    TextView mTvRealPayment;
    @Bind(R.id.tv_order_no)
    TextView mTvOrderNo;
    @Bind(R.id.tv_order_time)
    TextView mTvOrderTime;
    @Bind(R.id.total_price)
    TextView mTotalPrice;
    @Bind(R.id.tv_connect_kefu)
    TextView mTvConnectKF;
    @Bind(R.id.rl_order_bottom)
    RelativeLayout mRlOrderBottom;
    @Bind(R.id.tv_deal_status)
    TextView mTvDealStaus;
    @Bind(R.id.tv_btn)
    TextView mBtnRight;     //底部最右边按钮
    @Bind(R.id.tv_btn_ckyt)
    TextView mBtnCkst;    //查看来源按钮
    @Bind(R.id.tv_btn_wlgz)
    TextView mBtnWlgz;  //物流跟踪 默认不显示，需要第三个按钮的时候显示
    @Bind(R.id.tv_btn_lxkf)
    TextView mBtnLxkf;      //联系客服  默认显示 需要第四个的时候显示
    @Bind(R.id.ll_service)
    LinearLayout mLlService;
    @Bind(R.id.tv_service1)
    TextView mTvService1;
    @Bind(R.id.tv_service2)
    TextView mTvService2;
    @Bind(R.id.tv_service3)
    TextView mTvService3;
    @Bind(R.id.tv_specification)
    TextView mSpec;

    private OrderDetailEntity2 mOrderEntity;
    private boolean orderChanged;
    private boolean isWJOrder;
    private int crossBorder;
    private boolean isPostWJOrder;
    private String payInfo;

    public static void start(Context context, long orderId) {
        Intent starter = new Intent(context, OrderDetailActivity.class);
        starter.putExtra(EXTRA_ORDER_ENTITY, orderId);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle();
        mPresenter.parseIntent(getIntent());
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleId = R.string.my_order;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected OrderDetailPresenter createPresenter() {
        return new OrderDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_order_details_page;
    }

    @Override
    public void getOnParseIntent(OrderDetailEntity2 orderEntity) {
        if (orderEntity != null) {
            mOrderEntity = orderEntity;
            updateUI();
            mPresenter.initView();
        }

    }

    private void updateUI() {
        mTvConsigneeName.setText(mOrderEntity.getDeliveryName());
        mTvConsigneePhone.setText(mOrderEntity.getDeliveryMobile());
        String address = mOrderEntity.getDeliveryProv() + mOrderEntity.getDeliveryCity() +
                mOrderEntity.getDeliveryDistrict() + mOrderEntity.getDeliveryAddress();
        mTvConsigneeAddress.setText(address);
        OrderDetailEntity2.GoodsBean goodsBean = mOrderEntity.getGoods().get(0);
        mTvName.setText(goodsBean.getGoodName());
        mTvGoodsNum.setText("x" + goodsBean.getQuantity());
        mTvPrice.setText(NumberUtil.Instance.formatPrice(goodsBean.getUnitPrice()));
//        String totalprice = NumberUtil.Instance.formatPrice(goodsBean.getUnitPrice() * goodsBean.getQuantity());
        mTvTotalPrice.setText(NumberUtil.Instance.formatPrice(mOrderEntity.getPrice()));
        mTvRealPayment.setText(NumberUtil.Instance.formatPrice(mOrderEntity.getPrice()));
        mTvOrderNo.setText(mOrderEntity.getOrderId() + "");
        mTvOrderTime.setText(mOrderEntity.getCreateTime());
        mTvCountry.setText(goodsBean.getGood().getCountryname() + "馆");
//        GlideUtil.showImageView(this, R.drawable.img_qs_108x70, goodsBean.getGood().getCountrypic(), mIvCountry);
        Glide.with(this).load(goodsBean.getGood().getCountrypic())
                .placeholder(R.drawable.img_qs_108x70)
                .error(R.drawable.img_qs_108x70)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mIvCountry);


        GlideUtil.showImageView(this, R.drawable.img_qs_90x90, goodsBean.getGoodCovery(), mIvGoodPic);

        mSpec.setText(goodsBean.getGood().getGoodSkus().get(0).getSkuName());
        showService(goodsBean.getGood().getService(), mLlService, mTvService1, mTvService2, mTvService3);

        String from = goodsBean.getGood().getFrom();
//        from='wujie'  from="raw"
        String from1 = mOrderEntity.getFrom();
        isPostWJOrder = TextUtils.equals("wujie", from1);
        isWJOrder = TextUtils.equals(from, "wujie");
        payInfo = mOrderEntity.getPayInfo();
        crossBorder = goodsBean.getGood().getCrossBorder();
    }

    @Override
    public void setNoPaymentTypeView() {
        mTvDealStaus.setText("待付款");
        mTvTopStatus.setText("等待买家付款");
        hideCKYT();
        mBtnRight.setText("立即付款");
        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals("h5",payInfo)  || TextUtils.equals("yjf",payInfo)) {
                    mPresenter.payWuJie(mOrderEntity.getOrderId());
                } else  {
                    mPresenter.pay(mOrderEntity.getOrderId());
                }
            }
        });
        mBtnWlgz.setVisibility(View.VISIBLE);
        mBtnWlgz.setText("取消订单");
        mBtnWlgz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.cancelOrder(mOrderEntity.getOrderId());
            }
        });
    }

    private void hideCKYT() {
//        if (isWJOrder) {
//            mBtnCkst.setVisibility(View.GONE);
//            mBtnCkst.setEnabled(false);
//        } else {
//            mBtnCkst.setVisibility(View.VISIBLE);
//            mBtnCkst.setOnClickListener(this);
//        }
        mBtnCkst.setOnClickListener(this);
    }

    @Override
    public void setNoDispatchTypeView() {
        mTvDealStaus.setText("待发货");
        mTvTopStatus.setText("买家已付款");
        mBtnCkst.setVisibility(View.GONE);
        mBtnWlgz.setVisibility(View.GONE);
//        if (isWJOrder) {
//            mBtnLxkf.setVisibility(View.GONE);
//            mBtnRight.setText("联系客服");
//            mBtnRight.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CustomerServiceActivity.start(OrderDetailActivity.this, mOrderEntity.getOrderId());
//                }
//            });
//        } else {
//            mBtnRight.setText("查看来源");
//            mBtnRight.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    goToOriginalSTie();
//                }
//            });
//            mBtnLxkf.setVisibility(View.VISIBLE);
//            mBtnLxkf.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CustomerServiceActivity.start(OrderDetailActivity.this, mOrderEntity.getOrderId());
//                }
//            });
//        }

        mBtnRight.setText("查看来源");
        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_DETAIL;
                if (isPostWJOrder) {goToStoreActivity();}
                else {goToOriginalSTie();}
            }
        });
        mBtnLxkf.setVisibility(View.VISIBLE);
        mBtnLxkf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerServiceActivity.start(OrderDetailActivity.this, mOrderEntity.getOrderId());
            }
        });
    }

    private void goToStoreActivity() {
//        startActivity(new Intent(this, StoreActivity.class));
        CommonWebActivity.startForStore(this);
    }

    //查看来源
    private void goToOriginalSTie() {
        if (mOrderEntity != null
                && mOrderEntity.getGoods() != null && mOrderEntity.getGoods().size() > 0
                && mOrderEntity.getGoods().get(0) != null
                && mOrderEntity.getGoods().get(0).getDropTip() != null) {
            String tipUrl = mOrderEntity.getGoods().get(0).getDropTip().getTipUrl();
            long tipid = mOrderEntity.getGoods().get(0).getDropTip().getTipId();
            long dropId = mOrderEntity.getGoods().get(0).getDropTip().getDropId();
            String goodId = mOrderEntity.getGoods().get(0).getGoodId();

            if (tipid == Constants.STORE_TIP_ID && dropId == Constants.STORE_DROP_ID) {
                GoodsDetailsActivity.start(OrderDetailActivity.this, goodId, Constants.STORE_TIP_ID, Constants.STORE_DROP_ID, Constants.STORE_TIP_TITLE);
            } else if (tipid == Constants.VIP_TIP_ID && dropId == Constants.VIP_DROP_ID) {
                GoodsDetailsActivity.start(OrderDetailActivity.this, goodId, Constants.VIP_TIP_ID, Constants.VIP_DROP_ID, Constants.VIP_TIP_TITLE);
            } else {
                CommonWebActivity.start(this, mOrderEntity.getGoods().get(0).getTipId(), tipUrl);
            }
        } else {
            ToastUtil.showShort(getResources().getString(R.string.good_unshelve));
        }
    }

    @Override
    public void setNoReceiveTypeView() {
        mTvDealStaus.setText("待收货");
        mTvTopStatus.setText("卖家已发货");
        mBtnRight.setText("确认收货");
        hideCKYT();
        checkWuLiu();
        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.confirmReceived(mOrderEntity.getOrderId());
            }
        });
        mBtnLxkf.setVisibility(View.VISIBLE);
        mBtnLxkf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerServiceActivity.start(OrderDetailActivity.this, mOrderEntity.getOrderId());
            }
        });
    }

    @Override
    public void setFinishTypeView() {
        mTvDealStaus.setText("交易完成");
        mTvTopStatus.setText("已完成");
        hideCKYT();
        mBtnRight.setText("重新购买");
        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_DETAIL;
                if (isPostWJOrder) goToStoreActivity();
                else goToOriginalSTie();
            }
        });
        checkWuLiu();
    }

    //物流界面
    private void checkWuLiu() {
        mBtnWlgz.setVisibility(View.VISIBLE);
        mBtnWlgz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.getLogiticsInfo(mOrderEntity.getOrderId());
            }
        });
    }

    @Override
    public void setExchangeType() {
        mTvDealStaus.setText("退换/售后");
        mTvTopStatus.setText("退换/售后");
        hideCKYT();
        mRlOrderBottom.setVisibility(View.GONE);
        mTvConnectKF.setVisibility(View.VISIBLE);
    }

    @Override
    public void cancelOrderSuccess() {
        setCancelOrderView();
        orderChanged = true;
    }

    @Override
    public void setUncommentTypeView() {
        mTvDealStaus.setText("待评价");
        mTvTopStatus.setText("待评价");
        hideCKYT();
        mBtnRight.setText("立即评价");
        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderEntity.ResultsBean.GoodsBean goodsBean = new OrderEntity.ResultsBean.GoodsBean();
                OrderDetailEntity2.GoodsBean bean = mOrderEntity.getGoods().get(0);
                goodsBean.setGoodCovery(bean.getGoodCovery());
                goodsBean.setGoodName(bean.getGoodName());
                goodsBean.setGoodId(bean.getGoodId());
                goodsBean.setOrderId(bean.getOrderId());
                ScoreActivity.start(OrderDetailActivity.this, goodsBean);
            }
        });
        checkWuLiu();
    }

    @Override
    public void confirmReceivedSuccess() {
        setUncommentTypeView();
    }

    @Override
    public void checkPayedSuccess() {
        if (mOrderEntity.getStatus() == 0) {
            setNoDispatchTypeView();
        }
    }

    @Override
    public void setCancelType() {
        setCancelOrderView();
    }

    @Override
    public void onGetLogiticsInfoSuccess(LogiticsEntity entity) {
        entity.setGoodPhoto(mOrderEntity.getGoods().get(0).getGoodCovery());
        LogisticsActivity.start(OrderDetailActivity.this, entity);
    }

    private void setCancelOrderView() {
        mTvDealStaus.setText("交易关闭");
        mTvTopStatus.setText("交易关闭");
        hideCKYT();
        mBtnRight.setText("重新购买");
        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_DETAIL;
                if (isPostWJOrder) goToStoreActivity();
                else goToOriginalSTie();
            }
        });
        mBtnWlgz.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_connect_kefu:
                if (!FastClickUtil.isFastDoubleClick()) {
                    CustomerServiceActivity.start(this, mOrderEntity.getOrderId());
                }
            case R.id.tv_btn_ckyt:
                if (!FastClickUtil.isFastDoubleClick()) {
                    WaterDropApp.PAY_FROM = Constants.PAY_FROM_ORDER_DETAIL;
                    if (isPostWJOrder) goToStoreActivity();
                    else goToOriginalSTie();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            finish();
        }
    }

    private void showService(List<OrderDetailEntity2.GoodsBean.GoodBean.ServiceBean> service
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
            userInfoEvent.notify = true;
            EventBus.getDefault().post(userInfoEvent);
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(UserInfoEvent userInfoEvent) {
        if (userInfoEvent.notify) {
            setNoDispatchTypeView();
            orderChanged = true;
        }
    }

}
