package com.drops.waterdrop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengxiaolei on 2017/5/23.
 */

public class HotPostBean implements Serializable {
    private List<Integer> imgList;

    private String title;

    private String desc;

    private int userHead;

    private String userName;

    private int likes;

    private boolean isLiked;

    public List<Integer> getImgList() {
        return imgList;
    }

    public void setImgList(List<Integer> imgList) {
        this.imgList = imgList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getUserHead() {
        return userHead;
    }

    public void setUserHead(int userHead) {
        this.userHead = userHead;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
