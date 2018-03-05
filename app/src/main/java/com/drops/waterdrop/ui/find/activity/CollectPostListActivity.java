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
import com.drops.waterdrop.ui.find.adapter.CollectPostListAdapter;
import com.drops.waterdrop.ui.find.presenter.CollectPostListPresenter;
import com.drops.waterdrop.ui.find.view.ICollectPostListView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.custom.CustomAnimation;
import com.netease.nim.uikit.model.PostForFriendCollectEntity;

import butterknife.Bind;

import static com.netease.nim.uikit.Constants.FRIEND_UID;

/**
 * Created by dengxiaolei on 2017/7/10.
 */

public class CollectPostListActivity extends BaseActivity<ICollectPostListView, CollectPostListPresenter> implements ICollectPostListView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private long friendUid;
    private CollectPostListAdapter mAdapter;
    private View noneDataView;


    public static void start(Context context, Long frienduid) {
        Intent starter = new Intent(context, CollectPostListActivity.class);
        starter.putExtra(FRIEND_UID, frienduid);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        parseIntent(getIntent());

        initTitle();
        initList();
    }

    private void parseIntent(Intent intent) {
        friendUid = intent.getLongExtra(Constants.FRIEND_UID, Constants.DEFALUT_FRIEND_UID);

    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "收藏的水帖";
        options.isNeedNavigate = true;
        setMyToolbar(options);
    }

    private void initList() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initEmptyPage();
        initAdapter();
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

    private void initAdapter() {
        mAdapter = new CollectPostListAdapter(0);
        mAdapter.setEmptyView(noneDataView);
        mAdapter.openLoadAnimation(new CustomAnimation());
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.setLoadMoreAdapter(mAdapter);
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

            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        PostForFriendCollectEntity.ResultsBean item = mAdapter.getItem(position);
                        if (item != null) {
                            CommonWebActivity.start(CollectPostListActivity.this, item.getTipId(), item.getTipUrl());
                        }
                    }
                }
            });
        }
    }

    @Override
    protected CollectPostListPresenter createPresenter() {
        return new CollectPostListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_collect_post_list;
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
