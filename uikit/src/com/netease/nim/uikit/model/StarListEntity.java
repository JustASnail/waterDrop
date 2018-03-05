package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/15.
 */

public class StarListEntity extends BaseResultEntity {

    /**
     * friends : [{"fNeteaseAccid":"yuanshi_14973482558711","fNeteaseToken":"0dd765a9361123ba1d432f07672abbc3","fNickName":"青岩","fPhoto":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=875316266,1428855355&fm=80&w=179&h=119&img.JPEG","fUid":14973482558711,"fUserDesc":"描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述","relationStatus":0,"starFlag":1},{"fNeteaseAccid":"yuanshi_14973475743191","fNeteaseToken":"e99e8552a9fbf9840edf9d0b5d69e3a8","fNickName":"方烈","fPhoto":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=875316266,1428855355&fm=80&w=179&h=119&img.JPEG","fUid":14973475743191,"fUserDesc":"描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述","relationStatus":0,"starFlag":1},{"fNeteaseAccid":"yuanshi_14967746218421","fNeteaseToken":"71af25bbefd1bace844673a633fffdba","fNickName":"方烈","fPhoto":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=875316266,1428855355&fm=80&w=179&h=119&img.JPEG","fUid":14967746218421,"fUserDesc":"描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述","relationStatus":0,"starFlag":1}]
     * nextSearchTime : 2017-06-07 02:43:39
     * totalSize : 3
     */

    private String nextSearchStart;
    private int pageSize;
    private List<FriendsBean> results;

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

    public List<FriendsBean> getFriends() {
        return results;
    }

    public void setFriends(List<FriendsBean> friends) {
        this.results = friends;
    }

    public static class FriendsBean {
        /**
         * fNeteaseAccid : yuanshi_14973482558711
         * fNeteaseToken : 0dd765a9361123ba1d432f07672abbc3
         * fNickName : 青岩
         * fPhoto : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=875316266,1428855355&fm=80&w=179&h=119&img.JPEG
         * fUid : 14973482558711
         * fUserDesc : 描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述
         * relationStatus : 0
         * starFlag : 1
         */

        private String fNeteaseAccid;
        private String fNeteaseToken;
        private String fNickName;
        private String fPhoto;
        private long fUid;
        private String fUserDesc;
        private int relationStatus;
        private int starFlag;

        public String getFNeteaseAccid() {
            return fNeteaseAccid;
        }

        public void setFNeteaseAccid(String fNeteaseAccid) {
            this.fNeteaseAccid = fNeteaseAccid;
        }

        public String getFNeteaseToken() {
            return fNeteaseToken;
        }

        public void setFNeteaseToken(String fNeteaseToken) {
            this.fNeteaseToken = fNeteaseToken;
        }

        public String getFNickName() {
            return fNickName;
        }

        public void setFNickName(String fNickName) {
            this.fNickName = fNickName;
        }

        public String getFPhoto() {
            return fPhoto;
        }

        public void setFPhoto(String fPhoto) {
            this.fPhoto = fPhoto;
        }

        public long getFUid() {
            return fUid;
        }

        public void setFUid(long fUid) {
            this.fUid = fUid;
        }

        public String getFUserDesc() {
            return fUserDesc;
        }

        public void setFUserDesc(String fUserDesc) {
            this.fUserDesc = fUserDesc;
        }

        public int getRelationStatus() {
            return relationStatus;
        }

        public void setRelationStatus(int relationStatus) {
            this.relationStatus = relationStatus;
        }

        public int getStarFlag() {
            return starFlag;
        }

        public void setStarFlag(int starFlag) {
            this.starFlag = starFlag;
        }
    }
}
