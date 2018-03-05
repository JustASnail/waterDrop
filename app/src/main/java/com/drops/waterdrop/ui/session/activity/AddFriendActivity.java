package com.drops.waterdrop.ui.session.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.session.adapter.AddContactAdapter;
import com.drops.waterdrop.ui.session.presenter.AddFriendPresenter;
import com.drops.waterdrop.ui.session.view.IAddFriendView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.DrawableCenterTextView;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.model.LocalContactEntity;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import rx.functions.Action1;


/**
 * 添加好友页面
 */
@Deprecated
public class AddFriendActivity extends BaseActivity<IAddFriendView, AddFriendPresenter> implements IAddFriendView {
    private DrawableCenterTextView mSearchView;
    private TextView mTvNoPermission;

    private AddContactAdapter mAdapter;

    private Thread mThread;
    private TextView mTvLoading;

    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AddFriendActivity.class);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.fade_entry, R.anim.hold);

    }


    @Override
    protected void initView() {
        findViews();
        IndexableLayout indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);
        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        indexableLayout.showAllLetter(false);
        // setAdapter
        mAdapter = new AddContactAdapter(this);
        indexableLayout.setAdapter(mAdapter);
        // set Datas

        // set Material Design OverlayView
       indexableLayout.setOverlayStyle_MaterialDesign(Color.parseColor("#0EC7F0"));

        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
        indexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);

        // set Listener
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<LocalContactEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, LocalContactEntity entity) {
               /* if (originalPosition >= 0) {
                    ToastUtil.showShort("选中:" + entity.getNick() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
                } else {
                    ToastUtil.showShort("选中Header/Footer:" + entity.getNick() + "  当前位置:" + currentPosition);
                }*/
            }
        });

        if (!MyUserCache.getGuide()) {
            mSearchView.post(new Runnable() {
                @Override
                public void run() {
                    mPresenter.showGuideView(mSearchView, R.id.ll_addfriend);
                }
            });

        }
    }

    @Override
    protected void initData() {
        mPresenter.initContactView();
        requestBasicPermission();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected AddFriendPresenter createPresenter() {
        return new AddFriendPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_friend;
    }

    private void findViews() {
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);//设置状态栏透明， 并且toolbar向下偏移状态栏的高度

        View statusBarFix = findView(R.id.status_bar_fix);
        statusBarFix.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getStatusBarHeight(this)));//填充状态栏

        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "添加好友";
        options.isNeedNavigate = true;
        setMyToolbar(options);

        mTvLoading = (TextView) findViewById(R.id.tv_title);
        mSearchView = (DrawableCenterTextView) findViewById(R.id.search_view_text);
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FastClickUtil.isFastDoubleClick()) {
                    showSearchPage();
                }
            }


        });

        mTvNoPermission = (TextView) findViewById(R.id.tv_no_permission);
    }


    private void requestBasicPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        rxPermissions.request(Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        Logger.d("访问通讯录权限：" + aBoolean);
                        if (!aBoolean) {
                            ToastUtil.showShort("请开启读取通讯录权限");
                        } else {
                            mTvNoPermission.setVisibility(View.GONE);
                            mThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    mPresenter.setContactsView();
                                }
                            });
                            mThread.start();
                        }
                    }
                });

    }


    private void showSearchPage() {
        SearchActivity.start(this);
    }


    @Override
    public void setContactsList(List<LocalContactEntity> list) {
        if (mAdapter != null) {
            mAdapter.setDatas(list);
        }
        mTvLoading.setText("通讯录好友");

    }

    @Override
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvLoading.setText("正在刷新通信录...");
            }
        });
    }

    @Override
    public void setNoContacts() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvLoading.setText("通讯录好友");
            }
        });

    }

    @Override
    public void dissMissLoading() {
        mTvLoading.setText("通讯录好友");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mThread != null) {
            mThread = null;
        }


    }

    @Override
    public void onBackPressed() {
        if (MyUserCache.getGuide()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean isSupportSwipeBack() {
        if (MyUserCache.getGuide()) {
            return super.isSupportSwipeBack();
        } else {
            return false;
        }
    }
}