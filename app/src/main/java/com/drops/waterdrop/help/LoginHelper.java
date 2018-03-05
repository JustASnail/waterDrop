package com.drops.waterdrop.help;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.AppCache;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.app.preference.UserPreferences;
import com.drops.waterdrop.ui.mine.activity.BindPhoneActivity;
import com.drops.waterdrop.ui.other.activity.LoginActivity;
import com.drops.waterdrop.ui.other.activity.MainActivity;
import com.drops.waterdrop.util.sys.UIUtils;
import com.google.gson.Gson;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.DataCacheManager;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nim.uikit.green_dao.GreenDaoManager;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AccessTokenWXEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.UserInfoEntity;
import com.netease.nim.uikit.model.VerifyOpenId;
import com.netease.nim.uikit.model.WXUserInfoEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Wonder on 2016/8/7.
 */
public enum LoginHelper {


    Instance;

    private Context mContext;

    private AbortableFuture<LoginInfo> mLoginRequest;
    private boolean isFromWeb;

    private String mLoginType;
    private boolean bindMobile;


    LoginHelper() {
    }

    public void login(String account, String pwd, Context context) {
        mContext = context;
        loginMyServer(account, pwd);
    }

    public void login(String account, String pwd, Context context, boolean isFromWeb) {
        this.isFromWeb = isFromWeb;
        mContext = context;
        loginMyServer(account, pwd);
    }

    private void loginMyServer(String account, String pwd) {
        final String stringMD5 = MD5.getStringMD5(pwd);

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.mobile, account);
        map.put(RequestParams.password, stringMD5);
        Observable<BaseResponse<UserInfoEntity>> observable = HttpUtil.getInstance().sApi.login(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<UserInfoEntity>(mContext, "登陆中...") {
            @Override
            protected void _onNext(UserInfoEntity userInfoEntity) {
                if (userInfoEntity != null) {
                    SaveDateToLocal(userInfoEntity);
                }
                
                loginIM(mContext, userInfoEntity.getNeteaseAccid(), userInfoEntity.getNeteaseToken());
            }

            @Override
            protected void _onError(String message) {
                UIUtils.showToast("登陆失败:" + message);
            }
        });
    }

    /**
     * 登陆网易云信
     *
     * @param accid 网易云信的账号
     * @param token 网易云信登陆的token
     */
    public void loginIM(Context context, String accid, final String token) {
        mContext = context;
        DialogMaker.showProgressDialog(context, null, UIUtils.getString(R.string.logining), true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                stopLogin();
                DialogMaker.dismissProgressDialog();
            }
        }).setCanceledOnTouchOutside(false);


        mLoginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(accid, token));
        mLoginRequest
                .setCallback(new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        LogUtil.d("网易云信登陆:", "登陆成功");

                        onLoginIMSuccess(loginInfo, token);
                    }

                    @Override
                    public void onFailed(int code) {
                        LogUtil.e("网易云信登陆:", "登陆失败:" + code);

                        if (code == 302 || code == 404) {
                            onLoginError("账号或密码错误");
                        } else {
                            onLoginError("登陆失败");
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        LogUtil.e("网易云信登陆:", "登陆失败:" + "登陆异常：" + exception);
                        onLoginError("无效输入");
                    }
                });

    }

    /**
     * 保存网易云信相关东西
     *
     * @param loginInfo
     * @param token
     */
    private void onLoginIMSuccess(LoginInfo loginInfo, String token) {
        mLoginRequest = null;

//        NimUIKit.setAccount(loginInfo.getAccount());

        AppCache.setAccount(loginInfo.getAccount());
        DataCacheManager.buildDataCacheAsync();

        MyUserCache.saveIMAccount(loginInfo.getAccount());
        MyUserCache.saveIMToken(token);


        String nowDatetime = TimeUtil.getNowDatetime();
        MyUserCache.saveMessageListSearchStart(nowDatetime);//初始化 最近系统消息列表 的查询时间


        // 初始化消息提醒配置
        initNotificationConfig();

        DialogMaker.dismissProgressDialog();
        GreenDaoManager.getInstance().init(loginInfo.getAccount());

        WaterDropApp.app.startContactSyncService();

        startActivity();
    }

    /**
     * 初始化通知栏消息提醒
     */
    private void initNotificationConfig() {
        // 初始化消息提醒
        UserPreferences.setNotificationToggle(true);

        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = AppCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }


    private void stopLogin() {
        if (mLoginRequest != null) {
            mLoginRequest.abort();
            mLoginRequest = null;
        }
    }

    private void onLoginError(String error) {
        mLoginRequest = null;
        DialogMaker.dismissProgressDialog();
        UIUtils.showToast("登陆失败");
    }

    /**
     * 判断是否是第一次进入 如果是第一次进入就进入引导页
     */
    private void startActivity() {
        if (isFromWeb) {
            ((LoginActivity) mContext).setResult(Constants.FROM_WEB_VALUE);
            ((LoginActivity) mContext).finish();
        }  else {
            jumpToActivityAndClearTask(MainActivity.class);
        }
    }

    private void startBindMobile(String account, String token) {
        Intent intent = new Intent(mContext, BindPhoneActivity.class);
        intent.putExtra(Constants.EXTRA_PAGE_MODE, Constants.EXTRA_PAGE_FOR_WECHAT);
        intent.putExtra(Constants.EXTRA_ACCOUNT, account);
        intent.putExtra(Constants.EXTRA_TOKEN, token);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ((Activity) mContext).startActivity(intent);
        ((Activity) mContext).finish();
        WaterDropApp.finishActivityclass(LoginActivity.class);
    }

    private void jumpToActivityAndClearTask(Class activity) {
        Intent intent = new Intent(mContext, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ((Activity) mContext).startActivity(intent);
        ((Activity) mContext).finish();
        WaterDropApp.finishActivityclass(LoginActivity.class);
    }

    /**
     * 保存自己服务器的用户信息
     *
     * @param userInfoEntity
     */
    private void SaveDateToLocal(UserInfoEntity userInfoEntity) {
        MyUserCache.saveUserMobile(userInfoEntity.getMobile());
        MyUserCache.saveUserToken(userInfoEntity.getUserToken());
        MyUserCache.saveUserUid(userInfoEntity.getUid());

        MyUserCache.saveUserNickName(userInfoEntity.getNickName());
        MyUserCache.saveUserSex(userInfoEntity.getSex());
        MyUserCache.saveUserDesc(userInfoEntity.getUserDesc());
        MyUserCache.saveUserLocation(userInfoEntity.getLocation());
        MyUserCache.saveUserPhoto(userInfoEntity.getPhoto());

//        saveIdentify(userInfoEntity);       //保存身份

        List<UserInfoEntity.UserLikesBean> userLikes = userInfoEntity.getUserLikes();
        if (userLikes != null && userLikes.size() > 0) {
            MyUserCache.saveUserLikes(userLikes);
        }
    }

    /**
     * 保存用户标示  （贴主， 塘主）
     *
     * @param userInfoEntity
     */
   /* private void saveIdentify(UserInfoEntity userInfoEntity) {
        List<UserInfoEntity.IdentifiesBean> identifies = userInfoEntity.getIdentifies();
        if (identifies != null) {
            if (identifies.size() == 0) {
                MyUserCache.saveUserIdentify(Constants.IDENTIFY_NONE);
            } else if (identifies.size() == 1) {
                int type = identifies.get(0).getType();
                if (type == 1) {
                    MyUserCache.saveUserIdentify(Constants.IDENTIFY_TANGZU);
                    MyUserCache.saveTangZuLevel(identifies.get(0).getLevel());
                } else {
                    MyUserCache.saveUserIdentify(Constants.IDENTIFY_TIEZU);
                    MyUserCache.saveTieZuLevel(identifies.get(0).getLevel());
                }
            } else if (identifies.size() == 2) {
                int type = identifies.get(0).getType();
                if (type == 1) {
                    MyUserCache.saveTangZuLevel(identifies.get(0).getLevel());
                    MyUserCache.saveTieZuLevel(identifies.get(1).getLevel());
                } else if (type == 2) {
                    MyUserCache.saveTangZuLevel(identifies.get(1).getLevel());
                    MyUserCache.saveTieZuLevel(identifies.get(0).getLevel());
                }
                MyUserCache.saveUserIdentify(Constants.IDENTIFY_DOUBLE);
            }
        }
    }*/

/*******************************************    微信登陆    *************************************************/
    /**
     * 微信登陆
     *
     * @param context
     * @param code
     */
    public void loginForWX(Context context, String code) {
        mContext = context;

        Observable<AccessTokenWXEntity> forWX = HttpUtil.getInstance().sWXApi.getWXAccessToken(Constants.WEIXIN_APP_ID, Constants.APP_SECRET, code, "authorization_code");
        HttpUtil.getInstance().toSubscribe(forWX, new ProgressSubscriber<AccessTokenWXEntity>(context) {
            @Override
            protected void _onNext(AccessTokenWXEntity o) {
                if (!TextUtils.isEmpty(o.getOpenid())) {
                    verifyOpenId(o);
                } else {
                    onWXLoginError();
                }
            }

            @Override
            protected void _onError(String message) {
                onWXLoginError();

            }
        });
    }


    /**
     * 验证该用户有没有注册过
     *
     * @param entity
     */
    private void verifyOpenId(final AccessTokenWXEntity entity) {
        HashMap<String, String> map = new HashMap<>();
        map.put("open_id", entity.getOpenid());
        Gson gson = new Gson();
        String s = gson.toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), s);
        Observable<BaseResponse<VerifyOpenId>> observable = HttpUtil.getInstance().sApi.verifyOpenId(requestBody);
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<VerifyOpenId>(mContext) {
            @Override
            protected void _onNext(VerifyOpenId verifyOpenId) {
                if (verifyOpenId != null) {
                    int registerStatus = verifyOpenId.getRegisterStatus();
                    if (registerStatus == 1) {//已经注册
                        loginServerForWX(entity.getOpenid(), entity.getUnionid());
                    } else {
                        getWXUserInfo(entity);
                    }
                    MyUserCache.saveUserUnionId(entity.getUnionid());
                }
            }

            @Override
            protected void _onError(String message) {
                getWXUserInfo(entity);
            }
        });
    }


    /**
     * 获取微信用户个人资料
     *
     * @param entity
     */
    private void getWXUserInfo(final AccessTokenWXEntity entity) {
        Observable<WXUserInfoEntity> observable = HttpUtil.getInstance().sWXApi.getWXUserInfo(entity.getAccess_token(), entity.getOpenid());
        HttpUtil.getInstance().toSubscribe(observable, new ProgressSubscriber<WXUserInfoEntity>(mContext) {
            @Override
            protected void _onNext(WXUserInfoEntity userInfo) {
                cacheAvatar(userInfo);
//                loginServerForWX(userInfo);
            }

            @Override
            protected void _onError(String message) {
                loginServerForWX(entity.getOpenid(), entity.getUnionid());
            }
        });
    }

    /**
     * 缓存微信头像，并上传到七牛，拿到key
     * @param entity
     */
    private void cacheAvatar(final WXUserInfoEntity entity) {
        WxAvatarUrlHelper.get().checkWxAvatarUrl(mContext, entity.getHeadimgurl(), false, new WxAvatarUrlHelper.WxAvatarCallback() {
            @Override
            public void onSuccess(String key) {
                entity.setHeadimgurl(key);
                loginServerForWX(entity);
            }

            @Override
            public void onFail() {
                //如果失败暂时先用微信的url,后面会有地方再次检测
                loginServerForWX(entity);
            }
        });
    }

    /**
     * 当没有注册过的用户登陆服务器
     */
    private void loginServerForWX(WXUserInfoEntity userInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put("sex", userInfo.getSex());
        map.put("photo", userInfo.getHeadimgurl());
        map.put("nick_name", userInfo.getNickname());
        map.put("union_id", userInfo.getUnionid());
        map.put("open_id", userInfo.getOpenid());
        map.put("login_type", "2");

        Observable<BaseResponse<UserInfoEntity>> observable = HttpUtil.getInstance().sApi.loginForWX(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<UserInfoEntity>(mContext, "正在登陆...") {
            @Override
            protected void _onNext(UserInfoEntity userInfoEntity) {
                if (userInfoEntity != null) {
                    SaveDateToLocal(userInfoEntity);

                    if (TextUtils.isEmpty(userInfoEntity.getMobile())) {
                        startBindMobile(userInfoEntity.getNeteaseAccid(), userInfoEntity.getNeteaseToken());
                    } else {
                        loginIM(mContext, userInfoEntity.getNeteaseAccid(), userInfoEntity.getNeteaseToken());
                    }
                }
            }

            @Override
            protected void _onError(String message) {
                onWXLoginError();
            }
        });
    }

    /**
     * 当已经注册过的用户登陆服务器
     *
     * @param openid
     * @param unionid
     */
    private void loginServerForWX(String openid, String unionid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.open_id, openid);
        map.put(RequestParams.union_id, unionid);
        map.put(RequestParams.login_type, Constants.LOGIN_TYPE_WX);
        Observable<BaseResponse<UserInfoEntity>> observable = HttpUtil.getInstance().sApi.loginForWX(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<UserInfoEntity>(mContext, "正在登陆...") {


            @Override
            protected void _onNext(UserInfoEntity userInfoEntity) {
                if (userInfoEntity != null) {
                    SaveDateToLocal(userInfoEntity);

                    if (TextUtils.isEmpty(userInfoEntity.getMobile())) {
                        startBindMobile(userInfoEntity.getNeteaseAccid(), userInfoEntity.getNeteaseToken());
                    } else {
                        loginIM(mContext, userInfoEntity.getNeteaseAccid(), userInfoEntity.getNeteaseToken());
                    }
                }
            }

            @Override
            protected void _onError(String message) {
                onWXLoginError();
            }
        });
    }

    private void onWXLoginError() {
        ((Activity) mContext).finish();
    }

}
