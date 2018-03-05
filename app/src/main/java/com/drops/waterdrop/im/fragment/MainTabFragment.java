package com.drops.waterdrop.im.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drops.waterdrop.R;
import com.drops.waterdrop.im.tab.MainTab;
import com.netease.nim.uikit.common.fragment.TabFragment;


public abstract class MainTabFragment extends TabFragment {

    private boolean loaded = false;

    private MainTab tabData;

    /***
     * 只有第一次被选中时才会被调用
     */
    protected abstract void onInit();

    protected boolean inited() {
        return loaded;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_tab_fragment_container, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void attachTabData(MainTab tabData) {
        this.tabData = tabData;
    }

    /**
     * 在当前页面被选中时会被调用
     */
    @Override
    public void onCurrent() {
        super.onCurrent();
        if (!loaded && loadRealLayout()) {
            loaded = true;
            onInit();
        }
    }


    private boolean loadRealLayout() {
        ViewGroup root = (ViewGroup) getView();
        if (root != null) {
            root.removeAllViewsInLayout();
            View.inflate(root.getContext(), tabData.layoutId, root);
        }
        return root != null;
    }
}
