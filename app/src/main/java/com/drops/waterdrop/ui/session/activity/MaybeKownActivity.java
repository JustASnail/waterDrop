package com.drops.waterdrop.ui.session.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.session.adapter.MaybeKownFriendAdapter;
import com.drops.waterdrop.ui.session.presenter.MaybeKownPresenter;
import com.drops.waterdrop.ui.session.view.IMaybeKownView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.model.RecommendFriendEntity;
import com.netease.nim.uikit.team.ui.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/9/12.
 */

public class MaybeKownActivity extends BaseActivity<IMaybeKownView, MaybeKownPresenter> implements IMaybeKownView {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private MaybeKownFriendAdapter mAdapter;
    private List<RecommendFriendEntity.ResultsBean> mData;

    public static void start(Context context) {
        Intent starter = new Intent(context, MaybeKownActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "您可能认识的人";
        options.rightString = "换一批";
        setMyToolbar(options);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (mAdapter == null) {
            mAdapter = new MaybeKownFriendAdapter(0);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider));
        }

    }

    @Override
    protected void initData() {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mPresenter.getRecommendFriendData();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onRightTextClick() {
        super.onRightTextClick();
        initData();
    }

    @Override
    protected MaybeKownPresenter createPresenter() {
        return new MaybeKownPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_maybe_kown;
    }


    @Override
    public void onGetRecommendFriendSucceed(List<RecommendFriendEntity.ResultsBean> results) {
        mData.clear();
        mData.addAll(results);
        if (mAdapter != null) {
            mAdapter.setNewData(mData);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            long uid = data.getLongExtra(Constants.EXTRA_FRIEND_UID, 0);
            if (mData != null && mData.size() > 0) {
                for (RecommendFriendEntity.ResultsBean resultsBean : mData) {
                    if (resultsBean.getUid() == uid) {
                        resultsBean.setRelationStatus(2);
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

        }
    }
}
