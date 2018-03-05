package com.drops.waterdrop.ui.find.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.adapter.WatchPicViewPageAdapter;
import com.drops.waterdrop.ui.find.presenter.WatchPicPresenter;
import com.drops.waterdrop.ui.find.view.IWatchPicView;
import com.drops.waterdrop.widget.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/5/27.
 */

public class WatchPicActivity extends BaseActivity<IWatchPicView, WatchPicPresenter> implements IWatchPicView, ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final String EXTRA_IMG_URLS = "IMG_URLS";
    public static final String EXTRA_POSITION = "CURRENT_POSITION";
    @Bind(R.id.viewPager)
    ViewPagerFixed mViewPager;
    @Bind(R.id.hint)
    TextView mHint;

    private WatchPicViewPageAdapter adapter;



    public static void start(Context context, ArrayList<String> imgUrls, int position, View view) {
        Intent starter = new Intent(context, WatchPicActivity.class);
        starter.putStringArrayListExtra(EXTRA_IMG_URLS, imgUrls);
        starter.putExtra(EXTRA_POSITION, position);
        context.startActivity(starter,
                ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,view,"shareAnim").toBundle());
    }

    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPresenter.loadImage();

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected WatchPicPresenter createPresenter() {
        return new WatchPicPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_watch_pic;
    }





    @Override
    public Intent getDataIntent() {
        return getIntent();
    }

    @Override
    public void setImageBrowse(List<String> images, int position) {
        if (adapter == null && images != null && images.size() != 0) {
            adapter = new WatchPicViewPageAdapter(this, images);
            mViewPager.setAdapter(adapter);
            mViewPager.setCurrentItem(position);
            mViewPager.addOnPageChangeListener(this);
            mHint.setText(position + 1 + "/" + images.size());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPresenter.setPosition(position);
        mHint.setText(position + 1 + "/" + mPresenter.getImages().size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 欢迎页 沉浸模式
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
