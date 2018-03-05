package com.drops.waterdrop.ui.base;

import android.support.v4.app.Fragment;

/**
 * Fragment的懒加载
 * Created by ended on 2017/3/20.
 */

abstract class LazyFragment extends Fragment {

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;

    /**
     * 适用于ViewPager里的fragment的切换
     * 调用时机：ViewPager里的fragment第一次初始化时会被调用一次。 当fragment被切换时会被调用
     * @param isVisibleToUser true表示显示， false表示隐藏
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

/*
    */
/**
     * 适用于fragment之间通过replace和hide方式来切换
     * 这个方法调用时机： 当前fragment调用hide时才会调用
     * @param hidden true表示隐藏， false表示显示
     *//*

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
*/

    /**
     * 不可见时
     */
    private void onInvisible() {

    }

    /**
     * 可见时
     */
    private void onVisible() {
        lazyLoad();
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

}
