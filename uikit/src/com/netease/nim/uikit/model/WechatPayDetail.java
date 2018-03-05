package com.netease.nim.uikit.model;

/**
 * Created by dengxiaolei on 2017/7/8.
 */

public class WechatPayDetail extends BaseResultEntity {

    /**
     * appPackage : Sign=WXPay
     * appid : wx074d08eccff05d5b
     * noncestr : Fq222JtIqsU14gYnEiZ9Meao9fLfVclM
     * outTradeNo : 2017070817241801754
     * partnerid : 1463426802
     * prepayid : wx20170708172418cf3e42a3810576839563
     * sign : A8785B2195577BF5936928D5F5CEA6E6
     * timestamp : 1499505858
     */

    private String appPackage;
    private String appid;
    private String noncestr;
    private String outTradeNo;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timestamp;

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
