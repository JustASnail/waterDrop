package com.drops.waterdrop.ui.session.contact;

import com.netease.nim.uikit.UIKitLogTag;
import com.netease.nim.uikit.common.util.log.LogUtil;

/**
 * Created by dengxiaolei on 2017/5/11.
 */

public class ReloadFrequencyControl {
    boolean isReloading = false;
    boolean needReload = false;
    boolean reloadParam = false;

    public boolean canDoReload(boolean param) {
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

    public boolean continueDoReloadWhenCompleted() {
        return needReload;
    }

    public void resetStatus() {
        isReloading = false;
        needReload = false;
        reloadParam = false;
    }

    public boolean getReloadParam() {
        return reloadParam;
    }
}
