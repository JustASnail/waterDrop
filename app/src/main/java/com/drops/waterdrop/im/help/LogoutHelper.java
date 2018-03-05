package com.drops.waterdrop.im.help;

import com.drops.waterdrop.app.AppCache;
import com.netease.nim.uikit.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

/**
 * 注销帮助类
 * Created by huangjun on 2015/10/8.
 */
public class LogoutHelper {
    public static void logout() {
        // 清理缓存&注销监听&清除状态
        NimUIKit.clearCache();
//        ChatRoomHelper.logout(); //聊天室退出
        AppCache.clear();
        MyUserCache.saveIMAccount("");
        MyUserCache.saveIMToken("");
        MyUserCache.saveUserUid(1);
        MyUserCache.saveUserToken("");
        MyUserCache.saveUserLikes(null);

        NIMClient.getService(AuthService.class).logout();

        LoginSyncDataStatusObserver.getInstance().reset();

        // TODO: 2017/8/22  服务器退出
//        DropManager.getInstance().destroy();//红点

    }
}
