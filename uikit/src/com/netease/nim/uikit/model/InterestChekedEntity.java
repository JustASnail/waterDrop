package com.netease.nim.uikit.model;

import java.io.Serializable;

/**
 * Created by dengxiaolei on 2017/7/10.
 */

public class InterestChekedEntity implements Serializable {

    private boolean isSelected;

    private String content;

    private int categoryId;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
