package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/10.
 */

public class AttentionPoolListEntity extends BaseResultEntity {

    /**
     * results : [{"attentionNum":1,"attentionStatus":1,"attentionShowStatus":1,"categoryId":1,"categoryName":"","createUid":14967746218421,"dropCode":"42887157","dropDesc":"浙江省","dropId":14986186028391,"dropName":"方烈","dropPhoto":"http://oogijmhwg.bkt.clouddn.com/18658828735","groupUserNum":1,"joinStatus":-1,"likeNum":0,"neteaseRoomId":"60739687","starFlag":1,"status":1,"tipNum":0}]
     * nextSearchStart : 60739687
     * pageSize : 10
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
         * attentionNum : 1
         * attentionStatus : 1
         * attentionShowStatus : 1
         * categoryId : 1
         * categoryName :
         * createUid : 14967746218421
         * dropCode : 42887157
         * dropDesc : 浙江省
         * dropId : 14986186028391
         * dropName : 方烈
         * dropPhoto : http://oogijmhwg.bkt.clouddn.com/18658828735
         * groupUserNum : 1
         * joinStatus : -1
         * likeNum : 0
         * neteaseRoomId : 60739687
         * starFlag : 1
         * status : 1
         * tipNum : 0
         */

        private int attentionNum;
        private int attentionStatus;
        private int attentionShowStatus;
        private int categoryId;
        private String categoryName;
        private long createUid;
        private String dropCode;
        private String dropDesc;
        private long dropId;
        private String dropName;
        private String dropPhoto;
        private int groupUserNum;
        private int joinStatus;
        private int likeNum;
        private String neteaseRoomId;
        private int starFlag;
        private int status;
        private int tipNum;

        public int getAttentionNum() {
            return attentionNum;
        }

        public void setAttentionNum(int attentionNum) {
            this.attentionNum = attentionNum;
        }

        public int getAttentionStatus() {
            return attentionStatus;
        }

        public void setAttentionStatus(int attentionStatus) {
            this.attentionStatus = attentionStatus;
        }

        public int getAttentionShowStatus() {
            return attentionShowStatus;
        }

        public void setAttentionShowStatus(int attentionShowStatus) {
            this.attentionShowStatus = attentionShowStatus;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public long getCreateUid() {
            return createUid;
        }

        public void setCreateUid(long createUid) {
            this.createUid = createUid;
        }

        public String getDropCode() {
            return dropCode;
        }

        public void setDropCode(String dropCode) {
            this.dropCode = dropCode;
        }

        public String getDropDesc() {
            return dropDesc;
        }

        public void setDropDesc(String dropDesc) {
            this.dropDesc = dropDesc;
        }

        public long getDropId() {
            return dropId;
        }

        public void setDropId(long dropId) {
            this.dropId = dropId;
        }

        public String getDropName() {
            return dropName;
        }

        public void setDropName(String dropName) {
            this.dropName = dropName;
        }

        public String getDropPhoto() {
            return dropPhoto;
        }

        public void setDropPhoto(String dropPhoto) {
            this.dropPhoto = dropPhoto;
        }

        public int getGroupUserNum() {
            return groupUserNum;
        }

        public void setGroupUserNum(int groupUserNum) {
            this.groupUserNum = groupUserNum;
        }

        public int getJoinStatus() {
            return joinStatus;
        }

        public void setJoinStatus(int joinStatus) {
            this.joinStatus = joinStatus;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public String getNeteaseRoomId() {
            return neteaseRoomId;
        }

        public void setNeteaseRoomId(String neteaseRoomId) {
            this.neteaseRoomId = neteaseRoomId;
        }

        public int getStarFlag() {
            return starFlag;
        }

        public void setStarFlag(int starFlag) {
            this.starFlag = starFlag;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTipNum() {
            return tipNum;
        }

        public void setTipNum(int tipNum) {
            this.tipNum = tipNum;
        }
    }
}
