package com.netease.nim.uikit.common.util;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by dengxiaolei on 2017/6/14.
 */

public enum QiNiuUtil {
    Instance;


    private final UploadManager mUploadManager;

    private boolean isCancelled;

    QiNiuUtil() {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .build();
        mUploadManager = new UploadManager(config);
    }

    /*
    上传图片
     */
    public void putImg(File file, String attachName, String token, final OnPutImgListener listener) {
        mUploadManager.put(file, attachName, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            listener.onPutSucceed(key);
                        } else {
                            listener.onPutError();
                        }
                    }
                }, new UploadOptions(null, null, false, null,
                        new UpCancellationSignal() {
                            public boolean isCancelled() {
                                return isCancelled;
                            }
                        }));
    }

    public void putImg(byte[] data, String attachName, String token, final OnPutImgListener listener){
        mUploadManager.put(data, attachName, token,
                new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    listener.onPutSucceed(key);
                } else {
                    listener.onPutError();
                }
            }
        }, new UploadOptions(null, null, false, null,
                new UpCancellationSignal() {
                    public boolean isCancelled() {
                        return isCancelled;
                    }
                }));
    }

    /*
    取消上传
     */
    public void cancell() {
        isCancelled = true;
    }

    public interface OnPutImgListener{
        void onPutSucceed(String key);

        void onPutError();
    }



}
