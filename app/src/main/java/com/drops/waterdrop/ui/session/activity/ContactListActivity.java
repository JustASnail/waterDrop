package com.drops.waterdrop.ui.session.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.activity.UserProfileActivity;
import com.drops.waterdrop.ui.session.presenter.ContactListPresenter;
import com.drops.waterdrop.ui.session.view.IContactListView;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.dialog.CustomAlertDialog;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.contact.ContactAdapter;
import com.netease.nim.uikit.model.MyFriendEntity;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableHeaderAdapter;
import me.yokeyword.indexablerv.IndexableLayout;


/**
 * 通讯录
 * Created by dengxiaolei on 2017/5/11.
 */

public class ContactListActivity extends BaseActivity<IContactListView, ContactListPresenter> implements IContactListView {
    /*
        @Bind(R.id.unread_cover)
        DropCover mUnreadCover;*/
    private ContactAdapter mAdapter;

    private List<MyFriendEntity> mFriends;
    private IndexableLayout mIndexableLayout;


    public static void start(Context context) {
        Intent starter = new Intent(context, ContactListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
     /*   StatusBarUtil.setTransparentForImageViewInFragment(this, null);//设置状态栏透明， 并且toolbar向下偏移状态栏的高度

        View statusBarFix = findView(R.id.status_bar_fix);
        statusBarFix.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getStatusBarHeight(this)));//填充状态栏*/

        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "通讯录";
        options.imgRightId = R.mipmap.icon_add_friend;
        setMyToolbar(options);

        initAdapter();

        final ImageView ivRight = (ImageView) findViewById(R.id.iv_right);
        if (!MyUserCache.getGuide()) {
            ivRight.post(new Runnable() {
                @Override
                public void run() {
                    mPresenter.showGuideView(ivRight, R.id.rl_container);
                }
            });
        }


    }


    @Override
    protected void initData() {
        //注册未读红点消息监听
//        mPresenter.registerSystemMessageObservers(true);
//        mPresenter.requestSystemMessageUnreadCount();
//        mPresenter.initUnreadCover(mUnreadCover);

        // 注册观察者
        mPresenter.registerObserver(true);


        // 加载本地数据
        mPresenter.reload(false);
    }

    @Override
    protected void initListener() {
        findViewById(R.id.add_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    AddFriendV2Activity.startActivity(ContactListActivity.this);
//                    AddFriendActivity.start(ContactListActivity.this);
                }

            }
        });

    }




    @Override
    protected ContactListPresenter createPresenter() {
        return new ContactListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_contact_list;
    }

    @Override
    public void onGetFriendsSucceed(List<MyFriendEntity> friends) {
        if (mAdapter != null) {
            mAdapter.setDatas(friends);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.onDestroy();
    }

    @Override
    public void initAdapter() {
        if (mAdapter == null) {
            mIndexableLayout = findView(com.netease.nim.uikit.R.id.indexableLayout);
            mIndexableLayout.setLayoutManager(new LinearLayoutManager(this));
            mIndexableLayout.showAllLetter(false);
            List<String> headList = new ArrayList<>();
            headList.add("");
            mIndexableLayout.addHeaderAdapter(new HeadAdapter("↑", null, headList));


            // set Material Design OverlayView
            mIndexableLayout.setOverlayStyle_MaterialDesign(Color.parseColor("#0EC7F0"));

            // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
            mIndexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);
            mAdapter = new ContactAdapter(this);
            mIndexableLayout.setAdapter(mAdapter);

            mAdapter.setOnItemContentClickListener(onContactItemClickListener);
            mAdapter.setOnItemContentLongClickListener(onContactItemLongClickListener);
        }
    }

    @Override
    public void setUnreadCount(int unread) {

    }


    class HeadAdapter extends IndexableHeaderAdapter {

        public HeadAdapter(String index, String indexTitle, List datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext())
                    .inflate(com.netease.nim.uikit.R.layout.layout_contact_header, parent, false);
            return new HeadContentVH(inflate);
        }

        @Override
        public void onBindContentViewHolder(final RecyclerView.ViewHolder holder, Object entity) {
            HeadContentVH vh = (HeadContentVH) holder;

           /* int unreadCount = SystemMessageUnreadManager.getInstance().getSysMsgUnreadCount();
            updateUnreadNum(((HeadContentVH) holder).tvUnreadNum, unreadCount);

            ReminderManager.getInstance().registerUnreadNumChangedCallback(new ReminderManager.UnreadNumChangedCallback() {
                @Override
                public void onUnreadNumChanged(ReminderItem item) {
                    if (item.getId() != ReminderId.CONTACT) {
                        return;
                    }

                    updateUnreadNum(((HeadContentVH) holder).tvUnreadNum, item.getUnread());
                }
            });*/
            vh.rlMyQun.setOnClickListener(onMyQunClickListener);
            vh.rlNewFriend.setOnClickListener(onNewFriendClickListener);
            vh.rlRecommend.setOnClickListener(onRecommentClickListener);
        }

  /* private class HeadVH extends RecyclerView.ViewHolder {
               public HeadVH(View itemView) {
                   super(itemView);

               }
           }*/

        private class HeadContentVH extends RecyclerView.ViewHolder {
            RelativeLayout rlNewFriend;
            RelativeLayout rlMyQun;
            RelativeLayout rlRecommend;
            TextView tvUnreadNum;

            public HeadContentVH(View itemView) {
                super(itemView);
                rlNewFriend = (RelativeLayout) itemView.findViewById(com.netease.nim.uikit.R.id.rl_new_friend);
                rlMyQun = (RelativeLayout) itemView.findViewById(com.netease.nim.uikit.R.id.rl_my_qun);
                rlRecommend = (RelativeLayout) itemView.findViewById(com.netease.nim.uikit.R.id.rl_recommend_friend);
//                tvUnreadNum = (TextView) itemView.findViewById(com.netease.nim.uikit.R.id.tab_new_msg_label);
            }
        }

/*
        private void updateUnreadNum(TextView tvUnreadNum, int unreadCount) {
            // 2.*版本viewholder复用问题
            if (unreadCount > 0) {
                tvUnreadNum.setVisibility(View.VISIBLE);
                tvUnreadNum.setText("" + unreadCount);
            } else {
                tvUnreadNum.setVisibility(View.GONE);
            }
        }
*/

    }


    private View.OnClickListener onMyQunClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!FastClickUtil.isFastDoubleClick()) {
//                MyPoolListActivity.start(ContactListActivity.this);
                FansGroupActivity.start(ContactListActivity.this);
            }
        }
    };

    private View.OnClickListener onNewFriendClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!FastClickUtil.isFastDoubleClick()) {
                SystemMessageActivity.start(ContactListActivity.this, false);
            }
        }
    };

    private View.OnClickListener onRecommentClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!FastClickUtil.isFastDoubleClick()) {
                MaybeKownActivity.start(ContactListActivity.this);
            }
        }
    };


    private IndexableAdapter.OnItemContentClickListener onContactItemClickListener = new IndexableAdapter.OnItemContentClickListener<MyFriendEntity>() {
        @Override
        public void onItemClick(View v, int originalPosition, int currentPosition, MyFriendEntity entity) {
//            NimUIKit.startP2PSession(ContactListActivity.this, entity.getfNeteaseAccid());
            if (!FastClickUtil.isFastDoubleClick()) {
                UserProfileActivity.start(ContactListActivity.this, entity.getFUid());
            }
        }
    };

    private IndexableAdapter.OnItemContentLongClickListener<MyFriendEntity> onContactItemLongClickListener = new IndexableAdapter.OnItemContentLongClickListener<MyFriendEntity>() {
        @Override
        public boolean onItemLongClick(View v, int originalPosition, int currentPosition, final MyFriendEntity entity) {

            // 长按联系人
            CustomAlertDialog dialog = new CustomAlertDialog(ContactListActivity.this);
            dialog.addItem("删除", new CustomAlertDialog.onSeparateItemClickListener() {
                @Override
                public void onClick() {
                    mPresenter.onRemoveFriend(entity);
                }
            });
            dialog.show();
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        if (MyUserCache.getGuide()) {
            super.onBackPressed();
        }

    }

    @Override
    public boolean isSupportSwipeBack() {
        if (MyUserCache.getGuide()) {
            return super.isSupportSwipeBack();
        } else {
            return false;
        }
    }
}
