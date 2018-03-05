package com.drops.waterdrop.model;

import java.io.Serializable;

/**
 * Created by dengxiaolei on 2017/8/21.
 */

public class PushCotentModel implements Serializable {
    private String targetId;
    private int msgtype;
    private String schema;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public int getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(int msgtype) {
        this.msgtype = msgtype;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

}
