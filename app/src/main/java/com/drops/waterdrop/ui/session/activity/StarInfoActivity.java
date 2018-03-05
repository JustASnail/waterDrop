package com.drops.waterdrop.ui.session.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.activity.HotPostDetailActivity;
import com.drops.waterdrop.ui.find.activity.PoolDetailPageActivity;
import com.drops.waterdrop.ui.find.adapter.TopPostAdapter;
import com.drops.waterdrop.ui.session.presenter.StarInfoPresenter;
import com.drops.waterdrop.ui.session.view.IStarInfoView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.util.sys.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.StarInfoEntity;

import java.util.List;

import butterknife.Bind;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.drops.waterdrop.R.id.tv_re_du_num;

/**
 * Created by dengxiaolei on 2017/5/12.
 */

public class StarInfoActivity extends BaseActivity<IStarInfoView, StarInfoPresenter> implements IStarInfoView, View.OnClickListener {

    @Bind(R.id.iv_head)
    HeadImageView mIvHead;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_fans_num)
    TextView mTvFansNum;
    @Bind(R.id.tv_account)
    TextView mTvAccount;
    @Bind(R.id.tv_follow)
    TextView mTvFollow;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.nsv)
    NestedScrollView mNsv;
    @Bind(R.id.root_layout)
    CoordinatorLayout mRootLayout;
    @Bind(R.id.rl_head_container)
    RelativeLayout mRlHeadContainer;
    @Bind(R.id.tv_sign)
    TextView mTvSign;
    @Bind(R.id.cv_sign)
    CardView mCvSign;
    @Bind(R.id.iv_pool_head)
    HeadImageView mIvPoolHead;
    @Bind(R.id.tv_pool_name)
    TextView mTvPoolName;
    @Bind(R.id.tv_pool_content)
    TextView mTvPoolContent;
    @Bind(R.id.rl_pool_info)
    RelativeLayout mRlPoolInfo;
    @Bind(tv_re_du_num)
    TextView mTvReDuNum;

    @Bind(R.id.tv_shop_name1)
    TextView mTvShopName1;
    @Bind(R.id.iv_shop_head1)
    HeadImageView mIvShopHead1;
    @Bind(R.id.rl_shop1)
    RelativeLayout mRlShop1;
    @Bind(R.id.tv_shop_name2)
    TextView mTvShopName2;
    @Bind(R.id.iv_shop_head2)
    HeadImageView mIvShopHead2;
    @Bind(R.id.rl_shop2)
    RelativeLayout mRlShop2;
    @Bind(R.id.tv_shop_name3)
    TextView mTvShopName3;
    @Bind(R.id.iv_shop_head3)
    HeadImageView mIvShopHead3;
    @Bind(R.id.rl_shop3)
    RelativeLayout mRlShop3;
    @Bind(R.id.cv_shop)
    CardView mCvShop;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.iv_head_ip_tag)
    ImageView mIvHeadIpTag;
    @Bind(R.id.iv_pool_ip_tag)
    ImageView mIvPoolIpTag;
    @Bind(R.id.cv_pool)
    CardView mCvPool;
    @Bind(R.id.tv_more_pool)
    TextView mTvMorePool;
    private TopPostAdapter mAdapter;

    private StarInfoEntity mStarInfoEntity;
    private List<StarInfoEntity.DropsBean> mPoolList;
    private List<StarInfoEntity.DropTipsBean> mTipList;
    private List<StarInfoEntity.ShopsBean> mShopList;

    public static void start(Context context, long f_uid) {
        Intent starter = new Intent(context, StarInfoActivity.class);
        starter.putExtra(Constants.EXTRA_FRIEND_UID, f_uid);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        parseIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.sele_commn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCollapsingToolbarLayout.setTitleEnabled(false);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        StatusBarUtil.setTransparentForImageView(this, toolbar);//设置状态栏透明， 并且toolbar向下偏移状态栏的高度

    }

    private void updateUI() {
        initUserUI();
        initPoolUI();
        initShopUI();
        initTopPosts();
    }

    private void initTopPosts() {
        if (mTipList == null || mTipList.size() <= 0) {
            return;
        }

        mRecyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        if (mAdapter == null) {
            mAdapter = new TopPostAdapter(0, mTipList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setFocusable(false);
            layoutManager.setAutoMeasureEnabled(true);
//        layoutManager.setSmoothScrollbarEnabled(true);
//        mNsv.setSmoothScrollingEnabled(true);
            mRecyclerView.setNestedScrollingEnabled(false);

            TextView textView = new TextView(this);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setText("热帖");
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(Color.WHITE);
            textView.setPadding(UIUtils.dip2Px(10), UIUtils.dip2Px(10), UIUtils.dip2Px(10), UIUtils.dip2Px(10));
            textView.setLayoutParams(layoutParams);
            mAdapter.addHeaderView(textView);
        } else {
            mAdapter.setNewData(mTipList);
        }

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    HotPostDetailActivity.start(StarInfoActivity.this, mTipList.get(position).getTipId());

                }
            }
        });

    }

    private void initShopUI() {
        if (mShopList == null || mShopList.size() <= 0) {
            mCvShop.setVisibility(View.GONE);
            return;
        }
        StarInfoEntity.ShopsBean shopsBean1 = mShopList.get(0);
        StarInfoEntity.ShopsBean shopsBean2 = mShopList.get(1);
        StarInfoEntity.ShopsBean shopsBean3 = mShopList.get(2);

        mTvShopName1.setText(shopsBean1.getShopName());
       GlideUtil.showImageViewToCircle(this, R.drawable.icon_default_head_60dp, shopsBean1.getShopPhoto(), mIvShopHead1);

        mRlShop1.setOnClickListener(this);

        if (shopsBean2 == null) {
            mRlShop2.setVisibility(View.GONE);
            mRlShop3.setVisibility(View.GONE);
            return;
        }
        mTvShopName2.setText(shopsBean2.getShopName());
      GlideUtil.showImageViewToCircle(this, R.drawable.icon_default_head_60dp, shopsBean2.getShopPhoto(), mIvShopHead2);

        mRlShop2.setOnClickListener(this);

        if (shopsBean3 == null) {
            mRlShop3.setVisibility(View.GONE);
            return;
        }
        mTvShopName3.setText(shopsBean3.getShopName());
     GlideUtil.showImageViewToCircle(this, R.drawable.icon_default_head_60dp,shopsBean3.getShopPhoto(), mIvShopHead3);

        mRlShop3.setOnClickListener(this);
    }

    private void initPoolUI() {
        if (mPoolList == null || mPoolList.size() <= 0) {
            mCvPool.setVisibility(View.GONE);
            return;
        }
        if (mPoolList.get(0).getStarFlag() == Constants.IS_STAR_FLAG) {
            mIvPoolIpTag.setVisibility(View.VISIBLE);
        } else {
            mIvPoolIpTag.setVisibility(View.GONE);
        }
    GlideUtil.showImageViewToCircle(this, R.drawable.icon_default_head_60dp,mPoolList.get(0).getDropPhoto(), mIvPoolHead);

        mTvPoolName.setText(mPoolList.get(0).getDropName());
        mTvPoolContent.setText(mPoolList.get(0).getDropDesc());
        mTvReDuNum.setText(mPoolList.get(0).getLikeNum() + "");
        mCvPool.setOnClickListener(this);
        mTvMorePool.setOnClickListener(this);
    }

    private void initUserUI() {
        if (mStarInfoEntity == null) {
            return;
        }
        //可以把视频的缩略图设置给CollapsingToolbarLayout
      /*  Glide.with(this).load(mStarInfoEntity.getPhoto()).bitmapTransform(new BlurTransformation(StarInfoActivity.this, 10))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                            GlideDrawable> glideAnimation) {
                        mRlHeadContainer.setBackgroundDrawable(resource);

                    }
                });*/
        GlideUtil.showImageView(this, mStarInfoEntity.getPhoto(), mRlHeadContainer);
        Glide.with(this).load(mStarInfoEntity.getPhoto()).bitmapTransform(new BlurTransformation(StarInfoActivity.this, 10))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                            GlideDrawable> glideAnimation) {
                        mCollapsingToolbarLayout.setContentScrim(resource);//把一张图片模糊化， 并且设置给CollapsingToolbarLayout
                    }
                });
//        Glide.with(this).load(R.mipmap.zhoujielun1).bitmapTransform(new BlurTransformation(this, 25)).crossFade(1000).into(mIvHead);
       GlideUtil.showImageViewToCircle(this, R.drawable.icon_default_head_60dp, mStarInfoEntity.getPhoto(), mIvHead);

        mTvName.setText(mStarInfoEntity.getNickName());
        mTvAccount.setText(mStarInfoEntity.getIdCode() + "");
        mTvFansNum.setText("粉丝： " + mStarInfoEntity.getSameFriendNum());

        if (mStarInfoEntity.getStarFlag() == Constants.IS_STAR_FLAG) {
            mIvHeadIpTag.setVisibility(View.VISIBLE);
        } else {
            mIvHeadIpTag.setVisibility(View.GONE);
        }

        if (mStarInfoEntity.getRelationStatus() == 1) {
            mTvFollow.setText("聊天");
        } else {
            mTvFollow.setText("关注");
        }

        mTvSign.setText(mStarInfoEntity.getUserDesc());
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            long f_uid = intent.getLongExtra(Constants.EXTRA_FRIEND_UID, -1);
            mPresenter.getData(f_uid);
        }
    }


    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {


    }

    @Override
    protected StarInfoPresenter createPresenter() {
        return new StarInfoPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_star_info;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_pool:
                if (!FastClickUtil.isFastDoubleClick()) {
                    PoolDetailPageActivity.start(StarInfoActivity.this, mPoolList.get(0).getDropId());
                }
                break;
            case R.id.rl_shop1:
                ToastUtil.showShort("意大利美食馆");
                break;
            case R.id.rl_shop2:
                ToastUtil.showShort("意大利美妆馆");
                break;
            case R.id.rl_shop3:
                ToastUtil.showShort("意大利时尚馆");
                break;
            case R.id.tv_more_pool:
                if (!FastClickUtil.isFastDoubleClick()) {
//                    PoolListActivity.start(StarInfoActivity.this, mStarListBean, false);
                }
                break;


        }
    }


    @Override
    public void onGetDataSucceed(StarInfoEntity starInfoEntity) {
        mStarInfoEntity = starInfoEntity;
        mPoolList = starInfoEntity.getDrops();
        mTipList = starInfoEntity.getDropTips();
        mShopList = starInfoEntity.getShops();
        updateUI();
    }

}
