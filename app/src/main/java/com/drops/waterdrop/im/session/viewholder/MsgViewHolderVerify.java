package com.drops.waterdrop.im.session.viewholder;

import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.netease.nim.uikit.session.attachment.VerifyContentAttachment;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.session.emoji.MoonUtil;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;

/**
 * Created by dengxiaolei on 2017/5/18.
 */

public class MsgViewHolderVerify extends MsgViewHolderBase {

    private VerifyContentAttachment attachment;

    public MsgViewHolderVerify(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_verify;
    }

    @Override
    protected void inflateContentView() {
    }

    @Override
    protected void bindContentView() {
        attachment = (VerifyContentAttachment) message.getAttachment();
        layoutDirection();
        TextView bodyTextView = findViewById(com.netease.nim.uikit.R.id.nim_message_item_text_body);

        bodyTextView.setTextColor(isReceivedMessage() ? Color.BLACK : Color.WHITE);
        bodyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick();
            }
        });
        MoonUtil.identifyFaceExpression(NimUIKit.getContext(), bodyTextView, getDisplayText(), ImageSpan.ALIGN_BOTTOM);
        bodyTextView.setMovementMethod(LinkMovementMethod.getInstance());
        bodyTextView.setOnLongClickListener(longClickListener);

        bottomMarkTextView.setText("以上为打招呼的内容");
        bottomMarkTextView.setVisibility(View.VISIBLE);
    }

    private void layoutDirection() {
        TextView bodyTextView = findViewById(com.netease.nim.uikit.R.id.nim_message_item_text_body);
        if (isReceivedMessage()) {
            bodyTextView.setBackgroundResource(com.netease.nim.uikit.R.drawable.nim_message_item_left_selector);
            bodyTextView.setPadding(ScreenUtil.dip2px(15), ScreenUtil.dip2px(8), ScreenUtil.dip2px(10), ScreenUtil.dip2px(8));
        } else {
            bodyTextView.setBackgroundResource(com.netease.nim.uikit.R.drawable.nim_message_item_right_selector);
            bodyTextView.setPadding(ScreenUtil.dip2px(10), ScreenUtil.dip2px(8), ScreenUtil.dip2px(15), ScreenUtil.dip2px(8));
        }
    }

    protected String getDisplayText() {
        return attachment.getVerify();
    }

}
