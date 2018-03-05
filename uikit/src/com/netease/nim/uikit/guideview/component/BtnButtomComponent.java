package com.netease.nim.uikit.guideview.component;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.guideview.Component;


/**
 * Created by binIoter on 16/6/17.
 */
public class BtnButtomComponent implements Component {

    @Override
    public View getView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.layout_cai_niao_guide1, null);
        ViewGroup.LayoutParams param =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        inflate.setLayoutParams(param);
        return inflate;
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
