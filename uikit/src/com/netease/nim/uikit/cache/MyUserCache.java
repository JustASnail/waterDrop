package com.netease.nim.uikit.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.ArraySet;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.model.AddressBookFriendsEntity;
import com.netease.nim.uikit.model.LocalContactEntity;
import com.netease.nim.uikit.model.UserInfoEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by hzxuwen on 2015/4/13.
 */
public class MyUserCache {
    private static final String KEY_FIRST_ENTER = "first_enter";
    private static final String KEY_USER_MOBILE = "mobile";
    private static final String KEY_IM_ACCOUNT = "im_account";
    private static final String KEY_IM_TOKEN = "im_token";

    private static final String KEY_USER_TOKEN = "user_token";
    private static final String KEY_USER_UID = "user_uid";
    private static final String KEY_USER_UNION = "union_id";

    private static final String KEY_USER_LOCATION = "user_location";
    private static final String KEY_USER_NICKNAME = "user_nickName";
    private static final String KEY_USER_PHOTO = "user_photo";
    private static final String KEY_USER_SEX = "user_sex";
    private static final String KEY_USER_DESC = "user_desc";
    private static final String KEY_USER_JINDOU = "user_jindou";
    private static final String KEY_USER_IDENTIFY = "user_identify";
    private static final String KEY_USER_IDENTIFY_NUM = "user_identify_num";
    private static final String KEY_TANGZU_LEVEL = "tangzu_level";
    private static final String KEY_TIEZU_LEVEL = "tiezu_level";
    private static final String KEY_LAST_BROWSER_TIME = "last_browser_time";

    private static final String KEY_USER_LIKE_LIST = "user_likes_list";
    private static final String KEY_MESSAGE_LIST_SEARCH_START = "message_list_search_start";//消息列表查询时间
    private static final String KEY_IS_GUIDE = "is_guide";//是否展示过新手导航


    //******************************   应用的用户数据   ******************************//
    public static void saveUserMobile(String mobile) {
        saveString(KEY_USER_MOBILE, mobile);
    }

    public static String getUserMobile() {
        return getString(KEY_USER_MOBILE);
    }

    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }

    public static void saveUserUid(long uid) {
        saveLong(KEY_USER_UID, uid);
    }

    public static long getUserUid() {
        return getLong(KEY_USER_UID);
    }

    public static void saveUserLocation(String location) {
        saveString(KEY_USER_LOCATION, location);
    }

    public static String getUserLocation() {
        return getString(KEY_USER_LOCATION);
    }

    public static void saveUserNickName(String nickName) {
        saveString(KEY_USER_NICKNAME, nickName);
    }

    public static String getUserNickName() {
        return getString(KEY_USER_NICKNAME);
    }

    public static void saveUserPhoto(String photo) {
        saveString(KEY_USER_PHOTO, photo);
    }

    public static String getUserPhoto() {
        return getString(KEY_USER_PHOTO);
    }

    public static void saveUserRemainMoney(String photo) {
        saveString(Constants.REAMIN_MONEY+getIMAccount(), photo);
    }

    public static String getUserRemainMoney() {
        return getString(Constants.REAMIN_MONEY+getIMAccount());
    }

    public static void saveUserSex(int sex) {
        saveInt(KEY_USER_SEX, sex);
    }

    public static int getUserSex() {
        return getInt(KEY_USER_SEX);
    }

    public static void saveUserDesc(String desc) {
        saveString(KEY_USER_DESC, desc);
    }

    public static String getUserDesc() {
        return getString(KEY_USER_DESC);
    }

    public static void saveUserJinDou(int num) {
        saveInt(KEY_USER_JINDOU, num);
    }

    public static int getUserJinDou() {
        return getInt(KEY_USER_JINDOU);
    }

    public static void saveUserIdentify(String s) {
        saveString(KEY_USER_IDENTIFY, s);
    }

    public static String getUserIdentify() {
        return getString(KEY_USER_IDENTIFY);
    }

    public static void saveLastBrowserTime(String time){
        saveString(KEY_LAST_BROWSER_TIME+getIMAccount(), time);
    }

    public static String getLastBrowserTime(){
        return getString(KEY_LAST_BROWSER_TIME+getIMAccount());
    }

    public static void saveBankName(String s) {
        saveString(Constants.BANK_NAME+getIMAccount(), s);
    }

    public static String getBankName() {
        return getString(Constants.BANK_NAME+getIMAccount());
    }

    public static void saveBankNum(String s) {
        saveString(Constants.BANK_NUM+getIMAccount(), s);
    }

    public static String getBankNum() {
        return getString(Constants.BANK_NUM+getIMAccount());
    }

    public static void saveCardPic(String s) {
        saveString(Constants.CARD_PIC+getIMAccount(), s);
    }

    public static String getCardPic() {
        return getString(Constants.CARD_PIC+getIMAccount());
    }

    public static long getCardId() {
        return getLong(Constants.CARD_ID+getIMAccount());
    }

    public static void saveCardId(long s) {
        saveLong(Constants.CARD_ID+getIMAccount(), s);
    }

    public static void saveUserLikes(List<UserInfoEntity.UserLikesBean> userLikes) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        Gson gson = new Gson();
        String json = gson.toJson(userLikes);
        editor.putString(KEY_USER_LIKE_LIST, json);
        editor.commit();
    }

    public static List<UserInfoEntity.UserLikesBean> getUserLikes() {
        String json = getString(KEY_USER_LIKE_LIST);
        Gson gson = new Gson();
        List<UserInfoEntity.UserLikesBean> list = gson.fromJson(json, new TypeToken<List<UserInfoEntity.UserLikesBean>>() {
        }.getType());

        return list;
    }

    public static void saveUserInviteStatus(String moblie, boolean status) {
        saveBoolean(getIMAccount() + moblie, status);
    }

    public static boolean getUserInviteStatus(String moblie) {
        return getBoolean(getIMAccount() + moblie);
    }

    public static void saveMobileContact(List<LocalContactEntity> mobileContactEntityList) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        Gson gson = new Gson();
        String json = gson.toJson(mobileContactEntityList);
        editor.putString("moblie_contact" + getIMAccount(), json);
        editor.commit();
    }

    public static List<LocalContactEntity> getMobileContact() {
        String json = getString("moblie_contact" + getIMAccount());
        Gson gson = new Gson();
        List<LocalContactEntity> list = gson.fromJson(json, new TypeToken<List<LocalContactEntity>>() {
        }.getType());

        return list;
    }

    public static ArraySet<String> getContactPhoneNumbers(){
        String json = getString("contact_phone_number_" + getIMAccount());
        Gson gson = new Gson();
        ArraySet<String> list = gson.fromJson(json, new TypeToken<ArraySet<String>>() {
        }.getType());
        return list;
    }

    public static void setContactPhoneNumbers(ArraySet<String> phoneNumbers){
        ArraySet<String> data = getContactPhoneNumbers();
        if (data == null){
            data = new ArraySet<>();
        }
        data.addAll(phoneNumbers);

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("contact_phone_number_" + getIMAccount(), new Gson().toJson(data)).apply();
    }

    public static List<LocalContactEntity> getContactCacheData(){
        String json = getString("contact_local_cache_"+getIMAccount());
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<LocalContactEntity>>() {}.getType());
    }

    public static void setContactCacheData(List<LocalContactEntity> beanList){
        List<LocalContactEntity> data = getContactCacheData();
        if (data == null){
            data = new ArrayList<>();
        }
        data.addAll(beanList);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("contact_local_cache_" + getIMAccount(), new Gson().toJson(data)).apply();
    }

    /**
     * 退出时，在setIMAccount()之前调用
     */
    private static void clearContactCache(){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("contact_local_cache_" + getIMAccount(), "");
        editor.putString("contact_phone_number_" + getIMAccount(), "");
        editor.apply();
    }


    public static void saveGuide( boolean isGuide) {
        saveBoolean(KEY_IS_GUIDE, isGuide);
    }

    public static boolean getGuide() {
        return getBoolean(KEY_IS_GUIDE);
    }


    //******************************   IM端用户数据   ******************************//



    public static void saveIMAccount(String account) {
        if (TextUtils.isEmpty(account))
            clearContactCache();

        saveString(KEY_IM_ACCOUNT, account);
    }

    public static String getIMAccount() {
        return getString(KEY_IM_ACCOUNT);
    }

    public static void saveIMToken(String token) {
        saveString(KEY_IM_TOKEN, token);
    }


    public static String getIMToken() {
        return getString(KEY_IM_TOKEN);
    }


    public static void saveFirstEnter(boolean isEnter) {
        saveBoolean(KEY_FIRST_ENTER, isEnter);
    }

    public static boolean getFirstEnter() {
        return getBoolean(KEY_FIRST_ENTER);
    }

    public static void saveMessageListSearchStart(String searchStart) {//要和用户有关联
        saveString(getIMAccount() + "system_search_time", searchStart);
    }

    public static String getMessageListSearchStart() {
        String string = getSharedPreferences().getString(getIMAccount() + "system_search_time", null);
        return string;
    }
/*
    public static void saveMessageListSearchStart(String searchStart) {//要和用户有关联
        String imAccount = getIMAccount();
        saveString(imAccount, searchStart);
    }

    public static String getMessageListSearchStart() {
        String imAccount = getIMAccount();
        String string = getString(imAccount);
        return string;
    }*/




    private static void saveBoolean(String keyFirstEnter, boolean isEnter) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(keyFirstEnter, isEnter);
        editor.commit();
    }

    private static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    private static void saveLong(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putLong(key, value);
        editor.commit();
    }

    private static int getInt(String key) {
        return getSharedPreferences().getInt(key, -1);
    }

    private static void saveInt(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private static long getLong(String key) {
        return getSharedPreferences().getLong(key, 1);
    }


    static SharedPreferences getSharedPreferences() {
        return NimUIKit.getContext().getSharedPreferences("WaterDrops", Context.MODE_PRIVATE);
    }

    public static void saveObj(String key, Object value) throws IOException {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(value);
        String objString = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        editor.putString(key, objString).commit();
        objectOutputStream.close();
    }

    public static Object getObj(String key) throws StreamCorruptedException, IOException, ClassNotFoundException {
        String str = getSharedPreferences().getString(key, "");
        if (str.length() <= 0)
            return null;
        Object obj = null;
        byte[] mobileBytes = Base64.decode(str.toString().getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream;
        objectInputStream = new ObjectInputStream(byteArrayInputStream);
        obj = objectInputStream.readObject();
        objectInputStream.close();
        return obj;
    }

    public static void saveUserUnionId(String union_id) {
        saveString(KEY_USER_UNION, union_id);
    }

    public static String getUserUnionId() {
        return getString(KEY_USER_UNION);
    }

    public static void saveVipOrSuply(int b) {
        saveInt(Constants.IS_VIP + getIMAccount(), b);
    }

    public static int getVipOrSuply(){
        return getInt(Constants.IS_VIP + getIMAccount());
    }

    public static void saveChinaPavilionActivityActive(boolean b) {
        saveBoolean("ChinaPavilionActivityActive", b);
    }

    public static boolean isChinaPavilionActivityActive() {
        return getBoolean("ChinaPavilionActivityActive");
    }

}
