package com.drops.waterdrop.ui.store.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.LazyLoadFragment;
import com.drops.waterdrop.ui.store.adapter.BrandListAdapter;
import com.drops.waterdrop.ui.store.adapter.BrandPagerAdapter;
import com.drops.waterdrop.ui.store.adapter.BrandTagAdapter;
import com.drops.waterdrop.ui.store.fragment.BrandAllFragment;
import com.drops.waterdrop.ui.store.fragment.BrandItemFragment;
import com.drops.waterdrop.ui.store.presenter.ChinaPavilionPresenter;
import com.drops.waterdrop.ui.store.view.IChinaPavilionView;
import com.drops.waterdrop.widget.FlexibleViewPager;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.model.BrandTagEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Smile on 2017/9/14.
 */

public class ChinaPavilionAvtivity extends BaseActivity<IChinaPavilionView, ChinaPavilionPresenter> implements IChinaPavilionView,View.OnClickListener, TagFlowLayout.OnTagClickListener, BrandListAdapter.OnBrandClickListener {

    private static final String TAG = "Smile";
    private TabLayout mTabLayout;
    private ImageView mIvBack;
    private FlexibleViewPager mViewPager;
    private List<LazyLoadFragment> mFragments;
    private RelativeLayout mRlMore;
    private ImageView mIvWhite;
    private LinearLayout mLlTag;
    private TagFlowLayout mTagFlow;
    private BrandTagAdapter mBrandTagAdapter;
    private boolean canShow = true;
    private BrandAllFragment allFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catchException();
    }

    @Override
    protected void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mViewPager = (FlexibleViewPager) findViewById(R.id.view_pager);
        mRlMore = (RelativeLayout) findViewById(R.id.rl_more);
        mIvWhite = (ImageView) findViewById(R.id.iv_white);
        mLlTag = (LinearLayout) findViewById(R.id.ll_tag);
        mTagFlow = (TagFlowLayout) findViewById(R.id.tag_flow_layout);
    }

    @Override
    protected void initData() {
        mPresenter.getBrandList();
    }

    private void initTagFlow(List<BrandTagEntity.ResultsBean> list) {
        mBrandTagAdapter = new BrandTagAdapter(list);
        mTagFlow.setOnTagClickListener(this);
    }

    private void initTab(List<BrandTagEntity.ResultsBean> list) {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            allFragment = BrandAllFragment.newInstance("全部品牌");
            mFragments.add(allFragment);
            for (BrandTagEntity.ResultsBean s : list) {
                mFragments.add(BrandItemFragment.newInstance(s.getBrandName(), list.indexOf(s), s.getBrandId()));
            }
        }

        PagerAdapter adapter = new BrandPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
        allFragment.setOnBrandClickListener(this);
    }

    @Override
    protected void initListener() {
        mRlMore.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
    }

    @Override
    protected ChinaPavilionPresenter createPresenter() {
        return new ChinaPavilionPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_china_pavilion;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_more:
                if (canShow) {
                    mIvWhite.setVisibility(View.VISIBLE);
                    mLlTag.setVisibility(View.VISIBLE);
                    enableTab(false);
                    enableViewPager(false);
                    if (mBrandTagAdapter != null) mTagFlow.setAdapter(mBrandTagAdapter);
                    canShow = false;
                } else {
                    mIvWhite.setVisibility(View.GONE);
                    mLlTag.setVisibility(View.GONE);
                    enableTab(true);
                    enableViewPager(true);
                    canShow = true;
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void enableTab(boolean enable) {
        LinearLayout tabStrip = (LinearLayout) mTabLayout.getChildAt(0);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            View tabView = tabStrip.getChildAt(i);
            if (tabView != null) {
                tabView.setClickable(enable);
            }
        }
    }

    private void enableViewPager(boolean enable) {
        mViewPager.setCanScroll(enable);
    }

    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        mIvWhite.setVisibility(View.GONE);
        mLlTag.setVisibility(View.GONE);
        enableViewPager(true);
        enableTab(true);
        mViewPager.setCurrentItem(position + 1);
        canShow = true;
        return true;
    }

    @Override
    public void onBrandClickListener(int pos) {
        mViewPager.setCurrentItem(pos + 1);
    }

    @Override
    public void onGetBrandTagList(List<BrandTagEntity.ResultsBean> results) {
        initTab(results);
        initTagFlow(results);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBrandTagAdapter != null) {
            mBrandTagAdapter = null;
        }
        if (mFragments != null) {
            mFragments.clear();
            mFragments = null;
        }
        MyUserCache.saveChinaPavilionActivityActive(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyUserCache.saveChinaPavilionActivityActive(true);
    }

    final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    private void catchException() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                MyUserCache.saveChinaPavilionActivityActive(false);
                // Handle everthing else
                defaultHandler.uncaughtException(thread, throwable);
            }
        });
    }

}
