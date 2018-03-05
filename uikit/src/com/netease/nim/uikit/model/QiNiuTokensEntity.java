package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/14.
 */

public class QiNiuTokensEntity extends BaseResultEntity {

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
         * attachName : 59210c6c040452e7e.png
         * token : tBp6z80=
         */

        private String attachName;
        private String token;
        private String domain;

        public String getDomain(){
            return domain;
        }

        public String getAttachName() {
            return attachName;
        }

        public void setAttachName(String attachName) {
            this.attachName = attachName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
