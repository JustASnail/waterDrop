package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/13.
 */

public class AddressBookFriendsEntity extends BaseResultEntity {


    /**
     * pageSize : 9
     * results : [{"fNeteaseAccid":"yuanshi_14973382240861","fNeteaseToken":"ac9afffc067f3168c1179f624ecac54a","mobile":"13817741615","nickName":"秘密加","photo":"http://oogijmhwg.bkt.clouddn.com/04e5b3f3257f40c9be039aefc2bf0258.png","registerStatus":1,"relationStatus":1,"uid":14973382240861},{"mobile":"46356988969","nickName":"","photo":"","registerStatus":0,"relationStatus":0,"uid":-1},{"fNeteaseAccid":"yuanshi_14975993441071","fNeteaseToken":"ea25ac6c3628e32211fa13d34c8f6feb","mobile":"13917592252","nickName":"哈哈啊.kk","photo":"http://oogijmhwg.bkt.clouddn.com/58d5ca3753c32a2eef23d1f80513445a.png","registerStatus":1,"relationStatus":0,"uid":14975993441071},{"mobile":"13636320882","nickName":"","photo":"","registerStatus":0,"relationStatus":0,"uid":-1},{"mobile":"13964351869","nickName":"","photo":"","registerStatus":0,"relationStatus":0,"uid":-1},{"fNeteaseAccid":"yuanshi_14973240537431","fNeteaseToken":"264a266183b8444114935c7b1fa5a2ee","mobile":"13816882342","nickName":"天啦噜？","photo":"http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png","registerStatus":1,"relationStatus":0,"uid":14973240537431},{"mobile":"13513648963","nickName":"","photo":"","registerStatus":0,"relationStatus":0,"uid":-1},{"mobile":"13812346966","nickName":"","photo":"","registerStatus":0,"relationStatus":0,"uid":-1},{"fNeteaseAccid":"yuanshi_14973304643721","fNeteaseToken":"75ae3158c923f94016886923c19f3118","mobile":"15800873806","nickName":"dvbjw","photo":"http://oogijmhwg.bkt.clouddn.com/4c17f3e912034b378731f3a427111faf.png","registerStatus":1,"relationStatus":1,"uid":14973304643721}]
     * totalSize : 9
     */

    private int pageSize;
    private int totalSize;
    private List<ResultsBean> results;

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
         * fNeteaseAccid : yuanshi_14973382240861
         * fNeteaseToken : ac9afffc067f3168c1179f624ecac54a
         * mobile : 13817741615
         * nickName : 秘密加
         * photo : http://oogijmhwg.bkt.clouddn.com/04e5b3f3257f40c9be039aefc2bf0258.png
         * registerStatus : 1
         * relationStatus : 1
         * uid : 14973382240861
         */

        private String fNeteaseAccid;
        private String fNeteaseToken;
        private String mobile;
        private String nickName;
        private String photo;
        private int registerStatus;
        private int relationStatus;
        private long uid;

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

        public int getRegisterStatus() {
            return registerStatus;
        }

        public void setRegisterStatus(int registerStatus) {
            this.registerStatus = registerStatus;
        }

        public int getRelationStatus() {
            return relationStatus;
        }

        public void setRelationStatus(int relationStatus) {
            this.relationStatus = relationStatus;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }
    }
}
