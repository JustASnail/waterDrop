package com.netease.nim.uikit.green_dao;


import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.gen.DaoMaster;
import com.netease.nim.uikit.gen.DaoSession;

public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;

    private GreenDaoManager() {

    }

    /**
     * 静态内部类，实例化对象使用
     */
    private static class SingleInstanceHolder {
        private static final GreenDaoManager INSTANCE = new GreenDaoManager();
    }

    /**
     * 对外唯一实例的接口
     *
     * @return
     */
    public static GreenDaoManager getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }

    /**
     * 初始化数据
     */
    public void init(String account) {
        MyOpenHelper helper = new MyOpenHelper(NimUIKit.getContext(),account,null);
        mDevOpenHelper = new DaoMaster.DevOpenHelper(NimUIKit.getContext(),
                account);
        mDaoMaster = new DaoMaster(mDevOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }




    public DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getmDaoSession() {
        if (mDaoSession == null) {
            init(MyUserCache.getIMAccount());
        }
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }


}