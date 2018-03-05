package com.netease.nim.uikit.model;

/**
 * Created by dengxiaolei on 2017/6/13.
 */

public class FriendEntity extends BaseResultEntity {
    /**
     * mobile : 13588706339
     * nickName : 方烈
     * photo : http:///authenticPhoto65FM1.jpg
     * registerStatus : 1
     * relationStatus : -1
     * uid : 14967746218421
     */

    private String mobile;
    private String nickName;
    private String photo;
    private String note;
    private int registerStatus;
    private int relationStatus;
    private long uid;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(int registerStatus) {
        this.registerStatus = registerStatus;
    }

    public int getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(int relationStatus) {
        this.relationStatus = relationStatus;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
