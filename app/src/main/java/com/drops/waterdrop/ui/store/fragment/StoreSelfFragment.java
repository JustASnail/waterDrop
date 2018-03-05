package com.drops.waterdrop.ui.store.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.im.fragment.MainTabFragment;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.drops.waterdrop.ui.find.activity.GoodsDetailsActivity;
import com.drops.waterdrop.ui.store.activity.ChinaPavilionAvtivity;
import com.drops.waterdrop.ui.store.adapter.BannerViewHolder;
import com.drops.waterdrop.ui.store.adapter.FunActiveListAdapter;
import com.drops.waterdrop.util.ShadowUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.storebanner.MZBannerView;
import com.drops.waterdrop.widget.storebanner.holder.MZHolderCreator;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.custom.CustomAnimation;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AppInfoEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.StoreHomePageEntity;
import java.util.List;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/20.
 */

public class StoreSelfFragment extends MainTabFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    private static final String TAG = "StoreSelfFragment";
    public static final int PICK_AVATAR_REQUEST = 0x0E;
    private MZBannerView mMZBanner;
    private RecyclerView mRecyclerView;
    private AppCompatActivity mActivity;
    private SwipeRefreshLayout mSwipeRefresh;
    private FunActiveListAdapter mAdapter;
    private ImageView mIvCross;
    private ImageView mIvChina;
    private CardView mCVCross;
    private CardView mCVChina;

    @Override
    protected void onInit() {

        initView();
        initPavilion();
        initData();
        initListener();
    }

    private void initPavilion() {
        Observable<BaseResponse<AppInfoEntity>> observable = HttpUtil.getInstance().sApi.getAppInfo();
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AppInfoEntity>(getContext(),false) {
            @Override
            protected void _onNext(AppInfoEntity entity) {
                List<AppInfoEntity.ResultsBean> results = entity.getResults();
                for (AppInfoEntity.ResultsBean resultsBean : results) {
                    String paramKey = resultsBean.getParamKey();
                    if (paramKey.equals(Constants.CHINA_MALL_PHOTO)) {
                        String url = resultsBean.getParamValue();
                        GlideUtil.showImageViewFirst(getContext(), R.mipmap.img_qs_108x70, url, mIvChina);
                    } else if (paramKey.equals(Constants.BORDER_MALL_PHOTO)) {
                        String url = resultsBean.getParamValue();
                        GlideUtil.showImageViewFirst(getContext(), R.mipmap.img_qs_108x70, url, mIvCross);
                    }
                }
            }
            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void initData() {
        Observable<BaseResponse<StoreHomePageEntity>> observable = HttpUtil.getInstance().sApi.getHomePageInfo(RequestBodyUtils.build(null));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<StoreHomePageEntity>(getContext(),false) {
            @Override
            protected void _onNext(StoreHomePageEntity entity) {

                initBanner(entity.getBanners());
                initRecyclerView(entity.getRecommends());
                if (mSwipeRefresh.isRefreshing()) {
                    mSwipeRefresh.setRefreshing(false);
                }
            }

            @Override
            protected void _onError(String message) {
                if (mSwipeRefresh.isRefreshing()) {
                    mSwipeRefresh.setRefreshing(false);
                }
                ToastUtil.showShort(message);
            }
        });
    }

    private void initListener() {
        mSwipeRefresh.setOnRefreshListener(this);
        mCVCross.setOnClickListener(this);
        mCVChina.setOnClickListener(this);
    }

    private void initRecyclerView(List<StoreHomePageEntity.RecommendsBean> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        mAdapter = new FunActiveListAdapter(0);
        mAdapter.setNewData(list);
        mAdapter.openLoadAnimation(new CustomAnimation());
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    private void initView() {
        fillStatusBar();    //填充状态栏

        mSwipeRefresh = findView(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        mIvCross = findView(R.id.iv_bg_cross);
        mIvChina = findView(R.id.iv_bg_china);
        mMZBanner = findView(R.id.banner);
        mRecyclerView = findView(R.id.recycler_view);
        mCVCross = findView(R.id.cv_crossboard);
        mCVChina = findView(R.id.cv_china);
        View view = findView(R.id.shadow);
        ShadowUtil.shadow(getContext(), view);
    }

    private void fillStatusBar() {
        View statusBarFix = findView(R.id.status_bar_fix);
        statusBarFix.setBackgroundResource(R.color.colorPrimary);
        statusBarFix.setMinimumHeight(ScreenUtil.getStatusBarHeight(getActivity()));
        statusBarFix.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ScreenUtil.getStatusBarHeight(getActivity())));
    }

    private void initBanner(List<StoreHomePageEntity.BannersBean> list) {
        mMZBanner.setPages(list, new MZHolderCreator() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mMZBanner.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMZBanner != null) {
            mMZBanner.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMZBanner != null) {
            mMZBanner.start();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public void onRefresh() {
        initData();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        StoreHomePageEntity.RecommendsBean item = mAdapter.getItem(position);
        if (item != null) {
            if (item.getType() == 1) {
                GoodsDetailsActivity.start(getContext(), item.getGoodId(), Constants.STORE_TIP_ID, Constants.STORE_DROP_ID, Constants.STORE_TIP_TITLE);
            } else if (item.getType() == 2){
//                CommonWebActivity.startForStoreAndActive(getContext(), item.getLink(), true);
                String link = item.getLink();
                CommonWebActivity.startOfActive(getContext(), link);
            } else if (item.getType() == 3) {
                String link = item.getLink();
                String tipId = link.substring(link.indexOf("=") + 1).trim();
                CommonWebActivity.start(getContext(), Long.parseLong(tipId), link);
            }
        }
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        mSwipeRefresh.setEnabled(verticalOffset >= 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_crossboard:
//                startActivity(new Intent(getContext(),StoreActivity.class));
                CommonWebActivity.startForStore(getContext());
                break;
            case R.id.cv_china:
                startActivity(new Intent(getContext(), ChinaPavilionAvtivity.class));
                break;
        }
    }
}
