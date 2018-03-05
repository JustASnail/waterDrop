package com.drops.waterdrop.ui.find.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.mine.activity.UserProfileActivity;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.sys.UIUtils;
import com.google.gson.Gson;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.CollectItemHolder;
import com.netease.nim.uikit.model.PostListEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/7/3.
 */

public class ShuiTieRecommendListAdapter extends BaseQuickAdapter<PostListEntity.ResultsBean, BaseViewHolder> {

    private static final int COLLECT_COLLAPSE = 0;
    private static final int COLLECT_EXTEND = 1;
    private int useEnableWidth;
    private int collectMenuItemSide;
    private int collectMenuItemDivider;
    private int collectMenuTextSize;
    private int collectMenuTextColor;
    private Drawable collectMenuDrawable;
    private int collectMenuDrawablePadding;
    private int collectExtendItemWidth;

    public ShuiTieRecommendListAdapter(Context context) {
        super(R.layout.item_recommend_shui_tie);

        collectExtendItemWidth = UIUtils.dip2Px(40);
        useEnableWidth = UIUtils.getDisplayWidth() - UIUtils.dip2Px(16) * 2 - UIUtils.dip2Px(10) * 2 - UIUtils.dip2Px(30);
        collectMenuItemSide = UIUtils.dip2Px(15);
        collectMenuItemDivider = UIUtils.dip2Px(3);
        collectMenuTextSize = UIUtils.sp2px(12);
        collectMenuTextColor = UIUtils.getColor(R.color.color_grey_666666);
        collectMenuDrawable = UIUtils.getDrawable(context, R.mipmap.icon_sy_stxl);
        collectMenuDrawablePadding = UIUtils.dip2Px(2);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PostListEntity.ResultsBean item) {
        ImageView ivImg = helper.getView(R.id.iv_img);
        HeadImageView ivAvatar = helper.getView(R.id.iv_avatar);
        final ImageView ivLike = helper.getView(R.id.iv_like);

        GlideUtil.showImageView(mContext, R.drawable.img_qs_375x207, item.getCover(), ivImg);
        String creatorPhoto = "";
        if (item.getCreator() != null) {
            creatorPhoto = item.getCreator().getPhoto();
        }
        GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_50x50, creatorPhoto, ivAvatar);
        helper.setText(R.id.tv_date, item.getCreateTimeShow());
        helper.setText(R.id.tv_name, item.getTipTitle());
        helper.setText(R.id.tv_desc, item.getTipContent());
        helper.setText(R.id.tv_access_num, NumberUtil.Instance.formatNumber(item.getBrowserNum()));

        if (item.getCollectStatus() == 1) {
            ivLike.setImageResource(R.mipmap.btn_tjst_star_2);
        } else {
            ivLike.setImageResource(R.mipmap.btn_tjst_star_1);
        }

        helper.setOnClickListener(R.id.rl_container_like, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getCollectStatus() == 1) {
                    //取消收藏
                    item.setCollectStatus(0);
                    notifyDataSetChanged();
                    deleteCollectTip(item.getTipId());
                } else {
//                    ivLike.setImageResource(R.mipmap.btn_tjst_star_1);
                    //收藏
                    item.setCollectStatus(1);
                    notifyDataSetChanged();
                    collectTip(item.getTipId());

                }
            }
        });


        final LinearLayout mCollectMenu = helper.getView(R.id.iv_collect_menu);
        final LinearLayout mCollectExtend = helper.getView(R.id.iv_collect_extend);
        final RecyclerView mCollectExtendRV = helper.getView(R.id.iv_collect_extend_rv);
        HeadImageView mCollectExtendImg = helper.getView(R.id.iv_collect_extend_menu);


        List<CollectItemHolder> childs;
        final List<PostListEntity.ResultsBean.CreatorBean> beanList = item.getCollectFriends();
        if (beanList != null && beanList.size() > 0) {
            mCollectMenu.removeAllViews();

            childs = item.getCollectMenuViews();
            if (childs == null) {
                childs = new ArrayList<>();
                int size = beanList.size();
                for (int i = 0; i < size; i++) {
                    childs.add(new CollectMenuItemHolder(beanList.get(i)));
                    if (i >= 3) {
                        break;
                    }
                }
                childs.add(new CollectMenuTextItemHolder(beanList.size()));
                item.setCollectMenuViews(childs);
            }
            helper.setVisible(R.id.iv_collect_friend, true);
        } else {
//            mCollectMenu.removeAllViews();
//            childs = item.getCollectMenuViews();
//            if (childs == null){
//                childs = new ArrayList<>();
//                childs.add(new CollectMenuTextItemHolder(0));
//                item.setCollectMenuViews(childs);
//            }
            helper.setVisible(R.id.iv_collect_friend, false);
            return;
        }
        for (CollectItemHolder holder : childs) {
            View view = holder.getView();
            if (view.getParent() == null) {
                mCollectMenu.addView(holder.getView());
            }
        }

        if (item.getCollectButtonState() == COLLECT_EXTEND) {
            mCollectMenu.performClick();
        } else {
            mCollectExtendImg.performClick();
        }

        mCollectMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = beanList.size();
                if (size == 0) {
                    return;
                }

                item.setCollectButtonState(COLLECT_EXTEND);

                int rvWidth = size * collectExtendItemWidth;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mCollectExtendRV.getLayoutParams();
                layoutParams.width = Math.min(useEnableWidth, rvWidth);

                ShuiTieCollectExtendAdapter adapter = new ShuiTieCollectExtendAdapter(beanList);
                mCollectExtendRV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true));
                mCollectExtendRV.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        UserProfileActivity.start(mContext, beanList.get(position).getUid());
                    }
                });


                mCollectMenu.setVisibility(View.INVISIBLE);
                mCollectExtend.setVisibility(View.VISIBLE);
            }
        });
        mCollectExtendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setCollectButtonState(COLLECT_COLLAPSE);
                mCollectMenu.setVisibility(View.VISIBLE);
                mCollectExtend.setVisibility(View.GONE);
            }
        });
    }

    private class CollectMenuTextItemHolder extends CollectItemHolder {
        private TextView tv;
        private int collectState;

        public CollectMenuTextItemHolder(int collectState) {
            super(null);
            this.collectState = collectState;
        }

        @Override
        public View getView() {
            if (tv == null) {
                tv = new TextView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(layoutParams);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, collectMenuTextSize);
                tv.setTextColor(collectMenuTextColor);
                collectMenuDrawable.setBounds(0, 0, collectMenuDrawable.getMinimumWidth(), collectMenuDrawable.getMinimumHeight());
                tv.setCompoundDrawables(null, null, collectMenuDrawable, null);
                tv.setCompoundDrawablePadding(collectMenuDrawablePadding);

                String content = UIUtils.stringFormat(R.string.tips_collect_num, collectState);
                tv.setText(Html.fromHtml(content));
            }
            return tv;
        }
    }

    private class CollectMenuItemHolder extends CollectItemHolder {
        private HeadImageView img;

        public CollectMenuItemHolder(PostListEntity.ResultsBean.CreatorBean bean) {
            super(bean);
        }

        @Override
        public View getView() {
            if (img == null) {
                img = new HeadImageView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(collectMenuItemSide, collectMenuItemSide);
                layoutParams.rightMargin = collectMenuItemDivider;
                img.setLayoutParams(layoutParams);
                GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_50x50, bean.getPhoto(), img);
            }
            return img;
        }


    }

    private void collectTip(long tipId) {
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

    private void deleteCollectTip(long tipId) {

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
}
