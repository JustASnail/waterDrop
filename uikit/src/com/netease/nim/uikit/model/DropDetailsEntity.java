package com.netease.nim.uikit.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/15.
 */

public class DropDetailsEntity extends BaseResultEntity {

    /**
     * attentionNum : 1
     * attentionStatus : 0
     * categoryId : 1
     * createUid : 14967432761081
     * creator : {"idCode":9553347,"mobile":"18658828735","neteaseAccid":"yuanshi_14967432761081","neteaseToken":"90ac55970a7cc5d06186b6a4d4295c8e","nickName":"青岩","photo":"http://oogijmhwg.bkt.clouddn.com/authenticPhoto65FM1.jpg","uid":14967432761081,"userDesc":""}
     * dropCode : 85902385
     * dropDesc : 描述
     * dropId : 14968253943581
     * dropName : 方烈
     * dropPhoto : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG
     * groupUserNum : 1
     * likeNum : 0
     * neteaseRoomId : 50840228
     * starFlag : 0
     * status : 1
     * tipNum : 11
     * tips : [{"categoryId":2,"cover":"http://oogijmhwg.bkt.clouddn.com/skdfjsakl","createTime":"2017-06-15","createUid":14967746218421,"creator":{"idCode":6674957,"mobile":"13588706339","neteaseAccid":"yuanshi_14967746218421","neteaseToken":"71af25bbefd1bace844673a633fffdba","nickName":"方烈","photo":"http://v1.qzone.cc/avatar/201408/20/11/43/53f419541c31a667.jpg","uid":14967746218421,"userDesc":"描述描"}}]
     */

    private int attentionNum;
    private int attentionStatus;
    private int categoryId;
    private long createUid;
    private CreatorBean creator;
    private String dropCode;
    private String dropDesc;
    private long dropId;
    private String dropName;
    private String dropPhoto;
    private String headImg;
    private int groupUserNum;
    private int likeNum;
    private String neteaseRoomId;
    private int starFlag;
    private int status;
    private int tipNum;
    private int joinStatus;//1是已经加入， 0待审核 -1没有申请
    private List<TipsBean> tips;

    public int getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(int joinStatus) {
        this.joinStatus = joinStatus;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public List<TipsBean> getTips() {
        return tips;
    }

    public void setTips(List<TipsBean> tips) {
        this.tips = tips;
    }

    public static class CreatorBean {
        /**
         * idCode : 9553347
         * mobile : 18658828735
         * neteaseAccid : yuanshi_14967432761081
         * neteaseToken : 90ac55970a7cc5d06186b6a4d4295c8e
         * nickName : 青岩
         * photo : http://oogijmhwg.bkt.clouddn.com/authenticPhoto65FM1.jpg
         * uid : 14967432761081
         * userDesc :
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

    public static class TipsBean implements MultiItemEntity {
        /**
         * categoryId : 2
         * cover : http://oogijmhwg.bkt.clouddn.com/skdfjsakl
         * createTime : 2017-06-15
         * createUid : 14967746218421
         * creator : {"idCode":6674957,"mobile":"13588706339","neteaseAccid":"yuanshi_14967746218421","neteaseToken":"71af25bbefd1bace844673a633fffdba","nickName":"方烈","photo":"http://v1.qzone.cc/avatar/201408/20/11/43/53f419541c31a667.jpg","uid":14967746218421,"userDesc":"描述描"}
         */

        private int categoryId;
        private int browserNum;
        private String cover;
        private String createTime;
        private String createTimeShow;
        private String createTimestamp;
        private long createUid;
        private CreatorBeanX creator;

        private long dropId;
        private int likeNum;
        private int photoNum;
        private int showType;
        private int status;
        private String tipContent;
        private long tipId;
        private String tipTitle;
        private List<PhotoBean> photos;
        private String tipUrl;

        public String getTipUrl() {
            return tipUrl;
        }

        public void setTipUrl(String tipUrl) {
            this.tipUrl = tipUrl;
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

        public List<PhotoBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotoBean> photos) {
            this.photos = photos;
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

        public CreatorBeanX getCreator() {
            return creator;
        }

        public void setCreator(CreatorBeanX creator) {
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

        @Override
        public int getItemType() {
            return showType;
        }

        public static class CreatorBeanX {
            /**
             * idCode : 6674957
             * mobile : 13588706339
             * neteaseAccid : yuanshi_14967746218421
             * neteaseToken : 71af25bbefd1bace844673a633fffdba
             * nickName : 方烈
             * photo : http://v1.qzone.cc/avatar/201408/20/11/43/53f419541c31a667.jpg
             * uid : 14967746218421
             * userDesc : 描述描
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

    public static class PhotoBean{
        private long createUid;
        private long dropId;
        private long photoId;
        private long tipId;
        private String photo;
        private int status;

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

        public long getPhotoId() {
            return photoId;
        }

        public void setPhotoId(long photoId) {
            this.photoId = photoId;
        }

        public long getTipId() {
            return tipId;
        }

        public void setTipId(long tipId) {
            this.tipId = tipId;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}