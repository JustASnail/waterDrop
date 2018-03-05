package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/9/26.
 */

public class StoreHomePageEntity {

    /**
     * banners : [{"goodId":"xadfas","photos":["http://www.sina.com.cn"],"title":"title2","type":1}]
     * nextSearchStart : 1
     * pageSize : 10
     * recommends : [{"goodId":"dfasfds","photos":["http://www.sina.com.cn"],"title":"ds","type":1}]
     * totalSize : 1
     */

    private String nextSearchStart;
    private int pageSize;
    private int totalSize;
    private List<BannersBean> banners;
    private List<RecommendsBean> recommends;

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

    public List<RecommendsBean> getRecommends() {
        return recommends;
    }

    public void setRecommends(List<RecommendsBean> recommends) {
        this.recommends = recommends;
    }

    public static class BannersBean {
        /**
         * goodId : xadfas
         * photos : ["http://www.sina.com.cn"]
         * title : title2
         * type : 1
         */

        private String goodId;
        private String title;
        private String link;
        private int type;
        private List<String> photos;

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
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

        public List<String> getPhotos() {
            return photos;
        }

        public void setPhotos(List<String> photos) {
            this.photos = photos;
        }

        public String getLink() {
            return link;
        }
    }

    public static class RecommendsBean {
        /**
         * goodId : dfasfds
         * photos : ["http://www.sina.com.cn"]
         * title : ds
         * type : 1
         */

        private String goodId;
        private String title;
        private int type;
        private List<String> photos;
        private String link;

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
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

        public List<String> getPhotos() {
            return photos;
        }

        public void setPhotos(List<String> photos) {
            this.photos = photos;
        }

        public String getLink() {
            return link;
        }
    }
}
