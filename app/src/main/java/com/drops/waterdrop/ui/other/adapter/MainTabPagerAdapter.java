package com.drops.waterdrop.ui.other.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.drops.waterdrop.im.fragment.MainTabFragment;
import com.drops.waterdrop.im.tab.MainTab;
import com.drops.waterdrop.im.tab.SlidingTabPagerAdapter;

import java.util.List;

/**
 * 主页viewpager的adapter
 */
public class MainTabPagerAdapter extends SlidingTabPagerAdapter {

	@Override
	public int getCacheCount() {
		return MainTab.values().length;
	}

	public MainTabPagerAdapter(FragmentManager fm, Context context, ViewPager pager) {
		super(fm, MainTab.values().length, context.getApplicationContext(), pager);

		for (MainTab tab : MainTab.values()) {
			try {
				MainTabFragment fragment = null;

				List<Fragment> fs = fm.getFragments();
				if (fs != null) {
					for (Fragment f : fs) {
						if (f.getClass() == tab.clazz) {
							fragment = (MainTabFragment) f;
							break;
						}
					}
				}

				if (fragment == null) {
					fragment = tab.clazz.newInstance();//核心 创建出MainTab里的每一个fragment
				}

				fragment.setState(this);
				fragment.attachTabData(tab);

				fragments[tab.tabIndex] = fragment;									//核心、、、
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getCount() {
		return MainTab.values().length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		MainTab tab = MainTab.fromTabIndex(position);

		int resId = tab != null ? tab.resId : 0;

		return resId != 0 ? context.getText(resId) : "";
	}


}