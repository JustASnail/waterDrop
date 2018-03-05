package com.drops.waterdrop.model;

import java.io.Serializable;

/**
 * Created by shuyu on 2016/11/11.
 */

public class VideoModel implements Serializable{
    private String videoUrl;
    private String imgUrl;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
