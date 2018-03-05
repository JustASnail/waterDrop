package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/5.
 */

public class SearchPostEntity extends BaseResultEntity {


    /**
     * nextSearchStart : 2017-07-03 02:59:46
     * pageSize : 10
     * results : [{"browserNum":400,"categoryId":2,"collectShowStatus":0,"collectStatus":0,"cover":"http://pond.waterdrop.xin/zxz-sttj-6@2x.jpg","createTime":"2017-07-12","createTimeShow":"8天前","createTimestamp":"1499831986000","createUid":54149413,"creator":{"idCode":54149413,"neteaseAccid":"yuanshi_54149413","neteaseToken":"439179858ebc6188cb0b89e99cffcc1c","nickName":"  ","photo":"http://oogijmhwg.bkt.clouddn.com/e6e80f6f98c8c080f85e480a50156343.png","uid":54149413,"userDesc":"我就是                           "},"dropId":14992398432561,"likeNum":460,"likeStatus":0,"photoNum":1,"showType":1,"status":1,"tipContent":"阿哲旅行在路上，有诗和远方","tipId":15690278862351,"tipTitle":"阿哲旅行在路上，有诗和远方","tipUrl":"http://api.waterdrop.xin/drops_wechat170525/app_h5/tip-detail.html"}]
     * totalSize : 4
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
         * browserNum : 400
         * categoryId : 2
         * collectShowStatus : 0
         * collectStatus : 0
         * cover : http://pond.waterdrop.xin/zxz-sttj-6@2x.jpg
         * createTime : 2017-07-12
         * createTimeShow : 8天前
         * createTimestamp : 1499831986000
         * createUid : 54149413
         * creator : {"idCode":54149413,"neteaseAccid":"yuanshi_54149413","neteaseToken":"439179858ebc6188cb0b89e99cffcc1c","nickName":"  ","photo":"http://oogijmhwg.bkt.clouddn.com/e6e80f6f98c8c080f85e480a50156343.png","uid":54149413,"userDesc":"我就是                           "}
         * dropId : 14992398432561
         * likeNum : 460
         * likeStatus : 0
         * photoNum : 1
         * showType : 1
         * status : 1
         * tipContent : 阿哲旅行在路上，有诗和远方
         * tipId : 15690278862351
         * tipTitle : 阿哲旅行在路上，有诗和远方
         * tipUrl : http://api.waterdrop.xin/drops_wechat170525/app_h5/tip-detail.html
         */

        private int browserNum;
        private int categoryId;
        private int collectShowStatus;
        private int collectStatus;
        private String cover;
        private String createTime;
        private String createTimeShow;
        private String createTimestamp;
        private int createUid;
        private CreatorBean creator;
        private long dropId;
        private int likeNum;
        private int likeStatus;
        private int photoNum;
        private int showType;
        private int status;
        private String tipContent;
        private long tipId;
        private String tipTitle;
        private String tipUrl;

        public int getBrowserNum() {
            return browserNum;
        }

        public void setBrowserNum(int browserNum) {
            this.browserNum = browserNum;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
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

        public String getCreateTimeShow() {
            return createTimeShow;
        }

        public void setCreateTimeShow(String createTimeShow) {
            this.createTimeShow = createTimeShow;
        }

        public String getCreateTimestamp() {
            return createTimestamp;
        }

        public void setCreateTimestamp(String createTimestamp) {
            this.createTimestamp = createTimestamp;
        }

        public int getCreateUid() {
            return createUid;
        }

        public void setCreateUid(int createUid) {
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

        public int getShowType() {
            return showType;
        }

        public void setShowType(int showType) {
            this.showType = showType;
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

        public String getTipUrl() {
            return tipUrl;
        }

        public void setTipUrl(String tipUrl) {
            this.tipUrl = tipUrl;
        }

        public static class CreatorBean {
            /**
             * idCode : 54149413
             * neteaseAccid : yuanshi_54149413
             * neteaseToken : 439179858ebc6188cb0b89e99cffcc1c
             * nickName :
             * photo : http://oogijmhwg.bkt.clouddn.com/e6e80f6f98c8c080f85e480a50156343.png
             * uid : 54149413
             * userDesc : 我就是
             */

            private int idCode;
            private String neteaseAccid;
            private String neteaseToken;
            private String nickName;
            private String photo;
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
}
