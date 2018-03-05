package com.netease.nim.uikit.common.util;

import com.google.gson.Gson;
import com.netease.nim.uikit.cache.MyUserCache;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by dengxiaolei on 2017/7/20.
 */

public class RequestBodyUtils {

    public static RequestBody build(Map<String, Object> map){
        if (map == null)
            map = new HashMap<>();

        map.put("uid", MyUserCache.getUserUid());
        map.put("user_token", MyUserCache.getUserToken());

        Gson gson = new Gson();
        String s = gson.toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), s);
        return requestBody;
    }

    public static RequestBody getBody(Map<String, Object> map){
        Gson gson = new Gson();
        String s = gson.toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), s);
        return requestBody;
    }

}
