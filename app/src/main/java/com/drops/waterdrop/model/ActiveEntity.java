package com.drops.waterdrop.model;

import java.io.Serializable;

/**
 * Created by dengxiaolei on 2017/6/9.
 */

public class ActiveEntity implements Serializable {
    private String headImgBg;
    private int headImgBgId;
    private String activeName;
    private String activeData;

    public int getHeadImgBgId() {
        return headImgBgId;
    }

    public void setHeadImgBgId(int headImgBgId) {
        this.headImgBgId = headImgBgId;
    }

    public String getHeadImgBg() {
        return headImgBg;
    }

    public void setHeadImgBg(String headImgBg) {
        this.headImgBg = headImgBg;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public String getActiveData() {
        return activeData;
    }

    public void setActiveData(String activeData) {
        this.activeData = activeData;
    }
}
