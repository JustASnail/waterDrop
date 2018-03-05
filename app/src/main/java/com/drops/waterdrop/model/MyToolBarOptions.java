package com.drops.waterdrop.model;

import android.view.View;

import com.drops.waterdrop.R;

/**
 *
 * Created by hzxuwen on 2016/6/16.
 */
public class MyToolBarOptions {
    /**
     * toolbar的title资源id
     */
    public int titleId = 0;
    /**
     * toolbar的title
     */
    public String titleString;

    public int rightStringId = 0;
    public String rightString;

    /**
     * toolbar的logo资源id
     */
    public int imgRightId = 0;
    /**
     * toolbar的返回按钮资源id，默认开启的资源nim_actionbar_dark_back_icon
     */
    public int navigateId = R.mipmap.icon_back_blue;
    /**
     * toolbar的返回按钮，默认开启
     */
    public boolean isNeedNavigate = false;

    public View.OnClickListener onNavigateClickListener;
}
