package com.netease.nim.uikit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengxiaolei on 2017/10/10.
 */

public class CashGiftRecordEntity extends BaseResultEntity {

    /**
     * nextSearchStart :
     * pageSize : 1
     * results : [{"deliveryAddress":"你你是在说我","deliveryAddressId":140,"deliveryCity":"市辖区","deliveryDistrict":"东城区","deliveryMobile":"11111111111","deliveryName":"黄志恒","deliveryProv":"北京市","exchangeCode":"12345","exchangeTimestamp":1507454820000,"expiryTimestamp":1507627112000,"giftDesc":"131","giftId":1,"giftPhoto":"http://oogF%E5%B8%A6%E7%BB%93%E5%8F%91%E4%BA%86","giftTitle":"测试1","idcardBack":"http://oo4cbf688d746e994125e28.png","idcardFront":"http://o7051873b0e8e6f09372.png","idcardNo":"410782199401069552","status":1,"uid":54893635}]
     * totalSize : 1
     */

    private String nextSearchStart;
    private int pageSize;
    private int totalSize;
    private List<ResultsBean> results;

    public String getNextSearchStart() {
        return nextSearchStart;
    }

    public void setNextSearchStart(String nextSearchStart) {
        this.nextSearchStart = nextSearchStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable{
        /**
         * deliveryAddress : 你你是在说我
         * deliveryAddressId : 140
         * deliveryCity : 市辖区
         * deliveryDistrict : 东城区
         * deliveryMobile : 11111111111
         * deliveryName : 黄志恒
         * deliveryProv : 北京市
         * exchangeCode : 12345
         * exchangeTimestamp : 1507454820000
         * expiryTimestamp : 1507627112000
         * giftDesc : 131
         * giftId : 1
         * giftPhoto : http://oogF%E5%B8%A6%E7%BB%93%E5%8F%91%E4%BA%86
         * giftTitle : 测试1
         * idcardBack : http://oo4cbf688d746e994125e28.png
         * idcardFront : http://o7051873b0e8e6f09372.png
         * idcardNo : 410782199401069552
         * status : 1
         * uid : 54893635
         */

        private String deliveryAddress;
        private int deliveryAddressId;
        private String deliveryCity;
        private String deliveryDistrict;
        private String deliveryMobile;
        private String deliveryName;
        private String deliveryProv;
        private String exchangeCode;
        private long exchangeTimestamp;
        private long expiryTimestamp;
        private String giftDesc;
        private long giftId;
        private String giftPhoto;
        private String giftTitle;
        private String idcardBack;
        private String idcardFront;
        private String idcardNo;
        private int status;
        private int uid;

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }

        public int getDeliveryAddressId() {
            return deliveryAddressId;
        }

        public void setDeliveryAddressId(int deliveryAddressId) {
            this.deliveryAddressId = deliveryAddressId;
        }

        public String getDeliveryCity() {
            return deliveryCity;
        }

        public void setDeliveryCity(String deliveryCity) {
            this.deliveryCity = deliveryCity;
        }

        public String getDeliveryDistrict() {
            return deliveryDistrict;
        }

        public void setDeliveryDistrict(String deliveryDistrict) {
            this.deliveryDistrict = deliveryDistrict;
        }

        public String getDeliveryMobile() {
            return deliveryMobile;
        }

        public void setDeliveryMobile(String deliveryMobile) {
            this.deliveryMobile = deliveryMobile;
        }

        public String getDeliveryName() {
            return deliveryName;
        }

        public void setDeliveryName(String deliveryName) {
            this.deliveryName = deliveryName;
        }

        public String getDeliveryProv() {
            return deliveryProv;
        }

        public void setDeliveryProv(String deliveryProv) {
            this.deliveryProv = deliveryProv;
        }

        public String getExchangeCode() {
            return exchangeCode;
        }

        public void setExchangeCode(String exchangeCode) {
            this.exchangeCode = exchangeCode;
        }

        public long getExchangeTimestamp() {
            return exchangeTimestamp;
        }

        public void setExchangeTimestamp(long exchangeTimestamp) {
            this.exchangeTimestamp = exchangeTimestamp;
        }

        public long getExpiryTimestamp() {
            return expiryTimestamp;
        }

        public void setExpiryTimestamp(long expiryTimestamp) {
            this.expiryTimestamp = expiryTimestamp;
        }

        public String getGiftDesc() {
            return giftDesc;
        }

        public void setGiftDesc(String giftDesc) {
            this.giftDesc = giftDesc;
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

        public String getIdcardBack() {
            return idcardBack;
        }

        public void setIdcardBack(String idcardBack) {
            this.idcardBack = idcardBack;
        }

        public String getIdcardFront() {
            return idcardFront;
        }

        public void setIdcardFront(String idcardFront) {
            this.idcardFront = idcardFront;
        }

        public String getIdcardNo() {
            return idcardNo;
        }

        public void setIdcardNo(String idcardNo) {
            this.idcardNo = idcardNo;
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
    }
}
