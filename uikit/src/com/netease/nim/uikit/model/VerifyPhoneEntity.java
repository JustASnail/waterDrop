package com.netease.nim.uikit.model;

/**
 * Created by HZH on 2017/7/5.
 */

public class VerifyPhoneEntity extends BaseResultEntity {

    /**
     * account : 18657172831
     * nationCode : 86
     * registerStatus : 0
     * uid : -1
     */
    //  注册状态，0表示没有注册，1表示已经注册
    private String account;
    private String nationCode;
    private int registerStatus;
    private int uid;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
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
