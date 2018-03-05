package com.drops.waterdrop.ui.find.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.help.GlideImageLoader;
import com.drops.waterdrop.model.VideoModel;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.adapter.GoodsDetailPagerAdapter;
import com.drops.waterdrop.ui.find.adapter.GoodsDetailsCommentAdapter;
import com.drops.waterdrop.ui.find.adapter.GoodsDetailsImgListAdapter;
import com.drops.waterdrop.ui.find.adapter.RecyclerItemViewHolder;
import com.drops.waterdrop.ui.find.fragment.SpecificationSelectorDialog;
import com.drops.waterdrop.ui.find.presenter.GoodsDetailsPresenter;
import com.drops.waterdrop.ui.find.view.IGoodsDetailsView;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.ShareUtils;
import com.drops.waterdrop.util.SinaUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.video.SampleListener;
import com.drops.waterdrop.video.VideoListBaseAdapter;
import com.drops.waterdrop.widget.WrapContentHeightViewPager;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.GoodsDetailEntity;
import com.netease.nim.uikit.model.LinkedMeModel;
import com.netease.nim.uikit.session.activity.ContactsSelectActivity;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dengxiaolei on 2017/6/7.
 */

public class GoodsDetailsActivity extends BaseActivity<IGoodsDetailsView, GoodsDetailsPresenter> implements IGoodsDetailsView, BaseQuickAdapter.RequestLoadMoreListener {


    @Bind(R.id.banner)
    Banner mBanner;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.tv_goods_name)
    TextView mTvGoodsName;
    @Bind(R.id.tv_price)
    TextView mTvPrice;
    @Bind(R.id.line1)
    View mLine1;
    @Bind(R.id.tv_bao_you)
    TextView mTvBaoYou;
    @Bind(R.id.icon)
    ImageView mIcon;
    @Bind(R.id.service1)
    TextView mService1;
    @Bind(R.id.service2)
    TextView mService2;
    @Bind(R.id.service3)
    TextView mService3;
    @Bind(R.id.tv_specification)
    TextView mTvSpecification;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    WrapContentHeightViewPager mViewPager;
//            ViewPager mViewPager;
    @Bind(R.id.tv_buy)
    TextView mTvBuy;
    @Bind(R.id.video_full_container)
    FrameLayout mVideoFullContainer;
    @Bind(R.id.ll_service)
    LinearLayout mLlService;

    FrameLayout videoFullContainer;

    ListVideoUtil listVideoUtil;
    int lastVisibleItem;
    int firstVisibleItem;
    private LinearLayoutManager mDetailsLayoutManager;
    private VideoListBaseAdapter mVideoListAdapter;
    private GoodsDetailPagerAdapter mGoodsDetailPagerAdapter;
    private RecyclerView rvVideo;
    private RecyclerView rvImg;
    private ImageView mIvParameter;
    private RecyclerView rvComment;
    private GoodsDetailsCommentAdapter mCommentAdapter;
    private GoodsDetailsImgListAdapter mGoodsDetailsImgListAdapter;

    private BottomSheetDialog mBottomDialog;
    private String mUrl;
    private String mParameterUrl;
    private View mIvVideoTitle;
    private View mIvImgTitle;

    private SpecificationSelectorDialog mSpeciSelector;
    private String speciDefaultValue;
    private String priceDefaultValue;
    private GoodsDetailEntity.GoodBean.GoodSku selectGoodSku;

    public static void start(Context context, GoodsDetailEntity entity, long tipId, long dropId, String tipTitle) {
        Intent starter = new Intent(context, GoodsDetailsActivity.class);
        starter.putExtra(Constants.EXTRA_ENTITY, entity);
        starter.putExtra(Constants.EXTRA_DROP_ID, dropId);
        starter.putExtra(Constants.EXTRA_TIP_ID, tipId);
        starter.putExtra(Constants.EXTRA_TIP_NAME, tipTitle);
        context.startActivity(starter);
    }

    public static void start(Context context, String goodsId, long tipId, long dropId, String tipTitle) {
        Intent starter = new Intent(context, GoodsDetailsActivity.class);
        starter.putExtra(Constants.EXTRA_DROP_ID, dropId);
        starter.putExtra(Constants.EXTRA_TIP_ID, tipId);
        starter.putExtra(Constants.EXTRA_GOODS_ID, goodsId);
        starter.putExtra(Constants.EXTRA_TIP_NAME, tipTitle);
        context.startActivity(starter);
    }


    @Override
    protected void initView() {
//        mToolbar.setTitleTextColor(Color.WHITE);


        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        StatusBarUtil.setTransparentForImageView(this, mToolbar);//设置状态栏透明， 并且toolbar向下偏移状态栏的高度
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mCollapsingToolbarLayout.setTitleEnabled(false);
        registerFinishReceiver(Constants.BR_PAY_H5_SUCC);
      /*  mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -mAppBarLayout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle("");
//                    mCollapsingToolbarLayout.setExpandedTitleMarginStart(100);
                } else {
                    mCollapsingToolbarLayout.setTitle("");
                }

            }
        });*/
    /*    initBanner();
        initTab();
*/
        initTab();
        mPresenter.parseIntent(getIntent());
//        initViewPager();
    }


    private void initTab() {
        View tabView1 = LayoutInflater.from(this).inflate(R.layout.layout_goods_detail_tab1, null);
        View tabView2 = LayoutInflater.from(this).inflate(R.layout.layout_goods_detail_tab2, null);
        View tabView3 = LayoutInflater.from(this).inflate(R.layout.layout_goods_detail_tab3, null);

        rvVideo = (RecyclerView) tabView1.findViewById(R.id.recycler_view_video);
        rvImg = (RecyclerView) tabView1.findViewById(R.id.recycler_view_img);
        mIvParameter = (ImageView) tabView2.findViewById(R.id.iv_img);
        rvComment = (RecyclerView) tabView3.findViewById(R.id.recycler_view_comment);

        mIvVideoTitle = tabView1.findViewById(R.id.video_title);
        mIvImgTitle = tabView1.findViewById(R.id.img_title);
        setCommentView(rvComment);

        ArrayList<View> views = new ArrayList<>();
        views.add(tabView1);
        views.add(tabView2);
        views.add(tabView3);
        ArrayList<String> titles = new ArrayList<>();
        titles.add("详情");
        titles.add("参数");
        titles.add("评分");
        mGoodsDetailPagerAdapter = new GoodsDetailPagerAdapter(views, titles);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mGoodsDetailPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

    }

    private void setCommentView(RecyclerView rv) {

        LinearLayoutManager commentLayoutManager = new LinearLayoutManager(this);
        commentLayoutManager.setAutoMeasureEnabled(true);
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(commentLayoutManager);
        mCommentAdapter = new GoodsDetailsCommentAdapter(0);
        View emptyView = View.inflate(this, R.layout.empty_view, null);
        mCommentAdapter.setEmptyView(emptyView);
        mCommentAdapter.setOnLoadMoreListener(this, rv);
        mPresenter.setLoadMoreAdapter(mCommentAdapter);
        rv.setAdapter(mCommentAdapter);
        rv.setFocusable(false);
    }

    private void setImgList(RecyclerView rv, List<String> photoDetails) {
        mGoodsDetailsImgListAdapter = new GoodsDetailsImgListAdapter(0, photoDetails);
//
//        ImgTestAdapter imgTestAdapter = new ImgTestAdapter(this, photoDetails);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(mGoodsDetailsImgListAdapter);
//        rv.setAdapter(imgTestAdapter);
    }

    private void setVideoList(RecyclerView rv, List<GoodsDetailEntity.GoodBean.VideoDetailBean> videoDetail) {
        ArrayList<VideoModel> videoModels = new ArrayList<>();
        for (GoodsDetailEntity.GoodBean.VideoDetailBean videoDetailBean : videoDetail) {
            VideoModel videoModel = new VideoModel();
            videoModel.setImgUrl(videoDetailBean.getImg());
            videoModel.setVideoUrl(videoDetailBean.getUrl());
            videoModels.add(videoModel);
        }

        listVideoUtil = new ListVideoUtil(this);
        listVideoUtil.setFullViewContainer(mVideoFullContainer);
        listVideoUtil.setHideStatusBar(true);

        mVideoListAdapter = new VideoListBaseAdapter(this, videoModels);
        mVideoListAdapter.setListVideoUtil(listVideoUtil);

        mDetailsLayoutManager = new LinearLayoutManager(this);
        mDetailsLayoutManager.setAutoMeasureEnabled(true);
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(mDetailsLayoutManager);
        rv.setAdapter(mVideoListAdapter);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = mDetailsLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = mDetailsLayoutManager.findLastVisibleItemPosition();
                Debuger.printfLog("firstVisibleItem " + firstVisibleItem + " lastVisibleItem " + lastVisibleItem);
                //大于0说明有播放,//对应的播放列表TAG
                if (listVideoUtil.getPlayPosition() >= 0 && listVideoUtil.getPlayTAG().equals(RecyclerItemViewHolder.TAG)) {
                    //当前播放的位置
                    int position = listVideoUtil.getPlayPosition();
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果是小窗口就不需要处理
                        if (!listVideoUtil.isSmall() && !listVideoUtil.isFull()) {
                            System.out.println("小窗口");
                            //小窗口
                            int size = CommonUtil.dip2px(GoodsDetailsActivity.this, 150);
                            //actionbar为true才不会掉下面去
                            listVideoUtil.showSmallVideo(new Point(size, size), true, true);
                        }
                    } else {
                        if (listVideoUtil.isSmall()) {
                            listVideoUtil.smallVideoToNormal();
                        }
                    }
                }
            }
        });

        //小窗口关闭被点击的时候回调处理回复页面
        listVideoUtil.setVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                Debuger.printfLog("Duration " + listVideoUtil.getDuration() + " CurrentPosition " + listVideoUtil.getCurrentPositionWhenPlaying());
            }

            @Override
            public void onQuitSmallWidget(String url, Object... objects) {
                super.onQuitSmallWidget(url, objects);
                //大于0说明有播放,//对应的播放列表TAG
                if (listVideoUtil.getPlayPosition() >= 0 && listVideoUtil.getPlayTAG().equals(RecyclerItemViewHolder.TAG)) {
                    //当前播放的位置
                    int position = listVideoUtil.getPlayPosition();
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //释放掉视频
                        if (listVideoUtil != null) {
                            listVideoUtil.releaseVideoPlayer();
                        }
                        mVideoListAdapter.notifyDataSetChanged();
                    }
                }

            }
        });


    }


    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {
    }

    @Override
    protected GoodsDetailsPresenter createPresenter() {
        return new GoodsDetailsPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_goods_details;
    }

    private void initBanner(List<String> imgs) {
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        if (imgs != null)
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
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @OnClick(R.id.tv_buy)
    void onBuyClick() {
        //商品信息中有skus信息时，才会调起选择规格dialog，否则，在商品信息正常情况下都直接进入订单确认界面
        if (mPresenter.mEntity == null || mPresenter.mEntity.getGood() == null || mPresenter.mEntity.getGood().getGoodSkus() == null
                || mPresenter.mEntity.getGood().getGoodSkus().size() == 0) {
            startOrderConfirmation(null);
            return;
        }

        //已经选择好一个规格的，直接进入订单确认界面
        if (selectGoodSku != null) {
            startOrderConfirmation(selectGoodSku);
            return;
        }

        showSpeciSelector();
    }

    @OnClick(R.id.agd_specification)
    void onSpecificationClick() {
        if (mPresenter.mEntity == null || mPresenter.mEntity.getGood() == null || mPresenter.mEntity.getGood().getGoodSkus() == null
                || mPresenter.mEntity.getGood().getGoodSkus().size() == 0 || !mPresenter.buyEnableBySku) {
            return;
        }

        showSpeciSelector();
    }

    private void showSpeciSelector() {
        if (mSpeciSelector == null) {
            mSpeciSelector = new SpecificationSelectorDialog(this, mPresenter.mEntity.getGood());
        }

        mSpeciSelector.show(new SpecificationSelectorDialog.Callback() {

            @Override
            public void onSelected(GoodsDetailEntity.GoodBean.GoodSku goodSku, int quantity) {
                selectGoodSku = goodSku;
                mTvSpecification.setText(goodSku.getSkuName());
                mTvPrice.setText(NumberUtil.Instance.formatPrice(goodSku.getPrice()));
                goodSku.setQuantity(quantity);
                startOrderConfirmation(goodSku);
            }

            @Override
            public void onClose(GoodsDetailEntity.GoodBean.GoodSku goodSku, int quantity) {
                selectGoodSku = goodSku;
                if (goodSku == null) {
                    mTvSpecification.setText(speciDefaultValue);
                    mTvPrice.setText(priceDefaultValue);
                    return;
                }

                selectGoodSku.setQuantity(quantity);
                mTvSpecification.setText(goodSku.getSkuName());
                mTvPrice.setText(NumberUtil.Instance.formatPrice(goodSku.getPrice()));
            }
        });
    }

    private void startOrderConfirmation(GoodsDetailEntity.GoodBean.GoodSku goodSku) {
        if (mPresenter.mEntity == null) {
            ToastUtil.showShort("商品信息异常，无法下单");
            return;
        }
        OrderConfirmationActivity.start(this, mPresenter.mEntity, mPresenter.mDropId, mPresenter.mTipId, Constants.ORDER_TYPE_DEFAULT, goodSku);
    }

    @Override
    public void onBackPressed() {

        if (listVideoUtil != null && listVideoUtil.backFromFull()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (listVideoUtil != null) {
            listVideoUtil.releaseVideoPlayer();
        }
        GSYVideoPlayer.releaseAllVideos();

        super.onDestroy();

     /*   RefWatcher refWatcher = WaterDropApp.getRefWatcher(this);
        refWatcher.watch(this);*/
    }


    private void updateUI(GoodsDetailEntity entity) {
        if (entity == null) return;

        mUrl = "http://api.waterdrop.xin/drops_wechat/app_h5/goods-detail.html" +
                "?tip_id=" + mPresenter.mTipId + "&drop_id=" + mPresenter.mDropId + "&goods_id=" + mPresenter.mEntity.getGood().getGoodId();

        GoodsDetailEntity.GoodBean good = entity.getGood();

        if (good == null) {
            ToastUtil.showShort("找不到该宝贝");
            finish();
            return;
        }
        initBanner(entity.getGood().getActPicDetail());

        mTvGoodsName.setText(entity.getGood().getActTitle());
        mTvPrice.setText(priceDefaultValue = (NumberUtil.Instance.formatPrice(entity.getGood().getActPrice())));

        List<GoodsDetailEntity.GoodBean.MoneyDetailBean> moneyDetail = good.getMoneyDetail();
        if (moneyDetail != null && moneyDetail.size() > 0) {
            mTvSpecification.setText(speciDefaultValue = (moneyDetail.get(0).getDesc()));
        }
        List<GoodsDetailEntity.GoodBean.VideoDetailBean> videoDetail = entity.getGood().getVideoDetail();

        if (videoDetail != null && videoDetail.size() > 0) {
            mIvVideoTitle.setVisibility(View.VISIBLE);
            setVideoList(rvVideo, videoDetail);
        }

        List<String> photoDetails = entity.getGood().getPhotoDetails();
        if (photoDetails != null && photoDetails.size() > 0) {
            mIvImgTitle.setVisibility(View.VISIBLE);
            setImgList(rvImg, photoDetails);
        }

        mParameterUrl = entity.getGood().getParameteruri();
        if (TextUtils.isEmpty(mParameterUrl)) {
            mIvParameter.setImageResource(R.drawable.img_qs_303x483);
        } else {
            GlideUtil.showImageView(this, mParameterUrl, mIvParameter);
        }

        List<GoodsDetailEntity.GoodBean.ServiceBean> service = entity.getGood().getService();
        if (service != null && service.size() > 0) {
            mLlService.setVisibility(View.VISIBLE);
            if (service.size() == 1) {
                mService1.setText(service.get(0).getTitle());
                mService1.setVisibility(View.VISIBLE);
            } else if (service.size() == 2) {
                mService1.setText(service.get(0).getTitle());
                mService1.setVisibility(View.VISIBLE);

                mService2.setText(service.get(1).getTitle());
                mService2.setVisibility(View.VISIBLE);
            } else {
                mService1.setText(service.get(0).getTitle());
                mService1.setVisibility(View.VISIBLE);

                mService2.setText(service.get(1).getTitle());
                mService2.setVisibility(View.VISIBLE);

                mService3.setText(service.get(2).getTitle());
                mService3.setVisibility(View.VISIBLE);
            }
        } else {
            mLlService.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getScoreList(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            onMoreIconClick();

        }
        return super.onOptionsItemSelected(item);
    }


    private void onMoreIconClick() {
        if (mBottomDialog == null) {
            View inflate = View.inflate(this, R.layout.dialog_share_layout, null);
            mBottomDialog = new BottomSheetDialog(this);
            mBottomDialog.setContentView(inflate);
            mBottomDialog.setCancelable(true);
            mBottomDialog.setCanceledOnTouchOutside(false);
            inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomDialog.dismiss();
                }
            });

            inflate.findViewById(R.id.tv_friend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareFriend();
                    mBottomDialog.dismiss();
                }
            });

            inflate.findViewById(R.id.tv_wechat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPresenter.mEntity == null) {
                        mBottomDialog.dismiss();
                        return;
                    }
                    String title = mTvGoodsName.getText().toString();
                    GoodsDetailEntity.GoodBean good = mPresenter.mEntity.getGood();
                    String actPic = "";
                    if (good != null) {
                        actPic = good.getActPic();
                    }
                  /*  if (!TextUtils.isEmpty(mUrl) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(actPic)) {
                        ShareUtils.share2WxWebPage(GoodsDetailsActivity.this, true
                                , mUrl
                                , title
                                , actPic
                                , Constants.GOOD_DESC);
                    }*/
                    LinkedMeModel linedModel = new LinkedMeModel();
                    linedModel.setTipTitle(mPresenter.mTipName);
                    linedModel.setGoodsId(mPresenter.mGoodsId);
                    linedModel.setTipId(mPresenter.mTipId);
                    linedModel.setDropId(mPresenter.mDropId);
                    ShareUtils.generateUrl(GoodsDetailsActivity.this, 1, "GOODS", true, mUrl, title, actPic, Constants.POOL_DESC, linedModel);

                    mBottomDialog.dismiss();
                }
            });
            inflate.findViewById(R.id.tv_wechat_moment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPresenter.mEntity == null) {
                        mBottomDialog.dismiss();
                        return;
                    }
                    String title = mTvGoodsName.getText().toString();
                    GoodsDetailEntity.GoodBean good = mPresenter.mEntity.getGood();
                    String actPic = "";
                    if (good != null) {
                        actPic = good.getActPic();
                    }
                  /*  ShareUtils.share2WxWebPage(GoodsDetailsActivity.this, false
                            , mUrl
                            , title
                            , actPic
                            , Constants.GOOD_DESC);*/
                    LinkedMeModel linedModel = new LinkedMeModel();
                    linedModel.setTipTitle(mPresenter.mTipName);
                    linedModel.setGoodsId(mPresenter.mGoodsId);
                    linedModel.setTipId(mPresenter.mTipId);
                    linedModel.setDropId(mPresenter.mDropId);
                    ShareUtils.generateUrl(GoodsDetailsActivity.this, 1, "GOODS", false, mUrl, title, actPic, Constants.POOL_DESC, linedModel);

                    mBottomDialog.dismiss();
                }
            });
            inflate.findViewById(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInstanlled = SinaUtil.isInstalled(GoodsDetailsActivity.this, "com.sina.weibo");
                    if (isInstanlled) {
                        if (!TextUtils.isEmpty(mUrl) && mPresenter.mEntity != null && mPresenter.mEntity.getGood() != null) {
                          /*  WBEntryActivity.start(GoodsDetailsActivity.this
                                    , mUrl
                                    , mTvGoodsName.getText().toString()
                                    , mPresenter.mEntity.getGood().getActPic()
                                    , Constants.GOOD_DESC);*/
                            LinkedMeModel linedModel = new LinkedMeModel();
                            linedModel.setTipTitle(mPresenter.mTipName);
                            linedModel.setGoodsId(mPresenter.mGoodsId);
                            linedModel.setTipId(mPresenter.mTipId);
                            linedModel.setDropId(mPresenter.mDropId);
                            ShareUtils.generateUrl(GoodsDetailsActivity.this, 2, "GOODS", false, mUrl, mTvGoodsName.getText().toString(), mPresenter.mEntity.getGood().getActPic(), Constants.POOL_DESC, linedModel);

                        }

                    } else {
                        ToastUtil.showShort("请先安装新浪微博客户端");
                    }
                    mBottomDialog.dismiss();
                }
            });
        }

        mBottomDialog.show();


    }

    private void shareFriend() {
        if (mPresenter.mEntity == null || TextUtils.isEmpty(mPresenter.mGoodsId) || mPresenter.mTipId == 0 || TextUtils.isEmpty(mPresenter.mTipName)) {
            return;
        }
        ContactsSelectActivity.startForGoods(this, mPresenter.mGoodsId, mPresenter.mEntity.getGood().getActTitle()
                , mPresenter.mEntity.getGood().getActPic(), mPresenter.mEntity.getGood().getActDes(), mPresenter.mTipId, mPresenter.mDropId, mPresenter.mTipName);
    }


    @Override
    public void updateUI() {
        updateUI(mPresenter.mEntity);
       /* mUrl = "http://192.168.0.105:8092/index.html" +
                "?user_token=" + MyUserCache.getUserToken() + "&uid=" + MyUserCache.getUserUid()
                + "&tip_id=" + mPresenter.mTipId + "&drop_id=" + mPresenter.mDropId;*/

        mPresenter.getScoreList(false);
    }

    @Override
    public void onLoadGoodsDetailSucceed(GoodsDetailEntity o) {
        updateUI(o);
        mPresenter.getScoreList(false);
    }

    @Override
    public void setBuyButtonEnable(boolean enable) {
        mTvBuy.setEnabled(enable);

        if (enable) {
            GoodsDetailEntity.GoodBean goodBean = mPresenter.mEntity.getGood();
            List<GoodsDetailEntity.GoodBean.GoodSku> goodSkus = goodBean.getGoodSkus();
            if (goodSkus == null) {
                return;
            }

            int count = goodSkus.size();
            if (count == 1) {
                GoodsDetailEntity.GoodBean.GoodSku goodSku = goodSkus.get(0);
                selectGoodSku = goodSku;
                mTvSpecification.setText(goodSku.getSkuName());
                mTvPrice.setText(NumberUtil.Instance.formatPrice(goodSku.getPrice()));
                goodSku.setQuantity(1);
            }
        } else {
            mTvBuy.setText("已售罄");
        }
    }


}
