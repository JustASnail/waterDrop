package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.adapter.JinDouListAdapter;
import com.drops.waterdrop.ui.mine.event.UserInfoEvent;
import com.drops.waterdrop.ui.mine.presenter.GoldenBeanPresenter;
import com.drops.waterdrop.ui.mine.view.IGoldenBeanView;
import com.drops.waterdrop.util.ShadowUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.JinDouEntity;

import org.greenrobot.eventbus.EventBus;


import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/6/26.
 */

public class GoldenBeanActivity extends BaseActivity<IGoldenBeanView, GoldenBeanPresenter> implements View.OnClickListener,IGoldenBeanView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_jdsm)
    TextView mTvJdsm;
    @Bind(R.id.tv_count_jindou)
    TextView mTvCount;
    @Bind(R.id.recycler_list_jindou)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.tv_mingxi)
    TextView tvMingxi;
    private JinDouListAdapter mAdapter;
    private View notDataView;

    public static void start(Context context) {
        Intent starter = new Intent(context, GoldenBeanActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void initView() {
        initEmptyPager();
    }

    @Override
    protected void initData() {
        if (mAdapter == null) {
            mAdapter = new JinDouListAdapter(0);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter.setEmptyView(notDataView);
            mRecyclerView.setAdapter(mAdapter);
            mPresenter.setLoadMoreAdapter(mAdapter);
        }

        mPresenter.getJinDouList(false, false);
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mIvBack.setOnClickListener(this);
        mTvJdsm.setOnClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        if (mAdapter != null) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);

            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        JinDouEntity.ResultsBean bean = mAdapter.getItem(position);
                        GoldenBeanDetailActivity.start(GoldenBeanActivity.this, bean);
                    }
                }
            });
        }
    }

    @Override
    protected GoldenBeanPresenter createPresenter() {
        return new GoldenBeanPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_goldenbean;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_jdsm:
                GoldenBeanDescription.startActivity(this, GoldenBeanDescription.TITLE_GOLDEN_DESC, Constants.GoldenBeanDescriptionUrl);
                break;
        }
    }

    @Override
    public void onGetJinDouSuccess(String totalBeans) {
        mTvCount.setText(totalBeans);
        //发消息
        UserInfoEvent userInfoEvent = new UserInfoEvent();
        userInfoEvent.type = Constants.TYPE_BEANS;
        userInfoEvent.beans = totalBeans;
        EventBus.getDefault().post(userInfoEvent);
    }

    @Override
    public void setRefreshing(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
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
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getJinDouList(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        mPresenter.getJinDouList(false, true);
    }
}
