package com.netease.nim.uikit.cache;

import android.os.Handler;
import android.text.TextUtils;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.UIKitLogTag;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.gen.FriendDBDao;
import com.netease.nim.uikit.green_dao.FriendDB;
import com.netease.nim.uikit.green_dao.GreenDaoManager;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.MyFriendEntity;
import com.netease.nim.uikit.model.MyFriendsListEntity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.FriendServiceObserve;
import com.netease.nimlib.sdk.friend.model.BlackListChangedNotify;
import com.netease.nimlib.sdk.friend.model.Friend;
import com.netease.nimlib.sdk.friend.model.FriendChangedNotify;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import rx.Observable;
import rx.Subscriber;


/**
 * 好友关系缓存
 * 注意：获取通讯录列表即是根据Friend列表帐号，去取对应的UserInfo
 * <p/>
 * Created by huangjun on 2015/9/14.
 */
public class FriendDataCache {

    public static FriendDataCache getInstance() {
        return InstanceHolder.instance;
    }


    public FriendDataCache() {

    }

    /**
     * 属性
     */
    private Set<String> friendAccountSet = new CopyOnWriteArraySet<>();

//    private Map<String, MyFriendEntity> friendMap = new ConcurrentHashMap<>();

    private List<FriendDataChangedObserver> friendObservers = new ArrayList<>();

    /**
     * 初始化&清理
     */

    public void clear() {
        clearFriendCache();
    }

    private void getFriends() {
        HashMap<String, Object> map = new HashMap<>();
        Observable<BaseResponse<MyFriendsListEntity>> observable = HttpUtil.getInstance().sApi.getMyFriends(RequestBodyUtils.build(map));

        HttpUtil.getInstance().execute(observable, new Subscriber<MyFriendsListEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MyFriendsListEntity myFriendsListEntity) {
                if (myFriendsListEntity != null) {
                    doBuildCache(myFriendsListEntity.getFriends());
                }

            }
        });
    }

    private void doBuildCache(List<MyFriendEntity> myFriends) {
        ArrayList<String> accountList = new ArrayList<>();
//        friendMap.clear();
        for (MyFriendEntity f : myFriends) {
//            friendMap.put(f.getfNeteaseAccid(), f);//缓存每一个好友对象
            accountList.add(f.getfNeteaseAccid());

        }
        cacheFriendToDB(myFriends);
        // 确定缓存
        friendAccountSet.clear();
        friendAccountSet.addAll(accountList);//缓存每一个好友账号

        LogUtil.i(UIKitLogTag.FRIEND_CACHE, "build FriendDataCache completed, friends count = " + friendAccountSet.size());
    }

    private void cacheFriendToDB(List<MyFriendEntity> myFriends) {
        FriendDBDao friendDBDao = GreenDaoManager.getInstance().getmDaoSession().getFriendDBDao();
        friendDBDao.deleteAll();
        for (MyFriendEntity f : myFriends) {
                FriendDB db = new FriendDB();
                db.setFNeteaseAccid(f.getfNeteaseAccid());
                db.setFNeteaseToken(f.getfNeteaseToken());
                db.setFNickName(f.getFNickName());
                db.setFPhoto(f.getFPhoto());
                db.setFUserDesc(f.getfUserDesc());
                db.setNote(f.getNote());
                db.setRelationStatus(f.getRelationStatus());
                db.setFUid(f.getFUid());
                friendDBDao.insert(db);
        }
    }


    public void buildCache() {
        // 获取我所有的好友关系
        getFriends();

    }

    private void clearFriendCache() {
        friendAccountSet.clear();
//        friendMap.clear();

    }

    /**
     * ****************************** 好友查询接口 ******************************
     */

    public List<String> getMyFriendAccounts() {
        List<String> accounts = new ArrayList<>(friendAccountSet.size());
        accounts.addAll(friendAccountSet);

        return accounts;
    }

    public int getMyFriendCounts() {
        return friendAccountSet.size();
    }


    public MyFriendEntity getFriendByAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            return null;
        }
        FriendDBDao friendDBDao = GreenDaoManager.getInstance().getmDaoSession().getFriendDBDao();
        FriendDB friend = friendDBDao.queryBuilder().where(FriendDBDao.Properties.FNeteaseAccid.eq(account)).build().unique();
        if (friend != null) {
            MyFriendEntity entity = new MyFriendEntity();
            entity.setfNeteaseAccid(friend.getFNeteaseAccid());
            entity.setfNeteaseToken(friend.getFNeteaseToken());
            entity.setFNickName(friend.getFNickName());
            entity.setFPhoto(friend.getFPhoto());
            entity.setFUid(friend.getFUid());
            entity.setfUserDesc(friend.getFUserDesc());
            entity.setNote(friend.getNote());
            entity.setRelationStatus(friend.getRelationStatus());
            return entity;
        }
        return null;
    }

    public List<MyFriendEntity> queryFriends() {
        FriendDBDao friendDBDao = GreenDaoManager.getInstance().getmDaoSession().getFriendDBDao();
        List<FriendDB> friendDBs = friendDBDao.loadAll();
        List<MyFriendEntity> myFriendEntities = new ArrayList<>();
        if (friendDBs != null && friendDBs.size() > 0) {
            for (FriendDB friendDB : friendDBs) {
                MyFriendEntity entity = new MyFriendEntity();
                entity.setfNeteaseAccid(friendDB.getFNeteaseAccid());
                entity.setfNeteaseToken(friendDB.getFNeteaseToken());
                entity.setFNickName(friendDB.getFNickName());
                entity.setFPhoto(friendDB.getFPhoto());
                entity.setFUid(friendDB.getFUid());
                entity.setfUserDesc(friendDB.getFUserDesc());
                entity.setNote(friendDB.getNote());
                entity.setRelationStatus(friendDB.getRelationStatus());
                myFriendEntities.add(entity);
            }
        }
        Logger.d("本地通讯录数据库缓存：" + friendDBs.size());

        return myFriendEntities;
    }

    public boolean isMyFriend(String account) {
        return friendAccountSet.contains(account);
    }


    public void notifyContacts(List<String> accounts) {
        // 通知好友关系更新
        if (!accounts.isEmpty()) {
            for (FriendDataChangedObserver o : friendObservers) {
                o.onAddedOrUpdatedFriends(accounts);
            }
        }
    }

    /**
     * ****************************** 缓存好友关系变更监听&通知 ******************************
     */

    /**
     * 缓存监听SDK
     */
    public void registerObservers(boolean register) {
        NIMClient.getService(FriendServiceObserve.class).observeFriendChangedNotify(friendChangedNotifyObserver, register);
        NIMClient.getService(FriendServiceObserve.class).observeBlackListChangedNotify(blackListChangedNotifyObserver, register);
    }

    /**
     * APP监听缓存
     */
    public void registerFriendDataChangedObserver(FriendDataChangedObserver o, boolean register) {
        if (o == null) {
            return;
        }

        if (register) {
            if (!friendObservers.contains(o)) {
                friendObservers.add(o);
            }
        } else {
            friendObservers.remove(o);
        }
    }

    public interface FriendDataChangedObserver {
        void onAddedOrUpdatedFriends(List<String> accounts);

        void onDeletedFriends(List<String> accounts);

        void onAddUserToBlackList(List<String> account);

        void onRemoveUserFromBlackList(List<String> account);
    }

    /**
     * 监听好友关系变化
     */
    private Observer<FriendChangedNotify> friendChangedNotifyObserver = new Observer<FriendChangedNotify>() {
        @Override
        public void onEvent(final FriendChangedNotify friendChangedNotify) {
            new Handler()
                    .postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //当好友有变化时  重新获取服务器好友
                            HashMap<String, Object> map = new HashMap<>();

                            Observable<BaseResponse<MyFriendsListEntity>> observable = HttpUtil.getInstance().sApi.getMyFriends(RequestBodyUtils.build(map));
                            HttpUtil.getInstance().execute(observable, new ProgressSubscriber<MyFriendsListEntity>(NimUIKit.getContext(), false) {


                                @Override
                                protected void _onNext(MyFriendsListEntity myFriendsListEntity) {
                                    //重新设置缓存
                                    if (myFriendsListEntity != null) {
                                        doBuildCache(myFriendsListEntity.getFriends());
                                    }

                                    List<Friend> addedOrUpdatedFriends = friendChangedNotify.getAddedOrUpdatedFriends();
                                    List<String> friendAccounts = new ArrayList<>(addedOrUpdatedFriends.size());//我的好友集合 包括黑名单的
                                    List<String> myFriendAccounts = new ArrayList<>(addedOrUpdatedFriends.size());//我的好友集合 不包括黑名单的

                                    // 如果在黑名单中，那么不加到好友列表中
                                    String account;
                                    for (Friend f : addedOrUpdatedFriends) {
                                        account = f.getAccount();
                                        friendAccounts.add(account);

                                        if (NIMClient.getService(FriendService.class).isInBlackList(account)) {
                                            continue;
                                        }

                                        myFriendAccounts.add(account);
                                    }
                                    // 通知好友关系更新
                                    if (!friendAccounts.isEmpty()) {
                                        for (FriendDataChangedObserver o : friendObservers) {
                                            o.onAddedOrUpdatedFriends(friendAccounts);
                                        }
                                    }


                                    // 处理被删除的好友关系
                                    List<String> deletedFriendAccounts = friendChangedNotify.getDeletedFriends();
                                    if (!deletedFriendAccounts.isEmpty()) {
                                        // notify
                                        for (FriendDataChangedObserver o : friendObservers) {
                                            o.onDeletedFriends(deletedFriendAccounts);
                                        }
                                    }
                                }

                                @Override
                                protected void _onError(String message) {

                                }
                            });

                        }
                    }, 2000);

        }
    };

    private void deleteFriendsDB(List<String> accounts) {
        FriendDBDao friendDBDao = GreenDaoManager.getInstance().getmDaoSession().getFriendDBDao();
        for (String account : accounts) {
            friendDBDao.queryBuilder().where(FriendDBDao.Properties.FNeteaseAccid.eq(account)).buildDelete().executeDeleteWithoutDetachingEntities();
        }
    }

    /**
     * 监听黑名单变化(决定是否加入或者移出好友列表)
     */
    private Observer<BlackListChangedNotify> blackListChangedNotifyObserver = new Observer<BlackListChangedNotify>() {
        @Override
        public void onEvent(BlackListChangedNotify blackListChangedNotify) {
            List<String> addedAccounts = blackListChangedNotify.getAddedAccounts();
            List<String> removedAccounts = blackListChangedNotify.getRemovedAccounts();

            if (!addedAccounts.isEmpty()) {
                // 拉黑，即从好友名单中移除
                friendAccountSet.removeAll(addedAccounts);

                // log
                DataCacheManager.Log(addedAccounts, "on add users to black list", UIKitLogTag.FRIEND_CACHE);

                // notify
                for (FriendDataChangedObserver o : friendObservers) {
                    o.onAddUserToBlackList(addedAccounts);
                }

                // 拉黑，要从最近联系人列表中删除该好友
                for (String account : addedAccounts) {
                    NIMClient.getService(MsgService.class).deleteRecentContact2(account, SessionTypeEnum.P2P);
                }
            }

            if (!removedAccounts.isEmpty()) {
                // 移出黑名单，判断是否加入好友名单
                for (String account : removedAccounts) {
                    if (NIMClient.getService(FriendService.class).isMyFriend(account)) {
                        friendAccountSet.add(account);
                    }
                }

                // log
                DataCacheManager.Log(removedAccounts, "on remove users from black list", UIKitLogTag.FRIEND_CACHE);

                // 通知观察者
                for (FriendDataChangedObserver o : friendObservers) {
                    o.onRemoveUserFromBlackList(removedAccounts);
                }
            }
        }
    };

    /**
     * ************************************ 单例 **********************************************
     */

    static class InstanceHolder {
        final static FriendDataCache instance = new FriendDataCache();
    }
}
