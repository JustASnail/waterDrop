package com.netease.nim.uikit.model;

import android.view.View;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/05 22:59
 */

public abstract class CollectItemHolder {
    public PostListEntity.ResultsBean.CreatorBean bean;
    public CollectItemHolder(PostListEntity.ResultsBean.CreatorBean bean){
        this.bean = bean;
    }

    public abstract View getView();
}
