package com.netease.nim.uikit.model;

import java.io.Serializable;

/**
 * Created by dengxiaolei on 2017/7/13.
 */

public class SystemMsgContentBean implements Serializable {

    /**
     * data : {"targetId":-1,"tips":"请求添加您为好友","msgtype":"11","schema":"YSWaterDrop://NaviPush/WDSystemNotificationViewController"}
     * msgTime : 2017-07-13 11:05:37
     * type : 11
     */

    private DataBean data;
    private String msgTime;
    private String type;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class DataBean {
        /**
         * targetId : -1
         * tips : 请求添加您为好友
         * msgtype : 11
         * schema : YSWaterDrop://NaviPush/WDSystemNotificationViewController
         */

        private long targetId;
        private String tips;
        private String msgtype;
        private String schema;

        public long getTargetId() {
            return targetId;
        }

        public void setTargetId(long targetId) {
            this.targetId = targetId;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getMsgtype() {
            return msgtype;
        }

        public void setMsgtype(String msgtype) {
            this.msgtype = msgtype;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }
    }
}
