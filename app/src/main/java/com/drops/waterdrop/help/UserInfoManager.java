package com.drops.waterdrop.help;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.util.Singleton;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.UserCenterEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;

import rx.Observable;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/10/11 14:40
 */

public class UserInfoManager {

    public interface Callback{
        void onGet(UserCenterEntity entity);
        void onError(String msg);
    }

    public static UserInfoManager get(){
        return gDefault.get();
    }

    public UserCenterEntity getData(){
        return userCenterEntity;
    }

    public void autoGet(@NonNull Callback callback, boolean isForce){
        autoGet(callback, isForce, false);
    }

    public void autoGet(@NonNull Callback callback, boolean isForce, boolean isShowProgress){
        this.callback = callback;
        if (userCenterEntity != null && !isForce){
            callback.onGet(userCenterEntity);
        } else {
            request(isShowProgress);
        }
    }

    public void autoGet(@NonNull Callback callback){
        autoGet(callback, false);
    }

    private void request(boolean isShowProgress) {
        String lastBrowserTime = MyUserCache.getLastBrowserTime();
        if (TextUtils.isEmpty(lastBrowserTime)) {
            String nowDatetime = TimeUtil.getNowDatetime();
            MyUserCache.saveLastBrowserTime(nowDatetime);
            lastBrowserTime = nowDatetime;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.pre_browser_time, lastBrowserTime);
        Observable<BaseResponse<UserCenterEntity>> observable = HttpUtil.getInstance().sApi.getUserInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<UserCenterEntity>(WaterDropApp.getContext(), isShowProgress) {
            @Override
            protected void _onNext(UserCenterEntity entity) {
                userCenterEntity = entity;
                userCenterEntity.parseData();
                if (callback != null)
                    callback.onGet(entity);
            }

            @Override
            protected void _onError(String message) {
                if (callback != null)
                    callback.onError(message);
            }
        });
    }

    private UserCenterEntity userCenterEntity;
    private Callback callback;
    private UserInfoManager(){
        request(false);
    }

    private static final Singleton<UserInfoManager> gDefault = new Singleton<UserInfoManager>() {
        @Override
        protected UserInfoManager create() {
            return new UserInfoManager();
        }
    };
}
