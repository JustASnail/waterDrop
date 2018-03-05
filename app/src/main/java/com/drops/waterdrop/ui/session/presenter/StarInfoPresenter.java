package com.drops.waterdrop.ui.session.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.session.view.IStarInfoView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.StarInfoEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/5/23.
 */

public class StarInfoPresenter extends BasePresenter<IStarInfoView> {
    public StarInfoPresenter(BaseActivity context) {
        super(context);
    }


    public void getData(long f_uid) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.f_uid, f_uid);
        Observable<BaseResponse<StarInfoEntity>> observable = HttpUtil.getInstance().sApi.getStarDetails(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<StarInfoEntity>(mContext) {
            @Override
            protected void _onNext(StarInfoEntity starInfoEntity) {
                getView().onGetDataSucceed(starInfoEntity);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                mContext.finish();
            }
        });
    }
}
