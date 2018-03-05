package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/7.
 */

public class GoodsScoreListEntity extends BaseResultEntity {

    /**
     * nextSearchStart : 2017-07-07 16:31:02
     * pageSize : 10
     * results : [{"goodId":"cdaeb152fcb9174408d59eb9de51d5aa","scoreTime":"2015-01-01 12:00:00","orderId":14974677629131,"score":4,"user":{"idCode":6674957,"mobile":"13588706339","neteaseAccid":"yuanshi_14967746218421","neteaseToken":"71af25bbefd1bace844673a633fffdba","nickName":"方烈","photo":"http://v1.qzone.cc/avatar/201408/20/11/43/53f419541c31a667.jpg","uid":14967746218421,"userDesc":"描述述描述"}}]
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
         * goodId : cdaeb152fcb9174408d59eb9de51d5aa
         * scoreTime : 2015-01-01 12:00:00
         * orderId : 14974677629131
         * score : 4
         * user : {"idCode":6674957,"mobile":"13588706339","neteaseAccid":"yuanshi_14967746218421","neteaseToken":"71af25bbefd1bace844673a633fffdba","nickName":"方烈","photo":"http://v1.qzone.cc/avatar/201408/20/11/43/53f419541c31a667.jpg","uid":14967746218421,"userDesc":"描述述描述"}
         */

        private String goodId;
        private String scoreTime;
        private long orderId;
        private int score;
        private UserBean user;

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }

        public String getScoreTime() {
            return scoreTime;
        }

        public void setScoreTime(String scoreTime) {
            this.scoreTime = scoreTime;
        }

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * idCode : 6674957
             * mobile : 13588706339
             * neteaseAccid : yuanshi_14967746218421
             * neteaseToken : 71af25bbefd1bace844673a633fffdba
             * nickName : 方烈
             * photo : http://v1.qzone.cc/avatar/201408/20/11/43/53f419541c31a667.jpg
             * uid : 14967746218421
             * userDesc : 描述述描述
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
