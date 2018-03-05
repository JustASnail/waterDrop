package com.drops.waterdrop.ui.session.activity;

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
import com.drops.waterdrop.ui.find.activity.PoolDetailPageActivity;
import com.drops.waterdrop.ui.find.adapter.MyPoolListAdapter;
import com.drops.waterdrop.ui.session.presenter.MyPoolListPresenter;
import com.drops.waterdrop.ui.session.view.IMyPoolListView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.custom.CustomAnimation;
import com.netease.nim.uikit.model.PoolListEntity;

import butterknife.Bind;

import static com.netease.nim.uikit.Constants.DEFALUT_FRIEND_UID;
import static com.netease.nim.uikit.Constants.FRIEND_UID;
import static com.netease.nim.uikit.Constants.TITLE_CREATE;
import static com.netease.nim.uikit.Constants.TITLE_FOCUS;
import static com.netease.nim.uikit.Constants.TITLE_MINE;
import static com.netease.nim.uikit.Constants.TITLE_TYPE;


/**
 * 关注的水塘
 * Created by dengxiaolei on 2017/6/9.
 */

public class MyPoolListActivity extends BaseActivity<IMyPoolListView, MyPoolListPresenter> implements IMyPoolListView, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private View notDataView;
    private MyPoolListAdapter mAdapter;
    private long friendUid;
    private int titleType;
    private boolean isFriendsPool;

    public static void start(Context context, Long fiendUid,int titleType) {
        Intent starter = new Intent(context, MyPoolListActivity.class);
        starter.putExtra(FRIEND_UID, fiendUid);
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
        friendUid = getIntent().getLongExtra(FRIEND_UID, DEFALUT_FRIEND_UID);
        titleType = getIntent().getIntExtra(TITLE_TYPE, TITLE_MINE);
        isFriendsPool = friendUid > 0;
    }

    private void initList() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initEmptyPager();
        initAdapter();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        switch (titleType) {
            case TITLE_MINE:
                options.titleString = "我的水塘";
                break;
            case TITLE_CREATE:
                options.titleString = "创建的水塘";
                break;
            case TITLE_FOCUS:
                options.titleString = "关注的水塘";
                break;
        }

        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        switch (titleType) {
            case TITLE_MINE:
                mPresenter.getPoolListDatas(false, false);
                break;
            case TITLE_CREATE:
                mPresenter.getFriendCreatePool(false, false, friendUid);
                break;
            case TITLE_FOCUS:
                mPresenter.getFriendFocusPool(false, false, friendUid);
                break;
        }
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
                        PoolDetailPageActivity.start(MyPoolListActivity.this, mAdapter.getItem(position).getDropId());
                    }
                }
            });
        }
    }

    @Override
    protected MyPoolListPresenter createPresenter() {
        return new MyPoolListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_pool_list;
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PoolListEntity.ResultsBean resultsBean = mAdapter.getData().get(position);
        PoolDetailPageActivity.start(this, resultsBean.getDropId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);

        switch (titleType) {
            case TITLE_MINE:
                mPresenter.getPoolListDatas(true,false);
                break;
            case TITLE_CREATE:
                mPresenter.getFriendCreatePool(true, false, friendUid);
                break;
            case TITLE_FOCUS:
                mPresenter.getFriendFocusPool(true, false, friendUid);
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        switch (titleType) {
            case TITLE_MINE:
                mPresenter.getPoolListDatas(false, true);
                break;
            case TITLE_CREATE:
                mPresenter.getFriendCreatePool(false, true, friendUid);
                break;
            case TITLE_FOCUS:
                mPresenter.getFriendFocusPool(false, true, friendUid);
                break;
        }
    }

    @Override
    public void setEnableLoadMore(boolean isEnable) {
        mAdapter.setEnableLoadMore(isEnable);
    }

    @Override
    public void setRefresh(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefreshLayout.setEnabled(b);
    }


    private void initAdapter() {
        mAdapter = new MyPoolListAdapter(0, isFriendsPool);
        mAdapter.setEmptyView(notDataView);
        mAdapter.openLoadAnimation(new CustomAnimation());
        mRecyclerView.setAdapter(mAdapter);

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



}
