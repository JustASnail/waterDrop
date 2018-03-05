package com.drops.waterdrop.ui.find.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.help.GlideImageLoader;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.adapter.CommentAdapter;
import com.drops.waterdrop.ui.find.adapter.GoodsAdapter;
import com.drops.waterdrop.ui.find.presenter.HotPostDetailPresenter;
import com.drops.waterdrop.ui.find.view.IHotPostDetailView;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.SinaUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.PostEntity;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.drops.waterdrop.R.id.iv_user_head;
import static com.drops.waterdrop.R.id.tv_like_btn;

/**
 * Created by dengxiaolei on 2017/5/25.
 */

public class HotPostDetailActivity extends BaseActivity<IHotPostDetailView, HotPostDetailPresenter> implements IHotPostDetailView, View.OnClickListener, WbShareCallback {
    @Bind(R.id.banner)
    Banner mBanner;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.tv_post_name)
    TextView mTvPostName;
    @Bind(R.id.tv_post_content)
    TextView mTvPostContent;
    @Bind(iv_user_head)
    HeadImageView mIvUserHead;
    @Bind(R.id.tv_post_user)
    TextView mTvPostUser;
    @Bind(R.id.tv_post_user_name)
    TextView mTvPostUserName;
    @Bind(R.id.tv_post_time)
    TextView mTvPostTime;
    @Bind(R.id.tv_star_btn)
    TextView mTvStarBtn;
    @Bind(tv_like_btn)
    TextView mTvLikeBtn;
    @Bind(R.id.iv_pool_head)
    HeadImageView mIvPoolHead;
    @Bind(R.id.tv_pool_name)
    TextView mTvPoolName;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.recycler_view_goods)
    RecyclerView mRecyclerViewGoods;

    private BottomSheetDialog mBottomDialog;


    //资源文件
    private List<Integer> images = new ArrayList<>();

    public static final String URL_COMMODITY1 = "https://img14.360buyimg.com/n0/jfs/t3088/64/5917557750/202995/5bfe1d67/58981c71N9736976c.jpg";
    public static final String URL_COMMODITY2 = "https://img14.360buyimg.com/n0/jfs/t2827/27/2812645576/130865/894dbcb4/577250f5N4eb0b792.jpg";
    private PostEntity mEntity;
    private boolean mCollect;
    private WbShareHandler shareHandler;

    public static void start(Context context, long tip_id) {
        Intent starter = new Intent(context, HotPostDetailActivity.class);
        starter.putExtra(Constants.EXTRA_TIP_ID, tip_id);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {

        StatusBarUtil.setTransparentForImageView(this, mToolbar);//设置状态栏透明， 并且toolbar向下偏移状态栏的高度
        mPresenter.parseIntent(getIntent());
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
    }

    private void initUI() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -mAppBarLayout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle(mEntity.getTipTitle());
//                    mCollapsingToolbarLayout.setExpandedTitleMarginStart(100);
                } else {
                    mCollapsingToolbarLayout.setTitle("");
                }

            }
        });
        initBanner();

        GlideUtil.showImageViewToCircle(this, com.netease.nim.uikit.R.drawable.icon_default_head_60dp, mEntity.getCreator().getPhoto(), mIvUserHead);
        GlideUtil.showImageViewToCircle(this, com.netease.nim.uikit.R.drawable.icon_default_head_60dp, mEntity.getDropInfo().getDropPhoto(), mIvPoolHead);



        mTvPostUserName.setText(mEntity.getCreator().getNickName());
        mTvPostTime.setText(mEntity.getCreateTime() + "");
        mTvLikeBtn.setText(NumberUtil.Instance.formatNumber(mEntity.getLikeNum()));

        mCollect = mEntity.getJoinStatus() == 1;
        setCollect(mCollect);
        mTvPostName.setText(mEntity.getTipTitle());
        mTvPostContent.setText(mEntity.getTipContent());

        mTvPoolName.setText(mEntity.getDropInfo().getDropName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(HotPostDetailActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setFocusable(false);
        layoutManager.setAutoMeasureEnabled(true);
//        layoutManager.setSmoothScrollbarEnabled(true);
//        mNsv.setSmoothScrollingEnabled(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(new CommentAdapter(0, mPresenter.getComments()));

        LinearLayoutManager goodsLayoutManager = new LinearLayoutManager(HotPostDetailActivity.this);
        mRecyclerViewGoods.setLayoutManager(goodsLayoutManager);
        mRecyclerViewGoods.setFocusable(false);
        goodsLayoutManager.setAutoMeasureEnabled(true);
//        layoutManager.setSmoothScrollbarEnabled(true);
//        mNsv.setSmoothScrollingEnabled(true);
        mRecyclerViewGoods.setNestedScrollingEnabled(false);
        GoodsAdapter goodsAdapter = new GoodsAdapter(0, mPresenter.getGoods());
        mRecyclerViewGoods.setAdapter(goodsAdapter);
        goodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                GoodsDetailsActivity.start(HotPostDetailActivity.this, OrderConfirmationPresenter.TYPE_DEFAULT);
            }
        });
    }

    private void initBanner() {

        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(mPresenter.getBannerData());
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

    @Override
    protected void initData() {
        mPresenter.getData();
//        mCollapsingToolbarLayout.setTitleEnabled(false);
//        Glide.with(this).load(URL_COMMODITY1).into(mIvCommodityIcon);
//        Glide.with(this).load(URL_COMMODITY2).into(mIvCommodityIcon2);

        //微博的
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();
    }

    @Override
    protected void initListener() {
        mTvLikeBtn.setOnClickListener(this);
        mTvStarBtn.setOnClickListener(this);
    }

    @Override
    protected HotPostDetailPresenter createPresenter() {
        return new HotPostDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_hot_post_detail;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            onShareIconClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onShareIconClick() {
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
                    ToastUtil.showShort("分享到好友成功");
                    mBottomDialog.dismiss();
                }
            });

            inflate.findViewById(R.id.tv_wechat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ShareUtils.share2WxWebPage(HotPostDetailActivity.this, true
//                            , "https:www.baidu.com"
//                            , mEntity.getTipTitle()
//                            , "优质内容，最懂您的生活！");
//                    mBottomDialog.dismiss();
                }
            });
            inflate.findViewById(R.id.tv_wechat_moment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ShareUtils.share2WxWebPage(HotPostDetailActivity.this, false
//                            , "https:www.baidu.com"
//                            , mEntity.getTipTitle()
//                            , "优质内容，最懂您的生活！");
                    mBottomDialog.dismiss();
                }
            });inflate.findViewById(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInstanlled = SinaUtil.isInstalled(HotPostDetailActivity.this, "com.sina.weibo");
                    if (isInstanlled) {
//                        shareToWeibo();
//                        WBEntryActivity.start(HotPostDetailActivity.this, "https://www.baidu.com", "title", "哈哈哈");
                    }else {
                        ToastUtil.showShort("请先安装新浪微博客户端");
                    }
                    mBottomDialog.dismiss();
                }
            });
        }

        mBottomDialog.show();

    }

    private void shareToWeibo() {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        imageObject.setImageObject(bitmap);
        weiboMessage.mediaObject = imageObject;
        shareHandler.shareMessage(weiboMessage, true);
    }

    @Override
    public void onClick(View v) {
        if (!FastClickUtil.isFastDoubleClick()) {
            switch (v.getId()) {
                case R.id.tv_like_btn:
                    onLikeBtnClick();
                    break;
                case R.id.tv_star_btn:
                    onStarBtnClick();
                    break;
            }
        }

    }


    private void onStarBtnClick() {
        if (mCollect) {
            mPresenter.deleteCollect();
        } else {
            mPresenter.insertCollect();
        }
        mCollect = !mCollect;
    }

    private void setCollect(boolean b) {
        Drawable topDrawable;
        if (b) {
            topDrawable = getResources().getDrawable(R.mipmap.icon_star);
            mTvStarBtn.setText("已收藏");
        } else {
            topDrawable = getResources().getDrawable(R.mipmap.icon_stared);
            mTvStarBtn.setText("收藏");
        }
        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
        mTvStarBtn.setCompoundDrawables(null, topDrawable, null, null);
    }

    private void onLikeBtnClick() {
        String likes = mTvLikeBtn.getText().toString().trim();
        if (TextUtils.equals("13", likes)) {
            Drawable topDrawable = getResources().getDrawable(R.mipmap.icon_like);
            topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
            mTvLikeBtn.setCompoundDrawables(null, topDrawable, null, null);

            mTvLikeBtn.setText("14");
        } else {
            Drawable topDrawable = getResources().getDrawable(R.mipmap.icon_liked);
            topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
            mTvLikeBtn.setCompoundDrawables(null, topDrawable, null, null);

            mTvLikeBtn.setText("13");
        }
    }

    @Override
    public void onGetDataSucceed(PostEntity entity) {
        mEntity = entity;
        initUI();
    }


    @Override
    public void insertCollectSucceed() {
        setCollect(true);
        mCollect = true;
    }

    @Override
    public void deleteCollectSucceed() {
        setCollect(false);
        mCollect = false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent,this);

    }

    @Override
    public void onWbShareSuccess() {
        ToastUtil.showShort("success");
    }

    @Override
    public void onWbShareCancel() {
        ToastUtil.showShort("cancel");
        finish();
    }

    @Override
    public void onWbShareFail() {
        ToastUtil.showShort("failed");
    }

}
