package com.drops.waterdrop.ui.find.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.AmountView;
import com.drops.waterdrop.widget.SpeciTextView;
import com.google.android.flexbox.FlexboxLayout;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.GoodsDetailEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/26 14:57
 */

public class SpecificationSelectorDialog {

    public interface Callback{
        void onSelected(GoodsDetailEntity.GoodBean.GoodSku goodSku, int quantity);
        void onClose(GoodsDetailEntity.GoodBean.GoodSku goodSku, int quantity);}


    private Context context;
    private BottomSheetDialog dialog;
    private Callback callback;
    private GoodsDetailEntity.GoodBean goodBean;

    public SpecificationSelectorDialog(Context context, GoodsDetailEntity.GoodBean goodBean){
        this.context = context;
        this.goodBean = goodBean;

        View view = View.inflate(context, R.layout.dialog_specification_selector, null);
        ButterKnife.bind(this, view);
        initView();

        dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().findViewById(com.netease.nim.uikit.R.id.design_bottom_sheet).setBackgroundResource(com.netease.nim.uikit.R.color.transparent);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (callback != null)
                    callback.onClose(selectedBean, mAmount.getAmount());
            }
        });
    }

    public void show(Callback callback){
        this.callback = callback;
        dialog.show();
    }

    @Bind(R.id.dialog_photo)
    ImageView mPhoto;
    @Bind(R.id.dialog_price)
    TextView mPrice;
    @Bind(R.id.dialog_num)
    TextView mNum;
    @Bind(R.id.dialog_selected_spec)
    TextView mSelectSpec;
    @Bind(R.id.dialog_spec_container)
    FlexboxLayout mSpecContainer;
    @Bind(R.id.dialog_amount)
    AmountView mAmount;

    private String numFormat = "库存%d件";
    private String priceFormat = "¥ %s";
    private String defaultPriceFormat = "¥ %s - ¥ %s";
    private String defalutSelectedSpec = "选择 规格";
    private SpeciTextView lastSelected;
    private GoodsDetailEntity.GoodBean.GoodSku selectedBean;

    private void initView(){
        mAmount.setGoods_storage(999);
        mAmount.clearFocus();
        setDefaultValue();

        List<GoodsDetailEntity.GoodBean.GoodSku> beanList = new ArrayList<>();
        beanList.addAll(goodBean.getGoodSkus());
        fillSpec(beanList);
    }

    private void setDefaultValue(){
        setGoodSpeciIcon(goodBean.getActPic());
        setNum(goodBean.getCount());
        setSelectedSpec(defalutSelectedSpec);

        String min = goodBean.getMinPrice();
        String max = goodBean.getMaxPrice();
        if (TextUtils.isEmpty(min))
            min = "";
        if (TextUtils.isEmpty(max))
            max = "";

        if (min.equals(max)){
            setPrice(String.format(priceFormat, max));
        } else {
            setPrice(String.format(defaultPriceFormat, min, max));
        }
    }

    private void setGoodSpeciIcon(String url){
        GlideUtil.showImageView(context, R.mipmap.img_qs_90x90, url, mPhoto);
    }

    private void setPrice(String price){
        mPrice.setText(price);
    }

    private void setNum(int num){
        mNum.setText(String.format(numFormat, num));
    }

    private void setSelectedSpec(String content){
        mSelectSpec.setText(content);
    }

    private void fillSpec(List<GoodsDetailEntity.GoodBean.GoodSku> beanList){
        boolean isOnlyOne = beanList.size() == 1;
        for (int i = 0; i < beanList.size(); i++){
            GoodsDetailEntity.GoodBean.GoodSku bean = beanList.get(i);
            final SpeciTextView tv = new SpeciTextView(context);
            mSpecContainer.addView(tv);

            tv.setData(bean);

            tv.setOnSelectedListener(new SpeciTextView.OnSelectedListener() {
                @Override
                public void onSelected(GoodsDetailEntity.GoodBean.GoodSku goodSku) {
                    if (lastSelected == tv){
                        setDefaultValue();
                        selectedBean = null;
                        lastSelected = null;
                        return;
                    }

                    selectedBean = goodSku;

                    setNum(goodSku.getQuantity());
                    setPrice(String.format(priceFormat, goodSku.getPrice()));
                    setGoodSpeciIcon(goodSku.getPhoto());
                    setSelectedSpec("已选择"+goodSku.getSkuName());

                    if (lastSelected != null){
                        lastSelected.setUnSelected();
                    }
                    lastSelected = tv;
                }
            });

            if (isOnlyOne && tv.isEnabled()){
                tv.performClick();
            }
        }
    }

    @OnClick(R.id.dialog_close)
    void onCloseClick(){
        if (callback != null){
            callback.onClose(selectedBean, mAmount.getAmount());
        }
        dialog.dismiss();
    }

    @OnClick(R.id.dialog_buy)
    void onBuyClick(){
        if (selectedBean == null){
            ToastUtil.showShort("请选择一个规格");
            return;
        }
        if (callback != null)
            callback.onSelected(selectedBean, mAmount.getAmount());

        dialog.dismiss();
    }

}
