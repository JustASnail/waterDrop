package com.drops.waterdrop.ui.mine.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.fragment.TransferInfoDialog;
import com.drops.waterdrop.ui.mine.presenter.BindBankCardPresenter;
import com.drops.waterdrop.ui.mine.view.IBindBankCardView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.KVCombinationView;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.BankCardListEntity;
import com.netease.nim.uikit.model.BankCardNoListEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/31 02:54
 */

public class BankCardDetailActivity extends BaseActivity<IBindBankCardView, BindBankCardPresenter> implements IBindBankCardView {

    @Bind(R.id.abcd_company_account)
    KVCombinationView mCompanyAccount;
    @Bind(R.id.abcd_company_name)
    KVCombinationView mCompanyName;
    @Bind(R.id.abcd_bank_name)
    KVCombinationView mBankName;
    @Bind(R.id.abcd_create_account_province)
    KVCombinationView mCreateAccountProvince;
    @Bind(R.id.abcd_create_account_city)
    KVCombinationView mCreateAccountCity;
    @Bind(R.id.abcd_create_account_bank)
    KVCombinationView mCreateAccountBank;
    @Bind(R.id.abcd_bind_date)
    KVCombinationView mBindDate;
    @Bind(R.id.abcd_transfer_info)
    KVCombinationView mTransferInfo;
    @Bind(R.id.abcd_transfer_certificate)
    KVCombinationView mTransferCertificate;
    @Bind(R.id.abcd_apply_status)
    KVCombinationView mApplyStatus;
    @Bind(R.id.abcd_bind_fail_area)
    LinearLayout mBindCardFail;

    private BankCardListEntity.BankCard bankCard;
    private TransferInfoDialog transferInfoDialog;

    @Override
    protected void initView() {
        initTitle();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "银行帐户详情";
        options.isNeedNavigate = true;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        bankCard = (BankCardListEntity.BankCard) getIntent().getSerializableExtra("obj");
        if (bankCard == null)
            finish();

        changeTip();
        mCompanyAccount.setValue(bankCard.getCardNoForFormat1());
        mCompanyName.setValue(bankCard.getUserName());
        mBankName.setValue(bankCard.getBankName());
        mCreateAccountProvince.setValue(bankCard.getCardProv());
        mCreateAccountCity.setValue(bankCard.getCardCity());
        mCreateAccountBank.setValue(bankCard.getCardDeposit());
        mBindDate.setValue(bankCard.getCreateTime());
        GlideUtil.showImageView(this, bankCard.getSubmitPhoto(), mTransferCertificate.getRightImage());
        mApplyStatus.setValue(bankCard.getReviewState());
        mApplyStatus.setValueColor(bankCard.getReviewStateColor());

        if (bankCard.isReviewFail()){
            mBindCardFail.setVisibility(View.VISIBLE);
        }
    }

    private void changeTip() {
        boolean mIsVip = MyUserCache.getVipOrSuply() == 2;
        mCompanyAccount.setHint(mIsVip ? R.string.hint_person_account : R.string.hint_company_account);
        mCompanyAccount.setmName(mIsVip ? R.string.person_account : R.string.company_account);
        mCompanyName.setHint(mIsVip ? R.string.hint_person_name : R.string.hint_company_name);
        mCompanyName.setmName(mIsVip ? R.string.person_name : R.string.company_name);
    }

    @Override
    protected void initListener() {
    }

    @OnClick(R.id.abcd_re_submit)
    void onReSubmitClick(){
        mPresenter.checkBindBankCardParam(mCompanyAccount, mCompanyName, mBankName, mCreateAccountProvince, mCreateAccountCity, mCreateAccountBank, bankCard.getSubmitPhoto());
    }

    @OnClick(R.id.abcd_transfer_info)
    void onTransferInfoClick(){
        if (!FastClickUtil.isFastDoubleClick()) {
            if (transferInfoDialog == null){
                transferInfoDialog = new TransferInfoDialog();
            }
            transferInfoDialog.show(getSupportFragmentManager(), "");
        }
    }

    @Override
    protected BindBankCardPresenter createPresenter() {
        return new BindBankCardPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bank_card_detail;
    }

    @Override
    public void onSubmitSuccess() {
        ToastUtil.showLong("重新提交成功，请等待审核");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onGetBankCardNos(List<BankCardNoListEntity.BankCardNo> bankCardNos) {

    }

    @Override
    public void onGetQiNiuImageKey(String url, String key) {

    }
}
