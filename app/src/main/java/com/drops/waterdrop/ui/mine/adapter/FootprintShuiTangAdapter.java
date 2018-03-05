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
import com.netease.nim.uikit.model.FootprintShuiTangEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/3.
 */

public class FootprintShuiTangAdapter extends BaseQuickAdapter<FootprintShuiTangEntity.ResultsBean, BaseViewHolder> {
    public FootprintShuiTangAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_footprint);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FootprintShuiTangEntity.ResultsBean item) {
        ImageView ivCover = helper.getView(R.id.iv_cover);
        TextView tvTitle = helper.getView(R.id.tv_top);
        TextView tvContent = helper.getView(R.id.tv_mid);
        TextView tvTime = helper.getView(R.id.tv_time);
        ImageView ivDelete = helper.getView(R.id.iv_delete);

        final FootprintShuiTangEntity.ResultsBean.DropBean drop = item.getDrop();
        if (drop != null) {
            GlideUtil.showImageView(mContext, R.drawable.img_qs_108x70, drop.getHeadImg(), ivCover);
            tvTitle.setText(drop.getDropName());
            tvContent.setText(drop.getDropDesc());
            tvTime.setText(drop.getCreateTime());
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteShuiTang(drop.getDropId(), helper.getPosition());
                }
            });
        }
    }

    private void deleteShuiTang(long dropId, final int pos) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, dropId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.deleteFootprintShuiTang(RequestBodyUtils.build(map));
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


    public interface onUpdateListener {
        void onUpdate(int pos);
    }

    private onUpdateListener mOnUpdateListener;

    public void setOnUpdateListener(onUpdateListener onUpdateListener) {
        mOnUpdateListener = onUpdateListener;
    }

}
