package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.drops.waterdrop.ui.mine.adapter.MyPostListAdapter;
import com.drops.waterdrop.ui.mine.presenter.MyPostListPresenter;
import com.drops.waterdrop.ui.mine.view.IMyPostListView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.MyPostEntity;

import butterknife.Bind;

import static com.netease.nim.uikit.Constants.FRIEND_UID;


/**
 * Created by Mr.Smile on 2017/7/3.
 */

public class MyPostListActivity extends BaseActivity<IMyPostListView,MyPostListPresenter> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,IMyPostListView {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private MyPostListAdapter mAdapter;
    private View noneDataView;
    private boolean hasFriendUid;
    private long friendUid;
    private int titleType;

    public static void start(Context context, Long frienduid, int titleType) {
        Intent starter = new Intent(context, MyPostListActivity.class);
        starter.putExtra(FRIEND_UID, frienduid);
        starter.putExtra(Constants.TITLE_TYPE, titleType);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        getExtraData();
        initTitle();
        initList();
    }

    private void getExtraData() {
        friendUid = getIntent().getLongExtra(Constants.FRIEND_UID, Constants.DEFALUT_FRIEND_UID);
        hasFriendUid = friendUid > 0;
        titleType = getIntent().getIntExtra(Constants.TITLE_TYPE, Constants.TITLE_MINE);
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "我的水帖";
        options.isNeedNavigate = true;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        mPresenter.getPostList(false, false);
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        if (mAdapter != null) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);

            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        MyPostEntity.ResultsBean resultsBean = mAdapter.getData().get(position);
                        CommonWebActivity.start(MyPostListActivity.this, resultsBean.getTipId(), resultsBean.getTipUrl());
                    }
                }
            });
        }
    }

    @Override
    protected MyPostListPresenter createPresenter() {
        return new MyPostListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_post_list;
    }

    private void initList() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initEmptyPage();
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new MyPostListAdapter(0);
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
        mPresenter.getPostList(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        mPresenter.getPostList(false, true);
    }

    @Override
    public void setRefresh(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefreshLayout.setEnabled(b);
    }
}
