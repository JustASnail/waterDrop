package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/9/15.
 */

public class BrandListEntity {

    /**
     * banners : [{"goodId":"goodId","link":"http://www.sina.com.cn","photos":["http://www.sina.com.cn"],"type":1}]
     * brandGoods : [{"brandDesc":"世界第一值得拥有的品牌","brandName":"天士力","brandPhoto":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1018867192,1791011491&fm=58&bpow=1233&bpoh=1176&u_exp_0=831888088,1474244358&fm_exp_0=86","goodSize":100,"goods":[{"actPic":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1018867192,1791011491&fm=58&bpow=1233&bpoh=1176&u_exp_0=831888088,1474244358&fm_exp_0=86","actPrice":23,"actTitle":"测试商品","count":100,"crossBorder":0,"from":"raw","goodCode":"100","goodId":"122","isWj":0,"settlePrice":23,"status":1}]}]
     * nextSearchStart : 1
     * pageSize : 10
     * totalSize : 100
     */

    private String nextSearchStart;
    private int pageSize;
    private int totalSize;
    private List<BannersBean> banners;
    private List<BrandGoodsBean> brandGoods;

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

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public List<BrandGoodsBean> getBrandGoods() {
        return brandGoods;
    }

    public void setBrandGoods(List<BrandGoodsBean> brandGoods) {
        this.brandGoods = brandGoods;
    }

    public static class BannersBean {
        /**
         * goodId : goodId
         * link : http://www.sina.com.cn
         * photos : ["http://www.sina.com.cn"]
         * type : 1
         */

        private String goodId;
        private String link;
        private int type;
        private List<String> photos;

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<String> getPhotos() {
            return photos;
        }

        public void setPhotos(List<String> photos) {
            this.photos = photos;
        }
    }

    public static class BrandGoodsBean {
        /**
         * brandDesc : 世界第一值得拥有的品牌
         * brandName : 天士力
         * brandPhoto : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1018867192,1791011491&fm=58&bpow=1233&bpoh=1176&u_exp_0=831888088,1474244358&fm_exp_0=86
         * goodSize : 100
         * goods : [{"actPic":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1018867192,1791011491&fm=58&bpow=1233&bpoh=1176&u_exp_0=831888088,1474244358&fm_exp_0=86","actPrice":23,"actTitle":"测试商品","count":100,"crossBorder":0,"from":"raw","goodCode":"100","goodId":"122","isWj":0,"settlePrice":23,"status":1}]
         */

        private String brandDesc;
        private String brandName;
        private String brandPhoto;
        private int goodSize;
        private List<GoodsBean> goods;

        public String getBrandDesc() {
            return brandDesc;
        }

        public void setBrandDesc(String brandDesc) {
            this.brandDesc = brandDesc;
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

        public int getGoodSize() {
            return goodSize;
        }

        public void setGoodSize(int goodSize) {
            this.goodSize = goodSize;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * actPic : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1018867192,1791011491&fm=58&bpow=1233&bpoh=1176&u_exp_0=831888088,1474244358&fm_exp_0=86
             * actPrice : 23
             * actTitle : 测试商品
             * count : 100
             * crossBorder : 0
             * from : raw
             * goodCode : 100
             * goodId : 122
             * isWj : 0
             * settlePrice : 23
             * status : 1
             */

            private String actPic;
            private double actPrice;
            private String actTitle;
            private int count;
            private int crossBorder;
            private String from;
            private String goodCode;
            private String goodId;
            private int isWj;
            private double settlePrice;
            private int status;

            public String getActPic() {
                return actPic;
            }

            public void setActPic(String actPic) {
                this.actPic = actPic;
            }

            public double getActPrice() {
                return actPrice;
            }

            public void setActPrice(double actPrice) {
                this.actPrice = actPrice;
            }

            public String getActTitle() {
                return actTitle;
            }

            public void setActTitle(String actTitle) {
                this.actTitle = actTitle;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
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

            public int getIsWj() {
                return isWj;
            }

            public void setIsWj(int isWj) {
                this.isWj = isWj;
            }

            public double getSettlePrice() {
                return settlePrice;
            }

            public void setSettlePrice(double settlePrice) {
                this.settlePrice = settlePrice;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}

