package com.netease.nim.uikit.model;

/**
 * Created by dengxiaolei on 2017/9/8.
 */

public class IdCardEntity extends BaseResultEntity {

    /**
     * idcardBack : back
     * idcardFront : front
     * idcardNo : 450521198703058735
     * name : 温朝凯
     * uid : 90794549
     */

    private String idcardBack;
    private String idcardFront;
    private String idcardNo;
    private String name;
    private int uid;

    public String getIdcardBack() {
        return idcardBack;
    }

    public void setIdcardBack(String idcardBack) {
        this.idcardBack = idcardBack;
    }

    public String getIdcardFront() {
        return idcardFront;
    }

    public void setIdcardFront(String idcardFront) {
        this.idcardFront = idcardFront;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
