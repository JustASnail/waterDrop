package com.netease.nim.uikit.green_dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dengxiaolei on 2017/6/22.
 */

@Entity()
public class FriendDB {
    @Id
    private long fUid;

    private String fNeteaseAccid;
    private String fNeteaseToken;
    private String fNickName;
    private String fPhoto;
    private String note;
    private String fUserDesc;
    private int relationStatus;
    @Generated(hash = 1532509879)
    public FriendDB(long fUid, String fNeteaseAccid, String fNeteaseToken,
            String fNickName, String fPhoto, String note, String fUserDesc,
            int relationStatus) {
        this.fUid = fUid;
        this.fNeteaseAccid = fNeteaseAccid;
        this.fNeteaseToken = fNeteaseToken;
        this.fNickName = fNickName;
        this.fPhoto = fPhoto;
        this.note = note;
        this.fUserDesc = fUserDesc;
        this.relationStatus = relationStatus;
    }
    @Generated(hash = 1469182943)
    public FriendDB() {
    }
    public long getFUid() {
        return this.fUid;
    }
    public void setFUid(long fUid) {
        this.fUid = fUid;
    }
    public String getFNeteaseAccid() {
        return this.fNeteaseAccid;
    }
    public void setFNeteaseAccid(String fNeteaseAccid) {
        this.fNeteaseAccid = fNeteaseAccid;
    }
    public String getFNeteaseToken() {
        return this.fNeteaseToken;
    }
    public void setFNeteaseToken(String fNeteaseToken) {
        this.fNeteaseToken = fNeteaseToken;
    }
    public String getFNickName() {
        return this.fNickName;
    }
    public void setFNickName(String fNickName) {
        this.fNickName = fNickName;
    }
    public String getFPhoto() {
        return this.fPhoto;
    }
    public void setFPhoto(String fPhoto) {
        this.fPhoto = fPhoto;
    }
    public String getNote() {
        return this.note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getFUserDesc() {
        return this.fUserDesc;
    }
    public void setFUserDesc(String fUserDesc) {
        this.fUserDesc = fUserDesc;
    }
    public int getRelationStatus() {
        return this.relationStatus;
    }
    public void setRelationStatus(int relationStatus) {
        this.relationStatus = relationStatus;
    }

}
