package com.netease.nim.uikit.team.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.SimpleCallback;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.adapter.TAdapterDelegate;
import com.netease.nim.uikit.common.adapter.TViewHolder;
import com.netease.nim.uikit.common.media.picker.PickImageHelper;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nim.uikit.contact_selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.FansGroupEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.netease.nim.uikit.session.actions.PickImageAction;
import com.netease.nim.uikit.team.adapter.TeamMemberAdapter;
import com.netease.nim.uikit.team.adapter.TeamMemberAdapter.TeamMemberItem;
import com.netease.nim.uikit.team.helper.TeamHelper;
import com.netease.nim.uikit.team.ui.TeamInfoGridView;
import com.netease.nim.uikit.team.viewholder.TeamMemberHolder;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.nos.NosService;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;
import com.netease.nimlib.sdk.team.constant.TeamInviteModeEnum;
import com.netease.nimlib.sdk.team.constant.TeamMemberType;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * 高级群群资料页
 * Created by huangjun on 2015/3/17.
 */
public class AdvancedTeamInfoActivity extends UI implements
        TAdapterDelegate, TeamMemberAdapter.AddMemberCallback, TeamMemberHolder.TeamMemberHolderEventListener {

    private static final int REQUEST_CODE_TRANSFER = 101;
    private static final int REQUEST_CODE_MEMBER_LIST = 102;
    private static final int REQUEST_CODE_CONTACT_SELECT = 103;
    private static final int REQUEST_PICK_ICON = 104;

    private static final int ICON_TIME_OUT = 30000;

    // constant
    private static final String TAG = "RegularTeamInfoActivity";

    private static final String EXTRA_ID = "EXTRA_ID";
    public static final String RESULT_EXTRA_REASON = "RESULT_EXTRA_REASON";
    public static final String RESULT_EXTRA_REASON_QUIT = "RESULT_EXTRA_REASON_QUIT";
    public static final String RESULT_EXTRA_REASON_DISMISS = "RESULT_EXTRA_REASON_DISMISS";

    private static final int TEAM_MEMBERS_SHOW_LIMIT = 5;

    private AlertDialog.Builder mBuilder;


    // data
    private TeamMemberAdapter adapter;
    private String teamId;
    private Team team;
    private String creator;
    private List<String> memberAccounts;
    private List<TeamMember> members;
    private List<TeamMemberAdapter.TeamMemberItem> dataSource;

    private List<String> managerList;
    private UserInfoObservable.UserInfoObserver userInfoObserver;
    private AbortableFuture<String> uploadFuture;

    // view
    private View headerLayout;
    private HeadImageView teamHeadImage;
    private HeadImageView teamHeadImage2;
    private TextView teamNameText;
    private TextView teamIdText;
    private TextView teamCreateTimeText;


    private TeamInfoGridView gridView;
    private View layoutTeamName;
    private View layoutTeamIntroduce;
    private View layoutNotificationConfig;
    // 群资料修改权限
    private View layoutInfoUpdate;
    private TextView infoUpdateText;
    // 被邀请人身份验证权限
    private View layoutInviteeAuthen;
    private TextView inviteeAutenText;

    private TextView memberCountText;
    private TextView introduceEdit;
    private TextView announcementEdit;
    private TextView extensionTextView;
    private TextView authenticationText;
    private TextView notificationConfigText;

    // state
    private boolean isSelfAdmin = false;
    private boolean isSelfManager = false;
    private TextView mTitle;
    private TextView mTeamNum;
    private ImageView iconNextAvatar;
    private RelativeLayout mTeanmMembersContainer;
    private TextView mTvIntroduce;
    private TextView mExitTeam;
    private boolean mIsMannger;
    private long mDropId;

    public static void start(Context context, String tid) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ID, tid);
        intent.setClass(context, AdvancedTeamInfoActivity.class);
        context.startActivity(intent);
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

    /**
     * ***************************** Life cycle *****************************
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nim_advanced_team_info_activity);
        ImageView ivLeft = (ImageView) findViewById(R.id.iv_left);
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setImageResource(R.drawable.icon_back_white);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitle = (TextView) findViewById(R.id.tv_commn_title);


        parseIntentData();
        findViews();
//        initActionbar();
        initAdapter();
        loadTeamInfo();
        getDropId();
        requestMembers();
        registerObservers(true);
    }

    private void getDropId() {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.room_id, teamId);

        Observable<BaseResponse<FansGroupEntity>> observable = HttpUtil.getInstance().sApi.getRoomInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<FansGroupEntity>(this, false) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_CONTACT_SELECT:
                final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                if (selected != null && !selected.isEmpty()) {
                    inviteMembers(selected);
                }
                break;
            case REQUEST_CODE_TRANSFER:
                final ArrayList<String> target = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                if (target != null && !target.isEmpty()) {
                    transferTeam(target.get(0));
                }
                break;
            case AdvancedTeamNicknameActivity.REQ_CODE_TEAM_NAME:
//                setBusinessCard(data.getStringExtra(AdvancedTeamNicknameActivity.EXTRA_NAME));
                break;
            case AdvancedTeamMemberInfoActivity.REQ_CODE_REMOVE_MEMBER:
                boolean isSetAdmin = data.getBooleanExtra(AdvancedTeamMemberInfoActivity.EXTRA_ISADMIN, false);
                boolean isRemoveMember = data.getBooleanExtra(AdvancedTeamMemberInfoActivity.EXTRA_ISREMOVE, false);
                String account = data.getStringExtra(EXTRA_ID);
                refreshAdmin(isSetAdmin, account);
                if (isRemoveMember) {
                    removeMember(account);
                }
                break;
            case REQUEST_CODE_MEMBER_LIST:
                requestMembers();
                break;
            case REQUEST_PICK_ICON:
                String path = data.getStringExtra(com.netease.nim.uikit.session.constant.Extras.EXTRA_FILE_PATH);
                updateTeamIcon(path);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {

        registerObservers(false);

        super.onDestroy();
    }

    private void parseIntentData() {
        teamId = getIntent().getStringExtra(EXTRA_ID);
    }

    private void findViews() {
        headerLayout = findViewById(R.id.team_info_header);
        mExitTeam = (TextView) findViewById(R.id.tv_exit_team);

        teamHeadImage = (HeadImageView) findViewById(R.id.team_head_image);
        teamHeadImage2 = (HeadImageView) findViewById(R.id.iv_group_avatar);
        teamNameText = (TextView) findViewById(R.id.team_name);
        teamIdText = (TextView) findViewById(R.id.team_id);
        teamCreateTimeText = (TextView) findViewById(R.id.team_create_time);

        mTeamNum = (TextView) findViewById(R.id.tv_team_num);
        iconNextAvatar = (ImageView) findViewById(R.id.icon_next_avatar);
        iconNextAvatar.setVisibility(View.INVISIBLE);

/*        layoutMime = findViewById(R.id.team_mime_layout);
        ((TextView) layoutMime.findViewById(R.id.item_title)).setText(R.string.my_team_card);
        teamBusinessCard = (TextView) layoutMime.findViewById(R.id.item_detail);
        layoutMime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvancedTeamNicknameActivity.start(AdvancedTeamInfoActivity.this, teamBusinessCard.getText().toString());
            }
        });*/

        gridView = (TeamInfoGridView) findViewById(R.id.team_member_grid_view);
        gridView.setVisibility(View.GONE);
     /*   memberCountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvancedTeamMemberActivity.startActivityForResult(AdvancedTeamInfoActivity.this, teamId, REQUEST_CODE_MEMBER_LIST);
            }
        });*/

        layoutTeamName = findViewById(R.id.team_name_layout);
        ((TextView) layoutTeamName.findViewById(R.id.item_title)).setText(R.string.team_name);


//        layoutTeamIntroduce = findViewById(R.id.team_introduce_layout);
        mTvIntroduce = (TextView) findViewById(R.id.tv_introduce);
//        ((TextView) layoutTeamIntroduce.findViewById(R.id.item_title)).setText(R.string.team_introduce);
//        introduceEdit = ((TextView) layoutTeamIntroduce.findViewById(R.id.item_detail));
        mTvIntroduce.setHint(R.string.team_introduce_hint);

        mTeanmMembersContainer = (RelativeLayout) findViewById(R.id.ll_team_container);

        findViewById(R.id.rl_team_member_num).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (team != null && team.getMemberCount() > 0) {
                    AdvancedTeamMemberActivity.startActivityForResult(AdvancedTeamInfoActivity.this, teamId, REQUEST_CODE_MEMBER_LIST, mIsMannger, mDropId);

                }
            }
        });
        mTeanmMembersContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (team != null && team.getMemberCount() > 0) {
                    AdvancedTeamMemberActivity.startActivityForResult(AdvancedTeamInfoActivity.this, teamId, REQUEST_CODE_MEMBER_LIST, mIsMannger, mDropId);

                }
            }
        });
        // 群消息提醒设置
        NIMClient.getService(TeamService.class).muteTeam(teamId, false);//false是开启提醒
        mExitTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    showExitTeamDialog();
                }
            }
        });
    }

    public void showExitTeamDialog() {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(this);
            mBuilder.setTitle("温馨提示");
            mBuilder.setMessage("确认退出该群？");
            mBuilder.setNegativeButton("取消", null);
        }

        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quitTeam();
            }
        });
        mBuilder.show();
    }


    /**
     * 打开图片选择器
     */
    private void showSelector(int titleId, final int requestCode) {
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.titleResId = titleId;
        option.multiSelect = false;
        option.crop = true;
        option.cropOutputImageWidth = 720;
        option.cropOutputImageHeight = 720;

        PickImageHelper.pickImage(AdvancedTeamInfoActivity.this, requestCode, option);
    }


    private void initAdapter() {
        memberAccounts = new ArrayList<>();
        members = new ArrayList<>();
        dataSource = new ArrayList<>();
        managerList = new ArrayList<>();
        adapter = new TeamMemberAdapter(this, dataSource, this, null, this);
        adapter.setEventListener(this);

        gridView.setSelector(R.color.transparent);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        gridView.setAdapter(adapter);
    }

    /**
     * 初始化群组基本信息
     */
    private void loadTeamInfo() {
        Team t = TeamDataCache.getInstance().getTeamById(teamId);
        if (t != null) {
            updateTeamInfo(t);
        } else {
            TeamDataCache.getInstance().fetchTeamById(teamId, new SimpleCallback<Team>() {
                @Override
                public void onResult(boolean success, Team result) {
                    if (success && result != null) {
                        updateTeamInfo(result);
                    } else {
                        onGetTeamInfoFailed();
                    }
                }
            });
        }
    }

    private void onGetTeamInfoFailed() {
        Toast.makeText(this, getString(R.string.team_not_exist), Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * 更新群信息
     *
     * @param t
     */
    private void updateTeamInfo(final Team t) {
        Logger.d("群：" + t.isMyTeam());
        this.team = t;
        if (team == null) {
            Toast.makeText(this, getString(R.string.team_not_exist), Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            creator = team.getCreator();
            if (creator.equals(NimUIKit.getAccount())) {
                isSelfAdmin = true;
            }

            setTitle(team.getName());
        }

//        teamHeadImage.loadTeamIconByTeam(team);
        GlideUtil.showImageViewToCircle(this, R.drawable.img_qs_50x50, team.getIcon(), teamHeadImage);
        GlideUtil.showImageViewToCircle(this, R.drawable.img_qs_50x50, team.getIcon(), teamHeadImage2);
        teamNameText.setText(team.getName());
        mTitle.setText(team.getName());
        teamIdText.setText(team.getId());

        ((TextView) layoutTeamName.findViewById(R.id.item_detail)).setText(team.getName());
        mTvIntroduce.setText(team.getIntroduce());
//        notificationConfigText.setText(team.mute() ? "关闭" : "开启");

//        setAnnouncement(team.getAnnouncement());

        mTeamNum.setText(team.getMemberCount() + "");
        teamCreateTimeText.setText(TimeUtil.getDateTimeString(team.getCreateTime(), "yyyy.MM.dd"));

    }

    /**
     * 更新群成员信息
     *
     * @param m
     */
    private void updateTeamMember(final List<TeamMember> m) {
        if (m != null && m.isEmpty()) {
            return;
        }

//        updateTeamBusinessCard(m);
        addTeamMembers(m, true);
    }


    /**
     * 添加群成员到列表
     *
     * @param m     群成员列表
     * @param clear 是否清除
     */
    private void addTeamMembers(final List<TeamMember> m, boolean clear) {
        if (m == null || m.isEmpty()) {
            return;
        }

        isSelfManager = false;
        isSelfAdmin = false;

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
            if (tm == null) {
                continue;
            }
            if (tm.getType() == TeamMemberType.Manager) {
                managerList.add(tm.getAccount());
            }
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

        if (isSelfAdmin || isSelfManager) {
            mIsMannger = true;

            layoutTeamName.findViewById(R.id.icon_next).setVisibility(View.VISIBLE);
            layoutTeamName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeamNameSettingActivity.start(AdvancedTeamInfoActivity.this, teamId, team.getName());
                }
            });

            findViewById(R.id.icon_next2).setVisibility(View.VISIBLE);
            mTvIntroduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeamPropertySettingActivity.start(AdvancedTeamInfoActivity.this, teamId, TeamFieldEnum.Introduce, team.getIntroduce());
                }
            });

            iconNextAvatar.setVisibility(View.VISIBLE);

            headerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSelector(R.string.set_head_image, REQUEST_PICK_ICON);
                }
            });
        }


//        updateAuthenView();
        updateTeamMemberDataSource();

        if (mIsMannger) {
            if (mExitTeam != null) {
                mExitTeam.setVisibility(View.GONE);
            }
        } else {
            if (mExitTeam != null) {
                mExitTeam.setVisibility(View.VISIBLE);
            }
        }

    }


    /**
     * 更新成员信息
     */
    private void updateTeamMemberDataSource() {
        if (members.size() > 0) {
            gridView.setVisibility(View.VISIBLE);
        } else {
            gridView.setVisibility(View.GONE);
            return;
        }

        dataSource.clear();

// add item
       /*
        if (team.getTeamInviteMode() == TeamInviteModeEnum.All || isSelfAdmin || isSelfManager) {
            dataSource.add(new TeamMemberAdapter.TeamMemberItem(TeamMemberAdapter.TeamMemberItemTag.ADD, null, null,
                    null));
        }*/

        // member item
        int count = 0;
        String identity = null;
        for (String account : memberAccounts) {
            int limit = TEAM_MEMBERS_SHOW_LIMIT;
            if (team.getTeamInviteMode() == TeamInviteModeEnum.All || isSelfAdmin || isSelfManager) {
                limit = TEAM_MEMBERS_SHOW_LIMIT - 1;
            }
            if (count < limit) {
                identity = getIdentity(account);
                dataSource.add(new TeamMemberAdapter.TeamMemberItem(TeamMemberAdapter.TeamMemberItemTag
                        .NORMAL, teamId, account, identity));
            }
            count++;
        }




        // refresh
        adapter.notifyDataSetChanged();
//        memberCountText.setText(String.format("共%d人", count));
    }

    private String getIdentity(String account) {
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

    /**
     * *************************** 加载&变更数据源 ********************************
     */
    private void requestMembers() {
        TeamDataCache.getInstance().fetchTeamMemberList(teamId, new SimpleCallback<List<TeamMember>>() {
            @Override
            public void onResult(boolean success, List<TeamMember> members) {
                if (success && members != null && !members.isEmpty()) {
                    updateTeamMember(members);
                }
            }
        });
    }

    /**
     * ************************** 群信息变更监听 **************************
     */
    /**
     * 注册群信息更新监听
     *
     * @param register
     */
    private void registerObservers(boolean register) {
        if (register) {
            TeamDataCache.getInstance().registerTeamMemberDataChangedObserver(teamMemberObserver);
            TeamDataCache.getInstance().registerTeamDataChangedObserver(teamDataObserver);
        } else {
            TeamDataCache.getInstance().unregisterTeamMemberDataChangedObserver(teamMemberObserver);
            TeamDataCache.getInstance().unregisterTeamDataChangedObserver(teamDataObserver);
        }

        registerUserInfoChangedObserver(register);
    }

    TeamDataCache.TeamMemberDataChangedObserver teamMemberObserver = new TeamDataCache.TeamMemberDataChangedObserver() {

        @Override
        public void onUpdateTeamMember(List<TeamMember> m) {
            for (TeamMember mm : m) {
                for (TeamMember member : members) {
                    if (mm.getAccount().equals(member.getAccount())) {
                        members.set(members.indexOf(member), mm);
                        break;
                    }
                }
            }
            addTeamMembers(m, false);
        }

        @Override
        public void onRemoveTeamMember(TeamMember member) {
            removeMember(member.getAccount());
        }
    };

    TeamDataCache.TeamDataChangedObserver teamDataObserver = new TeamDataCache.TeamDataChangedObserver() {
        @Override
        public void onUpdateTeams(List<Team> teams) {
            for (Team team : teams) {
                if (team.getId().equals(teamId)) {
                    updateTeamInfo(team);
                    updateTeamMemberDataSource();
                    break;
                }
            }
        }

        @Override
        public void onRemoveTeam(Team team) {
            if (team.getId().equals(teamId)) {
                AdvancedTeamInfoActivity.this.team = team;
                finish();
            }
        }
    };

    /**
     * ******************************* Action *********************************
     */

    /**
     * 从联系人选择器发起邀请成员
     */
    @Override
    public void onAddMember() {
        ContactSelectActivity.Option option = TeamHelper.getContactSelectOption(memberAccounts);
        NimUIKit.startContactSelect(AdvancedTeamInfoActivity.this, option, REQUEST_CODE_CONTACT_SELECT);
    }


    /**
     * 邀请群成员
     *
     * @param accounts 邀请帐号
     */
    private void inviteMembers(ArrayList<String> accounts) {
        NIMClient.getService(TeamService.class).addMembers(teamId, accounts).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                Toast.makeText(AdvancedTeamInfoActivity.this, "添加群成员成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(int code) {
                if (code == ResponseCode.RES_TEAM_INVITE_SUCCESS) {
                    Toast.makeText(AdvancedTeamInfoActivity.this, R.string.team_invite_members_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdvancedTeamInfoActivity.this, "invite members failed, code=" + code, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "invite members failed, code=" + code);
                }
            }

            @Override
            public void onException(Throwable exception) {

            }
        });
    }

    /**
     * 转让群
     *
     * @param account 转让的帐号
     */
    private void transferTeam(final String account) {
        TeamMember member = TeamDataCache.getInstance().getTeamMember(teamId, account);
        if (member == null) {
            Toast.makeText(AdvancedTeamInfoActivity.this, "成员不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        if (member.isMute()) {
            Toast.makeText(AdvancedTeamInfoActivity.this, "该成员已被禁言，请先取消禁言", Toast.LENGTH_LONG).show();
            return;
        }
        NIMClient.getService(TeamService.class).transferTeam(teamId, account, false)
                .setCallback(new RequestCallback<List<TeamMember>>() {
                    @Override
                    public void onSuccess(List<TeamMember> members) {
                        creator = account;
                        updateTeamMember(TeamDataCache.getInstance().getTeamMemberList(teamId));
                        Toast.makeText(AdvancedTeamInfoActivity.this, R.string.team_transfer_success, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int code) {
                        Toast.makeText(AdvancedTeamInfoActivity.this, R.string.team_transfer_failed, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "team transfer failed, code=" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
    }

    /**
     * 非群主退出群
     */
    private void quitTeam() {
        if (mDropId <= 0) {
            getDropId();
        }
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, mDropId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.logoutTeam(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(this) {
            @Override
            protected void _onNext(Object o) {
                Toast.makeText(AdvancedTeamInfoActivity.this, R.string.quit_team_success, Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK, new Intent().putExtra(RESULT_EXTRA_REASON, RESULT_EXTRA_REASON_QUIT));
                finish();
            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(AdvancedTeamInfoActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onHeadImageViewClick(String account) {
        // 打开群成员信息详细页面
//        AdvancedTeamMemberInfoActivity.startActivityForResult(AdvancedTeamInfoActivity.this, account, teamId);
        try {
            String yuanshi_ = account.replace("yuanshi_", "");
            long uid = Long.parseLong(yuanshi_);
            NimUIKit.getTeamMemberClickListener().onTeamMemberClickListener(AdvancedTeamInfoActivity.this, uid);
        } catch (Exception e) {
        }
    }


    /**
     * 移除群成员成功后，删除列表中的群成员
     *
     * @param account 被删除成员帐号
     */
    private void removeMember(String account) {
        if (TextUtils.isEmpty(account)) {
            return;
        }

        memberAccounts.remove(account);

        for (TeamMember m : members) {
            if (m.getAccount().equals(account)) {
                members.remove(m);
                break;
            }
        }

//        memberCountText.setText(String.format("共%d人", members.size()));

        for (TeamMemberItem item : dataSource) {
            if (item.getAccount() != null && item.getAccount().equals(account)) {
                dataSource.remove(item);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 是否设置了管理员刷新界面
     *
     * @param isSetAdmin
     * @param account
     */
    private void refreshAdmin(boolean isSetAdmin, String account) {
        if (isSetAdmin) {
            if (managerList.contains(account)) {
                return;
            }
            managerList.add(account);
            updateTeamMemberDataSource();
        } else {
            if (managerList.contains(account)) {
                managerList.remove(account);
                updateTeamMemberDataSource();
            }
        }
    }

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

    /**
     * 更新头像
     */
    private void updateTeamIcon(final String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }

        File file = new File(path);
        if (file == null) {
            return;
        }
        DialogMaker.showProgressDialog(this, null, null, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancelUpload(R.string.team_update_cancel);
            }
        }).setCanceledOnTouchOutside(true);

        LogUtil.i(TAG, "start upload icon, local file path=" + file.getAbsolutePath());
        new Handler().postDelayed(outimeTask, ICON_TIME_OUT);
        uploadFuture = NIMClient.getService(NosService.class).upload(file, PickImageAction.MIME_JPEG);
        uploadFuture.setCallback(new RequestCallbackWrapper<String>() {
            @Override
            public void onResult(int code, String url, Throwable exception) {
                if (code == ResponseCode.RES_SUCCESS && !TextUtils.isEmpty(url)) {
                    LogUtil.i(TAG, "upload icon success, url =" + url);

                    NIMClient.getService(TeamService.class).updateTeam(teamId, TeamFieldEnum.ICON, url).setCallback(new RequestCallback<Void>() {
                        @Override
                        public void onSuccess(Void param) {
                            DialogMaker.dismissProgressDialog();
                            Toast.makeText(AdvancedTeamInfoActivity.this, R.string.update_success, Toast.LENGTH_SHORT).show();
                            onUpdateDone();
                        }

                        @Override
                        public void onFailed(int code) {
                            DialogMaker.dismissProgressDialog();
                            Toast.makeText(AdvancedTeamInfoActivity.this, String.format(getString(R.string.update_failed), code), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onException(Throwable exception) {
                            DialogMaker.dismissProgressDialog();
                        }
                    }); // 更新资料
                } else {
                    Toast.makeText(AdvancedTeamInfoActivity.this, R.string.team_update_failed, Toast
                            .LENGTH_SHORT).show();
                    onUpdateDone();
                }
            }
        });
    }

    private void cancelUpload(int resId) {
        if (uploadFuture != null) {
            uploadFuture.abort();
            Toast.makeText(AdvancedTeamInfoActivity.this, resId, Toast.LENGTH_SHORT).show();
            onUpdateDone();
        }
    }

    private Runnable outimeTask = new Runnable() {
        @Override
        public void run() {
            cancelUpload(R.string.team_update_failed);
        }
    };

    private void onUpdateDone() {
        uploadFuture = null;
        DialogMaker.dismissProgressDialog();
    }


}
