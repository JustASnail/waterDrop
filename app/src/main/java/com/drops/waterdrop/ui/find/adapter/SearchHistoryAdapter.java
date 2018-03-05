package com.drops.waterdrop.ui.find.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.gen.SearchPoolHistoryDBDao;
import com.netease.nim.uikit.gen.SearchPostHistoryDBDao;
import com.netease.nim.uikit.green_dao.GreenDaoManager;
import com.netease.nim.uikit.model.SearchHistoryEntity;

import java.util.ArrayList;

/**
 * Created by dengxiaolei on 2017/6/28.
 */

public class SearchHistoryAdapter extends BaseQuickAdapter<SearchHistoryEntity, BaseViewHolder> {

    private int mMode;

    public SearchHistoryAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_search_history);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SearchHistoryEntity item) {
        ImageView ivAvatar = helper.getView(R.id.iv_avatar);
        helper.setText(R.id.tv_name, item.getName());
        ImageView ivDelete = helper.getView(R.id.iv_delete);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMode == Constants.POOL_SEARCH_MODE) {
                    SearchPoolHistoryDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSearchPoolHistoryDBDao();
                    dbDao.queryBuilder().where(SearchPoolHistoryDBDao.Properties.Name.eq(item.getName())).buildDelete().executeDeleteWithoutDetachingEntities();
                } else {
                    SearchPostHistoryDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSearchPostHistoryDBDao();
                    dbDao.queryBuilder().where(SearchPostHistoryDBDao.Properties.Name.eq(item.getName())).buildDelete().executeDeleteWithoutDetachingEntities();
                }
                remove(helper.getAdapterPosition());
                notifyDataSetChanged();

            }
        });


    }

    public void setMode(int mode) {
        mMode = mode;
    }

    public void removeAll() {
        ArrayList<SearchHistoryEntity> list = new ArrayList<>();
        setNewData(list);
    }
}
