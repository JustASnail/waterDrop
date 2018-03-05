package com.drops.waterdrop.ui.activ.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.drops.waterdrop.ui.activ.fragment.ActiveListFragment;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/4.
 */

public class ActivePageAdapter extends FragmentStatePagerAdapter {

    private List<ActiveListFragment> mFragments;
    private List<String> mTitles;

    public ActivePageAdapter(FragmentManager fm, List<ActiveListFragment> fragments, List<String> titles) {
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
        return mTitles.get(position);
    }
}
