package com.drops.waterdrop.ui.mine.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.MyPostEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/3.
 */

public class MyPostListAdapter extends BaseQuickAdapter<MyPostEntity.ResultsBean,BaseViewHolder>{

    public MyPostListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_collection_st);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyPostEntity.ResultsBean item) {
        RelativeLayout rlCover = helper.getView(R.id.rl_cover);
        final RelativeLayout ivCheck = helper.getView(R.id.iv_check);
        final RelativeLayout tvUnselect = helper.getView(R.id.tv_unselect);
        ImageView ivPrivate = helper.getView(R.id.iv_private);
        rlCover.setVisibility(View.GONE);
        ivCheck.setVisibility(View.GONE);
        tvUnselect.setVisibility(View.GONE);
        ivPrivate.setVisibility(View.GONE);
        //以上用于隐私设置
        ImageView ivImg = helper.getView(R.id.iv_img);
        HeadImageView ivAvatar = helper.getView(R.id.iv_avatar);
        ImageView ivDelete = helper.getView(R.id.iv_delete);
        TextView tvTime = helper.getView(R.id.tv_date);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvDesc = helper.getView(R.id.tv_desc);

        GlideUtil.showImageView(mContext,R.drawable.img_qs_375x207, item.getCover(), ivImg);
        MyPostEntity.ResultsBean.CreatorBean creator = item.getCreator();
        if (creator != null) {
            GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_50x50, creator.getPhoto(), ivAvatar);
        }
        tvTime.setText(item.getCreateTime());
        tvDesc.setText(item.getTipContent());
        tvName.setText(item.getTipTitle());

        //删除
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCollection(item.getTipId());
            }
        });
    }

    private void deleteCollection(Long tipId) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.tip_id, tipId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.deleteMyPost(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object dataBean) {
                ToastUtil.showShort("删除成功");
                notifyDataSetChanged();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}
