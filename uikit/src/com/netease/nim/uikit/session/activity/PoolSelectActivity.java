package com.netease.nim.uikit.session.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.PoolListBean;
import com.netease.nim.uikit.session.adapter.PoolSelectAdapter;
import com.netease.nim.uikit.session.module.input.InputPanel;

import java.util.ArrayList;

/**
 * Created by dengxiaolei on 2017/6/1.
 */

public class PoolSelectActivity extends UI implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private PoolSelectAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, PoolSelectActivity.class);
        context.startActivity(starter);
    }


    public static void startForResult(Context context) {
        Intent starter = new Intent(context, PoolSelectActivity.class);
        ((Activity)context).startActivityForResult(starter, InputPanel.SEND_CARD_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_select);



        initView();

        initList();

        initData();
    }

    private void initData() {

        ArrayList<PoolListBean> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            PoolListBean poolListBean = new PoolListBean();
            poolListBean.setName("周杰伦的水塘" + i);
            poolListBean.setAccount("43237275");
            list.add(poolListBean);
        }

        if (mAdapter != null) {
            mAdapter.setNewData(list);
        }
    }

    private void initList() {
        mAdapter = new PoolSelectAdapter(0);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initView() {
        View ivBack = findViewById(R.id.iv_left);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.tv_commn_title);
        tvTitle.setText("选择水塘");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Object item = adapter.getItem(position);
        PoolListBean poolListBean = (PoolListBean) item;
        Intent intent = new Intent();

        intent.putExtra(Constants.EXTRA_CARD_ACCID, poolListBean.getAccount());//群的im账号
        intent.putExtra(Constants.EXTRA_CARD_UID,poolListBean.getName());//群的显示账号 long类型
        intent.putExtra(Constants.EXTRA_CARD_USER_NAME,poolListBean.getName());//群的名字
        intent.putExtra(Constants.EXTRA_CARD_USER_AVATAR,poolListBean.getName());//群的头像
//        intent.putExtra(Constants.EXTRA_CARD_TYPE, BusinessCardAttachment.CARD_TYPE_POOL);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_left) {
            finish();
        }
    }
}
