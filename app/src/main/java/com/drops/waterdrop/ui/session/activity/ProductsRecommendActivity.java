package com.drops.waterdrop.ui.session.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.drops.waterdrop.ui.find.activity.PoolDetailPageActivity;
import com.drops.waterdrop.ui.session.adapter.ProductsRecommendAdapter;
import com.google.gson.Gson;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.event.ProductsRecommendEvent;
import com.netease.nim.uikit.gen.SystemMessageDBDao;
import com.netease.nim.uikit.green_dao.GreenDaoManager;
import com.netease.nim.uikit.green_dao.SystemMessageDB;
import com.netease.nim.uikit.model.RecentMessageListEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;

/**
 * 精品推荐
 * Created by dengxiaolei on 2017/7/12.
 */

public class ProductsRecommendActivity extends BaseActivity {


    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ProductsRecommendAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, ProductsRecommendActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "精品推荐";
        setMyToolbar(options);

        mAdapter = new ProductsRecommendAdapter(0);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        SystemMessageDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSystemMessageDBDao();
        List<SystemMessageDB> poolList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(51)).list();
        List<SystemMessageDB> tipList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(52)).list();

        ArrayList<SystemMessageDB> dbs = new ArrayList<>();
        if (poolList != null) {
            dbs.addAll(poolList);
        }
        if (tipList != null) {
            dbs.addAll(tipList);
        }

        sortRecentContacts(dbs);
        mAdapter.setNewData(dbs);
    }

    @Override
    protected void initListener() {
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        SystemMessageDB systemMessageDB = mAdapter.getData().get(position);
                        if (systemMessageDB.getType() == 51) {//水塘
                            PoolDetailPageActivity.start(ProductsRecommendActivity.this, systemMessageDB.getTargetId());
                        } else if (systemMessageDB.getType() == 52) {//水贴
                            String data = systemMessageDB.getData();
                            Gson gson = new Gson();
                            RecentMessageListEntity.ResultsBean.DataBean entity = gson.fromJson(data, RecentMessageListEntity.ResultsBean.DataBean.class);
                            CommonWebActivity.start(ProductsRecommendActivity.this, systemMessageDB.getTargetId(), entity.getTipUrl());
                        }

                        if (systemMessageDB.getUnreadTag() == 0) {
                            systemMessageDB.setUnreadTag(1);
                            SystemMessageDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSystemMessageDBDao();
                            dbDao.update(systemMessageDB);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                }
            });
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_products_recommend;
    }


    /**
     * **************************** 排序 ***********************************
     */
    private void sortRecentContacts(List<SystemMessageDB> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }

    private static Comparator<SystemMessageDB> comp = new Comparator<SystemMessageDB>() {

        @Override
        public int compare(SystemMessageDB o1, SystemMessageDB o2) {
            long time = o1.getTime() - o2.getTime();
            return time == 0 ? 0 : (time > 0 ? -1 : 1);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //当收到推荐系统通知时 拉取本地消息更新列表
    public void onSystemMessageEvent(ProductsRecommendEvent event) {
        SystemMessageDBDao dbDao = GreenDaoManager.getInstance().getmDaoSession().getSystemMessageDBDao();
        List<SystemMessageDB> poolList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(51)).list();//水塘内容推荐
        List<SystemMessageDB> tipList = dbDao.queryBuilder().where(SystemMessageDBDao.Properties.Type.eq(52)).list();//水贴内容推荐


        List<SystemMessageDB> tuiJianList = new ArrayList<>();
        if (poolList != null) {
            tuiJianList.addAll(poolList);
        }

        if (tipList != null) {
            tuiJianList.addAll(tipList);
        }

        sortRecentContacts(tuiJianList);
// 刷新列表
        if (mAdapter != null) {
            mAdapter.setNewData(tuiJianList);
        }

    }

}
