package com.drops.waterdrop.ui.session.presenter;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.drops.waterdrop.im.reminder.ReminderManager;
import com.drops.waterdrop.im.reminder.SystemMessageUnreadManager;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.session.activity.AddFriendV2Activity;
import com.drops.waterdrop.ui.session.view.IContactListView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.UIKitLogTag;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialog;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.ui.drop.DropCover;
import com.netease.nim.uikit.common.ui.drop.DropManager;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.guideview.Component;
import com.netease.nim.uikit.guideview.Guide;
import com.netease.nim.uikit.guideview.GuideBuilder;
import com.netease.nim.uikit.guideview.component.BtnButtomComponent2;
import com.netease.nim.uikit.guideview.component.BtnComponent;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.MyFriendEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/5/11.
 */

public class ContactListPresenter extends BasePresenter<IContactListView> {

    private ReloadFrequencyControl reloadControl = new ReloadFrequencyControl();
    private List<MyFriendEntity> mFriends;


    public ContactListPresenter(BaseActivity context) {
        super(context);
    }

    private void getFriends() {
     /*   List<String> myFriendAccounts = FriendDataCache.getInstance().getMyFriendAccounts();
        if (myFriendAccounts != null && myFriendAccounts.size() > 0) {
            ArrayList<MyFriendEntity> myFriendEntities = new ArrayList<>();
            for (String myFriendAccount : myFriendAccounts) {
                MyFriendEntity entity = FriendDataCache.getInstance().getFriendByAccount(myFriendAccount);
                myFriendEntities.add(entity);
                System.out.println("好友：" + entity.getFNickName());
            }
            getView().onGetFriendsSucceed(myFriendEntities);
        }*/

        List<MyFriendEntity> myFriendEntities = FriendDataCache.getInstance().queryFriends();
        if (myFriendEntities != null) {
            getView().onGetFriendsSucceed(myFriendEntities);
        }

       /* BaseRequestBody body = new BaseRequestBody();
        body.setUser_token(MyUserCache.getUserToken());
        body.setUid(MyUserCache.getUserUid());
        Observable<BaseResponse<MyFriendsListEntity>> observable = HttpUtil.getInstance().sApi.getMyFriends(body);
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<MyFriendsListEntity>(mContext, "正在加载...") {


            @Override
            protected void _onNext(MyFriendsListEntity myFriendsListEntity) {

                mFriends = myFriendsListEntity.getFriends();
                getView().onGetFriendsSucceed(mFriends);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort("加载失败:" + message);
            }
        });*/
    }

    private void removeFriend(MyFriendEntity entity) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.f_uid, entity.getFUid());
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.removeFriends(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, "删除中...") {
            @Override
            protected void _onNext(Object o) {
                ToastUtil.showShort("删除成功");
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort("删除失败");

            }
        });
    }

    public void onRemoveFriend(final MyFriendEntity entity) {
        EasyAlertDialog dialog = EasyAlertDialogHelper.createOkCancelDiolag(mContext, mContext.getString(com.netease.nim.uikit.R.string.remove_friend),
                mContext.getString(com.netease.nim.uikit.R.string.remove_friend_tip), true,
                new EasyAlertDialogHelper.OnDialogActionListener() {

                    @Override
                    public void doCancelAction() {

                    }

                    @Override
                    public void doOkAction() {
                        removeFriend(entity);
                    }
                });
        Activity activity = (Activity) mContext;

        if (!activity.isFinishing()) {
            dialog.show();
        }

    }

    public void onDestroy() {
        registerObserver(false);
//        registerSystemMessageObservers(false);
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    /**
     * *********************************** 通讯录加载控制 *******************************
     */

    /**
     * 加载通讯录数据并刷新
     *
     * @param reload true则重新加载数据；false则判断当前数据源是否空，若空则重新加载，不空则不加载
     */
    public void reload(boolean reload) {

       /* if (!reloadControl.canDoReload(reload)) {*/
       /*     return;*/
       /* }*/

        getFriends();

        getView().initAdapter();
/*

        // 开始加载
        if (!load(reload)) {
            // 如果不需要加载，则直接当完成处理
            onReloadCompleted();
        }*/
    }

    private boolean load(boolean reload) {
        if (!reload && mFriends != null && !mFriends.isEmpty()) {
            return false;
        }
        LogUtil.i(UIKitLogTag.CONTACT, "contact load data");
        getFriends();

        return true;
    }

    private void onReloadCompleted() {
        if (reloadControl.continueDoReloadWhenCompleted()) {
            // 计划下次加载，稍有延迟
            if (mHandler == null) {
                mHandler = new Handler();
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean reloadParam = reloadControl.getReloadParam();
                    Log.i(UIKitLogTag.CONTACT, "continue reload " + reloadParam);
                    reloadControl.resetStatus();
                    reload(reloadParam);
                }
            }, 50);
        } else {
            // 本次加载完成
            reloadControl.resetStatus();
        }

        LogUtil.i(UIKitLogTag.CONTACT, "contact load completed");
    }


    /**
     * 通讯录加载频率控制
     */
    class ReloadFrequencyControl {
        boolean isReloading = false;
        boolean needReload = false;
        boolean reloadParam = false;

        boolean canDoReload(boolean param) {
            if (isReloading) {
                // 正在加载，那么计划加载完后重载
                needReload = true;
                if (param) {
                    // 如果加载过程中又有多次reload请求，多次参数只要有true，那么下次加载就是reload(true);
                    reloadParam = true;
                }
                LogUtil.i(UIKitLogTag.CONTACT, "pending reload task");

                return false;
            } else {
                // 如果当前空闲，那么立即开始加载
                isReloading = true;
                return true;
            }
        }

        boolean continueDoReloadWhenCompleted() {
            return needReload;
        }

        void resetStatus() {
            isReloading = false;
            needReload = false;
            reloadParam = false;
        }

        boolean getReloadParam() {
            return reloadParam;
        }
    }


    /**
     * *********************************** 用户资料、好友关系变更、登录数据同步完成观察者 *******************************
     */

    public void registerObserver(boolean register) {
        if (register) {
            UserInfoHelper.registerObserver(userInfoObserver);
        } else {
            UserInfoHelper.unregisterObserver(userInfoObserver);
        }

        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);

        LoginSyncDataStatusObserver.getInstance().observeSyncDataCompletedEvent(loginSyncCompletedObserver);
    }

    //好友变更监听
    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            reloadWhenDataChanged(accounts, "onAddedOrUpdatedFriends", true);
            Logger.d("通讯录更新：" + accounts);
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            reloadWhenDataChanged(accounts, "onDeletedFriends", true);
            Logger.d("通讯录好友删除：" + accounts);

        }

        @Override
        public void onAddUserToBlackList(List<String> accounts) {
            reloadWhenDataChanged(accounts, "onAddUserToBlackList", true);
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> accounts) {
            reloadWhenDataChanged(accounts, "onRemoveUserFromBlackList", true);
        }
    };

    private UserInfoObservable.UserInfoObserver userInfoObserver = new UserInfoObservable.UserInfoObserver() {
        @Override
        public void onUserInfoChanged(List<String> accounts) {

            reloadWhenDataChanged(accounts, "onUserInfoChanged", true, false); // 非好友资料变更，不用刷新界面
        }
    };

    private Handler mHandler;
    private Observer<Void> loginSyncCompletedObserver = new Observer<Void>() {
        @Override
        public void onEvent(Void aVoid) {
            if (mHandler == null) {
                mHandler = new Handler();
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    reloadWhenDataChanged(null, "onLoginSyncCompleted", false);
                }
            }, 50);
        }
    };

    private void reloadWhenDataChanged(List<String> accounts, String reason, boolean reload) {
        reloadWhenDataChanged(accounts, reason, reload, true);
    }

    private void reloadWhenDataChanged(List<String> accounts, String reason, boolean reload, boolean force) {
        if (accounts == null || accounts.isEmpty()) {
            return;
        }

        boolean needReload = false;
        if (!force) {
            // 非force：与通讯录无关的（非好友）变更通知，去掉
            for (String account : accounts) {
                if (FriendDataCache.getInstance().isMyFriend(account)) {
                    needReload = true;
                    break;
                }
            }
        } else {
            needReload = true;
        }

        if (!needReload) {
            Log.d(UIKitLogTag.CONTACT, "no need to reload contact");
            return;
        }

        // log
        StringBuilder sb = new StringBuilder();
        sb.append("ContactFragment received data changed as [" + reason + "] : ");
        if (accounts != null && !accounts.isEmpty()) {
            for (String account : accounts) {
                sb.append(account);
                sb.append(" ");
            }
            sb.append(", changed size=" + accounts.size());
        }
        Log.i(UIKitLogTag.CONTACT, sb.toString());

        // reload
        reload(reload);
    }

    /**********************  未读消息  **************************/


    /**
     * 注册/注销系统消息未读数变化
     *
     * @param register
     */
    public void registerSystemMessageObservers(boolean register) {
        NIMClient.getService(SystemMessageObserver.class).observeUnreadCountChange(sysMsgUnreadCountChangedObserver,
                register);
    }

    private Observer<Integer> sysMsgUnreadCountChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer unreadCount) {
            SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unreadCount);
            ReminderManager.getInstance().updateContactUnreadNum(unreadCount);
        }
    };

    /**
     * 查询系统消息未读数
     */
    public void requestSystemMessageUnreadCount() {
        int unread = NIMClient.getService(SystemMessageService.class).querySystemMessageUnreadCountBlock();
        ReminderManager.getInstance().updateContactUnreadNum(unread);

        getView().setUnreadCount(unread);
    }

    /**
     * 初始化未读红点动画
     *
     * @param unreadCover
     */
    public void initUnreadCover(DropCover unreadCover) {
        DropManager.getInstance().init(mContext, unreadCover,
                new DropCover.IDropCompletedListener() {
                    @Override
                    public void onCompleted(Object id, boolean explosive) {
                        if (id == null || !explosive) {
                            return;
                        }
                        if (((String) id).contentEquals("1")) {//清除系统未读消息
                            NIMClient.getService(SystemMessageService.class).resetSystemMessageUnreadCount();
                            LogUtil.i("PoolFragment", "clearAllSystemUnreadCount");
                        }
                    }
                }

        );
    }

    public void showGuideView(ImageView tagView, int containerId) {

        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(tagView)
                .setFullingViewId(containerId)
                .setAlpha(200)
                .setHighTargetCorner(0)
                .setAutoDismiss(false)
                .setHighTargetPadding(ScreenUtil.dip2px(3))
                .setHighTargetGraphStyle(Component.CIRCLE)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);

        BtnComponent component = new BtnComponent();
        builder.addComponent(component);
        builder.addComponent(new BtnButtomComponent2());
        final Guide guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(mContext);

        component.setListener(new BtnComponent.OnViewClickListener() {
            @Override
            public void onClickListener() {
                if (!FastClickUtil.isFastDoubleClick()) {
//                    AddFriendActivity.start(mContext);
                    AddFriendV2Activity.startActivity(mContext);
                }

                guide.dismiss();

            }
        });
    }

}
