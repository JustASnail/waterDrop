package com.netease.nim.uikit.green_dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dengxiaolei on 2017/7/12.
 */

@Entity
public class SystemMessageDB {
    @Id
    private long messageId;
    private long time;

    private String title;

    private String note;


    private String createTime;
    private String createTimestamp;
    private String data;

    private int type;//区分消息类型

    private int unreadTag;//0是未读， 1是已读
    private int status;

    private long targetId;//对应跳转的id

    @Generated(hash = 883555816)
    public SystemMessageDB(long messageId, long time, String title, String note,
            String createTime, String createTimestamp, String data, int type,
            int unreadTag, int status, long targetId) {
        this.messageId = messageId;
        this.time = time;
        this.title = title;
        this.note = note;
        this.createTime = createTime;
        this.createTimestamp = createTimestamp;
        this.data = data;
        this.type = type;
        this.unreadTag = unreadTag;
        this.status = status;
        this.targetId = targetId;
    }

    @Generated(hash = 1846241604)
    public SystemMessageDB() {
    }

    public long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimestamp() {
        return this.createTimestamp;
    }

    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUnreadTag() {
        return this.unreadTag;
    }

    public void setUnreadTag(int unreadTag) {
        this.unreadTag = unreadTag;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTargetId() {
        return this.targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

}
