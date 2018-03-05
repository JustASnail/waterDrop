package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/8/1.
 */

public class SearchFriendEntity {

    /**
     * nextSearchStart :
     * pageSize : 2
     * results : [{"idCode":54893635,"neteaseAccid":"yuanshi_54893635","neteaseToken":"a7864e3e53da38f2d3a3537f5b7bc48a","nickName":"Alright","photo":"http://oogijmhwg.bkt.clouddn.com/22fcd5a7b1641a4152335881dc9bb177.png","relationStatus":0,"uid":54893635,"userDesc":"Keep holding on..."},{"idCode":66289145,"neteaseAccid":"yuanshi_66289145","neteaseToken":"2afd6965a8a3381b4d42e8576375cf60","nickName":"Alright","photo":"http://wx.qlogo.cn/mmhead/Q3auHgzwzM4vLT9K4vdPtwTTribUCxuib7tOsgDGyUfoAPj5G4A5VfZQ/0","relationStatus":0,"uid":66289145,"userDesc":""}]
     * totalSize : 2
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
         * idCode : 54893635
         * neteaseAccid : yuanshi_54893635
         * neteaseToken : a7864e3e53da38f2d3a3537f5b7bc48a
         * nickName : Alright
         * photo : http://oogijmhwg.bkt.clouddn.com/22fcd5a7b1641a4152335881dc9bb177.png
         * relationStatus : 0
         * uid : 54893635
         * userDesc : Keep holding on...
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
