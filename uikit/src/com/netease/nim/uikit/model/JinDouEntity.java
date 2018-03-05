package com.netease.nim.uikit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Smile on 2017/6/30.
 */

public class JinDouEntity {

    private String nextSearchStart;
    private int pageSize;
    private String totalBeans;
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

    public String getTotalBeans() {
        return totalBeans;
    }

    public void setTotalBeans(String totalBeans) {
        this.totalBeans = totalBeans;
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

    public static class ResultsBean implements Serializable{

        private String beans;
        private String createTime;
        private String createTimestamp;
        private CreatorBean creator;
        private DropTipBean dropTip;
        private int earnType;
        private String label;
        private int status;

        public String getBeans() {
            return beans;
        }

        public void setBeans(String beans) {
            this.beans = beans;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTimestamp() {
            return createTimestamp;
        }

        public void setCreateTimestamp(String createTimestamp) {
            this.createTimestamp = createTimestamp;
        }

        public CreatorBean getCreator() {
            return creator;
        }

        public void setCreator(CreatorBean creator) {
            this.creator = creator;
        }

        public DropTipBean getDropTip() {
            return dropTip;
        }

        public void setDropTip(DropTipBean dropTip) {
            this.dropTip = dropTip;
        }

        public int getEarnType() {
            return earnType;
        }

        public void setEarnType(int earnType) {
            this.earnType = earnType;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static class CreatorBean {
            /**
             * idCode : 8242697
             * neteaseAccid : yuanshi_14980969870711
             * neteaseToken : 09465ba3f56cd356086b74fa29c5f729
             * nickName : Alright
             * photo : http://oogijmhwg.bkt.clouddn.com/c19c58469c438fd25e94e0a6a5a12112.png
             * uid : 14980969870711
             * userDesc : Keep holding on...
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

        public static class DropTipBean {
            /**
             * browserNum : 169
             * categoryId : -1
             * cover : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1750734146,1448049649&fm=80&w=179&h=119&img.JPEG
             * createTime : 2017-06-08
             * createTimeShow : 一个月前
             * createTimestamp : 1496866307000
             * createUid : 14967432761081
             * dropId : 14968253943581
             * dropInfo : {"attentionNum":1,"attentionShowStatus":1,"attentionStatus":0,"categoryId":1,"createTime":"2017-07-05 15:15:13","createTimeShow":"5天","createTimestamp":"1499238913000","createUid":14967746218421,"dropCode":"98515106","dropDesc":"爱藏在隐蔽的夹缝里，你用心找就会发现。","dropId":14992389245041,"dropName":"水塘1","dropPhoto":"http://cdn001.waterdrop.xin/gzmt@3x.jpg","groupUserNum":1,"headImg":"http://www.gurubear.com.cn/editor/uploadfile/20130826151723.jpg","likeNum":0,"neteaseRoomId":"","starFlag":1,"status":1,"tipNum":0}
             * likeNum : 0
             * photoNum : 0
             * showType : 1
             * status : 1
             * tipContent : 方烈content
             * tipId : 14968663013081
             * tipTitle : 方烈title
             */

            private int browserNum;
            private int categoryId;
            private String cover;
            private String createTime;
            private String createTimeShow;
            private String createTimestamp;
            private long createUid;
            private long dropId;
            private DropInfoBean dropInfo;
            private int likeNum;
            private int photoNum;
            private int showType;
            private int status;
            private String tipContent;
            private long tipId;
            private String tipTitle;

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

            public static class DropInfoBean {
                /**
                 * attentionNum : 1
                 * attentionShowStatus : 1
                 * attentionStatus : 0
                 * categoryId : 1
                 * createTime : 2017-07-05 15:15:13
                 * createTimeShow : 5天
                 * createTimestamp : 1499238913000
                 * createUid : 14967746218421
                 * dropCode : 98515106
                 * dropDesc : 爱藏在隐蔽的夹缝里，你用心找就会发现。
                 * dropId : 14992389245041
                 * dropName : 水塘1
                 * dropPhoto : http://cdn001.waterdrop.xin/gzmt@3x.jpg
                 * groupUserNum : 1
                 * headImg : http://www.gurubear.com.cn/editor/uploadfile/20130826151723.jpg
                 * likeNum : 0
                 * neteaseRoomId :
                 * starFlag : 1
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
                private String headImg;
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

                public String getHeadImg() {
                    return headImg;
                }

                public void setHeadImg(String headImg) {
                    this.headImg = headImg;
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
    }
}

