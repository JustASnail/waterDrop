package com.netease.nim.uikit.session.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.session.attachment.WaterShareAttachment;

/**
 * Created by dengxiaolei on 2017/5/18.
 */

public class MsgViewHolderWaterShare extends MsgViewHolderBase {

    //    private BusinessCardAttachment attachment;
    private WaterShareAttachment attachment;
    private View mGoodsLayout;
    private View mPoolLayout;
    private View mTipLayout;
    private String mPhoto;
    private String mTitle;
    private String mContent;
    private String mFromName;

    public MsgViewHolderWaterShare(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.msg_item_water_share;
    }

    @Override
    protected void inflateContentView() {
    }


    @Override
    protected void bindContentView() {
        mGoodsLayout = findViewById(R.id.layout_goods);
        mPoolLayout = findViewById(R.id.layout_pool);
        mTipLayout = findViewById(R.id.layout_tip);
        attachment = (WaterShareAttachment) message.getAttachment();
        final String shareFrom = attachment.getShareFrom();

        mPhoto = attachment.getSharePhoto();
        mTitle = attachment.getShareTitle();
        mContent = attachment.getShareContent();
        mFromName = attachment.getFromName();
        if (TextUtils.equals("水塘", shareFrom)) {
            setPoolCard();
        } else if (TextUtils.equals("水帖", shareFrom)) {
            setTipCard();

        } else if (TextUtils.equals("水宝", shareFrom)) {
            setGoodsCard();

        }

//        cardView.setOnLongClickListener(longClickListener);
    }

    private void setGoodsCard() {
        mGoodsLayout.setVisibility(View.VISIBLE);
        mTipLayout.setVisibility(View.GONE);
        mPoolLayout.setVisibility(View.GONE);
        ImageView ivHead = findViewById(R.id.iv_head_goods);
        TextView tvTitle = findViewById(R.id.tv_goods_title);
        TextView tvContent = findViewById(R.id.tv_goods_content);
        TextView tvFrom = findViewById(R.id.tv_goods_from_name);

        tvTitle.setText(mTitle);
        tvContent.setText("水滴好货，等你来发现");
        GlideUtil.showImageView(context, R.drawable.img_qs_90x90, mPhoto, ivHead);
        tvFrom.setText(mFromName);

        if (NimUIKit.getSessionListener() != null) {
            View.OnClickListener cardListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NimUIKit.getSessionListener().onSessionContentClicked(context, message);
                }
            };
            mGoodsLayout.setOnClickListener(cardListener);
        }
        mGoodsLayout.setOnLongClickListener(longClickListener);
    }

    private void setTipCard() {
        mTipLayout.setVisibility(View.VISIBLE);
        mPoolLayout.setVisibility(View.GONE);
        mGoodsLayout.setVisibility(View.GONE);
        ImageView ivHead = findViewById(R.id.iv_head_tip);
        TextView tvTitle = findViewById(R.id.tv_tip_title);
        TextView tvContent = findViewById(R.id.tv_tip_content);
        TextView tvFrom = findViewById(R.id.tv_tip_from_name);

        tvContent.setText("优质水帖,最懂你的生活");
        tvTitle.setText(mTitle);
        GlideUtil.showImageView(context, R.drawable.img_qs_90x90, mPhoto, ivHead);
        tvFrom.setText(mFromName);

        if (NimUIKit.getSessionListener() != null) {
            View.OnClickListener cardListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NimUIKit.getSessionListener().onSessionContentClicked(context, message);
                }
            };
            mTipLayout.setOnClickListener(cardListener);
        }
        mTipLayout.setOnLongClickListener(longClickListener);
    }

    private void setPoolCard() {
        mPoolLayout.setVisibility(View.VISIBLE);
        mGoodsLayout.setVisibility(View.GONE);
        mTipLayout.setVisibility(View.GONE);
        ImageView ivHead = findViewById(R.id.iv_head_pool);
        TextView tvTitle = findViewById(R.id.tv_pool_title);
        TextView tvContent = findViewById(R.id.tv_pool_content);

        tvTitle.setText(mTitle);
        tvContent.setText(mContent);
        GlideUtil.showImageView(context, R.drawable.img_qs_90x90, mPhoto, ivHead);

        if (NimUIKit.getSessionListener() != null) {
            View.OnClickListener cardListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NimUIKit.getSessionListener().onSessionContentClicked(context, message);
                }
            };
            mPoolLayout.setOnClickListener(cardListener);
        }
        mPoolLayout.setOnLongClickListener(longClickListener);
    }


    @Override
    protected int leftBackground() {
        return 0;
    }

    @Override
    protected int rightBackground() {
        return 0;
    }


}
