package com.netease.nim.uikit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Smile on 2017/7/13.
 */

public class OrderDetailEntity2 extends BaseResultEntity {

    /**
     * billCompany :
     * billTfn :
     * billType : -1
     * createTime : 2017-07-26 10:19:30
     * createTimestamp : 1501035570000
     * deliveryAddress : LOL开空调斯里兰卡
     * deliveryAddressId : 100
     * deliveryCity : 海口市
     * deliveryDistrict : 秀英区
     * deliveryMobile : 11111111111
     * deliveryName : Alright
     * deliveryProv : 海南省
     * goods : [{"dropId":14992389638051,"dropTip":{"browserNum":340,"categoryId":2,"cover":"http://pond.waterdrop.xin/pjj-sttj-10@2x.jpg","createTime":"2017-07-13","createTimeShow":"12天前","createTimestamp":"1499943586000","createUid":84944713,"dropId":14992389638051,"likeNum":370,"photoNum":1,"showType":1,"status":1,"tipContent":"你以为只有地球人喝酒？外星人喝不喝我不知道","tipId":15691378862377,"tipTitle":"你以为只有地球人喝酒？外星人喝不喝我不知道","tipUrl":"http://api.waterdrop.xin/drops_wechat/app_h5/tip-detail.html"},"favourablePrice":109,"good":{"actAge":"","actArea":1,"actCity":"","actId2":"","actPic":"http://pond.waterdrop.xin/dbessmzfzlb.jpg","actPicDetail":["http://pond.waterdrop.xin/dbessmzfzb@2x.jpg"],"actPrice":109,"actTitle":"少数民族舞蹈演出服","actType":301,"average":0,"brand":"","brandcode":0,"cardStatus":0,"collectShowStatus":1,"commenter":0,"count":1000,"countrycode":86,"countryname":"中国","countrypic":"http://cdn1.drops.xin/country:38*28:86.png","createtime":"1469009349","crossBorder":0,"customTime":"","customWeeks":"","endTime":"1911686400","goodId":"ssmzwdycfcb9174408d59eb9de51d5aa","id":174,"isactivity":0,"isfrom":"1698975667","ismark":0,"isrecommend":1,"latitude":0,"longitude":0,"mark":0,"money01":0,"money01Detail":"","money02":0,"money02Detail":"","money03":0,"money03Detail":"","moneyDetail":[{"amount":"1000","desc":"1套","value":"109"}],"notices":"","parameter":"","parameteruri":"http://pond.waterdrop.xin/dbessmzfzcs.jpg","photoDetails":["http://pond.waterdrop.xin/dbessmzfzxq1.jpg","http://pond.waterdrop.xin/dbessmzfzxq2.jpg","http://pond.waterdrop.xin/dbessmzfzxq3.jpg","http://pond.waterdrop.xin/dbessmzfzxq4.jpg","http://pond.waterdrop.xin/dbessmzfzxq5.jpg","http://pond.waterdrop.xin/dbessmzfzxq6.jpg"],"score":0,"service":[{"id":"1","linkType":0,"title":"7天退换","type":1},{"id":"2","linkType":0,"title":"原厂直销","type":1},{"id":"3","linkType":0,"title":"正品保证","type":1}],"servicecode":["1","2","3"],"starid":0,"starname":"","startTime":"1468972800","status":0,"storeid":"","supplier":"","suppliercode":"","updatetime":"1469014838","uuid":"oYJ4Pv3s11Qqz64Thq1EGvlB8Axs","video":"","videoDetail":[],"wjSku":""},"goodCovery":"http://pond.waterdrop.xin/dbessmzfzlb.jpg","goodId":"ssmzwdycfcb9174408d59eb9de51d5aa","goodName":"少数民族舞蹈演出服","id":378,"orderId":15010355709601,"quantity":1,"tipId":15691378862377,"unitPrice":109}]
     * idcardBack :
     * idcardFront :
     * needBill : 0
     * orderId : 15010355709601
     * payInfo : h5
     * payNo :
     * payTime :
     * price : 109
     * status : 0
     * uid : 54893635
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
    private int uid;
    private String from;
    private List<GoodsBean> goods;

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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public static class GoodsBean implements Serializable{
        /**
         * dropId : 14992389638051
         * dropTip : {"browserNum":340,"categoryId":2,"cover":"http://pond.waterdrop.xin/pjj-sttj-10@2x.jpg","createTime":"2017-07-13","createTimeShow":"12天前","createTimestamp":"1499943586000","createUid":84944713,"dropId":14992389638051,"likeNum":370,"photoNum":1,"showType":1,"status":1,"tipContent":"你以为只有地球人喝酒？外星人喝不喝我不知道","tipId":15691378862377,"tipTitle":"你以为只有地球人喝酒？外星人喝不喝我不知道","tipUrl":"http://api.waterdrop.xin/drops_wechat/app_h5/tip-detail.html"}
         * favourablePrice : 109
         * good : {"actAge":"","actArea":1,"actCity":"","actId2":"","actPic":"http://pond.waterdrop.xin/dbessmzfzlb.jpg","actPicDetail":["http://pond.waterdrop.xin/dbessmzfzb@2x.jpg"],"actPrice":109,"actTitle":"少数民族舞蹈演出服","actType":301,"average":0,"brand":"","brandcode":0,"cardStatus":0,"collectShowStatus":1,"commenter":0,"count":1000,"countrycode":86,"countryname":"中国","countrypic":"http://cdn1.drops.xin/country:38*28:86.png","createtime":"1469009349","crossBorder":0,"customTime":"","customWeeks":"","endTime":"1911686400","goodId":"ssmzwdycfcb9174408d59eb9de51d5aa","id":174,"isactivity":0,"isfrom":"1698975667","ismark":0,"isrecommend":1,"latitude":0,"longitude":0,"mark":0,"money01":0,"money01Detail":"","money02":0,"money02Detail":"","money03":0,"money03Detail":"","moneyDetail":[{"amount":"1000","desc":"1套","value":"109"}],"notices":"","parameter":"","parameteruri":"http://pond.waterdrop.xin/dbessmzfzcs.jpg","photoDetails":["http://pond.waterdrop.xin/dbessmzfzxq1.jpg","http://pond.waterdrop.xin/dbessmzfzxq2.jpg","http://pond.waterdrop.xin/dbessmzfzxq3.jpg","http://pond.waterdrop.xin/dbessmzfzxq4.jpg","http://pond.waterdrop.xin/dbessmzfzxq5.jpg","http://pond.waterdrop.xin/dbessmzfzxq6.jpg"],"score":0,"service":[{"id":"1","linkType":0,"title":"7天退换","type":1},{"id":"2","linkType":0,"title":"原厂直销","type":1},{"id":"3","linkType":0,"title":"正品保证","type":1}],"servicecode":["1","2","3"],"starid":0,"starname":"","startTime":"1468972800","status":0,"storeid":"","supplier":"","suppliercode":"","updatetime":"1469014838","uuid":"oYJ4Pv3s11Qqz64Thq1EGvlB8Axs","video":"","videoDetail":[],"wjSku":""}
         * goodCovery : http://pond.waterdrop.xin/dbessmzfzlb.jpg
         * goodId : ssmzwdycfcb9174408d59eb9de51d5aa
         * goodName : 少数民族舞蹈演出服
         * id : 378
         * orderId : 15010355709601
         * quantity : 1
         * tipId : 15691378862377
         * unitPrice : 109
         */

        private long dropId;
        private DropTipBean dropTip;
        private double favourablePrice;
        private GoodBean good;
        private String goodCovery;
        private String goodId;
        private String goodName;
        private int id;
        private long orderId;
        private int quantity;
        private long tipId;
        private double unitPrice;

        public long getDropId() {
            return dropId;
        }

        public void setDropId(long dropId) {
            this.dropId = dropId;
        }

        public DropTipBean getDropTip() {
            return dropTip;
        }

        public void setDropTip(DropTipBean dropTip) {
            this.dropTip = dropTip;
        }

        public double getFavourablePrice() {
            return favourablePrice;
        }

        public void setFavourablePrice(double favourablePrice) {
            this.favourablePrice = favourablePrice;
        }

        public GoodBean getGood() {
            return good;
        }

        public void setGood(GoodBean good) {
            this.good = good;
        }

        public String getGoodCovery() {
            return goodCovery;
        }

        public void setGoodCovery(String goodCovery) {
            this.goodCovery = goodCovery;
        }

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }

        public String getGoodName() {
            return goodName;
        }

        public void setGoodName(String goodName) {
            this.goodName = goodName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public long getTipId() {
            return tipId;
        }

        public void setTipId(long tipId) {
            this.tipId = tipId;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public static class DropTipBean implements Serializable{
            /**
             * browserNum : 340
             * categoryId : 2
             * cover : http://pond.waterdrop.xin/pjj-sttj-10@2x.jpg
             * createTime : 2017-07-13
             * createTimeShow : 12天前
             * createTimestamp : 1499943586000
             * createUid : 84944713
             * dropId : 14992389638051
             * likeNum : 370
             * photoNum : 1
             * showType : 1
             * status : 1
             * tipContent : 你以为只有地球人喝酒？外星人喝不喝我不知道
             * tipId : 15691378862377
             * tipTitle : 你以为只有地球人喝酒？外星人喝不喝我不知道
             * tipUrl : http://api.waterdrop.xin/drops_wechat/app_h5/tip-detail.html
             */

            private int browserNum;
            private int categoryId;
            private String cover;
            private String createTime;
            private String createTimeShow;
            private String createTimestamp;
            private int createUid;
            private long dropId;
            private int likeNum;
            private int photoNum;
            private int showType;
            private int status;
            private String tipContent;
            private long tipId;
            private String tipTitle;
            private String tipUrl;

            public int getBrowserNum() {
                return browserNum;
            }

            public void setBrowserNum(int browserNum) {
                this.browserNum = browserNum;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreateTimeShow() {
                return createTimeShow;
            }

            public void setCreateTimeShow(String createTimeShow) {
                this.createTimeShow = createTimeShow;
            }

            public String getCreateTimestamp() {
                return createTimestamp;
            }

            public void setCreateTimestamp(String createTimestamp) {
                this.createTimestamp = createTimestamp;
            }

            public int getCreateUid() {
                return createUid;
            }

            public void setCreateUid(int createUid) {
                this.createUid = createUid;
            }

            public long getDropId() {
                return dropId;
            }

            public void setDropId(long dropId) {
                this.dropId = dropId;
            }

            public int getLikeNum() {
                return likeNum;
            }

            public void setLikeNum(int likeNum) {
                this.likeNum = likeNum;
            }

            public int getPhotoNum() {
                return photoNum;
            }

            public void setPhotoNum(int photoNum) {
                this.photoNum = photoNum;
            }

            public int getShowType() {
                return showType;
            }

            public void setShowType(int showType) {
                this.showType = showType;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTipContent() {
                return tipContent;
            }

            public void setTipContent(String tipContent) {
                this.tipContent = tipContent;
            }

            public long getTipId() {
                return tipId;
            }

            public void setTipId(long tipId) {
                this.tipId = tipId;
            }

            public String getTipTitle() {
                return tipTitle;
            }

            public void setTipTitle(String tipTitle) {
                this.tipTitle = tipTitle;
            }

            public String getTipUrl() {
                return tipUrl;
            }

            public void setTipUrl(String tipUrl) {
                this.tipUrl = tipUrl;
            }
        }

        public static class GoodBean implements Serializable{
            /**
             * actAge :
             * actArea : 1
             * actCity :
             * actId2 :
             * actPic : http://pond.waterdrop.xin/dbessmzfzlb.jpg
             * actPicDetail : ["http://pond.waterdrop.xin/dbessmzfzb@2x.jpg"]
             * actPrice : 109
             * actTitle : 少数民族舞蹈演出服
             * actType : 301
             * average : 0
             * brand :
             * brandcode : 0
             * cardStatus : 0
             * collectShowStatus : 1
             * commenter : 0
             * count : 1000
             * countrycode : 86
             * countryname : 中国
             * countrypic : http://cdn1.drops.xin/country:38*28:86.png
             * createtime : 1469009349
             * crossBorder : 0
             * customTime :
             * customWeeks :
             * endTime : 1911686400
             * goodId : ssmzwdycfcb9174408d59eb9de51d5aa
             * id : 174
             * isactivity : 0
             * isfrom : 1698975667
             * ismark : 0
             * isrecommend : 1
             * latitude : 0
             * longitude : 0
             * mark : 0
             * money01 : 0
             * money01Detail :
             * money02 : 0
             * money02Detail :
             * money03 : 0
             * money03Detail :
             * moneyDetail : [{"amount":"1000","desc":"1套","value":"109"}]
             * notices :
             * parameter :
             * parameteruri : http://pond.waterdrop.xin/dbessmzfzcs.jpg
             * photoDetails : ["http://pond.waterdrop.xin/dbessmzfzxq1.jpg","http://pond.waterdrop.xin/dbessmzfzxq2.jpg","http://pond.waterdrop.xin/dbessmzfzxq3.jpg","http://pond.waterdrop.xin/dbessmzfzxq4.jpg","http://pond.waterdrop.xin/dbessmzfzxq5.jpg","http://pond.waterdrop.xin/dbessmzfzxq6.jpg"]
             * score : 0
             * service : [{"id":"1","linkType":0,"title":"7天退换","type":1},{"id":"2","linkType":0,"title":"原厂直销","type":1},{"id":"3","linkType":0,"title":"正品保证","type":1}]
             * servicecode : ["1","2","3"]
             * starid : 0
             * starname :
             * startTime : 1468972800
             * status : 0
             * storeid :
             * supplier :
             * suppliercode :
             * updatetime : 1469014838
             * uuid : oYJ4Pv3s11Qqz64Thq1EGvlB8Axs
             * video :
             * videoDetail : []
             * wjSku :
             */

            private String from;
            private int isWj;
            private String actAge;
            private int actArea;
            private String actCity;
            private String actId2;
            private String actPic;
            private Double actPrice;
            private String actTitle;
            private int actType;
            private double average;
            private String brand;
            private int brandcode;
            private int cardStatus;
            private int collectShowStatus;
            private int commenter;
            private int count;
            private int countrycode;
            private String countryname;
            private String countrypic;
            private String createtime;
            private int crossBorder;
            private String customTime;
            private String customWeeks;
            private String endTime;
            private String goodId;
            private int id;
            private int isactivity;
            private String isfrom;
            private int ismark;
            private int isrecommend;
            private int latitude;
            private int longitude;
            private int mark;
            private int money01;
            private String money01Detail;
            private int money02;
            private String money02Detail;
            private int money03;
            private String money03Detail;
            private String notices;
            private String parameter;
            private String parameteruri;
            private int score;
            private int starid;
            private String starname;
            private String startTime;
            private int status;
            private String storeid;
            private String supplier;
            private String suppliercode;
            private String updatetime;
            private String uuid;
            private String video;
            private String wjSku;
            private List<String> actPicDetail;
            private List<MoneyDetailBean> moneyDetail;
            private List<String> photoDetails;
            private List<ServiceBean> service;
            private List<String> servicecode;
            private List<GoodSkusBean> goodSkus;
            private List<?> videoDetail;

            public String getActAge() {
                return actAge;
            }

            public void setActAge(String actAge) {
                this.actAge = actAge;
            }

            public int getActArea() {
                return actArea;
            }

            public void setActArea(int actArea) {
                this.actArea = actArea;
            }

            public String getActCity() {
                return actCity;
            }

            public void setActCity(String actCity) {
                this.actCity = actCity;
            }

            public String getActId2() {
                return actId2;
            }

            public void setActId2(String actId2) {
                this.actId2 = actId2;
            }

            public String getActPic() {
                return actPic;
            }

            public void setActPic(String actPic) {
                this.actPic = actPic;
            }

            public Double getActPrice() {
                return actPrice;
            }

            public void setActPrice(Double actPrice) {
                this.actPrice = actPrice;
            }

            public String getActTitle() {
                return actTitle;
            }

            public void setActTitle(String actTitle) {
                this.actTitle = actTitle;
            }

            public int getActType() {
                return actType;
            }

            public void setActType(int actType) {
                this.actType = actType;
            }

            public double getAverage() {
                return average;
            }

            public void setAverage(double average) {
                this.average = average;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public int getBrandcode() {
                return brandcode;
            }

            public void setBrandcode(int brandcode) {
                this.brandcode = brandcode;
            }

            public int getCardStatus() {
                return cardStatus;
            }

            public void setCardStatus(int cardStatus) {
                this.cardStatus = cardStatus;
            }

            public int getCollectShowStatus() {
                return collectShowStatus;
            }

            public void setCollectShowStatus(int collectShowStatus) {
                this.collectShowStatus = collectShowStatus;
            }

            public int getCommenter() {
                return commenter;
            }

            public void setCommenter(int commenter) {
                this.commenter = commenter;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getCountrycode() {
                return countrycode;
            }

            public void setCountrycode(int countrycode) {
                this.countrycode = countrycode;
            }

            public String getCountryname() {
                return countryname;
            }

            public void setCountryname(String countryname) {
                this.countryname = countryname;
            }

            public String getCountrypic() {
                return countrypic;
            }

            public void setCountrypic(String countrypic) {
                this.countrypic = countrypic;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public int getCrossBorder() {
                return crossBorder;
            }

            public void setCrossBorder(int crossBorder) {
                this.crossBorder = crossBorder;
            }

            public String getCustomTime() {
                return customTime;
            }

            public void setCustomTime(String customTime) {
                this.customTime = customTime;
            }

            public String getCustomWeeks() {
                return customWeeks;
            }

            public void setCustomWeeks(String customWeeks) {
                this.customWeeks = customWeeks;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getGoodId() {
                return goodId;
            }

            public void setGoodId(String goodId) {
                this.goodId = goodId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsactivity() {
                return isactivity;
            }

            public void setIsactivity(int isactivity) {
                this.isactivity = isactivity;
            }

            public String getIsfrom() {
                return isfrom;
            }

            public void setIsfrom(String isfrom) {
                this.isfrom = isfrom;
            }

            public int getIsmark() {
                return ismark;
            }

            public void setIsmark(int ismark) {
                this.ismark = ismark;
            }

            public int getIsrecommend() {
                return isrecommend;
            }

            public void setIsrecommend(int isrecommend) {
                this.isrecommend = isrecommend;
            }

            public int getLatitude() {
                return latitude;
            }

            public void setLatitude(int latitude) {
                this.latitude = latitude;
            }

            public int getLongitude() {
                return longitude;
            }

            public void setLongitude(int longitude) {
                this.longitude = longitude;
            }

            public int getMark() {
                return mark;
            }

            public void setMark(int mark) {
                this.mark = mark;
            }

            public int getMoney01() {
                return money01;
            }

            public void setMoney01(int money01) {
                this.money01 = money01;
            }

            public String getMoney01Detail() {
                return money01Detail;
            }

            public void setMoney01Detail(String money01Detail) {
                this.money01Detail = money01Detail;
            }

            public int getMoney02() {
                return money02;
            }

            public void setMoney02(int money02) {
                this.money02 = money02;
            }

            public String getMoney02Detail() {
                return money02Detail;
            }

            public void setMoney02Detail(String money02Detail) {
                this.money02Detail = money02Detail;
            }

            public int getMoney03() {
                return money03;
            }

            public void setMoney03(int money03) {
                this.money03 = money03;
            }

            public String getMoney03Detail() {
                return money03Detail;
            }

            public void setMoney03Detail(String money03Detail) {
                this.money03Detail = money03Detail;
            }

            public String getNotices() {
                return notices;
            }

            public void setNotices(String notices) {
                this.notices = notices;
            }

            public String getParameter() {
                return parameter;
            }

            public void setParameter(String parameter) {
                this.parameter = parameter;
            }

            public String getParameteruri() {
                return parameteruri;
            }

            public void setParameteruri(String parameteruri) {
                this.parameteruri = parameteruri;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getStarid() {
                return starid;
            }

            public void setStarid(int starid) {
                this.starid = starid;
            }

            public String getStarname() {
                return starname;
            }

            public void setStarname(String starname) {
                this.starname = starname;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStoreid() {
                return storeid;
            }

            public void setStoreid(String storeid) {
                this.storeid = storeid;
            }

            public String getSupplier() {
                return supplier;
            }

            public void setSupplier(String supplier) {
                this.supplier = supplier;
            }

            public String getSuppliercode() {
                return suppliercode;
            }

            public void setSuppliercode(String suppliercode) {
                this.suppliercode = suppliercode;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getWjSku() {
                return wjSku;
            }

            public void setWjSku(String wjSku) {
                this.wjSku = wjSku;
            }

            public List<String> getActPicDetail() {
                return actPicDetail;
            }

            public void setActPicDetail(List<String> actPicDetail) {
                this.actPicDetail = actPicDetail;
            }

            public List<MoneyDetailBean> getMoneyDetail() {
                return moneyDetail;
            }

            public void setMoneyDetail(List<MoneyDetailBean> moneyDetail) {
                this.moneyDetail = moneyDetail;
            }

            public List<String> getPhotoDetails() {
                return photoDetails;
            }

            public void setPhotoDetails(List<String> photoDetails) {
                this.photoDetails = photoDetails;
            }

            public List<ServiceBean> getService() {
                return service;
            }

            public void setService(List<ServiceBean> service) {
                this.service = service;
            }

            public List<String> getServicecode() {
                return servicecode;
            }

            public void setServicecode(List<String> servicecode) {
                this.servicecode = servicecode;
            }

            public List<?> getVideoDetail() {
                return videoDetail;
            }

            public void setVideoDetail(List<?> videoDetail) {
                this.videoDetail = videoDetail;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public int getIsWj() {
                return isWj;
            }

            public void setIsWj(int isWj) {
                this.isWj = isWj;
            }

            public List<GoodSkusBean> getGoodSkus() {
                return goodSkus;
            }

            public static class MoneyDetailBean {
                /**
                 * amount : 1000
                 * desc : 1套
                 * value : 109
                 */

                private String amount;
                private String desc;
                private String value;

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class ServiceBean {
                /**
                 * id : 1
                 * linkType : 0
                 * title : 7天退换
                 * type : 1
                 */

                private String id;
                private int linkType;
                private String title;
                private int type;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public int getLinkType() {
                    return linkType;
                }

                public void setLinkType(int linkType) {
                    this.linkType = linkType;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }
            }

            public static class GoodSkusBean implements Serializable{
                /**
                 * goodId : 201710101502259512395686
                 * price : 230.00
                 * quantity : 9999
                 * skuId : 1
                 * skuName : 20g*盒
                 */

                private String goodId;
                private String price;
                private int quantity;
                private String skuId;
                private String skuName;

                public String getGoodId() {
                    return goodId;
                }

                public void setGoodId(String goodId) {
                    this.goodId = goodId;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public String getSkuId() {
                    return skuId;
                }

                public void setSkuId(String skuId) {
                    this.skuId = skuId;
                }

                public String getSkuName() {
                    return skuName;
                }

                public void setSkuName(String skuName) {
                    this.skuName = skuName;
                }
            }
        }
    }
}