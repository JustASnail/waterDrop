package com.drops.waterdrop.ui.other.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.drops.waterdrop.R;
import com.drops.waterdrop.help.UserInfoManager;
import com.drops.waterdrop.help.WxAvatarUrlHelper;
import com.drops.waterdrop.im.reminder.ReminderItem;
import com.drops.waterdrop.im.tab.PagerSlidingTabStrip;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.activity.MemberCenterActivity;
import com.drops.waterdrop.ui.mine.event.UserInfoEvent;
import com.drops.waterdrop.ui.other.adapter.MainTabPagerAdapter;
import com.drops.waterdrop.ui.other.presenter.MainPresenter;
import com.drops.waterdrop.ui.other.view.IMainView;
import com.drops.waterdrop.ui.session.activity.ContactListActivity;
import com.drops.waterdrop.ui.store.fragment.StoreSelfFragment;
import com.drops.waterdrop.widget.FlexibleViewPager;
import com.jaeger.library.StatusBarUtil;
import com.microquation.linkedme.android.LinkedME;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.event.H5CameraClickEvent;
import com.netease.nim.uikit.event.MemberCenterEvent;
import com.netease.nim.uikit.guideview.Component;
import com.netease.nim.uikit.guideview.Guide;
import com.netease.nim.uikit.guideview.GuideBuilder;
import com.netease.nim.uikit.guideview.component.BtnButtomComponent;
import com.netease.nim.uikit.guideview.component.BtnComponent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;


public class MainActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView, ViewPager.OnPageChangeListener {
/*
    @Bind(R.id.unread_cover)
    DropCover mUnreadCover;*/

    @Bind(R.id.tag_view)
    ImageView mTagView;
    @Bind(R.id.tabs)
    PagerSlidingTabStrip mTabLayout;
    @Bind(R.id.main_tab_pager)
    FlexibleViewPager mViewPager;

    private MainTabPagerAdapter adapter;
    private int scrollState;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        mPresenter.parseIntent(getIntent());
    }

    @Override
    public void onResume() {
        super.onResume();
        //此处针对跳转是否受用户登录限制的情况
        if (!TextUtils.isEmpty(MyUserCache.getIMAccount())) {
            //已登录用户可以跳转到分享页面
            Logger.d("首页：onResume已登陆");
            LinkedME.getInstance().setImmediate(true);
            mPresenter.enableMsgNotification(false, mViewPager.getCurrentItem());
        } else {
            Logger.d("首页：onResume未登陆");

            //未登录用户不跳转到分享页面，而是跳转到登录页面，登录成功后跳转到分享页面
            //未登录用户不自动跳转
            LinkedME.getInstance().setImmediate(false);
            LoginActivity.start(this);
            finish();
        }
        //quitOtherActivities();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.enableMsgNotification(true, mViewPager.getCurrentItem());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unRegisterObservers();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void initView() {

        StatusBarUtil.setTransparentForImageViewInFragment(this, null);//设置状态栏透明， 并且toolbar向下偏移状态栏的高度

        View statusBarFix = findView(R.id.status_bar_fix);
        statusBarFix.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getStatusBarHeight(this)));//填充状态栏

        setupPager();

        setupTabs();
    }

    @Override
    protected void initData() {
//        requestBasicPermission();

        mPresenter.parseIntent(getIntent());//解析intent

        mPresenter.syncData();//同步app数据

        mPresenter.registerObservers();

//        mPresenter.initUnreadCover(mUnreadCover);//初始化未读红点动画


        /**
         * 检测头像url是否是微信url
         */
        WxAvatarUrlHelper.get().checkWxAvatarUrl(this, MyUserCache.getUserPhoto(), true, null);
        //提前请求出个人中心数据
        UserInfoManager.get().autoGet(null, true);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    /**
     * 设置viewPager
     */
    private void setupPager() {
        // CACHE COUNT
        adapter = new MainTabPagerAdapter(getSupportFragmentManager(), this, mViewPager);
        mViewPager.setOffscreenPageLimit(adapter.getCacheCount());
        //pager跳转动画
//        pager.setPageTransformer(true, new FadeInOutPageTransformer());
        // ADAPTER
        mViewPager.setAdapter(adapter);
        mViewPager.setPageMargin(ScreenUtil.dip2px(16));

        //设置viewpager是否可有左右滑动
        mViewPager.setCanScroll(false);
        // TAKE OVER CHANGE
        mViewPager.setOnPageChangeListener(this);
        mTabLayout.setFirstTabClickListener(new PagerSlidingTabStrip.OnFirstTabClickListener() {
            @Override
            public void onClickListener() {
                showGuideView();
            }
        });

    }

    /**
     * 设置tab条目的内容
     */
    private void setupTabs() {
        mTabLayout.setIndicatorHeight(0);
        mTabLayout.setOnCustomTabListener(new PagerSlidingTabStrip.OnCustomTabListener() {
            @Override
            public int getTabLayoutResId(int position) {
                switch (position) {
                    case 0:
                        return R.layout.tab_layout_main_message;
                    case 1:
                        return R.layout.tab_layout_main_find;
                    case 2:
                        return R.layout.tab_layout_main_home;
                    case 3:
                        return R.layout.tab_layout_main_activ;
                    case 4:
                        return R.layout.tab_layout_main_mine;
                }
                return R.layout.tab_layout_main_message;
            }

            @Override
            public View getTabLayoutView(LayoutInflater inflater, int position) {

                return super.getTabLayoutView(inflater, position);
            }

            @Override
            public boolean screenAdaptation() {
                return true;
            }
        });
        mTabLayout.setViewPager(mViewPager);
        mTabLayout.setOnTabClickListener(adapter);
        mTabLayout.setOnTabDoubleTapListener(adapter);

        mViewPager.setCurrentItem(2);

    }

    /**
     * 基本权限管理
     */




    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // TO TABS
        mTabLayout.onPageScrolled(position, positionOffset, positionOffsetPixels);
        // TO ADAPTER
        adapter.onPageScrolled(position);
    }

    @Override
    public void onPageSelected(int position) {
        // TO TABS
        mTabLayout.onPageSelected(position);

        selectPage(position);

        mPresenter.enableMsgNotification(false, mViewPager.getCurrentItem());

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TO TABS
        mTabLayout.onPageScrollStateChanged(state);

        scrollState = state;

        selectPage(mViewPager.getCurrentItem());
    }

    private void selectPage(int page) {
        // TO PAGE
        if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
            adapter.onPageSelected(mViewPager.getCurrentItem());
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.clear();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == StoreSelfFragment.PICK_AVATAR_REQUEST) {
            H5CameraClickEvent event = new H5CameraClickEvent();
            if (data == null) {
                event.setImgPath(null);
                EventBus.getDefault().post(event);
            } else {
                String path = data.getStringExtra(com.netease.nim.uikit.session.constant.Extras.EXTRA_FILE_PATH);

                event.setImgPath(path);
                EventBus.getDefault().post(event);
            }
        } else if (requestCode == MemberCenterActivity.REQUEST_CODE_VR) {
            EventBus.getDefault().post(new MemberCenterEvent());
        }
    }

    @Override
    public void onBackPressed() {

//        if (mViewPager.getCurrentItem() == 3) {
//            if (backPressListener != null) {
//                backPressListener.onBackPressed();
//            }
//        } else {
            moveTaskToBack(true);
//        }
    }

    @Override
    public void onUnreadNumChanged(int tabIndex, ReminderItem item) {
        mTabLayout.updateTab(tabIndex, item);
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    public void setBackPressListener(OnBackPressListener backPressListener) {
        this.backPressListener = backPressListener;
    }

    public interface OnBackPressListener{
        void onBackPressed();
    }

    private OnBackPressListener backPressListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEventMainThread(UserInfoEvent userInfoEvent) {
        if (userInfoEvent.cannotBack) {
            moveTaskToBack(true);
        }
    }

    public void showGuideView() {

        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(mTagView)
                .setFullingViewId(R.id.main_container)
                .setAlpha(200)
                .setHighTargetCorner(0)
                .setAutoDismiss(false)
                .setHighTargetPadding(ScreenUtil.dip2px(3))
                .setHighTargetGraphStyle(Component.CIRCLE)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        BtnComponent component = new BtnComponent();
        builder.addComponent(component);
        builder.addComponent(new BtnButtomComponent());
        final Guide guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(this);

        component.setListener(new BtnComponent.OnViewClickListener() {
            @Override
            public void onClickListener() {
                if (!FastClickUtil.isFastDoubleClick()) {
                    ContactListActivity.start(MainActivity.this);
                }

                guide.dismiss();

            }
        });
    }

}
