package com.drops.waterdrop.ui.mine.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.LazyLoadFragment;
import com.drops.waterdrop.ui.find.activity.PoolDetailPageActivity;
import com.drops.waterdrop.ui.mine.activity.FootprintActivity;
import com.drops.waterdrop.ui.mine.adapter.AddressManageAdapter;
import com.drops.waterdrop.ui.mine.adapter.FootprintShuiTangAdapter;
import com.drops.waterdrop.ui.mine.adapter.FootprintShuiTieAdapter;
import com.drops.waterdrop.ui.mine.event.UserInfoEvent;
import com.drops.waterdrop.ui.mine.presenter.FootprintPresenter;
import com.drops.waterdrop.ui.mine.view.IFootprintView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.FootprintShuiTangEntity;
import com.netease.nim.uikit.model.FootprintShuiTieEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/6/28.
 */

public class FootprintFragment extends LazyLoadFragment<IFootprintView, FootprintPresenter> implements BaseQuickAdapter.OnItemClickListener, IFootprintView, FootprintActivity.OneKeyCleanPressListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private static final String ARG_TITLE = "title_arg_key";
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private FootprintShuiTieAdapter mShuiTieAdapter;
    private FootprintShuiTangAdapter mShuiTangAdapter;
    private View notDataView;

    public static FootprintFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        FootprintFragment fragment = new FootprintFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        initEmptyPager();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mShuiTangAdapter = new FootprintShuiTangAdapter(0);
        mShuiTangAdapter.setEmptyView(notDataView);
        mRecyclerView.setAdapter(mShuiTangAdapter);
        mPresenter.getShuiTangList(false, false);
        mPresenter.setPoolLoadMoreAdapter(mShuiTangAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mShuiTangAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mShuiTangAdapter.setOnItemClickListener(this);
        mShuiTangAdapter.setOnUpdateListener(new FootprintShuiTangAdapter.onUpdateListener() {
            @Override
            public void onUpdate(int pos) {
                mShuiTangAdapter.remove(pos);
                mShuiTangAdapter.notifyDataSetChanged();
            }
        });

        ((FootprintActivity) getActivity()).addOneKeyPresseListener(this);
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
        FootprintShuiTangEntity.ResultsBean resultsBean = mShuiTangAdapter.getData().get(position);
        PoolDetailPageActivity.start(getContext(), resultsBean.getDropId());
    }

    @Override
    public void onGetFootprintShuiTieSucceed(List<FootprintShuiTieEntity.ResultsBean> resultsBeen) {
    }

    @Override
    public void onGetFootprintShuiTangSucceed(List<FootprintShuiTangEntity.ResultsBean> resultsBeen) {
    }

    @Override
    public void onCleanFootprintSuccess(int type) {
        List<FootprintShuiTangEntity.ResultsBean> data = mShuiTangAdapter.getData();
        if (data != null) {
            data.clear();
            mShuiTangAdapter.setNewData(data);
        }
    }

    @Override
    public void onOneKeyCleanPressed(int type) {
        //0表示水塘 1表示水帖，接口中 1表示水塘  2表示水帖，所以加一
        mPresenter.cleanFootprintByType(1);
    }

    private void initEmptyPager() {
        notDataView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getShuiTangList(false, false);
            }
        });
    }

    @Override
    public void onRefresh() {
        mShuiTangAdapter.setEnableLoadMore(false);
        mPresenter.getShuiTangList(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        mPresenter.getShuiTangList(false, true);
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
        UserInfoEvent userInfoEvent = new UserInfoEvent();
        userInfoEvent.type = Constants.TYPE_FOOTPRINT;
        userInfoEvent.footPrintPool = mShuiTangAdapter.getData().size();
        EventBus.getDefault().post(userInfoEvent);
    }
}
