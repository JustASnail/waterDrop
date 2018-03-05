package com.netease.nim.uikit.model;

import java.util.List;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/03 11:30
 */

public class FriendCollectEntity extends BaseResultEntity {
    private String nextSearchStart;
    private int pageSize;
    private int totalSize;
    private List<Result> results;

    public String getNextSearchStart() {
        return nextSearchStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public List<Result> getResults() {
        return results;
    }

    public static class Result extends BaseResultEntity{
        private long idCode;
        private String neteaseAccid;
        private String neteaseToken;
        private String nickName;
        private String photo;
        private long uid;
        private String userDesc;

        public Result(){
            nickName = "lun";
            photo = "http://oogijmhwg.bkt.clouddn.com/166cd9285b186fabd561b6ec01070694.png";
        }

        public long getIdCode() {
            return idCode;
        }

        public String getNeteaseAccid() {
            return neteaseAccid;
        }

        public String getNeteaseToken() {
            return neteaseToken;
        }

        public String getNickName() {
            return nickName;
        }

        public String getPhoto() {
            return photo;
        }

        public long getUid() {
            return uid;
        }

        public String getUserDesc() {
            return userDesc;
        }
    }
}
