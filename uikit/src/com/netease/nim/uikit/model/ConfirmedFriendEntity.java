package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/13.
 */

public class ConfirmedFriendEntity extends BaseResultEntity {

    /**
     * nextSearchStart : 2017-07-12 12:54:06
     * pageSize : 10
     * results : [{"createTime":"2017-07-12 12:54:06","createTimestamp":"1499835246000","fromUser":{"idCode":9550309,"neteaseAccid":"yuanshi_14973430855411","neteaseToken":"9e2837e2ff5875aa903bf9831ed331dc","nickName":"Dan老大哥保障标准的通知","photo":"http://oogijmhwg.bk2a85b1c3a8991968.png","uid":14973430855411,"userDesc":"水滴im测试，发现错误记录下来"},"iosSchema":"YSWaterDrop://NaviPush/WDSystemtroller","messageId":134,"note":"请求添加您为好友","status":0,"targetId":-1,"title":"Dan老大哥","type":11},{"createTime":"2017-07-10 01:31:43","createTimestamp":"1499621503000","data":{"attentionNum":1,"categoryId":3,"createTime":"2017-07-05 15:15:52","createTimestamp":"1499238952000","createUid":14967746218421,"dropCode":"94256606","dropDesc":"转眼扑蝶的岁月都过去，只剩看山的岁月了。","dropId":14992389638051,"dropName":"水塘1","dropPhoto":"http://cdn001.waterdrop.xin/zhmqpxt@3x.jpg","groupUserNum":1,"likeNum":0,"neteaseRoomId":"","starFlag":1,"status":1},"fromUser":{"idCode":4923765,"neteaseAccid":"yuanshi_14981930033361","neteaseToken":"972257a3232825b5c83b84928975d671","nickName":"南木。","photo":"http://oogijmhwg632f5160c4ff1","uid":14981930033361,"userDesc":""},"iosSchema":"YSWaterDrop://NaviPush/WDSystewController","messageId":25,"note":"水电费雷克萨","status":0,"targetId":14992389638051,"title":"uð\u001d","type":12}]
     * totalSize : 3
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
         * createTime : 2017-07-12 12:54:06
         * createTimestamp : 1499835246000
         * fromUser : {"idCode":9550309,"neteaseAccid":"yuanshi_14973430855411","neteaseToken":"9e2837e2ff5875aa903bf9831ed331dc","nickName":"Dan老大哥保障标准的通知","photo":"http://oogijmhwg.bk2a85b1c3a8991968.png","uid":14973430855411,"userDesc":"水滴im测试，发现错误记录下来"}
         * iosSchema : YSWaterDrop://NaviPush/WDSystemtroller
         * messageId : 134
         * note : 请求添加您为好友
         * status : 0
         * targetId : -1
         * title : Dan老大哥
         * type : 11
         * data : {"attentionNum":1,"categoryId":3,"createTime":"2017-07-05 15:15:52","createTimestamp":"1499238952000","createUid":14967746218421,"dropCode":"94256606","dropDesc":"转眼扑蝶的岁月都过去，只剩看山的岁月了。","dropId":14992389638051,"dropName":"水塘1","dropPhoto":"http://cdn001.waterdrop.xin/zhmqpxt@3x.jpg","groupUserNum":1,"likeNum":0,"neteaseRoomId":"","starFlag":1,"status":1}
         */

        private String createTime;
        private String createTimestamp;
        private FromUserBean fromUser;
        private String iosSchema;
        private int messageId;
        private String note;
        private int status;
        private long targetId;
        private String title;
        private int type;
        private DataBean data;

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

        public FromUserBean getFromUser() {
            return fromUser;
        }

        public void setFromUser(FromUserBean fromUser) {
            this.fromUser = fromUser;
        }

        public String getIosSchema() {
            return iosSchema;
        }

        public void setIosSchema(String iosSchema) {
            this.iosSchema = iosSchema;
        }

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getTargetId() {
            return targetId;
        }

        public void setTargetId(long targetId) {
            this.targetId = targetId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class FromUserBean {
            /**
             * idCode : 9550309
             * neteaseAccid : yuanshi_14973430855411
             * neteaseToken : 9e2837e2ff5875aa903bf9831ed331dc
             * nickName : Dan老大哥保障标准的通知
             * photo : http://oogijmhwg.bk2a85b1c3a8991968.png
             * uid : 14973430855411
             * userDesc : 水滴im测试，发现错误记录下来
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

        public static class DataBean {
            /**
             * attentionNum : 1
             * categoryId : 3
             * createTime : 2017-07-05 15:15:52
             * createTimestamp : 1499238952000
             * createUid : 14967746218421
             * dropCode : 94256606
             * dropDesc : 转眼扑蝶的岁月都过去，只剩看山的岁月了。
             * dropId : 14992389638051
             * dropName : 水塘1
             * dropPhoto : http://cdn001.waterdrop.xin/zhmqpxt@3x.jpg
             * groupUserNum : 1
             * likeNum : 0
             * neteaseRoomId :
             * starFlag : 1
             * status : 1
             */

            private int attentionNum;
            private int categoryId;
            private String createTime;
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
        }
    }
}
