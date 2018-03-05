package com.netease.nim.uikit.session.viewholder;

import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.session.attachment.BusinessCardAttachment;

/**
 * Created by dengxiaolei on 2017/5/18.
 */

public class MsgViewHolderBusinessCard extends MsgViewHolderBase {

    private BusinessCardAttachment attachment;

    public MsgViewHolderBusinessCard(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.msg_item_business_card_left;
    }

    @Override
    protected void inflateContentView() {
    }


    @Override
    protected void bindContentView() {
        attachment = (BusinessCardAttachment) message.getAttachment();

        long account = attachment.getCardUid();
        String name = attachment.getCardName();
        String avatar = attachment.getCardAvatar();
        int sex = attachment.getSex();

        ImageView imgHead = findViewById(R.id.iv_head);
        ImageView ivSexFlag = findViewById(R.id.iv_sex_flag);
        TextView tvName = findViewById(R.id.tv_name);
        TextView tvBusiness = findViewById(R.id.tv_business);

        CardView cardView = findViewById(R.id.cv_card);

        if (TextUtils.isEmpty(name)) {
            tvName.setText(String.valueOf(account));
        } else {
            tvName.setText(name);
        }

        if (sex == 1) {
            ivSexFlag.setImageResource(R.drawable.icon_grxx_xb_1);
            ivSexFlag.setVisibility(View.VISIBLE);
        } else if (sex == 2){
            ivSexFlag.setImageResource(R.drawable.icon_grxx_xb_2);
            ivSexFlag.setVisibility(View.VISIBLE);
        }


     /*   if (!TextUtils.isEmpty(cardType)) {
            tvBusiness.setText(cardType);
        }*/

        GlideUtil.showImageView(context, R.drawable.icon_default_head_60dp, avatar, imgHead);

        if (NimUIKit.getSessionListener() != null) {
            View.OnClickListener cardListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NimUIKit.getSessionListener().onSessionContentClicked(context, message);
                }
            };
            cardView.setOnClickListener(cardListener);
        }
        cardView.setOnLongClickListener(longClickListener);
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
