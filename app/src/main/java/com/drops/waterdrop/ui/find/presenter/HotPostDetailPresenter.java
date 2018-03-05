package com.drops.waterdrop.ui.find.presenter;

import android.content.Intent;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.find.view.IHotPostDetailView;
import com.drops.waterdrop.util.ToastUtil;
import com.google.gson.Gson;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.PostEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

import static com.drops.waterdrop.app.WaterDropApp.tipId;

/**
 * Created by dengxiaolei on 2017/5/25.
 */

public class HotPostDetailPresenter extends BasePresenter<IHotPostDetailView> {

    private PostEntity mEntity;

    private long mTip_id;

    public HotPostDetailPresenter(BaseActivity context) {
        super(context);
    }

    public void parseIntent(Intent intent) {
        mTip_id = intent.getLongExtra(Constants.EXTRA_TIP_ID, -1);
    }

    public void getData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.tip_id, mTip_id);
        Observable<BaseResponse<PostEntity>> observable = HttpUtil.getInstance().sApi.getPostDetails(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<PostEntity>(mContext) {

            @Override
            protected void _onNext(PostEntity entity) {
                mEntity = entity;
                getView().onGetDataSucceed(entity);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                mContext.finish();
            }
        });
    }


    public List<String> getBannerData() {
        ArrayList<String> photoUrls = new ArrayList<>();
        List<PostEntity.PhotosBean> photos = mEntity.getPhotos();
        if (photos != null && photos.size() > 0) {
            for (PostEntity.PhotosBean photo : photos) {
                photoUrls.add(photo.getPhoto());
            }
        }
        return photoUrls;
    }

    public List<PostEntity.CommentBean> getComments() {
        return mEntity.getComments();
    }

    public void deleteCollect() {
        ArrayList<String> list = new ArrayList<>();
        list.add(String.valueOf(String.valueOf(tipId)));
        Gson gson = new Gson();
        String s = gson.toJson(list);
        Map<String, Object> map = new HashMap<>();
        map.put("collect_id_json", s);
        map.put("type", 1);

        Observable<BaseResponse<Object>> insert = HttpUtil.getInstance().sApi.removeCollect(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(insert, new ProgressSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {
            }

            @Override
            protected void _onError(String message) {
            }
        });
    }

    public void insertCollect() {
        Map<String, Object> map = new HashMap<>();
        map.put("collect_id", String.valueOf(String.valueOf(tipId)));
        map.put("type", 1);
        Observable<BaseResponse<Object>> insert = HttpUtil.getInstance().sApi.insertCollect(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(insert, new ProgressSubscriber<Object>(mContext, false) {
            @Override
            protected void _onNext(Object o) {
            }

            @Override
            protected void _onError(String message) {
            }
        });
    }

    public List<PostEntity.GoodsBean> getGoods() {
        return mEntity.getGoods();
    }
}
