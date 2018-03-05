package com.drops.waterdrop.ui.mine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.help.UserInfoManager;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BaseWebViewActivity;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.drops.waterdrop.ui.find.activity.GoodsDetailsActivity;
import com.drops.waterdrop.ui.mine.adapter.VIPGoodAdapter;
import com.drops.waterdrop.ui.mine.event.BuyMemberEvent;
import com.drops.waterdrop.ui.mine.presenter.MemberCenterPresenter;
import com.drops.waterdrop.ui.mine.view.IMemberCenterView;
import com.drops.waterdrop.ui.store.view.SpaceItemDecoration;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.storebanner.MZBannerView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.custom.CustomAnimation;
import com.netease.nim.uikit.model.MemberActiveEntitiy;
import com.netease.nim.uikit.model.UserCenterEntity;
import com.netease.nim.uikit.model.VipAreaEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/18 23:38
 */

public class MemberCenterActivity extends BaseActivity<IMemberCenterView, MemberCenterPresenter> implements IMemberCenterView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static final int TYPE_CLICK_EVENT_BUY = 1;
    public static final int TYPE_CLICK_EVENT_ACTIVE = 2;
    public static final int TYPE_CLICK_EVENT_VR = 3;

    private static final String STATEMENT_URL = "http://pond.waterdrop.xin/member-introduction.html";
    public static final int REQUEST_CODE_VR = 123;
    private VIPGoodAdapter mAdapter;

    public static void startActivity(Context context, int type) {
        Intent intent = new Intent(context, MemberCenterActivity.class);
        intent.putExtra(Constants.TYPE_CLICK_EVENT, type);
        if (type == MemberCenterActivity.TYPE_CLICK_EVENT_VR) {
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_VR);
        } else {
            context.startActivity(intent);
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MemberCenterActivity.class);
        context.startActivity(intent);
    }


    @Bind(R.id.amc_vip_icon)
    ImageView mVipIcon;
    @Bind(R.id.amc_vip_tips)
    TextView mVipTips;
    @Bind(R.id.amc_want_become_member)
    RelativeLayout mBecomeMemberArea;
    @Bind(R.id.amc_become_member_method1_tip)
    TextView mBecomeMemberM1Tip;
//    @Bind(R.id.amc_become_member_method2_tip)
//    TextView mBecomeMemberM2Tip;
    @Bind(R.id.amc_become_member_method3_tip)
    TextView mBecomeMemberM3Tip;

    @Bind(R.id.ll_vip)
    LinearLayout mLlVipArea;
    @Bind(R.id.iv_vrglass)
    ImageView mIvVRGlass;
    @Bind(R.id.tv_active_title)
    TextView mTvActiveTitle;
    @Bind(R.id.tv_active_data)
    TextView mTvActiveData;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.scroll_view)
    NestedScrollView mScrollView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.rl_header)
    RelativeLayout mRlHeader;

    private AlertDialog mActiveDialog;
    private UserCenterEntity entity;
    private View notDataView;
    private int type;
    private boolean isActive = false;

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initSwipeRefresh();
        extraSkip();
    }

    private void extraSkip() {
        if (getIntent() != null ) {
            type = getIntent().getIntExtra(Constants.TYPE_CLICK_EVENT, 0);
            if (type == TYPE_CLICK_EVENT_BUY) {
                if (isVip()) return;
                onBuyClick();
            } else if (type == TYPE_CLICK_EVENT_ACTIVE) {
                if (isVip()) return;
                onActiveClick();
            }
        }
    }

    private boolean isVip() {
        if (UserInfoManager.get().getData().isMemberRole) {
            ToastUtil.showShort("小主，您已经是会员了");
            return true;
        }
        return false;
    }

    private void initTitle(){
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "会员专区";
        options.isNeedNavigate = true;
        setMyToolbar(options);
    }

    private void initSwipeRefresh() {
        mSwipeRefresh = findView(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefresh.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        mRecyclerView = findView(R.id.recycler_view);
        initEmptyPager();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        mAdapter = new VIPGoodAdapter(0);
        mAdapter.setEmptyView(notDataView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(MZBannerView.dpToPx(10)));
        mAdapter.setOnItemClickListener(this);
        mAdapter.openLoadAnimation(new CustomAnimation());
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mPresenter.setLoadMoreAdapter(mAdapter);
    }

    private void initEmptyPager() {
        notDataView = LayoutInflater.from(this).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    @Override
    protected void initData() {
        updateUserInfo();
    }

    private void refreshUserCenter(){
        if (entity.isMemberRole){
            mVipIcon.setBackgroundResource(R.mipmap.icon_wd_vip2);
            String[] date;
            if (!TextUtils.isEmpty(entity.getMemberDate()) && (date = entity.getMemberDate().split("-")).length == 3){
                mVipTips.setText("有效期至：" + date[0] + "年" + date[1] + "月" + date[2] + "日");
            }
        } else {
            mVipIcon.setBackgroundResource(R.mipmap.icon_wd_vip1);
            mVipTips.setText("很抱歉，你还未成为水滴会员！");

            mBecomeMemberM1Tip.setText(Html.fromHtml(getString(R.string.tips_member_center_become_method_1)));
//            mBecomeMemberM2Tip.setText(Html.fromHtml(getString(R.string.tips_member_center_become_method_2)));
            mBecomeMemberM3Tip.setText(Html.fromHtml(getString(R.string.tips_member_center_become_method_3)));
        }
        mBecomeMemberArea.setVisibility(entity.isMemberRole ? View.GONE : View.VISIBLE);
        mLlVipArea.setVisibility(entity.isMemberRole ? View.VISIBLE : View.GONE);
        mScrollView.setNestedScrollingEnabled(entity.isMemberRole);
        mSwipeRefresh.setEnabled(entity.isMemberRole);

        if (entity.isMemberRole) {
            mPresenter.getData(false, false);
        }
    }

    @OnClick(R.id.amc_statement)
    void onStatementClick(){
        BaseWebViewActivity.startActivity(this, STATEMENT_URL, "会员权益");
    }

    @OnClick(R.id.amc_active)
    void onActiveClick(){
        if (mActiveDialog == null){
            createDialog();
        }
        mActiveDialog.show();
    }

//    @OnClick(R.id.amc_copy_wx)
//    void onCopyWxClick(){
//        mPresenter.copyWxToClipboard();
//    }

    @OnClick(R.id.amc_buy)
    void onBuyClick(){
        if (!WaterDropApp.mWxApi.isWXAppInstalled()){
            ToastUtil.showLong("请先安装微信客户端");
            return;
        }

        mPresenter.applyPayForMember();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected MemberCenterPresenter createPresenter() {
        return new MemberCenterPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_member_center;
    }

    private EditText mActiveMemberInput;
    private void createDialog(){
        View view = View.inflate(this, R.layout.dialog_active_member, null);
        mActiveMemberInput = (EditText) view.findViewById(R.id.dam_input);
        view.findViewById(R.id.dam_active).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mActiveMemberInput.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    ToastUtil.showLong("请输入激活码");
                    return;
                }

                mActiveMemberInput.setText(null);
                mPresenter.requestActiveMember(content);
                mActiveDialog.dismiss();
            }
        });
        view.findViewById(R.id.dam_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActiveDialog.dismiss();
            }
        });
        mActiveDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create();
        mActiveDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
    }

    @Override
    public void setMemberInfo(MemberActiveEntitiy entity) {
        if (entity.isOnlyActiveMember()) {
            ToastUtil.showLong("激活成功！");
            if (type == TYPE_CLICK_EVENT_VR) {
                isActive = true;
                updateUserInfo();
                return;
            }
        }

        updateUserInfo();

        if (entity.isActiveAndGift()){
            SelectAddressForSendVrActivity.startActivity(this, entity);
        }

    }

    @Override
    public void setRefresh(boolean b) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(b);
        }
    }

    @Override
    public void onGetActiveData(VipAreaEntity.BannerBean banner) {
        mTvActiveData.setText(banner.getDesc());
        mTvActiveTitle.setText(banner.getTitle());
        GlideUtil.showImageViewFirst(this, R.mipmap.img_qs_90x90, banner.getBannerPhoto(), mIvVRGlass);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefresh.setEnabled(b);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefresh.setEnabled(false);
        mPresenter.getData(false, true);
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getData(true,false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        VipAreaEntity.ResultsBean item = mAdapter.getItem(position);
        if (item != null && !TextUtils.isEmpty(item.getGoodId())) {
            WaterDropApp.PAY_FROM = Constants.PAY_FROM_VIP_AREA;
            GoodsDetailsActivity.start(this, item.getGoodId(), Constants.VIP_TIP_ID, Constants.VIP_DROP_ID, Constants.VIP_TIP_TITLE);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void updateUserInfo(){
        UserInfoManager.get().autoGet(new UserInfoManager.Callback() {
            @Override
            public void onGet(UserCenterEntity userCenterEntity) {
                entity = userCenterEntity;
                if (type == TYPE_CLICK_EVENT_VR && isActive) {
                    sendBuyMemberSuccBroadcast();
                    return;
                }

                refreshUserCenter();
            }

            @Override
            public void onError(String msg) {

            }
        }, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && type == TYPE_CLICK_EVENT_VR) {
            isActive = true;
            updateUserInfo();
        }
    }

    private void sendBuyMemberSuccBroadcast() {
        setResult(RESULT_OK);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BuyMemberEvent event) {
        if (event.isSucc) {
            isActive = true;
            updateUserInfo();
        }
    }

    @OnClick(R.id.rl_header)
    void onHeaderClick(){
        String s = "http://api.waterdrop.xin/drops_wechat/app_h5/banner-action.html";
        CommonWebActivity.start(this, 0, s);
    }

}
