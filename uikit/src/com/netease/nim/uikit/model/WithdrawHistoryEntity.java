package com.netease.nim.uikit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Smile on 2017/8/24.
 */

public class WithdrawHistoryEntity implements Serializable{

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
         * bankName : 招商银行
         * cardCity : 杭州市
         * cardDeposit : 高新支行
         * userName : 方烈
         * cardNo : 6266095710766945
         * cardProv : 浙江省
         * errorMsg : 如果有错误的话，这里是错误信息
         * dealId : 15039033812781
         * dealPrice : 20
         * status : 0
         * uid : 38484159
         * "type": 1, // 供应商的话，type等于1，超级会员type等于2
         */

        private String bankName;
        private String cardCity;
        private String cardDeposit;
        private String userName;
        private String cardNo;
        private String cardProv;
        private String errorMsg;
        private long dealId;
        private double dealPrice;
        private int status;
        private int uid;
        private int type;
        private String createTime;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCardCity() {
            return cardCity;
        }

        public void setCardCity(String cardCity) {
            this.cardCity = cardCity;
        }

        public String getCardDeposit() {
            return cardDeposit;
        }

        public void setCardDeposit(String cardDeposit) {
            this.cardDeposit = cardDeposit;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCardProv() {
            return cardProv;
        }

        public void setCardProv(String cardProv) {
            this.cardProv = cardProv;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public long getDealId() {
            return dealId;
        }

        public void setDealId(long dealId) {
            this.dealId = dealId;
        }

        public double getDealPrice() {
            return dealPrice;
        }

        public void setDealPrice(double dealPrice) {
            this.dealPrice = dealPrice;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
