package com.drops.waterdrop.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ended on 2017/4/7.
 * 扩展动态控制 ViewPager 滑动使能功能
 */
public class FlexibleViewPager extends ViewPager {

    private boolean mIsCanScroll = true;


    public FlexibleViewPager(Context context) {
        super(context);
    }

    public FlexibleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!mIsCanScroll) {
            // Never allow swiping to switch between pages
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mIsCanScroll) {
            // Never allow swiping to switch between pages
            return false;
        }
        return super.onTouchEvent(event);
    }

    /**
     *
     * @return 返回当前viewpager是否可以左右滑动
     */
    public boolean isCanScroll() {
        return mIsCanScroll;
    }

    /**
     * 设置当前viewpager是否可以滑动
     * @param canScroll
     */
    public void setCanScroll(boolean canScroll) {
        mIsCanScroll = canScroll;
    }
}