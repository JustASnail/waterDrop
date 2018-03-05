package com.netease.nim.uikit.http;

import android.text.TextUtils;
import android.util.Log;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.http.http_api.ApiService;
import com.netease.nim.uikit.http.http_cache.CaheInterceptor;
import com.netease.nim.uikit.http.http_cache.NovateCookieManger;
import com.netease.nim.uikit.model.BaseResponse;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 重要  用这个
 * Created by SNAIL on 2017/3/9.
 */

public class HttpUtil {

    private static final String HEAD_LINE_NEWS = "T1348647909107";

    //设缓存有效期为1天
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

//    public static final String JAVA_HOST = "http://106.14.125.78:8082/";
//    public static final String JAVA_HOST = "http://106.14.120.9:7080/";    //测试
    public static final String JAVA_HOST = "http://app.waterdrop.xin/";
    public static final String WX_HOST = "https://api.weixin.qq.com/";

    private  Retrofit retrofit;
    private  Cache cache = null;
    private  File httpCacheDirectory;

    private static final int DEFAULT_TIMEOUT = 20;

    // 递增页码
    private static final int INCREASE_PAGE = 20;
    public ApiService sApi;
    public ApiService sWXApi;


    private HttpUtil() {
        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(NimUIKit.getContext().getCacheDir(), "water_drops_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addNetworkInterceptor(
//                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(new NovateCookieManger(NimUIKit.getContext()))
                .cache(cache)
                .addInterceptor(sLoggingInterceptor)
                .addInterceptor(sLogging)
                .addInterceptor(new CaheInterceptor())
                .addNetworkInterceptor(new CaheInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(JAVA_HOST)
                .build();

        Retrofit retrofitWX = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(WX_HOST)
                .build();
        sWXApi = retrofitWX.create(ApiService.class);

        //打印响应json
        sLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

        sApi = retrofit.create(ApiService.class);
    }


    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    //获取单例
    public static HttpUtil getInstance(){
        return SingletonHolder.INSTANCE;
    }
    /**
     * 初始化网络通信服务
     */
    public static void init() {



    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }



    /**
     * 打印返回的json数据拦截器(打印请求)
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //final Request request = chain.request(); //post提交普通的键值对参数

            //post提交json数据参数
            final Request request = chain.request().newBuilder().addHeader("Content-type", "application/json").build();
            //打印url信息
            Logger.w(String.format("发送请求 %s on %s%s   请求方式:%s",
                    request.url(), chain.connection(), request.headers(), request.method()));

            final Response response = chain.proceed(request);


            return response;
        }
    };

    /**
     * 打印返回的响应头json数据（打印响应）
     */
    private static final HttpLoggingInterceptor sLogging = new HttpLoggingInterceptor(
            new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (TextUtils.isEmpty(message))
                        return;
                    String s = message.substring(0, 1);

                    //如果收到想响应是json才打印
                    if ("{".equals(s) || "[".equals(s)) {
                        Logger.json(message);
                    }
                }
            });

    private static class ResponseFunc<T> implements Func1<BaseResponse<T>, T> {

        @Override
        public T call(BaseResponse<T> response) {
//            try {

                if (response == null) {
                    //失败1
                    throw new ApiException("异常");
                }
                int code = response.getCode();
                String message = response.getMessage();

                if (code != 200) {
                    throw new ApiException(message);
                }

//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }

            return response.getData();
        }
    }


    public void toSubscribe(Observable o, Subscriber s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    /**
     * 通用
     *
     * @param observable api接口返回的observable 范型也是接口返回的一致（返回值T明确）
     * @param subscriber 自定义封装的对返回有处理的subscriber
     * @param <T>        map操作符把baseresponse转换成T
     * @return
     */
    public  <T> T execute(Observable<BaseResponse<T>> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ResponseFunc<T>())
                .subscribe(subscriber);

        return null;
    }
}
