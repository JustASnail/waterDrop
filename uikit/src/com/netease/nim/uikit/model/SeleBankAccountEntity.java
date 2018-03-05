package com.netease.nim.uikit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Smile on 2017/8/25.
 */

public class SeleBankAccountEntity implements Serializable{
    /**
     * nextSearchStart :
     * pageSize : 1
     * results : [{"bankName":"招商银行","cardCity":"杭州市","cardDeposit":"高新支行","cardId":15038084555331,"cardNo":"6266095710766945","cardProv":"浙江省","status":0,"uid":38484159,"userName":"温朝凯"}]
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
         * bankName : 招商银行
         * cardCity : 杭州市
         * cardDeposit : 高新支行
         * cardId : 15038084555331
         * cardNo : 6266095710766945
         * cardProv : 浙江省
         * status : 0
         * uid : 38484159
         * userName : 温朝凯
         */

        private String cardPhoto;
        private String bankName;
        private String cardCity;
        private String cardDeposit;
        private long cardId;
        private String cardNo;
        private String cardProv;
        private int status;
        private int uid;
        private String userName;
        private boolean selected;

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

        public long getCardId() {
            return cardId;
        }

        public void setCardId(long cardId) {
            this.cardId = cardId;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getCardPhoto() {
            return cardPhoto;
        }

        public void setCardPhoto(String cardPhoto) {
            this.cardPhoto = cardPhoto;
        }
    }

}
