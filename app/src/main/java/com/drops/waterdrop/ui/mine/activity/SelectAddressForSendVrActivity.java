package com.drops.waterdrop.ui.mine.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.presenter.SelectAddressForSendVrPresenter;
import com.drops.waterdrop.ui.mine.view.SelectAddressForSendVrView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.util.sys.UIUtils;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.MemberActiveEntitiy;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/10/12 14:48
 */

public class SelectAddressForSendVrActivity extends BaseActivity<SelectAddressForSendVrView, SelectAddressForSendVrPresenter> implements SelectAddressForSendVrView {

    public static void startActivity(Activity context, MemberActiveEntitiy entitiy){
        Intent intent = new Intent(context, SelectAddressForSendVrActivity.class);
        intent.putExtra(Constants.EXTRA_OBJ, entitiy);
        context.startActivityForResult(intent, 200);
    }

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

    @Bind(R.id.asafsv_gift_img)
    ImageView mGiftImg;
    @Bind(R.id.asafsv_gift_name)
    TextView mName;
    @Bind(R.id.asafsv_gift_content)
    TextView mContent;

    private MemberActiveEntitiy memberActiveEntitiy;
    private long mAddressId = -1;
    private boolean mHasDefaultAddress;
    private AlertDialog dialog;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        memberActiveEntitiy = (MemberActiveEntitiy) getIntent().getSerializableExtra(Constants.EXTRA_OBJ);

        MemberActiveEntitiy.Banner banner = memberActiveEntitiy.getBanner();
        GlideUtil.showImageView(this, banner.getBannerPhoto(), mGiftImg);
        mName.setText(banner.getTitle());
        mContent.setText(banner.getDesc());

        mPresenter.getAddressList();
    }

    @Override
    protected void initListener() {
    }

    @OnClick(R.id.asafsv_select_addr)
    void onSelectAddressClick(){
        if (!FastClickUtil.isFastDoubleClick()) {
            AddressManageActivity.startForResult(this, 200);
        }
    }

    @OnClick(R.id.asafsv_ok)
    void onOkClick(){
        if (mAddressId <= 0){
            ToastUtil.showLong("请添加地址");
            return;
        }
        showDialog();
    }

    private void showDialog(){
        if (dialog == null){
            dialog = new AlertDialog.Builder(this)
                    .setMessage("地址一旦填写无法修改")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request();
                        }
                    })
                    .create();
        }
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(UIUtils.getColor(R.color.colorBlue));
    }

    private void request(){
        mPresenter.sendAddressForMemberActiveGift(memberActiveEntitiy.getActiveCode(), Long.toString(mAddressId));
    }

    @Override
    protected SelectAddressForSendVrPresenter createPresenter() {
        return new SelectAddressForSendVrPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_select_address_for_send_vr;
    }

    @Override
    public void onGetAddress(List<AddressEntity.ResultsBean> results) {
        for (AddressEntity.ResultsBean result : results) {
            if (result.getDefaultFlag() == 1) {//是默认地址
                setAddressView(result);

                mHasDefaultAddress = true;
            }
        }

        if (!mHasDefaultAddress) {
            setAddressView(results.get(0));
        }
    }

    public void setAddressView(AddressEntity.ResultsBean entity) {
        mRlAddress.setVisibility(View.VISIBLE);
        mTvNoAddress.setVisibility(View.GONE);
        mTvConsigneeName.setText(entity.getName());
        mTvConsigneePhone.setText(entity.getMobile());
        mTvConsigneeAddress.setText(entity.getProv() + entity.getCity() + entity.getDistrict() + " " + entity.getDetail());
        mAddressId = entity.getAddressId();
    }

    @Override
    public void onNoAddress() {
        mRlAddress.setVisibility(View.GONE);
        mTvNoAddress.setVisibility(View.VISIBLE);
        mAddressId = -1;
    }

    @Override
    public void onSendSucc() {
        ToastUtil.showLong("激活成功！");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        ToastUtil.showLong("请准确填写收货地址以便能及时收到水滴高清VR眼镜！");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            AddressEntity.ResultsBean entity = (AddressEntity.ResultsBean) data.getSerializableExtra(Constants.EXTRA_ENTITY);
            if (entity != null) {
                setAddressView(entity);
            }
        }
    }
}
