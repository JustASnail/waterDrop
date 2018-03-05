package com.drops.waterdrop.ui.pool.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.find.activity.PoolDetailPageActivity;
import com.drops.waterdrop.ui.pool_card.CardHandler;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.RecommendPoolListEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * description
 * <p>
 * Created by sunjian on 2017/6/24.
 */

public class PoolCardHandler implements CardHandler<RecommendPoolListEntity.ResultsBean>, View.OnClickListener {
    private int startX;
    private int startY;

    private boolean isLiked;
    private RecommendPoolListEntity.ResultsBean  mData;
    @Override
    public View onBind(final Context context, final RecommendPoolListEntity.ResultsBean  data, final int position) {
        mData = data;
        View view = View.inflate(context, R.layout.item_pool_cards, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_container);
        TextView tvPoolName = (TextView) view.findViewById(R.id.tv_pool_name);

        FrameLayout flLike = (FrameLayout) view.findViewById(R.id.fl_container_like);
        final ImageView ivLikeIcon = (ImageView) view.findViewById(R.id.iv_like);
        final TextView tvNumber = (TextView) view.findViewById(R.id.tv_number);
//        mTvNumber.setText(data.getAttentionNum() + "");
       tvNumber.setText(NumberUtil.Instance.formatNumber(data.getAttentionNum()));

        Glide.with(context).load(data.getDropPhoto()).into(imageView);
        tvPoolName.setText(data.getDropName());
        int attentionStatus = data.getAttentionStatus();
        if (attentionStatus == 1) {
            ivLikeIcon.setImageResource(R.mipmap.icon_st_sc);
            isLiked = true;
        } else {
            ivLikeIcon.setImageResource(R.mipmap.icon_st_wsc);
            isLiked = false;
        }
        final long dropId = data.getDropId();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ToastUtil.showShort(position+"");
                String str = "14968253943581";
                PoolDetailPageActivity.start(context, Long.valueOf(str));*/
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        int endX = (int) event.getX();
                        int endY = (int) event.getY();

                        if (Math.abs(endX - startX) < 50 && Math.abs(endY - startY) < 50) {
//                            PoolDetailPageActivity.start(context, dropId);
                            Intent intent = new Intent(context, PoolDetailPageActivity.class);
                            intent.putExtra(Constants.EXTRA_DROP_ID, dropId);
                            ((Activity)context).startActivity(intent);
//                            ((Activity)context).overridePendingTransition(R.anim.zoom_in_entry, R.anim.zoom_in_exit);

                            return true;
                        }
                        break;
                }
                return false;
            }
        });

        flLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getAttentionStatus() == 1) {
                    deleteAttention(context, data.getDropId());
                    data.setAttentionStatus(0);
                    data.setAttentionNum(data.getAttentionNum() - 1);
                    ivLikeIcon.setImageResource(R.mipmap.icon_st_wsc);
                    tvNumber.setText(NumberUtil.Instance.formatNumber(data.getAttentionNum()));

                } else {
                    insertAttention(context, data.getDropId());
                    data.setAttentionStatus(1);
                    data.setAttentionNum(data.getAttentionNum() + 1);
                    ivLikeIcon.setImageResource(R.mipmap.icon_st_sc);
                    tvNumber.setText(NumberUtil.Instance.formatNumber(data.getAttentionNum()));

                }

            }
        });
        return view;
    }

    private void deleteAttention(Context context, long dropId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, dropId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.cancelAttentionPool(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(context, false) {
            @Override
            protected void _onNext(Object entity) {

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    private void insertAttention(Context context, long dropId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, dropId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.attentionPool(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(context, false) {
            @Override
            protected void _onNext(Object entity) {

            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
    

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_container_like:

                break;
        }
    }




}
