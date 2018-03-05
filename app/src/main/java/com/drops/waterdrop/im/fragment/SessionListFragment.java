package com.drops.waterdrop.im.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.drops.waterdrop.R;
import com.drops.waterdrop.help.SessionHelper;
import com.drops.waterdrop.im.help.LogoutHelper;
import com.drops.waterdrop.im.reminder.ReminderManager;
import com.drops.waterdrop.im.tab.MainTab;
import com.drops.waterdrop.ui.other.activity.LoginActivity;
import com.drops.waterdrop.ui.session.activity.ContactListActivity;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.recent.RecentContactsCallback;
import com.netease.nim.uikit.recent.RecentContactsFragment;
import com.netease.nim.uikit.session.attachment.BusinessCardAttachment;
import com.netease.nim.uikit.session.attachment.StickerAttachment;
import com.netease.nim.uikit.session.attachment.VerifyContentAttachment;
import com.netease.nim.uikit.session.attachment.WaterShareAttachment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dengxiaolei on 2017/4/5.
 */

public class SessionListFragment extends MainTabFragment implements Toolbar.OnMenuItemClickListener {

    private static final int BASIC_PERMISSION_REQUEST_CODE = 12;
    private View notifyBar;

    private TextView notifyBarText;

    // 同时在线的其他端的信息
    private List<OnlineClient> onlineClients;

    private View multiportBar;

    private RecentContactsFragment fragment;
    private ImageView mIvRight;

    public SessionListFragment() {
        this.setContainerId(MainTab.RECENT_CONTACTS.fragmentId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onCurrent();//提前加载onInit()里的数据
    }

    @Override
    public void onDestroy() {
        registerObservers(false);
        super.onDestroy();
    }

    @Override
    protected void onInit() {
        Logger.d("首页初始化：SessionListFragment" );

        findViews();
        registerObservers(true);

        addRecentContactsFragment();

        notifyBarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = MyUserCache.getIMAccount();
                String token = MyUserCache.getIMToken();
            }
        });
    }

    private void registerObservers(boolean register) {
//        NIMClient.getService(AuthServiceObserver.class).observeOtherClients(clientsObserver, register);
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, register);
    }

    private void findViews() {
        notifyBar = getView().findViewById(R.id.status_notify_bar);
        notifyBarText = (TextView) getView().findViewById(R.id.status_desc_label);
        notifyBar.setVisibility(View.GONE);

        TextView tvTitle = (TextView) getView().findViewById(R.id.tv_commn_title);
        tvTitle.setText("消息");
        mIvRight = (ImageView) getView().findViewById(R.id.iv_right);
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(R.mipmap.txl);
        mIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    ContactListActivity.start(getContext());
                }

            }
        });

        View statusBarFix = findView(R.id.status_bar_fix);
        statusBarFix.setBackgroundResource(R.color.colorPrimary);
        statusBarFix.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getStatusBarHeight(getActivity())));//填充状态栏


//        toolbar.inflateMenu(R.menu.main_activity_session_list);
//        toolbar.setOnMenuItemClickListener(this);

        multiportBar = getView().findViewById(R.id.multiport_notify_bar);
        multiportBar.setVisibility(View.GONE);
        multiportBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MultiportActivity.startActivity(getActivity(), onlineClients);
            }
        });
    }


    /**
     * 用户状态变化
     */
    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            LogUtil.d("当前用户在线状态：", code.getValue() + "");
            if (code.wontAutoLogin()) {
                kickOut(code);
            } else {
                if (code == StatusCode.NET_BROKEN) {
                    notifyBar.setVisibility(View.VISIBLE);
                    notifyBarText.setText(R.string.net_broken);
                } else if (code == StatusCode.UNLOGIN) {
                    notifyBar.setVisibility(View.VISIBLE);
                    notifyBarText.setText(R.string.nim_status_unlogin);
                } else if (code == StatusCode.CONNECTING) {
                    notifyBar.setVisibility(View.VISIBLE);
                    notifyBarText.setText(R.string.nim_status_connecting);
                } else if (code == StatusCode.LOGINING) {
                    notifyBar.setVisibility(View.VISIBLE);
                    notifyBarText.setText(R.string.nim_status_logining);
                } else {
                    notifyBar.setVisibility(View.GONE);
                }
            }
        }
    };


    /**
     * 云信多终端登陆监测
     */
/*
    Observer<List<OnlineClient>> clientsObserver = new Observer<List<OnlineClient>>() {
        @Override
        public void onEvent(List<OnlineClient> onlineClients) {
            SessionListFragment.this.onlineClients = onlineClients;
            if (onlineClients == null || onlineClients.size() == 0) {
                multiportBar.setVisibility(View.GONE);
            } else {
                multiportBar.setVisibility(View.VISIBLE);
                TextView status = (TextView) multiportBar.findViewById(R.id.multiport_desc_label);
                OnlineClient client = onlineClients.get(0);
                switch (client.getClientType()) {
                    case ClientType.Windows:
                        status.setText(getString(R.string.multiport_logging) + getString(R.string.computer_version));
                        break;
                    case ClientType.Web:
                        status.setText(getString(R.string.multiport_logging) + getString(R.string.web_version));
                        break;
                    case ClientType.iOS:
                    case ClientType.Android:
                        status.setText(getString(R.string.multiport_logging) + getString(R.string.mobile_version));
                        break;
                    default:
                        multiportBar.setVisibility(View.GONE);
                        break;
                }
            }
        }
    };
*/
    private void kickOut(StatusCode code) {
        MyUserCache.saveIMAccount("");

        if (code == StatusCode.PWD_ERROR) {
            LogUtil.e("Auth", "user password error");
            Toast.makeText(getActivity(), R.string.login_failed, Toast.LENGTH_SHORT).show();
        } else {
            LogUtil.i("Auth", "Kicked!");
        }
        onLogout();
    }

    // 注销
    private void onLogout() {
        // 清理缓存&注销监听&清除状态
        LogoutHelper.logout();

        LoginActivity.start(getActivity(), true);
        getActivity().finish();
    }

    // 将最近联系人列表fragment动态集成进来。 开发者也可以使用在xml中配置的方式静态集成。
    private void addRecentContactsFragment() {
        fragment = new RecentContactsFragment();
        fragment.setContainerId(R.id.messages_fragment);

        final UI activity = (UI) getActivity();

        // 如果是activity从堆栈恢复，FM中已经存在恢复而来的fragment，此时会使用恢复来的，而new出来这个会被丢弃掉
        fragment = (RecentContactsFragment) activity.addFragment(fragment);

        fragment.setCallback(new RecentContactsCallback() {
            @Override
            public void onRecentContactsLoaded() {
                // 最近联系人列表加载完毕
            }

            @Override
            public void onUnreadCountChange(int unreadCount) {
                ReminderManager.getInstance().updateSessionUnreadNum(unreadCount);
            }

            @Override
            public void onItemClick(RecentContact recent) {
                // 回调函数，以供打开会话窗口时传入定制化参数，或者做其他动作
                switch (recent.getSessionType()) {
                    case P2P:
                        SessionHelper.startP2PSession(getActivity(), recent.getContactId());
                        break;
                    case Team:
                        SessionHelper.startTeamSession(getActivity(), recent.getContactId());

                        break;
                    case System:
                        Map<String, Object> map = recent.getExtension();
                        int msgType = (int) map.get(Constants.KEY_MESSAGE_TYPE);

                        NimUIKit.getSystemMessageListener().onItemClickListener(getContext(), msgType);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public String getDigestOfAttachment(MsgAttachment attachment) {
                // 设置自定义消息的摘要消息，展示在最近联系人列表的消息缩略栏上
                // 当然，你也可以自定义一些内建消息的缩略语，例如图片，语音，音视频会话等，自定义的缩略语会被优先使用。
              /*  if (attachment instanceof GuessAttachment) {//猜拳
                    GuessAttachment guess = (GuessAttachment) attachment;
                    return guess.getValue().getDesc();
                } else if (attachment instanceof RTSAttachment) {
                    return "[白板]";
                } else  else if (attachment instanceof SnapChatAttachment) {
                    return "[阅后即焚]";
                }*/
                if (attachment instanceof StickerAttachment) {
                    return "[贴图]";
                }

                if (attachment instanceof VerifyContentAttachment) {
                    return "招呼信息";
                }
                if (attachment instanceof BusinessCardAttachment) {
                    return "分享了一个名片";
                }
                if (attachment instanceof WaterShareAttachment) {
                    WaterShareAttachment share = (WaterShareAttachment) attachment;
                    String shareFrom = share.getShareFrom();
                    if (TextUtils.equals("水塘", shareFrom)) {
                        return "分享了一个水塘";
                    } else if (TextUtils.equals("水帖", shareFrom)) {
                        return "分享了一个水帖";


                    } else if (TextUtils.equals("水宝", shareFrom)) {
                        return "分享了一个水宝";

                    }

                    return "分享";
                }
                return null;
            }

            @Override
            public String getDigestOfTipMsg(RecentContact recent) {
                String msgId = recent.getRecentMessageId();
                List<String> uuids = new ArrayList<>(1);
                uuids.add(msgId);
                List<IMMessage> msgs = NIMClient.getService(MsgService.class).queryMessageListByUuidBlock(uuids);
                if (msgs != null && !msgs.isEmpty()) {
                    IMMessage msg = msgs.get(0);
                    Map<String, Object> content = msg.getRemoteExtension();
                    if (content != null && !content.isEmpty()) {
                        return (String) content.get("content");
                    }
                }

                return null;
            }
        });
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_bar_search:
                GlobalSearchActivity.start(getContext());

                 return true;
*/

        }
        return false;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!MyUserCache.getGuide() && isVisibleToUser) {
//            showGuideView();

        }
    }


}
