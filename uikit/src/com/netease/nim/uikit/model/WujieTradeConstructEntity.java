package com.netease.nim.uikit.model;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/08 15:00
 */

public class WujieTradeConstructEntity extends BaseResultEntity {

    private long orderId;
    private String payOrderNo;
    private String wjOrderNo;
    private String wjPayWayUrl;

    public long getOrderId() {
        return orderId;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public String getWjOrderNo() {
        return wjOrderNo;
    }

    public String getWjPayWayUrl() {
        return wjPayWayUrl;
    }
}
