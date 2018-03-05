package com.drops.waterdrop.ui.mine.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.base.LazyLoadFragment;
import com.drops.waterdrop.ui.mine.adapter.FootprintPageAdapter;
import com.drops.waterdrop.ui.mine.fragment.FootprintFragment;
import com.drops.waterdrop.ui.mine.fragment.FootprintPostFragment;
import com.drops.waterdrop.widget.FlexibleViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Smile on 2017/7/3.
 */

public class FootprintActivity extends BaseActivity {
    private static final String TAG = "FootprintActivity";
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    FlexibleViewPager mViewPager;
    @Bind(R.id.tv_clean)
    TextView tvClean;
    private ArrayList<LazyLoadFragment> mFragments;

    @Override
    protected void initView() {
        initTitle();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "我的足迹";
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        initTab();
    }

    private void initTab() {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            FootprintFragment STangFragment = FootprintFragment.newInstance(getString(R.string.shui_tang));
            FootprintPostFragment STieFragment = FootprintPostFragment.newInstance(getString(R.string.shui_tie));
            mFragments.add(STangFragment);
            mFragments.add(STieFragment);
        }

        FootprintPageAdapter footprintAdapter = new FootprintPageAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(footprintAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {
        tvClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mViewPager.getCurrentItem();
                showCleanDialog(currentItem);
            }
        });
    }

    private void showCleanDialog(final int currentItem) {
        new AlertDialog.Builder(this)
                .setMessage("小主，确定清空足迹吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (currentItem == 0) {
                            if (oneKeyCleanPressListener != null) {
                                oneKeyCleanPressListener.onOneKeyCleanPressed(currentItem);
                            }
                        } else {
                            if (oneKeyCleanPressPostListener != null) {
                                oneKeyCleanPressPostListener.onOneKeyCleanPostPressed(currentItem);
                            }
                        }
                    }
                }).show();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_footprint;
    }

    private OneKeyCleanPressListener oneKeyCleanPressListener;

    public void addOneKeyPresseListener(OneKeyCleanPressListener listener) {
        this.oneKeyCleanPressListener = listener;
    }
    public interface OneKeyCleanPressListener{
        void onOneKeyCleanPressed(int type);
    }

    private OneKeyCleanPressPostListener oneKeyCleanPressPostListener;

    public void addOneKeyPressePostListener(OneKeyCleanPressPostListener listener) {
        this.oneKeyCleanPressPostListener = listener;
    }
    public interface OneKeyCleanPressPostListener{
        void onOneKeyCleanPostPressed(int type);
    }
}
