package com.drops.waterdrop.util.contact;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArraySet;
import android.text.TextUtils;

import com.drops.waterdrop.model.MyContactInfo;
import com.drops.waterdrop.util.sys.ContactUtils;
import com.google.gson.Gson;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.model.AddressBookFriendsEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.LocalContactEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;


/**
 * 同步规则：
 * （1）请求过接口的号码会缓存下来，同时，号码对应的用户信息也会缓存下来供页面使用
 * （2）每次差量请求，都只请求缓存中没有的，如果缓存中都有，就不做接下来的操作
 * （3）每次应用启动（Application.onCreate运行）都会对比缓存与通讯录数据，有差做请求，无差不请求
 *
 * 当前表现
 * （1）通讯录新添加的手机号基本能保证在应用重新启动时会缓存到本地
 * （2）应用重新启动及登录成功都会触发服务
 * （3）如果没有获取通讯录权限，是无法做缓存操作的
 * （4）退出登录会清除缓存
 * （5）在每次缓存结束会发送{@link ContactDataSyncService#ACTION_CONTACT_SYNC_FINISH}广播
 *
 * 现存问题：
 * （1）如果用户删除了手机号，但缓存到本地的手机号不会删除
 * （2）缓存到本地的手机号注册状态不会再次改变，即使服务器已经改变，缓存也不会变，除非退出登录
 * （3）算法需要优化
 *
 * 以后改进
 * （1）找合适时机做全量请求，暂时只在本地没有缓存时做全量请求，其它启动时都是差量请求
 *
 *
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/17 09:34
 */

public class ContactDataSyncService extends IntentService {

    public static final String ACTION_DATA_SYNC = "com.drops.waterdrop.contact.sync";

    public static final String ACTION_CONTACT_SYNC_FINISH = "com.drops.waterdrop.CONTACT_SYNC_FINISH";

    private static final int NUM = 200;

    private Gson gson;
    private List<AddressBookFriendsEntity.ResultsBean> results = new ArrayList<>();
    private List<MyContactInfo> myContactInfos;

    public ContactDataSyncService() {
        super("ContactDataSyncService");
        gson = new Gson();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isGrantedForM(String permission) {
        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isGranted(String permission) {
        return !isMarshmallow() || isGrantedForM(permission);
    }

    private boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action))
            return;

        switch (action){
            case ACTION_DATA_SYNC:
                //没有权限及im没有登录都不执行
                if (isGranted(Manifest.permission.READ_CONTACTS) && !TextUtils.isEmpty(MyUserCache.getIMAccount())){
                    compareLocalData();
                } else {
                }
                break;
        }
    }

    private void compareLocalData(){
        myContactInfos = ContactUtils.get().getPhoneContacts(getBaseContext());
        if (myContactInfos == null || myContactInfos.size() == 0){
            sendSyncSuccBroadcast();
            return;
        }

        ArraySet<String> phoneNumbers = new ArraySet<>();
        ArraySet<String> localPhoneNumbers = MyUserCache.getContactPhoneNumbers();
        if (localPhoneNumbers == null || localPhoneNumbers.size() == 0){
            for (MyContactInfo contact : myContactInfos) {
                String[] numbers = contact.getMobile();
                for (String phoneNumber : numbers) {
                    String moblie = StringUtil.removeBlanks(phoneNumber);
                    if (moblie.length() >= 11) {
                        if (moblie.length() != 11) {
                            String substring = moblie.substring(moblie.length() - 11);
                            phoneNumbers.add(substring);
                        } else {
                            phoneNumbers.add(moblie);
                        }
                    }
                }
            }
        } else {
            for (MyContactInfo contact : myContactInfos) {
                String[] numbers = contact.getMobile();
                for (String phoneNumber : numbers) {
                    String moblie = StringUtil.removeBlanks(phoneNumber);
                    if (!localPhoneNumbers.contains(moblie)){
                        phoneNumbers.add(moblie);
                    }
                }
            }
        }

        if (phoneNumbers.size() > 0){
            //说明有新的号码需要询问服务器
            List<Object> data = Arrays.asList(phoneNumbers.toArray());
            requestFriendInfo(phoneNumbers, data, 0, data.size() > NUM ? NUM : data.size());
        } else {
            sendSyncSuccBroadcast();
        }
    }

    private void requestFriendInfo(final ArraySet<String> phoneNumbers, final List<Object> data, final int start, final int end) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobiles", gson.toJson(data.subList(start, end)));

        Observable<BaseResponse<AddressBookFriendsEntity>> observable = HttpUtil.getInstance().sApi.getAddressBookFriends(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new Subscriber<AddressBookFriendsEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(AddressBookFriendsEntity addressBookFriendsEntity) {
                List<AddressBookFriendsEntity.ResultsBean> result = addressBookFriendsEntity.getResults();
                if (result != null && result.size() > 0) {
                    results.addAll(result);
                }

                int s = end;
                int e = s + NUM;

                if (s >= data.size()){
                    updateName();
                    MyUserCache.setContactPhoneNumbers(phoneNumbers);
                    sendSyncSuccBroadcast();
                    return;
                }

                if (s < data.size() && e > data.size()){
                    e = data.size();
                }

                requestFriendInfo(phoneNumbers, data, s, e);
            }
        });
    }

    private void updateName(){
        ArrayList<LocalContactEntity> localContactEntities = new ArrayList<>();
        for (AddressBookFriendsEntity.ResultsBean friend : results) {
            if (friend.getUid() != MyUserCache.getUserUid()) {
                LocalContactEntity entity = new LocalContactEntity();
                entity.setMobile(friend.getMobile());
                entity.setNickName(friend.getNickName());
                entity.setPhoto(friend.getPhoto());
                entity.setRelationStatus(friend.getRelationStatus());
                entity.setUid(friend.getUid());
                entity.setRegisterStatus(friend.getRegisterStatus());
                localContactEntities.add(entity);
            }
        }

        for (MyContactInfo contact : myContactInfos) {
            String[] numbers = contact.getMobile();
            for (String phoneNumber : numbers) {
                String moblie = StringUtil.removeBlanks(phoneNumber);
                if (moblie.length() >= 11) {
                    if (moblie.length() != 11) {
                        moblie = moblie.substring(moblie.length() - 11);
                    }
                }

                for (LocalContactEntity bean : localContactEntities) {
                    if (TextUtils.equals(bean.getMobile(), moblie)) {
                        bean.setName(contact.name);
                    }
                }
            }
        }

        MyUserCache.setContactCacheData(localContactEntities);
    }

    private void sendSyncSuccBroadcast(){
        sendBroadcast(new Intent(ACTION_CONTACT_SYNC_FINISH));
    }
}
