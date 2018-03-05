package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/7.
 */

public class PostListEntity extends BaseResultEntity {

    /**
     * nextSearchStart : 2
     * pageSize : 10
     * results : [{"browserNum":0,"categoryId":2,"collectShowStatus":0,"collectStatus":0,"cover":"http://oogijmhwg.bkt.clouddn.com/skdfjsakl","createTime":"2017-06-15","createUid":14967746218421,"creator":{"idCode":6674957,"mobile":"13588706339","neteaseAccid":"yuanshi_14967746218421","neteaseToken":"71af25bbefd1bace844673a633fffdba","nickName":"方烈","photo":"http://v1.qzone.cc/avatar/201408/20/11/43/53f419541c31a667.jpg","uid":14967746218421,"userDesc":"描"},"dropId":14968253943581,"likeNum":200,"likeStatus":0,"photoNum":5,"showType":1,"status":1,"tipContent":"方烈content","tipId":14974599425041,"tipTitle":"方烈title"}]
     * totalSize : 29
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
         * browserNum : 0
         * categoryId : 2
         * collectShowStatus : 0
         * collectStatus : 0
         * cover : http://oogijmhwg.bkt.clouddn.com/skdfjsakl
         * createTime : 2017-06-15
         * createUid : 14967746218421
         * creator : {"idCode":6674957,"mobile":"13588706339","neteaseAccid":"yuanshi_14967746218421","neteaseToken":"71af25bbefd1bace844673a633fffdba","nickName":"方烈","photo":"http://v1.qzone.cc/avatar/201408/20/11/43/53f419541c31a667.jpg","uid":14967746218421,"userDesc":"描"}
         * dropId : 14968253943581
         * likeNum : 200
         * likeStatus : 0
         * photoNum : 5
         * showType : 1
         * status : 1
         * tipContent : 方烈content
         * tipId : 14974599425041
         * tipTitle : 方烈title
         */


        private int browserNum;
        private int categoryId;
        private int collectShowStatus;
        private int collectStatus;
        private String cover;
        private String createTime;
        private long createUid;
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
        private String createTimeShow;
        private String createTimestamp;
        private String tipUrl;
        private List<CreatorBean> collectFriends;

        /******逻辑用到的*******/
        private int collectButtonState;
        private List<CollectItemHolder> collectMenuViews;

        public List<CollectItemHolder> getCollectMenuViews() {
            return collectMenuViews;
        }

        public void setCollectMenuViews(List<CollectItemHolder> collectMenuViews) {
            this.collectMenuViews = collectMenuViews;
        }

        public int getCollectButtonState() {
            return collectButtonState;
        }

        public void setCollectButtonState(int collectButtonState) {
            this.collectButtonState = collectButtonState;
        }

        /******逻辑用到的*******/


        public List<CreatorBean> getCollectFriends() {
            return collectFriends;
        }

        public void setCollectFriends(List<CreatorBean> collectFriends) {
            this.collectFriends = collectFriends;
        }

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

        public String getTipUrl() {
            return tipUrl;
        }

        public void setTipUrl(String tipUrl) {
            this.tipUrl = tipUrl;
        }

        public static class CreatorBean {
            /**
             * idCode : 6674957
             * mobile : 13588706339
             * neteaseAccid : yuanshi_14967746218421
             * neteaseToken : 71af25bbefd1bace844673a633fffdba
             * nickName : 方烈
             * photo : http://v1.qzone.cc/avatar/201408/20/11/43/53f419541c31a667.jpg
             * uid : 14967746218421
             * userDesc : 描
             */

            private int idCode;
            private String mobile;
            private String neteaseAccid;
            private String neteaseToken;
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
