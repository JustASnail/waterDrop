package com.netease.nim.uikit.model;

import android.text.TextUtils;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Created by dengxiaolei on 2017/5/22.
 */

public class ContactEntity implements IndexableEntity {

    private String accid;//im账号

    private String name;

    private String avatar;
    private int sex;

    private long uid;

    private String token;

    private boolean isAdded;


    @Override
    public String getFieldIndexBy() {
        if (TextUtils.isEmpty(name)) {
            return accid;
        } else {
            return name;
        }
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        if (TextUtils.isEmpty(name)) {
            this.accid = indexField;
        } else {
            this.name = indexField;

        }
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {

    }

    public String getAccId() {
        return accid;
    }

    public void setAccId(String accid) {
        this.accid = accid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }



    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
}
