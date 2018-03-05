package com.drops.waterdrop.webapi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.drops.waterdrop.ui.find.activity.GoodsDetailsActivity;
import com.drops.waterdrop.ui.find.activity.OrderConfirmationActivity;
import com.drops.waterdrop.ui.find.activity.PoolDetailPageActivity;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.drops.waterdrop.ui.mine.activity.UserProfileActivity;
import com.drops.waterdrop.ui.other.activity.LoginActivity;
import com.drops.waterdrop.util.ToastUtil;
import com.google.gson.Gson;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.GoodsDetailEntity;
import com.netease.nim.uikit.model.WebEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;

import rx.Observable;


/**
 * Created by Mr.Smile on 2017/7/12.
 */

public class WebEntityActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userToken = MyUserCache.getUserToken();
        if (!TextUtils.isEmpty(userToken)) {
            initData();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(Constants.FROM_WEB_KEY, Constants.FROM_WEB_VALUE);
            startActivityForResult(intent, Constants.FROM_WEB_VALUE);
        }
    }

    private void initData() {
        Uri data = getIntent().getData();
        if (data != null) {
            String params = data.getQueryParameter("params");
            com.orhanobut.logger.Logger.json(params);
            Gson gson = new Gson();
            WebEntity webEntity = gson.fromJson(params, WebEntity.class);
            String action = webEntity.getAction();
            goTo(action,webEntity.getParams());
        }
    }

    private void goTo(String action, WebEntity.ParamsBean paramsBean) {
        if (TextUtils.equals(action, "tipDetail")) {
            CommonWebActivity.start(this, paramsBean.getTipId(), paramsBean.getTipUrl());
            finish();
        } else if (TextUtils.equals(action, "goodsDetail")) {
            GoodsDetailsActivity.start(this, paramsBean.getGoodId(), paramsBean.getTipId(), paramsBean.getDropId(),paramsBean.getTipTitle());
            finish();
        } else if (TextUtils.equals(action, "dropInfo")) {
            PoolDetailPageActivity.start(this, paramsBean.getDropId());
            finish();
        } else if (TextUtils.equals(action, "goodsBuy")) {
            getGoodsData(paramsBean.getGoodId(), paramsBean.getTipId(), paramsBean.getDropId(), Constants.ORDER_TYPE_DEFAULT);
        } else if (TextUtils.equals(action, "userInfo")) {
            UserProfileActivity.start(this, paramsBean.getUid());
            finish();
        }
    }

    private void getGoodsData(String goodsId, final long tipId, final long dropId, final int type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.good_id, goodsId);
        Observable<BaseResponse<GoodsDetailEntity>> observable = HttpUtil.getInstance().sApi.getGoodsDetail(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<GoodsDetailEntity>(this) {
            @Override
            protected void _onNext(GoodsDetailEntity o) {
                if (o != null) {
                    OrderConfirmationActivity.start(WebEntityActivity.this, o, dropId, tipId, type);
                    finish();
                } else {
                    ToastUtil.showShort("此商品异常");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.FROM_WEB_VALUE) {
            initData();
        }
    }
}
