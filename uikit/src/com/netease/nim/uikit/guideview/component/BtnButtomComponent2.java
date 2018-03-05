package com.netease.nim.uikit.guideview.component;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.guideview.Component;


/**
 * Created by binIoter on 16/6/17.
 */
public class BtnButtomComponent2 implements Component {

    @Override
    public View getView(LayoutInflater inflater) {
        ImageView mImageView = new ImageView(inflater.getContext());
        mImageView.setImageResource(R.drawable.xsyd_2_s);
        return mImageView;
    }

    @Override
    public int getAnchor() {
        return Component.ANCHOR_BOTTOM;
    }

    @Override
    public int getFitPosition() {
        return Component.FIT_END;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return 10;
    }
}
