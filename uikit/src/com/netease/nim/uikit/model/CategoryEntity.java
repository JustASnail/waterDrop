package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/6.
 */

public class CategoryEntity extends BaseResultEntity {

    /**
     * pageSize : 2
     * results : [{"categoryId":3,"categoryName":"欧美","isLeaf":0,"leafs":[{"categoryId":1,"categoryName":"数码","isLeaf":1,"leafs":[],"parentId":3}],"parentId":-1}]
     * totalSize : 2
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
         * categoryId : 3
         * categoryName : 欧美
         * isLeaf : 0
         * leafs : [{"categoryId":1,"categoryName":"数码","isLeaf":1,"leafs":[],"parentId":3}]
         * parentId : -1
         */

        private int categoryId;
        private String categoryName;
        private int isLeaf;
        private int parentId;
        private List<LeafsBean> leafs;

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

        public List<LeafsBean> getLeafs() {
            return leafs;
        }

        public void setLeafs(List<LeafsBean> leafs) {
            this.leafs = leafs;
        }

        public static class LeafsBean {
            /**
             * categoryId : 1
             * categoryName : 数码
             * isLeaf : 1
             * leafs : []
             * parentId : 3
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
}
