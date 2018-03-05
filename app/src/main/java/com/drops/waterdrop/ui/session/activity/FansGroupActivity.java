package com.drops.waterdrop.ui.session.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.session.adapter.FansGroupListAdapter;
import com.drops.waterdrop.ui.session.presenter.FansGroupPresenter;
import com.drops.waterdrop.ui.session.view.IFansGroupView;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nimlib.sdk.team.model.Team;

import java.util.List;

import butterknife.Bind;

/**
 * 铁粉群
 * Created by dengxiaolei on 2017/7/11.
 */

public class FansGroupActivity extends BaseActivity<IFansGroupView, FansGroupPresenter> implements IFansGroupView{

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private FansGroupListAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, FansGroupActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "铁粉群";
        setMyToolbar(options);

        mAdapter = new FansGroupListAdapter(0);
        View emptyView = View.inflate(this, R.layout.empty_view, null);
        mAdapter.setEmptyView(emptyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);


        registerTeamUpdateObserver(true);
    }

    @Override
    protected void initData() {
      mPresenter.getGroup();
    }

    @Override
    protected void initListener() {
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    NimUIKit.startTeamSession(FansGroupActivity.this, mAdapter.getData().get(position).getId());
                }
            });
        }
    }

    @Override
    protected FansGroupPresenter createPresenter() {
        return new FansGroupPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fans_group;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerTeamUpdateObserver(false);
    }

    @Override
    public void onLoadGroupSucceed(List<Team> teams) {
        if (mAdapter != null) {
            mAdapter.setNewData(teams);
        }
    }

    private void registerTeamUpdateObserver(boolean register) {
        if (register) {
            TeamDataCache.getInstance().registerTeamDataChangedObserver(teamDataChangedObserver);
        } else {
            TeamDataCache.getInstance().unregisterTeamDataChangedObserver(teamDataChangedObserver);
        }
    }

    TeamDataCache.TeamDataChangedObserver teamDataChangedObserver = new TeamDataCache.TeamDataChangedObserver() {
        @Override
        public void onUpdateTeams(List<Team> teams) {
            mPresenter.getGroup();

        }

        @Override
        public void onRemoveTeam(Team team) {
            mPresenter.getGroup();

        }
    };

}
