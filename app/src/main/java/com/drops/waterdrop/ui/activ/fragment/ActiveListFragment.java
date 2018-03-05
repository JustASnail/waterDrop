package com.drops.waterdrop.ui.activ.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.activ.adapter.ActiveListAdapter;
import com.drops.waterdrop.ui.activ.presenter.ActiveListPresenter;
import com.drops.waterdrop.ui.activ.view.IActiveListView;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.LazyLoadFragment;
import com.netease.nim.uikit.common.util.FastClickUtil;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/6/4.
 */

public class ActiveListFragment extends LazyLoadFragment<IActiveListView, ActiveListPresenter> implements BaseQuickAdapter.OnItemClickListener {

    public static final String ARG_TYPE = "PAGE_TYPE";
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ActiveListAdapter mAdapter;

    public static ActiveListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        ActiveListFragment fragment = new ActiveListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        mPresenter.setPageType(getArguments());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ActiveListAdapter(0);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        mAdapter.setNewData(mPresenter.getData());
    }

    @Override
    protected ActiveListPresenter createPresenter() {
        return new ActiveListPresenter((BaseActivity) getActivity());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_active_list;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!FastClickUtil.isFastDoubleClick()) {
            mPresenter.onItemClick(position);
        }
    }
}
