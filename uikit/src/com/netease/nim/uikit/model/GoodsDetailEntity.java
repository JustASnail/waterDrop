package com.netease.nim.uikit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/7.
 */

public class GoodsDetailEntity extends BaseResultEntity {

    /**
     * collectStatus : 0
     * good : {"actAddress":"","actAge":"","actArea":1,"actCity":"","actDes":"","actId2":"","actPic":"http://pond.waterdrop.xin/dbeczlb@2x.jpg","actPicDetail":["http://pond.waterdrop.xin/dbeczbaner@2x.jpg"],"actPrice":418,"actTitle":"天士力帝泊洱即溶普洱茶珍","actType":301,"average":0,"brand":"","brandcode":0,"cardStatus":0,"collectShowStatus":1,"commenter":0,"count":1000,"countrycode":86,"countryname":"中国","countrypic":"http://cdn1.drops.xin/country:38*28:86.png","createtime":"1469009349","crossBorder":0,"customTime":"","customWeeks":"","detail":"","endTime":"1911686400","goodId":"dbecz152fcb9174408d59eb9de51d5aa","id":33,"isactivity":0,"isfrom":"1698975667","ismark":0,"isrecommend":1,"latitude":0,"longitude":0,"mark":0,"money01":0,"money01Detail":"","money02":0,"money02Detail":"","money03":0,"money03Detail":"","moneyDetail":[{"amount":"1000","desc":"100支／盒","value":"418"}],"notices":"","parameter":"","parameteruri":"http://pond.waterdrop.xin/dbeczcs.jpg","photoDetails":["http://pond.waterdrop.xin/dbecz1.jpg","http://pond.waterdrop.xin/dbecz2.jpg","http://pond.waterdrop.xin/dbecz3.jpg","http://pond.waterdrop.xin/dbecz4.jpg"],"score":0,"service":[{"id":"1","linkType":0,"title":"7天退换","type":1},{"id":"2","linkType":0,"title":"原厂直销","type":1},{"id":"3","linkType":0,"title":"正品保证","type":1}],"servicecode":["1","2","3"],"starid":0,"starname":"","startTime":"1468972800","status":0,"storeid":"","supplier":"","suppliercode":"","updatetime":"1469014838","uuid":"oYJ4Pv3s11Qqz64Thq1EGvlB8Axs","video":"","videoDetail":[],"wjSku":""}
     * shop : {"gmtCreated":1497435522000,"gmtModified":1497435524000,"shopId":86,"shopName":"中国馆","shopPhoto":"http://img.zcool.cn/community/focus/181159409da400000125d133fe38.jpg","status":1}
     */

    private int collectStatus;
    private GoodBean good;
    private ShopBean shop;

    public int getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(int collectStatus) {
        this.collectStatus = collectStatus;
    }

    public GoodBean getGood() {
        return good;
    }

    public void setGood(GoodBean good) {
        this.good = good;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public static class GoodBean implements Serializable{
        /**
         * actAddress :
         * actAge :
         * actArea : 1
         * actCity :
         * actDes :
         * actId2 :
         * actPic : http://pond.waterdrop.xin/dbeczlb@2x.jpg
         * actPicDetail : ["http://pond.waterdrop.xin/dbeczbaner@2x.jpg"]
         * actPrice : 418
         * actTitle : 天士力帝泊洱即溶普洱茶珍
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
         * detail :
         * endTime : 1911686400
         * goodId : dbecz152fcb9174408d59eb9de51d5aa
         * id : 33
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
         * moneyDetail : [{"amount":"1000","desc":"100支／盒","value":"418"}]
         * notices :
         * parameter :
         * parameteruri : http://pond.waterdrop.xin/dbeczcs.jpg
         * photoDetails : ["http://pond.waterdrop.xin/dbecz1.jpg","http://pond.waterdrop.xin/dbecz2.jpg","http://pond.waterdrop.xin/dbecz3.jpg","http://pond.waterdrop.xin/dbecz4.jpg"]
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

        private String actAddress;
        private String actAge;
        private int actArea;
        private String actCity;
        private String actDes;
        private String actId2;
        private String actPic;
        private Double actPrice;
        private String actTitle;
        private int actType;
        private float average;
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
        private String from;
        private String customTime;
        private String customWeeks;
        private String detail;
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
        private List<VideoDetailBean> videoDetail;
        private List<GoodSku> goodSkus;
        private String minPrice;
        private String maxPrice;

        public String getMinPrice() {
            return minPrice;
        }

        public String getMaxPrice() {
            return maxPrice;
        }

        public List<GoodSku> getGoodSkus() {
            return goodSkus;
        }

        public String getFrom() {
            return from;
        }

        public boolean fromWuJi(){
            return "wujie".equals(from);
        }

        public boolean isCrossBorder(){
            return crossBorder == 1;
        }

        public String getActAddress() {
            return actAddress;
        }

        public void setActAddress(String actAddress) {
            this.actAddress = actAddress;
        }

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

        public String getActDes() {
            return actDes;
        }

        public void setActDes(String actDes) {
            this.actDes = actDes;
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

        public float getAverage() {
            return average;
        }

        public void setAverage(float average) {
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

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
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

        public List<VideoDetailBean> getVideoDetail() {
            return videoDetail;
        }

        public void setVideoDetail(List<VideoDetailBean> videoDetail) {
            this.videoDetail = videoDetail;
        }

        public static class VideoDetailBean implements Serializable{

            private String img;
            private String url;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class GoodSku implements Serializable{
            private String goodId;
            private String photo;
            private String price;
            private int quantity;
            private String skuId;
            private String skuName;

            public void setQuantity(int quantity){
                this.quantity = quantity;
            }

            public String getGoodId() {
                return goodId;
            }

            public String getPhoto() {
                return photo;
            }

            public String getPrice() {
                return price;
            }

            public int getQuantity() {
                return quantity;
            }

            public String getSkuId() {
                return skuId;
            }

            public String getSkuName() {
                return skuName;
            }
        }

        public static class MoneyDetailBean implements Serializable{
            /**
             * amount : 1000
             * desc : 100支／盒
             * value : 418
             */

            private String amount;
            private String desc;
            private int specificationId;
            private String title;
            private String value;

            public int getSpecificationId() {
                return specificationId;
            }

            public String getTitle() {
                return title;
            }

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

        public static class ServiceBean implements Serializable{
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
    }

    public static class ShopBean implements Serializable{
        /**
         * gmtCreated : 1497435522000
         * gmtModified : 1497435524000
         * shopId : 86
         * shopName : 中国馆
         * shopPhoto : http://img.zcool.cn/community/focus/181159409da400000125d133fe38.jpg
         * status : 1
         */

        private long gmtCreated;
        private long gmtModified;
        private int shopId;
        private String shopName;
        private String shopPhoto;
        private int status;

        public long getGmtCreated() {
            return gmtCreated;
        }

        public void setGmtCreated(long gmtCreated) {
            this.gmtCreated = gmtCreated;
        }

        public long getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(long gmtModified) {
            this.gmtModified = gmtModified;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopPhoto() {
            return shopPhoto;
        }

        public void setShopPhoto(String shopPhoto) {
            this.shopPhoto = shopPhoto;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
