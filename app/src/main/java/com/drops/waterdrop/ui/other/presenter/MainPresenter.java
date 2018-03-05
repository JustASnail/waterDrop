package com.drops.waterdrop.ui.other.presenter;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.AppCache;
import com.drops.waterdrop.app.preference.UserPreferences;
import com.drops.waterdrop.help.SessionHelper;
import com.drops.waterdrop.im.help.LogoutHelper;
import com.drops.waterdrop.im.reminder.ReminderItem;
import com.drops.waterdrop.im.reminder.ReminderManager;
import com.drops.waterdrop.im.reminder.SystemMessageUnreadManager;
import com.drops.waterdrop.im.tab.MainTab;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.other.activity.LoginActivity;
import com.drops.waterdrop.ui.other.activity.WelcomeActivity;
import com.drops.waterdrop.ui.other.view.IMainView;
import com.drops.waterdrop.util.sys.UIUtils;
import com.netease.nim.uikit.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.drop.DropCover;
import com.netease.nim.uikit.common.ui.drop.DropManager;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.mixpush.MixPushService;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

import static com.netease.nimlib.sdk.media.player.AudioPlayer.TAG;

/**
 * Created by dengxiaolei on 2017/4/24.
 */

public class MainPresenter extends BasePresenter<IMainView> implements ReminderManager.UnreadNumChangedCallback {

    private static final String EXTRA_APP_QUIT = "APP_QUIT";

    public MainPresenter(BaseActivity context) {
        super(context);
    }

    public void parseIntent(Intent intent) {
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            IMMessage message = (IMMessage) intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
            switch (message.getSessionType()) {
                case P2P:
                    SessionHelper.startP2PSession(mContext, message.getSessionId());
                    break;
                case Team:
                    SessionHelper.startTeamSession(mContext, message.getSessionId());

                    break;
                default:
                    break;
            }
        } else if (intent.hasExtra(EXTRA_APP_QUIT)) {
            onLogout();
            return;
        /*} else if (intent.hasExtra(AVChatActivity.INTENT_ACTION_AVCHAT)) {//聊天室暂时用不到
            if (AVChatProfile.getInstance().isAVChatting()) {
                Intent localIntent = new Intent();
                localIntent.setClass(this, AVChatActivity.class);
                startActivity(localIntent);
            }*/
        } else if (intent.hasExtra(Extras.EXTRA_JUMP_P2P)) {
            Intent data = intent.getParcelableExtra(Extras.EXTRA_DATA);
            String account = data.getStringExtra(Extras.EXTRA_ACCOUNT);
            if (!TextUtils.isEmpty(account)) {
                SessionHelper.startP2PSession(mContext, account);

            }
        }
    }

    /**
     * 同步数据
     */
    public void syncData() {
        // 等待同步数据完成
        boolean syncCompleted = LoginSyncDataStatusObserver.getInstance().observeSyncDataCompletedEvent(new Observer<Void>() {
            @Override
            public void onEvent(Void v) {

                syncPushNoDisturb(UserPreferences.getStatusConfig());

                DialogMaker.dismissProgressDialog();
            }
        });

        LogUtil.i(TAG, "数据是否同步完成 = " + syncCompleted);
        if (!syncCompleted) {
            DialogMaker.showProgressDialog(mContext, UIUtils.getString(R.string.prepare_data)).setCanceledOnTouchOutside(false);
        } else {
            syncPushNoDisturb(UserPreferences.getStatusConfig());
        }

    }

    /**
     * 若增加第三方推送免打扰（V3.2.0新增功能），则：
     * 1.添加下面逻辑使得 push 免打扰与先前的设置同步。
     * 2.设置界面{@link com.netease.nim.demo.main.activity.SettingsActivity} 以及
     * 免打扰设置界面{@link com.netease.nim.demo.main.activity.NoDisturbActivity} 也应添加 push 免打扰的逻辑
     * <p>
     * 注意：isPushDndValid 返回 false， 表示未设置过push 免打扰。
     */
    private void syncPushNoDisturb(StatusBarNotificationConfig staConfig) {

        boolean isNoDisbConfigExist = NIMClient.getService(MixPushService.class).isPushNoDisturbConfigExist();

        if (!isNoDisbConfigExist && staConfig.downTimeToggle) {
            NIMClient.getService(MixPushService.class).setPushNoDisturbConfig(staConfig.downTimeToggle,
                    staConfig.downTimeBegin, staConfig.downTimeEnd);
        }
    }

    /*
 消息提醒的场景
  */
    public void enableMsgNotification(boolean enable, int currentItem) {
        boolean msg = (currentItem != MainTab.RECENT_CONTACTS.tabIndex);
        if (enable | msg) {
            /**
             * 消息提醒的场景
             *
             * @param account,    聊天对象帐号，或者以下两个值：
             *                    {@link #MSG_CHATTING_ACCOUNT_ALL} 目前没有与任何人对话，但能看到消息提醒（比如在消息列表界面），不需要在状态栏做消息通知
             *                    {@link #MSG_CHATTING_ACCOUNT_NONE} 目前没有与任何人对话，需要状态栏消息通知
             */
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
        } else {
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);
        }
    }




    /*------------------------------------注册监听----------------------------------------*/

    /**
     * 注册相关监听
     */
    public void registerObservers() {
        registerMsgUnreadInfoObserver(true);;//注册未读消息监听
        registerSystemMessageObservers(true);//注册系统消息监听
        requestSystemMessageUnreadCount();//注册系统消息未读监听
    }

    public void unRegisterObservers(){
        registerMsgUnreadInfoObserver(false);
        registerSystemMessageObservers(false);
    }


    /**
     * 注册未读消息数量观察者
     */
    public void registerMsgUnreadInfoObserver(boolean register) {
        if (register) {
            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
        } else {
            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
        }
    }


    /**
     * 未读消息数量观察者实现
     */
    @Override
    public void onUnreadNumChanged(ReminderItem item) {
        MainTab tab = MainTab.fromReminderId(item.getId());
        if (tab != null) {
            getView().onUnreadNumChanged(tab.tabIndex, item);
        }
    }

    /**
     * 查询系统消息未读数
     */
    private void requestSystemMessageUnreadCount() {
        int unread = NIMClient.getService(SystemMessageService.class).querySystemMessageUnreadCountBlock();
        SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unread);
        ReminderManager.getInstance().updateContactUnreadNum(unread);
    }

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
     * 初始化未读红点动画
     */
    public void initUnreadCover(DropCover dropCover) {
        DropManager.getInstance().init(mContext, dropCover,
                new DropCover.IDropCompletedListener() {
                    @Override
                    public void onCompleted(Object id, boolean explosive) {
                        if (id == null || !explosive) {
                            return;
                        }

                        if (id instanceof RecentContact) {//清除单个联系人的未读消息
                            RecentContact r = (RecentContact) id;
                            NIMClient.getService(MsgService.class).clearUnreadCount(r.getContactId(), r.getSessionType());
                            LogUtil.i("PoolFragment", "clearUnreadCount, sessionId=" + r.getContactId());
                        } else if (id instanceof String) {//清除所有联系人的未读消息(不包括系统消息)
                            if (((String) id).contentEquals("0")) {
                                List<RecentContact> recentContacts = NIMClient.getService(MsgService.class).queryRecentContactsBlock();
                                for (RecentContact r : recentContacts) {
                                    if (r.getUnreadCount() > 0) {
                                        NIMClient.getService(MsgService.class).clearUnreadCount(r.getContactId(), r.getSessionType());
                                    }
                                }
                                LogUtil.i("PoolFragment", "clearAllUnreadCount");
                            } else if (((String) id).contentEquals("1")) {//清除系统未读消息
                                NIMClient.getService(SystemMessageService.class).resetSystemMessageUnreadCount();
                                LogUtil.i("PoolFragment", "clearAllSystemUnreadCount");
                            }
                        }
                    }
                });
    }

    // 注销
    private void onLogout() {
        // 清理缓存&注销监听
        LogoutHelper.logout();

        // 启动登录
        mContext.jumpToActivityAndClearTask(LoginActivity.class);

    }

    private void initStatusBarNotificationConfig(SDKOptions options) {
        // load 应用的状态栏配置
        StatusBarNotificationConfig config = loadStatusBarNotificationConfig();

        // load 用户的 StatusBarNotificationConfig 设置项
        StatusBarNotificationConfig userConfig = UserPreferences.getStatusConfig();
        if (userConfig == null) {
            userConfig = config;
        } else {
            // 新增的 UserPreferences 存储项更新，兼容 3.4 及以前版本
            // APP默认 StatusBarNotificationConfig 配置修改后，使其生效
            userConfig.notificationEntrance = config.notificationEntrance;
            userConfig.notificationFolded = config.notificationFolded;
        }
        // 持久化生效
        UserPreferences.setStatusConfig(config);
        // SDK statusBarNotificationConfig 生效
        options.statusBarNotificationConfig = userConfig;
    }

    // 这里开发者可以自定义该应用初始的 StatusBarNotificationConfig
    private StatusBarNotificationConfig loadStatusBarNotificationConfig() {
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        // 点击通知需要跳转到的界面
        config.notificationEntrance = WelcomeActivity.class;
        config.notificationSmallIconId = R.mipmap.ic_launcher;

        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";

        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;

        // save cache，留做切换账号备用
        AppCache.setNotificationConfig(config);
        return config;
    }


}
