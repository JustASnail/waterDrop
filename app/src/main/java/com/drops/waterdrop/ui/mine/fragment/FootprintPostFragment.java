package com.drops.waterdrop.ui.mine.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.LazyLoadFragment;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.drops.waterdrop.ui.mine.activity.FootprintActivity;
import com.drops.waterdrop.ui.mine.adapter.FootprintShuiTangAdapter;
import com.drops.waterdrop.ui.mine.adapter.FootprintShuiTieAdapter;
import com.drops.waterdrop.ui.mine.event.UserInfoEvent;
import com.drops.waterdrop.ui.mine.presenter.FootprintPresenter;
import com.drops.waterdrop.ui.mine.view.IFootprintView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.model.FootprintShuiTangEntity;
import com.netease.nim.uikit.model.FootprintShuiTieEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/6/28.
 */

public class FootprintPostFragment extends LazyLoadFragment<IFootprintView, FootprintPresenter> implements BaseQuickAdapter.OnItemClickListener, IFootprintView,FootprintActivity.OneKeyCleanPressPostListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private static final String ARG_TITLE = "title_arg_key";
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private FootprintShuiTieAdapter mShuiTieAdapter;
    private View notDataView;

    public static FootprintPostFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        FootprintPostFragment fragment = new FootprintPostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        initEmptyPager();
    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mShuiTieAdapter = new FootprintShuiTieAdapter(0);
        mShuiTieAdapter.setEmptyView(notDataView);
        mRecyclerView.setAdapter(mShuiTieAdapter);
        mPresenter.getShuiTieList(false, false);
        mPresenter.setLoadMoreAdapter(mShuiTieAdapter);


        mShuiTieAdapter.setOnItemClickListener(this);
        mShuiTieAdapter.setOnUpdateListener(new FootprintShuiTangAdapter.onUpdateListener() {
            @Override
            public void onUpdate(int pos) {
                mShuiTieAdapter.remove(pos);
                mShuiTieAdapter.notifyDataSetChanged();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mShuiTieAdapter.setOnLoadMoreListener(this, mRecyclerView);

        ((FootprintActivity) getActivity()).addOneKeyPressePostListener(this);
    }

    @Override
    protected FootprintPresenter createPresenter() {
        return new FootprintPresenter((BaseActivity) getActivity());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.footprint_fragment;
    }

    public String getTitle() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getString(ARG_TITLE);
        }
        return "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        FootprintShuiTieEntity.ResultsBean resultsBean = mShuiTieAdapter.getData().get(position);
        CommonWebActivity.start(getContext(), resultsBean.getTipId(), resultsBean.getDropTip().getTipUrl());
    }

    @Override
    public void onGetFootprintShuiTieSucceed(List<FootprintShuiTieEntity.ResultsBean> resultsBeen) {
    }

    @Override
    public void onGetFootprintShuiTangSucceed(List<FootprintShuiTangEntity.ResultsBean> resultsBeen) {
    }

    @Override
    public void onCleanFootprintSuccess(int type) {
        List<FootprintShuiTieEntity.ResultsBean> data = mShuiTieAdapter.getData();
        if (data != null) {
            data.clear();
            mShuiTieAdapter.setNewData(data);
        }
    }

    @Override
    public void onOneKeyCleanPostPressed(int type) {
        mPresenter.cleanFootprintByType(2);
    }

    private void initEmptyPager() {
        notDataView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getShuiTieList(false, false);
            }
        });
    }

    @Override
    public void onRefresh() {
        mShuiTieAdapter.setEnableLoadMore(false);
        mPresenter.getShuiTieList(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        mPresenter.getShuiTieList(false, true);
    }

    @Override
    public void setRefresh(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void setRefreshEnable(boolean b) {
        mSwipeRefreshLayout.setEnabled(b);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mShuiTieAdapter != null) {
            UserInfoEvent userInfoEvent = new UserInfoEvent();
            userInfoEvent.type = Constants.TYPE_FOOTPRINT;
            userInfoEvent.footPrintPost = mShuiTieAdapter.getData().size();
            EventBus.getDefault().post(userInfoEvent);
        }
    }
}
