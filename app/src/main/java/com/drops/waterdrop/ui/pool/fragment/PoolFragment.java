package com.drops.waterdrop.ui.pool.fragment;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.drops.waterdrop.R;
import com.drops.waterdrop.im.fragment.MainTabFragment;
import com.drops.waterdrop.ui.find.activity.PoolSearchActivity;
import com.drops.waterdrop.ui.other.activity.CopyQRCodeActivity;
import com.drops.waterdrop.ui.pool.DrawerLeftAdapter;
import com.drops.waterdrop.ui.pool.other.PoolCardHandler;
import com.drops.waterdrop.ui.pool.view.DrawerRightAdapter;
import com.drops.waterdrop.ui.pool_card.CardViewPager;
import com.drops.waterdrop.util.ToastUtil;
import com.google.gson.Gson;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.dialog.GridViewDialog;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.CategoryEntity;
import com.netease.nim.uikit.model.InterestChekedEntity;
import com.netease.nim.uikit.model.InterestEntity;
import com.netease.nim.uikit.model.PoolCategoryBean;
import com.netease.nim.uikit.model.RecommendPoolListEntity;
import com.netease.nim.uikit.model.UserInfoEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by dengxiaolei on 2017/5/10.
 */

public class PoolFragment extends MainTabFragment implements View.OnClickListener {


    private DrawerLayout mDrawerLayout;
    private DrawerLeftAdapter mLeftAdapter;
    private List<PoolCategoryBean> mPoolCategoryBeen;

    private AppCompatActivity mActivity;
    private List<CategoryEntity.ResultsBean> mCategoryLists;
    private DrawerRightAdapter mRightAdapter;
    private CardViewPager mViewPager;
    private ImageView mIvLeft;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        onCurrent();//提前加载onInit()里的数据


    }


    @Override
    protected void onInit() {
        Logger.d("首页初始化：PoolFragment" );

        initViews();
        requestBasicPermission();
        List<UserInfoEntity.UserLikesBean> likes = MyUserCache.getUserLikes();
        if (likes == null || likes.size() < 1) {
            initInterestDialog();
        } else {
            getPoolListDatas(-1);
        }


    }

    private void initInterestDialog() {
        getInterests();
    }

    private void getInterests() {
        HashMap<String, Object> map = new HashMap<>();
        Observable<BaseResponse<InterestEntity>> observable = HttpUtil.getInstance().sApi.getInterests(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<InterestEntity>(getContext(), false) {
            @Override
            protected void _onNext(InterestEntity interestEntity) {
                List<InterestEntity.ResultsBean> results = interestEntity.getResults();
                if (results != null && results.size() > 0) {
                    GridViewDialog dialog = new GridViewDialog(getContext(), "选择您感兴趣的内容", results, new GridViewDialog.OnOkClickListener() {
                        @Override
                        public void onClick(Dialog dialog, List<InterestChekedEntity> lists) {
                            putInterests(dialog, lists);

                        }
                    });
                    dialog.show();
                }

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    /**
     * 把选择的兴趣提交到服务器
     *
     * @param dialog
     * @param lists
     */
    private void putInterests(final Dialog dialog, final List<InterestChekedEntity> lists) {

        if (lists != null && lists.size() > 0) {
            List<Integer> categorys = new ArrayList<>();
            for (InterestChekedEntity list : lists) {
                categorys.add(list.getCategoryId());
            }

            Gson gson = new Gson();
            String json = gson.toJson(categorys);
            HashMap<String, Object> map = new HashMap<>();
            map.put(RequestParams.category_like_json, json);
            Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.putInterests(RequestBodyUtils.build(map));
            HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(getContext()) {
                @Override
                protected void _onNext(Object o) {
                    dialog.dismiss();

                    saveInterests(lists);

                    updatePools();
                }

                @Override
                protected void _onError(String message) {
                    ToastUtil.showShort(message);
                }
            });
        }
    }

    private void saveInterests(List<InterestChekedEntity> lists) {
        ArrayList<UserInfoEntity.UserLikesBean> list = new ArrayList<>();
        for (InterestChekedEntity interestChekedEntity : lists) {
            UserInfoEntity.UserLikesBean userLikesBean = new UserInfoEntity.UserLikesBean();
            userLikesBean.setCategoryId(interestChekedEntity.getCategoryId());
            userLikesBean.setCategoryName(interestChekedEntity.getContent());
            list.add(userLikesBean);
        }
        MyUserCache.saveUserLikes(list);
    }

    //根据选择的兴趣展示水塘列表
    private void updatePools() {
        getPoolListDatas(-1);

    }


    private void initViews() {
        initToolbar();
        initDrawerLayout();
        getCategory();

    }

    private void initToolbar() {
        View statusBarFix = findView(R.id.status_bar_fix);
        statusBarFix.setBackgroundResource(R.color.colorPrimary);
        statusBarFix.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getStatusBarHeight(getActivity())));//填充状态栏

        TextView tvTitle = findView(R.id.tv_commn_title);
        tvTitle.setText("推荐水塘");
        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

        findView(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    PoolSearchActivity.start(getContext(), Constants.POOL_SEARCH_MODE);

                }
            }
        });

        findView(R.id.iv_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    requestCameraPermission();
                }
            }

        });
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
                        recyclerViewRight.setVisibility(View.VISIBLE);

                        mRightAdapter.setCurrentPosition(-1);
                        mRightAdapter.setNewData(leafs);
                    } else {
                        recyclerViewRight.setVisibility(View.GONE);
                        getPoolListDatas(item.getCategoryId());

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
                    getPoolListDatas(item.getCategoryId());

                }
            }
        });
    }

    private void initPoolCards(List<RecommendPoolListEntity.ResultsBean> results) {
        mViewPager = findView(R.id.view_pager_pool);
        mViewPager.bind(getFragmentManager(), new PoolCardHandler(), results);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);

                } else {

                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
        }
    }


    private void getCategory() {
//        BaseRequestBody body = new BaseRequestBody();
//        body.setCategory_type(1);

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.category_type, 1);
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
     * 根据分类获取水塘列表
     *
     * @param categoryId 分类id
     */
    public void getPoolListDatas(int categoryId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.category_id, categoryId);
        Observable<BaseResponse<RecommendPoolListEntity>> observable = HttpUtil.getInstance().sApi.getRecommendPoolList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<RecommendPoolListEntity>(getContext()) {
            @Override
            protected void _onNext(RecommendPoolListEntity entity) {
                List<RecommendPoolListEntity.ResultsBean> results = entity.getResults();
                if (results != null && results.size() > 0) {
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                    initPoolCards(results);
                } else {
                    ToastUtil.showShort("暂无该分类内容或内容已下架");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void requestBasicPermission() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.setLogging(true);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
//               , Manifest.permission.READ_CONTACTS,
//                Manifest.permission.WRITE_CONTACTS
        ).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (!aBoolean) {
                    Toast.makeText(getContext(), "未全部授权，部分功能可能无法正常运行！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
