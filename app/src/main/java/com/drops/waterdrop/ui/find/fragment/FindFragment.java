package com.drops.waterdrop.ui.find.fragment;


import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.help.GlideImageLoader;
import com.drops.waterdrop.help.UserInfoManager;
import com.drops.waterdrop.im.fragment.MainTabFragment;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.drops.waterdrop.ui.find.activity.PoolSearchActivity;
import com.drops.waterdrop.ui.find.activity.VRPlayerActivity;
import com.drops.waterdrop.ui.find.adapter.ShuiTieRecommendListAdapter;
import com.drops.waterdrop.ui.mine.activity.MemberCenterActivity;
import com.drops.waterdrop.ui.other.activity.CopyQRCodeActivity;
import com.drops.waterdrop.ui.pool.DrawerLeftAdapter;
import com.drops.waterdrop.ui.pool.view.DrawerRightAdapter;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.util.sys.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.custom.CustomAnimation;
import com.netease.nim.uikit.event.MemberCenterEvent;
import com.netease.nim.uikit.event.OnTipColletionEvent;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.CategoryEntity;
import com.netease.nim.uikit.model.PoolCategoryBean;
import com.netease.nim.uikit.model.PostListEntity;
import com.netease.nim.uikit.model.TipBannerEntity;
import com.netease.nim.uikit.model.UserCenterEntity;
import com.netease.nim.uikit.model.UserInfoEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;

/**
 * 推荐水帖
 * Created by dengxiaolei on 2017/5/15.
 */

public class FindFragment extends MainTabFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String VRURL1 = "http://pond.waterdrop.xin/RYOT.mp4";
    public static final String VRURL2 = "http://pond.waterdrop.xin/0825.mp4";
    public static final String IMG1 = "http://pond.waterdrop.xin/stlbbanner3@3x-2.png";
    public static final String IMG2 = "http://pond.waterdrop.xin/stlbbanner1@3x-2.png";
    public static final long USER1 = 54389056;
    public static final long USER2 = 98251814;
    public static final long MY_USER = 12300644;

    RecyclerView mRecyclerView;
    private LinearLayout mLlBanner;
    private Banner mBanner;
    private FrameLayout mMoreShuiTie;
    private ShuiTieRecommendListAdapter mAdapter;
    private Toolbar mToolbar;
    private AppBarLayout mAppbarLayout;

    private DrawerLayout mDrawerLayout;
    private DrawerLeftAdapter mLeftAdapter;
    private List<PoolCategoryBean> mPoolCategoryBeen;

    private AppCompatActivity mActivity;
    private DrawerRightAdapter mRightAdapter;

    private List<CategoryEntity.ResultsBean> mCategoryLists;
    private BaseRequestBody mBody;
    private String mNextSearchStart;
    private int mRealTotalSize;
    private int mCurrentCategory = -1;
    private int mCurrentTipPosition;
    private HashMap<String, Object> map;

    private String waitPlayVRUrl;


    @Override
    protected void onInit() {
        Logger.d("首页初始化：FindFragment");

        EventBus.getDefault().register(this);

        initViews();

        initToolbar();

        initList();

        initDrawerLayout();
        initData();


    }

    private void initToolbar() {
        StatusBarUtil.setTransparentForImageViewInFragment(getActivity(), mToolbar);
        mToolbar.setTitle("");

        mActivity.setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.mipmap.btn_st_sx);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });


    }

    private void initData() {
        getCategory();
        updatePosts();
        getBannerData(false);
    }

    private void getBannerData(boolean isRefresh) {
        HashMap<String, Object> map = new HashMap<>();
        Observable<BaseResponse<TipBannerEntity>> observable = HttpUtil.getInstance().sApi.getTipBannerList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<TipBannerEntity>(getContext(), !isRefresh) {
            @Override
            protected void _onNext(TipBannerEntity tipBannerEntity) {
                List<TipBannerEntity.ResultsBean> results = tipBannerEntity.getResults();
                if (results != null && results.size() > 0) {
                    if (MyUserCache.getUserUid() == USER1) {
                        initVIPBanner(results);

                    } else if (MyUserCache.getUserUid() == USER2) {
                        initVIPBanner(results);

                    } else if (MyUserCache.getUserUid() == MY_USER) {
                        initVIPBanner(results);

                    } else {
                        initBanner(results);
                    }
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    SwipeRefreshLayout mSwipeRefreshLayout;

    private void initList() {
        mSwipeRefreshLayout = findView(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setProgressViewOffset(true, -20, UIUtils.dip2Px(20));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset >= 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ShuiTieRecommendListAdapter(getActivity());
        mAdapter.openLoadAnimation(new CustomAnimation());
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mCurrentTipPosition = position;
                PostListEntity.ResultsBean item = mAdapter.getItem(position);
                CommonWebActivity.startOfHome(getContext(), item.getTipId(), item.getTipUrl(), true);
            }
        });


    }

    private void initViews() {
        mBanner = findView(R.id.banner);
        mLlBanner = findView(R.id.ll_banner);
        mAppbarLayout = findView(R.id.app_bar_layout);
        mMoreShuiTie = findView(R.id.fl_more_shui_tie);
        mRecyclerView = findView(R.id.recycler_view);
        mToolbar = findView(R.id.toolbar);

    }


    private void initVIPBanner(final List<TipBannerEntity.ResultsBean> results) {
        final ArrayList<String> imgs = new ArrayList<>();


        for (TipBannerEntity.ResultsBean result : results) {
            if (!TextUtils.isEmpty(result.getCover())) {
                imgs.add(result.getCover());
            }
        }

        imgs.add(IMG1);
        imgs.add(IMG2);

        mLlBanner.setVisibility(View.VISIBLE);
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(imgs);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Accordion);
        //设置标题集合（当banner样式有显示title时）
//        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(4000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    if (imgs.get(position).equals(IMG1)) {
                        VRPlayerActivity.start(getContext(), VRURL1);

                    } else if (imgs.get(position).equals(IMG2)) {
                        VRPlayerActivity.start(getContext(), VRURL2);

                    } else {
                        TipBannerEntity.ResultsBean bean = results.get(position);
                        if (bean.isVr()) {
                            UserCenterEntity entity = UserInfoManager.get().getData();
                            if (entity == null)
                                return;

                            if (entity.isMemberRole) {
                                VRPlayerActivity.start(getContext(), bean.getVrUrl());
                            } else {
                                waitPlayVRUrl = bean.getVrUrl();
                                MemberCenterActivity.startActivity(getActivity(), MemberCenterActivity.TYPE_CLICK_EVENT_VR);
                            }
                        } else {
                            CommonWebActivity.start(getContext(), results.get(position).getTipId(), results.get(position).getTipUrl());
                        }
                    }

                }

            }
        });
    }


    private void initBanner(final List<TipBannerEntity.ResultsBean> results) {
        ArrayList<String> imgs = new ArrayList<>();

        for (TipBannerEntity.ResultsBean result : results) {
            if (!TextUtils.isEmpty(result.getCover())) {
                imgs.add(result.getCover());
            }
        }

        mLlBanner.setVisibility(View.VISIBLE);
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(imgs);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Accordion);
        //设置标题集合（当banner样式有显示title时）
//        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(4000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!FastClickUtil.isFastDoubleClick()) {

                    TipBannerEntity.ResultsBean bean = results.get(position);
                    if (bean.isVr()) {
                        UserCenterEntity entity = UserInfoManager.get().getData();
                        if (entity == null)
                            return;

                        if (entity.isMemberRole) {
                            VRPlayerActivity.start(getContext(), bean.getVrUrl());
                        } else {
                            waitPlayVRUrl = bean.getVrUrl();
                            MemberCenterActivity.startActivity(getActivity(), MemberCenterActivity.TYPE_CLICK_EVENT_VR);
                        }
                    } else {
                        CommonWebActivity.start(getContext(), results.get(position).getTipId(), results.get(position).getTipUrl());
                    }
                }
//                VRPlayerActivity.start(getContext(),"");

            }
        });
    }


    private void initListener() {

    }


    private void initEmptyPager() {

    }

    private void initDrawerLayout() {
        mDrawerLayout = findView(R.id.drawer_layout);

        TextView drawerTitle = findView(R.id.drawer_title);
        drawerTitle.setText("筛选");

        initPoolsCategoryLeft();
        initPoolsCategoryRight();
    }

    private void initPoolsCategoryLeft() {
        RecyclerView recyclerViewLeft = findView(R.id.recycler_view_left);

        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        mLeftAdapter = new DrawerLeftAdapter(0);
        recyclerViewLeft.setAdapter(mLeftAdapter);
    }


    private void initPoolsCategoryRight() {
        final RecyclerView recyclerViewRight = findView(R.id.recycler_view_right);
        recyclerViewRight.setLayoutManager(new LinearLayoutManager(getContext()));
        mRightAdapter = new DrawerRightAdapter(0);
        recyclerViewRight.setAdapter(mRightAdapter);


        if (mLeftAdapter != null) {
            mLeftAdapter.setLeftItemSelectedListener(new DrawerLeftAdapter.OnLeftItemSelectedListener() {
                @Override
                public void onLeftItemSelectedListener(int position, CategoryEntity.ResultsBean item) {
                    List<CategoryEntity.ResultsBean.LeafsBean> leafs = item.getLeafs();
                    if (leafs != null && leafs.size() > 0) {
                        mRightAdapter.setCurrentPosition(-1);
                        mRightAdapter.setNewData(leafs);
                        recyclerViewRight.setVisibility(View.VISIBLE);

                    } else {
//                        ToastUtil.showShort("显示" + content + "的水塘");
                        recyclerViewRight.setVisibility(View.GONE);
//                        mCurrentCategory = item.getCategoryId();
                        getPostListDatas(false, false, item.getCategoryId());
                    }


                }
            });

        }

        mRightAdapter.setOnRightItemSelectedListener(new DrawerRightAdapter.OnRightItemSelectedListener() {
            @Override
            public void onRightItemSelectedListener(int position, CategoryEntity.ResultsBean.LeafsBean item) {
                if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
//                    ToastUtil.showShort("显示" + content + "的水塘");
                    getPostListDatas(false, false, item.getCategoryId());
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_recommend, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan:
                requestCameraPermission();
                break;
            case R.id.action_search:
                PoolSearchActivity.start(getContext(), Constants.POST_SEARCH_MODE);

                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void requestCameraPermission() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.setLogging(true);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            Toast.makeText(getContext(), "请开启相机和存储空间相关权限", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            CopyQRCodeActivity.start(getContext());

                        }
                    }
                });
    }


    private void getCategory() {
//        BaseRequestBody body = new BaseRequestBody();
//        body.setCategory_type(2);

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.category_type, 2);
        Observable<BaseResponse<CategoryEntity>> observable = HttpUtil.getInstance().sApi.getCategorys(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<CategoryEntity>(getContext(), false) {
            @Override
            protected void _onNext(CategoryEntity categoryEntity) {
                mCategoryLists = categoryEntity.getResults();
                if (mCategoryLists != null) {
                    updateCategory(mCategoryLists);
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    private void updateCategory(List<CategoryEntity.ResultsBean> categoryLists) {

        mLeftAdapter.setNewData(categoryLists);

        if (categoryLists.size() > 0 && categoryLists.get(0).getLeafs().size() > 0) {
            mRightAdapter.setNewData(categoryLists.get(0).getLeafs());
        }
    }

    /**
     * 根据分类获取水贴列表
     */
    public void getPostListDatas(final boolean isRefresh, final boolean isLoadMore, final int category) {
        if (isLoadMore) {
            map.put(RequestParams.search_start, mNextSearchStart);
            map.put(RequestParams.category_id, category);
        } else {
            map = new HashMap<>();
            map.put(RequestParams.category_id, category);
        }

        Observable<BaseResponse<PostListEntity>> observable = HttpUtil.getInstance().sApi.getPostLists(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<PostListEntity>(getContext(), !isLoadMore && !isRefresh) {
            @Override
            protected void _onNext(PostListEntity entity) {
                List<PostListEntity.ResultsBean> results = entity.getResults();

                if (results != null && results.size() > 0) {
                    mCurrentCategory = category;
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                    mNextSearchStart = entity.getNextSearchStart();
                    if (isLoadMore) {
                        mRealTotalSize += results.size();
                        mAdapter.addData(results);
                        mAdapter.loadMoreComplete();
                    } else {
                        mRealTotalSize = results.size();
                        mAdapter.setNewData(results);
                    }

                    if (mRealTotalSize >= entity.getTotalSize()) {
                        mAdapter.loadMoreEnd();
                    }


                } else {
                    ToastUtil.showShort("暂无该分类内容或内容已下架");
                }

                if (isRefresh) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mAdapter.setEnableLoadMore(true);
                }

                if (isLoadMore) {
                    mSwipeRefreshLayout.setEnabled(true);

                }

            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                if (isLoadMore) {
                    mAdapter.loadMoreFail();
                }

                if (isRefresh) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mAdapter.setEnableLoadMore(true);
                }

                if (isLoadMore) {
                    mSwipeRefreshLayout.setEnabled(true);

                }
            }
        });
    }


    @Override
    public void onLoadMoreRequested() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(false);
        }
        getPostListDatas(false, true, mCurrentCategory);
    }


    /**
     * 根据选择的兴趣展示水贴列表
     * 如果本地保存的有兴趣选择， 就展示兴趣选择， 如果没有就展示全部数据
     */
    private void updatePosts() {
        List<UserInfoEntity.UserLikesBean> userLikes = MyUserCache.getUserLikes();
        if (userLikes != null && userLikes.size() > 0) {
            mCurrentCategory = -1;
            getPostListDatas(false, false, -1);
        } else {
            mCurrentCategory = -1;
            getPostListDatas(false, false, -1);//展示全部数据
        }
    }


    @Override
    public void onRefresh() {
        if (mAdapter != null) {
            mAdapter.setEnableLoadMore(false);
        }
        getPostListDatas(true, false, mCurrentCategory);
        getBannerData(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //当收到推荐系统通知时 拉取本地消息更新列表
    public void onTipCollectionEvent(OnTipColletionEvent event) {
       /* if (event != null && mAdapter != null) {
            mAdapter.getData()
        }*/
        Logger.d("水贴点赞：" + event.getBrowserNum());
        if (mCurrentTipPosition != -1 && mAdapter != null) {
            mAdapter.getData().get(mCurrentTipPosition).setCollectStatus(event.getCollectStatus());
            mAdapter.getData().get(mCurrentTipPosition).setBrowserNum(event.getBrowserNum());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMemberCenterEvent(MemberCenterEvent event) {
        if (TextUtils.isEmpty(waitPlayVRUrl) || !UserInfoManager.get().getData().isMemberRole)
            return;

        VRPlayerActivity.start(getContext(), waitPlayVRUrl);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
