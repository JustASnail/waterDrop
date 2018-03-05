package com.drops.waterdrop.app;

import android.content.Context;
import android.content.Intent;

import com.drops.waterdrop.util.Singleton;

/**
 * crash异常log捕获
 * 捕获到的log会保存到sdcard文件里
 *
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context context;
    private Intent crashIntent;

    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler defaultHandler;

    public static CrashHandler get(){
        return gDefault.get();
    }

    // 初始化
    public void init(Context context, Intent crashIntent) {
        this.context = context;
        this.crashIntent = crashIntent;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
        if (!handleException(arg1) && defaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(arg0, arg1);
        } else {

            // 跳转到指定Activity
            crashIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(crashIntent);
            System.exit(1);
        }
    }

    // 处理异常
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // TODO: 2017/8/17 以后可以做保存log信息到本地

        return true;
    }

    private static final Singleton<CrashHandler> gDefault = new Singleton<CrashHandler>() {
        @Override
        protected CrashHandler create() {
            return new CrashHandler();
        }
    };
}