package com.drops.waterdrop.ui.mine.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.drops.waterdrop.util.sys.TextLimitUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.activity.UserInfoEditItemActivity;
import com.drops.waterdrop.ui.mine.view.IUserInfoEditItemView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.UpdateUserInfoEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


/**
 * Created by dengxiaolei on 2017/6/14.
 */

public class UserInfoEditItemPresenter extends BasePresenter<IUserInfoEditItemView> {

    public static final int KEY_NICKNAME = 1;
    public static final int KEY_GENDER = 2;
    public static final int KEY_SIGNATURE = 3;
    public static final int KEY_BIRTH = 4;
    public static final int KEY_PHONE = 5;
    public static final int KEY_EMAIL = 6;
    public static final int KEY_ALIAS = 7;
    public String mTitle;
    private String mData;
    public int mKey;

    public UserInfoEditItemPresenter(BaseActivity context) {
        super(context);
    }

    public void parseIntent(Intent intent) {
        mKey = intent.getIntExtra(UserInfoEditItemActivity.EXTRA_KEY, -1);
        mData = intent.getStringExtra(UserInfoEditItemActivity.EXTRA_DATA);
        getView().onParseIntentSucceed(mKey, mData);

    }


    public void updateNickName(String nickName) {
        if (TextUtils.isEmpty(nickName)) {
            ToastUtil.showShort("昵称不能为空");
            return;
        }

        if (!TextLimitUtil.isIllegal(nickName)) {
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.nick_name, nickName);
        map.put(RequestParams.sex, MyUserCache.getUserSex());
        update(map);
    }

    public void updateSignature(String signature) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.user_desc, signature);
        map.put(RequestParams.sex, MyUserCache.getUserSex());
        update(map);
    }

    public void updateGender(int gender) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.sex, gender);
        update(map);
    }

    private void update(Map map) {
        Observable<BaseResponse<UpdateUserInfoEntity>> observable = HttpUtil.getInstance().sApi.updateUserInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<UpdateUserInfoEntity>(mContext, "正在更新...") {
            @Override
            protected void _onNext(UpdateUserInfoEntity updateUserInfoEntity) {
                ToastUtil.showShort("保存成功");
                updateLocalUserData(updateUserInfoEntity);
                mContext.finish();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort("保存失败");

            }
        });
    }

    private void updateLocalUserData(UpdateUserInfoEntity updateUserInfoEntity) {
        MyUserCache.saveUserNickName(updateUserInfoEntity.getNickName());
        MyUserCache.saveUserSex(updateUserInfoEntity.getSex());
        MyUserCache.saveUserDesc(updateUserInfoEntity.getUserDesc());
        MyUserCache.saveUserLocation(updateUserInfoEntity.getLocation());
        MyUserCache.saveUserPhoto(updateUserInfoEntity.getPhoto());

    }
}
