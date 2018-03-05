package com.drops.waterdrop.ui.mine.event;

import com.drops.waterdrop.model.OrderState;

/**
 * Created by Mr.Smile on 2017/7/18.
 */

public class UserInfoEvent {
    public UserInfoEvent() {
    }

    public int orderNum;
    public OrderState orderState;
    public int type;    //0表示订单的  1表示足迹的  2表示金豆的
    public String beans;
    public int footPrintPost;
    public int footPrintPool;
    public boolean notify;
    public boolean cannotBack;
}
