package com.netease.nim.uikit.event;

/**
 * Created by dengxiaolei on 2017/7/21.
 */

public class OnTipColletionEvent {

    private int browserNum;
    private int collectStatus;

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
}
