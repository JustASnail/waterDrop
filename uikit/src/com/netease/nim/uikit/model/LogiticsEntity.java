package com.netease.nim.uikit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Smile on 2017/7/8.
 */

public class LogiticsEntity implements Serializable{

    /**
     * deliveryCode : shunfeng
     * deliveryCompany : 顺丰
     * deliveryNo : 929277599546
     * deliveryPhone :
     * deliveryPhoto :
     * deliveryStatus : 1
     * logistics : [{"content":"已签收,感谢使用顺丰,期待再次为您服务","deliveryNo":"929277599546","deliveryProviderCode":"shunfeng","gmtCreated":1499412191000,"gmtModified":1499412191000,"id":21,"time":"2017-07-06 14:31:10"},{"content":"快件交给邵远志，正在派送途中（联系电话：13575910703）","deliveryNo":"929277599546","deliveryProviderCode":"shunfeng","gmtCreated":1499412191000,"gmtModified":1499412191000,"id":22,"time":"2017-07-06 14:31:00"}]
     */

    private String deliveryCode;
    private String deliveryCompany;
    private String deliveryNo;
    private String deliveryPhone;
    private String deliveryPhoto;
    private int deliveryStatus;
    private String goodPhoto;           //自己加的商品图片
    private List<LogisticsBean> logistics;

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getDeliveryPhoto() {
        return deliveryPhoto;
    }

    public void setDeliveryPhoto(String deliveryPhoto) {
        this.deliveryPhoto = deliveryPhoto;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public List<LogisticsBean> getLogistics() {
        return logistics;
    }

    public void setLogistics(List<LogisticsBean> logistics) {
        this.logistics = logistics;
    }

    public String getGoodPhoto() {
        return goodPhoto;
    }

    public void setGoodPhoto(String goodPhoto) {
        this.goodPhoto = goodPhoto;
    }

    public static class LogisticsBean implements Serializable{
        /**
         * content : 已签收,感谢使用顺丰,期待再次为您服务
         * deliveryNo : 929277599546
         * deliveryProviderCode : shunfeng
         * gmtCreated : 1499412191000
         * gmtModified : 1499412191000
         * id : 21
         * time : 2017-07-06 14:31:10
         */

        private String content;
        private String deliveryNo;
        private String deliveryProviderCode;
        private long gmtCreated;
        private long gmtModified;
        private int id;
        private String time;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDeliveryNo() {
            return deliveryNo;
        }

        public void setDeliveryNo(String deliveryNo) {
            this.deliveryNo = deliveryNo;
        }

        public String getDeliveryProviderCode() {
            return deliveryProviderCode;
        }

        public void setDeliveryProviderCode(String deliveryProviderCode) {
            this.deliveryProviderCode = deliveryProviderCode;
        }

        public long getGmtCreated() {
            return gmtCreated;
        }

        public void setGmtCreated(long gmtCreated) {
            this.gmtCreated = gmtCreated;
        }

        public long getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(long gmtModified) {
            this.gmtModified = gmtModified;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
