package com.drops.waterdrop.model;

/**
 * Created by dengxiaolei on 2017/6/5.
 */

public enum OrderState {

//     "status": 订单状态，0表示提交订单待付款，10表示订单已经付款完成， 11表示待收货，20 表示已经完成收货，
// 30表示已经完成评价，90 表示订单取消，99表示删除订单 ,70表示退换货

    All,//全部  -1 默认
    NO_PAYMENT,//待付款 0
    NO_DISPATCH,//待发货 10
    NO_RECEIVE,//待收货 11
    NO_COMMENT,//待评价 20  放在已完成列表里面
    FINISH,//已完成评价 30
    CANCEL,//取消 90
    DELETE,//删除 99
    EXCHANGE,//退换  70
}
