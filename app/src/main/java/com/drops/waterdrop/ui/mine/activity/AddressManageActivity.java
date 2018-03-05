package com.drops.waterdrop.ui.mine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.adapter.AddressManageAdapter;
import com.drops.waterdrop.ui.mine.presenter.AddressManagePresenter;
import com.drops.waterdrop.ui.mine.view.IAddressManageView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.model.AddressEntity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by dengxiaolei on 2017/6/2.
 */

public class AddressManageActivity extends BaseActivity<IAddressManageView, AddressManagePresenter> implements IAddressManageView, View.OnClickListener {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_add_address)
    TextView mTvAddAddress;
    private AddressManageAdapter mAdapter;

    private boolean isFromOrder;

    public static void start(Context context) {
        Intent starter = new Intent(context, AddressManageActivity.class);

        context.startActivity(starter);
    }

    public static void startForResult(Context context, int requstCode) {
        Intent starter = new Intent(context, AddressManageActivity.class);
        starter.putExtra(Constants.EXTRA_BOOLEAN, true);

        ((Activity) context).startActivityForResult(starter, requstCode);
    }

    @Override
    protected void initView() {
        parseIntent(getIntent());
        initTitle();
        initList();
    }

    private void parseIntent(Intent intent) {
        if (intent != null) {
            isFromOrder = intent.getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
        }
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleId = R.string.address_manage;
        setMyToolbar(options);
    }

    private void initList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AddressManageAdapter(0);
        mRecyclerView.setAdapter(mAdapter);

        View emptyView = View.inflate(this, R.layout.empty_view, null);
        ImageView img_empty = (ImageView) emptyView.findViewById(R.id.iv_empty_icon);
        img_empty.setImageResource(R.mipmap.img_all_qs);
        mAdapter.setEmptyView(emptyView);
        mAdapter.setOnUpdateListener(new AddressManageAdapter.onUpdateListener() {
            @Override
            public void onUpdate() {
                initData();
            }
        });

        if (isFromOrder) {
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    AddressEntity.ResultsBean resultsBean = mAdapter.getData().get(position);
                    Intent intent = new Intent();
                    intent.putExtra(Constants.EXTRA_ENTITY, resultsBean);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    @Override
    protected void initData() {
        mPresenter.getAddressData();
    }

    @Override
    protected void initListener() {
        mTvAddAddress.setOnClickListener(this);
    }

    @Override
    protected AddressManagePresenter createPresenter() {
        return new AddressManagePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_address_manage;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_address:
                if (!FastClickUtil.isFastDoubleClick()) {
                    AddAddressActivity.start(AddressManageActivity.this, AddAddressActivity.TYPE_ADD, null);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != AddAddressActivity.REQUEST_CODE) return;

        if (resultCode == RESULT_OK) {
            initData();

        } else if (requestCode == RESULT_CANCELED) {
            initData();
        }
    }

    @Override
    public void onGetAddressSucceed(List<AddressEntity.ResultsBean> addressInsertEntity) {
        mAdapter.setNewData(addressInsertEntity);
    }
}
