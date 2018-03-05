package com.drops.waterdrop.ui.other.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.widget.entry.BezierIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/5/10.
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.container)
    ViewPager mViewPager;

    @Bind(R.id.btn_enter)
    TextView mBtnEnter;

    private List<View> mViews = new ArrayList<>();
    private String[] titles1 = {"在水滴， 更靠谱", "在水滴， 不纠结",  "在水滴， 无假货"};
    private String[] titles2 = {"精准分享 可信圈子", "明星网红 替你选择", "全球好货 品牌直送"};
    private int[] imgIds = {R.mipmap.yd_bg_1, R.mipmap.yd_bg_2, R.mipmap.yd_bg_3};
    private List<ImageView> mImageViews = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, WelcomeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        initlayout();
//        initpoint();

        ViewpagerAdapter mSectionsPagerAdapter = new ViewpagerAdapter();
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);


        BezierIndicator bezierIndicator = (BezierIndicator) findViewById(R.id.bezierIndicator);
        bezierIndicator.setButton(mBtnEnter);
        bezierIndicator.setUpWithViewPager(mViewPager);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivityAndClearTask(LoginActivity.class);

            }
        });

    }

    private void initlayout() {
        for (int i = 0; i < imgIds.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_guide1, null);
            TextView title1 = (TextView) view.findViewById(R.id.tv_title1);
            TextView title2 = (TextView) view.findViewById(R.id.tv_title2);
            ImageView img = (ImageView) view.findViewById(R.id.iv_guide_img);
            title1.setText(titles1[i]);
            title2.setText(titles2[i]);
            switch (i) {
                case 0:
                    title1.setTextColor(Color.parseColor("#8DDEF4"));
                    title2.setTextColor(getResources().getColor(R.color.color_guide1));
                    break;
                case 1:
                    title1.setTextColor(Color.parseColor("#BFC5F5"));
                    title2.setTextColor(getResources().getColor(R.color.color_guide2));
                    break;
                case 2:
                    title1.setTextColor(Color.parseColor("#BCF3E9"));
                    title2.setTextColor(getResources().getColor(R.color.color_guide3));
                    break;
            }

            img.setImageResource(imgIds[i]);
            mViews.add(view);
        }

    }

/*
    private void initpoint() {
        //获取layout
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置每一个view即圆点的对左的偏移量
        params.setMargins(15, 0, 0, 0);
        params.height = UIUtils.dip2Px(8);
        params.width = UIUtils.dip2Px(8);
        //根据图片多少来确定个数
        for (int i = 0; i < imgIds.length; i++) {

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.dot_select);
            imageView.setLayoutParams(params); //把上面的控件属性设置到LinearLayout中
            if (i == 0) { //默认第一张为红色圆点
                imageView.setSelected(true);
            } else {
                imageView.setSelected(false);
            }
            //把圆点这个子视图导入我们的LinearLayout里面
            mPointLy.addView(imageView);
            mImageViews.add(imageView);//跟着viewpager变换颜色

        }
    }
*/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      /*  //滑动时改变圆点的状态
        for (int i = 0; i < mImageViews.size(); i++) {
            if (i == position) {
                mImageViews.get(i).setSelected(true);
            } else {
                mImageViews.get(i).setSelected(false);
            }
        }
       */

        //当为最后一个时，显示button，并隐藏圆点
      /*  if (position == mImageViews.size() - 1) {
            System.out.println("最后");
            mBtnEnter.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(mBtnEnter, "alpha", 0f, 1f);
            animator.setDuration(1000);
            animator.start();
        } else {
            mBtnEnter.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class ViewpagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // return super.instantiateItem(container, position);
            container.addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView(mViews.get(position));
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guide;
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

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
