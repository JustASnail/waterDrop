package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.StarListEntity;

/**
 * Created by dengxiaolei on 2017/5/24.
 */

public class PoolListAdapter extends BaseQuickAdapter<StarListEntity.FriendsBean, BaseViewHolder> {

    public static final String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498563998591&di=f776d6fe25db568c0095f4ad101ebfe1&imgtype=0&src=http%3A%2F%2Fpic.nipic.com%2F2008-07-23%2F200872316555565_2.jpg";
    public static final String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498564099133&di=085209ebe057b1eaaadc4714c99b1437&imgtype=0&src=http%3A%2F%2Fimage.uczzd.cn%2F381176148778536826.jpeg%3Fid%3D0%26from%3Dexport";

    public PoolListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_pool_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, StarListEntity.FriendsBean item) {

    }


}
