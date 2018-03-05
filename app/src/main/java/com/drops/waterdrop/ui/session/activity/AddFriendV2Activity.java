package com.drops.waterdrop.ui.session.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.session.adapter.AddContactV2Adapter;
import com.drops.waterdrop.ui.session.presenter.AddFriendV2Presenter;
import com.drops.waterdrop.ui.session.view.IAddFriendV2View;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.util.contact.ContactDataSyncService;
import com.drops.waterdrop.util.sys.UIUtils;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.LocalContactEntity;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import rx.functions.Action1;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/14 12:31
 */

public class AddFriendV2Activity extends BaseActivity<IAddFriendV2View, AddFriendV2Presenter> implements IAddFriendV2View {

    public static void startActivity(Context context){
        startActivity(context, true);
    }

    public static void startActivity(Context context, boolean isRegistered){
        Intent intent = new Intent(context, AddFriendV2Activity.class);
        intent.putExtra("bool", isRegistered);
        context.startActivity(intent);
    }

    private static final int VF_UN_PERMISSION_STATE = 0;
    private static final int VF_DATA_STATE = 1;
    private static final int VF_DATA_EMPTY_STATE = 2;

    @Bind(R.id.aafv_viewflipper)
    ViewFlipper mViewFlipper;
    @Bind(R.id.aafv_registered)
    TextView mRegistered;
    @Bind(R.id.aafv_unregistered)
    TextView mUnRegistered;
    @Bind(R.id.aafv_list)
    IndexableLayout mIndexableLayout;
    @Bind(R.id.aafv_search)
    LinearLayout mSearch;

    private boolean isRegistered;
    private RxPermissions rxPermissions;
    private int registeredColor;
    private int unRegisteredColor;
    private AddContactV2Adapter mAdapter;

    @Override
    protected void initView() {
        isRegistered = getIntent().getBooleanExtra("bool", true);
        initTitle();
        initIndexableLayout();

        mPresenter.checkGuide(mSearch, R.id.aafv_root);

        rxPermissions = new RxPermissions(this);
    }

    private void initTitle(){
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "添加好友";
        options.isNeedNavigate = true;
        setMyToolbar(options);
    }

    private void initIndexableLayout(){
        mIndexableLayout.setLayoutManager(new LinearLayoutManager(this));
        mIndexableLayout.showAllLetter(false);
        mAdapter = new AddContactV2Adapter(this);
        mIndexableLayout.setAdapter(mAdapter);
        mIndexableLayout.setOverlayStyle_MaterialDesign(Color.parseColor("#0EC7F0"));
        mIndexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<LocalContactEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, LocalContactEntity entity) {
            }
        });
    }

    @Override
    protected void initData() {
        registeredColor = UIUtils.getColor(R.color.colorBlue);
        unRegisteredColor = UIUtils.getColor(R.color.base_gray_85);

        mPresenter.loadData(isRegistered);
        initPermission();
    }

    private void initPermission(){
        if (!hasPermission()){
            mViewFlipper.setDisplayedChild(VF_UN_PERMISSION_STATE);
            registerSyncFinishBroadcast();
            requestPermission();
        }
    }

    private void registerSyncFinishBroadcast(){
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mPresenter.loadData(isRegistered);
            }
        }, ContactDataSyncService.ACTION_CONTACT_SYNC_FINISH);
    }

    private void requestPermission(){
        rxPermissions.request(Manifest.permission.WRITE_CONTACTS)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean){
                            startSyncService();
                        } else {
                            ToastUtil.showShort("请开启读取通讯录权限");
                        }
                    }
                });
    }

    private void startSyncService(){
        DialogMaker.showProgressDialog(AddFriendV2Activity.this, "导入数据中……");
        WaterDropApp.app.startContactSyncService(0);
        mViewFlipper.setDisplayedChild(VF_DATA_STATE);
    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.aafv_search)
    void onSearchClick(){
        if (!FastClickUtil.isFastDoubleClick()) {
            SearchActivity.start(this);
        }
    }

    @OnClick(R.id.aafv_registered)
    void onRegisteredClick(){
        mPresenter.loadData(true);
    }

    @OnClick(R.id.aafv_unregistered)
    void onUnRegisteredClick(){
        mPresenter.loadData(false);
    }

    @Override
    protected AddFriendV2Presenter createPresenter() {
        return new AddFriendV2Presenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_friend_v2;
    }

    @Override
    public void setSelected(boolean isRegistered) {
        if (isRegistered){
            mRegistered.setTextColor(registeredColor);
            mUnRegistered.setTextColor(unRegisteredColor);
        } else {
            mRegistered.setTextColor(unRegisteredColor);
            mUnRegistered.setTextColor(registeredColor);
        }
    }

    @Override
    public void setData(List<LocalContactEntity> entities) {
        if (DialogMaker.isShowing())
            DialogMaker.dismissProgressDialog();

        if (entities.size() != 0){
            mAdapter.setDatas(entities);
            mViewFlipper.setDisplayedChild(VF_DATA_STATE);
        } else {
            mViewFlipper.setDisplayedChild(VF_DATA_EMPTY_STATE);
        }
    }

    @Override
    public boolean hasPermission() {
        return rxPermissions.isGranted(Manifest.permission.READ_CONTACTS);
    }

    @Override
    public void showNoPermission() {
        mViewFlipper.setDisplayedChild(VF_UN_PERMISSION_STATE);
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
