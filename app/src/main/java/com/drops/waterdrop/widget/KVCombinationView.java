package com.drops.waterdrop.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/30 00:34
 */

public class KVCombinationView extends LinearLayout {

    private TextView mName;
    private TextView mValue;
    private EditText mValueEdit;
    private ImageView mArrow;
    private ImageView mRightImage;

    private boolean isEditable;
    private Resources resources;

    public KVCombinationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        resources = getResources();

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        LayoutInflater.from(context).inflate(R.layout.view_kv_combination, this, true);
        mName = (TextView) findViewById(R.id.vkdc_name);
        mValue = (TextView) findViewById(R.id.vkdc_value);
        mValueEdit = (EditText) findViewById(R.id.vkdc_value_edit);
        mArrow = (ImageView) findViewById(R.id.vkdc_arrow);
        mRightImage = (ImageView) findViewById(R.id.vkdc_right_image);

        Resources res = context.getResources();
        int defaultNameColor = res.getColor(R.color.color_grey_666666);
        int defaultValueColor = res.getColor(R.color.color_grey_555555);
        int defaultHintColor = res.getColor(R.color.color_grey_888888);

        TypedArray t = context.getTheme().obtainStyledAttributes(attrs, R.styleable.KVCombinationView, 0, 0);
        mName.setText(t.getString(R.styleable.KVCombinationView_kvName));
        mName.setTextColor(t.getColor(R.styleable.KVCombinationView_kvNameColor, defaultNameColor));

        boolean isShowArrow = t.getBoolean(R.styleable.KVCombinationView_kvArrow, false);
        mArrow.setVisibility(isShowArrow ? VISIBLE : GONE);

        isEditable = t.getBoolean(R.styleable.KVCombinationView_kvValueEditable, false);
        if (isEditable){
            mValueEdit.setVisibility(VISIBLE);
            mValue.setVisibility(GONE);

            mValueEdit.setHint(t.getString(R.styleable.KVCombinationView_kvValueHint));
            mValueEdit.setHintTextColor(t.getColor(R.styleable.KVCombinationView_kvValueHintColor, defaultHintColor));
            mValueEdit.setText(t.getString(R.styleable.KVCombinationView_kvValue));
            mValueEdit.setTextColor(t.getColor(R.styleable.KVCombinationView_kvValueColor, defaultValueColor));
            int gravity = t.getInt(R.styleable.KVCombinationView_kvValueGravity, 0);
            if (gravity != 0)
                mValueEdit.setGravity(gravity);

            int inputType = t.getInt(R.styleable.KVCombinationView_kvInputType, 0);
            if (inputType != 0) {
                mValueEdit.setInputType(inputType);
            }
        } else {
            mValueEdit.setVisibility(GONE);
            mValue.setVisibility(VISIBLE);

            setBackgroundResource(R.drawable.touch_bg);

            mValue.setHint(t.getString(R.styleable.KVCombinationView_kvValueHint));
            mValue.setHintTextColor(t.getColor(R.styleable.KVCombinationView_kvValueHintColor, defaultHintColor));
            mValue.setText(t.getString(R.styleable.KVCombinationView_kvValue));
            mValue.setTextColor(t.getColor(R.styleable.KVCombinationView_kvValueColor, defaultValueColor));
            int gravity = t.getInt(R.styleable.KVCombinationView_kvValueGravity, 1);
            if (gravity != 1)
                mValue.setGravity(gravity);
        }

        boolean isShowRightImage = t.getBoolean(R.styleable.KVCombinationView_kvHasRightImage, false);
        mRightImage.setVisibility(isShowRightImage ? VISIBLE : GONE);

        int rightImageWidth = t.getDimensionPixelSize(R.styleable.KVCombinationView_kvRightImageWidth, 0);
        int rightImageHeight = t.getDimensionPixelSize(R.styleable.KVCombinationView_kvRightImageHeight, 0);
        if (rightImageWidth != 0 && rightImageHeight != 0){
            ViewGroup.LayoutParams layoutParams = mRightImage.getLayoutParams();
            layoutParams.width = rightImageWidth;
            layoutParams.height = rightImageHeight;
        }

        Drawable rightImageDrawable = t.getDrawable(R.styleable.KVCombinationView_kvRightImage);
        if (rightImageDrawable != null){
            mRightImage.setImageDrawable(rightImageDrawable);
        }

        Drawable background = t.getDrawable(R.styleable.KVCombinationView_kvBackground);
        if (background != null){
            setBackground(background);
        }

        t.recycle();
    }

    public EditText getValueEditText(){
        if (isEditable)
            return mValueEdit;
        return null;
    }

    public void setValue(String value){
        if (isEditable){
            mValueEdit.setText(value);
        } else {
            mValue.setText(value);
        }
    }

    public void setValueColor(@ColorRes int rid){
        if (isEditable){
            mValueEdit.setTextColor(resources.getColor(rid));
        } else {
            mValue.setTextColor(resources.getColor(rid));
        }
    }

    public String getValue(){
        if (isEditable){
            return mValueEdit.getText().toString();
        } else {
            return mValue.getText().toString();
        }
    }

    public ImageView getRightImage(){
        if (mRightImage.getVisibility() == VISIBLE)
            return mRightImage;
        return null;
    }

    public boolean isNullValue(){
        return TextUtils.isEmpty(getValue().trim());
    }

    public TextView getmName(){
        return mName;
    }

    public void setmName(int res) {
        if (mName != null) {
            mName.setText(res);
        }
    }

    public void setHint(int res) {
        if (isEditable) {
            mValueEdit.setHint(res);
        }
    }
}
