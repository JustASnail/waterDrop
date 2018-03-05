package com.drops.waterdrop.ui.session.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.session.view.IVerifyView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

import static com.drops.waterdrop.ui.session.activity.VerifyActivity.RESULT_CODE_TEAM;

/**
 * Created by dengxiaolei on 2017/5/17.
 */

public class VerifyPresenter extends BasePresenter<IVerifyView> {
    public VerifyPresenter(BaseActivity context) {
        super(context);
    }

    public void doAddFriend(long f_uid, final String msg) {
        if (!NetworkUtil.isNetAvailable(mContext)) {
            ToastUtil.showShort("网络连接失败，请检查你的网络设置");
            return;
        }
        if (f_uid == MyUserCache.getUserUid()) {
            ToastUtil.showShort("不能添加自己为好友");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.f_uid, f_uid);
        map.put(RequestParams.note, msg);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.addFriend(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, "正在添加...") {
            @Override
            protected void _onNext(Object o) {
                ToastUtil.showShort("发送成功， 等待验证");
                getView().onAddFriendSucceed();
                mContext.finish();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    public void joinPool(long dropId, String note) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, dropId);
        map.put(RequestParams.note, note);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.joinPool(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, "正在申请...") {
            @Override
            protected void _onNext(Object o) {
                ToastUtil.showCustomToast("已发送", "已发送加群请求，等待塘主同意");
                mContext.setResult(RESULT_CODE_TEAM);
                mContext.finish();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}
