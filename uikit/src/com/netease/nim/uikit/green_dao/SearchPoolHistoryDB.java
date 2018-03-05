package com.netease.nim.uikit.green_dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dengxiaolei on 2017/7/5.
 */

@Entity
public class SearchPoolHistoryDB {
    @Id(autoincrement = true)
    private long id;


    private String name;


    @Generated(hash = 429180208)
    public SearchPoolHistoryDB(long id, String name) {
        this.id = id;
        this.name = name;
    }


    @Generated(hash = 1702084047)
    public SearchPoolHistoryDB() {
    }


    public long getId() {
        return this.id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }

}
