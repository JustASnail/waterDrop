package com.drops.waterdrop.ui.session.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.activity.GoldenBeanActivity;
import com.drops.waterdrop.ui.mine.activity.GoldenBeanDetailActivity;
import com.drops.waterdrop.ui.mine.activity.UserProfileActivity;
import com.drops.waterdrop.ui.session.presenter.SearchPresenter;
import com.drops.waterdrop.ui.session.view.ISearchView;
import com.drops.waterdrop.widget.SearchEditText;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.AddFriendForUid;
import com.netease.nim.uikit.model.JinDouEntity;
import com.netease.nim.uikit.model.SearchFriendEntity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/4/26.
 */

public class SearchActivity extends BaseActivity<ISearchView, SearchPresenter> implements View.OnClickListener, ISearchView {
    @Bind(R.id.search_edit)
    SearchEditText mSearchEdit;
    @Bind(R.id.tv_cancel)
    TextView mTvCancel;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;


    private String mAccount;

    private AddFriendForUid mUser;
    private SearchUserAdapter mAdapter;


    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(R.anim.fade_entry, R.anim.hold);

    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//设置状态栏字体颜色为深色
            if (true) {
                getWindow().getDecorView().setSystemUiVisibility(
                                 View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.white));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchUserAdapter(0);
        View emptyView = View.inflate(this, R.layout.empty_view, null);
        mAdapter.setEmptyView(emptyView);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mTvCancel.setOnClickListener(this);
        mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String moblile = mSearchEdit.getText().toString().trim();
                    if (!TextUtils.isEmpty(moblile)) {
//                        mPresenter.queryContact(moblile);
                        mPresenter.searchKeyword(moblile);
                    }
                    return true;
                }
                return false;
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    SearchFriendEntity.ResultsBean item = mAdapter.getItem(position);
                    UserProfileActivity.start(SearchActivity.this, item.getUid());
                }
            }
        });
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.rl_user_info:
                if (!TextUtils.isEmpty(mAccount)) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        VerifyActivity.start(SearchActivity.this, mUser);
                    }
                }
                break;*/
            case R.id.tv_cancel:
                finish();
                break;
        }
    }



    @Override
    public void onShowProgress() {
        DialogMaker.showProgressDialog(this, null, false);
    }

    @Override
    public void onDismissProgress() {
        DialogMaker.dismissProgressDialog();
    }

    @Override
    public void onQueryFailed() {

        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onQuerySuccess(AddFriendForUid user) {
        mRecyclerView.setVisibility(View.VISIBLE);

//        updateUserInfoView(user);
    }

/*
    private void updateUserInfoView(AddFriendForUid user) {
        mUser = user;
        mTvAccount.setText("帐号：" + user.getUid());
        GlideUtil.showImageViewToCircle(this, R.drawable.icon_default_head_60dp, user.getPhoto(), mImgHead);
        mTvName.setText("昵称：" + user.getNickName());
        mAccount = user.getUid() + "";
    }
*/

    @Override
    public void onQueryResultNull() {
        mRecyclerView.setVisibility(View.VISIBLE);
        EasyAlertDialogHelper.showOneButtonDiolag(SearchActivity.this, R.string.user_not_exsit,
                R.string.user_tips, R.string.ok, false, null);
    }

    @Override
    public void onSearchSuccess(List<SearchFriendEntity.ResultsBean> searchFriendEntity) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mAdapter.setNewData(searchFriendEntity);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /*
   * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
   *
   * @param v
   * @param event
   * @return
   */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                v.clearFocus();
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
