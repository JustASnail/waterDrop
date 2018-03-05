package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/10.
 */

public class PostForFriendCollectEntity extends BaseResultEntity {

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
         * brands : []
         * browserNum : 128
         * categoryId : 1
         * collectShowStatus : 1
         * collectStatus : 1
         * comments : []
         * cover : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1750734146,1448049649&fm=80&w=179&h=119&img.JPEG
         * createTime : 2017-06-08
         * createTimeShow : 一个月前
         * createTimestamp : 1496868141000
         * createUid : 14967432761081
         * creator : {"idCode":9553347,"neteaseAccid":"yuanshi_14967432761081","neteaseToken":"f3b41ba342dadf0cddb770bb6e6c93f4","nickName":"青岩","photo":"http://oogijmhwg.bkt.clouddn.com/authenticPhoto65FM1.jpg","uid":14967432761081,"userDesc":""}
         * dropId : 14968253943581
         * dropInfo : {"attentionNum":1,"attentionShowStatus":1,"attentionStatus":0,"categoryId":1,"createTime":"2017-06-07 16:49:51","createTimeShow":"一个月前","createTimestamp":"1496825391000","createUid":14967432761081,"dropCode":"85902385","dropDesc":"描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述","dropId":14968253943581,"dropName":"方烈","dropPhoto":"http://cdn001.waterdrop.xin/papijiang@3x.jpg","groupUserNum":1,"likeNum":0,"neteaseRoomId":"50840228","starFlag":0,"status":1,"tipNum":0}
         * goods : []
         * likeNum : 4
         * likeStatus : 0
         * photoNum : 5
         * photos : [{"createUid":14967432761081,"dropId":14968253943581,"photo":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG","photoId":14968681459091,"status":1,"tipId":14968681458961},{"createUid":14967432761081,"dropId":14968253943581,"photo":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG","photoId":14968681459111,"status":1,"tipId":14968681458961},{"createUid":14967432761081,"dropId":14968253943581,"photo":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG","photoId":14968681459141,"status":1,"tipId":14968681458961},{"createUid":14967432761081,"dropId":14968253943581,"photo":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG","photoId":14968681459161,"status":1,"tipId":14968681458961},{"createUid":14967432761081,"dropId":14968253943581,"photo":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG","photoId":14968681459191,"status":1,"tipId":14968681458961}]
         * showType : 1
         * status : 1
         * tipContent : 方烈content
         * tipId : 14968681458961
         * tipTitle : 方烈title
         */

        private int browserNum;
        private int categoryId;
        private int collectShowStatus;
        private int collectStatus;
        private String cover;
        private String createTime;
        private String createTimeShow;
        private String createTimestamp;
        private long createUid;
        private CreatorBean creator;
        private long dropId;
        private DropInfoBean dropInfo;
        private int likeNum;
        private int likeStatus;
        private int photoNum;
        private int showType;
        private int status;
        private String tipContent;
        private long tipId;
        private String tipTitle;
        private String tipUrl;
        private List<?> brands;
        private List<?> comments;
        private List<?> goods;
        private List<PhotosBean> photos;

        public String getTipUrl() {
            return tipUrl;
        }

        public void setTipUrl(String tipUrl) {
            this.tipUrl = tipUrl;
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

        public DropInfoBean getDropInfo() {
            return dropInfo;
        }

        public void setDropInfo(DropInfoBean dropInfo) {
            this.dropInfo = dropInfo;
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

        public List<?> getBrands() {
            return brands;
        }

        public void setBrands(List<?> brands) {
            this.brands = brands;
        }

        public List<?> getComments() {
            return comments;
        }

        public void setComments(List<?> comments) {
            this.comments = comments;
        }

        public List<?> getGoods() {
            return goods;
        }

        public void setGoods(List<?> goods) {
            this.goods = goods;
        }

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
        }

        public static class CreatorBean {
            /**
             * idCode : 9553347
             * neteaseAccid : yuanshi_14967432761081
             * neteaseToken : f3b41ba342dadf0cddb770bb6e6c93f4
             * nickName : 青岩
             * photo : http://oogijmhwg.bkt.clouddn.com/authenticPhoto65FM1.jpg
             * uid : 14967432761081
             * userDesc :
             */

            private int idCode;
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

        public static class DropInfoBean {
            /**
             * attentionNum : 1
             * attentionShowStatus : 1
             * attentionStatus : 0
             * categoryId : 1
             * createTime : 2017-06-07 16:49:51
             * createTimeShow : 一个月前
             * createTimestamp : 1496825391000
             * createUid : 14967432761081
             * dropCode : 85902385
             * dropDesc : 描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述
             * dropId : 14968253943581
             * dropName : 方烈
             * dropPhoto : http://cdn001.waterdrop.xin/papijiang@3x.jpg
             * groupUserNum : 1
             * likeNum : 0
             * neteaseRoomId : 50840228
             * starFlag : 0
             * status : 1
             * tipNum : 0
             */

            private int attentionNum;
            private int attentionShowStatus;
            private int attentionStatus;
            private int categoryId;
            private String createTime;
            private String createTimeShow;
            private String createTimestamp;
            private long createUid;
            private String dropCode;
            private String dropDesc;
            private long dropId;
            private String dropName;
            private String dropPhoto;
            private int groupUserNum;
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

            public int getAttentionShowStatus() {
                return attentionShowStatus;
            }

            public void setAttentionShowStatus(int attentionShowStatus) {
                this.attentionShowStatus = attentionShowStatus;
            }

            public int getAttentionStatus() {
                return attentionStatus;
            }

            public void setAttentionStatus(int attentionStatus) {
                this.attentionStatus = attentionStatus;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
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

        public static class PhotosBean {
            /**
             * createUid : 14967432761081
             * dropId : 14968253943581
             * photo : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG
             * photoId : 14968681459091
             * status : 1
             * tipId : 14968681458961
             */

            private long createUid;
            private long dropId;
            private String photo;
            private long photoId;
            private int status;
            private long tipId;

            public long getCreateUid() {
                return createUid;
            }

            public void setCreateUid(long createUid) {
                this.createUid = createUid;
            }

            public long getDropId() {
                return dropId;
            }

            public void setDropId(long dropId) {
                this.dropId = dropId;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public long getPhotoId() {
                return photoId;
            }

            public void setPhotoId(long photoId) {
                this.photoId = photoId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getTipId() {
                return tipId;
            }

            public void setTipId(long tipId) {
                this.tipId = tipId;
            }
        }
    }
}
