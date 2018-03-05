package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.event.UserInfoEvent;
import com.drops.waterdrop.ui.mine.presenter.ScorePresenter;
import com.drops.waterdrop.ui.mine.view.IScoreView;
import com.drops.waterdrop.widget.HZHRatingBar;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.OrderEntity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

import static com.drops.waterdrop.ui.mine.activity.OrderDetailActivity.EXTRA_ORDER_ENTITY;

/**
 * Created by Mr.Smile on 2017/6/6.
 */

public class ScoreActivity extends BaseActivity<IScoreView, ScorePresenter> implements IScoreView, View.OnClickListener, HZHRatingBar.OnRatingChangeListener {
    @Bind(R.id.iv_goods)
    ImageView mIvGoods;
    @Bind(R.id.tv_goods_name)
    TextView mTvGoodsName;
    @Bind(R.id.btn_comment)
    Button mBtnComment;
    @Bind(R.id.ratingbar)
    HZHRatingBar ratingBar;

    private OrderEntity.ResultsBean.GoodsBean mOrderEntity;
    private int mScore;

    public static void start(Context context, OrderEntity.ResultsBean.GoodsBean entity) {
        Intent starter = new Intent(context, ScoreActivity.class);
        starter.putExtra(EXTRA_ORDER_ENTITY, entity);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle();
        mPresenter.parseIntent(getIntent());
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleId = R.string.comment;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mBtnComment.setOnClickListener(this);
        ratingBar.setOnRatingChangeListener(this);
    }

    @Override
    protected ScorePresenter createPresenter() {
        return new ScorePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_score;
    }


    @Override
    public void getOnParseIntent(OrderEntity.ResultsBean.GoodsBean orderEntity) {
        mOrderEntity = orderEntity;
        GlideUtil.showImageView(this, R.drawable.img_qs_90x90, orderEntity.getGoodCovery(), mIvGoods);
        mTvGoodsName.setText(orderEntity.getGoodName());
    }

    @Override
    public void onCommitScoreSuccess() {
        UserInfoEvent userInfoEvent = new UserInfoEvent();
        userInfoEvent.notify = true;
        EventBus.getDefault().post(userInfoEvent);

        finish();
    }

    @Override
    public void onClick(View v) {
        mPresenter.commitRating(mScore, mOrderEntity.getOrderId(), mOrderEntity.getGoodId());
    }

    @Override
    public void onRatingChange(float RatingCount) {
        mScore = (int) RatingCount;
    }

}
