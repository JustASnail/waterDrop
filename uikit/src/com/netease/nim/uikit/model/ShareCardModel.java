package com.netease.nim.uikit.model;

import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import java.io.Serializable;

/**
 * Created by dengxiaolei on 2017/7/22.
 */

public class ShareCardModel implements Serializable {

    private String receiveAccount;
    private String receiveUserName;
    private String receiveUserPhoto;

    private SessionTypeEnum sessionTypeEnum;

    private int shareType;

    private String shareId;

    private String shareTitle;

    private String sharePhoto;

    private String shareContent;

    private String shareFrom;

    private String fromName;

    private String tipUrl;

    private long poolId;

    private long tipId;

    private String cardUserNickName;
    private String cardUserPhoto;
    private long cardUserUid;
    private int cardUserSex;

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getReceiveUserPhoto() {
        return receiveUserPhoto;
    }

    public void setReceiveUserPhoto(String receiveUserPhoto) {
        this.receiveUserPhoto = receiveUserPhoto;
    }

    public String getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public SessionTypeEnum getSessionTypeEnum() {
        return sessionTypeEnum;
    }

    public void setSessionTypeEnum(SessionTypeEnum sessionTypeEnum) {
        this.sessionTypeEnum = sessionTypeEnum;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getSharePhoto() {
        return sharePhoto;
    }

    public void setSharePhoto(String sharePhoto) {
        this.sharePhoto = sharePhoto;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareFrom() {
        return shareFrom;
    }

    public void setShareFrom(String shareFrom) {
        this.shareFrom = shareFrom;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getTipUrl() {
        return tipUrl;
    }

    public void setTipUrl(String tipUrl) {
        this.tipUrl = tipUrl;
    }

    public long getPoolId() {
        return poolId;
    }

    public void setPoolId(long poolId) {
        this.poolId = poolId;
    }

    public long getTipId() {
        return tipId;
    }

    public void setTipId(long tipId) {
        this.tipId = tipId;
    }

    public String getCardUserNickName() {
        return cardUserNickName;
    }

    public void setCardUserNickName(String cardUserNickName) {
        this.cardUserNickName = cardUserNickName;
    }

    public long getCardUserUid() {
        return cardUserUid;
    }

    public void setCardUserUid(long cardUserUid) {
        this.cardUserUid = cardUserUid;
    }

    public int getCardUserSex() {
        return cardUserSex;
    }

    public void setCardUserSex(int cardUserSex) {
        this.cardUserSex = cardUserSex;
    }

    public String getCardUserPhoto() {
        return cardUserPhoto;
    }

    public void setCardUserPhoto(String cardUserPhoto) {
        this.cardUserPhoto = cardUserPhoto;
    }
}
