package com.drops.waterdrop.ui.mine.presenter;

import android.content.Intent;

import com.drops.waterdrop.help.SessionHelper;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.IUserProfileView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.FriendDetailEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/6/29.
 */

public class UserProfilePresenter extends BasePresenter<IUserProfileView> {

    private long mUserId;

    public UserProfilePresenter(BaseActivity context) {
        super(context);
    }

    public void parseIntent(Intent intent) {
        mUserId = intent.getLongExtra(Constants.EXTRA_USER_ID, 0);
    }

    public void getData() {
        if (mUserId == 0) return;

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.friend_uid, mUserId);
        Observable<BaseResponse<FriendDetailEntity>> observable = HttpUtil.getInstance().sApi.getFriendDetail(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<FriendDetailEntity>(mContext) {
            @Override
            protected void _onNext(FriendDetailEntity entity) {
                if (entity.getUid() > 0) {
                    getView().onGetDataSucceed(entity);
                } else {
                    ToastUtil.showShort("该用户不存在");
                    mContext.finish();
                }

            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                mContext.finish();

            }
        });
    }

    public void goMessage(String neteaseAccid) {
        SessionHelper.startP2PSession(mContext, neteaseAccid);
    }

    public void addFriend(long uid) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.f_uid, uid);
        map.put(RequestParams.note, "");
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.addFriend(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, "正在添加...") {
            @Override
            protected void _onNext(Object o) {
                getView().onAddFriendSucceed();
                ToastUtil.showShort("发送成功， 等待验证");
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}
