package com.netease.nim.uikit.model;

/**
 * Created by Mr.Smile on 2017/7/21.
 */

public class OrderStatusEntity {

    /**
     * orderId : 14974677629131
     * payNo : dfqe
     * payTime : 2017-06-15 10:35:50
     * status : 10
     * uid : 14973382240861
     */

    private long orderId;
    private String payNo;
    private String payTime;
    private int status;
    private long uid;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
