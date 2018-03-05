package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/10/11.
 */

public class VipAreaEntity {

    /**
     * banner : {"bannerPhoto":"http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg","desc":"每购买以下一件礼包即可免费获得358元的水滴VR蓝光3D眼镜","title":"买就送蓝光高清VR眼镜"}
     * nextSearchStart : 2
     * pageSize : 10
     * results : [{"actPic":"http://oogijmhwg.bkt.clouddn.com/%E5%86%85%E6%B5%8B%E5%9B%BE2.JPG","actPrice":0,"actTitle":"帝泊洱即溶普洱茶珍（三合一装）","count":84,"crossBorder":0,"from":"raw","goodCode":"6aeec239e01321a642f8a54ac3cee9cf","goodId":"201710101346208764492655","isWj":0,"photoDetails":[],"settlePrice":0,"status":0},{"actPic":"http://oqcff239w.bkt.clouddn.com/20170821172003","actPrice":0.01,"actTitle":"嗲嗲死嗲嗲斯达康圣诞节啊机会收到啦好老师的哈里斯的哈","count":1000,"crossBorder":0,"from":"raw","goodId":"15033072045763","isWj":0,"photoDetails":[],"settlePrice":0.01,"status":0},{"actPic":"http://oqcff239w.bkt.clouddn.com/20170816151011","actPrice":12,"actTitle":"JJ","crossBorder":0,"from":"raw","goodId":"15028674118508","isWj":0,"photoDetails":[],"settlePrice":12,"status":0},{"actPic":"http://oqcff239w.bkt.clouddn.com/20170818162347","actPrice":78,"actTitle":"贵猪生态野猪腊肉4口味休闲食品60g*4袋","count":13,"crossBorder":0,"from":"raw","goodId":"15027685841963","isWj":0,"photoDetails":[],"settlePrice":78,"status":1},{"actPic":"http://file.wddcn.com/wddwechatshop/picture/goods/2017/10/44d9cd9e4b8340a3bdf3062c3a68d36b5.png_300x300.png","actPrice":230,"actTitle":"秀碧除疤膏 深度除疤 亮白肌肤 20g*盒","count":332,"crossBorder":0,"from":"wujie","goodCode":"FPQMER0002","goodId":"201710101502259512395686","isWj":1,"photoDetails":[],"settlePrice":230,"status":0}]
     * totalSize : 5
     */

    private BannerBean banner;
    private String nextSearchStart;
    private int pageSize;
    private int totalSize;
    private List<ResultsBean> results;

    public BannerBean getBanner() {
        return banner;
    }

    public void setBanner(BannerBean banner) {
        this.banner = banner;
    }

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

    public static class BannerBean {
        /**
         * bannerPhoto : http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg
         * desc : 每购买以下一件礼包即可免费获得358元的水滴VR蓝光3D眼镜
         * title : 买就送蓝光高清VR眼镜
         */

        private String bannerPhoto;
        private String desc;
        private String title;

        public String getBannerPhoto() {
            return bannerPhoto;
        }

        public void setBannerPhoto(String bannerPhoto) {
            this.bannerPhoto = bannerPhoto;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ResultsBean {
        /**
         * actPic : http://oogijmhwg.bkt.clouddn.com/%E5%86%85%E6%B5%8B%E5%9B%BE2.JPG
         * actPrice : 0
         * actTitle : 帝泊洱即溶普洱茶珍（三合一装）
         * count : 84
         * crossBorder : 0
         * from : raw
         * goodCode : 6aeec239e01321a642f8a54ac3cee9cf
         * goodId : 201710101346208764492655
         * isWj : 0
         * photoDetails : []
         * settlePrice : 0
         * status : 0
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
        private List<?> photoDetails;

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

        public List<?> getPhotoDetails() {
            return photoDetails;
        }

        public void setPhotoDetails(List<?> photoDetails) {
            this.photoDetails = photoDetails;
        }
    }
}

