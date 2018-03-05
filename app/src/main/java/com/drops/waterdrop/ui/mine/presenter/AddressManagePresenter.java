package com.drops.waterdrop.ui.mine.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.IAddressManageView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/6/2.
 */

public class AddressManagePresenter extends BasePresenter<IAddressManageView> {

    public AddressManagePresenter(BaseActivity context) {
        super(context);
    }

    public void getAddressData() {
        Map<String, Object> map = new HashMap<>();
        Observable<BaseResponse<AddressEntity>> observable = HttpUtil.getInstance().sApi.getAddress(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity>(mContext) {
            @Override
            protected void _onNext(AddressEntity dataBean) {
                getView().onGetAddressSucceed(dataBean.getResults());
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}
