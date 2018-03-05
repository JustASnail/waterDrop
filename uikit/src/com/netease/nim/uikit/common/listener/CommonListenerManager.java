package com.netease.nim.uikit.common.listener;

/**
 * Created by dengxiaolei on 2017/7/21.
 */

public enum CommonListenerManager {
    Instance;

    private OnH5TipCollectionClickListener mOnH5TipCollectionClickListener;
    public void setH5TipCollectionListener(OnH5TipCollectionClickListener listener){
        mOnH5TipCollectionClickListener = listener;
    }

    public OnH5TipCollectionClickListener getOnH5TipCollectionClickListener() {
        return mOnH5TipCollectionClickListener;
    }
}
