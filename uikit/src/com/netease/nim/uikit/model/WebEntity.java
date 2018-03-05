package com.netease.nim.uikit.model;

/**
 * Created by Mr.Smile on 2017/7/12.
 */

public class WebEntity {

    /**
     * action : tipDetail
     * params : {"tipId":14974599425041}
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
         * tipId : 14974599425041
         */

        private long tipId;
        private long uid;
        private long dropId;
        private String goodId;
        private String cover;
        private String shareUrl;
        private String tipDes;
        private String tipTitle;
        private String tipUrl;

        public long getTipId() {
            return tipId;
        }

        public void setTipId(long tipId) {
            this.tipId = tipId;
        }

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

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
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

        public String getTipUrl() {
            return tipUrl;
        }
    }
}
