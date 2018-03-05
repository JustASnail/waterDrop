package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.activity.CollectPostListActivity;
import com.drops.waterdrop.ui.find.activity.FriendCreatePostListActivity;
import com.drops.waterdrop.ui.mine.presenter.UserProfilePresenter;
import com.drops.waterdrop.ui.mine.view.IUserProfileView;
import com.drops.waterdrop.ui.session.activity.MyPoolListActivity;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.FriendDetailEntity;
import com.netease.nim.uikit.session.activity.ContactsSelectActivity;

import java.util.List;

import butterknife.Bind;

/**
 * 用户资料
 * Created by dengxiaolei on 2017/6/29.
 */

public class UserProfileActivity extends BaseActivity<IUserProfileView, UserProfilePresenter> implements IUserProfileView, View.OnClickListener {

    @Bind(R.id.iv_head)
    ImageView mIvHead;
    @Bind(R.id.tv_desc)
    TextView mTvDesc;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.rl_attention_pool)
    RelativeLayout mRlAttentionPool;
    @Bind(R.id.iv_gender)
    ImageView mIvGender;
    @Bind(R.id.iv_tang_zhu)
    ImageView mIvTangZhu;
    @Bind(R.id.iv_tie_zhu)
    ImageView mIvTieZhu;
    @Bind(R.id.tv_attention_pool_num)
    TextView mTvAttentionPoolNum;
    @Bind(R.id.tv_create_pool_num)
    TextView mTvCreatePoolNum;
    @Bind(R.id.rl_create_pool)
    RelativeLayout mRlCreatePool;
    @Bind(R.id.icon_next3)
    ImageView mIconNext3;
    @Bind(R.id.tv_collection_post_num)
    TextView mTvCollectionPostNum;
    @Bind(R.id.rl_collection_post)
    RelativeLayout mRlCollectionPost;
    @Bind(R.id.tv_create_post_num)
    TextView mTvCreatePostNum;
    @Bind(R.id.rl_create_post)
    RelativeLayout mRlCreatePost;
    @Bind(R.id.tv_bottom_btn)
    TextView mTvBottomBtn;
    @Bind(R.id.iv_pool_owner_tag)
    ImageView mIvPoolOwnerTag;
    @Bind(R.id.iv_post_owner_tag)
    ImageView mIvPostOwnerTag;
    private FriendDetailEntity mEntity;


    public static void start(Context context, long userId) {
        Intent starter = new Intent(context, UserProfileActivity.class);
        starter.putExtra(Constants.EXTRA_USER_ID, userId);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        mPresenter.parseIntent(getIntent());
        initTitle();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "个人名片";
        options.imgRightId = R.mipmap.icon_st_fx;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }

    @Override
    protected void initListener() {
        mRlAttentionPool.setOnClickListener(this);
        mRlCreatePool.setOnClickListener(this);
        mRlCollectionPost.setOnClickListener(this);
        mRlCreatePost.setOnClickListener(this);
    }

    @Override
    protected UserProfilePresenter createPresenter() {
        return new UserProfilePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_profile;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onGetDataSucceed(FriendDetailEntity entity) {
        updateUI(entity);
    }

    @Override
    public void onAddFriendSucceed() {
        mTvBottomBtn.setText("等待验证");
        mTvBottomBtn.setEnabled(false);
    }

    private void updateUI(final FriendDetailEntity entity) {
        mEntity = entity;
        String avatarUrl = entity.getPhoto();
        GlideUtil.showImageView(this,R.drawable.img_qs_375x322,  avatarUrl, mIvHead);
        mTvName.setText(entity.getNickName());
        String userDesc = entity.getUserDesc();
        if (TextUtils.isEmpty(userDesc)) {
            mTvDesc.setText("这个人太酷， 什么都没有留下！");
        } else {
            mTvDesc.setText(userDesc);
        }

        mTvAttentionPoolNum.setText(entity.getAttentionDropSize() + "");
        mTvCreatePoolNum.setText(entity.getCreateDropSize() + "");
        mTvCollectionPostNum.setText(entity.getCollectTipSize() + "");
        mTvCreatePostNum.setText(entity.getCreateTipSize() + "");

        List<FriendDetailEntity.IdentifiesBean> identifies = entity.getIdentifies();
        if (identifies != null && identifies.size() > 0) {
            mRlCreatePool.setVisibility(View.VISIBLE);
            mRlCreatePost.setVisibility(View.VISIBLE);

            for (FriendDetailEntity.IdentifiesBean identify : identifies) {
                if (identify.getType() == 1) {//明星塘主
                    setStarPoolVIP(identify.getLevel());
                } else if (identify.getType() == 2) {//贴主
                    setTipVIP(identify.getLevel());
                } else if (identify.getType() == 3) {//塘主
                    setNormalPoolVIP(identify.getLevel());

                }
            }
        } else {
            mRlCreatePool.setVisibility(View.GONE);
            mRlCreatePost.setVisibility(View.GONE);
        }
/*

        List<FriendDetailEntity.DropsBean> drops = entity.getDrops();
        if (drops != null && drops.size() > 0) {
            mTvPoolNum.setText(drops.size() + "");
        } else {
            mTvPoolNum.setText(0 + "");
        }
*/

        int sex = entity.getSex();
        if (sex == 1) {//男
            mIvGender.setImageResource(R.mipmap.icon_grxx_xb_1);
            mIvGender.setVisibility(View.VISIBLE);
        } else if (sex == 2) {//女
            mIvGender.setImageResource(R.mipmap.icon_grxx_xb_2);
            mIvGender.setVisibility(View.VISIBLE);
        }

        if (entity.getUid() == MyUserCache.getUserUid()) {
            mTvBottomBtn.setVisibility(View.GONE);
        } else {
            mTvBottomBtn.setVisibility(View.VISIBLE);
            if (entity.getRelationStatus() == 1) {
                mTvBottomBtn.setText("开始聊天");
                mTvBottomBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.goMessage(entity.getNeteaseAccid());
                    }
                });
            } else if (entity.getRelationStatus() > 1) {
                mTvBottomBtn.setText("等待验证");

            } else {
                mTvBottomBtn.setText("添加好友");
                mTvBottomBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.addFriend(entity.getUid());
                    }
                });
            }

        }


    }

    private void setTipVIP(int level) {
        switch (level) {
            case 1:
                mIvPostOwnerTag.setImageResource(R.drawable.icon_grmp_tz_1);
                break;
            case 2:
                mIvPostOwnerTag.setImageResource(R.drawable.icon_grmp_tz_2);
                break;
            case 3:
                mIvPostOwnerTag.setImageResource(R.drawable.icon_grmp_tz_3);
                break;
            case 4:
                mIvPostOwnerTag.setImageResource(R.drawable.icon_grmp_tz_4);
                break;
            case 5:
                mIvPostOwnerTag.setImageResource(R.drawable.icon_grmp_tz_5);
                break;
            case 6:
                mIvPostOwnerTag.setImageResource(R.drawable.icon_grmp_tz_6);
                break;
            case 7:
                mIvPostOwnerTag.setImageResource(R.drawable.icon_grmp_tz_7);
                break;
            case 8:
                mIvPostOwnerTag.setImageResource(R.drawable.icon_grmp_tz_8);
                break;
            case 9:
                mIvPostOwnerTag.setImageResource(R.drawable.icon_grmp_tz_9);
                break;
            case 10:
                mIvPostOwnerTag.setImageResource(R.drawable.icon_grmp_tz_10);
                break;
        }
        mIvPostOwnerTag.setVisibility(View.VISIBLE);
    }

    private void setStarPoolVIP(int level) {
        switch (level) {
            case 1:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_mxtz_1);
                break;
            case 2:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_mxtz_2);
                break;
            case 3:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_mxtz_3);
                break;
            case 4:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_mxtz_4);
                break;
            case 5:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_mxtz_5);
                break;
            case 6:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_mxtz_6);
                break;
            case 7:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_mxtz_7);
                break;
            case 8:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_mxtz_8);
                break;
            case 9:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_mxtz_9);
                break;
            case 10:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_mxtz_10);
                break;
        }
        mIvPoolOwnerTag.setVisibility(View.VISIBLE);

    }


    private void setNormalPoolVIP(int level) {
        switch (level) {
            case 1:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_rztz_1);
                break;
            case 2:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_rztz_2);
                break;
            case 3:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_rztz_3);
                break;
            case 4:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_rztz_4);
                break;
            case 5:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_rztz_5);
                break;
            case 6:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_rztz_6);
                break;
            case 7:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_rztz_7);
                break;
            case 8:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_rztz_8);
                break;
            case 9:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_rztz_9);
                break;
            case 10:
                mIvPoolOwnerTag.setImageResource(R.drawable.icon_grmp_rztz_10);
                break;
        }
        mIvPoolOwnerTag.setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View v) {
        if (!FastClickUtil.isFastDoubleClick()) {
            switch (v.getId()) {
                case R.id.rl_attention_pool:
                    if (mEntity != null) {
                        MyPoolListActivity.start(UserProfileActivity.this, mEntity.getUid(), Constants.TITLE_FOCUS);
                    }
                    break;
                case R.id.rl_create_pool:
                    MyPoolListActivity.start(UserProfileActivity.this, mEntity.getUid(), Constants.TITLE_CREATE);
                    break;
                case R.id.rl_collection_post:
                    CollectPostListActivity.start(UserProfileActivity.this, mEntity.getUid());
                    break;
                case R.id.rl_create_post:
                    FriendCreatePostListActivity.start(UserProfileActivity.this, mEntity.getUid());
                    break;
            }
        }

    }

    @Override
    protected void onRightImgClick() {
        super.onRightImgClick();
        if (mEntity != null) {
            ContactsSelectActivity.startForUser(this, mEntity.getUid(), mEntity.getNickName(), mEntity.getPhoto(), mEntity.getSex());
        }
    }
}
