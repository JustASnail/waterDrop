package com.drops.waterdrop.ui.mine.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.FootprintShuiTieEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/3.
 */

public class FootprintShuiTieAdapter extends BaseQuickAdapter<FootprintShuiTieEntity.ResultsBean, BaseViewHolder> {
    public FootprintShuiTieAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_footprint);
    }

    @Override
    protected void convert(final BaseViewHolder helper, FootprintShuiTieEntity.ResultsBean item) {
        ImageView ivCover = helper.getView(R.id.iv_cover);
        TextView tvTitle = helper.getView(R.id.tv_top);
        TextView tvContent = helper.getView(R.id.tv_mid);
        TextView tvTime = helper.getView(R.id.tv_time);
        ImageView ivDelete = helper.getView(R.id.iv_delete);

        final FootprintShuiTieEntity.ResultsBean.DropTipBean dropTip =  item.getDropTip();
        GlideUtil.showImageView(mContext, R.drawable.img_qs_108x70,dropTip.getCover(), ivCover);
        tvTitle.setText(dropTip.getTipTitle());
        tvContent.setText(dropTip.getTipContent());
        tvTime.setText(dropTip.getCreateTime());
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteShuiTie(dropTip.getTipId(), helper.getPosition());
            }
        });
    }

    private void deleteShuiTie(long tipId, final int pos) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.tip_id, tipId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.deleteFootprintShuiTie(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object dataBean) {
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onUpdate(pos);
                }
                ToastUtil.showShort("删除成功");
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private FootprintShuiTangAdapter.onUpdateListener mOnUpdateListener;

    public void setOnUpdateListener(FootprintShuiTangAdapter.onUpdateListener onUpdateListener) {
        mOnUpdateListener = onUpdateListener;
    }

}
