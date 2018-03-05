package com.netease.nim.uikit.model;

import java.io.Serializable;

/**
 * Created by dengxiaolei on 2017/7/7.
 */

public class JsInteractionParamsEntity implements Serializable {

    /**
     * action : goods
     * params : {"goodId":"cdaeb152fcb9174408d59eb9de51d5aa"}
     * version : 1.0
     */

    private String action;
    private ParamsBean params;
    private String version;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class ParamsBean {
        /**
         * goodId : cdaeb152fcb9174408d59eb9de51d5aa
         */

        private String goodId;
        private long uid;
        private long dropId;
        private String dropName;
        private long tipId;

        private String cover;
        private String tipDes;
        private String tipTitle;
        private int browserNum;
        private int collectStatus;

        private String fun;
        private String msg;

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public long getDropId() {
            return dropId;
        }

        public void setDropId(long dropId) {
            this.dropId = dropId;
        }

        public long getTipId() {
            return tipId;
        }

        public void setTipId(long tipId) {
            this.tipId = tipId;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTipDes() {
            return tipDes;
        }

        public void setTipDes(String tipDes) {
            this.tipDes = tipDes;
        }

        public String getTipTitle() {
            return tipTitle;
        }

        public void setTipTitle(String tipTitle) {
            this.tipTitle = tipTitle;
        }

        public String getDropName() {
            return dropName;
        }

        public void setDropName(String dropName) {
            this.dropName = dropName;
        }

        public String getFun() {
            return fun;
        }

        public void setFun(String fun) {
            this.fun = fun;
        }
        public int getBrowserNum() {
            return browserNum;
        }

        public void setBrowserNum(int browserNum) {
            this.browserNum = browserNum;
        }

        public int getCollectStatus() {
            return collectStatus;
        }

        public void setCollectStatus(int collectStatus) {
            this.collectStatus = collectStatus;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
