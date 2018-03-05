package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/8/24.
 */

public class AccountDetaiEntity {

    /**
     * nextSearchStart :
     * pageSize : 1
     * results : [{"createTimestamp":1504061173000,"createTime":"2018-01-01 12:00:00","comment":"如果存在则是快递单号","id":1,"price":-20,"status":1,"type":3,"uid":38484159}]
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

    public static class ResultsBean {
        /**
         * createTimestamp : 1504061173000
         * createTime : 2018-01-01 12:00:00
         * comment : 如果存在则是快递单号
         * id : 1
         * price : -20
         * status : 1
         * type : 3
         * uid : 38484159
         * identityType   // 用户身份类型，1表示供应商，2表示超级会员
         * title
         */

        private long createTimestamp;
        private String createTime;
        private String title;
        private String comment;
        private int id;
        private String price;
        private int status;
        private int type;
        private int identityType;
        private int uid;

        public long getCreateTimestamp() {
            return createTimestamp;
        }

        public void setCreateTimestamp(long createTimestamp) {
            this.createTimestamp = createTimestamp;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getIdentityType() {
            return identityType;
        }

        public void setIdentityType(int identityType) {
            this.identityType = identityType;
        }

        public String getTitle() {
            return title;
        }
    }
}
