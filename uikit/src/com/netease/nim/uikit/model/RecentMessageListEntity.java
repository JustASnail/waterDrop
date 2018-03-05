package com.netease.nim.uikit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/12.
 */

public class RecentMessageListEntity extends BaseResultEntity {

    /**
     * nextSearchStart : 2017-07-10 02:03:34
     * pageSize : 10
     * results : [{"createTime":"2017-07-10 02:03:34","createTimestamp":"1499623414000","data":{"billCompany":"","billTfn":"","billType":-1,"createTime":"2017-07-07 18:11:09","createTimestamp":"1499422269000","deliveryAddress":"啦啦啦","deliveryAddressId":50,"deliveryCity":"市辖区","deliveryDistrict":"东城区","deliveryMobile":"18901686661","deliveryName":"啦啦啦啦","deliveryProv":"北京市","goods":[{"dropId":14968253943581,"favourablePrice":228,"goodCovery":"http://cdn1.drops.xin/帝泊洱274×274.JPG","goodId":"cdaeb152fcb9174408d59eb9de51d5aa","goodName":"帝泊洱三合一即溶普洱茶珍25.2g/盒","id":14,"orderId":14994222696831,"quantity":1,"tipId":14974599425041,"unitPrice":228}],"idcardBack":"","idcardFront":"","needBill":0,"orderId":14994222696831,"payInfo":"h5","payNo":"","payTime":"","price":228,"status":90,"uid":14973304643721},"iosSchema":"","messageId":30,"note":"订单派送中","status":1,"targetId":14994222696831,"type":21},{"createTime":"2017-07-10 02:02:23","createTimestamp":"1499623343000","data":{"billCompany":"","billTfn":"","billType":-1,"createTime":"2017-07-07 18:11:09","createTimestamp":"1499422269000","deliveryAddress":"啦啦啦","deliveryAddressId":50,"deliveryCity":"市辖区","deliveryDistrict":"东城区","deliveryMobile":"18901686661","deliveryName":"啦啦啦啦","deliveryProv":"北京市","goods":[{"dropId":14968253943581,"favourablePrice":228,"goodCovery":"http://cdn1.drops.xin/帝泊洱274×274.JPG","goodId":"cdaeb152fcb9174408d59eb9de51d5aa","goodName":"帝泊洱三合一即溶普洱茶珍25.2g/盒","id":14,"orderId":14994222696831,"quantity":1,"tipId":14974599425041,"unitPrice":228}],"idcardBack":"","idcardFront":"","needBill":0,"orderId":14994222696831,"payInfo":"h5","payNo":"","payTime":"","price":228,"status":90,"uid":14973304643721},"iosSchema":"","messageId":29,"note":"订单生成","status":1,"targetId":14994222696831,"type":21},{"createTime":"2017-07-10 01:51:46","createTimestamp":"1499622706000","data":{"beans":100,"dropId":14992389245041,"earnType":"2","earnUid":14967432761081,"gmtCreated":1499265511000,"gmtModified":1499265511000,"goodId":"cdaeb152fcb9174408d59eb9de51d5aa","id":17,"status":1,"tipId":14991923345041,"uid":14973304643721},"fromUser":{"idCode":9370781,"neteaseAccid":"yuanshi_14973240537431","neteaseToken":"99911497af6358d5a677ef4b378ba146","nickName":"天啦噜？","photo":"http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png","uid":14973240537431,"userDesc":"你好😊"},"iosSchema":"YSWaterDrop://NaviPush/WDGoodBeanViewController","messageId":28,"note":"方烈在title水贴中购买了帝泊洱三合一即溶普洱茶珍25.2g/盒消费了20金豆","status":1,"targetId":17,"type":31},{"createTime":"2017-07-10 01:35:48","createTimestamp":"1499621748000","data":{"browserNum":42,"categoryId":1,"cover":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG","createTime":"2017-06-08","createTimeShow":"一个月前","createTimestamp":"1496869493000","createUid":14967432761081,"dropId":14968253943581,"likeNum":5,"photoNum":5,"showType":1,"status":1,"tipContent":"方烈content","tipId":14968694978591,"tipTitle":"方烈title"},"fromUser":{"idCode":9370781,"neteaseAccid":"yuanshi_14973240537431","neteaseToken":"99911497af6358d5a677ef4b378ba146","nickName":"天啦噜？","photo":"http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png","uid":14973240537431,"userDesc":"你好😊"},"iosSchema":"YSWaterDrop://NaviPush/WDPoolPostDetailViewController?tipId=14968694978591","messageId":27,"note":"水贴推荐","status":1,"targetId":14968694978591,"type":52},{"createTime":"2017-07-10 01:33:02","createTimestamp":"1499621582000","data":{"attentionNum":0,"categoryId":1,"createTime":"2017-07-05 15:15:52","createTimestamp":"1499238952000","createUid":14967746218421,"dropCode":"94256606","dropDesc":"转眼扑蝶的岁月都过去，只剩看山的岁月了。","dropId":14992389638051,"dropName":"水塘1","dropPhoto":"http://cdn001.waterdrop.xin/zhmqpxt@3x.jpg","groupUserNum":1,"likeNum":0,"neteaseRoomId":"","starFlag":1,"status":1},"fromUser":{"idCode":9370781,"neteaseAccid":"yuanshi_14973240537431","neteaseToken":"99911497af6358d5a677ef4b378ba146","nickName":"天啦噜？","photo":"http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png","uid":14973240537431,"userDesc":"你好😊"},"iosSchema":"YSWaterDrop://NaviPush/WDPoolViewController?dropId=14992389638051","messageId":26,"note":"水塘推荐","status":1,"targetId":14992389638051,"type":51},{"createTime":"2017-07-10 01:31:43","createTimestamp":"1499621503000","data":{"attentionNum":0,"categoryId":1,"createTime":"2017-07-05 15:15:52","createTimestamp":"1499238952000","createUid":14967746218421,"dropCode":"94256606","dropDesc":"转眼扑蝶的岁月都过去，只剩看山的岁月了。","dropId":14992389638051,"dropName":"水塘1","dropPhoto":"http://cdn001.waterdrop.xin/zhmqpxt@3x.jpg","groupUserNum":1,"likeNum":0,"neteaseRoomId":"","starFlag":1,"status":1},"fromUser":{"idCode":4923765,"neteaseAccid":"yuanshi_14981930033361","neteaseToken":"972257a3232825b5c83b84928975d671","nickName":"南木。","photo":"http://oogijmhwg.bkt.clouddn.com/4b9747017b693bd1a7b632f5160c4ff1","uid":14981930033361,"userDesc":""},"iosSchema":"","messageId":25,"note":"水电费雷克萨","status":0,"targetId":14992389638051,"type":12},{"createTime":"2017-07-10 01:27:00","createTimestamp":"1499621220000","fromUser":{"idCode":9370781,"neteaseAccid":"yuanshi_14973240537431","neteaseToken":"99911497af6358d5a677ef4b378ba146","nickName":"天啦噜？","photo":"http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png","uid":14973240537431,"userDesc":"你好😊"},"iosSchema":"","messageId":24,"note":"我是yuanshi_14973304643721，hello~","status":0,"targetId":-1,"type":11}]
     * totalSize : 7
     */

    private String nextSearchStart;
    private int pageSize;
    private int totalSize;
    private List<ResultsBean> results;

    public String getNextSearchStart() {
        return nextSearchStart;
    }

    public void setNextSearchStart(String nextSearchStart) {
        this.nextSearchStart = nextSearchStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable{
        /**
         * createTime : 2017-07-10 02:03:34
         * createTimestamp : 1499623414000
         * data : {"billCompany":"","billTfn":"","billType":-1,"createTime":"2017-07-07 18:11:09","createTimestamp":"1499422269000","deliveryAddress":"啦啦啦","deliveryAddressId":50,"deliveryCity":"市辖区","deliveryDistrict":"东城区","deliveryMobile":"18901686661","deliveryName":"啦啦啦啦","deliveryProv":"北京市","goods":[{"dropId":14968253943581,"favourablePrice":228,"goodCovery":"http://cdn1.drops.xin/帝泊洱274×274.JPG","goodId":"cdaeb152fcb9174408d59eb9de51d5aa","goodName":"帝泊洱三合一即溶普洱茶珍25.2g/盒","id":14,"orderId":14994222696831,"quantity":1,"tipId":14974599425041,"unitPrice":228}],"idcardBack":"","idcardFront":"","needBill":0,"orderId":14994222696831,"payInfo":"h5","payNo":"","payTime":"","price":228,"status":90,"uid":14973304643721}
         * iosSchema :
         * messageId : 30
         * note : 订单派送中
         * status : 1
         * targetId : 14994222696831
         * type : 21
         * fromUser : {"idCode":9370781,"neteaseAccid":"yuanshi_14973240537431","neteaseToken":"99911497af6358d5a677ef4b378ba146","nickName":"天啦噜？","photo":"http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png","uid":14973240537431,"userDesc":"你好😊"}
         */

        private String createTime;
        private String createTimestamp;
        private DataBean data;
        private String iosSchema;
        private long messageId;
        private String title;
        private String note;
        private int status;
        private long targetId;
        private int type;
        private FromUserBean fromUser;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getIosSchema() {
            return iosSchema;
        }

        public void setIosSchema(String iosSchema) {
            this.iosSchema = iosSchema;
        }

        public long getMessageId() {
            return messageId;
        }

        public void setMessageId(long messageId) {
            this.messageId = messageId;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getTargetId() {
            return targetId;
        }

        public void setTargetId(long targetId) {
            this.targetId = targetId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public FromUserBean getFromUser() {
            return fromUser;
        }

        public void setFromUser(FromUserBean fromUser) {
            this.fromUser = fromUser;
        }

        public static class DataBean implements Serializable{
            /**
             * billCompany :
             * billTfn :
             * billType : -1
             * createTime : 2017-07-07 18:11:09
             * createTimestamp : 1499422269000
             * deliveryAddress : 啦啦啦
             * deliveryAddressId : 50
             * deliveryCity : 市辖区
             * deliveryDistrict : 东城区
             * deliveryMobile : 18901686661
             * deliveryName : 啦啦啦啦
             * deliveryProv : 北京市
             * goods : [{"dropId":14968253943581,"favourablePrice":228,"goodCovery":"http://cdn1.drops.xin/帝泊洱274×274.JPG","goodId":"cdaeb152fcb9174408d59eb9de51d5aa","goodName":"帝泊洱三合一即溶普洱茶珍25.2g/盒","id":14,"orderId":14994222696831,"quantity":1,"tipId":14974599425041,"unitPrice":228}]
             * idcardBack :
             * idcardFront :
             * needBill : 0
             * orderId : 14994222696831
             * payInfo : h5
             * payNo :
             * payTime :
             * price : 228
             * status : 90
             * uid : 14973304643721
             */

            private String billCompany;
            private String billTfn;
            private int billType;
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
            private float price;
            private List<GoodsBean> goods;


            private int status;
            private float beans;
            private int browserNum;
            private int categoryId;
            private int attentionNum;
            private int likeNum;
            private int photoNum;
            private int showType;
            private String dropPhoto;
            private String groupUserNum;
            private String dropName;
            private String dropCode;
            private String dropDesc;
            private String earnType;
            private String goodId;
            private String tipContent;
            private String tipTitle;
            private String cover;
            private String createTime;
            private String createTimeShow;
            private String createTimestamp;
            private long dropId;
            private long createUid;
            private long earnUid;
            private long gmtCreated;
            private long gmtModified;
            private long id;
            private long tipId;
            private long uid;
            private String tipUrl;

            public String getTipUrl() {
                return tipUrl;
            }

            public void setTipUrl(String tipUrl) {
                this.tipUrl = tipUrl;
            }

            public float getBeans() {
                return beans;
            }

            public void setBeans(float beans) {
                this.beans = beans;
            }

            public int getBrowserNum() {
                return browserNum;
            }

            public void setBrowserNum(int browserNum) {
                this.browserNum = browserNum;
            }

            public String getCreateTimeShow() {
                return createTimeShow;
            }

            public void setCreateTimeShow(String createTimeShow) {
                this.createTimeShow = createTimeShow;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public int getAttentionNum() {
                return attentionNum;
            }

            public void setAttentionNum(int attentionNum) {
                this.attentionNum = attentionNum;
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

            public String getDropPhoto() {
                return dropPhoto;
            }

            public void setDropPhoto(String dropPhoto) {
                this.dropPhoto = dropPhoto;
            }

            public String getGroupUserNum() {
                return groupUserNum;
            }

            public void setGroupUserNum(String groupUserNum) {
                this.groupUserNum = groupUserNum;
            }

            public String getDropName() {
                return dropName;
            }

            public void setDropName(String dropName) {
                this.dropName = dropName;
            }

            public String getDropDesc() {
                return dropDesc;
            }

            public void setDropDesc(String dropDesc) {
                this.dropDesc = dropDesc;
            }

            public String getDropCode() {
                return dropCode;
            }

            public void setDropCode(String dropCode) {
                this.dropCode = dropCode;
            }

            public String getEarnType() {
                return earnType;
            }

            public void setEarnType(String earnType) {
                this.earnType = earnType;
            }

            public String getGoodId() {
                return goodId;
            }

            public void setGoodId(String goodId) {
                this.goodId = goodId;
            }

            public String getTipContent() {
                return tipContent;
            }

            public void setTipContent(String tipContent) {
                this.tipContent = tipContent;
            }

            public String getTipTitle() {
                return tipTitle;
            }

            public void setTipTitle(String tipTitle) {
                this.tipTitle = tipTitle;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public long getDropId() {
                return dropId;
            }

            public void setDropId(long dropId) {
                this.dropId = dropId;
            }

            public long getCreateUid() {
                return createUid;
            }

            public void setCreateUid(long createUid) {
                this.createUid = createUid;
            }

            public long getEarnUid() {
                return earnUid;
            }

            public void setEarnUid(long earnUid) {
                this.earnUid = earnUid;
            }

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

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getTipId() {
                return tipId;
            }

            public void setTipId(long tipId) {
                this.tipId = tipId;
            }

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

            public float getPrice() {
                return price;
            }

            public void setPrice(float price) {
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

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean implements Serializable{
                /**
                 * dropId : 14968253943581
                 * favourablePrice : 228
                 * goodCovery : http://cdn1.drops.xin/帝泊洱274×274.JPG
                 * goodId : cdaeb152fcb9174408d59eb9de51d5aa
                 * goodName : 帝泊洱三合一即溶普洱茶珍25.2g/盒
                 * id : 14
                 * orderId : 14994222696831
                 * quantity : 1
                 * tipId : 14974599425041
                 * unitPrice : 228
                 */

                private long dropId;
                private double favourablePrice;
                private String goodCovery;
                private String goodId;
                private String goodName;
                private int id;
                private long orderId;
                private int quantity;
                private long tipId;
                private int unitPrice;

                public long getDropId() {
                    return dropId;
                }

                public void setDropId(long dropId) {
                    this.dropId = dropId;
                }

                public double getFavourablePrice() {
                    return favourablePrice;
                }

                public void setFavourablePrice(double favourablePrice) {
                    this.favourablePrice = favourablePrice;
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

                public int getUnitPrice() {
                    return unitPrice;
                }

                public void setUnitPrice(int unitPrice) {
                    this.unitPrice = unitPrice;
                }
            }
        }

        public static class FromUserBean implements Serializable{
            /**
             * idCode : 9370781
             * neteaseAccid : yuanshi_14973240537431
             * neteaseToken : 99911497af6358d5a677ef4b378ba146
             * nickName : 天啦噜？
             * photo : http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png
             * uid : 14973240537431
             * userDesc : 你好😊
             */

            private int idCode;
            private String neteaseAccid;
            private String neteaseToken;
            private String nickName;
            private String photo;
            private long uid;
            private String userDesc;

            public int getIdCode() {
                return idCode;
            }

            public void setIdCode(int idCode) {
                this.idCode = idCode;
            }

            public String getNeteaseAccid() {
                return neteaseAccid;
            }

            public void setNeteaseAccid(String neteaseAccid) {
                this.neteaseAccid = neteaseAccid;
            }

            public String getNeteaseToken() {
                return neteaseToken;
            }

            public void setNeteaseToken(String neteaseToken) {
                this.neteaseToken = neteaseToken;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public long getUid() {
                return uid;
            }

            public void setUid(long uid) {
                this.uid = uid;
            }

            public String getUserDesc() {
                return userDesc;
            }

            public void setUserDesc(String userDesc) {
                this.userDesc = userDesc;
            }
        }
    }
}
