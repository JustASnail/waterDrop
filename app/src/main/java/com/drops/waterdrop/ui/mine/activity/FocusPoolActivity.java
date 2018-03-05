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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.activity.PoolDetailPageActivity;
import com.drops.waterdrop.ui.mine.adapter.FocusPoolAdapter;
import com.drops.waterdrop.ui.mine.presenter.FocusPoolPresenter;
import com.drops.waterdrop.ui.mine.view.IFocusPoolView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.PoolListEntity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/7/4.
 */

public class  FocusPoolActivity extends BaseActivity<IFocusPoolView, FocusPoolPresenter> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, IFocusPoolView {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.tv_private)
    TextView tvPrivate;
    private FocusPoolAdapter mAdapter;
    private View noneDataView;
    private boolean isPrivate;

    public static void start(Context context) {
        Intent starter = new Intent(context, FocusPoolActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle();
        initList();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "我的关注";
        options.isNeedNavigate = true;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        mPresenter.getFocusPoolList(false, false);
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        if (mAdapter != null) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);

            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        PoolListEntity.ResultsBean resultsBean = mAdapter.getData().get(position);
                        PoolDetailPageActivity.start(FocusPoolActivity.this, resultsBean.getDropId());
                    }
                }
            });

            mAdapter.setListener(new FocusPoolAdapter.CancelFocusPoolListener() {
                @Override
                public void onCancelFocusPool(int pos) {
                    mAdapter.remove(pos);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }

        tvPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = tvPrivate.getText();
                if (TextUtils.equals(text, getString(R.string.set_private))) {
                    tvPrivate.setText(R.string.set_finish);
                    isPrivate = true;
                } else if (TextUtils.equals(text, getString(R.string.set_finish))) {
                    tvPrivate.setText(R.string.set_private);
                    isPrivate = false;
                }
                mAdapter.setPrivateMode(isPrivate);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected FocusPoolPresenter createPresenter() {
        return new FocusPoolPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_focus_pool;
    }

    private void initList() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initEmptyPage();
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new FocusPoolAdapter(0);
        mAdapter.setEmptyView(noneDataView);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.setLoadMoreAdapter(mAdapter);
    }


    private void initEmptyPage() {
        noneDataView = LayoutInflater.from(this).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        noneDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getFocusPoolList(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        mPresenter.getFocusPoolList(false, true);
    }

    @Override
    public void onGetDataSuccess(List<PoolListEntity.ResultsBean> resultsBeen) {
        mAdapter.setNewData(resultsBeen);
    }

    @Override
    public void setEnableLoadMore(boolean b) {
        mAdapter.setEnableLoadMore(b);
    }

    @Override
    public void setRefresh(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

}
