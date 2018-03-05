package com.netease.nim.uikit.team.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.cache.SimpleCallback;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.adapter.TAdapterDelegate;
import com.netease.nim.uikit.common.adapter.TViewHolder;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.FansGroupEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.netease.nim.uikit.session.activity.ContactMultSelectActivity;
import com.netease.nim.uikit.team.adapter.MyTeamMemberAdapter;
import com.netease.nim.uikit.team.adapter.TeamMemberAdapter;
import com.netease.nim.uikit.team.helper.TeamHelper;
import com.netease.nim.uikit.team.viewholder.TeamMemberHolder;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.team.constant.TeamMemberType;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * 群成员列表界面
 * Created by hzxuwen on 2015/3/17.
 */
public class AdvancedTeamMemberActivity extends UI implements TAdapterDelegate,
        TeamMemberAdapter.RemoveMemberCallback, TeamMemberAdapter.AddMemberCallback, TeamMemberHolder.TeamMemberHolderEventListener {

    // constant
    private static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_DATA = "EXTRA_DATA";
    private static final String EXTRA_IS_MANNGER = "EXTRA_IS_MANNGER";

    // data source
    private String teamId;
    private List<TeamMember> members;
    //    private TeamMemberAdapter adapter;
    private ArrayList<String> memberAccounts;
    private List<TeamMemberAdapter.TeamMemberItem> dataSource;
    private String creator;
    private List<String> managerList;

    // state
    private boolean isSelfAdmin = false;
    private boolean isSelfManager = false;
    private boolean isMemberChange = false;
    private UserInfoObservable.UserInfoObserver userInfoObserver;
    private MyTeamMemberAdapter mAdapter;
    private TextView mTvMemberNum;
    private boolean mIsMannger;
    private long mDropId;

    public static void startActivityForResult(Activity context, String tid, int resCode, boolean isMannger, long dropId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ID, tid);
        intent.putExtra(EXTRA_IS_MANNGER, isMannger);
        intent.putExtra(Constants.EXTRA_DROP_ID, dropId);
        intent.setClass(context, AdvancedTeamMemberActivity.class);
        context.startActivityForResult(intent, resCode);
    }

    /**
     * *********************************lifeCycle*******************************************
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nim_team_member_grid_layout);
        parseIntentData();


        ImageView ivLeft = (ImageView) findViewById(R.id.iv_left);
        TextView tvRight = (TextView) findViewById(R.id.tv_right);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMemberChange) {
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                }
                finish();
            }
        });

        if (mIsMannger) {
            tvRight.setText("邀请好友");
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        ContactMultSelectActivity.startForResult(AdvancedTeamMemberActivity.this, teamId, memberAccounts, mDropId);
                    }
                }
            });
        }


        ivLeft.setVisibility(View.VISIBLE);
        TextView tvTitle = (TextView) findViewById(R.id.tv_commn_title);
        tvTitle.setText("群成员");


        loadTeamInfo();
        initAdapter();
        findViews();
//        registerUserInfoChangedObserver(true);
        requestData();
    }

    @Override
    protected void onDestroy() {
//        registerUserInfoChangedObserver(false);

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (isMemberChange) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
        }
        super.onBackPressed();

    }

    private void parseIntentData() {
        teamId = getIntent().getStringExtra(EXTRA_ID);
        mIsMannger = getIntent().getBooleanExtra(EXTRA_IS_MANNGER, false);
        mDropId = getIntent().getLongExtra(Constants.EXTRA_DROP_ID, 0);
    }

    private void loadTeamInfo() {
        Team team = TeamDataCache.getInstance().getTeamById(teamId);
        if (team != null) {
            creator = team.getCreator();
        }
    }

    private void findViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyTeamMemberAdapter(0);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String account = mAdapter.getItem(position).getAccount();
                NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(account);
                try {
                    String yuanshi_ = account.replace("yuanshi_", "");
                    long uid = Long.parseLong(yuanshi_);
                    NimUIKit.getTeamMemberClickListener().onTeamMemberClickListener(AdvancedTeamMemberActivity.this, uid);
                } catch (Exception e) {
                }

            }
        });

        if (mIsMannger) {
            mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                    String account = mAdapter.getItem(position).getAccount();
                    if (!TextUtils.equals(account, MyUserCache.getIMAccount())) {
                        showConfirmButton(account, position);
                    }
                    return true;
                }
            });

        }

        mTvMemberNum = (TextView) findViewById(R.id.tv_team_member_num);
    }

    private AlertDialog.Builder mBuilder;

    /**
     * 移除群成员确认
     */
    private void showConfirmButton(final String account, final int position) {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(this);
            mBuilder.setTitle("温馨提示");
            mBuilder.setMessage(getString(R.string.team_member_remove_confirm));
            mBuilder.setNegativeButton(getString(R.string.cancel), null);
        }

        mBuilder.setPositiveButton(getString(R.string.remove), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeMember(account, position);
            }
        });
        mBuilder.show();

    }

    /**
     * 移除群成员
     */
    private void removeMember(final String account, final int position) {
        if (mDropId == 0) {
            getDropId();
        }

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, mDropId);


        try {
            String yuanshi_ = account.replace("yuanshi_", "");
            long uid = Long.parseLong(yuanshi_);
            map.put(RequestParams.kick_uid, uid);

        } catch (Exception e) {

        }

        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.kickOut(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(this) {
            @Override
            protected void _onNext(Object o) {
                if (mAdapter != null) {

                    mAdapter.getData().remove(position);
                    mTvMemberNum.setText(mAdapter.getData().size() + "");
                    memberAccounts.remove(account);
                    mAdapter.notifyDataSetChanged();
                }
                Toast.makeText(AdvancedTeamMemberActivity.this, "移除成功", Toast.LENGTH_SHORT).show();
                isMemberChange = true;
            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(AdvancedTeamMemberActivity.this, "移除失败， 请重试！", Toast.LENGTH_LONG).show();

            }
        });

      /*  DialogMaker.showProgressDialog(this, getString(R.string.empty));
        NIMClient.getService(TeamService.class).removeMember(teamId, account).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                if (mAdapter != null) {

                    mAdapter.getData().remove(position);
                    mTvMemberNum.setText(mAdapter.getData().size() + "");
                    memberAccounts.remove(account);

                    mAdapter.notifyDataSetChanged();
                }
                DialogMaker.dismissProgressDialog();
                Toast.makeText(AdvancedTeamMemberActivity.this, "移除成功", Toast.LENGTH_SHORT).show();
                isMemberChange = true;
            }

            @Override
            public void onFailed(int code) {
                DialogMaker.dismissProgressDialog();
                Toast.makeText(AdvancedTeamMemberActivity.this, "移除失败， 请重试！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onException(Throwable exception) {
                DialogMaker.dismissProgressDialog();
            }
        });*/
    }

    private void getDropId() {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.room_id, teamId);

        Observable<BaseResponse<FansGroupEntity>> observable = HttpUtil.getInstance().sApi.getRoomInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<FansGroupEntity>(this, true) {
            @Override
            protected void _onNext(FansGroupEntity fansGroupEntity) {
                if (fansGroupEntity != null && fansGroupEntity.getDropId() > 0) {
                    mDropId = fansGroupEntity.getDropId();
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }


    private void initAdapter() {
        memberAccounts = new ArrayList<>();
        members = new ArrayList<>();
        dataSource = new ArrayList<>();
        managerList = new ArrayList<>();
/*        adapter = new TeamMemberAdapter(this, dataSource, this, this, this);
        adapter.setEventListener(this);*/
    }

    private void updateTeamMember(final List<TeamMember> members) {
        if (members != null && members.isEmpty()) {
            return;
        }

        addTeamMembers(members, true);
    }

    private void addTeamMembers(final List<TeamMember> m, boolean clear) {
        if (m == null || m.isEmpty()) {
            return;
        }

        if (clear) {
            this.members.clear();
            this.memberAccounts.clear();
        }

        // add
        if (this.members.isEmpty()) {
            this.members.addAll(m);
        } else {
            for (TeamMember tm : m) {
                if (!this.memberAccounts.contains(tm.getAccount())) {
                    this.members.add(tm);
                }
            }
        }

        // sort
        Collections.sort(this.members, TeamHelper.teamMemberComparator);

        // accounts, manager, creator
        this.memberAccounts.clear();
        this.managerList.clear();
        for (TeamMember tm : members) {
            initManagerList(tm);
            if (tm.getAccount().equals(NimUIKit.getAccount())) {
                if (tm.getType() == TeamMemberType.Manager) {
                    isSelfManager = true;
                } else if (tm.getType() == TeamMemberType.Owner) {
                    isSelfAdmin = true;
                    creator = NimUIKit.getAccount();
                }
            }
            this.memberAccounts.add(tm.getAccount());
        }

        mAdapter.setNewData(members);
        mTvMemberNum.setText(members.size() + "");

//        updateTeamMemberDataSource();
    }

    /**
     * 初始化管理员列表
     *
     * @param tm 群成员
     */
    private void initManagerList(TeamMember tm) {
        if (tm.getType() == TeamMemberType.Manager) {
            managerList.add(tm.getAccount());
        }
    }

/*
    private void updateTeamMemberDataSource() {
        if (members.size() <= 0) {
            return;
        }

        dataSource.clear();

        // member item
        for (String account : memberAccounts) {
            dataSource.add(new TeamMemberItem(TeamMemberAdapter.TeamMemberItemTag
                    .NORMAL, teamId, account, initMemberIdentity(account)));
        }

        // refresh
//        adapter.notifyDataSetChanged();
    }
*/

    /**
     * 初始化成员身份
     *
     * @param account 帐号
     * @return String
     */
    private String initMemberIdentity(String account) {
        String identity;
        if (creator.equals(account)) {
            identity = TeamMemberHolder.OWNER;
        } else if (managerList.contains(account)) {
            identity = TeamMemberHolder.ADMIN;
        } else {
            identity = null;
        }
        return identity;
    }


    @Override
    public void onAddMember() {

    }

    @Override
    public void onRemoveMember(String account) {

    }

    /**
     * ******************************加载数据*******************************
     */
    private void requestData() {
        TeamDataCache.getInstance().fetchTeamMemberList(teamId, new SimpleCallback<List<TeamMember>>() {
            @Override
            public void onResult(boolean success, List<TeamMember> members) {
                if (success && members != null && !members.isEmpty()) {
                    updateTeamMember(members);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ContactMultSelectActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            //刷新
            requestData();
        }
    }

    /**
     * ************************ TAdapterDelegate **************************
     */

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public Class<? extends TViewHolder> viewHolderAtPosition(int position) {
        return TeamMemberHolder.class;
    }

    @Override
    public boolean enabled(int position) {
        return false;
    }

    @Override
    public void onHeadImageViewClick(String account) {
        AdvancedTeamMemberInfoActivity.startActivityForResult(AdvancedTeamMemberActivity.this, account, teamId);
    }


    /**
     * 是否设置了管理员刷新界面
     *
     * @param isSetAdmin 是否设置为管理员
     * @param account    帐号
     */
/*
    private void refreshAdmin(boolean isSetAdmin, String account) {
        if (isSetAdmin) {
            if (managerList.contains(account)) {
                return;
            }
            managerList.add(account);
            isMemberChange = true;
            updateTeamMemberDataSource();
        } else {
            if (managerList.contains(account)) {
                managerList.remove(account);
                isMemberChange = true;
                updateTeamMemberDataSource();
            }
        }
    }
*/

/*
    private void registerUserInfoChangedObserver(boolean register) {
        if (register) {
            if (userInfoObserver == null) {
                userInfoObserver = new UserInfoObservable.UserInfoObserver() {
                    @Override
                    public void onUserInfoChanged(List<String> accounts) {
                        adapter.notifyDataSetChanged();
                    }
                };
            }
            UserInfoHelper.registerObserver(userInfoObserver);
        } else {
            UserInfoHelper.unregisterObserver(userInfoObserver);
        }
    }
*/
}
