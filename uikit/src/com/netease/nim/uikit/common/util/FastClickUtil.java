package com.netease.nim.uikit.common.util;

/**
 * Created by dengxiaolei on 2017/6/1.
 */

public class FastClickUtil {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
