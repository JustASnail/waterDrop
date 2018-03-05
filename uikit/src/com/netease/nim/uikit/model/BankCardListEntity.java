package com.netease.nim.uikit.model;

import android.support.annotation.ColorRes;
import android.text.TextUtils;

import com.netease.nim.uikit.R;

import java.util.List;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/29 22:43
 */

public class BankCardListEntity extends BaseResultEntity {

    private String nextSearchStart;
    private int pageSize;
    private List<BankCard> results;
    private int totalSize;

    public String getNextSearchStart() {
        return nextSearchStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<BankCard> getResults() {
        return results;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public class BankCard extends BaseResultEntity{
        private String bankName;
        private String cardCity;
        private String cardDeposit;
        private long cardId;
        private String cardNo;
        private String cardProv;
        private String cardPhoto;
        private int status;
        private long uid;
        private String userName;
        private String createTime;
        private String createTimestamp;
        private String submitPhoto;
        private int type;

        public String getCardPhoto() {
            return cardPhoto;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getCreateTimestamp() {
            return createTimestamp;
        }

        public String getSubmitPhoto(){
            return submitPhoto;
        }

        public String getBankName() {
            return bankName;
        }

        public String getCardCity() {
            return cardCity;
        }

        public String getCardDeposit() {
            return cardDeposit;
        }

        public long getCardId() {
            return cardId;
        }

        public String getCardNo() {
            return cardNo;
        }

        public String getCardProv() {
            return cardProv;
        }

        public int getStatus() {
            return status;
        }

        public long getUid() {
            return uid;
        }

        public String getUserName() {
            return userName;
        }

        public boolean isReviewing(){
            return status == 0;
        }

        public boolean isReviewSuccess(){
            return status == 1;
        }

        public boolean isReviewFail(){
            return status == 90;
        }

        public String getReviewState(){
            if (isReviewing())
                return "审核中";
            if (isReviewSuccess())
                return "绑定成功";
            if (isReviewFail())
                return "绑定失败";
            return "";
        }

        public int getReviewStateColor(){
            if (isReviewSuccess())
                return R.color.c_green;
            if (isReviewFail())
                return R.color.c_red;
            return R.color.c_grey_8;
        }

        public String getCardNoForFormat1(){
            if (TextUtils.isEmpty(cardNo))
                return cardNo;
            return cardNo.replaceAll("\\d{4}(?!$)", "$0 ");
        }

        public String getCardNoForFormat2(){
            if (TextUtils.isEmpty(cardNo) || cardNo.length() < 4)
                return cardNo;
            return "**** **** **** " + cardNo.substring(cardNo.length() - 4, cardNo.length());
        }

        public int getType() {
            return type;
        }
    }

}
