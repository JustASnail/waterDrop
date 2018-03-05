package com.drops.waterdrop.ui.store.view;

import com.netease.nim.uikit.model.BrandListEntity;

import java.util.List;

/**
 * Created by Mr.Smile on 2017/9/14.
 */

public interface IBrandAllView {
    void setRefresh(boolean b);

    void onGetBanner(List<BrandListEntity.BannersBean> banners);

    void setRefreshEnable(boolean b);
}
