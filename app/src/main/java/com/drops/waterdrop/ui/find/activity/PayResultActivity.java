package com.drops.waterdrop.ui.find.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.presenter.PayResultPresenter;
import com.drops.waterdrop.ui.find.view.IPayResultView;
import com.drops.waterdrop.ui.mine.activity.MemberCenterActivity;
import com.drops.waterdrop.ui.mine.activity.MyOrderPageActivity;
import com.drops.waterdrop.ui.mine.activity.OrderDetailActivity;
import com.drops.waterdrop.ui.mine.activity.OrderListActivity;
import com.drops.waterdrop.ui.mine.event.BuyMemberEvent;
import com.drops.waterdrop.ui.mine.event.UserInfoEvent;
import com.drops.waterdrop.ui.other.activity.MainActivity;
import com.drops.waterdrop.ui.store.activity.ChinaPavilionAvtivity;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/6/9.
 */

public class PayResultActivity extends BaseActivity<IPayResultView, PayResultPresenter> {

    private static final int REQUEST_PAY = 100;
    @Bind(R.id.iv_pay_top_icon)
    ImageView mIvPayTopIcon;
    @Bind(R.id.tv_pay_result)
    TextView mTvPayResult;
    @Bind(R.id.tv_pay_result_desc)
    TextView mTvPayResultDesc;
    @Bind(R.id.iv_pay_bottom_img)
    ImageView mIvPayBottomImg;
    private boolean mPayResult;

    public static void start(Context context, boolean isSucceed) {
        Intent starter = new Intent(context, PayResultActivity.class);
        starter.putExtra(Constants.EXTRA_PAY_RESULT, isSucceed);
        ((Activity) context).startActivityForResult(starter, REQUEST_PAY);
    }

    @Override
    protected void initView() {
        mPayResult = getIntent().getBooleanExtra(Constants.EXTRA_PAY_RESULT, false);
        initTitle();

        mIvPayTopIcon.setImageResource(mPayResult?R.mipmap.icon_dd_zfcg : R.mipmap.icon_dd_zfsb);
        mIvPayBottomImg.setImageResource(mPayResult?R.mipmap.img_dd_zfcg : R.mipmap.img_dd_zfsb);

        mTvPayResult.setText(mPayResult ? "支付成功":"支付失败");
        mTvPayResultDesc.setText(mPayResult ? "感谢您在水滴平台购买商品":"支付遇到问题,请尝试重新购买");
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleId = R.string.pay_result;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        findViewById(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payBackTo();
            }
        });
    }

    private void buyMemberEvent(){
        if (mPayResult){
            EventBus.getDefault().post(new BuyMemberEvent(mPayResult));
        }
        finish();
    }

    private void payBack() {
        if (mPayResult) {
            UserInfoEvent userInfoEvent = new UserInfoEvent();
            userInfoEvent.notify = true;
            EventBus.getDefault().post(userInfoEvent);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        payBackTo();
    }

    private void payBackTo() {
        if (WaterDropApp.PAY_FROM== Constants.PAY_FROM_TIP_DETAIL) {
            CommonWebActivity.start(PayResultActivity.this, WaterDropApp.tipId, WaterDropApp.tipUrl);
            finish();
        }else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_ORDER_LIST) {
//            payBack();
            startActivity(new Intent(PayResultActivity.this,MyOrderPageActivity.class));
        } else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_ORDER_DETAIL) {
//            payBack();
            startActivity(new Intent(PayResultActivity.this,OrderDetailActivity.class));
        } else {
            if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_CHINA_PAVILION) {
                if (MyUserCache.isChinaPavilionActivityActive()) {
                    startActivity(new Intent(PayResultActivity.this, ChinaPavilionAvtivity.class));
                } else {
                    MainActivity.start(PayResultActivity.this);
                }
                finish();
            } else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_VIP_AREA) {
                MemberCenterActivity.startActivity(PayResultActivity.this);
                finish();
            } else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_MEMBER_CENTER) {
                buyMemberEvent();
            } else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_CHINA_PAVILION_H5) {
                WaterDropApp.PAY_FROM_H5 = -1;
                CommonWebActivity.startOfActive(PayResultActivity.this, WaterDropApp.tipUrl);
                finish();
            } else if (WaterDropApp.PAY_FROM == Constants.PAY_FROM_CHINA_PAVILION_HOME) {
                MainActivity.start(PayResultActivity.this);
            }
        }
    }

    @Override
    protected PayResultPresenter createPresenter() {
        return new PayResultPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_order_result;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

}
