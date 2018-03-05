package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/7/17.
 */

public class FansGroupEntity {

    /**
     * attentionNum : 1
     * attentionStatus : 1
     * attentionShowStatus : 1
     * categoryId : 1
     * categoryName : 食品
     * createUid : 14967432761081
     * dropCode : 57788618
     * dropDesc : 浙江省
     * dropId : 14968199811281
     * dropName : 方烈
     * dropPhoto : 18658828735
     * groupUserNum : 1
     * tipNum : 1
     * starFlag : 1
     * joinStatus : 1
     * likeNum : 0
     * neteaseRoomId : 50801638
     * status : 1
     * tips : [{"categoryId":3,"collectShowStatus":0,"collectStatus":0,"cover":"http://oogijmhwg.bkt.clouddn.com/skdfjsakl","createTime":"2017-07-05","createUid":14981930033361,"creator":{"idCode":4923765,"mobile":"15158116123","nickName":"南木。","photo":"http://oogijmhwg.bkt.clouddn.com/4b9747017b693bd1a7b632f5160c4ff1","uid":14981930033361,"userDesc":""},"dropId":14986186028391,"joinStatus":-1,"likeNum":0,"likeStatus":0,"photoNum":5,"photos":[{"createUid":14981930033361,"dropId":14986186028391,"photo":"http://oogijmhwg.bkt.clouddn.com/skdfjsakl","photoId":14991922217811,"status":1,"tipId":14991922217771}],"showType":2,"status":1,"tipContent":"方烈content","tipId":14991922217771,"tipTitle":"方烈title"}]
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
    private int tipNum;
    private int starFlag;
    private int joinStatus;
    private int likeNum;
    private String neteaseRoomId;
    private int status;
    private List<TipsBean> tips;

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

    public int getTipNum() {
        return tipNum;
    }

    public void setTipNum(int tipNum) {
        this.tipNum = tipNum;
    }

    public int getStarFlag() {
        return starFlag;
    }

    public void setStarFlag(int starFlag) {
        this.starFlag = starFlag;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TipsBean> getTips() {
        return tips;
    }

    public void setTips(List<TipsBean> tips) {
        this.tips = tips;
    }

    public static class TipsBean {
        /**
         * categoryId : 3
         * collectShowStatus : 0
         * collectStatus : 0
         * cover : http://oogijmhwg.bkt.clouddn.com/skdfjsakl
         * createTime : 2017-07-05
         * createUid : 14981930033361
         * creator : {"idCode":4923765,"mobile":"15158116123","nickName":"南木。","photo":"http://oogijmhwg.bkt.clouddn.com/4b9747017b693bd1a7b632f5160c4ff1","uid":14981930033361,"userDesc":""}
         * dropId : 14986186028391
         * joinStatus : -1
         * likeNum : 0
         * likeStatus : 0
         * photoNum : 5
         * photos : [{"createUid":14981930033361,"dropId":14986186028391,"photo":"http://oogijmhwg.bkt.clouddn.com/skdfjsakl","photoId":14991922217811,"status":1,"tipId":14991922217771}]
         * showType : 2
         * status : 1
         * tipContent : 方烈content
         * tipId : 14991922217771
         * tipTitle : 方烈title
         */

        private int categoryId;
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
        private int showType;
        private int status;
        private String tipContent;
        private long tipId;
        private String tipTitle;
        private List<PhotosBean> photos;

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

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
        }

        public static class CreatorBean {
            /**
             * idCode : 4923765
             * mobile : 15158116123
             * nickName : 南木。
             * photo : http://oogijmhwg.bkt.clouddn.com/4b9747017b693bd1a7b632f5160c4ff1
             * uid : 14981930033361
             * userDesc :
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

        public static class PhotosBean {
            /**
             * createUid : 14981930033361
             * dropId : 14986186028391
             * photo : http://oogijmhwg.bkt.clouddn.com/skdfjsakl
             * photoId : 14991922217811
             * status : 1
             * tipId : 14991922217771
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