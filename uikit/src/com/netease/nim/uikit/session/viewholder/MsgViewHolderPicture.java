package com.netease.nim.uikit.session.viewholder;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderPicture extends MsgViewHolderThumbBase {

    public MsgViewHolderPicture(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_picture;
    }

    @Override
    protected void onItemClick() {
        if (NimUIKit.getSessionImgItemClickListener() != null) {
            NimUIKit.getSessionImgItemClickListener().onImgClickListener(context, message);
        }
    }

    @Override
    protected String thumbFromSourceFile(String path) {
        return path;
    }
}
