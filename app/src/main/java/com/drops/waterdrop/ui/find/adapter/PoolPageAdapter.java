package com.drops.waterdrop.ui.find.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.drops.waterdrop.ui.find.fragment.PoolListFragment;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/4.
 */

public class PoolPageAdapter extends FragmentStatePagerAdapter {

    private List<PoolListFragment> mFragments;
    private String[] mTitles;

    public PoolPageAdapter(FragmentManager fm, List<PoolListFragment> fragments, String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
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
        return mTitles[position];
    }
}
