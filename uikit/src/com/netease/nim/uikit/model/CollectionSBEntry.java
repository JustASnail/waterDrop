package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/6/27.
 */

public class CollectionSBEntry extends BaseResultEntity {

    /**
     * code : 200
     * data : {"goods":[{"actAddress":"上海发货","actAge":"","actArea":1,"actCity":"","actDes":"","actId2":"","actPic":"http://cdn1.drops.xin/帝泊洱274×274.JPG","actPicDetail":"[\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-商品详情Banner1.JPG\",\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-商品详情Banner2.JPG\",\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-商品详情Banner3.JPG\"]","actPrice":228,"actTitle":"帝泊洱三合一即溶普洱茶珍25.2g/盒","actType":301,"average":5,"brand":"帝泊洱","brandcode":0,"cardStatus":0,"commenter":2,"count":175956,"countrycode":86,"countryname":"中国","createtime":"1469009349","crossBorder":0,"customTime":"","customWeeks":"[]","detail":"111","endTime":"1911686400","expressCorp":"","expressId":"","goodId":"cdaeb152fcb9174408d59eb9de51d5aa","id":1,"isactivity":0,"isfrom":"1698975667","ismark":0,"isrecommend":1,"latitude":0,"longitude":0,"mark":0,"money01":0,"money01Detail":"","money02":0,"money02Detail":"","money03":0,"money03Detail":"","moneyDetail":"[{\"amount\":\"\",\"value2\":\"\",\"value\":\"228\",\"desc\":\"即溶古树普洱茶珍0.5g*14+即溶薏仁普洱茶珍0.8g*14+即溶荷叶冬瓜普洱茶珍0.5g*14\"}]","notices":"111","parameter":"","parameteruri":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-单盒装.jpg","score":10,"service":"{\"id\":1,\"title\":\"7天无理由退货\",\"linkType\":0,\"type\":1},{\"id\":2,\"title\":\"原厂直销\",\"linkType\":0,\"type\":1},{\"id\":3,\"title\":\"正品保证\",\"linkType\":0,\"type\":1}","servicecode":"1,2,3","starid":0,"starname":"","startTime":"1468972800","status":2,"storeid":"","supplier":"上海银贸品牌管理咨询有限公司","suppliercode":"86021000001","type01":1,"type02":1,"type03":0,"type04":1,"updatetime":"1496916342","uuid":"oYJ4Pv3s11Qqz64Thq1EGvlB8Axs","video":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品广告篇.mp4","videoDetail":"[{\"img\":\"http://oosuclqg5.bkt.clouddn.com/diboer.jpg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品科技篇.mp4\"},{\"img\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品场景篇.jpg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品场景篇.mp4\"},{\"img\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-视频列表图-产品详情篇.jpg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-视频列表图-产品详情篇.mp4\"},{\"img\":\"http://cdn001.waterdrop.xin/dbr81.jpeg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-品牌形象篇.mp4\"},{\"img\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-企业资质篇.jpg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-企业资质篇.mp4\"},{\"img\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品文化篇.jpg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品文化篇.mp4\"}]","wjSku":""}],"totalSize":1}
     * message : 成功
     * method : COLLECT-GOOD-RETRIEVE
     */

    private int code;
    private DataBean data;
    private String message;
    private String method;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public static class DataBean {
        /**
         * goods : [{"actAddress":"上海发货","actAge":"","actArea":1,"actCity":"","actDes":"","actId2":"","actPic":"http://cdn1.drops.xin/帝泊洱274×274.JPG","actPicDetail":"[\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-商品详情Banner1.JPG\",\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-商品详情Banner2.JPG\",\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-商品详情Banner3.JPG\"]","actPrice":228,"actTitle":"帝泊洱三合一即溶普洱茶珍25.2g/盒","actType":301,"average":5,"brand":"帝泊洱","brandcode":0,"cardStatus":0,"commenter":2,"count":175956,"countrycode":86,"countryname":"中国","createtime":"1469009349","crossBorder":0,"customTime":"","customWeeks":"[]","detail":"111","endTime":"1911686400","expressCorp":"","expressId":"","goodId":"cdaeb152fcb9174408d59eb9de51d5aa","id":1,"isactivity":0,"isfrom":"1698975667","ismark":0,"isrecommend":1,"latitude":0,"longitude":0,"mark":0,"money01":0,"money01Detail":"","money02":0,"money02Detail":"","money03":0,"money03Detail":"","moneyDetail":"[{\"amount\":\"\",\"value2\":\"\",\"value\":\"228\",\"desc\":\"即溶古树普洱茶珍0.5g*14+即溶薏仁普洱茶珍0.8g*14+即溶荷叶冬瓜普洱茶珍0.5g*14\"}]","notices":"111","parameter":"","parameteruri":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-单盒装.jpg","score":10,"service":"{\"id\":1,\"title\":\"7天无理由退货\",\"linkType\":0,\"type\":1},{\"id\":2,\"title\":\"原厂直销\",\"linkType\":0,\"type\":1},{\"id\":3,\"title\":\"正品保证\",\"linkType\":0,\"type\":1}","servicecode":"1,2,3","starid":0,"starname":"","startTime":"1468972800","status":2,"storeid":"","supplier":"上海银贸品牌管理咨询有限公司","suppliercode":"86021000001","type01":1,"type02":1,"type03":0,"type04":1,"updatetime":"1496916342","uuid":"oYJ4Pv3s11Qqz64Thq1EGvlB8Axs","video":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品广告篇.mp4","videoDetail":"[{\"img\":\"http://oosuclqg5.bkt.clouddn.com/diboer.jpg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品科技篇.mp4\"},{\"img\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品场景篇.jpg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品场景篇.mp4\"},{\"img\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-视频列表图-产品详情篇.jpg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-视频列表图-产品详情篇.mp4\"},{\"img\":\"http://cdn001.waterdrop.xin/dbr81.jpeg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-品牌形象篇.mp4\"},{\"img\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-企业资质篇.jpg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-企业资质篇.mp4\"},{\"img\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品文化篇.jpg\",\"url\":\"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品文化篇.mp4\"}]","wjSku":""}]
         * totalSize : 1
         */

        private int totalSize;
        private List<GoodsBean> goods;

        public int getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(int totalSize) {
            this.totalSize = totalSize;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * actAddress : 上海发货
             * actAge :
             * actArea : 1
             * actCity :
             * actDes :
             * actId2 :
             * actPic : http://cdn1.drops.xin/帝泊洱274×274.JPG
             * actPicDetail : ["http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-商品详情Banner1.JPG","http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-商品详情Banner2.JPG","http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-商品详情Banner3.JPG"]
             * actPrice : 228
             * actTitle : 帝泊洱三合一即溶普洱茶珍25.2g/盒
             * actType : 301
             * average : 5
             * brand : 帝泊洱
             * brandcode : 0
             * cardStatus : 0
             * commenter : 2
             * count : 175956
             * countrycode : 86
             * countryname : 中国
             * createtime : 1469009349
             * crossBorder : 0
             * customTime :
             * customWeeks : []
             * detail : 111
             * endTime : 1911686400
             * expressCorp :
             * expressId :
             * goodId : cdaeb152fcb9174408d59eb9de51d5aa
             * id : 1
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
             * moneyDetail : [{"amount":"","value2":"","value":"228","desc":"即溶古树普洱茶珍0.5g*14+即溶薏仁普洱茶珍0.8g*14+即溶荷叶冬瓜普洱茶珍0.5g*14"}]
             * notices : 111
             * parameter :
             * parameteruri : http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-单盒装.jpg
             * score : 10
             * service : {"id":1,"title":"7天无理由退货","linkType":0,"type":1},{"id":2,"title":"原厂直销","linkType":0,"type":1},{"id":3,"title":"正品保证","linkType":0,"type":1}
             * servicecode : 1,2,3
             * starid : 0
             * starname :
             * startTime : 1468972800
             * status : 2
             * storeid :
             * supplier : 上海银贸品牌管理咨询有限公司
             * suppliercode : 86021000001
             * type01 : 1
             * type02 : 1
             * type03 : 0
             * type04 : 1
             * updatetime : 1496916342
             * uuid : oYJ4Pv3s11Qqz64Thq1EGvlB8Axs
             * video : http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品广告篇.mp4
             * videoDetail : [{"img":"http://oosuclqg5.bkt.clouddn.com/diboer.jpg","url":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品科技篇.mp4"},{"img":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品场景篇.jpg","url":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品场景篇.mp4"},{"img":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-视频列表图-产品详情篇.jpg","url":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-视频列表图-产品详情篇.mp4"},{"img":"http://cdn001.waterdrop.xin/dbr81.jpeg","url":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-品牌形象篇.mp4"},{"img":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-企业资质篇.jpg","url":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-企业资质篇.mp4"},{"img":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品文化篇.jpg","url":"http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品文化篇.mp4"}]
             * wjSku :
             */

            private String actAddress;
            private String actAge;
            private int actArea;
            private String actCity;
            private String actDes;
            private String actId2;
            private String actPic;
            private String actPicDetail;
            private Double actPrice;
            private String actTitle;
            private int actType;
            private float average;
            private String brand;
            private int brandcode;
            private int cardStatus;
            private int commenter;
            private int count;
            private int countrycode;
            private String countryname;
            private String createtime;
            private int crossBorder;
            private String customTime;
            private String customWeeks;
            private String detail;
            private String endTime;
            private String expressCorp;
            private String expressId;
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
            private String moneyDetail;
            private String notices;
            private String parameter;
            private String parameteruri;
            private int score;
            private String service;
            private String servicecode;
            private int starid;
            private String starname;
            private String startTime;
            private int status;
            private String storeid;
            private String supplier;
            private String suppliercode;
            private int type01;
            private int type02;
            private int type03;
            private int type04;
            private String updatetime;
            private String uuid;
            private String video;
            private String videoDetail;
            private String wjSku;

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

            public String getActPicDetail() {
                return actPicDetail;
            }

            public void setActPicDetail(String actPicDetail) {
                this.actPicDetail = actPicDetail;
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

            public String getExpressCorp() {
                return expressCorp;
            }

            public void setExpressCorp(String expressCorp) {
                this.expressCorp = expressCorp;
            }

            public String getExpressId() {
                return expressId;
            }

            public void setExpressId(String expressId) {
                this.expressId = expressId;
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

            public String getMoneyDetail() {
                return moneyDetail;
            }

            public void setMoneyDetail(String moneyDetail) {
                this.moneyDetail = moneyDetail;
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

            public String getService() {
                return service;
            }

            public void setService(String service) {
                this.service = service;
            }

            public String getServicecode() {
                return servicecode;
            }

            public void setServicecode(String servicecode) {
                this.servicecode = servicecode;
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

            public int getType01() {
                return type01;
            }

            public void setType01(int type01) {
                this.type01 = type01;
            }

            public int getType02() {
                return type02;
            }

            public void setType02(int type02) {
                this.type02 = type02;
            }

            public int getType03() {
                return type03;
            }

            public void setType03(int type03) {
                this.type03 = type03;
            }

            public int getType04() {
                return type04;
            }

            public void setType04(int type04) {
                this.type04 = type04;
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

            public String getVideoDetail() {
                return videoDetail;
            }

            public void setVideoDetail(String videoDetail) {
                this.videoDetail = videoDetail;
            }

            public String getWjSku() {
                return wjSku;
            }

            public void setWjSku(String wjSku) {
                this.wjSku = wjSku;
            }
        }
    }
}
