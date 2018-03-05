package com.netease.nim.uikit.common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;

/**
 * 简单的带有输入框的对话框
 * <p>
 * Created by huangjun on 2015/5/28.股灾
 */
public class EditDialog extends Dialog {

    private TextView mTitleTextView;


    private TextView mLeftBtn;
    private TextView mRightBtn;

    private TextView mTvContent;

    private int mResourceId;

    private View.OnClickListener mLeftBtnListener;

    private View.OnClickListener mRightBtnListener;

    private String mTitle;
    private String mCardName;
    private String mReceiverName;
    private String mReceiverUrl;
    private String mReceiverAccount;

    private String mLeftBtnStr = "确定";

    private String mRightBtnStr = "取消";


    private String mEditHint;

    private int mMaxEditTextLength;

    private int mMaxLines = 0;

    private boolean mSingleLine = false;

    private boolean mShowEditTextLength = false;

    private int inputType = -1;
    private HeadImageView mIvAvatar;
    private TextView mTvName;

    public EditDialog(Context context, int resourceId, int style) {
        super(context, style);
        mMaxEditTextLength = 16;
        if (-1 != resourceId) {
            setContentView(resourceId);
            this.mResourceId = resourceId;
        }
        LayoutParams Params = getWindow().getAttributes();
        Params.width = LayoutParams.MATCH_PARENT;
        Params.height = LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(Params);
    }

    public EditDialog(Context context, int style) {
        this(context, -1, style);
        mResourceId = R.layout.alert_dialog_with_edit_text;
    }

    public EditDialog(Context context, String cardName, String receiverName, String receiverAvatar) {
        this(context, R.style.sdk_share_dialog);
        mResourceId = R.layout.alert_dialog_with_edit_text;
        this.mReceiverName = receiverName;
        this.mCardName = cardName;
        this.mReceiverUrl = receiverAvatar;
    }


    public void setLeftText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.mLeftBtnStr = text;
            if (null != mLeftBtn) {
                mLeftBtn.setText(text);
            }
        }
    }

    public void setRightText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.mRightBtnStr = text;
            if (null != mRightBtn) {
                mRightBtn.setText(text);
            }
        }
    }

    public void setInputType(int type) {
        this.inputType = type;
    }


    public void addLeftButtonListener(View.OnClickListener mLeftBtnListener) {
        this.mLeftBtnListener = mLeftBtnListener;
    }

    public void addRightButtonListener(View.OnClickListener rightBtnListener) {
        this.mRightBtnListener = rightBtnListener;
    }


    public int getResourceId() {
        return mResourceId;
    }

    public void setResourceId(int resourceId) {
        this.mResourceId = resourceId;
    }

    public TextView getLeftBtn() {
        return mLeftBtn;
    }

    public TextView getRightBtn() {
        return mRightBtn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mResourceId);

        try {
            /*RelativeLayout root = (RelativeLayout) findViewById(R.id.easy_edit_dialog_root);
            ViewGroup.LayoutParams params = root.getLayoutParams();
            params.width = (int) ScreenUtil.getDialogWidth();
            root.setLayoutParams(params);*/

            if (mTitle != null) {
                mTitleTextView = (TextView) findViewById(R.id.tv_title);
                mTitleTextView.setText(mTitle);
            }


            mTvContent = (TextView) findViewById(R.id.et_content);
            mTvName = (TextView) findViewById(R.id.tv_name);
            if (mCardName != null) {
                mTvContent.setText( mCardName);
            }

            mTvName.setText(mReceiverName);
            mIvAvatar = (HeadImageView) findViewById(R.id.iv_avatar);

                GlideUtil.showImageViewToCircle(getContext(), R.drawable.icon_default_head_60dp, mReceiverUrl, mIvAvatar);


            mLeftBtn = (TextView) findViewById(R.id.tv_left);
            if (!TextUtils.isEmpty(mLeftBtnStr)) {
                mLeftBtn.setText(mLeftBtnStr);
            }
            mLeftBtn.setOnClickListener(mLeftBtnListener);

            mRightBtn = (TextView) findViewById(R.id.tv_right);
            if (!TextUtils.isEmpty(mRightBtnStr)) {
                mRightBtn.setText(mRightBtnStr);
            }
            mRightBtn.setOnClickListener(mRightBtnListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
