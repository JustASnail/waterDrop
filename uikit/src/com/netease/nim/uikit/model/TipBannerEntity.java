package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/13.
 */

public class TipBannerEntity extends BaseResultEntity {

    /**
     * nextSearchStart : 3
     * pageSize : 10
     * results : [{"collectShowStatus":0,"collectStatus":0,"cover":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG","createTime":"2017-06-10","createUid":14967746218421,"creator":{"idCode":6674957,"mobile":"13588706339","nickName":"方烈","photo":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=875316266,1428855355&fm=80&w=179&h=119&img.JPEG","uid":14967746218421,"userDesc":"描述述描述"},"dropId":14968253943581,"joinStatus":-1,"likeNum":0,"likeStatus":0,"photoNum":5,"status":1,"tipContent":"方烈content","tipId":14970739446971,"tipTitle":"方烈title"}]
     * totalSize : 5
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
         * collectShowStatus : 0
         * collectStatus : 0
         * cover : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG
         * createTime : 2017-06-10
         * createUid : 14967746218421
         * creator : {"idCode":6674957,"mobile":"13588706339","nickName":"方烈","photo":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=875316266,1428855355&fm=80&w=179&h=119&img.JPEG","uid":14967746218421,"userDesc":"描述述描述"}
         * dropId : 14968253943581
         * joinStatus : -1
         * likeNum : 0
         * likeStatus : 0
         * photoNum : 5
         * status : 1
         * tipContent : 方烈content
         * tipId : 14970739446971
         * tipTitle : 方烈title
         */

        private int collectShowStatus;
        private int collectStatus;
        private String cover;
        private String createTime;
        private long createUid;
        private CreatorBean creator;
        private long dropId;
        private int joinStatus;
        private int likeNum;
        private int likeStatus;
        private int photoNum;
        private int status;
        private String tipContent;
        private long tipId;
        private String tipTitle;
        private String createTimeShow;
        private String createTimestamp;
        private String tipUrl;
        private int showType;
        private String updateTimestamp;
        private int vrFlag;
        private String vrUrl;

        public boolean isVr(){
            return vrFlag == 1;
        }



        public int getShowType(){
            return showType;
        }

        public String getUpdateTimestamp() {
            return updateTimestamp;
        }

        public int getVrFlag() {
            return vrFlag;
        }

        public String getVrUrl() {
            return vrUrl;
        }

        public String getCreateTimeShow() {
            return createTimeShow;
        }

        public void setCreateTimeShow(String createTimeShow) {
            this.createTimeShow = createTimeShow;
        }

        public String getTipUrl() {
            return tipUrl;
        }

        public void setTipUrl(String tipUrl) {
            this.tipUrl = tipUrl;
        }

        public String getCreateTimestamp() {
            return createTimestamp;
        }

        public void setCreateTimestamp(String createTimestamp) {
            this.createTimestamp = createTimestamp;
        }

        public int getCollectShowStatus() {
            return collectShowStatus;
        }

        public void setCollectShowStatus(int collectShowStatus) {
            this.collectShowStatus = collectShowStatus;
        }

        public int getCollectStatus() {
            return collectStatus;
        }

        public void setCollectStatus(int collectStatus) {
            this.collectStatus = collectStatus;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public long getCreateUid() {
            return createUid;
        }

        public void setCreateUid(long createUid) {
            this.createUid = createUid;
        }

        public CreatorBean getCreator() {
            return creator;
        }

        public void setCreator(CreatorBean creator) {
            this.creator = creator;
        }

        public long getDropId() {
            return dropId;
        }

        public void setDropId(long dropId) {
            this.dropId = dropId;
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

        public int getLikeStatus() {
            return likeStatus;
        }

        public void setLikeStatus(int likeStatus) {
            this.likeStatus = likeStatus;
        }

        public int getPhotoNum() {
            return photoNum;
        }

        public void setPhotoNum(int photoNum) {
            this.photoNum = photoNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTipContent() {
            return tipContent;
        }

        public void setTipContent(String tipContent) {
            this.tipContent = tipContent;
        }

        public long getTipId() {
            return tipId;
        }

        public void setTipId(long tipId) {
            this.tipId = tipId;
        }

        public String getTipTitle() {
            return tipTitle;
        }

        public void setTipTitle(String tipTitle) {
            this.tipTitle = tipTitle;
        }

        public static class CreatorBean {
            /**
             * idCode : 6674957
             * mobile : 13588706339
             * nickName : 方烈
             * photo : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=875316266,1428855355&fm=80&w=179&h=119&img.JPEG
             * uid : 14967746218421
             * userDesc : 描述述描述
             */

            private int idCode;
            private String mobile;
            private String nickName;
            private String photo;
            private long uid;
            private String userDesc;

            public int getIdCode() {
                return idCode;
            }

            public void setIdCode(int idCode) {
                this.idCode = idCode;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
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

            public long getUid() {
                return uid;
            }

            public void setUid(long uid) {
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
}
