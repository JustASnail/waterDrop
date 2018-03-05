package com.drops.waterdrop.ui.mine.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.SelectAddressForSendVrView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.BaseResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/10/12 14:50
 */

public class SelectAddressForSendVrPresenter extends BasePresenter<SelectAddressForSendVrView> {
    public SelectAddressForSendVrPresenter(BaseActivity context) {
        super(context);
    }

    public void getAddressList() {
        Observable<BaseResponse<AddressEntity>> observable = HttpUtil.getInstance().sApi.getAddress(RequestBodyUtils.build(null));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity>(mContext, true) {
            @Override
            protected void _onNext(AddressEntity dataBean) {
                List<AddressEntity.ResultsBean> results = dataBean.getResults();
                if (results != null && results.size() > 0) {
                    getView().onGetAddress(results);
                } else {
                    getView().onNoAddress();
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                getView().onNoAddress();

            }
        });
    }

    public void sendAddressForMemberActiveGift(String activeCode, String addressId){
        Map<String, Object> map = new HashMap<>();
        map.put("active_code", activeCode);
        map.put("address_id", addressId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.sendAddressForMemberActiveGift(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, true) {
            @Override
            protected void _onNext(Object s) {
                getView().onSendSucc();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showLong(message);
            }
        });
    }
}
