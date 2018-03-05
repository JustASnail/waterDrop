package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.help.UserInfoManager;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.event.AvailableMoneyChangeEvent;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.model.UserCenterEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/8/24.
 */

public class MineBankActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.tv_withdraw_history)
    TextView tvWithdrawHistory;
    @Bind(R.id.tv_withdraw)
    TextView tvWithdraw;
    @Bind(R.id.tv_bank_account)
    TextView tvBankAccount;
    @Bind(R.id.tv_account_detail)
    TextView tvAccountDetail;
    @Bind(R.id.tv_balance)
    TextView tvBalance;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "我的账户";
        setMyToolbar(options);

        mSwipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
    }

    @Override
    protected void initData() {
        getBanlance(false);
    }

    private void getBanlance(final boolean isRefresh) {
        UserInfoManager.get().autoGet(new UserInfoManager.Callback() {
            @Override
            public void onGet(UserCenterEntity entity) {
                if (isRefresh && mSwipeRefresh.isRefreshing()) {
                    mSwipeRefresh.setRefreshing(false);
                }
                if (MyUserCache.getVipOrSuply() == 2) {
                    tvBalance.setText(entity.getAvailableMemberMoney());
                } else {
                    tvBalance.setText(entity.getAvailableMoney());
                }
            }

            @Override
            public void onError(String msg) {
                if (isRefresh && mSwipeRefresh.isRefreshing()) {
                    mSwipeRefresh.setRefreshing(false);
                }
                ToastUtil.showShort(msg);
            }
        }, isRefresh);
    }

    @Override
    protected void initListener() {
        tvAccountDetail.setOnClickListener(this);
        tvBankAccount.setOnClickListener(this);
        tvWithdraw.setOnClickListener(this);
        tvWithdrawHistory.setOnClickListener(this);
        mSwipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_mine_bank;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MineBankActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_account_detail:
                startActivity(new Intent(this,AccountDetailActivity.class));
                break;
            case R.id.tv_bank_account:
                BankCardListActivity.startActivity(this);
                break;
            case R.id.tv_withdraw:
                startActivity(new Intent(this,WithDrawActivity.class));
                break;
            case R.id.tv_withdraw_history:
                startActivity(new Intent(this,WithDrawHistoryActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh() {
        getBanlance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(AvailableMoneyChangeEvent event) {
        onRefresh();
    }
}

