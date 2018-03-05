package com.drops.waterdrop.ui.other.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.other.view.IIronFansGroupView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.FansGroupEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/17.
 */

public class IIronFansGroupPresenter extends BasePresenter<IIronFansGroupView> {

    public IIronFansGroupPresenter(BaseActivity context) {
        super(context);
    }

    public void getRoomInfo(String roomId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.room_id, roomId);
        Observable<BaseResponse<FansGroupEntity>> observable = HttpUtil.getInstance().sApi.getRoomInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<FansGroupEntity>(mContext) {
            @Override
            protected void _onNext(FansGroupEntity fansGroupEntity) {
                getView().onGetRoomInfoSuccess(fansGroupEntity);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    public void applyJionRoom(Long dropId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, String.valueOf(dropId));
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.applyJoinRoom(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object o) {
                getView().onApplySuccess();
                ToastUtil.showShort("申请成功");
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}
