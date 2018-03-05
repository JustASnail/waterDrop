package com.netease.nim.uikit.session.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.ContactEntity;
import com.netease.nim.uikit.model.MyFriendEntity;
import com.netease.nim.uikit.model.ShareCardModel;
import com.netease.nim.uikit.session.adapter.ContactsHeaderAdapter;
import com.netease.nim.uikit.session.adapter.ContactsSelectAdapter;
import com.netease.nim.uikit.session.helper.CardShareHelper;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableHeaderAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

import static com.netease.nim.uikit.R.id.indexableLayout;

/**
 * Created by dengxiaolei on 2017/5/22.
 */

public class ContactsSelectActivity extends UI {

    public static final int START_GROUP_ACTIVITY_REQUEST = 10;

    IndexableLayout mIndexableLayout;
    private ContactsSelectAdapter mAdapter;
    private ArrayList<ContactEntity> mList;
    private String mNickName;
    private String mCardAvatar;
    private int mUserSex;
    private long mCardUid;
    private int mShareType;
    private String mShareTitle;
    private String mShareContent;
    private String mSharePhoto;
    private String mShareId;
    private String mShareFrom;
    private long mTipId;
    private long mPoolId;
    private String mFromName;
    private String mTipUrl;
//    private ImageView mEmptyView;
    private ShareCardModel mModel;

    public static void startForUser(Context context, long uid, String nickName, String phone, int sex) {
        Intent starter = new Intent(context, ContactsSelectActivity.class);
        starter.putExtra(Constants.EXTRA_CARD_UID, uid);
        starter.putExtra(Constants.EXTRA_NICK_NAME, nickName);
        starter.putExtra(Constants.EXTRA_CARD_USER_AVATAR, phone);
        starter.putExtra(Constants.EXTRA_CARD_SEX, sex);
        starter.putExtra(Constants.EXTRA_SHARE_TYPE, Constants.SHARE_TYPE_USER);
        context.startActivity(starter);
    }

    /**
     * 水宝分享的跳转
     *
     * @param context
     * @param goodsId    水宝
     * @param goodsTitle 水宝的标题
     * @param goodsPhoto 水宝的图片
     * @param goodsDesc  水宝的描述
     * @param tipId      水贴id
     * @param poolId     水塘id
     * @param goodFrom   来自哪个水贴
     */
    public static void startForGoods(Context context, String goodsId, String goodsTitle, String goodsPhoto, String goodsDesc,
                                     long tipId, long poolId, String goodFrom) {
        Intent starter = new Intent(context, ContactsSelectActivity.class);
        starter.putExtra(Constants.EXTRA_SHARE_TYPE, Constants.SHARE_TYPE_GOODS);
        starter.putExtra(Constants.EXTRA_SHARE_ID, goodsId);
        starter.putExtra(Constants.EXTRA_TITLE, goodsTitle);
        starter.putExtra(Constants.EXTRA_PHOTO, goodsPhoto);
        starter.putExtra(Constants.EXTRA_CONTENT, goodsDesc);
        starter.putExtra(Constants.EXTRA_TIP_ID, tipId);
        starter.putExtra(Constants.EXTRA_POOL_ID, poolId);
        starter.putExtra(Constants.EXTRA_FROM_NAME, goodFrom);

        context.startActivity(starter);
    }

    /**
     * 水贴分享的跳转
     *
     * @param context
     * @param tipId    水塘id
     * @param tipTitle 水塘标题
     * @param tipPhoto 水塘图片
     * @param tipDesc  水塘介绍
     * @param tipFrom  来自哪个水塘
     * @param tipUrl   水贴的url
     */
    public static void startForTip(Context context, long tipId, String tipTitle, String tipPhoto, String tipDesc, String tipFrom, String tipUrl
    ) {
        Intent starter = new Intent(context, ContactsSelectActivity.class);
        starter.putExtra(Constants.EXTRA_SHARE_TYPE, Constants.SHARE_TYPE_POST);
        starter.putExtra(Constants.EXTRA_SHARE_ID, String.valueOf(tipId));
        starter.putExtra(Constants.EXTRA_TITLE, tipTitle);
        starter.putExtra(Constants.EXTRA_PHOTO, tipPhoto);
        starter.putExtra(Constants.EXTRA_CONTENT, tipDesc);
        starter.putExtra(Constants.EXTRA_FROM_NAME, tipFrom);
        starter.putExtra(Constants.EXTRA_TIP_URL, tipUrl);
        context.startActivity(starter);
    }

    /**
     * 水塘分享的跳转
     *
     * @param context
     * @param poolId    水塘id
     * @param poolTitle 水塘标题
     * @param poolPhoto 水塘图片
     * @param poolDesc  水塘介绍
     */
    public static void startForPool(Context context, long poolId, String poolTitle, String poolPhoto, String poolDesc
    ) {
        Intent starter = new Intent(context, ContactsSelectActivity.class);
        starter.putExtra(Constants.EXTRA_SHARE_TYPE, Constants.SHARE_TYPE_POOL);
        starter.putExtra(Constants.EXTRA_SHARE_ID, String.valueOf(poolId));
        starter.putExtra(Constants.EXTRA_TITLE, poolTitle);
        starter.putExtra(Constants.EXTRA_PHOTO, poolPhoto);
        starter.putExtra(Constants.EXTRA_CONTENT, poolDesc);
        context.startActivity(starter);
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, ContactsSelectActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contacts_select);


        initToolbar();

        initIndexableLayout();

        initData();

        parseIntent(getIntent());
    }

    private void parseIntent(Intent intent) {

        mShareType = intent.getIntExtra(Constants.EXTRA_SHARE_TYPE, -1);
        switch (mShareType) {
            case Constants.SHARE_TYPE_USER:
                mNickName = intent.getStringExtra(Constants.EXTRA_NICK_NAME);
                mCardAvatar = intent.getStringExtra(Constants.EXTRA_CARD_USER_AVATAR);
                mUserSex = intent.getIntExtra(Constants.EXTRA_CARD_SEX, 1);
                mCardUid = intent.getLongExtra(Constants.EXTRA_CARD_UID, 0);
                break;
            case Constants.SHARE_TYPE_GOODS:
                mTipId = intent.getLongExtra(Constants.EXTRA_TIP_ID, 0);
                mPoolId = intent.getLongExtra(Constants.EXTRA_POOL_ID, 0);
                mShareTitle = intent.getStringExtra(Constants.EXTRA_TITLE);
                mShareContent = intent.getStringExtra(Constants.EXTRA_CONTENT);
                mSharePhoto = intent.getStringExtra(Constants.EXTRA_PHOTO);
                mShareId = intent.getStringExtra(Constants.EXTRA_SHARE_ID);
                mFromName = intent.getStringExtra(Constants.EXTRA_FROM_NAME);
                mShareFrom = "水宝";
                break;
            case Constants.SHARE_TYPE_POST:
                mShareTitle = intent.getStringExtra(Constants.EXTRA_TITLE);
                mShareContent = intent.getStringExtra(Constants.EXTRA_CONTENT);
                mSharePhoto = intent.getStringExtra(Constants.EXTRA_PHOTO);
                mShareId = intent.getStringExtra(Constants.EXTRA_SHARE_ID);
                mFromName = intent.getStringExtra(Constants.EXTRA_FROM_NAME);
                mTipUrl = intent.getStringExtra(Constants.EXTRA_TIP_URL);
                mShareFrom = "水帖";
                break;
            case Constants.SHARE_TYPE_POOL:
                mShareTitle = intent.getStringExtra(Constants.EXTRA_TITLE);
                mShareContent = intent.getStringExtra(Constants.EXTRA_CONTENT);
                mSharePhoto = intent.getStringExtra(Constants.EXTRA_PHOTO);
                mShareId = intent.getStringExtra(Constants.EXTRA_SHARE_ID);
                mShareFrom = "水塘";
                break;
        }
        mModel = new ShareCardModel();

        mModel.setShareType(mShareType);
        mModel.setShareId(mShareId);
        mModel.setShareTitle(mShareTitle);
        mModel.setSharePhoto(mSharePhoto);
        mModel.setShareContent(mShareContent);
        mModel.setShareFrom(mShareFrom);
        mModel.setTipUrl(mTipUrl);
        mModel.setPoolId(mPoolId);
        mModel.setTipId(mTipId);
        mModel.setCardUserNickName(mNickName);
        mModel.setCardUserPhoto(mCardAvatar);
        mModel.setCardUserUid(mCardUid);
        mModel.setCardUserSex(mUserSex);
        mModel.setFromName(mFromName);

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

        title.setText("选择联系人");
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initIndexableLayout() {
//        mEmptyView = (ImageView) findViewById(R.id.iv_empty_view);
        mIndexableLayout = (IndexableLayout) findViewById(indexableLayout);
        mIndexableLayout.showAllLetter(false);
        mIndexableLayout.setLayoutManager(new LinearLayoutManager(this));
        mIndexableLayout.setOverlayStyle_MaterialDesign(Color.parseColor("#0EC7F0"));
        mAdapter = new ContactsSelectAdapter(this);
        mIndexableLayout.setAdapter(mAdapter);
        mIndexableLayout.setFastCompare(true);
        mAdapter.setOnItemContentClickListener(itemClickListener);

        List<String> headList = new ArrayList<>();
        headList.add("");
        ContactsHeaderAdapter headerAdapter = new ContactsHeaderAdapter("群", null, headList);
        mIndexableLayout.addHeaderAdapter(headerAdapter);
        headerAdapter.setOnItemHeaderClickListener(new IndexableHeaderAdapter.OnItemHeaderClickListener() {
            @Override
            public void onItemClick(View v, int currentPosition, Object entity) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    if (mModel != null) {
                        FansGroupSelectActivity.startForResult(ContactsSelectActivity.this, START_GROUP_ACTIVITY_REQUEST, mModel);
                    }
                }

            }
        });
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
//            mIndexableLayout.setVisibility(View.GONE);
//            mEmptyView.setVisibility(View.VISIBLE);
        } else {
//            mEmptyView.setVisibility(View.GONE);
//            mIndexableLayout.setVisibility(View.VISIBLE);

            for (MyFriendEntity myFriendEntity : myFriendEntities) {
                ContactEntity entity = new ContactEntity();
                entity.setAccId(myFriendEntity.getfNeteaseAccid());
                entity.setName(myFriendEntity.getFNickName());
                entity.setAvatar(myFriendEntity.getFPhoto());
                entity.setUid(myFriendEntity.getFUid());
                entity.setToken(myFriendEntity.getfNeteaseToken());
                mList.add(entity);
            }

            mAdapter.setDatas(mList);
        }

    }

    protected void initListener() {

    }

    private IndexableAdapter.OnItemContentClickListener itemClickListener = new IndexableAdapter.OnItemContentClickListener() {
        @Override
        public void onItemClick(View v, int originalPosition, int currentPosition, Object entity) {

            if (entity instanceof ContactEntity) {
                ContactEntity contactEntity = (ContactEntity) entity;
                if (mModel != null) {
                    mModel.setReceiveAccount(contactEntity.getAccId());
                    mModel.setReceiveUserName(contactEntity.getName());
                    mModel.setReceiveUserPhoto(contactEntity.getAvatar());
                    mModel.setSessionTypeEnum(SessionTypeEnum.P2P);

                    CardShareHelper.showMyDialog(ContactsSelectActivity.this, mModel, new CardShareHelper.OnShareSucceedListener() {
                        @Override
                        public void onShareSucceed() {
                            Toast.makeText(ContactsSelectActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }
    };


}