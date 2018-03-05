package com.drops.waterdrop.ui.other.view;


import com.drops.waterdrop.im.reminder.ReminderItem;

/**
 * Created by dengxiaolei on 2017/4/24.
 */

public interface IMainView {
    void onUnreadNumChanged(int tabIndex, ReminderItem item);
}
