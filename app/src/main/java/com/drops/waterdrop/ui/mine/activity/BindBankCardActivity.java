package com.drops.waterdrop.ui.mine.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.fragment.TransferInfoDialog;
import com.drops.waterdrop.ui.mine.presenter.BindBankCardPresenter;
import com.drops.waterdrop.ui.mine.view.IBindBankCardView;
import com.drops.waterdrop.util.AddressPickerViewUtil;
import com.drops.waterdrop.util.KeyboardUtils;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.util.rxjava.SingleSchedulerTransformer;
import com.drops.waterdrop.util.sys.UIUtils;
import com.drops.waterdrop.widget.KVCombinationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.media.picker.PickImageHelper;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.file.FileUtil;
import com.netease.nim.uikit.model.BankCardNoListEntity;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Action1;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/29 23:57
 */

public class BindBankCardActivity extends BaseActivity<IBindBankCardView, BindBankCardPresenter> implements IBindBankCardView {

    private static final int PICK_AVATAR_REQUEST = 0x0E;

    @Bind(R.id.abbc_company_account)
    KVCombinationView mCompanyAccount;
    @Bind(R.id.abbc_company_name)
    KVCombinationView mCompanyName;
    @Bind(R.id.abbc_bank_name)
    KVCombinationView mBankName;
    @Bind(R.id.abbc_create_account_province)
    KVCombinationView mCreateAccountProvince;
    @Bind(R.id.abbc_create_account_city)
    KVCombinationView mCreateAccountCity;
    @Bind(R.id.abbc_create_account_bank)
    KVCombinationView mCreateAccountBank;
    @Bind(R.id.abbc_transfer_info)
    KVCombinationView mTransferInfo;
    @Bind(R.id.abbc_transfer_certificate)
    KVCombinationView mTransferCerificate;
    @Bind(R.id.abbc_tips)
    TextView mTips;

    private String transferCertificateImageKey;
    private AddressPickerViewUtil addressPickerViewUtil;
    private List<BankCardNoListEntity.BankCardNo> bankCardNos = new ArrayList<>();
    private TransferInfoDialog transferInfoDialog;

    private RxPermissions rxPermissions;
    private File bankCardNoCache;
    private Gson gson;

    @Override
    protected void initView() {
        initTitle();

        mTips.setText(Html.fromHtml(UIUtils.getString(R.string.hint_submit_review)));

        changeTip();
    }

    private void changeTip() {
        boolean mIsVip = MyUserCache.getVipOrSuply() == 2;
        mCompanyAccount.setHint(mIsVip ? R.string.hint_person_account : R.string.hint_company_account);
        mCompanyAccount.setmName(mIsVip ? R.string.person_account : R.string.company_account);
        mCompanyName.setHint(mIsVip ? R.string.hint_person_name : R.string.hint_company_name);
        mCompanyName.setmName(mIsVip ? R.string.person_name : R.string.company_name);
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "银行帐户绑定";
        options.isNeedNavigate = true;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        gson = new Gson();
        bankCardNoCache = new File(FileUtil.getExternalFilesPath(this, null), "bank_card_no_cache");
        if (bankCardNoCache.exists() && rxPermissions.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)){
            readBankCardNoFromFile();
        } else {
            mPresenter.getBankCardNoList();
        }
    }

    @Override
    protected void initListener() {
        mCompanyAccount.getValueEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String content = mCompanyAccount.getValue().trim();
                    if (!TextUtils.isEmpty(content)){
                        for (BankCardNoListEntity.BankCardNo bankCardNo : bankCardNos){
                            if (!TextUtils.isEmpty(bankCardNo.getCardPrefix()) && content.startsWith(bankCardNo.getCardPrefix())){
                                mBankName.setValue(bankCardNo.getBankName());
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    @OnClick({R.id.abbc_create_account_city, R.id.abbc_create_account_province})
    void onProvinceCityClick() {
        KeyboardUtils.hideSoftInput(BindBankCardActivity.this);
        if (addressPickerViewUtil == null)
            addressPickerViewUtil = AddressPickerViewUtil.getInstance(BindBankCardActivity.this, false);

        addressPickerViewUtil.ShowPickerView(new AddressPickerViewUtil.OnPickerListener() {

            @Override
            public void onPickerListener(String prov, String city, String district) {
                if ("市辖区".equals(city))
                    city = prov;

                mCreateAccountProvince.setValue(prov);
                mCreateAccountCity.setValue(city);
            }
        }, BindBankCardActivity.this);
    }

    @OnClick(R.id.abbc_transfer_certificate)
    void onTransferCertificateClick(){
        KeyboardUtils.hideSoftInput(BindBankCardActivity.this);
        requestCameraPermission();
    }

    private void requestCameraPermission() {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            ToastUtil.showShort("未全部授权，无法开启相机权限, 请开启相关权限！");
                        } else {
                            showDialog();

                        }
                    }
                });
    }

    private void showDialog() {
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.titleResId = R.string.set_transfer_certificate;
        option.crop = true;
        option.multiSelect = false;
        option.cropOutputImageWidth = 1024;
        option.cropOutputImageHeight = 700;
        PickImageHelper.pickImage(this, PICK_AVATAR_REQUEST, option);
    }

    @OnClick(R.id.abbc_transfer_info)
    void onTransferInfoClick(){
        KeyboardUtils.hideSoftInput(BindBankCardActivity.this);
        if (transferInfoDialog == null){
            transferInfoDialog = new TransferInfoDialog();
        }
        transferInfoDialog.show(getSupportFragmentManager(), "");
    }

    @OnClick(R.id.abbc_submit)
    void onSubmitClick() {
        mPresenter.checkBindBankCardParam(mCompanyAccount, mCompanyName, mBankName, mCreateAccountProvince, mCreateAccountCity, mCreateAccountBank, transferCertificateImageKey);
    }


    @Override
    protected BindBankCardPresenter createPresenter() {
        return new BindBankCardPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    public void onSubmitSuccess() {
        ToastUtil.showLong("您的提交已成功，请等待审核");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onGetBankCardNos(List<BankCardNoListEntity.BankCardNo> bankCardNos) {
        this.bankCardNos.clear();
        this.bankCardNos.addAll(bankCardNos);
        if (rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            writeBankCardNoToFile(bankCardNos);
        }
    }

    @Override
    public void onGetQiNiuImageKey(String url, String key) {
        transferCertificateImageKey = key;
        GlideUtil.showImageView(this, url, mTransferCerificate.getRightImage());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_AVATAR_REQUEST) {
            String path = data.getStringExtra(com.netease.nim.uikit.session.constant.Extras.EXTRA_FILE_PATH);
            mPresenter.uploadTransferCerificateImage(path);
        }
    }

    private void writeBankCardNoToFile(final List<BankCardNoListEntity.BankCardNo> bankCardNos){
        Single.create(new Single.OnSubscribe<Boolean>() {
            @Override
            public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
                FileUtil.writeStringToFile(gson.toJson(bankCardNos), bankCardNoCache);
                singleSubscriber.onSuccess(true);
            }
        }).compose(new SingleSchedulerTransformer<Boolean>()).subscribe();
    }

    private void readBankCardNoFromFile(){
        Single.create(new Single.OnSubscribe<Boolean>() {
            @Override
            public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
                String json = FileUtil.readFileToString(bankCardNoCache);
                if (!TextUtils.isEmpty(json)){
                    bankCardNos = gson.fromJson(json, new TypeToken<List<BankCardNoListEntity.BankCardNo>>(){}.getType());
                }
                singleSubscriber.onSuccess(TextUtils.isEmpty(json));
            }
        }).compose(new SingleSchedulerTransformer<Boolean>())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean){
                            mPresenter.getBankCardNoList();
                        }
                    }
                });
    }
}
