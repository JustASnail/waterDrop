package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/6/27.
 */

public class CollectionSTEntry extends BaseResultEntity {

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

    public static class ResultsBean {


        private int browserNum;
        private int categoryId;
        private int collectShowStatus;
        private int collectStatus;
        private String cover;
        private String createTime;
        private String createTimeShow;
        private String createTimestamp;
        private long createUid;
        private CreatorBean creator;
        private long dropId;
        private DropInfoBean dropInfo;
        private int likeNum;
        private int likeStatus;
        private int photoNum;
        private int showType;
        private int status;
        private String tipContent;
        private long tipId;
        private String tipTitle;
        private String tipUrl;
        private List<BrandsBean> brands;
        private List<?> comments;
        private List<GoodsBean> goods;
        private List<PhotosBean> photos;

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

        public int getCollectShowStatus() {
            return collectShowStatus;
        }

        public void setCollectShowStatus(int collectShowStatus) {
            this.collectShowStatus = collectShowStatus;
        }

        public int getCollectStatus() {
            return collectStatus;
        }

        public void setCollectStatus(int collectStatus) {
            this.collectStatus = collectStatus;
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

        public long getCreateUid() {
            return createUid;
        }

        public void setCreateUid(long createUid) {
            this.createUid = createUid;
        }

        public CreatorBean getCreator() {
            return creator;
        }

        public void setCreator(CreatorBean creator) {
            this.creator = creator;
        }

        public long getDropId() {
            return dropId;
        }

        public void setDropId(long dropId) {
            this.dropId = dropId;
        }

        public DropInfoBean getDropInfo() {
            return dropInfo;
        }

        public void setDropInfo(DropInfoBean dropInfo) {
            this.dropInfo = dropInfo;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getLikeStatus() {
            return likeStatus;
        }

        public void setLikeStatus(int likeStatus) {
            this.likeStatus = likeStatus;
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

        public List<BrandsBean> getBrands() {
            return brands;
        }

        public void setBrands(List<BrandsBean> brands) {
            this.brands = brands;
        }

        public List<?> getComments() {
            return comments;
        }

        public void setComments(List<?> comments) {
            this.comments = comments;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
        }

        public static class CreatorBean {
            /**
             * idCode : 6674957
             * neteaseAccid : yuanshi_14967746218421
             * neteaseToken : 71af25bbefd1bace844673a633fffdba
             * nickName : 方烈
             * photo : http://v1.qzone.cc/avatar/201408/20/11/43/53f419541c31a667.jpg
             * uid : 14967746218421
             * userDesc : 描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述
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

        public static class DropInfoBean {
            /**
             * attentionNum : 3
             * attentionShowStatus : 1
             * attentionStatus : 0
             * categoryId : 1
             * createTime : 2017-07-05 15:15:13
             * createTimeShow : 12天前
             * createTimestamp : 1499238913000
             * createUid : 14967746218421
             * dropCode : 98515106
             * dropDesc : 爱藏在隐蔽的夹缝里，你用心找就会发现。
             * dropId : 14992389245041
             * dropName : 水塘1
             * dropPhoto : http://cdn001.waterdrop.xin/gzmt@3x.jpg
             * groupUserNum : 1
             * headImg : http://www.gurubear.com.cn/editor/uploadfile/20130826151723.jpg
             * likeNum : 0
             * neteaseRoomId :
             * starFlag : 1
             * status : 1
             * tipNum : 0
             */

            private int attentionNum;
            private int attentionShowStatus;
            private int attentionStatus;
            private int categoryId;
            private String createTime;
            private String createTimeShow;
            private String createTimestamp;
            private long createUid;
            private String dropCode;
            private String dropDesc;
            private long dropId;
            private String dropName;
            private String dropPhoto;
            private int groupUserNum;
            private String headImg;
            private int likeNum;
            private String neteaseRoomId;
            private int starFlag;
            private int status;
            private int tipNum;

            public int getAttentionNum() {
                return attentionNum;
            }

            public void setAttentionNum(int attentionNum) {
                this.attentionNum = attentionNum;
            }

            public int getAttentionShowStatus() {
                return attentionShowStatus;
            }

            public void setAttentionShowStatus(int attentionShowStatus) {
                this.attentionShowStatus = attentionShowStatus;
            }

            public int getAttentionStatus() {
                return attentionStatus;
            }

            public void setAttentionStatus(int attentionStatus) {
                this.attentionStatus = attentionStatus;
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

            public long getCreateUid() {
                return createUid;
            }

            public void setCreateUid(long createUid) {
                this.createUid = createUid;
            }

            public String getDropCode() {
                return dropCode;
            }

            public void setDropCode(String dropCode) {
                this.dropCode = dropCode;
            }

            public String getDropDesc() {
                return dropDesc;
            }

            public void setDropDesc(String dropDesc) {
                this.dropDesc = dropDesc;
            }

            public long getDropId() {
                return dropId;
            }

            public void setDropId(long dropId) {
                this.dropId = dropId;
            }

            public String getDropName() {
                return dropName;
            }

            public void setDropName(String dropName) {
                this.dropName = dropName;
            }

            public String getDropPhoto() {
                return dropPhoto;
            }

            public void setDropPhoto(String dropPhoto) {
                this.dropPhoto = dropPhoto;
            }

            public int getGroupUserNum() {
                return groupUserNum;
            }

            public void setGroupUserNum(int groupUserNum) {
                this.groupUserNum = groupUserNum;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public int getLikeNum() {
                return likeNum;
            }

            public void setLikeNum(int likeNum) {
                this.likeNum = likeNum;
            }

            public String getNeteaseRoomId() {
                return neteaseRoomId;
            }

            public void setNeteaseRoomId(String neteaseRoomId) {
                this.neteaseRoomId = neteaseRoomId;
            }

            public int getStarFlag() {
                return starFlag;
            }

            public void setStarFlag(int starFlag) {
                this.starFlag = starFlag;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getTipNum() {
                return tipNum;
            }

            public void setTipNum(int tipNum) {
                this.tipNum = tipNum;
            }
        }

        public static class BrandsBean {
            /**
             * brandId : 1
             * brandName : 资生堂
             * brandPhoto : https://ss2.bdstatic.com/8_V1bjqh_Q23odCf/pacific/upload_9906949_1470385125975.jpg?x=0&y=0&h=75&w=121&vh=93&vw=150&oh=75&ow=121
             * gmtCreated : 1497069170000
             * gmtModified : 1497069171000
             * status : 1
             */

            private int brandId;
            private String brandName;
            private String brandPhoto;
            private long gmtCreated;
            private long gmtModified;
            private int status;

            public int getBrandId() {
                return brandId;
            }

            public void setBrandId(int brandId) {
                this.brandId = brandId;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getBrandPhoto() {
                return brandPhoto;
            }

            public void setBrandPhoto(String brandPhoto) {
                this.brandPhoto = brandPhoto;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class GoodsBean {

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
            private int type01;
            private int type02;
            private int type03;
            private int type04;
            private String updatetime;
            private String uuid;
            private String video;
            private String wjSku;
            private List<String> actPicDetail;
            private List<MoneyDetailBean> moneyDetail;
            private List<ServiceBean> service;
            private List<String> servicecode;
            private List<VideoDetailBean> videoDetail;

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

            public static class MoneyDetailBean {
                /**
                 * amount :
                 * desc : 即溶古树普洱茶珍0.5g*14+即溶薏仁普洱茶珍0.8g*14+即溶荷叶冬瓜普洱茶珍0.5g*14
                 * value : 228
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
                 * title : 7天无理由退货
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

            public static class VideoDetailBean {
                /**
                 * img : http://oosuclqg5.bkt.clouddn.com/diboer.jpg
                 * url : http://oosuclqg5.bkt.clouddn.com/帝泊洱-0423-产品科技篇.mp4
                 */

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
        }

        public static class PhotosBean {
            /**
             * createUid : 14967746218421
             * dropId : 14992389245041
             * photo : http://7xp761.com1.z0.glb.clouddn.com/send-190.png
             * photoId : 14992419494211
             * status : 1
             * tipId : 14992419494181
             */

            private long createUid;
            private long dropId;
            private String photo;
            private long photoId;
            private int status;
            private long tipId;

            public long getCreateUid() {
                return createUid;
            }

            public void setCreateUid(long createUid) {
                this.createUid = createUid;
            }

            public long getDropId() {
                return dropId;
            }

            public void setDropId(long dropId) {
                this.dropId = dropId;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public long getPhotoId() {
                return photoId;
            }

            public void setPhotoId(long photoId) {
                this.photoId = photoId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getTipId() {
                return tipId;
            }

            public void setTipId(long tipId) {
                this.tipId = tipId;
            }
        }
    }
}