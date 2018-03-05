package com.drops.waterdrop.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.drops.waterdrop.BuildConfig;
import com.drops.waterdrop.R;
import com.drops.waterdrop.app.preference.UserPreferences;
import com.drops.waterdrop.help.SessionHelper;
import com.drops.waterdrop.im.mixpush.WaterDropPushContentProvider;
import com.drops.waterdrop.ui.other.activity.MainActivity;
import com.drops.waterdrop.ui.other.activity.WelcomeActivity;
import com.drops.waterdrop.ui.session.activity.JinDouMessageListActivity;
import com.drops.waterdrop.ui.session.activity.MaybeKownActivity;
import com.drops.waterdrop.ui.session.activity.OrderMessageListActivity;
import com.drops.waterdrop.ui.session.activity.ProductsRecommendActivity;
import com.drops.waterdrop.ui.session.activity.SystemMessageActivity;
import com.drops.waterdrop.util.SinaUtil;
import com.drops.waterdrop.util.contact.ContactDataSyncService;
import com.drops.waterdrop.util.sys.SystemUtil;
import com.google.gson.Gson;
import com.microquation.linkedme.android.LinkedME;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.contact.core.query.PinYin;
import com.netease.nim.uikit.custom.DefalutUserInfoProvider;
import com.netease.nim.uikit.emoji.IImageLoader;
import com.netease.nim.uikit.emoji.LQREmotionKit;
import com.netease.nim.uikit.event.BalanceMsgEvent;
import com.netease.nim.uikit.event.FriendAuthenticationEvent;
import com.netease.nim.uikit.event.JinDouMsgEvent;
import com.netease.nim.uikit.event.OrderMsgEvent;
import com.netease.nim.uikit.event.ProductsRecommendEvent;
import com.netease.nim.uikit.event.SystemMessageEvent;
import com.netease.nim.uikit.model.SystemMsgContentBean;
import com.netease.nim.uikit.session.attachment.BusinessCardAttachment;
import com.netease.nim.uikit.session.attachment.WaterShareAttachment;
import com.netease.nim.uikit.session.helper.NotificationHelper;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderThumbBase;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimStrings;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.model.AVChatAttachment;
import com.netease.nimlib.sdk.msg.MessageNotifierCustomization;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;
import com.netease.nimlib.sdk.team.model.IMMessageFilter;
import com.netease.nimlib.sdk.team.model.UpdateTeamAttachment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by dengxiaolei on 2017/5/9.
 */

public class WaterDropApp extends MultiDexApplication {

    public static int PAY_FROM = Constants.PAY_FROM_CHINA_PAVILION;
    public static int PAY_FROM_H5 = -1;
    public static List<Activity> activities = new LinkedList<>();
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler
    public static WaterDropApp app;

    public static IWXAPI mWxApi;
    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    public static Context sContext;//上下文

    public static long tipId;
    public static String tipUrl;

//    public static RefWatcher getRefWatcher(Context context) {
//        WaterDropApp application = (WaterDropApp) context.getApplicationContext();
//        return application.refWatcher;
//
//
//    }
//    private RefWatcher refWatcher;

//private void setLeakCanary() {
//    if (LeakCanary.isInAnalyzerProcess(this)) {
//        return;
//    }
//    refWatcher = LeakCanary.install(this);
//}
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
//        setLeakCanary();
        if (!BuildConfig.IS_DEBUG) {
            CrashHandler.get().init(getApplicationContext(), new Intent(this, WelcomeActivity.class));
        }

        //对全局属性赋值
        sContext = getApplicationContext();
//        BGASwipeBackManager.getInstance().init(this);

        Logger.init("WaterDrop")                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(BuildConfig.IS_DEBUG ? LogLevel.FULL : LogLevel.NONE)        // default LogLevel.FULL ---> LogLevel.NONE for the release versions
//                .logLevel(LogLevel.FULL)        // default LogLevel.FULL ---> LogLevel.NONE for the release versions
                .methodOffset(2);                // default 0

        registToWX();
        WbSdk.install(this, new AuthInfo(this, SinaUtil.APP_KEY, SinaUtil.REDIRECT_URL, SinaUtil.SCOPE));
        AppCache.setContext(this);
        NimUIKit.setContext(this);
        // 注册小米推送appID 、appKey 以及在云信管理后台添加的小米推送证书名称，该逻辑放在 NIMClient init 之前
//        NIMPushClient.registerMiPush(this, "DEMO_MI_PUSH", "2882303761517502883", "5671750254883");
        // 注册自定义小米推送消息处理，这个是可选项
        //NIMPushClient.registerMixPushMessageHandler(new WaterDropMixPushMessageHandler());
        NIMClient.init(this, getLoginInfo(), getOptions());//自动登陆配置
//        ExtraOptions.provide();
        // crash handler
//        AppCrashHandler.getInstance(this);
        if (inMainProcess()) {

            // init pinyin
            PinYin.init(this);
            PinYin.validate();

            // 初始化UIKit模块
            initUIKit();
            //初始化表情控件
            LQREmotionKit.init(this, /*Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sticker",*/
                    new IImageLoader() {
                        @Override
                        public void displayImage(Context context, String path, ImageView imageView) {
                            ImageLoader.getInstance().displayImage(path, imageView);
                           /* Glide.with(context).load(path).crossFade()
                                    .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);*/
                        }
                    });

            // 注册通知消息过滤器
            registerIMMessageFilter();

            // 初始化消息提醒
            UserPreferences.setNotificationToggle(true);
            NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

            // 注册网络通话来电
//            enableAVChat();

            // 注册白板会话
//            enableRTS();

            // 注册语言变化监听
            registerLocaleReceiver(true);

            //注册系统自定义消息通知
            registerIMSystemMessage(true);

            startContactSyncService();
        }

        initLinkedme();
//        getAppInfo();
    }

    private void initLinkedme() {
        if (BuildConfig.DEBUG) {
            //设置debug模式下打印LinkedME日志
            LinkedME.getInstance(this).setDebug();
        } else {
            LinkedME.getInstance(this);
        }
        // 设置是否开启自动跳转指定页面，默认为true
        // 若在此处设置为false，请务必在配置Uri scheme的Activity页面的onCreate()方法中，
        // 重新设置为true，否则将禁止开启自动跳转指定页面功能
        // 示例：
        // public class MainActivity extends AppCompatActivity {
        // ...
        // @Override
        // protected void onCreate(Bundle savedInstanceState) {
        //    super.onCreate(savedInstanceState);
        //    setContentView(R.layout.main);
        //    LinkedME.getInstance().setImmediate(true);
        //   }
        // ...
        //  }
        LinkedME.getInstance().setImmediate(false);
        //设置处理跳转逻辑的中转页
        LinkedME.getInstance().setHandleActivity(MiddleActivity.class.getName());

    }

    public void startContactSyncService() {
        startContactSyncService(500);
    }

    public void startContactSyncService(int milliseconds) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Intent intent = new Intent(getContext(), ContactDataSyncService.class);
                        intent.setAction(ContactDataSyncService.ACTION_DATA_SYNC);
                        startService(intent);
                    }
                });
    }

//    private void getAppInfo() {
//        Observable<BaseResponse<AppInfoEntity>> observable = HttpUtil.getInstance().sApi.getAppInfo();
//        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AppInfoEntity>(this) {
//            @Override
//            protected void _onNext(AppInfoEntity entity) {
//                List<AppInfoEntity.ResultsBean> results = entity.getResults();
//                for (AppInfoEntity.ResultsBean resultsBean : results) {
//                    if (resultsBean.getParamKey().equals(Constants.DEBUG_URL)) {
//                        MyUserCache.saveString(Constants.DEBUG_URL, resultsBean.getParamValue());
//                    } else if (resultsBean.getParamKey().equals(Constants.DEBUG_MODE)) {
//                        MyUserCache.saveString(Constants.DEBUG_MODE, resultsBean.getParamValue());
//                    } else if (resultsBean.getParamKey().equals(Constants.SHOP_URL)) {
//                        MyUserCache.saveString(Constants.SHOP_URL, resultsBean.getParamValue());
//                    }
//                }
//            }
//
//            @Override
//            protected void _onError(String message) {
//                ToastUtil.showShort(message);
//            }
//        });
//    }


    private void registerIMSystemMessage(boolean b) {
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(new Observer<CustomNotification>() {
            @Override
            public void onEvent(CustomNotification message) {
                // 在这里处理自定义通知。
                SystemMessageEvent event = new SystemMessageEvent();
                event.setContent(message.getContent());
                EventBus.getDefault().post(event);
                String content = message.getContent();
                Gson gson = new Gson();
                SystemMsgContentBean systemMsgContentBean = gson.fromJson(content, SystemMsgContentBean.class);

                if (systemMsgContentBean != null) {
                    String type = systemMsgContentBean.getType();
                    try {
                        int typeInt = Integer.parseInt(type);
                        switch (typeInt) {
                            case 11://申请好友
                            case 12://群申请
                                EventBus.getDefault().post(new FriendAuthenticationEvent());
                                if (SystemUtil.isBackground(WaterDropApp.sContext)) {
                                    new NotificationHelper(WaterDropApp.sContext).activeCallingNotification(true, "验证申请", systemMsgContentBean.getData().getTips(), (int) systemMsgContentBean.getData().getTargetId(), SystemMessageActivity.class);
                                }

                                break;
                            case 21://物流信息
                                if (SystemUtil.isBackground(WaterDropApp.sContext)) {
                                    new NotificationHelper(WaterDropApp.sContext).
                                            activeCallingNotification(true, "交易物流信息", systemMsgContentBean.getData().getTips(),
                                                    (int) systemMsgContentBean.getData().getTargetId(), OrderMessageListActivity.class);
                                }


                                EventBus.getDefault().post(new OrderMsgEvent());

                                break;
                            case 31://金豆变动
                                if (SystemUtil.isBackground(WaterDropApp.sContext)) {
                                    new NotificationHelper(WaterDropApp.sContext).
                                            activeCallingNotification(true, "金豆账户变动", systemMsgContentBean.getData().getTips(),
                                                    (int) systemMsgContentBean.getData().getTargetId(), JinDouMessageListActivity.class);
                                }

                                EventBus.getDefault().post(new JinDouMsgEvent());

                                break;
                            case 51://水塘内容
                            case 52://水贴内容
                                if (SystemUtil.isBackground(WaterDropApp.sContext)) {
                                    new NotificationHelper(WaterDropApp.sContext).
                                            activeCallingNotification(true, "精品推荐", systemMsgContentBean.getData().getTips(),
                                                    (int) systemMsgContentBean.getData().getTargetId(), ProductsRecommendActivity.class);

                                }
                                EventBus.getDefault().post(new ProductsRecommendEvent());

                                break;

                            case 61://余额变更
                                if (SystemUtil.isBackground(WaterDropApp.sContext)) {
                                    new NotificationHelper(WaterDropApp.sContext).
                                            activeCallingNotification(true, "余额账户变动", systemMsgContentBean.getData().getTips(),
                                                    (int) systemMsgContentBean.getData().getTargetId(), JinDouMessageListActivity.class);

                                }
                                EventBus.getDefault().post(new BalanceMsgEvent());

                                break;

                            case 66:
                                if (SystemUtil.isBackground(WaterDropApp.sContext)) {
                                    new NotificationHelper(WaterDropApp.sContext).activeCallingNotification(true, "好友推荐", systemMsgContentBean.getData().getTips(),
                                            (int) systemMsgContentBean.getData().getTargetId(), MaybeKownActivity.class);
                                }

                                break;
                        }
                    } catch (Exception e) {
                    }
                }
                LogUtil.d("接收到自定义系统通知：", message.getContent());
            }
        }, b);
    }

    private void registToWX() {
        //你应用在微信开放平台上的AppID。
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(Constants.WEIXIN_APP_ID);
    }

    private LoginInfo getLoginInfo() {
        String account = MyUserCache.getIMAccount();
        String token = MyUserCache.getIMToken();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            AppCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private SDKOptions getOptions() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给SDK完成，需要添加以下配置。
        initStatusBarNotificationConfig(options);

        // 配置保存图片，文件，log等数据的目录
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置数据库加密秘钥
        options.databaseEncryptKey = "WATER_DROP";

        // 配置是否需要预下载附件缩略图
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小，
        options.thumbnailSize = MsgViewHolderThumbBase.getImageMaxEdge();

        // 用户信息提供者
        options.userInfoProvider = new DefalutUserInfoProvider(this);

        // 定制通知栏提醒文案（可选，如果不定制将采用SDK默认文案）
        options.messageNotifierCustomization = messageNotifierCustomization;

        // 在线多端同步未读数
        options.sessionReadAck = true;

        return options;
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

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

    /**
     * 通知消息过滤器（如果过滤则该消息不存储不上报）
     */
    private void registerIMMessageFilter() {
        NIMClient.getService(MsgService.class).registerIMMessageFilter(new IMMessageFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {
                if (UserPreferences.getMsgIgnore() && message.getAttachment() != null) {
                    if (message.getAttachment() instanceof UpdateTeamAttachment) {
                        UpdateTeamAttachment attachment = (UpdateTeamAttachment) message.getAttachment();
                        for (Map.Entry<TeamFieldEnum, Object> field : attachment.getUpdatedFields().entrySet()) {
                            if (field.getKey() == TeamFieldEnum.ICON) {
                                return true;
                            }
                        }
                    } else if (message.getAttachment() instanceof AVChatAttachment) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 音视频通话配置与监听
     */
//    private void enableAVChat() {
//        registerAVChatIncomingCallObserver(true);
//    }

/*
    private void registerAVChatIncomingCallObserver(boolean register) {
        AVChatManager.getInstance().observeIncomingCall(new Observer<AVChatData>() {
            @Override
            public void onEvent(AVChatData data) {
                String extra = data.getExtra();
                Log.e("Extra", "Extra Message->" + extra);
                if (PhoneCallStateObserver.getInstance().getPhoneCallState() != PhoneCallStateObserver.PhoneCallStateEnum.IDLE) {
                    LogUtil.i(TAG, "reject incoming call data =" + data.toString() + " as local phone is not idle");
                    AVChatManager.getInstance().sendControlCommand(AVChatControlCommand.BUSY, null);
                    return;
                }
                // 有网络来电打开AVChatActivity
                AVChatProfile.getInstance().setAVChatting(true);
                AVChatActivity.launch(DemoCache.getContext(), data, AVChatActivity.FROM_BROADCASTRECEIVER);
            }
        }, register);
    }
*/

/*
    */

    /**
     * 白板实时时会话配置与监听
     *//*

    private void enableRTS() {
        registerRTSIncomingObserver(true);
    }


    private void registerRTSIncomingObserver(boolean register) {
        RTSManager.getInstance().observeIncomingSession(new Observer<RTSData>() {
            @Override
            public void onEvent(RTSData rtsData) {
                RTSActivity.incomingSession(DemoCache.getContext(), rtsData, RTSActivity.FROM_BROADCAST_RECEIVER);
            }
        }, register);
    }
*/
    private void registerLocaleReceiver(boolean register) {
        if (register) {
            updateLocale();
            IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
            registerReceiver(localeReceiver, filter);
        } else {
            unregisterReceiver(localeReceiver);
        }
    }

    private BroadcastReceiver localeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)) {
                updateLocale();
            }
        }
    };

    private void updateLocale() {
        NimStrings strings = new NimStrings();
        strings.status_bar_multi_messages_incoming = getString(R.string.nim_status_bar_multi_messages_incoming);
        strings.status_bar_image_message = getString(R.string.nim_status_bar_image_message);
        strings.status_bar_audio_message = getString(R.string.nim_status_bar_audio_message);
        strings.status_bar_custom_message = getString(R.string.nim_status_bar_custom_message);
        strings.status_bar_file_message = getString(R.string.nim_status_bar_file_message);
        strings.status_bar_location_message = getString(R.string.nim_status_bar_location_message);
        strings.status_bar_notification_message = getString(R.string.nim_status_bar_notification_message);
        strings.status_bar_ticker_text = getString(R.string.nim_status_bar_ticker_text);
        strings.status_bar_unsupported_message = getString(R.string.nim_status_bar_unsupported_message);
        strings.status_bar_video_message = getString(R.string.nim_status_bar_video_message);
        strings.status_bar_hidden_message_content = getString(R.string.nim_status_bar_hidden_msg_content);
        NIMClient.updateStrings(strings);
    }

    private void initUIKit() {
        // 初始化，使用 uikit 默认的用户信息提供者
        NimUIKit.init(this);

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
//        NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // 会话窗口的定制初始化。
        SessionHelper.init();

        // 通讯录列表定制初始化
//         ContactHelper.init();

        // 添加自定义推送文案以及选项，请开发者在各端（Android、IOS、PC、Web）消息发送时保持一致，以免出现通知不一致的情况
        NimUIKit.CustomPushContentProvider(new WaterDropPushContentProvider());
    }


    private MessageNotifierCustomization messageNotifierCustomization = new MessageNotifierCustomization() {
        @Override
        public String makeNotifyContent(String nick, IMMessage message) {
            if (message != null) {
                MsgAttachment attachment = message.getAttachment();
                if (attachment instanceof BusinessCardAttachment) {
                    return "分享了一个名片";

                } else if (attachment instanceof WaterShareAttachment) {
                    WaterShareAttachment waterShare = (WaterShareAttachment) attachment;
                    String shareFrom = waterShare.getShareFrom();
                    if (TextUtils.equals("水塘", shareFrom)) {
                        return "分享了一个水塘";
                    } else if (TextUtils.equals("水帖", shareFrom)) {
                        return "分享了一个水帖";
                    } else if (TextUtils.equals("水宝", shareFrom)) {
                        return "分享了一个水宝";
                    }
                }
            }
            return null; // 采用SDK默认文案
        }

        @Override
        public String makeTicker(String nick, IMMessage message) {
            return null; // 采用SDK默认文案
        }
    };


    /**
     * 完全退出
     * 一般用于“退出程序”功能
     */
    public static void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

    public static void removeMainActivity() {

        for (Activity activity : activities) {
            if (activity instanceof MainActivity) {
                activity.finish();

            }
        }
    }


    /**
     * 结束指定类名的Activity
     */
    public static void finishActivityclass(Class<?> cls) {
        if (activities != null) {
            for (Activity activity : activities) {
                if (activity.getClass().equals(cls)) {
                    activities.remove(activity);
                    activity.finish();
                    break;
                }
            }
        }

    }

    public static void removeActivity(Class<? extends AppCompatActivity> clazz) {

        for (Activity activity : activities) {
            if (activity instanceof MainActivity) {
                activity.finish();

            }
        }
    }

    /**
     * 重启当前应用
     */
    public static void restart() {
        Intent intent = sContext.getPackageManager().getLaunchIntentForPackage(sContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sContext.startActivity(intent);
    }


    public static Context getContext() {
        return sContext;
    }

    public static void setContext(Context mContext) {
        WaterDropApp.sContext = mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        WaterDropApp.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        WaterDropApp.mMainThreadId = mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static void setMainThreadLooper(Looper mMainLooper) {
        WaterDropApp.mMainLooper = mMainLooper;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void setMainHandler(Handler mHandler) {
        WaterDropApp.mHandler = mHandler;
    }
}
