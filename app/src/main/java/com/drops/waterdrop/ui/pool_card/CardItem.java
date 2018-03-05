/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.drops.waterdrop.ui.pool_card;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.util.NumberUtil;
import com.netease.nim.uikit.event.PoolAttentionEvent;
import com.netease.nim.uikit.model.RecommendPoolListEntity;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * description
 * <p>card滑动条目
 * Created by dengxiaolei on 2017/6/30.
 */
public class CardItem<T> extends BaseCardItem<T> {

    private T mData;
    private int mPosition;

    private ImageView mIvLikeIcon;
    private TextView mTvNumber;

    private long dropId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mHandler == null) {
            throw new RuntimeException("please bind the handler !");
        }
        EventBus.getDefault().register(this);

        View view = mHandler.onBind(mContext, mData, mPosition);
        mIvLikeIcon = (ImageView) view.findViewById(R.id.iv_like);
        mTvNumber = (TextView) view.findViewById(R.id.tv_number);

        return view;
    }

    public void bindData(T data, int position) {
        mData = data;
        mPosition = position;
        if (mData instanceof RecommendPoolListEntity.ResultsBean) {
            dropId =  ((RecommendPoolListEntity.ResultsBean) mData).getDropId();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //当收到添加好友系统通知时 拉取本地消息更新列表
    public void onPoolAttentionEvent(PoolAttentionEvent event) {
        Logger.d("关====注了：" + event.isAttention());
        if (event.getDropId() == dropId) {
            if (mIvLikeIcon != null && mTvNumber != null) {
                mIvLikeIcon.setImageResource(event.isAttention() ? R.mipmap.icon_st_sc :  R.mipmap.icon_st_wsc);
                mTvNumber.setText(NumberUtil.Instance.formatNumber(event.getAttentionNum()));

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
