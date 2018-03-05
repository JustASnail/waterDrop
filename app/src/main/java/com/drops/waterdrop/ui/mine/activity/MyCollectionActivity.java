package com.drops.waterdrop.ui.mine.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.LazyLoadFragment;
import com.drops.waterdrop.ui.mine.adapter.CollectionPageAdapter;
import com.drops.waterdrop.ui.mine.fragment.CollectionBuyFragment;
import com.drops.waterdrop.ui.mine.fragment.CollectionFragment;
import com.drops.waterdrop.ui.mine.presenter.MyCollectionPresenter;
import com.drops.waterdrop.ui.mine.view.ICollectionView;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.widget.FlexibleViewPager;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/6/4.
 */

public class MyCollectionActivity extends BaseActivity<ICollectionView, MyCollectionPresenter> {

    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    FlexibleViewPager mViewPager;
    @Bind(R.id.tv_private)
    TextView tvPrivate;
    private ArrayList<LazyLoadFragment> mFragments;
    private boolean isPrivate;

    @Override
    protected void initView() {
        initTitle();
        initTab();
    }

    private void initTab() {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            CollectionFragment stFragment = CollectionFragment.newInstance(getString(R.string.collection_post));
            CollectionBuyFragment buyFragment = CollectionBuyFragment.newInstance(getString(R.string.buy_post));
            mFragments.add(stFragment);
            mFragments.add(buyFragment);
        }

        CollectionPageAdapter collectionPageAdapter = new CollectionPageAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(collectionPageAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleId = R.string.my_collection;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tvPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = tvPrivate.getText();
                if (TextUtils.equals(text, getString(R.string.set_private))) {
                    tvPrivate.setText(R.string.set_finish);
                    isPrivate = true;
                    setClickableFalse();
                } else if (TextUtils.equals(text, getString(R.string.set_finish))) {
                    tvPrivate.setText(R.string.set_private);
                    isPrivate = false;
                    setClickableTrue();
                }
                int selectedTabPosition = mTabLayout.getSelectedTabPosition();
                if (selectedTabPosition == 0) {
                    if (privateChangeListener != null) {
                        privateChangeListener.onPrivateSettingChange(isPrivate,selectedTabPosition);
                    }
                } else if (selectedTabPosition == 1) {
                    if (buyChangeListener != null) {
                        buyChangeListener.onBuyPrivateSettingChange(isPrivate, selectedTabPosition);
                    }
                }
            }
        });
    }

    private void setClickableFalse( ) {
        LinearLayout tabStrip = (LinearLayout) mTabLayout.getChildAt(0);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            View tabView = tabStrip.getChildAt(i);
            if (tabView != null) {
                tabView.setClickable(false);
            }
        }
    }

    private void setClickableTrue( ) {
        LinearLayout tabStrip = (LinearLayout) mTabLayout.getChildAt(0);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            View tabView = tabStrip.getChildAt(i);
            if (tabView != null) {
                tabView.setClickable(true);
            }
        }
    }

    @Override
    protected MyCollectionPresenter createPresenter() {
        return new MyCollectionPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_collection_page;
    }

    private PrivateChangeListener privateChangeListener;

    public void addPrivateChangeListener(PrivateChangeListener privateChangeListener) {
        this.privateChangeListener = privateChangeListener;
    }

    public interface PrivateChangeListener{
        void onPrivateSettingChange(boolean isPrivate, int tabPos);
    }

    private BuyPrivateChangeListener buyChangeListener;

    public void setPrivateChangeListener(BuyPrivateChangeListener privateChangeListener) {
        this.buyChangeListener = privateChangeListener;
    }

    public interface BuyPrivateChangeListener{
        void onBuyPrivateSettingChange(boolean isPrivate, int tabPos);
    }
}
