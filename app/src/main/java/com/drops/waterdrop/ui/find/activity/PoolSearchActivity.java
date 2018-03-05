package com.drops.waterdrop.ui.find.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.adapter.PoolSearchAdapter;
import com.drops.waterdrop.ui.find.adapter.PostSearchAdapter;
import com.drops.waterdrop.ui.find.adapter.SearchHistoryAdapter;
import com.drops.waterdrop.ui.find.adapter.SearchTagAdapter;
import com.drops.waterdrop.ui.find.presenter.PoolSearchPresenter;
import com.drops.waterdrop.ui.find.view.IPoolSearchView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.SearchEditText;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.HotSearchEntity;
import com.netease.nim.uikit.model.SearchPoolEntity;
import com.netease.nim.uikit.model.SearchPostEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.Bind;


/**
 * 搜索水贴、 水宝
 * Created by dengxiaolei on 2017/6/28.
 */

public class PoolSearchActivity extends BaseActivity<IPoolSearchView, PoolSearchPresenter> implements IPoolSearchView, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {


    @Bind(R.id.searchResultList)
    RecyclerView mSearchResultList;
    @Bind(R.id.search_edit)
    SearchEditText mSearchEditText;
    @Bind(R.id.container)
    View mContainerView;
    @Bind(R.id.tv_search_cancel)
    TextView mSearchCancel;
    @Bind(R.id.hot_search)
    TextView mHotSearch;
    @Bind(R.id.flowlayout)
    TagFlowLayout mFlowlayout;
    @Bind(R.id.recycler_view_hostory)
    RecyclerView mRecyclerViewHostory;
    @Bind(R.id.container_search)
    NestedScrollView mContainerSearch;
    @Bind(R.id.tv_delete_hostory)
    TextView mTvDeleteHostory;


    private SearchView mSearchView;
    private TextView mTvPoolSize;
    private SearchTagAdapter mSearchTagAdapter;
    private SearchHistoryAdapter mHistoryAdapter;
    private View mHeadLayout;
    private View mEmptyView;
    private PoolSearchAdapter mPoolAdapter;
    private PostSearchAdapter mPostAdapter;


    public static void start(Context context, int pageMode) {
        Intent starter = new Intent(context, PoolSearchActivity.class);
        starter.putExtra(Constants.EXTRA_PAGE_MODE, pageMode);
        context.startActivity(starter);

    }

    @Override
    protected void initView() {
        mPresenter.parseIntent(getIntent());
        showKeyboardDelayed(mSearchEditText);

        mEmptyView = View.inflate(this, R.layout.empty_view, null);

        mSearchResultList.setVisibility(View.GONE);
        mSearchResultList.setLayoutManager(new LinearLayoutManager(this));
        mHeadLayout = LayoutInflater.from(this).inflate(R.layout.layout_pool_search_head, null);
        mTvPoolSize = (TextView) mHeadLayout.findViewById(R.id.tv_pools_size);
        TextView tvMode = (TextView) mHeadLayout.findViewById(R.id.tv_mode);


        if (mPresenter.mPageMode == Constants.POST_SEARCH_MODE) {
            tvMode.setText("条相关水帖");
            updatePostSearchUI();
        } else {
            tvMode.setText("条相关水塘");
            updatePoolSearchUI();
        }


        initHistory();
    }

    private void updatePoolSearchUI() {
        mSearchEditText.setHint("在此输入你想搜索的水塘");

        mPoolAdapter = new PoolSearchAdapter(0);
        mPoolAdapter.addHeaderView(mHeadLayout);


        mPoolAdapter.setEmptyView(mEmptyView);

        mPoolAdapter.setOnLoadMoreListener(this, mSearchResultList);
        mPresenter.setLoadMoreAdapter(mPoolAdapter);
        mSearchResultList.setAdapter(mPoolAdapter);
        mPoolAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    PoolDetailPageActivity.start(PoolSearchActivity.this, mPoolAdapter.getItem(position).getDropId());
                }
            }
        });
    }

    private void updatePostSearchUI() {
        mSearchEditText.setHint("在此输入你想搜索的水帖");
        mPostAdapter = new PostSearchAdapter(0);
        mPostAdapter.addHeaderView(mHeadLayout);
        mPostAdapter.setEmptyView(mEmptyView);
        mSearchResultList.setAdapter(mPostAdapter);
        mPostAdapter.setOnLoadMoreListener(this, mSearchResultList);

        mPresenter.setLoadMoreAdapter(mPostAdapter);
        mPostAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    CommonWebActivity.start(PoolSearchActivity.this, mPostAdapter.getItem(position).getTipId(), mPostAdapter.getItem(position).getTipUrl());
                }
            }
        });

    }

    private void initHistory() {
        mContainerSearch.setVisibility(View.VISIBLE);
        mHistoryAdapter = new SearchHistoryAdapter(0);
        mHistoryAdapter.setMode(mPresenter.mPageMode);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        mRecyclerViewHostory.setLayoutManager(layout);
        layout.setAutoMeasureEnabled(true);

        mRecyclerViewHostory.setAdapter(mHistoryAdapter);
        mRecyclerViewHostory.setNestedScrollingEnabled(false);
        mTvDeleteHostory.setOnClickListener(this);

        mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String word = mHistoryAdapter.getData().get(position).getName();
                mPresenter.search(word, false);
                mSearchEditText.setText(word);
                mSearchEditText.setSelection(word.length());
            }
        });
    }

    private void initHotSearch(final List<HotSearchEntity.ResultsBean> entity) {
        if (mSearchTagAdapter == null) {
            mSearchTagAdapter = new SearchTagAdapter(entity);
        }

        mFlowlayout.setAdapter(mSearchTagAdapter);
        mFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String word = entity.get(position).getWord();
                mPresenter.search(word, false);
                mSearchEditText.setText(word);
                mSearchEditText.setSelection(word.length());
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getHotSearch();

        mHistoryAdapter.setNewData(mPresenter.getSearchHistory());

    }

    @Override
    protected void initListener() {
        mSearchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = s.toString().trim();
                if (TextUtils.isEmpty(content)) {
                    if (mContainerSearch.getVisibility() == View.GONE) {
                        mContainerSearch.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                showKeyboard(false);
                String content = v.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    mPresenter.search(content, false);
                }
                return true;
            }
        });

        mSearchResultList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                showKeyboard(false);

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        mContainerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected PoolSearchPresenter createPresenter() {
        return new PoolSearchPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pool_search;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mSearchResultList != null) {
            mSearchResultList.clearFocus();
        }
    }


    @Override
    public void onLoadHotSearchSucceed(List<HotSearchEntity.ResultsBean> list) {
        initHotSearch(list);
    }

    @Override
    public void onLoadHotSearchError() {

    }

    @Override
    public void onLoadSearchError(String message) {
        if (mSearchResultList.getVisibility() == View.GONE) {
            mSearchResultList.setVisibility(View.VISIBLE);
        }
        ToastUtil.showShort(message);
    }

    @Override
    public void onLoadPostSearchSucceed(List<SearchPostEntity.ResultsBean> results) {
        if (mPostAdapter != null) {
            mPostAdapter.setNewData(results);
            mTvPoolSize.setText(results.size() + "");
            if (mSearchResultList.getVisibility() == View.GONE) {
                mSearchResultList.setVisibility(View.VISIBLE);
            }

            if (mContainerSearch.getVisibility() == View.VISIBLE) {
                mContainerSearch.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void notifyHistoryList() {
        if (mHistoryAdapter != null) {
            mHistoryAdapter.setNewData(mPresenter.getSearchHistory());
        }

    }

    @Override
    public void onLoadPoolSearchSucceed(List<SearchPoolEntity.ResultsBean> results) {

        if (mPoolAdapter != null) {
            mPoolAdapter.setNewData(results);
            mTvPoolSize.setText(results.size() + "");
            if (mSearchResultList.getVisibility() == View.GONE) {
                mSearchResultList.setVisibility(View.VISIBLE);
            }

            if (mContainerSearch.getVisibility() == View.VISIBLE) {
                mContainerSearch.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_delete_hostory:
                mPresenter.deleteAllHistory();
                mHistoryAdapter.removeAll();
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.search(null, true);
    }
}
