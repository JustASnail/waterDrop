package com.netease.nim.uikit.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HZH on 2017/7/5.
 */

public class AddressEntity extends BaseResultEntity {

    private int pageSize;
    private int totalSize;
    private List<ResultsBean> results;

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

    public static class ResultsBean implements Serializable {
        /**
         * addressId : 51
         * city : 市辖区
         * defaultFlag : 1
         * detail : 龙宫
         * district : 东城区
         * idcardBack :
         * idcardFront :
         * mobile : 11111111111
         * name : 明
         * prov : 北京市
         * uid : 14980969870711
         */

        private long addressId;
        private String city;
        private int defaultFlag;
        private String detail;
        private String district;
        private String idcardBack;
        private String idcardFront;
        private String idcardNo;
        private String mobile;
        private String name;
        private String prov;
        private long uid;

        public boolean isCompleteIdcard(){
            return !TextUtils.isEmpty(idcardBack) && !TextUtils.isEmpty(idcardFront) && !TextUtils.isEmpty(idcardNo);
        }

        public String getIdcardNo(){
            return idcardNo;
        }

        public long getAddressId() {
            return addressId;
        }

        public void setAddressId(long addressId) {
            this.addressId = addressId;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getDefaultFlag() {
            return defaultFlag;
        }

        public void setDefaultFlag(int defaultFlag) {
            this.defaultFlag = defaultFlag;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }



        public void setIdcardNo(String idcardNo) {
            this.idcardNo = idcardNo;
        }
    }
}
