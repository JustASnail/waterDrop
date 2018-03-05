package com.drops.waterdrop.ui.find.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/8.
 */

public class GoodsDetailsViewPagerAdapter extends PagerAdapter {

    private List<View> mViews;
    private List<String> mTitles;

    public GoodsDetailsViewPagerAdapter(List<View> views, List<String> titles) {
        mViews = views;
        mTitles = titles;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(View view, int position, Object object)                       //销毁Item
    {
        ((ViewPager) view).removeView(mViews.get(position));
    }

    @Override
    public Object instantiateItem(View view, int position)                                //实例化Item
    {
        ((ViewPager) view).addView(mViews.get(position));

        return mViews.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
