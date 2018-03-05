package com.drops.waterdrop.model;

import java.io.Serializable;

/**
 * Created by dengxiaolei on 2017/6/4.
 */

public class TicketEntity implements Serializable {

    private String ticketType;
    private String name;
    private String getTime;
    private int imgId;
    private boolean isGeted;

    private String idCard;
    private int idCardFrontId;
    private int idCardBackId;
    private String area;
    private String address;
    private String userName;

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public boolean isGeted() {
        return isGeted;
    }

    public void setGeted(boolean geted) {
        isGeted = geted;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getIdCardFrontId() {
        return idCardFrontId;
    }

    public void setIdCardFrontId(int idCardFrontId) {
        this.idCardFrontId = idCardFrontId;
    }

    public int getIdCardBackId() {
        return idCardBackId;
    }

    public void setIdCardBackId(int idCardBackId) {
        this.idCardBackId = idCardBackId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
