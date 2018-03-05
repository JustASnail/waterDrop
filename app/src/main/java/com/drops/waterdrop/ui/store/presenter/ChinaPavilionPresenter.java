package com.drops.waterdrop.ui.store.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.store.view.IChinaPavilionView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.BrandTagEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/9/14.
 */

public class ChinaPavilionPresenter extends BasePresenter<IChinaPavilionView>{
    public ChinaPavilionPresenter(BaseActivity context) {
        super(context);
    }

    public void getBrandList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.country_code, "86");
        Observable<BaseResponse<BrandTagEntity>> observable = HttpUtil.getInstance().sApi.getBrandList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<BrandTagEntity>(mContext) {
            @Override
            protected void _onNext(BrandTagEntity brandTagEntity) {
                List<BrandTagEntity.ResultsBean> results = brandTagEntity.getResults();
                getView().onGetBrandTagList(results);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}
