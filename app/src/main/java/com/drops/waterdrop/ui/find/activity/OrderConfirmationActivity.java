package com.drops.waterdrop.ui.find.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.find.presenter.OrderConfirmationPresenter;
import com.drops.waterdrop.ui.find.view.IOrderConfirmationView;
import com.drops.waterdrop.ui.mine.activity.AddressManageActivity;
import com.drops.waterdrop.ui.mine.activity.IdCardEditActivity;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.util.sys.UIUtils;
import com.drops.waterdrop.widget.AmountView;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.GoodsDetailEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 生成订单
 * Created by dengxiaolei on 2017/6/8.
 */

public class OrderConfirmationActivity extends BaseActivity<IOrderConfirmationView, OrderConfirmationPresenter> implements CompoundButton.OnCheckedChangeListener, AmountView.OnAmountChangeListener, IOrderConfirmationView, View.OnClickListener, OnTabSelectListener {


    public static final String EXTRA_ORDER_TYPE = "order_type";
    private static final int REQUST_CODE = 10;
    @Bind(R.id.status_bar_fix)
    View mStatusBarFix;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.address_icon)
    ImageView mAddressIcon;
    @Bind(R.id.tv_no_address)
    TextView mTvNoAddress;
    @Bind(R.id.tv_consignee_name)
    TextView mTvConsigneeName;
    @Bind(R.id.tv_consignee_phone)
    TextView mTvConsigneePhone;
    @Bind(R.id.tv_consignee_address)
    TextView mTvConsigneeAddress;
    @Bind(R.id.rl_address)
    RelativeLayout mRlAddress;
    @Bind(R.id.iv_country_icon)
    ImageView mIvCountryIcon;
    @Bind(R.id.tv_country)
    TextView mTvCountry;
    @Bind(R.id.iv_icon)
    ImageView mIvIcon;
    @Bind(R.id.tv_price)
    TextView mTvPrice;
    @Bind(R.id.tv_goods_name)
    TextView mTvGoodsName;
    @Bind(R.id.specification)
    TextView mSpecification;
    @Bind(R.id.tv_goods_num)
    TextView mTvGoodsNum;
    @Bind(R.id.tv_specification)
    TextView mTvSpecification;
    @Bind(R.id.amount_view)
    AmountView mAmountView;
    @Bind(R.id.tv_send_type)
    TextView mTvSendType;
    @Bind(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @Bind(R.id.tv_integral)
    TextView mTvIntegral;
    @Bind(R.id.rl_integral)
    RelativeLayout mRlIntegral;
    @Bind(R.id.tv_wechat_pay)
    TextView mTvWechatPay;
    @Bind(R.id.switch_default)
    SwitchCompat mSwitchDefault;
    @Bind(R.id.invoice)
    TextView mInvoice;
    @Bind(R.id.rl_bill_type)
    RelativeLayout mRlBillType;
    @Bind(R.id.btn_ok)
    Button mBtnOk;
    @Bind(R.id.layout_address)
    View mLayoutAddress;
    @Bind(R.id.goods_num)
    TextView mGoodsNum;
    @Bind(R.id.tab_layout)
    SegmentTabLayout mSegmentTabLayout;
    @Bind(R.id.et_bill_code)
    EditText mEtBillCode;
    @Bind(R.id.ll_bill_code)
    LinearLayout mLlBillCode;
    @Bind(R.id.et_bill_content)
    EditText mEtBillContent;
    @Bind(R.id.ll_bill_content)
    LinearLayout mLlBillContent;
    @Bind(R.id.ll_service)
    LinearLayout mLlService;
    @Bind(R.id.tv_service1)
    TextView mTvService1;
    @Bind(R.id.tv_service2)
    TextView mTvService2;
    @Bind(R.id.tv_service3)
    TextView mTvService3;

    @Bind(R.id.aoc_identity)
    LinearLayout mIdentity;
    @Bind(R.id.aoc_identity_state)
    TextView mIdentityState;
    @Bind(R.id.rl_bill)
    RelativeLayout mRlBill;

    private long mAddressId = -1;
    private String[] mBillContent = {"个人", "公司"};
    private boolean isCompleteIdCard = false;
    private String mIdCardUserName;


    public static void start(Context context, GoodsDetailEntity entity, long dropId, long tipId, int type) {
        start(context, entity, dropId, tipId, type, null);
    }

    public static void start(Context context, GoodsDetailEntity entity, long dropId, long tipId, int type, GoodsDetailEntity.GoodBean.GoodSku goodSku){
        Intent starter = new Intent(context, OrderConfirmationActivity.class);
        starter.putExtra(Constants.EXTRA_ENTITY, entity);
        starter.putExtra(Constants.EXTRA_ORDER_TYPE, type);
        starter.putExtra(Constants.EXTRA_DROP_ID, dropId);
        starter.putExtra(Constants.EXTRA_TIP_ID, tipId);
        starter.putExtra(Constants.EXTRA_GOODSKU, goodSku);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle();
        registerFinishReceiver(Constants.BR_PAY_H5_SUCC);
        mPresenter.parseIntent(getIntent());
        mAmountView.setGoods_storage(999);

        if (mPresenter.mEntity == null || mPresenter.mEntity.getGood() == null) {
            ToastUtil.showShort("找不到该宝贝");
            finish();
            return;
        }
        GoodsDetailEntity.ShopBean shop = mPresenter.mEntity.getShop();
        mIdentity.setVisibility(mPresenter.isCrossBorder() ? View.VISIBLE : View.GONE);
        mTvCountry.setText(mPresenter.mEntity.getGood().getCountryname() + "馆");
//        GlideUtil.showImageView(this, R.drawable.img_qs_33x33, mPresenter.mEntity.getGood().getCountrypic(), mIvCountryIcon);
        Glide.with(this).load(mPresenter.mEntity.getGood().getCountrypic())
                .placeholder(R.drawable.img_qs_108x70)
                .error(R.drawable.img_qs_108x70)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mIvCountryIcon);
        mTvGoodsName.setText(mPresenter.mEntity.getGood().getActTitle());

        if (mPresenter.goodSku == null){
            GlideUtil.showImageView(this, R.drawable.img_qs_90x90, mPresenter.mEntity.getGood().getActPic(), mIvIcon);
            List<GoodsDetailEntity.GoodBean.MoneyDetailBean> moneyDetail = mPresenter.mEntity.getGood().getMoneyDetail();
            if (moneyDetail != null && moneyDetail.size() > 0) {
                mTvSpecification.setText(moneyDetail.get(0).getDesc());
            }
            mTvPrice.setText(NumberUtil.Instance.formatPrice(mPresenter.mEntity.getGood().getActPrice()));
            mTvGoodsNum.setText("1");
            mTvTotalPrice.setText(NumberUtil.Instance.formatPrice(mPresenter.mEntity.getGood().getActPrice()));
        } else {
            GlideUtil.showImageView(this,R.drawable.img_qs_90x90,  mPresenter.goodSku.getPhoto(), mIvIcon);
            mTvSpecification.setText(mPresenter.goodSku.getSkuName());
            mTvPrice.setText(NumberUtil.Instance.formatPrice(mPresenter.goodSku.getPrice()));
            mTvGoodsNum.setText(Integer.toString(mPresenter.goodSku.getQuantity()));
            mAmountView.setAmount(mPresenter.goodSku.getQuantity());
            double totalPrice = Double.parseDouble(mPresenter.goodSku.getPrice()) * mPresenter.goodSku.getQuantity();
            mTvTotalPrice.setText(NumberUtil.Instance.formatPrice(totalPrice));
        }


        mTvWechatPay.setText(mPresenter.payMethod());

        mSegmentTabLayout.setTabData(mBillContent);
        mPresenter.initAddressView();
        showService(mPresenter.mEntity.getGood().getService(), mLlService, mTvService1, mTvService2, mTvService3);

        mRlBill.setVisibility(mPresenter.isCrossBorder() ? View.GONE : View.VISIBLE);
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleId = R.string.order_confirm;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mAmountView.setOnAmountChangeListener(this);
        mSwitchDefault.setOnCheckedChangeListener(this);
        mBtnOk.setOnClickListener(this);
        mLayoutAddress.setOnClickListener(this);
        mSegmentTabLayout.setOnTabSelectListener(this);
    }

    @OnClick(R.id.aoc_identity)
    void onIdentityClick() {
        if (mAddressId > 0) {
            IdCardEditActivity.startForResult(this, mIdCardUserName, mAddressId);
        }
    }

    @Override
    protected OrderConfirmationPresenter createPresenter() {
        return new OrderConfirmationPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_order_confirmation;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mRlBillType.setVisibility(View.VISIBLE);
            mLlBillContent.setVisibility(View.VISIBLE);
        } else {
            mRlBillType.setVisibility(View.GONE);
            mLlBillContent.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAmountChange(View view, int amount) {
        mTvGoodsNum.setText(amount + "");
        if (amount == 0) {
            mTvTotalPrice.setText(NumberUtil.Instance.formatPrice(mPresenter.mEntity.getGood().getActPrice()));
        } else {
            Double actPrice = mPresenter.mEntity.getGood().getActPrice();//单价
            Double totoalPrice = actPrice * amount;
            mTvTotalPrice.setText(NumberUtil.Instance.formatPrice(totoalPrice));
        }

        if (Constants.ORDER_TYPE_ACTIVE == mPresenter.mOrderType) {
            mTvIntegral.setText("获赠" + 690 * amount + "积分");
        }

    }

    @Override
    public void onNoAddress() {
        mRlAddress.setVisibility(View.GONE);
        mTvNoAddress.setVisibility(View.VISIBLE);
        mAddressId = -1;

    }

    @Override
    public void setAddressView(AddressEntity.ResultsBean entity) {
        mIdCardUserName = entity.getName();
        mRlAddress.setVisibility(View.VISIBLE);
        mTvNoAddress.setVisibility(View.GONE);
        mTvConsigneeName.setText(entity.getName());
        mTvConsigneePhone.setText(entity.getMobile());
        mTvConsigneeAddress.setText(entity.getProv() + entity.getCity() + entity.getDistrict() + " " + entity.getDetail());
        mAddressId = entity.getAddressId();

        if (mPresenter.isCrossBorder() && (isCompleteIdCard = entity.isCompleteIdcard())) {
            mIdentityState.setText(getString(R.string.info_complete));
            mIdentityState.setTextColor(UIUtils.getColor(R.color.color_edt_hint));
        } else {
            mIdentityState.setText(getString(R.string.info_incomplete));
            mIdentityState.setTextColor(UIUtils.getColor(R.color.colorBlue));
        }
    }

    @Override
    public void setIntegralViewHidden() {
        mRlIntegral.setVisibility(View.GONE);
    }

    @Override
    public void setIntegralViewShow() {
        mRlIntegral.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_address:
                if (!FastClickUtil.isFastDoubleClick()) {
                    AddressManageActivity.startForResult(this, REQUST_CODE);
                }
                break;
            case R.id.btn_ok:
                if (!FastClickUtil.isFastDoubleClick()) {
                    mPresenter.goOrder(mAddressId, mTvGoodsNum.getText().toString().trim(), mSwitchDefault.isChecked(), mSegmentTabLayout.getCurrentTab(),
                            mEtBillContent.getText().toString().trim(), mEtBillCode.getText().toString().trim(), isCompleteIdCard);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (resultCode == RESULT_OK) {
            finish();
        }*/

        if (resultCode == RESULT_OK) {
            AddressEntity.ResultsBean entity = (AddressEntity.ResultsBean) data.getSerializableExtra(Constants.EXTRA_ENTITY);
            if (entity != null) {
                setAddressView(entity);
            }
        }


    }


    @Override
    public void onTabSelect(int position) {
        if (position == 0) {
            mLlBillCode.setVisibility(View.GONE);
        } else {
            mLlBillCode.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTabReselect(int position) {

    }

    private void showService(List<GoodsDetailEntity.GoodBean.ServiceBean> service
            , LinearLayout mLlService, TextView mService1, TextView mService2, TextView mService3) {
        if (service != null && service.size() > 0) {
            mLlService.setVisibility(View.VISIBLE);
            if (service.size() == 1) {
                mService1.setText(service.get(0).getTitle());
                mService1.setVisibility(View.VISIBLE);
            } else if (service.size() == 2) {
                mService1.setText(service.get(0).getTitle());
                mService1.setVisibility(View.VISIBLE);

                mService2.setText(service.get(1).getTitle());
                mService2.setVisibility(View.VISIBLE);
            } else {
                mService1.setText(service.get(0).getTitle());
                mService1.setVisibility(View.VISIBLE);

                mService2.setText(service.get(1).getTitle());
                mService2.setVisibility(View.VISIBLE);

                mService3.setText(service.get(2).getTitle());
                mService3.setVisibility(View.VISIBLE);
            }
        } else {
            mLlService.setVisibility(View.GONE);
        }
    }
}
