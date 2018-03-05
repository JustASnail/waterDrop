package com.drops.waterdrop.help;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.drops.waterdrop.util.Singleton;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.QiNiuUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.QiNiuTokensEntity;
import com.netease.nim.uikit.model.UpdateUserInfoEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 *
 * 缓存微信的头像url到七牛,避免微信头像url失效
 *
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/20 18:43
 */

public class WxAvatarUrlHelper {

    private static final String WX_AVATAR_HOST_URL = "wx.qlogo.cn";

    public static WxAvatarUrlHelper get(){
        return gDefault.get();
    }

    private Context context;
    private WxAvatarCallback callback;
    private boolean autoUpdate;

    /**
     *
     * @param context
     * @param url 微信头像url
     * @param autoUpdate 上传七牛成功后，是否自动更新接口
     * @param callback 微信上传到七牛后key的回调， 只用在第一次第三方登录
     */
    public void checkWxAvatarUrl(Context context, String url, boolean autoUpdate, WxAvatarCallback callback){
        if (!StringUtil.isUrl(url) || !url.contains(WX_AVATAR_HOST_URL)){
            onFailCallback();
            return;
        }

        this.context = context;
        this.callback = callback;
        this.autoUpdate = autoUpdate;
        downloadFile(url);
    }

    private void downloadFile(String url){
        Glide.with(context)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ByteArrayOutputStream baos = null;
                        try {
                            baos = new ByteArrayOutputStream();
                            boolean result = resource.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            if (result){
                                getQiNiuToken(baos.toByteArray());
                            }
                        } finally {
                            try {
                                if (baos != null) {
                                    baos.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void getQiNiuToken(final byte[] data) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.size, "1");
        map.put(RequestParams.type, 1);
        Observable<BaseResponse<QiNiuTokensEntity>> observable = HttpUtil.getInstance().sApi.getQiNiuToken(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<QiNiuTokensEntity>(context, false) {
            @Override
            protected void _onNext(QiNiuTokensEntity dataBean) {
                if (dataBean.getResults() != null && dataBean.getResults().size() == 1){
                    QiNiuTokensEntity.ResultsBean resultsBean = dataBean.getResults().get(0);
                    uploadToQiNiu(data, resultsBean.getAttachName(), resultsBean.getToken());
                } else {
                    onFailCallback();
                }
            }

            @Override
            protected void _onError(String message) {
                onFailCallback();
            }
        });
    }

    private void uploadToQiNiu(final byte[] data, String attachName, String token) {
        QiNiuUtil.Instance.putImg(data, attachName, token, new QiNiuUtil.OnPutImgListener() {
            @Override
            public void onPutSucceed(String key) {
                if (autoUpdate){
                    updateAvatarToServer(key);
                } else {
                    if (callback != null) {
                        callback.onSuccess(key);
                    }
                }
            }

            @Override
            public void onPutError() {
                onFailCallback();
            }
        });
    }

    private void updateAvatarToServer(String key) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.photo, key);
        Observable<BaseResponse<UpdateUserInfoEntity>> observable = HttpUtil.getInstance().sApi.updateUserInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<UpdateUserInfoEntity>(context, false) {
            @Override
            protected void _onNext(UpdateUserInfoEntity updateUserInfoEntity) {
                MyUserCache.saveUserPhoto(updateUserInfoEntity.getPhoto());
            }

            @Override
            protected void _onError(String message) {
                onFailCallback();
            }
        });
    }

    private void onFailCallback(){
        if (callback != null)
            callback.onFail();
    }

    public interface WxAvatarCallback{
        void onSuccess(String key);
        void onFail();
    }

    private static final Singleton<WxAvatarUrlHelper> gDefault = new Singleton<WxAvatarUrlHelper>() {
        @Override
        protected WxAvatarUrlHelper create() {
            return new WxAvatarUrlHelper();
        }
    };
}
