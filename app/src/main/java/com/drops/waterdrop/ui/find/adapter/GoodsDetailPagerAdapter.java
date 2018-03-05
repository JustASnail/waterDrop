package com.drops.waterdrop.ui.find.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/7.
 */

public class GoodsDetailPagerAdapter extends PagerAdapter {

    private List<View> views;
    private List<String> titiles;

    public GoodsDetailPagerAdapter(List<View> views, List<String> titles) {
        this.views=views;
        this.titiles=titles;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 实例化 一个 页卡
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 添加一个 页卡
        View child = views.get(position);
        child.setId(position);
        container.addView(child);

        return views.get(position);
    }

/*    *//**
     * 销毁 一个 页卡
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 删除
//        ImageLoader.getInstance().clearDiskCache();
//        ImageLoader.getInstance().clearMemoryCache();
        container.removeView(views.get(position));
    }

    /**
     *  重写 标题的 方法
     */
    @Override
    public CharSequence getPageTitle(int position) {
        // 给页面添加标题
        return titiles.get(position);
    }
}
