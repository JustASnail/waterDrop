package com.drops.waterdrop.ui.session.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.activity.UserProfileActivity;
import com.drops.waterdrop.ui.session.adapter.SystemMessageAdapter;
import com.drops.waterdrop.ui.session.presenter.SystemMessagePresenter;
import com.drops.waterdrop.ui.session.view.ISystemMessageView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.event.FriendAuthenticationEvent;
import com.netease.nim.uikit.gen.SystemMessageDBDao;
import com.netease.nim.uikit.green_dao.GreenDaoManager;
import com.netease.nim.uikit.green_dao.SystemMessageDB;
import com.netease.nimlib.sdk.msg.model.SystemMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;

public class SystemMessageActivity extends BaseActivity<ISystemMessageView, SystemMessagePresenter> implements ISystemMessageView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final boolean MERGE_ADD_FRIEND_VERIFY = true; // 是否要合并好友申请，同一个用户仅保留最近一条申请内容（默认不合并）
    private Set<String> addFriendVerifyRequestAccounts = new HashSet<>(); // 发送过好友申请的账号（好友申请合并用）

    private static final int LOAD_MESSAGE_COUNT = 10;//加载多少条系统消息
    private int loadOffset = 0;

    private Set<Long> itemIds = new HashSet<>();
    private List<SystemMessage> items = new ArrayList<>();

    private View noneDataView;

    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.activity_system_message)
    LinearLayout mActivitySystemMessage;
    private SystemMessageAdapter mAdapter;
    private boolean mIsShow;

    public static void start(Context context, boolean isShow) {
        Intent starter = new Intent(context, SystemMessageActivity.class);
        starter.putExtra(Constants.EXTRA_SHOW, isShow);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        mIsShow = getIntent().getBooleanExtra(Constants.EXTRA_SHOW, true);

        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "验证申请";
        setMyToolbar(options);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SystemMessageAdapter(0, mIsShow);
        View emptyView = View.inflate(this, R.layout.empty_view, null);
        mAdapter.setEmptyView(emptyView);
        mPresenter.setLoadMoreAdapter(mAdapter);
        initEmptyPage();
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
    protected void initData() {
        mPresenter.getNewsFriends(false, false);

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
                        UserProfileActivity.start(SystemMessageActivity.this, mAdapter.getItem(position).getFromUser().getUid());
                    }
                }
            });
        }

        SystemMessageDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSystemMessageDBDao();
        List<SystemMessageDB> friendList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(11), SystemMessageDBDao.Properties.UnreadTag.eq(0)).list();//好友验证
        List<SystemMessageDB> groupList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(12), SystemMessageDBDao.Properties.UnreadTag.eq(0)).list();//群申请验证                        SystemMessageActivity.start(context);

        if (friendList != null) {
            for (SystemMessageDB db : friendList) {
                db.setUnreadTag(1);
                dbDao.update(db);
            }
        }
        if (groupList != null) {
            for (SystemMessageDB db : groupList) {
                db.setUnreadTag(1);
                dbDao.update(db);
            }
        }
    }

    @Override
    protected SystemMessagePresenter createPresenter() {
        return new SystemMessagePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_system_message;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }



    @Override
    public void setRefreshing(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefreshLayout.setEnabled(b);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //当收到添加好友系统通知时 拉取本地消息更新列表
    public void onFriendAuthenticationEvent(FriendAuthenticationEvent event) {
        mPresenter.getNewsFriends(false, false);

    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.getNewsFriends(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        mPresenter.getNewsFriends(false, true);
    }
}
