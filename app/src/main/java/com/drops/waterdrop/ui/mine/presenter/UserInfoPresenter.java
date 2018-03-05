package com.drops.waterdrop.ui.mine.presenter;

import android.content.DialogInterface;
import android.text.TextUtils;

import com.netease.nim.uikit.cache.MyUserCache;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.IUserInfoView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.QiNiuUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.QiNiuTokensEntity;
import com.netease.nim.uikit.model.UpdateUserInfoEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/6/2.
 */

public class UserInfoPresenter extends BasePresenter<IUserInfoView> {


    public UserInfoPresenter(BaseActivity context) {
        super(context);
    }


    public void getUserInfo() {
        String photo = MyUserCache.getUserPhoto();
        String nickName = MyUserCache.getUserNickName();
        int sex = MyUserCache.getUserSex();
        String desc = MyUserCache.getUserDesc();
        String location = MyUserCache.getUserLocation();
        String sexStr = "未知";
        if (sex == 1) {
            sexStr = "男";
        } else if (sex == 2) {
            sexStr = "女";
        }

        getView().updateUI(photo, nickName, sexStr, desc, location);

    }


    public void updateAvatar(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }

        File file = new File(path);
        if (file == null) {
            return;
        }
        getQiNiuToken(file);
    }

    private void getQiNiuToken(final File file) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.size, "1");
        map.put(RequestParams.type, 1);
        Observable<BaseResponse<QiNiuTokensEntity>> observable = HttpUtil.getInstance().sApi.getQiNiuToken(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<QiNiuTokensEntity>(mContext, "正在更新...") {
            @Override
            protected void _onNext(QiNiuTokensEntity dataBean) {
                QiNiuTokensEntity.ResultsBean resultsBean = dataBean.getResults().get(0);
                String attachName = resultsBean.getAttachName();
                String token = resultsBean.getToken();
                if (!TextUtils.isEmpty(attachName) && !TextUtils.isEmpty(token)) {
                    putQiNiu(file, attachName, token);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void putQiNiu(File file, String attachName, String token) {

        DialogMaker.showProgressDialog(mContext, "正在更新", null, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                QiNiuUtil.Instance.cancell();
                DialogMaker.dismissProgressDialog();
            }
        }).setCanceledOnTouchOutside(true);

        QiNiuUtil.Instance.putImg(file, attachName, token, new QiNiuUtil.OnPutImgListener() {
            @Override
            public void onPutSucceed(String key) {
                updateAvatarToServer(key);
            }

            @Override
            public void onPutError() {
                onUpdateError();
            }
        });
    }

    private void onUpdateError() {
        DialogMaker.dismissProgressDialog();
        ToastUtil.showShort("更新失败");
    }

    private void updateAvatarToServer(String key) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.photo, key);
        map.put(RequestParams.sex, MyUserCache.getUserSex());
        Observable<BaseResponse<UpdateUserInfoEntity>> observable = HttpUtil.getInstance().sApi.updateUserInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<UpdateUserInfoEntity>(mContext, "正在更新...") {
            @Override
            protected void _onNext(UpdateUserInfoEntity updateUserInfoEntity) {
                updateLocalUserData(updateUserInfoEntity);
                getUserInfo();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort("更新失败：" + message);

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
