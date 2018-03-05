package com.drops.waterdrop.ui.activ.presenter;

import android.os.Bundle;

import com.drops.waterdrop.model.ActiveEntity;
import com.drops.waterdrop.ui.activ.fragment.ActiveListFragment;
import com.drops.waterdrop.ui.activ.view.IActiveListView;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.pool.activity.MyQiangPiaoDetailsActivity;
import com.drops.waterdrop.ui.pool.activity.NotStartActiveActivity;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/9.
 */

public class ActiveListPresenter extends BasePresenter<IActiveListView> {

    public static final int TYPE_NOT_START = 1;
    public static final int TYPE_STARTED = 2;
    public static final int TYPE_END = 3;

    public int mPageType = -1;
    public ActiveListPresenter(BaseActivity context) {
        super(context);
    }

    public List<ActiveEntity> getData() {
        return null;
    }



    public void setPageType(Bundle arguments) {
        if (arguments != null) {
            mPageType =  arguments.getInt(ActiveListFragment.ARG_TYPE);
        }
    }

    public void onItemClick(int position) {
        switch (mPageType) {
            case TYPE_NOT_START:
                NotStartActiveActivity.start(mContext);
                break;
            case TYPE_STARTED:
                MyQiangPiaoDetailsActivity.start(mContext, 4);
                break;

        }
    }
}
