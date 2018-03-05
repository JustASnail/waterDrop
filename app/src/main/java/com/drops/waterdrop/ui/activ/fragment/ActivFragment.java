package com.drops.waterdrop.ui.activ.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.im.fragment.MainTabFragment;
import com.drops.waterdrop.ui.activ.adapter.ActivePageAdapter;
import com.drops.waterdrop.ui.activ.presenter.ActiveListPresenter;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by dengxiaolei on 2017/5/15.
 */

public class ActivFragment extends MainTabFragment {
    TextView mTvTitle;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    private ArrayList<ActiveListFragment> mFragments;
    private ArrayList<String> mTitles;

    @Override
    protected void onInit() {
        initViews();

        initTab();

    }

    private void initTab() {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(ActiveListFragment.newInstance(ActiveListPresenter.TYPE_NOT_START));
            mFragments.add(ActiveListFragment.newInstance(ActiveListPresenter.TYPE_STARTED));
            mFragments.add(ActiveListFragment.newInstance(ActiveListPresenter.TYPE_END));
        }
        if (mTitles == null) {
            mTitles = new ArrayList<>();
            mTitles.add("未开始");
            mTitles.add("疯抢中");
            mTitles.add("已结束");
        }

        ActivePageAdapter pageAdapter = new ActivePageAdapter(getFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(1);
    }


    private void initViews() {
        View statusBarFix = findView(R.id.status_bar_fix);
        statusBarFix.setBackgroundResource(R.color.colorPrimary);
        statusBarFix.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getStatusBarHeight(getActivity())));//填充状态栏

        mTvTitle = findView(R.id.tv_commn_title);
        mTabLayout = findView(R.id.tab_layout);
        mViewPager = findView(R.id.viewPager);
        mTvTitle.setText("活动");
    }


}
