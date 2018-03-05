package com.netease.nim.uikit.model;

import java.io.Serializable;

/**
 * Created by dengxiaolei on 2017/9/28.
 */

public class LinkedMeModel implements Serializable {

    private long tipId;
    private long dropId;
    private String goodsId;

    private String tipTitle;


    private String dropTitle;

    private String tipUrl;

    public long getTipId() {
        return tipId;
    }

    public void setTipId(long tipId) {
        this.tipId = tipId;
    }

    public long getDropId() {
        return dropId;
    }

    public void setDropId(long dropId) {
        this.dropId = dropId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getDropTitle() {
        return dropTitle;
    }

    public void setDropTitle(String dropTitle) {
        this.dropTitle = dropTitle;
    }

    public String getTipUrl() {
        return tipUrl;
    }

    public void setTipUrl(String tipUrl) {
        this.tipUrl = tipUrl;
    }

    public String getTipTitle() {
        return tipTitle;
    }

    public void setTipTitle(String tipTitle) {
        this.tipTitle = tipTitle;
    }


}
