package com.netease.nim.uikit.session.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.ContactEntity;
import com.netease.nim.uikit.model.FansGroupEntity;
import com.netease.nim.uikit.model.MyFriendEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.netease.nim.uikit.session.adapter.ContactsMultSelectAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import rx.Observable;

import static com.netease.nim.uikit.R.id.indexableLayout;

/**
 * Created by dengxiaolei on 2017/5/22.
 */

public class ContactMultSelectActivity extends UI {

    public static final int START_GROUP_ACTIVITY_REQUEST = 10;
    public static final int REQUEST_CODE = 100;

    IndexableLayout mIndexableLayout;
    private ContactsMultSelectAdapter mAdapter;
    private ArrayList<ContactEntity> mList;

    private ImageView mEmptyView;
    private TextView mTvRight;
    private String mTeamId;
    private ArrayList mMembers;
    private long mDropId;

    private AlertDialog.Builder mBuilder;


    public static void startForResult(Context context, String teamId, ArrayList<String> members, long dropId) {
        Intent starter = new Intent(context, ContactMultSelectActivity.class);
        starter.putExtra(Constants.EXTRA_TEAM_ID, teamId);
        starter.putExtra(Constants.EXTRA_DROP_ID, dropId);
        starter.putStringArrayListExtra(Constants.EXTRA_LIST, members);
        ((Activity)context).startActivityForResult(starter, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contacts_mult_select);

        mTeamId = getIntent().getStringExtra(Constants.EXTRA_TEAM_ID);
        mMembers = getIntent().getStringArrayListExtra(Constants.EXTRA_LIST);
        mDropId = getIntent().getLongExtra(Constants.EXTRA_DROP_ID, 0);

        initToolbar();

        initIndexableLayout();

        initData();

        initListener();
    }


    /*  private void parseIntent() {
          Intent intent = getIntent();
          if (intent != null) {
              mReceiveAccount = intent.getStringExtra(EXTRA_ACCOUNT);
              mSessionType = (SessionTypeEnum) intent.getSerializableExtra(EXTRA_SESSION_TYPE);
          }
      }
  */
    private void initToolbar() {
        TextView title = (TextView) findViewById(R.id.tv_commn_title);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_left);
        mTvRight = (TextView) findViewById(R.id.tv_right);
        mTvRight.setText("确定");
        mTvRight.setTextColor(Color.GRAY);
        mTvRight.setEnabled(false);
        mTvRight.setVisibility(View.VISIBLE);

        title.setText("邀请好友");
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initIndexableLayout() {
        mEmptyView = (ImageView) findViewById(R.id.iv_empty_view);
        mIndexableLayout = (IndexableLayout) findViewById(indexableLayout);
        mIndexableLayout.showAllLetter(false);
        mIndexableLayout.setLayoutManager(new LinearLayoutManager(this));
        mIndexableLayout.setOverlayStyle_MaterialDesign(Color.parseColor("#0EC7F0"));
        mAdapter = new ContactsMultSelectAdapter(this);
        mIndexableLayout.setAdapter(mAdapter);
        mIndexableLayout.setFastCompare(true);
        mAdapter.setOnItemContentClickListener(itemClickListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && requestCode == START_GROUP_ACTIVITY_REQUEST) {
            finish();
        }
    }


    protected void initData() {
        mList = new ArrayList<>();
        mList.clear();

        List<MyFriendEntity> myFriendEntities = FriendDataCache.getInstance().queryFriends();
        if (myFriendEntities == null || myFriendEntities.size() < 1) {
            mIndexableLayout.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mIndexableLayout.setVisibility(View.VISIBLE);

            for (MyFriendEntity myFriendEntity : myFriendEntities) {
                ContactEntity entity = new ContactEntity();
                entity.setAccId(myFriendEntity.getfNeteaseAccid());
                entity.setName(myFriendEntity.getFNickName());
                entity.setAvatar(myFriendEntity.getFPhoto());
                entity.setUid(myFriendEntity.getFUid());
                entity.setToken(myFriendEntity.getfNeteaseToken());
                if (mMembers != null && mMembers.size() > 0 && mMembers.contains(myFriendEntity.getfNeteaseAccid())) {
                    entity.setAdded(true);
                } else {
                    entity.setAdded(false);
                }
                mList.add(entity);
            }

            mAdapter.setDatas(mList);
        }

    }

    protected void initListener() {
        mAdapter.setOnCheckBoxCheckedListener(new ContactsMultSelectAdapter.OnCheckBoxCheckedListener() {
            @Override
            public void onCheckChange() {
                List<Long> selectedAccounts = mAdapter.getSelectedAccounts();
                if (selectedAccounts.size() > 0) {
                    mTvRight.setText("确定(" + selectedAccounts.size() + ")");
                    mTvRight.setTextColor(Color.WHITE);
                    mTvRight.setEnabled(true);
                } else {
                    mTvRight.setText("确定");
                    mTvRight.setTextColor(Color.GRAY);
                    mTvRight.setEnabled(false);
                }
            }
        });

        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTeamDialog();
            }
        });
    }

    /**
     * 拉人入群的弹框
     */
    private void showAddTeamDialog() {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(this);
            mBuilder.setTitle("温馨提示");
            mBuilder.setMessage("确认邀请加入群聊吗？");
            mBuilder.setNegativeButton(getString(R.string.cancel), null);
        }

        mBuilder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addTeam();
            }
        });
        mBuilder.show();

    }

    private void getDropId() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.room_id, mTeamId);
        Observable<BaseResponse<FansGroupEntity>> observable = HttpUtil.getInstance().sApi.getRoomInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable , new ProgressSubscriber<FansGroupEntity>(ContactMultSelectActivity.this) {
            @Override
            protected void _onNext(FansGroupEntity fansGroupEntity) {
                if (fansGroupEntity != null && fansGroupEntity.getDropId() != 0) {
                    mDropId = fansGroupEntity.getDropId();
                } else {
                    Toast.makeText(ContactMultSelectActivity.this, "邀请识别， 请重试1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(ContactMultSelectActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addTeam() {
        if (mDropId == 0) {
            getDropId();
        }
        List<Long> selectedAccounts = mAdapter.getSelectedAccounts();
        Logger.d("加群：" + selectedAccounts.get(0));
        if (selectedAccounts == null || selectedAccounts.size() <= 0) {
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(selectedAccounts);
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, mDropId);
        map.put(RequestParams.friend_uid_json, json);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.addTeam(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(ContactMultSelectActivity.this) {
            @Override
            protected void _onNext(Object o) {
                Toast.makeText(ContactMultSelectActivity.this, "邀请成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(ContactMultSelectActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private IndexableAdapter.OnItemContentClickListener itemClickListener = new IndexableAdapter.OnItemContentClickListener() {
        @Override
        public void onItemClick(View v, int originalPosition, int currentPosition, Object entity) {

        }
    };


}