package com.drops.waterdrop.help;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.AppCache;
import com.drops.waterdrop.im.session.viewholder.MsgViewHolderSticker;
import com.drops.waterdrop.im.session.viewholder.MsgViewHolderTip;
import com.drops.waterdrop.im.session.viewholder.MsgViewHolderVerify;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.drops.waterdrop.ui.find.activity.GoodsDetailsActivity;
import com.drops.waterdrop.ui.find.activity.PoolDetailPageActivity;
import com.drops.waterdrop.ui.mine.activity.UserProfileActivity;
import com.drops.waterdrop.ui.session.activity.BalanceMessageListActivity;
import com.drops.waterdrop.ui.session.activity.JinDouMessageListActivity;
import com.drops.waterdrop.ui.session.activity.OrderMessageListActivity;
import com.drops.waterdrop.ui.session.activity.ProductsRecommendActivity;
import com.drops.waterdrop.ui.session.activity.RecommendFriendActivity;
import com.drops.waterdrop.ui.session.activity.SystemMessageActivity;
import com.drops.waterdrop.ui.session.activity.WatchMessagePictureActivity;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.ui.popupmenu.NIMPopupMenu;
import com.netease.nim.uikit.common.ui.popupmenu.PopupMenuItem;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.recent.SystemMessageEventListener;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.SessionEventListener;
import com.netease.nim.uikit.session.SessionImgItemClickListener;
import com.netease.nim.uikit.session.attachment.BusinessCardAttachment;
import com.netease.nim.uikit.session.attachment.CustomAttachParser;
import com.netease.nim.uikit.session.attachment.StickerAttachment;
import com.netease.nim.uikit.session.attachment.VerifyContentAttachment;
import com.netease.nim.uikit.session.attachment.WaterShareAttachment;
import com.netease.nim.uikit.session.helper.MessageHelper;
import com.netease.nim.uikit.session.helper.MessageListPanelHelper;
import com.netease.nim.uikit.session.module.MsgForwardFilter;
import com.netease.nim.uikit.session.module.MsgRevokeFilter;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBusinessCard;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderWaterShare;
import com.netease.nim.uikit.session.viewholder.SessionAutoLinkClickListener;
import com.netease.nim.uikit.team.TeamMemberItemClickListener;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.avchat.model.AVChatAttachment;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * UIKit自定义消息界面用法展示类
 */
public class SessionHelper {

    private static final int ACTION_HISTORY_QUERY = 0;
    private static final int ACTION_SEARCH_MESSAGE = 1;
    private static final int ACTION_CLEAR_MESSAGE = 2;

    private static SessionCustomization p2pCustomization;
    private static SessionCustomization teamCustomization;
    private static SessionCustomization myP2pCustomization;

    private static NIMPopupMenu popupMenu;
    private static List<PopupMenuItem> menuItemList;

    public static void init() {
        // 注册自定义消息附件解析器
        NIMClient.getService(MsgService.class).registerCustomAttachmentParser(new CustomAttachParser());

        // 注册各种扩展消息类型的显示ViewHolder
        registerViewHolders();

        // 设置会话中点击事件响应处理
        setSessionListener();

        // 注册消息转发过滤器
        registerMsgForwardFilter();

        // 注册消息撤回过滤器
        registerMsgRevokeFilter();

        // 注册消息撤回监听器
        registerMsgRevokeObserver();

        //设置系统消息条目监听器
        registerSystemMsgListener();

        //设置群成员点击事件
        registerTeamMemberListener();

        //设置聊天页面图片消息的点击事件
        registerSessionImgClickListener();

        //设置聊天页面文本web超链接点击事件
        registerSessionAutoLinkClickListener();

      /*  NimUIKit.setCommonP2PSessionCustomization(getP2pCustomization());

        NimUIKit.setCommonTeamSessionCustomization(getTeamCustomization());*/
    }


    public static void startP2PSession(Context context, String account) {
        startP2PSession(context, account, null);
    }

    public static void startP2PSession(Context context, String account, IMMessage anchor) {
        String account1 = AppCache.getAccount();
        if (account1 == null) return;
        if (!account1.equals(account)) {
            NimUIKit.startP2PSession(context, account, anchor);
        } else {
//            NimUIKit.startChatting(context, account, SessionTypeEnum.P2P, getMyP2pCustomization(), anchor);
        }
    }

    public static void startTeamSession(Context context, String tid) {
        startTeamSession(context, tid, null);
    }

    public static void startTeamSession(Context context, String tid, IMMessage anchor) {
        NimUIKit.startTeamSession(context, tid, anchor);
    }

    // 打开群聊界面(用于 UIKIT 中部分界面跳转回到指定的页面)
 /*   public static void startTeamSession(Context context, String tid, Class<? extends Activity> backToClass, IMMessage anchor) {
        NimUIKit.startChatting(context, tid, SessionTypeEnum.Team, getTeamCustomization(), backToClass, anchor);
    }*/

    // 定制化单聊界面。如果使用默认界面，返回null即可
/*
    private static SessionCustomization getP2pCustomization() {
        if (p2pCustomization == null) {
            p2pCustomization = new SessionCustomization() {
                // 由于需要Activity Result， 所以重载该函数。
                @Override
                public void onActivityResult(final Activity activity, int requestCode, int resultCode, Intent data) {
                    super.onActivityResult(activity, requestCode, resultCode, data);

                }

                @Override
                public MsgAttachment createStickerAttachment(String category, String item) {
                    return new StickerAttachment(category, item);
                }
            };

            // 背景
//            p2pCustomization.backgroundColor = Color.BLUE;
//            p2pCustomization.backgroundUri = "file:///android_asset/xx/bk.jpg";
//            p2pCustomization.backgroundUri = "file:///sdcard/Pictures/bk.png";
//            p2pCustomization.backgroundUri = "android.resource://com.netease.nim.demo/drawable/bk"

            // 定制加号点开后可以包含的操作， 默认已经有图片，视频等消息了
*/
/*
            ArrayList<BaseAction> actions = new ArrayList<>();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                actions.add(new AVChatAction(AVChatType.AUDIO));
                actions.add(new AVChatAction(AVChatType.VIDEO));
            }
            actions.add(new RTSAction());
            actions.add(new SnapChatAction());
            actions.add(new GuessAction());
            actions.add(new FileAction());
            actions.add(new TipAction());
            p2pCustomization.actions = actions;
*//*

            p2pCustomization.withSticker = true;//是否使用自定义表情

            // 定制ActionBar右边的按钮，可以加多个
            ArrayList<SessionCustomization.OptionsButton> buttons = new ArrayList<>();
            SessionCustomization.OptionsButton cloudMsgButton = new SessionCustomization.OptionsButton() {
                @Override
                public void onClick(Context context, View view, String sessionId) {
                    initPopuptWindow(context, view, sessionId, SessionTypeEnum.P2P);
                }
            };
            cloudMsgButton.iconId = R.drawable.nim_ic_messge_history;

            SessionCustomization.OptionsButton infoButton = new SessionCustomization.OptionsButton() {
                @Override
                public void onClick(Context context, View view, String sessionId) {
                   // MessageInfoActivity.startActivity(context, sessionId); //打开聊天信息
                    Toast.makeText(context, "打开聊天信息", Toast.LENGTH_SHORT).show();
                }
            };

            infoButton.iconId = R.drawable.nim_ic_message_actionbar_p2p_add;

            buttons.add(cloudMsgButton);
            buttons.add(infoButton);
            p2pCustomization.buttons = buttons;
        }

        return p2pCustomization;
    }
*/

/*
    private static SessionCustomization getMyP2pCustomization() {
        if (myP2pCustomization == null) {
            myP2pCustomization = new SessionCustomization() {
                // 由于需要Activity Result， 所以重载该函数。
                @Override
                public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
                    if (requestCode == TeamRequestCode.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                        String result = data.getStringExtra(TeamExtras.RESULT_EXTRA_REASON);
                        if (result == null) {
                            return;
                        }
                        if (result.equals(TeamExtras.RESULT_EXTRA_REASON_CREATE)) {
                            String tid = data.getStringExtra(TeamExtras.RESULT_EXTRA_DATA);
                            if (TextUtils.isEmpty(tid)) {
                                return;
                            }

                            startTeamSession(activity, tid);
                            activity.finish();
                        }
                    }
                }

                @Override
                public MsgAttachment createStickerAttachment(String category, String item) {
                    return new StickerAttachment(category, item);
                }
            };

            // 背景
//            p2pCustomization.backgroundColor = Color.BLUE;
//            p2pCustomization.backgroundUri = "file:///android_asset/xx/bk.jpg";
//            p2pCustomization.backgroundUri = "file:///sdcard/Pictures/bk.png";
//            p2pCustomization.backgroundUri = "android.resource://com.netease.nim.demo/drawable/bk"

            // 定制加号点开后可以包含的操作， 默认已经有图片，视频等消息了
            ArrayList<BaseAction> actions = new ArrayList<>();
            actions.add(new SnapChatAction());
            actions.add(new GuessAction());
            actions.add(new FileAction());
            myP2pCustomization.actions = actions;
            myP2pCustomization.withSticker = true;
            // 定制ActionBar右边的按钮，可以加多个
            ArrayList<SessionCustomization.OptionsButton> buttons = new ArrayList<>();
            SessionCustomization.OptionsButton cloudMsgButton = new SessionCustomization.OptionsButton() {
                @Override
                public void onClick(Context context, View view, String sessionId) {
                    initPopuptWindow(context, view, sessionId, SessionTypeEnum.P2P);
                }
            };

            cloudMsgButton.iconId = R.drawable.nim_ic_messge_history;

            buttons.add(cloudMsgButton);
            myP2pCustomization.buttons = buttons;
        }
        return myP2pCustomization;
    }
*/

/*
    private static SessionCustomization getTeamCustomization() {
        if (teamCustomization == null) {
            teamCustomization = new SessionCustomization() {
                @Override
                public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
                    if (requestCode == TeamRequestCode.REQUEST_CODE) {
                        if (resultCode == Activity.RESULT_OK) {
                            String reason = data.getStringExtra(TeamExtras.RESULT_EXTRA_REASON);
                            boolean finish = reason != null && (reason.equals(TeamExtras
                                    .RESULT_EXTRA_REASON_DISMISS) || reason.equals(TeamExtras.RESULT_EXTRA_REASON_QUIT));
                            if (finish) {
                                activity.finish(); // 退出or解散群直接退出多人会话
                            }
                        }
                    }
                }

                @Override
                public MsgAttachment createStickerAttachment(String category, String item) {
                    return new StickerAttachment(category, item);
                }
            };

            // 定制加号点开后可以包含的操作， 默认已经有图片，视频等消息了
            ArrayList<BaseAction> actions = new ArrayList<>();
            actions.add(new GuessAction());
            actions.add(new FileAction());
            actions.add(new TipAction());
            teamCustomization.actions = actions;

            // 定制ActionBar右边的按钮，可以加多个
            ArrayList<SessionCustomization.OptionsButton> buttons = new ArrayList<>();
            SessionCustomization.OptionsButton cloudMsgButton = new SessionCustomization.OptionsButton() {
                @Override
                public void onClick(Context context, View view, String sessionId) {
                    initPopuptWindow(context, view, sessionId, SessionTypeEnum.Team);
                }
            };
            cloudMsgButton.iconId = R.drawable.nim_ic_messge_history;

            SessionCustomization.OptionsButton infoButton = new SessionCustomization.OptionsButton() {
                @Override
                public void onClick(Context context, View view, String sessionId) {
                    Team team = TeamDataCache.getInstance().getTeamById(sessionId);
                    if (team != null && team.isMyTeam()) {
                        NimUIKit.startTeamInfo(context, sessionId);
                    } else {
                        Toast.makeText(context, R.string.team_invalid_tip, Toast.LENGTH_SHORT).show();
                    }
                }
            };
            infoButton.iconId = R.drawable.nim_ic_message_actionbar_team;
            buttons.add(cloudMsgButton);
            buttons.add(infoButton);
            teamCustomization.buttons = buttons;

            teamCustomization.withSticker = true;
        }

        return teamCustomization;
    }
*/

    //消息列表里的消息种类
    private static void registerViewHolders() {
/*
        NimUIKit.registerMsgItemViewHolder(FileAttachment.class, MsgViewHolderFile.class);
        NimUIKit.registerMsgItemViewHolder(AVChatAttachment.class, MsgViewHolderAVChat.class);
        NimUIKit.registerMsgItemViewHolder(GuessAttachment.class, MsgViewHolderGuess.class);
        NimUIKit.registerMsgItemViewHolder(CustomAttachment.class, MsgViewHolderDefCustom.class);
        NimUIKit.registerMsgItemViewHolder(SnapChatAttachment.class, MsgViewHolderSnapChat.class);
        NimUIKit.registerMsgItemViewHolder(RTSAttachment.class, MsgViewHolderRTS.class);
*/
        NimUIKit.registerMsgItemViewHolder(StickerAttachment.class, MsgViewHolderSticker.class);
        NimUIKit.registerMsgItemViewHolder(VerifyContentAttachment.class, MsgViewHolderVerify.class);
        NimUIKit.registerMsgItemViewHolder(BusinessCardAttachment.class, MsgViewHolderBusinessCard.class);
        NimUIKit.registerMsgItemViewHolder(WaterShareAttachment.class, MsgViewHolderWaterShare.class);
        NimUIKit.registerTipMsgViewHolder(MsgViewHolderTip.class);
    }

    private static void setSessionListener() {
        SessionEventListener listener = new SessionEventListener() {
            @Override
            public void onAvatarClicked(Context context, IMMessage message) {
                // 一般用于打开用户资料页面
                long uid = 0;

                String fromAccount = message.getFromAccount();
                if (fromAccount.contains("yuanshi_")) {
                    String yuanshi_ = fromAccount.replace("yuanshi_", "");
                    uid = Long.parseLong(yuanshi_);
                } else {
                    Map<String, Object> map = message.getRemoteExtension();
                    if (map == null) return;
                    uid = (long) map.get(Constants.KEY_UID);
                }

                if (uid != 0) {
                    UserProfileActivity.start(context, uid);
                } else {
                    Logger.d("点击头像：消息中检测不到用户的uid" + uid);
                }
            }

            @Override
            public void onAvatarLongClicked(Context context, IMMessage message) {
                // 一般用于群组@功能，或者弹出菜单，做拉黑，加好友等功能
            }

            @Override
            public void onSessionContentClicked(Context context, IMMessage message) {
                MsgAttachment attachment = message.getAttachment();
                if (attachment instanceof BusinessCardAttachment) {
                    BusinessCardAttachment businessCard = (BusinessCardAttachment) attachment;
                    long uid = businessCard.getCardUid();
                    if (uid != 0) {
                        UserProfileActivity.start(context, uid);
                    }

                } else if (attachment instanceof WaterShareAttachment) {
                    WaterShareAttachment waterShare = (WaterShareAttachment) attachment;
                    String shareFrom = waterShare.getShareFrom();
                    String shareId = waterShare.getShareId();

                    if (TextUtils.isEmpty(shareId)) {
                        return;
                    }
                    if (TextUtils.equals("水塘", shareFrom)) {
                        PoolDetailPageActivity.start(context, Long.parseLong(shareId));
                    } else if (TextUtils.equals("水帖", shareFrom)) {

                        CommonWebActivity.start(context, Long.parseLong(shareId), waterShare.getTipUrl());

                    } else if (TextUtils.equals("水宝", shareFrom)) {
                        long poolId = waterShare.getPoolId();
                        long tipId = waterShare.getTipId();
                        String tipTitle = waterShare.getFromName();
                        GoodsDetailsActivity.start(context, shareId, tipId, poolId, tipTitle);

                    }
                }


            }
        };

        NimUIKit.setSessionListener(listener);
    }

    private static void registerSystemMsgListener() {
        SystemMessageEventListener listener = new SystemMessageEventListener() {
            @Override
            public void onItemClickListener(Context context, int type) {
                switch (type) {
                    case Constants.MESSAGE_TYPE_YAN_ZHENG:
                        //跳转验证页面
                        SystemMessageActivity.start(context, true);

                        break;
                    case Constants.MESSAGE_TYPE_ORDER:
                        //跳转订单信息页面
                        OrderMessageListActivity.start(context);
                        break;
                    case Constants.MESSAGE_TYPE_JIN_DOU:
                        //跳转金豆页面
                        JinDouMessageListActivity.start(context);

                        break;
                    case Constants.MESSAGE_TYPE_JING_PIN:
                        //跳转推荐页面

                        ProductsRecommendActivity.start(context);
                        break;
                    case Constants.MESSAGE_TYPE_BALANCE:
                        //跳转余额页面

                        BalanceMessageListActivity.start(context);
                        break;
                    case Constants.MESSAGE_TYPE_RECOMMEND_FRIEND:
                        //跳转推荐好友页面

                        RecommendFriendActivity.start(context);
                        break;
                }
            }
        };

        NimUIKit.setSystemMessageListener(listener);

    }

    private static void registerTeamMemberListener() {
        TeamMemberItemClickListener listener = new TeamMemberItemClickListener() {
            @Override
            public void onTeamMemberClickListener(Context context, long uid) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    UserProfileActivity.start(context, uid);
                }
            }
        };

        NimUIKit.setTeamMemberClickListener(listener);

    }


    private static void registerSessionImgClickListener() {
        SessionImgItemClickListener listener = new SessionImgItemClickListener() {

            @Override
            public void onImgClickListener(Context context, IMMessage message) {
                WatchMessagePictureActivity.start(context, message);

            }
        };

        NimUIKit.setSessionImgItemClickListener(listener);

    }

    private static void registerSessionAutoLinkClickListener() {
        SessionAutoLinkClickListener listener = new SessionAutoLinkClickListener() {

            @Override
            public void onWebLinkClickListener(Context context, String url) {
//                Intent intent = new Intent(context,StoreActivity.class);
//                intent.putExtra(Constants.UNSTORE_URL, url);
//                context.startActivity(intent);
                CommonWebActivity.startOfActive(context, url);
            }
        };

        NimUIKit.setSessionAutoLinkClickListener(listener);

    }


    /**
     * 设置消息是否允许转发
     * 消息转发过滤器
     */
    private static void registerMsgForwardFilter() {
        NimUIKit.setMsgForwardFilter(new MsgForwardFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {
                if (message.getDirect() == MsgDirectionEnum.In
                        && (message.getAttachStatus() == AttachStatusEnum.transferring
                        || message.getAttachStatus() == AttachStatusEnum.fail)) {
                    // 接收到的消息，附件没有下载成功，不允许转发
                    return true;
                } /*else if (message.getMsgType() == MsgTypeEnum.custom && message.getAttachment() != null
                        *//*&& (message.getAttachment() instanceof SnapChatAttachment*//*
                      *//*  || message.getAttachment() instanceof RTSAttachment)*//*) {
                    // 白板消息和阅后即焚消息 不允许转发
                    return true;
                }*/
                return false;
            }
        });
    }

    /**
     * 消息撤回过滤器
     */
    private static void registerMsgRevokeFilter() {
        NimUIKit.setMsgRevokeFilter(new MsgRevokeFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {
                if (message.getAttachment() != null
                        && (message.getAttachment() instanceof AVChatAttachment
                       /* || message.getAttachment() instanceof RTSAttachment*/)) {
                    // 视频通话消息和白板消息 不允许撤回
                    return true;
                } else if (AppCache.getAccount().equals(message.getSessionId())) {
                    // 发给我的电脑 不允许撤回
                    return true;
                }
                return false;
            }
        });
    }

    private static void registerMsgRevokeObserver() {
        NIMClient.getService(MsgServiceObserve.class).observeRevokeMessage(new Observer<IMMessage>() {
            @Override
            public void onEvent(IMMessage message) {
                if (message == null) {
                    return;
                }

                MessageHelper.getInstance().onRevokeMessage(message);
            }
        }, true);
    }


    private static void initPopuptWindow(Context context, View view, String sessionId, SessionTypeEnum sessionTypeEnum) {
        if (popupMenu == null) {
            menuItemList = new ArrayList<>();
            popupMenu = new NIMPopupMenu(context, menuItemList, listener);
        }
        menuItemList.clear();
        menuItemList.addAll(getMoreMenuItems(context, sessionId, sessionTypeEnum));
        popupMenu.notifyData();
        popupMenu.show(view);
    }

    private static NIMPopupMenu.MenuItemClickListener listener = new NIMPopupMenu.MenuItemClickListener() {
        @Override
        public void onItemClick(final PopupMenuItem item) {
            switch (item.getTag()) {
                case ACTION_HISTORY_QUERY:
//                    MessageHistoryActivity.start(item.getContext(), item.getSessionId(), item.getSessionTypeEnum()); // 漫游消息查询
                    Toast.makeText(AppCache.getContext(), "进入漫游消息查询页面", Toast.LENGTH_SHORT).show();
                    break;
                case ACTION_SEARCH_MESSAGE:
//                    SearchMessageActivity.start(item.getContext(), item.getSessionId(), item.getSessionTypeEnum());
                    Toast.makeText(AppCache.getContext(), "进入消息搜索查询页面", Toast.LENGTH_SHORT).show();
                    break;
                case ACTION_CLEAR_MESSAGE:
                    EasyAlertDialogHelper.createOkCancelDiolag(item.getContext(), null, "确定要清空吗？", true, new EasyAlertDialogHelper.OnDialogActionListener() {
                        @Override
                        public void doCancelAction() {

                        }

                        @Override
                        public void doOkAction() {
                            NIMClient.getService(MsgService.class).clearChattingHistory(item.getSessionId(), item.getSessionTypeEnum());
                            MessageListPanelHelper.getInstance().notifyClearMessages(item.getSessionId());
                        }
                    }).show();
                    break;
            }
        }
    };

    private static List<PopupMenuItem> getMoreMenuItems(Context context, String sessionId, SessionTypeEnum sessionTypeEnum) {
        List<PopupMenuItem> moreMenuItems = new ArrayList<PopupMenuItem>();
        moreMenuItems.add(new PopupMenuItem(context, ACTION_HISTORY_QUERY, sessionId,
                sessionTypeEnum, AppCache.getContext().getString(R.string.message_history_query)));
        moreMenuItems.add(new PopupMenuItem(context, ACTION_SEARCH_MESSAGE, sessionId,
                sessionTypeEnum, AppCache.getContext().getString(R.string.message_search_title)));
        moreMenuItems.add(new PopupMenuItem(context, ACTION_CLEAR_MESSAGE, sessionId,
                sessionTypeEnum, AppCache.getContext().getString(R.string.message_clear)));
        return moreMenuItems;
    }

    /**
     * 发送好友验证消息（打招呼）
     *
     * @param account 接收者账号
     * @param text    招呼内容
     */
    public static void sendVerifyContent(String account, String text) {

        VerifyContentAttachment attachment = new VerifyContentAttachment();
        attachment.setVerify(text);

        IMMessage message = MessageBuilder.createCustomMessage(account, SessionTypeEnum.P2P, attachment);

        CustomMessageConfig config = new CustomMessageConfig();
        config.enableUnreadCount = false; // 该消息不计入未读数
        config.enableHistory = true;//该消息是否允许在消息历史中拉取，如果为 false，通过 MsgService#pullMessageHistory 拉取的结果将不包含该条消息。默认为 true 。
        config.enableRoaming = false;//该消息是否需要漫游。
        config.enableSelfSync = false;//多端同时登录时，发送一条消息后，是否要同步到其他同时登录的客户端。默认为 true 。
        config.enableUnreadCount = false;//该消息是否要计入未读数，如果为 true ，那么对方收到消息后，最近联系人列表中未读数加1。默认为 true 。
        config.enablePush = false;//该消息是否进行推送（消息提醒）

        message.setConfig(config);

        NIMClient.getService(MsgService.class).sendMessage(message, false);

    }


}
