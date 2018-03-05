package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.adapter.OrderPageAdapter;
import com.drops.waterdrop.ui.mine.fragment.AllOrderListFragment;
import com.drops.waterdrop.ui.mine.presenter.MyOrderPagePresenter;
import com.drops.waterdrop.ui.mine.view.IMyOrderPageView;
import com.drops.waterdrop.widget.FlexibleViewPager;
import com.netease.nim.uikit.Constants;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/7/5.
 */

public class MyOrderPageActivity extends BaseActivity<IMyOrderPageView, MyOrderPagePresenter> {

    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    FlexibleViewPager mViewPager;
    private ArrayList<AllOrderListFragment> mFragments;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyOrderPageActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, int type) {
        Intent starter = new Intent(context, MyOrderPageActivity.class);
        starter.putExtra(OrderListActivity.TITLE, type);
        context.startActivity(starter);
    }


    @Override
    protected void initView() {
        initTitle();

        initTab();
        int intExtra = getIntent().getIntExtra(OrderListActivity.TITLE, 0);
        if (intExtra != 0) {
            setTabSelected(intExtra);
        }
    }

    private void setTabSelected(int intExtra) {
        //为什么直接传intExtra会出现空指针  不明白
        switch (intExtra) {
            case Constants.ALL:
                mViewPager.setCurrentItem(0);
                mTabLayout.getTabAt(0).select();
                break;
            case Constants.UNPAY:
                mViewPager.setCurrentItem(1);
                mTabLayout.getTabAt(1).select();
                break;
            case Constants.UNSHIP:
                mViewPager.setCurrentItem(2);
                mTabLayout.getTabAt(2).select();
                break;
            case Constants.UNRECEIVE:
                mViewPager.setCurrentItem(3);
                mTabLayout.getTabAt(3).select();
                break;
            case Constants.UNCOMMENT:
                mViewPager.setCurrentItem(4);
                mTabLayout.getTabAt(4).select();
                break;
        }
    }

    private void initTab() {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            AllOrderListFragment allFragment = AllOrderListFragment.newInstance(getString(R.string.order_all));
            AllOrderListFragment noPaymentFragment = AllOrderListFragment.newInstance(getString(R.string.order_non_payment));
            AllOrderListFragment noDispatchFragment = AllOrderListFragment.newInstance(getString(R.string.order_unshipped));
            AllOrderListFragment noReceiveFragment = AllOrderListFragment.newInstance(getString(R.string.order_unreceive));
            AllOrderListFragment noCommentFragment = AllOrderListFragment.newInstance(getString(R.string.order_uncomment));
            mFragments.add(allFragment);
            mFragments.add(noPaymentFragment);
            mFragments.add(noDispatchFragment);
            mFragments.add(noReceiveFragment);
            mFragments.add(noCommentFragment);
        }

        OrderPageAdapter orderPageAdapter = new OrderPageAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(orderPageAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
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
    protected MyOrderPagePresenter createPresenter() {
        return new MyOrderPagePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_order_page;
    }


}
