package com.drops.waterdrop.ui.pool.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.activity.MyTicketActivity;
import com.netease.nim.uikit.common.util.FastClickUtil;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/6/9.
 */

public class MyQiangPiaoDetailsActivity extends BaseActivity implements View.OnClickListener {
    public static final String EXTRA_TYPE = "PAGE_TYPE";
    @Bind(R.id.goods1)
    View mGoods1;
    @Bind(R.id.goods2)
    View mGoods2;
    @Bind(R.id.rl_goolds_list)
    RelativeLayout mRlGooldsList;
    @Bind(R.id.friend_help)
    View mFriendHelp;
    @Bind(R.id.top)
    View mTop;
    @Bind(R.id.my_ticket)
    View mMyTicket;
    @Bind(R.id.rl_details)
    RelativeLayout mRlDetails;
    @Bind(R.id.rl_friend_help)
    RelativeLayout mRlFriendHelp;
    @Bind(R.id.rl_top)
    RelativeLayout mRlTop;
    @Bind(R.id.iv_btn)
    ImageView mIvBtn;
    @Bind(R.id.iv_goods)
    ImageView mIvGoods;
    @Bind(R.id.iv_details)
    ImageView mIvDetails;
    @Bind(R.id.iv_friend_help)
    ImageView mIvFriendHelp;
    @Bind(R.id.iv_top)
    ImageView mIvTop;

    private int PAGE_TYPE = 4;

    public static void start(Context context, int type) {
        Intent starter = new Intent(context, MyQiangPiaoDetailsActivity.class);
        starter.putExtra(EXTRA_TYPE, type);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        parseIntent();

        switch (PAGE_TYPE) {
            case 1:
                setQiangPiaoDetailsView();
                break;
            case 2:
                setFriendHelpView();
                break;
            case 3:
                setTopView();
                break;
            case 4:
                setGoodsListView();
                break;

        }
    }


    private void setGoodsListView() {
        initTitle("商品列表");
        mRlGooldsList.setVisibility(View.VISIBLE);
        mIvGoods.setImageResource(R.drawable.test_img_hd_gw);
        mGoods1.setOnClickListener(this);
        mGoods2.setOnClickListener(this);

    }

    private void setTopView() {
        initTitle("每日Top10");
        mIvTop.setImageResource(R.drawable.test_top);
        mRlTop.setVisibility(View.VISIBLE);
        mIvBtn.setVisibility(View.VISIBLE);
        mIvBtn.setOnClickListener(this);

    }

    private void setFriendHelpView() {
        initTitle("好友助力");
        mIvFriendHelp.setImageResource(R.drawable.test_hyzl);
        mRlFriendHelp.setVisibility(View.VISIBLE);
        mIvBtn.setVisibility(View.VISIBLE);
        mIvBtn.setOnClickListener(this);

    }

    private void setQiangPiaoDetailsView() {
        initTitle("水滴无界");
        mIvDetails.setImageResource(R.drawable.test_sdwj_bg);
        mRlDetails.setVisibility(View.VISIBLE);
        mIvBtn.setVisibility(View.VISIBLE);
        mFriendHelp.setOnClickListener(this);
        mTop.setOnClickListener(this);
        mMyTicket.setOnClickListener(this);
        mIvBtn.setOnClickListener(this);
    }

    private void parseIntent() {
        PAGE_TYPE = getIntent().getIntExtra(EXTRA_TYPE, 4);
    }

    private void initTitle(String title) {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = title;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_qiang_piao_details;
    }


    @Override
    public void onClick(View v) {
        if (!FastClickUtil.isFastDoubleClick()) {
            switch (v.getId()) {
                case R.id.goods1:
//                    GoodsDetailsActivity.start(this, OrderConfirmationPresenter.TYPE_ACTIVE);

                    break;
                case R.id.goods2:
//                    GoodsDetailsActivity.start(this, OrderConfirmationPresenter.TYPE_ACTIVE);

                    break;
                case R.id.friend_help:
                    MyQiangPiaoDetailsActivity.start(this, 2);
                    break;
                case R.id.top:
                    MyQiangPiaoDetailsActivity.start(this, 3);

                    break;
                case R.id.my_ticket:
                    MyTicketActivity.start(this);
                    break;
                case R.id.iv_btn:
                    MyQiangPiaoDetailsActivity.start(this, 4);

                    break;
            }

        }
    }


}
