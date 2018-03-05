package com.drops.waterdrop.ui.find.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.help.SessionHelper;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.adapter.PostListAtPoolAdapter;
import com.drops.waterdrop.ui.find.presenter.PoolDetailPagePresenter;
import com.drops.waterdrop.ui.find.view.IPoolDetailPageView;
import com.drops.waterdrop.ui.mine.activity.UserProfileActivity;
import com.drops.waterdrop.ui.session.activity.VerifyActivity;
import com.drops.waterdrop.util.ShareUtils;
import com.drops.waterdrop.util.SinaUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.util.sys.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.DropDetailsEntity;
import com.netease.nim.uikit.model.LinkedMeModel;
import com.netease.nim.uikit.session.activity.ContactsSelectActivity;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/5/22.
 */

public class PoolDetailPageActivity extends BaseActivity<IPoolDetailPageView, PoolDetailPagePresenter> implements IPoolDetailPageView, View.OnClickListener {
    private static final String KEY_DROP_ID = "key_drop_id";
    @Bind(R.id.tv_pool_desc)
    TextView mTvPoolDesc;
    @Bind(R.id.tv_number)
    TextView mTvNumber;
    @Bind(R.id.tv_pool_name)
    TextView mTvPoolName;
    @Bind(R.id.rl_head_container)
    RelativeLayout mRlHeadContainer;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.iv_creator_avatar)
    HeadImageView mIvCreatorAvatar;
    @Bind(R.id.tv_creator_name)
    TextView mTvCreatorName;
    @Bind(R.id.tv_group_num)
    TextView mTvGroupNum;
    @Bind(R.id.tv_attention)
    TextView mTvAttention;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private BottomSheetDialog mBottomDialog;
    private DropDetailsEntity mEntity;
    private long mDropId;
    private PostListAtPoolAdapter mPostListAtPoolAdapter;

    private int mAttentionStatus = 0;
    private String mUrl;


    public static void start(Context context, long dropId) {
        Intent starter = new Intent(context, PoolDetailPageActivity.class);
        starter.putExtra(Constants.EXTRA_DROP_ID, dropId);
        context.startActivity(starter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_DROP_ID, mDropId);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        if (savedInstanceState != null) {
            mDropId = savedInstanceState.getLong(KEY_DROP_ID);
        }
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);

        StatusBarUtil.setTransparentForImageView(this, mToolbar);//设置状态栏透明， 并且toolbar向下偏移状态栏的高度
        parseIntent();

    }


    private void parseIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mDropId = intent.getLongExtra(Constants.EXTRA_DROP_ID, -1);
            mPresenter.getData(mDropId);
        }
    }

    private void updateUI() {
        initHeadView();
    /*    Glide.with(this).load(mEntity.getDropPhoto()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
                mRlHeadContainer.setBackground(bitmapDrawable);
            }
        });*/

        GlideUtil.showImageView(this, mEntity.getHeadImg(), mRlHeadContainer);

        mToolbar.setTitle(mEntity.getDropName());
        mCollapsingToolbarLayout.setTitle(mEntity.getDropName());
        mTvPoolName.setText(mEntity.getDropName());
        setAttentionNum(mEntity.getAttentionNum());

        mTvPoolDesc.setText(mEntity.getDropDesc());

        DropDetailsEntity.CreatorBean creator = mEntity.getCreator();
        if (creator != null) {
            GlideUtil.showImageViewToCircle(this, R.drawable.icon_default_head_60dp, creator.getPhoto(), mIvCreatorAvatar);
            mTvCreatorName.setText(creator.getNickName());
        }


        final String neteaseRoomId = mEntity.getNeteaseRoomId();
        if (TextUtils.isEmpty(neteaseRoomId) || mEntity.getJoinStatus() != 1) {
            mTvGroupNum.setVisibility(View.GONE);
        } else {
            mTvGroupNum.setVisibility(View.VISIBLE);
            mTvGroupNum.setText(mEntity.getGroupUserNum() + "");
            mTvGroupNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SessionHelper.startTeamSession(PoolDetailPageActivity.this, neteaseRoomId);
                }
            });
        }


        if (mEntity.getAttentionStatus() == 1) {
            mTvAttention.setText("已关注");
            mAttentionStatus = 1;
            mTvAttention.setSelected(true);
        } else {
            mTvAttention.setText("+关注");
            mAttentionStatus = 0;
            mTvAttention.setSelected(false);

        }

        mTvAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAttentionStatus == 0) {
                    mPresenter.attentionPool(mEntity.getDropId());

                } else {
                    mPresenter.cancelAttention(mEntity.getDropId());

                }
            }
        });

        if (mPostListAtPoolAdapter == null) {
            mPostListAtPoolAdapter = new PostListAtPoolAdapter(mEntity.getTips());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mPostListAtPoolAdapter);
            View view = new View(this);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, UIUtils.dip2Px(10));
            view.setLayoutParams(params);
            mPostListAtPoolAdapter.addHeaderView(view);
        } else {
            mPostListAtPoolAdapter.setNewData(mEntity.getTips());
        }

        mPostListAtPoolAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommonWebActivity.start(PoolDetailPageActivity.this, mPostListAtPoolAdapter.getData().get(position).getTipId(), mPostListAtPoolAdapter.getData().get(position).getTipUrl());
            }
        });
    }


    private void initHeadView() {
        mCollapsingToolbarLayout.setExpandedTitleMarginStart(UIUtils.dip2Px(16));
        mCollapsingToolbarLayout.setExpandedTitleMarginBottom(UIUtils.dip2Px(30));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mTvPoolName.setVisibility(View.INVISIBLE);


/*
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

               */
/* if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()
                        && mVideoPlayer != null
                        && mVideoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {//appbar完全关闭时暂停视频播放
                    clickVideoStartButton();
                    isScrollPause = true;//记录下是通过滑动暂停的

                } else if (verticalOffset == 0//appbar完全打开时开始播放视频
                        && mVideoPlayer != null
                        && mVideoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PAUSE
                        && isScrollPause) {//只有通过滑动暂停的时候才会开始播放视频
                    clickVideoStartButton();
                    isScrollPause = false;
                }*//*


                if (verticalOffset <= -mAppBarLayout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle("周杰伦的水塘");
                    mCollapsingToolbarLayout.setExpandedTitleMarginStart(100);
                } else {
                    mCollapsingToolbarLayout.setTitle("");
                }


            }


        });
*/
    }


    @Override
    protected void initData() {
    }


    @Override
    protected void initListener() {
        mIvCreatorAvatar.setOnClickListener(this);
    }

    @Override
    protected PoolDetailPagePresenter createPresenter() {
        return new PoolDetailPagePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pool_detail_page;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            onMoreIconClick();
            return true;
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
                    String dropName = "";
                    String headImg = "";
                    if (mEntity != null) {
                        headImg = mEntity.getHeadImg();
                        dropName = mEntity.getDropName();
                    }
                    if (!TextUtils.isEmpty(headImg) && !TextUtils.isEmpty(dropName)) {
                        LinkedMeModel linedModel = new LinkedMeModel();
                        linedModel.setDropId(mDropId);
                    ShareUtils.generateUrl(PoolDetailPageActivity.this, 1, "POOL", true, mUrl, dropName, headImg, Constants.POOL_DESC,linedModel);

                    }
                       /* ShareUtils.share2WxWebPage(PoolDetailPageActivity.this, true
                                , mUrl
                                , dropName
                                , headImg
                                , Constants.POOL_DESC);*/


                    mBottomDialog.dismiss();

                }
            });

            inflate.findViewById(R.id.tv_wechat_moment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dropName = "";
                    String headImg = "";
                    if (mEntity != null) {
                        headImg = mEntity.getHeadImg();
                        dropName = mEntity.getDropName();
                    }
                    if (!TextUtils.isEmpty(headImg) && !TextUtils.isEmpty(dropName)) {
                        LinkedMeModel linedModel = new LinkedMeModel();
                        linedModel.setDropId(mDropId);
                        ShareUtils.generateUrl(PoolDetailPageActivity.this, 1, "POOL", false, mUrl, dropName, headImg, Constants.POOL_DESC,linedModel);
                    }
                    /*    ShareUtils.share2WxWebPage(PoolDetailPageActivity.this, false
                                , mUrl
                                , dropName
                                , headImg
                                , Constants.POOL_DESC);*/

                    mBottomDialog.dismiss();
                }
            });
            inflate.findViewById(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInstanlled = SinaUtil.isInstalled(PoolDetailPageActivity.this, "com.sina.weibo");
                    if (isInstanlled) {
                        if (mEntity != null && !TextUtils.isEmpty(mEntity.getDropName()) && !TextUtils.isEmpty(mEntity.getHeadImg())) {
                           /* WBEntryActivity.start(PoolDetailPageActivity.this
                                    , mUrl
                                    , mEntity.getDropName()
                                    , mEntity.getHeadImg()
                                    , Constants.GOOD_DESC);*/
                            LinkedMeModel linedModel = new LinkedMeModel();
                            linedModel.setDropId(mDropId);
                            ShareUtils.generateUrl(PoolDetailPageActivity.this, 2, "POOL", false, mUrl, mEntity.getDropName(), mEntity.getHeadImg(), Constants.POOL_DESC,linedModel);

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
        if (mEntity == null || mEntity.getDropId() == 0 || mEntity.getDropName() == null || mEntity.getHeadImg() == null || mEntity.getDropDesc() == null) {
            return;
        }
        ContactsSelectActivity.startForPool(this, mEntity.getDropId(), mEntity.getDropName(), mEntity.getHeadImg(), mEntity.getDropDesc());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_creator_avatar:
                if (!FastClickUtil.isFastDoubleClick()) {
                    UserProfileActivity.start(PoolDetailPageActivity.this, mEntity.getCreateUid());
                }
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == VerifyActivity.REQUSET_CODE_TEAM) {
            if (resultCode == VerifyActivity.RESULT_CODE_TEAM) {
                mPresenter.getData(mDropId);
            }
        }
    }

    @Override
    public void onGetDataSucceed(DropDetailsEntity entity) {
        mEntity = entity;
        mUrl = "http://api.waterdrop.xin/drops_wechat/app_h5/launch.html?action=dropInfo"
                + "&drop_id=" + entity.getDropId();


        updateUI();
    }

    @Override
    public void onAttentionSucceed() {
        mTvAttention.setText("已关注");
        mAttentionStatus = 1;
        mTvAttention.setSelected(true);
        if (mEntity != null) {
            mEntity.setAttentionNum(mEntity.getAttentionNum() + 1);
            setAttentionNum(mEntity.getAttentionNum());
        }

    }

    @Override
    public void onCancelAttentionSucceed() {
        mTvAttention.setText("+关注");
        mAttentionStatus = 0;
        mTvAttention.setSelected(false);
        if (mEntity != null) {
            mEntity.setAttentionNum(mEntity.getAttentionNum() - 1);
            setAttentionNum(mEntity.getAttentionNum());
        }
    }


    private void setAttentionNum(int num) {
        if (num >= 10000) {
            float i = num / 10000f;
            java.text.DecimalFormat df = new java.text.DecimalFormat("0.0");
            String format = df.format(i);
            mTvNumber.setText(format + "万");
        } else {
            mTvNumber.setText(num + "");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
//        overridePendingTransition(R.anim.zoom_out_entry, R.anim.zoom_out_exit);
    }


}
