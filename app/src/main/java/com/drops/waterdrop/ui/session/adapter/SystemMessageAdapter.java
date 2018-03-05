package com.drops.waterdrop.ui.session.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.ConfirmedFriendEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


/**
 * Created by dengxiaolei on 2017/5/15.
 */

public class SystemMessageAdapter extends BaseQuickAdapter<ConfirmedFriendEntity.ResultsBean, BaseViewHolder> {
    private boolean mIsShow;
    public SystemMessageAdapter(@LayoutRes int layoutResId, boolean isShow) {
        super(R.layout.item_friend_verify_list);
        mIsShow = isShow;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ConfirmedFriendEntity.ResultsBean item) {
//        helper.setImageResource(R.id.img_avatar, item.HeaderImgId);

        ImageView flag = helper.getView(R.id.iv_msg_flag);
        flag.setVisibility(mIsShow ? View.VISIBLE : View.GONE);
        ImageView ivPoint = helper.getView(R.id.point_flag);
        HeadImageView headImageView = helper.getView(R.id.img_avatar);
        final Button btnAdd = helper.getView(R.id.btn_add);
        GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_50x50, item.getFromUser().getPhoto(), headImageView);
        helper.setText(R.id.tv_name, item.getFromUser().getNickName());
        helper.setText(R.id.tv_note, item.getNote());
        helper.setText(R.id.tv_date, item.getCreateTime());
        if (item.getStatus() == 1) {//0是未处理， 1是已添加
            btnAdd.setText("已同意");
            btnAdd.setEnabled(false);
            ivPoint.setVisibility(View.GONE);
        } else {
            btnAdd.setText("同意");
            btnAdd.setEnabled(true);
            ivPoint.setVisibility(mIsShow ? View.VISIBLE : View.GONE);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getType() == 11) {
                    confirmFriend(item.getFromUser().getUid(), item);

                } else if (item.getType() == 12) {
                    addGroup(item);
                }
            }
        });
    }

    /*
    同意好友添加好友请求
     */
    private void confirmFriend(long uid, final ConfirmedFriendEntity.ResultsBean item) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.f_uid, uid);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.confirmedFriend(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, "正在添加...") {
            @Override
            protected void _onNext(Object o) {
                ToastUtil.showShort("已同意");
                item.setStatus(1);
                notifyDataSetChanged();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);

            }
        });
    }

    /*
   同意申请入群请求
    */
    private void addGroup(final ConfirmedFriendEntity.ResultsBean item) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, item.getTargetId());
        map.put(RequestParams.join_uid, item.getFromUser().getUid());
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.confirmAddGroup(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, "正在添加...") {
            @Override
            protected void _onNext(Object o) {
                ToastUtil.showShort("已同意");
                item.setStatus(1);
                notifyDataSetChanged();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);

            }
        });
    }

}
