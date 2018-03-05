package com.netease.nim.uikit.session.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.ShareCardModel;
import com.netease.nim.uikit.session.adapter.FansGroupSelectListAdapter;
import com.netease.nim.uikit.session.helper.CardShareHelper;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;

import java.util.List;


/**
 * Created by dengxiaolei on 2017/7/11.
 */

public class FansGroupSelectActivity extends UI{

    private RecyclerView mRecyclerView;
    private FansGroupSelectListAdapter mAdapter;
    private ShareCardModel mModel;

    public static void startForResult(Context context, int requestCode, ShareCardModel model) {
        Intent starter = new Intent(context, FansGroupSelectActivity.class);
        starter.putExtra(Constants.EXTRA_ENTITY, model);
        ((AppCompatActivity)context).startActivityForResult(starter, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fans_group_select);
        initView();
        initTitle();
        registerTeamUpdateObserver(true);

        parseIntent();

        getGroup();
    }

    private void parseIntent() {
        mModel = (ShareCardModel) getIntent().getSerializableExtra(Constants.EXTRA_ENTITY);
    }

    private void initView() {


        mRecyclerView = findView(R.id.recycler_view);
        mAdapter = new FansGroupSelectListAdapter(0);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        if (mModel != null) {
                            Team team = mAdapter.getData().get(position);
                            mModel.setReceiveAccount(team == null ? "" : team.getId());
                            mModel.setReceiveUserName(team == null ? "" : team.getName());
                            mModel.setReceiveUserPhoto(team == null ? "" : team.getIcon());
                            mModel.setSessionTypeEnum(SessionTypeEnum.Team);
                            CardShareHelper.showMyDialog(FansGroupSelectActivity.this, mModel, new CardShareHelper.OnShareSucceedListener() {
                                @Override
                                public void onShareSucceed() {
                                    Toast.makeText(FansGroupSelectActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            });

                        }
                    }
                }
            });
        }
    }

    private void initTitle() {
        ImageView ivLeft = findView(R.id.iv_left);
        TextView tvTitle = findView(R.id.tv_commn_title);
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("选择铁粉群");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerTeamUpdateObserver(false);
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
            getGroup();

        }

        @Override
        public void onRemoveTeam(Team team) {
            getGroup();

        }
    };

    private void getGroup() {
        List<Team> teamList = TeamDataCache.getInstance().getAllAdvancedTeams();
        if (mAdapter != null) {
            mAdapter.setNewData(teamList);
        }

    }

}
