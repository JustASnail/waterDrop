package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.adapter.MyTicketListAdapter;
import com.drops.waterdrop.ui.mine.presenter.MyTicketPresenter;
import com.drops.waterdrop.ui.mine.view.IMyTicketView;
import com.drops.waterdrop.util.sys.UIUtils;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.MyTicketEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by HZH on 2017/7/5.
 */

public class MyTicketActivity extends BaseActivity<IMyTicketView, MyTicketPresenter> implements IMyTicketView {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private MyTicketListAdapter mAdapter;

    private List<MyTicketEntity.ResultsBean> tickeyList = new ArrayList<>();
    private View notDataView;
    private int mPostion;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyTicketActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle();
        initEmptyPager();
        initList();
    }

    private void initList() {
        if (mAdapter == null) {
            mAdapter = new MyTicketListAdapter(0);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter.setEmptyView(notDataView);
            mRecyclerView.setAdapter(mAdapter);

            TextView textView = new TextView(this);
            textView.setText("注： 门票领取将在活动结束后7个工作日开放，为了保证您的门票可以顺利领取，在此期间请勿退换货！");
            textView.setTextSize(12);
            textView.setPadding(UIUtils.dip2Px(20), UIUtils.dip2Px(12), UIUtils.dip2Px(20), UIUtils.dip2Px(12));
            mAdapter.addHeaderView(textView);

            mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        }
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleId = R.string.my_ticket;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        mPresenter.getTicketList();
    }

    @Override
    protected void initListener() {
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        mPostion = position;
                        MyTicketEntity.ResultsBean item = tickeyList.get(position);
                        String isReceived = item.getIsReceived();
                        boolean isGet = TextUtils.equals(isReceived, "1");
                        GetTicketActivity.start(MyTicketActivity.this, isGet, item);
                    }
                }
            });
        }
    }

    @Override
    protected MyTicketPresenter createPresenter() {
        return new MyTicketPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_ticket;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GetTicketActivity.REQUEST_CODE_GET_TICKET) {
            MyTicketEntity.ResultsBean ticket = (MyTicketEntity.ResultsBean) data.getSerializableExtra(Constants.EXTRA_ENTITY);
            tickeyList.remove(mPostion);
            mAdapter.addData(mPostion, ticket);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetTicketsSuccess(List<MyTicketEntity.ResultsBean> resultsBean) {
        tickeyList.addAll(resultsBean);

        mAdapter.setNewData(resultsBean);
    }
    private void initEmptyPager() {
        notDataView = LayoutInflater.from(this).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }
}
