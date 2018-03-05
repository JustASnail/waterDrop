package com.drops.waterdrop.widget.longdrawer;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.util.GlideUtil;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/03 02:33
 */

public class AvatarItemView extends LinearLayout {

    private ImageView mAvatar;
    private TextView mName;

    public AvatarItemView(Context context) {
        super(context);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        LayoutInflater.from(context).inflate(R.layout.view_avatar_item, this, true);

        mAvatar = (ImageView) findViewById(R.id.vai_avatar);
        mName = (TextView) findViewById(R.id.vai_name);
    }

    public void setName(String name){
        mName.setText(name);
    }

    public void setAvatar(Context context, String url){
        GlideUtil.showImageViewToCircle(context, R.drawable.img_qs_50x50, url, mAvatar);
    }
}
