package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/6/28.
 */

public class MySTieEntity extends BaseResultEntity {

    private String searchTime;
    private int totalSize;
    private List<DropTipsBean> dropTips;

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<DropTipsBean> getDropTips() {
        return dropTips;
    }

    public void setDropTips(List<DropTipsBean> dropTips) {
        this.dropTips = dropTips;
    }

    public static class DropTipsBean {
        /**
         * cover : http://kladfjadl.png
         * createUid : 14967432761081
         * creator : {"mobile":"18658828735","nickName":"青岩","photo":"http://oogijmhwg.bkt.clouddn.com/authenticPhoto65FM1.jpg","uid":14967432761081}
         * dropId : 14968253943581
         * likeNum : 0
         * photoNum : 5
         * status : 1
         * tipContent : 方烈content
         * tipId : 14968681458961
         * joinStatus : 1
         * tipTitle : 方烈title
         */

        private String cover;
        private long createUid;
        private CreatorBean creator;
        private long dropId;
        private int likeNum;
        private int photoNum;
        private int status;
        private String tipContent;
        private long tipId;
        private int joinStatus;
        private String tipTitle;
        private String tipUrl;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
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

        public int getJoinStatus() {
            return joinStatus;
        }

        public void setJoinStatus(int joinStatus) {
            this.joinStatus = joinStatus;
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
             * mobile : 18658828735
             * nickName : 青岩
             * photo : http://oogijmhwg.bkt.clouddn.com/authenticPhoto65FM1.jpg
             * uid : 14967432761081
             */

            private String mobile;
            private String nickName;
            private String photo;
            private long uid;

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
        }
    }
}
