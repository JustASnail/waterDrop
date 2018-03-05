package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.adapter.BankCardListAdapter;
import com.drops.waterdrop.ui.mine.presenter.BankCardListPresenter;
import com.drops.waterdrop.ui.mine.view.IBankCardListView;
import com.netease.nim.uikit.model.BankCardListEntity;

import java.util.List;

import butterknife.Bind;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/29 22:01
 */

public class BankCardListActivity extends BaseActivity<IBankCardListView, BankCardListPresenter> implements IBankCardListView {

    public static void startActivity(Context context){
        context.startActivity(new Intent(context, BankCardListActivity.class));
    }

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private BankCardListAdapter mAdapter;
    private AlertDialog.Builder mBuilder;

    @Override
    protected void initView() {
        initTitle();
        initList();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "我的银行帐户";
        options.isNeedNavigate = true;
        setMyToolbar(options);
    }

    private void initList(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BankCardListAdapter(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(BankCardListActivity.this, BindBankCardActivity.class), 200);
            }
        });
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void initData() {
        mPresenter.getBankCards();
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(BankCardListActivity.this, BankCardDetailActivity.class);
                intent.putExtra("obj", mAdapter.getItem(position));
                startActivity(intent);
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                showDelDialog(mAdapter.getItem(position));
                return true;
            }
        });
    }

    private void showDelDialog(final BankCardListEntity.BankCard bankCard){
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(this);
            mBuilder.setTitle("温馨提示");
            mBuilder.setMessage("小主，您确认要删除此银行账户吗？");
            mBuilder.setNegativeButton("取消", null);
        }
        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.delBankCard(bankCard.getCardId());
            }
        });
        mBuilder.show();
    }

    @Override
    protected BankCardListPresenter createPresenter() {
        return new BankCardListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bank_card_list;
    }

    @Override
    public void onResponse(List<BankCardListEntity.BankCard> bankCards) {
        mAdapter.setNewData(bankCards);
    }

    @Override
    public void onDelBankCardSuccess() {
        mPresenter.getBankCards();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            mPresenter.getBankCards();
        }
    }
}
