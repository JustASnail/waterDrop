package com.drops.waterdrop.ui.find.activity;

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
import com.drops.waterdrop.ui.find.adapter.FriendCreatePostListAdapter;
import com.drops.waterdrop.ui.find.presenter.FriendCreatePostListPresenter;
import com.drops.waterdrop.ui.find.view.IFriendCreatePostListView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.custom.CustomAnimation;
import com.netease.nim.uikit.model.MyPostEntity;

import butterknife.Bind;

import static com.netease.nim.uikit.Constants.DEFALUT_FRIEND_UID;
import static com.netease.nim.uikit.Constants.FRIEND_UID;

/**
 * Created by dengxiaolei on 2017/7/10.
 */

public class FriendCreatePostListActivity extends BaseActivity<IFriendCreatePostListView, FriendCreatePostListPresenter> implements IFriendCreatePostListView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private FriendCreatePostListAdapter mAdapter;
    private View noneDataView;
    private long friendUid;


    public static void start(Context context, Long fiendUid) {
        Intent starter = new Intent(context, FriendCreatePostListActivity.class);
        starter.putExtra(FRIEND_UID, fiendUid);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        getExtraData();
        initTitle();
        initList();
    }

    private void getExtraData() {
        friendUid = getIntent().getLongExtra(FRIEND_UID, DEFALUT_FRIEND_UID);
    }

    private void initList() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initEmptyPage();
        initAdapter();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "写过的水帖";

        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        mPresenter.getFriendCollectPosts(false, false, friendUid);

    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        if (mAdapter != null) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);
            mPresenter.setLoadMoreAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        MyPostEntity.ResultsBean item = mAdapter.getItem(position);
                        if (item != null) {
                            CommonWebActivity.start(FriendCreatePostListActivity.this, item.getTipId(), item.getTipUrl());
                        }

                    }
                }
            });
        }
    }

    private void initAdapter() {
        mAdapter = new FriendCreatePostListAdapter(0);
        mAdapter.setEmptyView(noneDataView);
        mAdapter.openLoadAnimation(new CustomAnimation());
        mRecyclerView.setAdapter(mAdapter);
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
    protected FriendCreatePostListPresenter createPresenter() {
        return new FriendCreatePostListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_friend_create_post_list;
    }


    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getFriendCollectPosts(true, false, friendUid);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        mPresenter.getFriendCollectPosts(false, true, friendUid);
    }

    @Override
    public void setRefreshing(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefreshLayout.setEnabled(b);
    }
}
