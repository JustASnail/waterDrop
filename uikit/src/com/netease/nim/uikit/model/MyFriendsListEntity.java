package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/14.
 */

public class MyFriendsListEntity extends BaseResultEntity {

    /**
     * friends : [{"fNickName":"方烈","fPhoto":"http:///authenticPhoto65FM1.jpg","fUid":14967746218421,"note":"","relationStatus":0}]
     * searchTime : 2017-06-07 00:00:00
     * totalSize : 1
     */

    private String nextSearchStart;
    private int pageSize;
    private List<MyFriendEntity> results;

    public String getSearchTime() {
        return nextSearchStart;
    }

    public void setSearchTime(String searchTime) {
        this.nextSearchStart = searchTime;
    }

    public int getTotalSize() {
        return pageSize;
    }

    public void setTotalSize(int totalSize) {
        this.pageSize = totalSize;
    }

    public List<MyFriendEntity> getFriends() {
        return results;
    }

    public void setFriends(List<MyFriendEntity> friends) {
        this.results = friends;
    }

}
