package com.drops.waterdrop.ui.store.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.store.adapter.BrandListAdapter;
import com.drops.waterdrop.ui.store.view.IBrandAllView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.BrandListEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/9/14.
 */

public class BrandAllPresenter extends BasePresenter<IBrandAllView>{

    private Map<String, Object> map;
    private String mNextSearchStart;
    private int mRealTotalSize;
    private BrandListAdapter mLoadMoreAdapter;

    public BrandAllPresenter(BaseActivity context) {
        super(context);
    }

    public void getData(final boolean isRefresh, final boolean isLoadmore) {
        if (isLoadmore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
            map.put(RequestParams.country_code, "86");
        }

        Observable<BaseResponse<BrandListEntity>> observable = HttpUtil.getInstance().sApi.getAllBrandData(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<BrandListEntity>(mContext, !isLoadmore && !isRefresh) {

            @Override
            protected void _onNext(BrandListEntity brandListEntity) {
                List<BrandListEntity.BannersBean> banners = brandListEntity.getBanners();
                if (banners != null && banners.size() > 0) {
                    getView().onGetBanner(banners);
                }
                List<BrandListEntity.BrandGoodsBean> brandGoods = brandListEntity.getBrandGoods();
                if (brandGoods != null && brandGoods.size() > 0) {
                    mNextSearchStart = brandListEntity.getNextSearchStart();
                    if (isLoadmore) {
                        mRealTotalSize += brandGoods.size();
                        mLoadMoreAdapter.addData(brandGoods);
                        mLoadMoreAdapter.loadMoreComplete();
                    } else {
                        mRealTotalSize = brandGoods.size();
                        mLoadMoreAdapter.setNewData(brandGoods);
                    }

                    if (mRealTotalSize >= brandListEntity.getTotalSize()) {
                        mLoadMoreAdapter.loadMoreEnd();
                        getView().setRefreshEnable(true);
                    }
                }

                if (isLoadmore) {
                    getView().setRefreshEnable(true);
                }

                if (isRefresh) {
                    getView().setRefresh(false);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                if (isLoadmore) {
                    mLoadMoreAdapter.loadMoreFail();
                } else if (isRefresh){
                    getView().setRefresh(false);
                }
            }
        });
    }

    public void setLoadMoreAdapter(BrandListAdapter mAdapter) {
        mLoadMoreAdapter = mAdapter;
    }

}
