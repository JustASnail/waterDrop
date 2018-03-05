package com.netease.nim.uikit.model;

/**
 * Created by dengxiaolei on 2017/7/12.
 */

public class AddFriendForUid extends BaseResultEntity {

    /**
     * idCode : 8242697
     * neteaseAccid : yuanshi_14980969870711
     * neteaseToken : 406b89634c1f0c9e836bdeffe1a2daca
     * nickName : Alright
     * photo : http://oogijmhwg.bkt.clouddn.com/c19c58469c438fd25e94e0a6a5a12112.png
     * relationStatus : 0
     * uid : 14980969870711
     * userDesc : Keep holding on...
     */

    private int idCode;
    private String neteaseAccid;
    private String neteaseToken;
    private String nickName;
    private String photo;
    private int relationStatus;
    private long uid;
    private String userDesc;

    public int getIdCode() {
        return idCode;
    }

    public void setIdCode(int idCode) {
        this.idCode = idCode;
    }

    public String getNeteaseAccid() {
        return neteaseAccid;
    }

    public void setNeteaseAccid(String neteaseAccid) {
        this.neteaseAccid = neteaseAccid;
    }

    public String getNeteaseToken() {
        return neteaseToken;
    }

    public void setNeteaseToken(String neteaseToken) {
        this.neteaseToken = neteaseToken;
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

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }
}
