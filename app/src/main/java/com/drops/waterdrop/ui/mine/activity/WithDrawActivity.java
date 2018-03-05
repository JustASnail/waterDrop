package com.drops.waterdrop.ui.mine.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.event.AvailableMoneyChangeEvent;
import com.drops.waterdrop.ui.mine.presenter.WithdrawPresenter;
import com.drops.waterdrop.ui.mine.view.IWithdrawView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.sys.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/8/24.
 */

public class WithDrawActivity extends BaseActivity<IWithdrawView, WithdrawPresenter> implements IWithdrawView,View.OnClickListener{

    private static final String TAG = "WithDrawActivity";

    @Bind(R.id.cdv_bank)
    CardView cdvBank;
    @Bind(R.id.et_count)
    EditText etCount;
    @Bind(R.id.tv_remian)
    TextView tvRemain;
    @Bind(R.id.btn_withdraw)
    Button btnWithdraw;
    @Bind(R.id.tv_bank_name)
    TextView tvBankName;
    @Bind(R.id.tv_bank_num)
    TextView tvBankNum;
    @Bind(R.id.rl_bank)
    RelativeLayout rlBank;
    @Bind(R.id.iv_bg)
    ImageView mIvBg;

    private long cardId;
    private Double mRemian;
    private String bankNum;
    private long cardIdLocal;

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "我要提现";
        setMyToolbar(options);

        setBankCard();
    }

    private void setBankCard() {
        String bankName = MyUserCache.getBankName();
        bankNum = MyUserCache.getBankNum();
        String cardPic = MyUserCache.getCardPic();
        cardIdLocal = MyUserCache.getCardId();

        if (TextUtils.isEmpty(bankName) || TextUtils.isEmpty(bankNum)) {
            GlideUtil.showImageView(this, R.mipmap.icon_wd_zh_zwyh, mIvBg);
            tvBankName.setText("暂无银行");
            tvBankNum.setText("");
        } else {
            if (!TextUtils.isEmpty(bankName) && !TextUtils.isEmpty(bankNum)) {
                String cardNoForFormat = getCardNoWithString(bankNum);
                tvBankNum.setText(cardNoForFormat);
                tvBankName.setText(bankName);
                if (TextUtils.isEmpty(cardPic)) {
                    GlideUtil.showImageView(this, R.mipmap.icon_wd_zh_zwyh, mIvBg);
                } else {
                    GlideUtil.showImageView(this, R.mipmap.icon_wd_zh_zwyh,cardPic, mIvBg);
                }
            }
        }
    }

    @Override
    protected void initData() {
        String remainMoney = MyUserCache.getUserRemainMoney();
        mRemian = Double.valueOf(remainMoney);
        tvRemain.setText(remainMoney);
    }

    @Override
    protected void initListener() {
        cdvBank.setOnClickListener(this);
        btnWithdraw.setOnClickListener(this);
        setPricePoint(etCount);
    }

    @Override
    protected WithdrawPresenter createPresenter() {
        return new WithdrawPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cdv_bank:
                Intent intent = new Intent(this, SeleBankAccountActivity.class);
                startActivityForResult(intent, Constants.BANK_INFO);
                break;
            case R.id.btn_withdraw:
                CharSequence text = tvBankNum.getText();
                if (TextUtils.isEmpty(text)) {
                    ToastUtil.showShort("小主，请选择到账银行账户");
                    return;
                }
                String price = etCount.getText().toString().trim();
                if (TextUtils.isEmpty(price)) {
                    ToastUtil.showShort("小主，请输入您的提现金额");
                    return;
                }
                Double price1 = Double.valueOf(price);
                if (price1 > mRemian) {
                    ToastUtil.showShort("小主，余额不足哦");
                    return;
                }
                if (price1 < 0.01) {
                    ToastUtil.showShort("小主，提现最小金额为0.01元");
                    return;
                }
                showAddmitDialog(price1);
                break;
        }
    }

    private void showAddmitDialog(final double price1) {
        new AlertDialog.Builder(this)
                .setMessage("小主，您确定要提现吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cardId == 0) {
                            mPresenter.withdraw(cardIdLocal, price1);
                        } else {
                            mPresenter.withdraw(cardId, price1);
                        }
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.BANK_INFO && resultCode == Constants.BANK_INFO) {
            cardId = data.getLongExtra(Constants.CARD_ID, 0l);
            setBankCard();
        }
    }

    @Override
    public void onWithdraw() {
        EventBus.getDefault().post(new AvailableMoneyChangeEvent());
        finish();
    }

    public  void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}

            @Override
            public void afterTextChanged(Editable s) {}

        });
    }

    public static  String getCardNoWithString(String cardNo){
        if (TextUtils.isEmpty(cardNo) || cardNo.length() < 4)
            return cardNo;
        return "**** **** **** " + cardNo.substring(cardNo.length() - 4, cardNo.length());
    }

    public static  String getCardNoLast4(String cardNo){
        if (TextUtils.isEmpty(cardNo) || cardNo.length() < 4)
            return cardNo;
        return "(" + cardNo.substring(cardNo.length() - 4) + ")";
    }

}
