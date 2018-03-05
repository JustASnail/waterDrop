package com.netease.nim.uikit.model;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Created by dengxiaolei on 2017/6/14.
 */

public class MyFriendEntity implements IndexableEntity {

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
    private String fUserDesc;
    private int relationStatus;
    private String fNeteaseAccid;
    private String fNeteaseToken;

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

    public String getfUserDesc() {
        return fUserDesc;
    }

    public void setfUserDesc(String fUserDesc) {
        this.fUserDesc = fUserDesc;
    }

    public String getfNeteaseAccid() {
        return fNeteaseAccid;
    }

    public void setfNeteaseAccid(String fNeteaseAccid) {
        this.fNeteaseAccid = fNeteaseAccid;
    }

    public String getfNeteaseToken() {
        return fNeteaseToken;
    }

    public void setfNeteaseToken(String fNeteaseToken) {
        this.fNeteaseToken = fNeteaseToken;
    }

    @Override
    public String getFieldIndexBy() {
        return fNickName;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.fNickName = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {

    }
}
