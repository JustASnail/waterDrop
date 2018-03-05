package com.drops.waterdrop.im.tab;

import com.drops.waterdrop.R;
import com.drops.waterdrop.im.fragment.MainTabFragment;
import com.drops.waterdrop.im.fragment.SessionListFragment;
import com.drops.waterdrop.im.reminder.ReminderId;
import com.drops.waterdrop.ui.find.fragment.FindFragment;
import com.drops.waterdrop.ui.pool.fragment.PoolFragment;
import com.drops.waterdrop.ui.mine.fragment.MineFragment;
import com.drops.waterdrop.ui.store.fragment.StoreSelfFragment;

/**
 * Created by dengxiaolei on 2017/4/10.
 */

public enum MainTab {
    //底部导航信息 消息、发现、首页、活动、我的
    RECENT_CONTACTS(0, ReminderId.SESSION, SessionListFragment.class, R.string.main_tab_session, R.layout.fragment_recent_msg_list),
    FIND(1, ReminderId.INVALID, FindFragment.class, R.string.main_tab_shui_tie, R.layout.fragment_find),
    HOME(2, ReminderId.INVALID, PoolFragment.class, R.string.main_tab_pool, R.layout.fragment_pool),
    ACTIV(3,ReminderId.INVALID, StoreSelfFragment.class, R.string.main_tab_store, R.layout.fragment_store_self),
    MINE(4,ReminderId.INVALID, MineFragment.class, R.string.main_tab_mine, R.layout.fragment_mine);

    public final int tabIndex;

    public final int reminderId;

    public final Class<? extends MainTabFragment> clazz;

    public final int resId;

    public final int fragmentId;

    public final int layoutId;

    MainTab(int index, int reminderId, Class<? extends MainTabFragment> clazz, int resId, int layoutId) {
        this.tabIndex = index;
        this.reminderId = reminderId;
        this.clazz = clazz;
        this.resId = resId;
        this.fragmentId = index;
        this.layoutId = layoutId;
    }

    public static final MainTab fromReminderId(int reminderId) {
        for (MainTab value : MainTab.values()) {
            if (value.reminderId == reminderId) {
                return value;
            }
        }

        return null;
    }

    public static final MainTab fromTabIndex(int tabIndex) {
        for (MainTab value : MainTab.values()) {
            if (value.tabIndex == tabIndex) {
                return value;
            }
        }

        return null;
    }

}
