package com.netease.nim.uikit.model;

/**
 * Created by dengxiaolei on 2017/7/8.
 */

public class OrderDetailEntity extends BaseResultEntity {

    /**
     * billCompany :
     * billTfn :
     * billType : -1
     * createTime : 2017-07-08 17:16:17
     * createTimestamp : 1499505377825
     * deliveryAddress : 快快快
     * deliveryAddressId : 55
     * deliveryCity : 市辖区
     * deliveryDistrict : 东城区
     * deliveryMobile : 15649499999
     * deliveryName : 的后果v
     * deliveryProv : 北京市
     * idcardBack :
     * idcardFront :
     * needBill : 0
     * orderId : 14995053778231
     * payInfo : h5
     * payNo :
     * payTime :
     * price : 0.01
     * status : 0
     * uid : 14973240537431
     */

    private String billCompany;
    private String billTfn;
    private int billType;
    private String createTime;
    private String createTimestamp;
    private String deliveryAddress;
    private int deliveryAddressId;
    private String deliveryCity;
    private String deliveryDistrict;
    private String deliveryMobile;
    private String deliveryName;
    private String deliveryProv;
    private String idcardBack;
    private String idcardFront;
    private int needBill;
    private long orderId;
    private String payInfo;
    private String payNo;
    private String payTime;
    private double price;
    private int status;
    private long uid;

    public String getBillCompany() {
        return billCompany;
    }

    public void setBillCompany(String billCompany) {
        this.billCompany = billCompany;
    }

    public String getBillTfn() {
        return billTfn;
    }

    public void setBillTfn(String billTfn) {
        this.billTfn = billTfn;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(int deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryDistrict() {
        return deliveryDistrict;
    }

    public void setDeliveryDistrict(String deliveryDistrict) {
        this.deliveryDistrict = deliveryDistrict;
    }

    public String getDeliveryMobile() {
        return deliveryMobile;
    }

    public void setDeliveryMobile(String deliveryMobile) {
        this.deliveryMobile = deliveryMobile;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryProv() {
        return deliveryProv;
    }

    public void setDeliveryProv(String deliveryProv) {
        this.deliveryProv = deliveryProv;
    }

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

    public int getNeedBill() {
        return needBill;
    }

    public void setNeedBill(int needBill) {
        this.needBill = needBill;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
