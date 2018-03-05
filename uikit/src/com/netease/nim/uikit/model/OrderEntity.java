package com.netease.nim.uikit.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Smile on 2017/6/28.
 */

public class OrderEntity implements Serializable {

    private String nextSearchStart;
    private int pageSize;
    private int totalSize;
    private List<ResultsBean> results;

    public String getNextSearchStart() {
        return nextSearchStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public static class ResultsBean implements Serializable{
        /**
         * billCompany :
         * billTfn :
         * billType : -1
         * createTime : 2017-10-12 16:28:40
         * createTimestamp : 1507796920000
         * deliveryAddress : 你你是在说我
         * deliveryAddressId : 140
         * deliveryCity : 市辖区
         * deliveryDistrict : 东城区
         * deliveryMobile : 11111111111
         * deliveryName : 黄志恒
         * deliveryProv : 北京市
         * from : raw
         * goods : [{"dropId":18124286833,"dropTip":{"browserNum":0,"categoryId":300,"createTime":"2017-09-26","createTimeShow":"15天前","createTimestamp":"1506421154000","createUid":16403152,"dropId":18124286833,"likeNum":0,"photoNum":0,"showType":1,"status":0,"tipContent":"","tipId":1378862390,"tipTitle":"商城","updateTimestamp":"1506421157000","vrFlag":0,"vrUrl":""},"favourablePrice":230,"good":{"actDes":"","actId2":"actId2","actPic":"http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png","actPicDetail":["http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png"],"actPrice":230,"actTitle":"秀碧除疤膏 深度除疤 亮白肌肤 20g*盒","actType":1,"brand":"秀碧","collectShowStatus":1,"count":332,"countrycode":304,"countryname":"德国","countrypic":"http://pond.waterdrop.xin/304.png","crossBorder":1,"from":"wujie","gmtCreated":1507618945000,"goodCode":"FPQMER0002","goodId":"201710101502259512395686","goodSkus":[{"goodId":"201710101502259512395686","price":"230.00","quantity":9999,"skuId":"1","skuName":"20g*盒"},{"goodId":"201710101502259512395686","price":"400.00","quantity":9999,"skuId":"2","skuName":"40g*盒"},{"goodId":"201710101502259512395686","price":"500.00","quantity":0,"skuId":"3","skuName":"60g*盒"}],"id":1,"isWj":1,"isrecommend":1,"maxPrice":"500.00","minPrice":"230.00","moneyDetail":[{"amount":"332","desc":"件","specificationId":795,"title":"件","value":"230.0"}],"parameteruri":"http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png","photoDetails":[],"settlePrice":230,"status":0,"uuid":"uuid","video":""},"goodCovery":"http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png","goodId":"201710101502259512395686","goodName":"  秀碧除疤膏 深度除疤 亮白肌肤 20g*盒","id":670,"isWj":0,"orderId":15077969207671,"quantity":1,"skuId":"1","tipId":1378862390,"unitPrice":230}]
         * idcardBack : http://oogijmhwg.bkt.clouddn.com/9594ae2b23f4cbf688d746e994125e28.png
         * idcardFront : http://oogijmhwg.bkt.clouddn.com/35db5bfb7c3e77051873b0e8e6f09372.png
         * idcardNo : 410782199401069552
         * needBill : 0
         * orderId : 15077969207671
         * payInfo : h5
         * payNo :
         * payTime :
         * price : 230.00
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
        private String from;
        private String idcardBack;
        private String idcardFront;
        private String idcardNo;
        private int needBill;
        private long orderId;
        private String payInfo;
        private String payNo;
        private String payTime;
        private String price;
        private int status;
        private int uid;
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

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
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

        public String getIdcardNo() {
            return idcardNo;
        }

        public void setIdcardNo(String idcardNo) {
            this.idcardNo = idcardNo;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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

        public static class GoodsBean implements Serializable{
            /**
             * dropId : 18124286833
             * dropTip : {"browserNum":0,"categoryId":300,"createTime":"2017-09-26","createTimeShow":"15天前","createTimestamp":"1506421154000","createUid":16403152,"dropId":18124286833,"likeNum":0,"photoNum":0,"showType":1,"status":0,"tipContent":"","tipId":1378862390,"tipTitle":"商城","updateTimestamp":"1506421157000","vrFlag":0,"vrUrl":""}
             * favourablePrice : 230
             * good : {"actDes":"","actId2":"actId2","actPic":"http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png","actPicDetail":["http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png"],"actPrice":230,"actTitle":"秀碧除疤膏 深度除疤 亮白肌肤 20g*盒","actType":1,"brand":"秀碧","collectShowStatus":1,"count":332,"countrycode":304,"countryname":"德国","countrypic":"http://pond.waterdrop.xin/304.png","crossBorder":1,"from":"wujie","gmtCreated":1507618945000,"goodCode":"FPQMER0002","goodId":"201710101502259512395686","goodSkus":[{"goodId":"201710101502259512395686","price":"230.00","quantity":9999,"skuId":"1","skuName":"20g*盒"},{"goodId":"201710101502259512395686","price":"400.00","quantity":9999,"skuId":"2","skuName":"40g*盒"},{"goodId":"201710101502259512395686","price":"500.00","quantity":0,"skuId":"3","skuName":"60g*盒"}],"id":1,"isWj":1,"isrecommend":1,"maxPrice":"500.00","minPrice":"230.00","moneyDetail":[{"amount":"332","desc":"件","specificationId":795,"title":"件","value":"230.0"}],"parameteruri":"http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png","photoDetails":[],"settlePrice":230,"status":0,"uuid":"uuid","video":""}
             * goodCovery : http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png
             * goodId : 201710101502259512395686
             * goodName :   秀碧除疤膏 深度除疤 亮白肌肤 20g*盒
             * id : 670
             * isWj : 0
             * orderId : 15077969207671
             * quantity : 1
             * skuId : 1
             * tipId : 1378862390
             * unitPrice : 230
             */

            private long dropId;
            private DropTipBean dropTip;
            private double favourablePrice;
            private GoodBean good;
            private String goodCovery;
            private String goodId;
            private String goodName;
            private int id;
            private int isWj;
            private long orderId;
            private int quantity;
            private String skuId;
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

            public int getIsWj() {
                return isWj;
            }

            public void setIsWj(int isWj) {
                this.isWj = isWj;
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

            public String getSkuId() {
                return skuId;
            }

            public void setSkuId(String skuId) {
                this.skuId = skuId;
            }

            public long getTipId() {
                return tipId;
            }

            public double getUnitPrice() {
                return unitPrice;
            }

            public static class DropTipBean implements Serializable{
                /**
                 * browserNum : 0
                 * categoryId : 300
                 * createTime : 2017-09-26
                 * createTimeShow : 15天前
                 * createTimestamp : 1506421154000
                 * createUid : 16403152
                 * dropId : 18124286833
                 * likeNum : 0
                 * photoNum : 0
                 * showType : 1
                 * status : 0
                 * tipContent :
                 * tipId : 1378862390
                 * tipTitle : 商城
                 * updateTimestamp : 1506421157000
                 * vrFlag : 0
                 * vrUrl :
                 */

                private int browserNum;
                private int categoryId;
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
                private String updateTimestamp;
                private int vrFlag;
                private String vrUrl;
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

                public String getUpdateTimestamp() {
                    return updateTimestamp;
                }

                public void setUpdateTimestamp(String updateTimestamp) {
                    this.updateTimestamp = updateTimestamp;
                }

                public int getVrFlag() {
                    return vrFlag;
                }

                public void setVrFlag(int vrFlag) {
                    this.vrFlag = vrFlag;
                }

                public String getVrUrl() {
                    return vrUrl;
                }

                public void setVrUrl(String vrUrl) {
                    this.vrUrl = vrUrl;
                }

                public String getTipUrl() {
                    return tipUrl;
                }
            }

            public static class GoodBean implements Serializable{
                /**
                 * actDes :
                 * actId2 : actId2
                 * actPic : http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png
                 * actPicDetail : ["http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png"]
                 * actPrice : 230
                 * actTitle : 秀碧除疤膏 深度除疤 亮白肌肤 20g*盒
                 * actType : 1
                 * brand : 秀碧
                 * collectShowStatus : 1
                 * count : 332
                 * countrycode : 304
                 * countryname : 德国
                 * countrypic : http://pond.waterdrop.xin/304.png
                 * crossBorder : 1
                 * from : wujie
                 * gmtCreated : 1507618945000
                 * goodCode : FPQMER0002
                 * goodId : 201710101502259512395686
                 * goodSkus : [{"goodId":"201710101502259512395686","price":"230.00","quantity":9999,"skuId":"1","skuName":"20g*盒"},{"goodId":"201710101502259512395686","price":"400.00","quantity":9999,"skuId":"2","skuName":"40g*盒"},{"goodId":"201710101502259512395686","price":"500.00","quantity":0,"skuId":"3","skuName":"60g*盒"}]
                 * id : 1
                 * isWj : 1
                 * isrecommend : 1
                 * maxPrice : 500.00
                 * minPrice : 230.00
                 * moneyDetail : [{"amount":"332","desc":"件","specificationId":795,"title":"件","value":"230.0"}]
                 * parameteruri : http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png
                 * photoDetails : []
                 * settlePrice : 230
                 * status : 0
                 * uuid : uuid
                 * video :
                 */

                private String actDes;
                private String actId2;
                private String actPic;
                private double actPrice;
                private String actTitle;
                private int actType;
                private String brand;
                private int collectShowStatus;
                private int count;
                private int countrycode;
                private String countryname;
                private String countrypic;
                private int crossBorder;
                private String from;
                private long gmtCreated;
                private String goodCode;
                private String goodId;
                private int id;
                private int isWj;
                private int isrecommend;
                private String maxPrice;
                private String minPrice;
                private String parameteruri;
                private double settlePrice;
                private int status;
                private String uuid;
                private String video;
                private List<String> actPicDetail;
                private List<GoodSkusBean> goodSkus;
                private List<MoneyDetailBean> moneyDetail;
                private List<ServiceBean> service;
                private List<?> photoDetails;

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

                public double getActPrice() {
                    return actPrice;
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

                public String getBrand() {
                    return brand;
                }

                public void setBrand(String brand) {
                    this.brand = brand;
                }

                public int getCollectShowStatus() {
                    return collectShowStatus;
                }

                public void setCollectShowStatus(int collectShowStatus) {
                    this.collectShowStatus = collectShowStatus;
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

                public int getCrossBorder() {
                    return crossBorder;
                }

                public void setCrossBorder(int crossBorder) {
                    this.crossBorder = crossBorder;
                }

                public String getFrom() {
                    return from;
                }

                public void setFrom(String from) {
                    this.from = from;
                }

                public long getGmtCreated() {
                    return gmtCreated;
                }

                public void setGmtCreated(long gmtCreated) {
                    this.gmtCreated = gmtCreated;
                }

                public String getGoodCode() {
                    return goodCode;
                }

                public void setGoodCode(String goodCode) {
                    this.goodCode = goodCode;
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

                public int getIsWj() {
                    return isWj;
                }

                public void setIsWj(int isWj) {
                    this.isWj = isWj;
                }

                public int getIsrecommend() {
                    return isrecommend;
                }

                public void setIsrecommend(int isrecommend) {
                    this.isrecommend = isrecommend;
                }

                public String getMaxPrice() {
                    return maxPrice;
                }

                public void setMaxPrice(String maxPrice) {
                    this.maxPrice = maxPrice;
                }

                public String getMinPrice() {
                    return minPrice;
                }

                public void setMinPrice(String minPrice) {
                    this.minPrice = minPrice;
                }

                public String getParameteruri() {
                    return parameteruri;
                }

                public void setParameteruri(String parameteruri) {
                    this.parameteruri = parameteruri;
                }

                public double getSettlePrice() {
                    return settlePrice;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
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

                public List<String> getActPicDetail() {
                    return actPicDetail;
                }

                public void setActPicDetail(List<String> actPicDetail) {
                    this.actPicDetail = actPicDetail;
                }

                public List<GoodSkusBean> getGoodSkus() {
                    return goodSkus;
                }

                public void setGoodSkus(List<GoodSkusBean> goodSkus) {
                    this.goodSkus = goodSkus;
                }

                public List<MoneyDetailBean> getMoneyDetail() {
                    return moneyDetail;
                }

                public void setMoneyDetail(List<MoneyDetailBean> moneyDetail) {
                    this.moneyDetail = moneyDetail;
                }

                public List<?> getPhotoDetails() {
                    return photoDetails;
                }

                public void setPhotoDetails(List<?> photoDetails) {
                    this.photoDetails = photoDetails;
                }

                public List<ServiceBean> getService() {
                    return service;
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

                public static class MoneyDetailBean implements Serializable{
                    /**
                     * amount : 332
                     * desc : 件
                     * specificationId : 795
                     * title : 件
                     * value : 230.0
                     */

                    private String amount;
                    private String desc;
                    private int specificationId;
                    private String title;
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

                    public int getSpecificationId() {
                        return specificationId;
                    }

                    public void setSpecificationId(int specificationId) {
                        this.specificationId = specificationId;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
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
            }
        }
    }
}


