package com.drops.waterdrop.ui.mine.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.adapter.SeleBankAccountAdapter;
import com.drops.waterdrop.ui.mine.presenter.SeleBankAccountPresenter;
import com.drops.waterdrop.ui.mine.view.ISelectBankAccountView;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.model.SeleBankAccountEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/8/25.
 */

public class SeleBankAccountActivity extends BaseActivity<ISelectBankAccountView, SeleBankAccountPresenter> implements ISelectBankAccountView {

    @Bind(R.id.rv_bank_card)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_use_new)
    TextView tvUseNewCard;
    @Bind(R.id.tv_sele)
    TextView tvSelect;
    private SeleBankAccountAdapter mAdapter;

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "选择银行账户";
        setMyToolbar(options);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SeleBankAccountAdapter(0);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mPresenter.getBankList(false,false);
    }

    @Override
    protected void initListener() {
        tvUseNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeleBankAccountActivity.this, BindBankCardActivity.class));
            }
        });
        mAdapter.setBankSelectedListener(new SeleBankAccountAdapter.OnBankSelectedListener() {
            @Override
            public void onBankSelected(int pos) {
                SeleBankAccountEntity.ResultsBean item = mAdapter.getItem(pos);
                if (item != null) {
                    String bankNo = item.getCardNo();
                    long cardId = item.getCardId();
                    MyUserCache.saveBankName(item.getBankName());
                    MyUserCache.saveBankNum(bankNo);
                    MyUserCache.saveCardPic(item.getCardPhoto());
                    MyUserCache.saveCardId(cardId);

                    Intent data = new Intent();
                    data.putExtra(Constants.CARD_ID,cardId);
                    setResult(Constants.BANK_INFO,data);
                    SeleBankAccountActivity.this.finish();
                }
            }
        });
    }

    @Override
    protected SeleBankAccountPresenter createPresenter() {
        return new SeleBankAccountPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_sele_account;
    }

    @Override
    public void onGetBindedCardList(List<SeleBankAccountEntity.ResultsBean> bindedCardList) {
        if (bindedCardList.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvSelect.setVisibility(View.VISIBLE);
        }
        mAdapter.setNewData(bindedCardList);
    }
}
