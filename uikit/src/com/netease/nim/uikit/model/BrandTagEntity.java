package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/9/26.
 */

public class BrandTagEntity {

    /**
     * results : [{"brandId":1,"brandName":"资生堂","brandPhoto":"https://ss2.bdstatic.com/8_V1bjqh_Q23odCf/pacific/upload_9906949_1470385125975.jpg?x=0&y=0&h=75&w=121&vh=93&vw=150&oh=75&ow=121","status":1},{"brandId":2,"brandName":"泸州老窖","brandPhoto":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1616972636,1713765777&fm=58","status":1}]
     * totalSize : 2
     */

    private int totalSize;
    private List<ResultsBean> results;

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
        /**
         * brandId : 1
         * brandName : 资生堂
         * brandPhoto : https://ss2.bdstatic.com/8_V1bjqh_Q23odCf/pacific/upload_9906949_1470385125975.jpg?x=0&y=0&h=75&w=121&vh=93&vw=150&oh=75&ow=121
         * status : 1
         */

        private int brandId;
        private String brandName;
        private String brandPhoto;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
