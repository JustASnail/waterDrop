package com.netease.nim.uikit.model;

/**
 * Created by dengxiaolei on 2017/6/15.
 */

public class FFriendEntity extends BaseResultEntity {

    /**
     * fNickName : 方烈
     * fPhoto : http:///authenticPhoto65FM1.jpg
     * fUid : 14967746218421
     * note :
     * relationStatus : 0
     */

    private String fNickName;
    private String fPhoto;
    private long fUid;
    private String note;
    private int relationStatus;

    public String getFNickName() {
        return fNickName;
    }

    public void setFNickName(String fNickName) {
        this.fNickName = fNickName;
    }

    public String getFPhoto() {
        return fPhoto;
    }

    public void setFPhoto(String fPhoto) {
        this.fPhoto = fPhoto;
    }

    public long getFUid() {
        return fUid;
    }

    public void setFUid(long fUid) {
        this.fUid = fUid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(int relationStatus) {
        this.relationStatus = relationStatus;
    }
}
