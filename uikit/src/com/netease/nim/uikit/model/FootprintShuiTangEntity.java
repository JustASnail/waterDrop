package com.netease.nim.uikit.model;


import java.util.List;

/**
 * Created by Mr.Smile on 2017/6/30.
 */

public class FootprintShuiTangEntity {

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
         * createTime : 2017-07-19 14:36:05
         * createTimestamp : 1500446165000
         * drop : {"attentionNum":280019,"categoryId":3,"createTime":"2017-07-05 15:30:31","createTimestamp":"1499239831000","createUid":54149413,"dropCode":"28260364","dropDesc":"张信哲，1967年3月26日生于台湾，歌手、演员、舞台剧团团长。","dropId":14992398432561,"dropName":"<还爱\u2022光年>张信哲","dropPhoto":"http://pond.waterdrop.xin/st_tj_zhang@3x.png","groupUserNum":12,"headImg":"http://pond.waterdrop.xin/zxzsttu@3x.png","likeNum":100,"neteaseRoomId":"71077662","starFlag":1,"status":1}
         * dropId : 14992398432561
         */

        private String createTime;
        private String createTimestamp;
        private DropBean drop;
        private long dropId;

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

        public DropBean getDrop() {
            return drop;
        }

        public void setDrop(DropBean drop) {
            this.drop = drop;
        }

        public long getDropId() {
            return dropId;
        }

        public void setDropId(long dropId) {
            this.dropId = dropId;
        }

        public static class DropBean {
            /**
             * attentionNum : 280019
             * categoryId : 3
             * createTime : 2017-07-05 15:30:31
             * createTimestamp : 1499239831000
             * createUid : 54149413
             * dropCode : 28260364
             * dropDesc : 张信哲，1967年3月26日生于台湾，歌手、演员、舞台剧团团长。
             * dropId : 14992398432561
             * dropName : <还爱•光年>张信哲
             * dropPhoto : http://pond.waterdrop.xin/st_tj_zhang@3x.png
             * groupUserNum : 12
             * headImg : http://pond.waterdrop.xin/zxzsttu@3x.png
             * likeNum : 100
             * neteaseRoomId : 71077662
             * starFlag : 1
             * status : 1
             */

            private int attentionNum;
            private int categoryId;
            private String createTime;
            private String createTimestamp;
            private int createUid;
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

            public int getAttentionNum() {
                return attentionNum;
            }

            public void setAttentionNum(int attentionNum) {
                this.attentionNum = attentionNum;
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
        }
    }
}