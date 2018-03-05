package com.drops.waterdrop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengxiaolei on 2017/5/23.
 */

public class CommentBean implements Serializable {
    private String headUrl;

    private String name;

    private String content;

    private String date;

    private List<String> ImgUrls;

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImgUrls() {
        return ImgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        ImgUrls = imgUrls;
    }
}
