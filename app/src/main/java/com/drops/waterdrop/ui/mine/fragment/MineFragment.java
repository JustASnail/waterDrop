package com.drops.waterdrop.ui.mine.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.help.UserInfoManager;
import com.drops.waterdrop.im.fragment.MainTabFragment;
import com.drops.waterdrop.ui.mine.activity.AddressManageActivity;
import com.drops.waterdrop.ui.mine.activity.CashGiftActivity;
import com.drops.waterdrop.ui.mine.activity.FocusPoolActivity;
import com.drops.waterdrop.ui.mine.activity.FootprintActivity;
import com.drops.waterdrop.ui.mine.activity.GoldenBeanActivity;
import com.drops.waterdrop.ui.mine.activity.MemberCenterActivity;
import com.drops.waterdrop.ui.mine.activity.MineBankActivity;
import com.drops.waterdrop.ui.mine.activity.MineCodeActivity;
import com.drops.waterdrop.ui.mine.activity.MyCollectionActivity;
import com.drops.waterdrop.ui.mine.activity.MyOrderPageActivity;
import com.drops.waterdrop.ui.mine.activity.MyPostListActivity;
import com.drops.waterdrop.ui.mine.activity.OrderListActivity;
import com.drops.waterdrop.ui.mine.activity.SettingActivity;
import com.drops.waterdrop.ui.mine.event.UserInfoEvent;
import com.drops.waterdrop.ui.session.activity.MyPoolListActivity;
import com.drops.waterdrop.util.ShadowUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.drop.DropFake;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.UserCenterEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;

import rx.Observable;

import static com.netease.nim.uikit.Constants.DEFALUT_FRIEND_UID;
import static com.netease.nim.uikit.Constants.TITLE_MINE;

/**
 * Created by dengxiaolei on 2017/5/15.
 * Modify by Mr.Smile
 */

public class MineFragment extends MainTabFragment implements View.OnClickListener {
    private static final String TAG = "MineFragment";

    private NimUserInfo userInfo;
    private String mAccount;
    private HeadImageView mIvHead;
    private ImageView mIvGenderTag;
    private TextView mTvName;
    private TextView mTvAccount;
    private View mTvSetting;
    private TextView mTvQianming;
    private LinearLayout mLlJinDou;
    private TextView mTvCountJinDou;
    private TextView mTvCountZuJi;
    private LinearLayout mLlZuJi;
    private TextView mTvCollection;
    private TextView mTvFocusSTang;
    private RelativeLayout mLlOrderFinish, mLlOrderNoPay, mLlOrderUnreceived, mLlOrderUnshipped, mLlOrderExchange;
    private ImageView mIvTiezu, mIvTangzu;
    private View mTvPool;
    private View mTvShuiTie;
    private View mLinePost;
    private View mLinePool;
    private View mLineBank;
    private UserCenterEntity mEntity;
    private SwipeRefreshLayout mSwipeRefresh;
    private View mTvMineCode;
    private TextView mTvMineBank;
    private View mTvGift;
    private ImageView mIvVIP;
    private TextView mTvNumNoPay,mTvNumExchange,mTvNumUnreceived,mTvNumUnshipped, mTvNumFinish;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(UserInfoEvent userInfoEvent) {
        dealEvent(userInfoEvent);

    }

    private void dealEvent(UserInfoEvent userInfoEvent) {
        int type = userInfoEvent.type;
       if (type == Constants.TYPE_BEANS) {
            mTvCountJinDou.setText(userInfoEvent.beans);
        }
    }

    @Override
    protected void onInit() {
        initViews();
        intListener();
        getUserInfo(false);
    }

    private void getUserInfo(boolean isRefresh) {
        UserInfoManager.get().autoGet(new UserInfoManager.Callback() {
            @Override
            public void onGet(UserCenterEntity entity) {
                mEntity = entity;
                initData(entity);
                if (mSwipeRefresh.isRefreshing()) {
                    mSwipeRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(String msg) {
                if (mSwipeRefresh.isRefreshing()) {
                    mSwipeRefresh.setRefreshing(false);
                }
                ToastUtil.showShort(msg);
            }
        }, isRefresh);
    }

    private void initData(UserCenterEntity entity) {
        MyUserCache.saveUserPhoto(entity.getPhoto());
        setLevelImgNew(entity);
        updateUI();
    }

    private void initViews() {

        mIvHead = findView(R.id.iv_head);
        mIvGenderTag = findView(R.id.iv_gender_tag);
        mTvName = findView(R.id.tv_name);
        mTvAccount = findView(R.id.tv_account);
        mTvSetting = findView(R.id.tv_setting);
        mTvQianming = findView(R.id.tv_qianming);
        mLlJinDou = findView(R.id.ll_jindou);
        mLlZuJi = findView(R.id.ll_zuji);
        mTvCountJinDou = findView(R.id.tv_count_jindou);
        mTvCountZuJi = findView(R.id.tv_count_zuji);
        mTvCollection = findView(R.id.tv_collection);
        mTvFocusSTang = findView(R.id.tv_stang_focus);
        mLlOrderFinish = findView(R.id.ll_order_finish);
        mLlOrderNoPay = findView(R.id.ll_order_no_pay);
        mLlOrderUnreceived = findView(R.id.ll_order_unreceived);
        mLlOrderUnshipped = findView(R.id.ll_order_unshipped);
        mLlOrderExchange = findView(R.id.ll_order_exchange);
        mIvTangzu = findView(R.id.iv_tangzu);
        mIvTiezu = findView(R.id.iv_tiezu);
        mTvPool = findView(R.id.tv_my_pool);
        mTvShuiTie = findView(R.id.tv_shui_tie);
        mLinePool = findView(R.id.line_my_pool);
        mLinePost = findView(R.id.line_my_post);
        mLineBank = findView(R.id.line_my_bank);
        mTvMineCode = findView(R.id.tv_mine_code);
        mTvMineBank = findView(R.id.tv_mine_bank);
        mTvGift = findView(R.id.tv_gift);
        mIvVIP = findView(R.id.iv_vip);
        mSwipeRefresh = findView(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));

        ShadowUtil.shadow(getContext(), findView(R.id.shadow1));
        ShadowUtil.shadow(getContext(), findView(R.id.shadow2));

        mTvNumExchange = findView(R.id.tv_order_num_exchange);
        mTvNumNoPay = findView(R.id.tv_order_num_no_pay);
        mTvNumUnreceived = findView(R.id.tv_order_num_unreceived);
        mTvNumUnshipped = findView(R.id.tv_order_num_unshipped);
        mTvNumFinish = findView(R.id.tv_order_num_finish);
    }


    private void updateUI() {
        String photo = mEntity.getPhoto();
        GlideUtil.showImageViewFirst(getContext(), R.drawable.img_qs_60x60, photo, mIvHead);
        int userSex = MyUserCache.getUserSex();
        mIvGenderTag.setVisibility(View.VISIBLE);
        if (userSex == 1) {
            mIvGenderTag.setImageResource(R.mipmap.icon_wd_xb_2);
        } else if (userSex == 2) {
            mIvGenderTag.setImageResource(R.mipmap.icon_wd_xb_1);
        } else {
            mIvGenderTag.setVisibility(View.GONE);
        }

        String userNickName = MyUserCache.getUserNickName();
        if (!TextUtils.isEmpty(userNickName)) {
            mTvName.setText(userNickName);
        } else {
            mTvName.setText(MyUserCache.getUserMobile());
        }

        String desc  = MyUserCache.getUserDesc();
        if (TextUtils.isEmpty(desc)) {
            mTvQianming.setText("你颜值那么高，怎能没有自己的个性签名！");
        } else {
            mTvQianming.setText(desc);
        }
        numChanged();
    }

    private void numChanged() {
        mTvCountJinDou.setText(mEntity.getBeans());
        mTvCountZuJi.setText(mEntity.getFootPrintNum() + "");

        if (mEntity.getReadyPayNum() > 0) {
            mTvNumNoPay.setVisibility(View.VISIBLE);
            mTvNumNoPay.setText(mEntity.getReadyPayNum() + "");
        } else {
            mTvNumNoPay.setVisibility(View.GONE);
        }
        if (mEntity.getReadyDeliveryNum() > 0) {
            mTvNumUnshipped.setVisibility(View.VISIBLE);
            mTvNumUnshipped.setText(mEntity.getReadyDeliveryNum() + "");
        } else {
            mTvNumUnshipped.setVisibility(View.GONE);
        }
        if (mEntity.getReadyReceiveNum() > 0) {
            mTvNumUnreceived.setVisibility(View.VISIBLE);
            mTvNumUnreceived.setText(mEntity.getReadyReceiveNum() + "");
        } else {
            mTvNumUnreceived.setVisibility(View.GONE);
        }
        if (mEntity.getReadyRestoreNum() > 0) {
            mTvNumExchange.setVisibility(View.VISIBLE);
            mTvNumExchange.setText(mEntity.getReadyRestoreNum() + "");
        } else {
            mTvNumExchange.setVisibility(View.GONE);
        }
        if (mEntity.getReadyFinishNum() > 0) {
            mTvNumFinish.setVisibility(View.VISIBLE);
            mTvNumFinish.setText(mEntity.getReadyFinishNum() + "");
        } else {
            mTvNumFinish.setVisibility(View.GONE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mIvHead != null && mEntity != null) {
            updateUI();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
//        inflater.inflate(R.menu.menu_setting, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_setting) {
            if (!FastClickUtil.isFastDoubleClick()) {
                SettingActivity.start(getContext());
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void intListener() {
        findView(R.id.tv_address_manage).setOnClickListener(this);
//        findView(R.id.tv_ticket).setOnClickListener(this);
        findView(R.id.rl_order).setOnClickListener(this);
        mTvSetting.setOnClickListener(this);
        mLlJinDou.setOnClickListener(this);
        mLlZuJi.setOnClickListener(this);
        mTvCollection.setOnClickListener(this);
        mTvFocusSTang.setOnClickListener(this);
        mLlOrderNoPay.setOnClickListener(this);
        mLlOrderExchange.setOnClickListener(this);
        mLlOrderUnshipped.setOnClickListener(this);
        mLlOrderUnreceived.setOnClickListener(this);
        mLlOrderFinish.setOnClickListener(this);
        mTvPool.setOnClickListener(this);
        mTvShuiTie.setOnClickListener(this);
        mIvHead.setOnClickListener(this);
        mTvName.setOnClickListener(this);
        mTvQianming.setOnClickListener(this);
        mTvMineCode.setOnClickListener(this);
        mTvMineBank.setOnClickListener(this);
        mTvGift.setOnClickListener(this);
        findView(R.id.tv_member_center).setOnClickListener(this);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserInfo(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_member_center:
                if (!FastClickUtil.isFastDoubleClick()){
                    MemberCenterActivity.startActivity(getActivity());
                }
                break;
            case R.id.tv_address_manage:
                if (!FastClickUtil.isFastDoubleClick()) {
                    AddressManageActivity.start(getContext());
                }
                break;
            case R.id.iv_head:
            case R.id.tv_name:
            case R.id.tv_qianming:
                if (!FastClickUtil.isFastDoubleClick()) {
                    SettingActivity.start(getContext());
                }
                break;
//            case R.id.tv_ticket:
//                if (!FastClickUtil.isFastDoubleClick()) {
//                    MyTicketActivity.start(getContext());
//                }
//                break;
            case R.id.tv_setting:
                if (!FastClickUtil.isFastDoubleClick()) {
                    SettingActivity.start(getContext());
                }
                break;
            case R.id.ll_zuji:
                if (!FastClickUtil.isFastDoubleClick()) {
                    startActivity(new Intent(getContext(), FootprintActivity.class));
                }
                break;
            case R.id.ll_jindou:
                if (!FastClickUtil.isFastDoubleClick()) {
                    startActivity(new Intent(getContext(), GoldenBeanActivity.class));
                }
                break;
            case R.id.tv_collection:
                if (!FastClickUtil.isFastDoubleClick()) {
                    startActivity(new Intent(getContext(), MyCollectionActivity.class));
                }
                break;
            case R.id.tv_stang_focus:
                if (!FastClickUtil.isFastDoubleClick()) {
                    FocusPoolActivity.start(getContext());
                }
                break;
            case R.id.ll_order_exchange:
                if (!FastClickUtil.isFastDoubleClick()) {
                    OrderListActivity.start(getContext(), getString(R.string.order_exchange));
                }
                break;
            case R.id.rl_order:
                if (!FastClickUtil.isFastDoubleClick()) {
                    MyOrderPageActivity.start(getContext(),Constants.ALL);
                }
                break;
            case R.id.ll_order_finish:
                if (!FastClickUtil.isFastDoubleClick()) {
                    MyOrderPageActivity.start(getContext(), Constants.UNCOMMENT);
//                    OrderListActivity.start(getContext(), getString(R.string.order_uncomment));
                }
                break;
            case R.id.ll_order_no_pay:
                if (!FastClickUtil.isFastDoubleClick()) {
                    MyOrderPageActivity.start(getContext(), Constants.UNPAY);
//                    OrderListActivity.start(getContext(), getString(R.string.order_non_payment));
                }
                break;
            case R.id.ll_order_unreceived:
                if (!FastClickUtil.isFastDoubleClick()) {
//                OrderListActivity.start(getContext(), getString(R.string.order_unreceive));
                    MyOrderPageActivity.start(getContext(), Constants.UNRECEIVE);
                }
                break;
            case R.id.ll_order_unshipped:
                if (!FastClickUtil.isFastDoubleClick()) {
                    MyOrderPageActivity.start(getContext(), Constants.UNSHIP);
//                    OrderListActivity.start(getContext(), getString(R.string.order_unshipped));
                }
                break;
            case R.id.tv_my_pool:
                if (!FastClickUtil.isFastDoubleClick()) {
                    MyPoolListActivity.start(getContext(), DEFALUT_FRIEND_UID, TITLE_MINE);
                }
                break;
            case R.id.tv_shui_tie:
                if (!FastClickUtil.isFastDoubleClick()) {
                    MyPostListActivity.start(getContext(), DEFALUT_FRIEND_UID, TITLE_MINE);
                }
                break;
            case R.id.tv_mine_code:
                if (!FastClickUtil.isFastDoubleClick()) {
                    MineCodeActivity.start(getContext());
                }
                break;
            case R.id.tv_mine_bank:
                if (!FastClickUtil.isFastDoubleClick())
                    MineBankActivity.start(getContext());
                break;
            case R.id.tv_gift:
                if (!FastClickUtil.isFastDoubleClick())
                    CashGiftActivity.start(getContext());
                break;
        }
    }

    private void setLevelImgNew(UserCenterEntity entity) {
        if (entity.isCommonRole){
            mIvTangzu.setVisibility(View.GONE);
            mIvTiezu.setVisibility(View.GONE);
            mTvPool.setVisibility(View.GONE);
            mLinePool.setVisibility(View.GONE);
            mLinePost.setVisibility(View.GONE);
            mTvShuiTie.setVisibility(View.GONE);
            mTvMineBank.setVisibility(View.GONE);
            mLineBank.setVisibility(View.GONE);
            return;
        }

        if (entity.isTangZhuRole){//认证塘主
            mIvTangzu.setVisibility(View.VISIBLE);
            mTvPool.setVisibility(View.VISIBLE);
            mLinePool.setVisibility(View.VISIBLE);
            GlideUtil.showImageView(getContext(), getFamousTangzu(entity.levelTangZhu), mIvTangzu);
        }

        if (entity.isTieZhuRole){//帖主
            mIvTiezu.setVisibility(View.VISIBLE);
            mTvShuiTie.setVisibility(View.VISIBLE);
            mLinePost.setVisibility(View.VISIBLE);
            GlideUtil.showImageView(getContext(), getTieZuLevelImg(entity.levelTieZhu), mIvTiezu);
        }

        if (entity.isSuperTangZhuRole){//明星塘主
            mIvTangzu.setVisibility(View.VISIBLE);
            mTvPool.setVisibility(View.VISIBLE);
            mLinePool.setVisibility(View.VISIBLE);
            GlideUtil.showImageView(getContext(), getRenZhengTangzu(entity.levelSuperTangZhu), mIvTangzu);
        }

        if (entity.isSupplierRole){//供应商
            mLineBank.setVisibility(View.VISIBLE);
            mTvMineBank.setVisibility(View.VISIBLE);
        }

        if (entity.isMemberRole){//会员
            mLineBank.setVisibility(View.VISIBLE);
            mTvMineBank.setVisibility(View.VISIBLE);
            mIvVIP.setVisibility(View.VISIBLE);
        }
    }

    private int getFamousTangzu(int type) {
        int res = R.mipmap.icon_wd_mxtangzhu_1;
        switch (type) {
            case 1:
                res = R.mipmap.icon_wd_mxtangzhu_1;
                break;
            case 2:
                res = R.mipmap.icon_wd_mxtangzhu_2;
                break;
            case 3:
                res = R.mipmap.icon_wd_mxtangzhu_3;
                break;
            case 4:
                res = R.mipmap.icon_wd_mxtangzhu_4;
                break;
            case 5:
                res = R.mipmap.icon_wd_mxtangzhu_5;
                break;
            case 6:
                res = R.mipmap.icon_wd_mxtangzhu_6;
                break;
            case 7:
                res = R.mipmap.icon_wd_mxtangzhu_7;
                break;
            case 8:
                res = R.mipmap.icon_wd_mxtangzhu_8;
                break;
            case 9:
                res = R.mipmap.icon_wd_mxtangzhu_9;
                break;
            case 10:
                res = R.mipmap.icon_wd_mxtangzhu_10;
                break;
        }
        return res;
    }

    private int getTieZuLevelImg(int type) {
        int res = R.mipmap.icon_wd_tiezhu_1;
        switch (type) {
            case 1:
                res = R.mipmap.icon_wd_tiezhu_1;
                break;
            case 2:
                res = R.mipmap.icon_wd_tiezhu_2;
                break;
            case 3:
                res = R.mipmap.icon_wd_tiezhu_3;
                break;
            case 4:
                res = R.mipmap.icon_wd_tiezhu_4;
                break;
            case 5:
                res = R.mipmap.icon_wd_tiezhu_5;
                break;
            case 6:
                res = R.mipmap.icon_wd_tiezhu_6;
                break;
            case 7:
                res = R.mipmap.icon_wd_tiezhu_7;
                break;
            case 8:
                res = R.mipmap.icon_wd_tiezhu_6;
                break;
            case 9:
                res = R.mipmap.icon_wd_tiezhu_9;
                break;
            case 10:
                res = R.mipmap.icon_wd_tiezhu_10;
                break;
        }
        return res;
    }

    private int getRenZhengTangzu(int type) {
        int res = R.mipmap.icon_wd_rztangzhu_1;
        switch (type) {
            case 1:
                res = R.mipmap.icon_wd_rztangzhu_1;
                break;
            case 2:
                res = R.mipmap.icon_wd_rztangzhu_2;
                break;
            case 3:
                res = R.mipmap.icon_wd_rztangzhu_3;
                break;
            case 4:
                res = R.mipmap.icon_wd_rztangzhu_4;
                break;
            case 5:
                res = R.mipmap.icon_wd_rztangzhu_5;
                break;
            case 6:
                res = R.mipmap.icon_wd_rztangzhu_6;
                break;
            case 7:
                res = R.mipmap.icon_wd_rztangzhu_7;
                break;
            case 8:
                res = R.mipmap.icon_wd_rztangzhu_8;
                break;
            case 9:
                res = R.mipmap.icon_wd_rztangzhu_9;
                break;
            case 10:
                res = R.mipmap.icon_wd_rztangzhu_10;
                break;
        }
        return res;
    }
}
