package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/10.
 */

public class InterestEntity extends BaseResultEntity {

    /**
     * pageSize : 3
     * results : [{"categoryId":13,"categoryName":"欧美","isLeaf":1,"leafs":[],"parentId":-1},{"categoryId":14,"categoryName":"大陆","isLeaf":1,"leafs":[],"parentId":-1},{"categoryId":15,"categoryName":"港台","isLeaf":1,"leafs":[],"parentId":-1}]
     * totalSize : 3
     */

    private int pageSize;
    private int totalSize;
    private List<ResultsBean> results;

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
         * categoryId : 13
         * categoryName : 欧美
         * isLeaf : 1
         * leafs : []
         * parentId : -1
         */

        private int categoryId;
        private String categoryName;
        private int isLeaf;
        private int parentId;
        private List<?> leafs;

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getIsLeaf() {
            return isLeaf;
        }

        public void setIsLeaf(int isLeaf) {
            this.isLeaf = isLeaf;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public List<?> getLeafs() {
            return leafs;
        }

        public void setLeafs(List<?> leafs) {
            this.leafs = leafs;
        }
    }
}
