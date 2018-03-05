package com.netease.nim.uikit.model;

/**
 * Created by dengxiaolei on 2017/7/19.
 */

public class VerifyOpenId extends BaseResultEntity {

    /**
     * account : dkajldjfadfjkal
     * registerStatus : 0
     * uid : -1
     */

    private String account;
    private int registerStatus;
    private int uid;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(int registerStatus) {
        this.registerStatus = registerStatus;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
