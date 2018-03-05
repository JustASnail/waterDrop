package com.netease.nim.uikit.event;

/**
 * Created by dengxiaolei on 2017/7/17.
 */

public class PoolAttentionEvent {
    private boolean isAttention;

    private long dropId;

    private int attentionNum;

    public int getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(int attentionNum) {
        this.attentionNum = attentionNum;
    }

    public long getDropId() {
        return dropId;
    }

    public void setDropId(long dropId) {
        this.dropId = dropId;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }
}
