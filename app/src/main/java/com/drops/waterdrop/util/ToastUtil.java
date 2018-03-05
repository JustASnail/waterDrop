package com.drops.waterdrop.util;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;


/**
 * Created by llf on 2017/2/27.
 * Toast工具
 */

public class ToastUtil {
    private static Toast mToast;
    public static ToastUtil sToastUtil = new ToastUtil();

    private static Toast mCustomToast;
    private static TextView mTvContent;
    private static TextView mTvDesc;

    public ToastUtil() {

    }

    /**
     * 短时间显示Toast
     */
    public static void showShort(CharSequence message) {
        if (mToast == null) {
            mToast = Toast.makeText(WaterDropApp.sContext, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(CharSequence message) {
        if (mToast == null) {
            mToast = Toast.makeText(WaterDropApp.sContext, message, Toast.LENGTH_LONG);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param duration
     */
    public static void showIndefinite(String msg, int duration) {
        if (mToast == null) {
            mToast = new Toast(WaterDropApp.sContext);
        }

        mToast.setDuration(duration);
        mToast.setText(msg);

    }

    /**
     * 设置显示位置
     *
     * @param gravity
     * @return
     */
    public ToastUtil setGravity(int gravity) {
        if (mToast != null) {
            mToast.setGravity(gravity, 0, 0);
        }
        return this;
    }

    /**
     * 设置Toast字体及背景颜色
     *
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public ToastUtil setToastColor(int messageColor, int backgroundColor) {
        if (mToast != null) {
            View view = mToast.getView();
            TextView message = ((TextView) view.findViewById(android.R.id.message));
            message.setBackgroundColor(backgroundColor);
            message.setTextColor(messageColor);
        }
        return this;
    }

    /**
     * 设置Toast字体及背景
     *
     * @param messageColor
     * @param background
     * @return
     */
    public ToastUtil setToastBackground(int messageColor, int background) {
        if (mToast != null) {
            View view = mToast.getView();
            TextView message = ((TextView) view.findViewById(android.R.id.message));
            message.setBackgroundResource(background);
            message.setPadding(30, 30, 30, 30);
            view.setBackgroundColor(Color.TRANSPARENT);
            message.setTextColor(messageColor);
        }
        return this;
    }


    /**
     * 显示Toast
     *
     * @return
     */
    public ToastUtil show() {
        mToast.show();
        return this;
    }

    /**
     * 完全自定义布局Toast
     *
     *
     */
    public static void showCustomToast(String content, String desc) {
        if (mCustomToast == null) {
            mCustomToast = new Toast(WaterDropApp.sContext);
            mCustomToast.setGravity(Gravity.CENTER, 0, 0);
            View inflate = View.inflate(WaterDropApp.sContext, R.layout.toast_custom_layout, null);
            mTvContent = (TextView) inflate.findViewById(R.id.tv_toast_content);
            mTvDesc = (TextView) inflate.findViewById(R.id.tv_toast_desc);
            mCustomToast.setView(inflate);
        }
        if (mTvContent != null) {
            mTvContent.setText(content);
        }
        if (mTvDesc != null) {
            mTvDesc.setText(desc);
        }


        mCustomToast.setDuration(Toast.LENGTH_LONG);
        mCustomToast.show();

    }
}
