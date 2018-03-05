package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/7/24.
 */

public class AppInfoEntity {

    /**
     * nextSearchStart :
     * pageSize : 2
     * results : [{"paramKey":"DEBUG_MODE","paramRemark":"测试模式","paramValue":"0"},{"paramKey":"KEY_KEY","paramRemark":"CESHI","paramValue":"https:// 29380dcb05c58a5.jpeg"}]
     * totalSize : 2
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

    public static class ResultsBean {
        /**
         * paramKey : DEBUG_MODE
         * paramRemark : 测试模式
         * paramValue : 0
         */

        private String paramKey;
        private String paramRemark;
        private String paramValue;

        public String getParamKey() {
            return paramKey;
        }

        public void setParamKey(String paramKey) {
            this.paramKey = paramKey;
        }

        public String getParamRemark() {
            return paramRemark;
        }

        public void setParamRemark(String paramRemark) {
            this.paramRemark = paramRemark;
        }

        public String getParamValue() {
            return paramValue;
        }

        public void setParamValue(String paramValue) {
            this.paramValue = paramValue;
        }
    }
}
