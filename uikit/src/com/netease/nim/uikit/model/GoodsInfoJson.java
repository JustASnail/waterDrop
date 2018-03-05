package com.netease.nim.uikit.model;

/**
 * Created by dengxiaolei on 2017/7/8.
 */

public class GoodsInfoJson extends BaseResultEntity {

    /**
     * quantity : 50
     * drop_id : 50
     * tip_id : 50
     * good_id : cdaeb152fcb9174408d59eb9de51d5aa
     */

    private int quantity;
    private long drop_id;
    private long tip_id;
    private String good_id;
    private long sku_id;

    public long getSku_id() {
        return sku_id;
    }

    public void setSku_id(long sku_id) {
        this.sku_id = sku_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getDrop_id() {
        return drop_id;
    }

    public void setDrop_id(long drop_id) {
        this.drop_id = drop_id;
    }

    public long getTip_id() {
        return tip_id;
    }

    public void setTip_id(long tip_id) {
        this.tip_id = tip_id;
    }

    public String getGood_id() {
        return good_id;
    }

    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }
}
