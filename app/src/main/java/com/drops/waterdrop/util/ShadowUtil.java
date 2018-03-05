package com.drops.waterdrop.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.drops.waterdrop.R;
import com.drops.waterdrop.widget.ShadowProperty;
import com.drops.waterdrop.widget.ShadowViewDrawable;

import java.lang.reflect.Field;

/**
 * Created by Mr.Smile on 2017/7/21.
 */

public class ShadowUtil {
    public static void shadow(Context context, View view) {
        ShadowProperty shadowProperty = new ShadowProperty();
        shadowProperty
                .setShadowColor(context.getResources().getColor(R.color.test))
//                .setShadowDx(dip2px(this, 20f))
                .setShadowDy(dip2px(context, 0.4f))
                .setShadowRadius(dip2px(context, 5))
                .setShadowSide(ShadowProperty.BOTTOM);
        ShadowViewDrawable shadowViewDrawable = new ShadowViewDrawable(shadowProperty, Color.WHITE, 0, 0);
        ViewCompat.setBackground(view, shadowViewDrawable);
        ViewCompat.setLayerType(view, ViewCompat.LAYER_TYPE_SOFTWARE, null);
    }

    public static void shadowLeft(Context context, View view) {
        ShadowProperty shadowProperty = new ShadowProperty();
        shadowProperty
                .setShadowColor(context.getResources().getColor(R.color.colorPrimary))
//                .setShadowDx(dip2px(context, 0.4f))
//                .setShadowDy(dip2px(context, 0.4f))
                .setShadowRadius(dip2px(context, 5))
                .setShadowSide(ShadowProperty.LEFT);
        ShadowViewDrawable shadowViewDrawable = new ShadowViewDrawable(shadowProperty, Color.parseColor("#03B1FF"), 0, 0);
        ViewCompat.setBackground(view, shadowViewDrawable);
        ViewCompat.setLayerType(view, ViewCompat.LAYER_TYPE_SOFTWARE, null);
    }

    public static int dip2px(Context context, float dpValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } catch (Throwable throwable) {
            // igonre
        }
        return 0;
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
