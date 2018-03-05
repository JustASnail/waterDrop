package com.drops.waterdrop.ui.store.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.drops.waterdrop.ui.base.LazyLoadFragment;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/9/14.
 */

public class BrandPagerAdapter extends FragmentStatePagerAdapter {
    private List<LazyLoadFragment> mFragments;

    public BrandPagerAdapter(FragmentManager fm, List<LazyLoadFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments == null ? null : mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments == null ? "" : mFragments.get(position).getTitle();
    }
}
