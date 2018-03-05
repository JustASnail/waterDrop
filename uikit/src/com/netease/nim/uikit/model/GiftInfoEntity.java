package com.netease.nim.uikit.model;

/**
 * Created by dengxiaolei on 2017/10/10.
 */

public class GiftInfoEntity extends BaseResultEntity {

    /**
     * exchangeCode : 12345
     * expiryTimestamp : 1507627112000
     * giftId : 1
     * giftPhoto : http://oogijmB8%A6%E7%BB%93%E5%8F%91%E4%BA%86
     * giftTitle : 测试1
     * status : 0
     * uid : -1
     */

    private String exchangeCode;
    private long expiryTimestamp;
    private long giftId;
    private String giftDesc;
    private String giftPhoto;
    private String giftTitle;
    private int status;
    private int uid;

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public long getExpiryTimestamp() {
        return expiryTimestamp;
    }

    public void setExpiryTimestamp(long expiryTimestamp) {
        this.expiryTimestamp = expiryTimestamp;
    }

    public long getGiftId() {
        return giftId;
    }

    public void setGiftId(long giftId) {
        this.giftId = giftId;
    }

    public String getGiftPhoto() {
        return giftPhoto;
    }

    public void setGiftPhoto(String giftPhoto) {
        this.giftPhoto = giftPhoto;
    }

    public String getGiftTitle() {
        return giftTitle;
    }

    public void setGiftTitle(String giftTitle) {
        this.giftTitle = giftTitle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getGiftDesc() {
        return giftDesc;
    }

    public void setGiftDesc(String giftDesc) {
        this.giftDesc = giftDesc;
    }
}
