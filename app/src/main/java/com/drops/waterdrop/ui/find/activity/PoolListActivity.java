package com.drops.waterdrop.ui.find.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.activ.presenter.ActiveListPresenter;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.adapter.PoolPageAdapter;
import com.drops.waterdrop.ui.find.fragment.PoolListFragment;
import com.drops.waterdrop.ui.find.presenter.PoolListPresenter;
import com.drops.waterdrop.ui.find.view.IPoolListView;
import com.netease.nim.uikit.common.util.FastClickUtil;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/5/24.
 */

public class PoolListActivity extends BaseActivity<IPoolListView, PoolListPresenter> {


    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    public static final int PAGE_SIZE = 3;

    private ArrayList<PoolListFragment> mFragments;
    private String[] mTitles = {"明星水塘", "认证水塘", "活动水塘"};

    public static void start(Context context) {
        Intent starter = new Intent(context, PoolListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle();
        initTab();

    }

    private void initTitle() {
        MyToolBarOptions myToolBarOptions = new MyToolBarOptions();
        myToolBarOptions.isNeedNavigate = true;
        myToolBarOptions.imgRightId = R.mipmap.icon_add_friend;
        myToolBarOptions.titleString = "水塘";
        setMyToolbar(myToolBarOptions);

    }

    private void initTab() {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(PoolListFragment.newInstance(ActiveListPresenter.TYPE_NOT_START));
            mFragments.add(PoolListFragment.newInstance(ActiveListPresenter.TYPE_STARTED));
            mFragments.add(PoolListFragment.newInstance(ActiveListPresenter.TYPE_END));
        }


        PoolPageAdapter pageAdapter = new PoolPageAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(1);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected PoolListPresenter createPresenter() {
        return new PoolListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pool_list;
    }


    @Override
    protected void onRightImgClick() {
        super.onRightImgClick();
        if (!FastClickUtil.isFastDoubleClick()) {
//            PoolSearchActivity.start(this);
        }
    }
}
