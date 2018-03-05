package com.netease.nim.uikit.model;

import java.io.Serializable;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/10/12 15:41
 */

public class MemberActiveEntitiy implements Serializable {

    public boolean isActive(){
        return activeStatus == 1;
    }

    public boolean isOnlyActiveMember(){
        return codeType == 1;
    }

    public boolean isActiveAndGift(){
        return codeType == 2;
    }


    private int activeStatus;
    private int codeType;
    private String activeCode;
    private long uid;
    private Banner banner;

    public Banner getBanner(){
        return banner;
    }

    public int getActiveStatus() {
        return activeStatus;
    }

    public int getCodeType() {
        return codeType;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public long getUid() {
        return uid;
    }

    public class Banner implements Serializable{
        private String bannerPhoto;
        private String desc;
        private String price;
        private String title;

        public String getBannerPhoto() {
            return bannerPhoto;
        }

        public String getDesc() {
            return desc;
        }

        public String getPrice() {
            return price;
        }

        public String getTitle() {
            return title;
        }
    }

}
