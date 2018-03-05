package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/9/12.
 */

public class RecommendFriendEntity extends BaseResultEntity {

    /**
     * nextSearchStart :
     * pageSize : 10
     * results : [{"idCode":36468101,"neteaseAccid":"yuanshi_36468101","neteaseToken":"581828de52b7b49c7dfc10ca1ebb13c5","nickName":"㌍㌫㌶㍊㍍㍑㌫㌶㍍㍗.","photo":"http://wx.qlogo.cn/mmopen/XibcsBL2AXuw3nX4E0YJBMosZ6Pia2DMlUj60M9osPk4F4fJGicuWCMoP5J0hUfiaicY2TMuG3Zw1TZuLQQL2Y6MbFFicde8cRjzAo/0","relationStatus":0,"uid":36468101,"userDesc":""},{"idCode":10352848,"neteaseAccid":"yuanshi_10352848","neteaseToken":"4c8fb423969e02783597afd92f3cb206","nickName":"13713757237","photo":"","relationStatus":0,"uid":10352848,"userDesc":""}]
     * totalSize : 10
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
         * idCode : 36468101
         * neteaseAccid : yuanshi_36468101
         * neteaseToken : 581828de52b7b49c7dfc10ca1ebb13c5
         * nickName : ㌍㌫㌶㍊㍍㍑㌫㌶㍍㍗.
         * photo : http://wx.qlogo.cn/mmopen/XibcsBL2AXuw3nX4E0YJBMosZ6Pia2DMlUj60M9osPk4F4fJGicuWCMoP5J0hUfiaicY2TMuG3Zw1TZuLQQL2Y6MbFFicde8cRjzAo/0
         * relationStatus : 0
         * uid : 36468101
         * userDesc :
         */

        private int idCode;
        private String neteaseAccid;
        private String neteaseToken;
        private String nickName;
        private String photo;
        private int relationStatus;
        private int uid;
        private String userDesc;

        public int getIdCode() {
            return idCode;
        }

        public void setIdCode(int idCode) {
            this.idCode = idCode;
        }

        public String getNeteaseAccid() {
            return neteaseAccid;
        }

        public void setNeteaseAccid(String neteaseAccid) {
            this.neteaseAccid = neteaseAccid;
        }

        public String getNeteaseToken() {
            return neteaseToken;
        }

        public void setNeteaseToken(String neteaseToken) {
            this.neteaseToken = neteaseToken;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getRelationStatus() {
            return relationStatus;
        }

        public void setRelationStatus(int relationStatus) {
            this.relationStatus = relationStatus;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getUserDesc() {
            return userDesc;
        }

        public void setUserDesc(String userDesc) {
            this.userDesc = userDesc;
        }
    }
}
