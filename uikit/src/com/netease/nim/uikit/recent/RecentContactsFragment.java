package com.netease.nim.uikit.recent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.common.ui.dialog.CustomAlertDialog;
import com.netease.nim.uikit.common.ui.drop.DropCover;
import com.netease.nim.uikit.common.ui.drop.DropManager;
import com.netease.nim.uikit.common.ui.recyclerview.listener.SimpleClickListener;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nim.uikit.event.SystemMessageEvent;
import com.netease.nim.uikit.gen.FriendDBDao;
import com.netease.nim.uikit.gen.SystemMessageDBDao;
import com.netease.nim.uikit.green_dao.FriendDB;
import com.netease.nim.uikit.green_dao.GreenDaoManager;
import com.netease.nim.uikit.green_dao.SystemMessageDB;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AppInfoEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.MyFriendEntity;
import com.netease.nim.uikit.model.MyRecentContact;
import com.netease.nim.uikit.model.RecentMessageListEntity;
import com.netease.nim.uikit.recent.adapter.RecentContactAdapter;
import com.netease.nim.uikit.request_body.RequestParams;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Observable;

import static com.netease.nim.uikit.common.ui.dialog.CustomAlertDialog.onSeparateItemClickListener;

/**
 * 最近联系人列表(会话列表)
 * <p/>
 * Created by huangjun on 2015/2/1.
 */
public class RecentContactsFragment extends TFragment {

    // 置顶功能可直接使用，也可作为思路，供开发者充分利用RecentContact的tag字段
    public static final long RECENT_TAG_STICKY = 1; // 联系人置顶tag

    // view
    private RecyclerView recyclerView;

    private View emptyBg;

    private TextView emptyHint;

    // data
    private List<RecentContact> items;

    private Map<String, RecentContact> cached; // 暂缓刷上列表的数据（未读数红点拖拽动画运行时用）

    private RecentContactAdapter adapter;

    private boolean msgLoaded = false;

    private RecentContactsCallback callback;

    private UserInfoObservable.UserInfoObserver userInfoObserver;
    private String mSearchStart;

    private boolean isFrist = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);//订阅
        findViews();
        initMessageList();

        initServerMessage();

        requestMessages(true);
        registerObservers(true);
        registerDropCompletedListener(true);


    }

    private void initServerMessage() {

        Observable<BaseResponse<AppInfoEntity>> observable = HttpUtil.getInstance().sApi.getAppInfo();
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AppInfoEntity>(getContext()) {
            @Override
            protected void _onNext(AppInfoEntity entity) {
                List<AppInfoEntity.ResultsBean> results = entity.getResults();
                for (AppInfoEntity.ResultsBean resultsBean : results) {
                    if (TextUtils.equals(Constants.FRIEND_PUSH, resultsBean.getParamKey()) && TextUtils.equals("1", resultsBean.getParamValue())) {
                        insertRecommendFriendMsg();
                    }
                }
            }

            @Override
            protected void _onError(String message) {
            }
        });




    }

    private void insertRecommendFriendMsg() {

        //插入推进好友消息
        if (isFrist) {
            try {
                SystemMessageDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSystemMessageDBDao();
                SystemMessageDB recommendFriendMsg = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(Constants.MESSAGE_TYPE_RECOMMEND_FRIEND)).unique();//余额变动信息
                if (recommendFriendMsg != null) {
                    long currentTimeMillis = TimeUtil.currentTimeMillis();
                    String nowDatetime = TimeUtil.getNowDatetime();
                    recommendFriendMsg.setTime(currentTimeMillis);//数据库的id改成messageId
                    recommendFriendMsg.setNote("小主， 这里可能有您认识的人， 快来添加吧。");
                    recommendFriendMsg.setType(Constants.MESSAGE_TYPE_RECOMMEND_FRIEND);
                    recommendFriendMsg.setCreateTime(nowDatetime);
                    recommendFriendMsg.setCreateTimestamp(String.valueOf(currentTimeMillis));
//                            systemMessageDB.setMessageId(result.getMessageId());
//                            systemMessageDB.setStatus(result.getStatus());
//                            systemMessageDB.setTargetId(result.getTargetId());
                    recommendFriendMsg.setTitle("好友推荐");
                    recommendFriendMsg.setUnreadTag(0);
                    dbDao.update(recommendFriendMsg);
                } else {
                    long currentTimeMillis = TimeUtil.currentTimeMillis();
                    String nowDatetime = TimeUtil.getNowDatetime();
                    SystemMessageDB systemMessageDB = new SystemMessageDB();
                    systemMessageDB.setTime(currentTimeMillis);//数据库的id改成messageId
                    systemMessageDB.setNote("小主， 这里可能有您认识的人， 快来添加吧。");
                    systemMessageDB.setType(Constants.MESSAGE_TYPE_RECOMMEND_FRIEND);
                    systemMessageDB.setCreateTime(nowDatetime);
                    systemMessageDB.setCreateTimestamp(String.valueOf(currentTimeMillis));
                    systemMessageDB.setMessageId(Long.valueOf(-100));
//                            systemMessageDB.setStatus(result.getStatus());
//                            systemMessageDB.setTargetId(result.getTargetId());
                    systemMessageDB.setTitle("好友推荐");
                    systemMessageDB.setUnreadTag(0);
                    dbDao.insert(systemMessageDB);
                }

                refreshMessages(true);

                isFrist = false;

            } catch (Exception e) {

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nim_recent_contacts, container, false);
    }

    private void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
        boolean empty = items.isEmpty() && msgLoaded;
        emptyBg.setVisibility(empty ? View.VISIBLE : View.GONE);
        emptyHint.setHint("还没有会话，在通讯录中找个人聊聊吧！");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registerObservers(false);
        registerDropCompletedListener(false);
        EventBus.getDefault().unregister(this);//解除订阅

    }

    /**
     * 查找页面控件
     */
    private void findViews() {
        recyclerView = findView(R.id.recycler_view);
        emptyBg = findView(R.id.emptyBg);
        emptyHint = findView(R.id.message_list_empty_hint);
    }

    /**
     * 初始化消息列表
     */
    private void initMessageList() {
        items = new ArrayList<>();
        cached = new HashMap<>(3);

        // adapter
        adapter = new RecentContactAdapter(recyclerView, items);
        initCallBack();
        adapter.setCallback(callback);

        // recyclerView
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(touchListener);

        // drop listener
        DropManager.getInstance().setDropListener(new DropManager.IDropListener() {
            @Override
            public void onDropBegin() {
                touchListener.setShouldDetectGesture(false);
            }

            @Override
            public void onDropEnd() {
                touchListener.setShouldDetectGesture(true);
            }
        });
    }

    private void initCallBack() {
        if (callback != null) {
            return;
        }
        callback = new RecentContactsCallback() {
            @Override
            public void onRecentContactsLoaded() {

            }

            @Override
            public void onUnreadCountChange(int unreadCount) {

            }

            @Override
            public void onItemClick(RecentContact recent) {
                if (recent.getSessionType() == SessionTypeEnum.Team) {
                    NimUIKit.startTeamSession(getActivity(), recent.getContactId());
                } else if (recent.getSessionType() == SessionTypeEnum.P2P) {
                    NimUIKit.startP2PSession(getActivity(), recent.getContactId());
                } else if (recent.getSessionType() == SessionTypeEnum.System) {
                    Map<String, Object> map = recent.getExtension();
                    int msgType = (int) map.get(Constants.KEY_MESSAGE_TYPE);
           /*         switch (msgType) {
                        case Constants.MESSAGE_TYPE_YAN_ZHENG:
                            //跳转验证页面
                            break;
                        case Constants.MESSAGE_TYPE_ORDER:

                            //跳转订单信息页面

                            break;
                        case Constants.MESSAGE_TYPE_JIN_DOU:
                            //跳转金豆页面

                            break;
                        case Constants.MESSAGE_TYPE_JING_PIN:
                            //跳转推荐页面


                            break;
                    }*/
//                    NimUIKit.getSystemMessageListener().onItemClickListener(msgType);
                }
            }

            @Override
            public String getDigestOfAttachment(MsgAttachment attachment) {
                return null;
            }

            @Override
            public String getDigestOfTipMsg(RecentContact recent) {
                return null;
            }
        };
    }

    private SimpleClickListener<RecentContactAdapter> touchListener = new SimpleClickListener<RecentContactAdapter>() {
        @Override
        public void onItemClick(RecentContactAdapter adapter, View view, int position) {
            if (callback != null) {
                RecentContact recent = adapter.getItem(position);
                callback.onItemClick(recent);
            }
        }

        @Override
        public void onItemLongClick(RecentContactAdapter adapter, View view, int position) {
            showLongClickMenu(adapter.getItem(position), position);
        }

        @Override
        public void onItemChildClick(RecentContactAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemChildLongClick(RecentContactAdapter adapter, View view, int position) {

        }
    };

    private void showLongClickMenu(final RecentContact recent, final int position) {
        CustomAlertDialog alertDialog = new CustomAlertDialog(getActivity());
        alertDialog.setTitle(UserInfoHelper.getUserTitleName(recent.getContactId(), recent.getSessionType()));
        String title = getString(R.string.main_msg_list_delete_chatting);
        alertDialog.addItem(title, new onSeparateItemClickListener() {
            @Override
            public void onClick() {
                // 删除会话，删除后，消息历史被一起删除
                NIMClient.getService(MsgService.class).deleteRecentContact(recent);
                NIMClient.getService(MsgService.class).clearChattingHistory(recent.getContactId(), recent.getSessionType());
                adapter.remove(position);

                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        refreshMessages(true);
                    }
                });
            }
        });

       /* title = (isTagSet(recent, RECENT_TAG_STICKY) ? getString(R.string.main_msg_list_clear_sticky_on_top) : getString(R.string.main_msg_list_sticky_on_top));
        alertDialog.addItem(title, new onSeparateItemClickListener() {
            @Override
            public void onClick() {
                if (isTagSet(recent, RECENT_TAG_STICKY)) {
                    removeTag(recent, RECENT_TAG_STICKY);
                } else {
                    addTag(recent, RECENT_TAG_STICKY);
                }
                NIMClient.getService(MsgService.class).updateRecent(recent);

                refreshMessages(false);
            }
        });
*/
/*
        alertDialog.addItem("删除该聊天（仅服务器）", new onSeparateItemClickListener() {
            @Override
            public void onClick() {
                NIMClient.getService(MsgService.class)
                        .deleteRoamingRecentContact(recent.getContactId(), recent.getSessionType())
                        .setCallback(new RequestCallback<Void>() {
                            @Override
                            public void onSuccess(Void param) {
                                Toast.makeText(getActivity(), "delete success", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed(int code) {
                                Toast.makeText(getActivity(), "delete failed, code:" + code, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onException(Throwable exception) {

                            }
                        });
            }
        });
*/
        alertDialog.show();
    }

    private void addTag(RecentContact recent, long tag) {
        tag = recent.getTag() | tag;
        recent.setTag(tag);
    }

    private void removeTag(RecentContact recent, long tag) {
        tag = recent.getTag() & ~tag;
        recent.setTag(tag);
    }

    private boolean isTagSet(RecentContact recent, long tag) {
        return (recent.getTag() & tag) == tag;
    }

    private List<RecentContact> loadedRecents;

    private void requestMessages(boolean delay) {
        if (msgLoaded) {
            return;
        }
        getHandler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (msgLoaded) {
                    return;
                }
                // 查询最近联系人列表数据
                NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(new RequestCallbackWrapper<List<RecentContact>>() {

                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable exception) {
                        if (code != ResponseCode.RES_SUCCESS || recents == null) {
                            return;
                        }
                        //筛选掉非好友， 添加服务器里的头像和昵称
                        ArrayList<RecentContact> myRecentContacts = new ArrayList<>();
                        for (RecentContact recent : recents) {
//                            MyFriendEntity friend = FriendDataCache.getInstance().getFriendByAccount(recent.getContactId());
//                            if (friend != null) {
//                                String fNickName = friend.getFNickName();
//                                String fAvatar = friend.getFPhoto();
//                                Map<String, Object> data = new HashMap<>();
//                                data.put("avatar", fAvatar);
//                                data.put("nickName", fNickName);
//                                recent.setExtension(data);
                            myRecentContacts.add(recent);
//                            }

                        }

                        SystemMessageDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSystemMessageDBDao();



                        //读取本地缓存的系统消息
                        List<SystemMessageDB> friendList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(11)).list();//好友验证
                        List<SystemMessageDB> groupList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(12)).list();//群申请验证
                        List<SystemMessageDB> orderList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(21)).list();//订单物流
                        List<SystemMessageDB> moneyList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(31)).list();//金豆变动信息
                        List<SystemMessageDB> poolList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(51)).list();//水塘内容推荐
                        List<SystemMessageDB> tipList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(52)).list();//水贴内容推荐
                        List<SystemMessageDB> balanceList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(61)).list();//余额变动信息
                        List<SystemMessageDB> recommendFriendList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(Constants.MESSAGE_TYPE_RECOMMEND_FRIEND)).list();//余额变动信息


                        SystemMessageDB friendMsg = null;
                        SystemMessageDB orderMsg = null;
                        SystemMessageDB moneyMsg = null;
                        SystemMessageDB tuiJianMsg = null;
                        SystemMessageDB balanceMsg = null;
                        SystemMessageDB recommendFriendMsg = null;

                        int friendMsgUnread = 0;
                        int orderMsgUnread = 0;
                        int moneyMsgUnread = 0;
                        int tuiJianMsgUnread = 0;
                        int balanceMsgUnread = 0;
                        int recommendFriendMsgUnread = 0;

                        long yanZhengime = 0;
                        long orderTime = 0;
                        long moneyTime = 0;
                        long tuiJianTime = 0;
                        long balanceTime = 0;
                        long recommendFriendTime = 0;


                        /**********************************************  验证 ********************************************************/
                        List<SystemMessageDB> yanZhengList = new ArrayList<>();
                        if (friendList != null) {
                            yanZhengList.addAll(friendList);
                        }

                        if (groupList != null) {
                            yanZhengList.addAll(groupList);
                        }

                        for (SystemMessageDB systemMessageDB : yanZhengList) {//筛选出时间最新的一条
                            if (systemMessageDB.getUnreadTag() == 0) {
                                friendMsgUnread++;
                            }
                            if (systemMessageDB.getTime() - yanZhengime >= 0) {
                                friendMsg = systemMessageDB;
                                yanZhengime = systemMessageDB.getTime();
                            }
                        }

                        if (friendMsg != null) {//插入验证消息
                            MyRecentContact recentContact = new MyRecentContact();
                            recentContact.setFromNick(friendMsg.getTitle());
                            recentContact.setContent(friendMsg.getNote());
                            recentContact.setRecentMessageId(String.valueOf(friendMsg.getMessageId()));
                            recentContact.setTime(friendMsg.getTime());
                            recentContact.setSessionTypeEnum(SessionTypeEnum.System);
                            recentContact.setUnreadCount(friendMsgUnread);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put(Constants.KEY_MESSAGE_TYPE, Constants.MESSAGE_TYPE_YAN_ZHENG);
                            recentContact.setExtension(map);
                            myRecentContacts.add(recentContact);
                        }


/**********************************************  订单 ********************************************************/

                        if (orderList != null) {
                            for (SystemMessageDB systemMessageDB : orderList) {
                                if (systemMessageDB.getUnreadTag() == 0) {
                                    orderMsgUnread++;
                                }
                                if (systemMessageDB.getTime() - orderTime >= 0) {
                                    orderMsg = systemMessageDB;
                                    orderTime = systemMessageDB.getTime();
                                }
                            }
                        }
                        if (orderMsg != null) {//插入订单消息
                            MyRecentContact recentContact = new MyRecentContact();
                            recentContact.setFromNick(orderMsg.getTitle());
                            recentContact.setContent(orderMsg.getNote());
                            recentContact.setRecentMessageId(String.valueOf(orderMsg.getMessageId()));
                            recentContact.setTime(orderMsg.getTime());
                            recentContact.setSessionTypeEnum(SessionTypeEnum.System);
                            recentContact.setUnreadCount(orderMsgUnread);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put(Constants.KEY_MESSAGE_TYPE, Constants.MESSAGE_TYPE_ORDER);
                            recentContact.setExtension(map);
                            myRecentContacts.add(recentContact);
                        }

                        /**********************************************  金豆 ********************************************************/

                        if (moneyList != null) {
                            for (SystemMessageDB systemMessageDB : moneyList) {
                                if (systemMessageDB.getUnreadTag() == 0) {
                                    moneyMsgUnread++;
                                }
                                if (systemMessageDB.getTime() - moneyTime >= 0) {
                                    moneyMsg = systemMessageDB;
                                    moneyTime = systemMessageDB.getTime();
                                }
                            }
                        }
                        if (moneyMsg != null) {//插入金豆消息
                            MyRecentContact recentContact = new MyRecentContact();
                            recentContact.setFromNick(moneyMsg.getTitle());
                            recentContact.setContent(moneyMsg.getNote());
                            recentContact.setRecentMessageId(String.valueOf(moneyMsg.getMessageId()));
                            recentContact.setTime(moneyMsg.getTime());
                            recentContact.setSessionTypeEnum(SessionTypeEnum.System);
                            recentContact.setUnreadCount(moneyMsgUnread);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put(Constants.KEY_MESSAGE_TYPE, Constants.MESSAGE_TYPE_JIN_DOU);
                            recentContact.setExtension(map);
                            myRecentContacts.add(recentContact);
                        }

                        /**********************************************  推荐 ********************************************************/
                        List<SystemMessageDB> tuiJianList = new ArrayList<>();
                        if (poolList != null) {
                            tuiJianList.addAll(poolList);
                        }

                        if (tipList != null) {
                            tuiJianList.addAll(tipList);
                        }

                        for (SystemMessageDB systemMessageDB : tuiJianList) {//筛选出时间最新的一条
                            if (systemMessageDB.getUnreadTag() == 0) {
                                tuiJianMsgUnread++;
                            }
                            if (systemMessageDB.getTime() - tuiJianTime >= 0) {
                                tuiJianMsg = systemMessageDB;
                                tuiJianTime = systemMessageDB.getTime();
                            }
                        }

                        if (tuiJianMsg != null) {//插入推荐消息
                            MyRecentContact recentContact = new MyRecentContact();
                            recentContact.setFromNick(tuiJianMsg.getTitle());
                            recentContact.setContent(tuiJianMsg.getNote());
                            recentContact.setRecentMessageId(String.valueOf(tuiJianMsg.getMessageId()));
                            recentContact.setTime(tuiJianMsg.getTime());
                            recentContact.setSessionTypeEnum(SessionTypeEnum.System);
                            recentContact.setUnreadCount(tuiJianMsgUnread);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put(Constants.KEY_MESSAGE_TYPE, Constants.MESSAGE_TYPE_JING_PIN);
                            recentContact.setExtension(map);
                            myRecentContacts.add(recentContact);
                        }

                        /**********************************************  余额 ********************************************************/

                        if (moneyList != null) {
                            for (SystemMessageDB systemMessageDB : balanceList) {
                                if (systemMessageDB.getUnreadTag() == 0) {
                                    balanceMsgUnread++;
                                }
                                if (systemMessageDB.getTime() - balanceTime >= 0) {
                                    balanceMsg = systemMessageDB;
                                    balanceTime = systemMessageDB.getTime();
                                }
                            }
                        }
                        if (balanceMsg != null) {//插入金豆消息
                            MyRecentContact recentContact = new MyRecentContact();
                            recentContact.setFromNick(balanceMsg.getTitle());
                            recentContact.setContent(balanceMsg.getNote());
                            recentContact.setRecentMessageId(String.valueOf(balanceMsg.getMessageId()));
                            recentContact.setTime(balanceMsg.getTime());
                            recentContact.setSessionTypeEnum(SessionTypeEnum.System);
                            recentContact.setUnreadCount(balanceMsgUnread);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put(Constants.KEY_MESSAGE_TYPE, Constants.MESSAGE_TYPE_BALANCE);
                            recentContact.setExtension(map);
                            myRecentContacts.add(recentContact);
                        }

                   /*     *//**********************************************  推荐好友 ********************************************************/

                        if (recommendFriendList != null) {
                            for (SystemMessageDB systemMessageDB : recommendFriendList) {
                                if (systemMessageDB.getUnreadTag() == 0) {
                                    recommendFriendMsgUnread++;
                                }
                                if (systemMessageDB.getTime() - recommendFriendTime >= 0) {
                                    recommendFriendMsg = systemMessageDB;
                                    recommendFriendTime = systemMessageDB.getTime();
                                }
                            }
                        }
                        if (recommendFriendMsg != null) {//推荐好友
                            MyRecentContact recentContact = new MyRecentContact();
                            recentContact.setFromNick(recommendFriendMsg.getTitle());
                            recentContact.setContent(recommendFriendMsg.getNote());
//                            recentContact.setRecentMessageId(String.valueOf(recommendFriendMsg.getMessageId()));
                            recentContact.setTime(recommendFriendMsg.getTime());
                            recentContact.setSessionTypeEnum(SessionTypeEnum.System);
                            recentContact.setUnreadCount(recommendFriendMsgUnread);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put(Constants.KEY_MESSAGE_TYPE, Constants.MESSAGE_TYPE_RECOMMEND_FRIEND);
                            recentContact.setExtension(map);
                            myRecentContacts.add(recentContact);
                        }


                        loadedRecents = myRecentContacts;
                        // 初次加载，更新离线的消息中是否有@我的消息
                        for (RecentContact loadedRecent : loadedRecents) {
                            if (loadedRecent.getSessionType() == SessionTypeEnum.Team) {
                                updateOfflineContactAited(loadedRecent);
                            }
                        }
                        // 此处如果是界面刚初始化，为了防止界面卡顿，可先在后台把需要显示的用户资料和群组资料在后台加载好，然后再刷新界面
                        //
                        msgLoaded = true;
                        if (isAdded()) {
                            onRecentContactsLoaded();
                        }
                    }
                });


                //读取系统推送的接口


            }
        }, delay ? 250 : 0);
    }

    private void onRecentContactsLoaded() {
        items.clear();
        if (loadedRecents != null) {
            items.addAll(loadedRecents);
            loadedRecents = null;
        }
        refreshMessages(true);

        if (callback != null) {
            callback.onRecentContactsLoaded();
        }
    }

    private void refreshMessages(boolean unreadChanged) {
        requestMessages(true);
        sortRecentContacts(items);
        notifyDataSetChanged();

        if (unreadChanged) {

            // 方式一：累加每个最近联系人的未读（快）
            /*
            int unreadNum = 0;
            for (RecentContact r : items) {
                unreadNum += r.getUnreadCount();
            }
            */

            // 方式二：直接从SDK读取（相对慢）
//获取最近列表未读数
            int unreadNum = 0;
            for (RecentContact r : items) {
                unreadNum += r.getUnreadCount();
            }
//            int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
//            long count = GreenDaoManager.getInstance().getmDaoSession().getSystemMessageDBDao().queryBuilder().where(SystemMessageDBDao.Properties.UnreadTag.eq(0)).count();
            if (callback != null) {
                callback.onUnreadCountChange(unreadNum);
            }
        }
    }

    /**
     * **************************** 排序 ***********************************
     */
    private void sortRecentContacts(List<RecentContact> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }

    private static Comparator<RecentContact> comp = new Comparator<RecentContact>() {

        @Override
        public int compare(RecentContact o1, RecentContact o2) {
            // 先比较置顶tag
            long sticky = (o1.getTag() & RECENT_TAG_STICKY) - (o2.getTag() & RECENT_TAG_STICKY);
            if (sticky != 0) {
                return sticky > 0 ? -1 : 1;
            } else {
                long time = o1.getTime() - o2.getTime();
                return time == 0 ? 0 : (time > 0 ? -1 : 1);
            }
        }
    };

    /**
     * ********************** 收消息，处理状态变化 ************************
     */
    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeReceiveMessage(messageReceiverObserver, register);
        service.observeRecentContact(messageObserver, register);
        service.observeMsgStatus(statusObserver, register);
        service.observeRecentContactDeleted(deleteObserver, register);

        registerTeamUpdateObserver(register);
        registerTeamMemberUpdateObserver(register);
        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
    }

    /**
     * 注册群信息&群成员更新监听
     */
    private void registerTeamUpdateObserver(boolean register) {
        if (register) {
            TeamDataCache.getInstance().registerTeamDataChangedObserver(teamDataChangedObserver);
        } else {
            TeamDataCache.getInstance().unregisterTeamDataChangedObserver(teamDataChangedObserver);
        }
    }

    private void registerTeamMemberUpdateObserver(boolean register) {
        if (register) {
            TeamDataCache.getInstance().registerTeamMemberDataChangedObserver(teamMemberDataChangedObserver);
        } else {
            TeamDataCache.getInstance().unregisterTeamMemberDataChangedObserver(teamMemberDataChangedObserver);
        }
    }

    private void registerDropCompletedListener(boolean register) {
        if (register) {
            DropManager.getInstance().addDropCompletedListener(dropCompletedListener);
        } else {
            DropManager.getInstance().removeDropCompletedListener(dropCompletedListener);
        }
    }

    // 暂存消息，当RecentContact 监听回来时使用，结束后清掉
    private Map<String, Set<IMMessage>> cacheMessages = new HashMap<>();

    //监听在线消息中是否有@我
    private Observer<List<IMMessage>> messageReceiverObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> imMessages) {
            if (imMessages != null) {
                for (IMMessage imMessage : imMessages) {
                    if (!AitHelper.isAitMessage(imMessage)) {
                        continue;
                    }
                    Set<IMMessage> cacheMessageSet = cacheMessages.get(imMessage.getSessionId());
                    if (cacheMessageSet == null) {
                        cacheMessageSet = new HashSet<>();
                        cacheMessages.put(imMessage.getSessionId(), cacheMessageSet);
                    }
                    cacheMessageSet.add(imMessage);
                }


                for (IMMessage imMessage : imMessages) {
                    updataFriendInfo(imMessage);
                }
            }
        }
    };

    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> recentContacts) {
            if (recentContacts == null) {
                return;
            }
           /* List<RecentContact> contacts = new ArrayList<>();//筛选掉不是好友的最近联系人
            for (RecentContact recentContact : recentContacts) {
                MyFriendEntity friend = FriendDataCache.getInstance().getFriendByAccount(recentContact.getContactId());
                if (friend != null) {
                    String fNickName = friend.getFNickName();
                    String fAvatar = friend.getFPhoto();
                    Map<String, Object> data = new HashMap<>();
                    data.put("avatar", fAvatar);
                    data.put("nickName", fNickName);
                    recentContact.setExtension(data);
                    contacts.add(recentContact);
                }
            }
*/
            if (!DropManager.getInstance().isTouchable()) {
                // 正在拖拽红点，缓存数据
                for (RecentContact r : recentContacts) {
                    cached.put(r.getContactId(), r);
                }

                return;
            }

            onRecentContactChanged(recentContacts);
        }
    };

    private void onRecentContactChanged(List<RecentContact> recentContacts) {
        int index;
        for (RecentContact r : recentContacts) {
            index = -1;
            for (int i = 0; i < items.size(); i++) {
                if (r.getContactId().equals(items.get(i).getContactId())
                        && r.getSessionType() == (items.get(i).getSessionType())) {
                    index = i;
                    break;
                }
            }

            if (index >= 0) {
                items.remove(index);
            }

            items.add(r);
            if (r.getSessionType() == SessionTypeEnum.Team && cacheMessages.get(r.getContactId()) != null) {
                AitHelper.setRecentContactAited(r, cacheMessages.get(r.getContactId()));
            }
        }

        cacheMessages.clear();

        refreshMessages(true);
    }

    DropCover.IDropCompletedListener dropCompletedListener = new DropCover.IDropCompletedListener() {
        @Override
        public void onCompleted(Object id, boolean explosive) {
            if (cached != null && !cached.isEmpty()) {
                // 红点爆裂，已经要清除未读，不需要再刷cached
                if (explosive) {
                    if (id instanceof RecentContact) {
                        RecentContact r = (RecentContact) id;
                        cached.remove(r.getContactId());
                    } else if (id instanceof String && ((String) id).contentEquals("0")) {
                        cached.clear();
                    }
                }

                // 刷cached
                if (!cached.isEmpty()) {
                    List<RecentContact> recentContacts = new ArrayList<>(cached.size());
                    recentContacts.addAll(cached.values());
                    cached.clear();

                    onRecentContactChanged(recentContacts);
                }
            }
        }
    };

    Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage message) {
            int index = getItemIndex(message.getUuid());
            if (index >= 0 && index < items.size()) {
                RecentContact item = items.get(index);
                item.setMsgStatus(message.getStatus());
                refreshViewHolderByIndex(index);
            }
        }
    };

    Observer<RecentContact> deleteObserver = new Observer<RecentContact>() {
        @Override
        public void onEvent(RecentContact recentContact) {
            if (recentContact != null) {

                for (RecentContact item : items) {
                    if (TextUtils.equals(item.getContactId(), recentContact.getContactId())
                            && item.getSessionType() == recentContact.getSessionType()) {
                        items.remove(item);
                        refreshMessages(true);
                        break;
                    }
                }
            } else {
                items.clear();
                refreshMessages(true);
            }
        }
    };

    TeamDataCache.TeamDataChangedObserver teamDataChangedObserver = new TeamDataCache.TeamDataChangedObserver() {

        @Override
        public void onUpdateTeams(List<Team> teams) {
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeam(Team team) {

        }
    };

    TeamDataCache.TeamMemberDataChangedObserver teamMemberDataChangedObserver = new TeamDataCache.TeamMemberDataChangedObserver() {
        @Override
        public void onUpdateTeamMember(List<TeamMember> members) {
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeamMember(TeamMember member) {

        }
    };

    private int getItemIndex(String uuid) {
        for (int i = 0; i < items.size(); i++) {
            RecentContact item = items.get(i);
            if (TextUtils.equals(item.getRecentMessageId(), uuid)) {
                return i;
            }
        }

        return -1;
    }

    protected void refreshViewHolderByIndex(final int index) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                adapter.notifyItemChanged(index);
            }
        });
    }

    public void setCallback(RecentContactsCallback callback) {
        this.callback = callback;
    }

    private void registerUserInfoObserver() {
        if (userInfoObserver == null) {
            userInfoObserver = new UserInfoObservable.UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    refreshMessages(false);
                }
            };
        }

        UserInfoHelper.registerObserver(userInfoObserver);
    }

    private void unregisterUserInfoObserver() {
        if (userInfoObserver != null) {
            UserInfoHelper.unregisterObserver(userInfoObserver);
        }
    }

    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            refreshMessages(false);
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            refreshMessages(false);
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            refreshMessages(false);
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            refreshMessages(false);
        }
    };

    private void updateOfflineContactAited(final RecentContact recentContact) {
        if (recentContact == null || recentContact.getSessionType() != SessionTypeEnum.Team
                || recentContact.getUnreadCount() <= 0) {
            return;
        }

        // 锚点
        List<String> uuid = new ArrayList<>(1);
        uuid.add(recentContact.getRecentMessageId());

        List<IMMessage> messages = NIMClient.getService(MsgService.class).queryMessageListByUuidBlock(uuid);

        if (messages == null || messages.size() < 1) {
            return;
        }
        final IMMessage anchor = messages.get(0);

        // 查未读消息
        NIMClient.getService(MsgService.class).queryMessageListEx(anchor, QueryDirectionEnum.QUERY_OLD,
                recentContact.getUnreadCount() - 1, false).setCallback(new RequestCallbackWrapper<List<IMMessage>>() {

            @Override
            public void onResult(int code, List<IMMessage> result, Throwable exception) {
                if (code == ResponseCode.RES_SUCCESS && result != null) {
                    result.add(0, anchor);
                    Set<IMMessage> messages = null;
                    // 过滤存在的@我的消息
                    for (IMMessage msg : result) {
                        if (AitHelper.isAitMessage(msg)) {
                            if (messages == null) {
                                messages = new HashSet<>();
                            }
                            messages.add(msg);
                        }
                    }

                    // 更新并展示
                    if (messages != null) {
                        AitHelper.setRecentContactAited(recentContact, messages);
                        notifyDataSetChanged();
                    }
                }
            }
        });

    }

    private void updataFriendInfo(IMMessage message) {
        Map<String, Object> remoteExtension = message.getRemoteExtension();
        if (remoteExtension != null) {
            try {
                String avatar = (String) remoteExtension.get(Constants.KEY_AVATAR);
                String nickName = (String) remoteExtension.get(Constants.KEY_NICK_NAME);
                long uid = (long) remoteExtension.get(Constants.KEY_UID);

                MyFriendEntity friendEntity = FriendDataCache.getInstance().getFriendByAccount(message.getFromAccount());
                if (friendEntity != null) {
                    if (!TextUtils.equals(friendEntity.getFNickName(), avatar) || !TextUtils.equals(friendEntity.getFPhoto(), avatar)) {
                        FriendDB friendDB = new FriendDB();
                        friendDB.setFNeteaseAccid(friendEntity.getfNeteaseAccid());
                        friendDB.setFNeteaseToken(friendEntity.getfNeteaseToken());
                        friendDB.setFNickName(nickName);
                        friendDB.setFPhoto(avatar);
                        friendDB.setFUid(friendEntity.getFUid());
                        friendDB.setFUserDesc(friendEntity.getfUserDesc());
                        friendDB.setNote(friendEntity.getNote());
                        friendDB.setRelationStatus(friendEntity.getRelationStatus());
                        FriendDBDao friendDBDao = GreenDaoManager.getInstance().getmDaoSession().getFriendDBDao();
                        friendDBDao.update(friendDB);

                    }
                }

            } catch (Exception e) {

            }


        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //当收到系统通知时 请求最新的消息列表 保存到本地
    public void onSystemMessageEvent(SystemMessageEvent event) {
        String messageListSearchStart = MyUserCache.getMessageListSearchStart();
        Map<String, Object> map = new HashMap<>();
        System.out.println("传入时间：" + messageListSearchStart);

        map.put(RequestParams.search_start, messageListSearchStart);
        Observable<BaseResponse<RecentMessageListEntity>> observable = HttpUtil.getInstance().sApi.getRecentMessageList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<RecentMessageListEntity>(getContext(), false) {
            @Override
            protected void _onNext(RecentMessageListEntity recentMessageListEntity) {
                List<RecentMessageListEntity.ResultsBean> results = recentMessageListEntity.getResults();
                if (results != null && results.size() > 0) {
                    MyUserCache.saveMessageListSearchStart(recentMessageListEntity.getNextSearchStart());
                    setRecentToDB(results);
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    /**
     * 把拉去的最新的消息列表保存到本地
     * <p>
     * 保证了消息的单一
     *
     * @param results
     */
    private void setRecentToDB(List<RecentMessageListEntity.ResultsBean> results) {

        SystemMessageDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSystemMessageDBDao();
        for (RecentMessageListEntity.ResultsBean result : results) {
            long messageId = result.getMessageId();//验证messageid， 如果数据库中已存在就不插入

            SystemMessageDB unique = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.MessageId.eq(messageId)).build().unique();
            if (unique == null) {
                SystemMessageDB systemMessageDB = new SystemMessageDB();
                systemMessageDB.setTime(Long.parseLong(result.getCreateTimestamp()));//数据库的id改成messageId
                systemMessageDB.setNote(result.getNote());
                systemMessageDB.setType(result.getType());
                systemMessageDB.setCreateTime(result.getCreateTime());
                systemMessageDB.setCreateTimestamp(result.getCreateTimestamp());
                systemMessageDB.setMessageId(result.getMessageId());
                systemMessageDB.setStatus(result.getStatus());
                systemMessageDB.setTargetId(result.getTargetId());
                systemMessageDB.setTitle(result.getTitle());
                systemMessageDB.setUnreadTag(0);
                Gson gson = new Gson();
                String dataJson = gson.toJson(result.getData());
                systemMessageDB.setData(dataJson);
                try {
                    dbDao.insert(systemMessageDB);
                } catch (Exception e) {

                }
            }

        }
        msgLoaded = false;
        refreshMessages(true);
    }

/*1， 这个界面初始化时， 读取数据库的消息。 出入到最近联系列表。
* 2， 当收到系统消息时， 然后再重新读取 消息接口 插入新的消息。 刷新最近联系人列表（每次刷新列表都要重新设置底下的tab未读数和具体条目的未读数）
*       这样保证了最近联系人列表数据和未读数的完整
* 3， 当收到系统消息时， 筛选系统消息的type 给相应界面发送eventbus消息。 提示根据数据库刷新页面
* 4， 当每个type的消息条目被点击时， 检查是否时未读的条目。 如果是未读的条目， 就修改数据库中相应消息的未读状态，
*       然后发送eventbus消息提示主界面更新未读数（待确定）， 同时修改当前页面集合的该条数据并notify
 * 这样保证了 每点击一个未读的条目， 他的状态都会更新。 主界面的总未读数也会更新
* 5， 主界面使用eventbus接收消息， 做相应的未读数减法
*
* 问题： 如果 消息接口 每次拉去的都是全部数据。 当用户清除数据时 重新拉取全部数据时， 所有的数据都会变成未读
*
*  请求接口： 当收到系统通知 然后记录"时间参数"， 然后请求网络， 成功之后再把记录的时间更新到本地。 （时间保存到本地要和用户建立联系）
* */

//todo 当这个fragment跳转页面回来时 刷新列表


    @Override
    public void onResume() {
        super.onResume();
        msgLoaded = false;
        refreshMessages(true);
    }
}
