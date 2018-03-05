package com.drops.waterdrop.ui.mine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.drops.waterdrop.ui.mine.fragment.AllOrderListFragment;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/4.
 */

public class OrderPageAdapter extends FragmentStatePagerAdapter {

    private List<AllOrderListFragment> mFragments;

    public OrderPageAdapter(FragmentManager fm, List<AllOrderListFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments == null) return null;
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        if (mFragments == null) return 0;
        return mFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}
