package com.drops.waterdrop.ui.find.view;

import android.content.Intent;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/5/27.
 */

public interface IWatchPicView {
    Intent getDataIntent();

    void setImageBrowse(List<String> images, int position);
}
