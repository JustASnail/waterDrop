package com.netease.nim.uikit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/2.
 */

public class PoolCategoryBean implements Serializable {
    private String name;

    private List<String> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
