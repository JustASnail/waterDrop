package com.netease.nim.uikit.model;

import java.util.List;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/31 15:25
 */

public class BankCardNoListEntity extends BaseResultEntity {

    private String nextSearchStart;
    private int pageSize;
    private List<BankCardNo> results;
    private int totalSize;

    public String getNextSearchStart() {
        return nextSearchStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<BankCardNo> getResults() {
        return results;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public class BankCardNo extends BaseResultEntity{
        private String bankName;
        private String cardName;
        private String cardPrefix;
        private String cardPhoto;

        public String getCardPhoto(){
            return cardPhoto;
        }

        public String getBankName() {
            return bankName;
        }

        public String getCardName() {
            return cardName;
        }

        public String getCardPrefix() {
            return cardPrefix;
        }
    }
}
