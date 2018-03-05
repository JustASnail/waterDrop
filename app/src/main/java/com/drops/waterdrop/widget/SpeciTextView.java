package com.drops.waterdrop.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.drops.waterdrop.R;
import com.drops.waterdrop.util.sys.UIUtils;
import com.netease.nim.uikit.model.GoodsDetailEntity;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/27 01:49
 */

public class SpeciTextView extends AppCompatTextView implements View.OnClickListener {

    private int speciV, speciH;
    private int selectedColor;
    private int unSelectedColor;
    private int enableFalseColor;
    private GoodsDetailEntity.GoodBean.GoodSku bean;
    private OnSelectedListener onSelectedListener;

    public SpeciTextView(Context context) {
        super(context);

        speciV = UIUtils.dip2Px(5);
        speciH = UIUtils.dip2Px(10);
        setPadding(speciH, speciV, speciH, speciV);

        selectedColor = UIUtils.getColor(R.color.text_red);
        unSelectedColor = UIUtils.getColor(R.color.base_gray_85);
        enableFalseColor = UIUtils.getColor(R.color.gray_color);

        setBackgroundResource(R.drawable.selector_speci);
    }

    public void setData(GoodsDetailEntity.GoodBean.GoodSku bean){
        this.bean = bean;
        setText(bean.getSkuName());
        int amount = bean.getQuantity();
        if (amount > 0){
            setUnSelected();
        } else {
            setEnabled(false);
            setTextColor(enableFalseColor);
            setBackgroundResource(R.drawable.shape_speci_enable_false);
        }

        setOnClickListener(this);
    }

    public void setSelected(){
        setSelected(true);
        setTextColor(selectedColor);
    }

    public void setUnSelected(){
        setSelected(false);
        setTextColor(unSelectedColor);
    }

    public void setOnSelectedListener(OnSelectedListener listener){
        onSelectedListener = listener;
    }


    @Override
    public void onClick(View v) {
        if (isSelected()){
            setSelected(false);
            setTextColor(unSelectedColor);
        } else {
            setSelected(true);
            setTextColor(selectedColor);
        }
        if (onSelectedListener != null)
            onSelectedListener.onSelected(bean);
    }

    public interface OnSelectedListener{
        void onSelected(GoodsDetailEntity.GoodBean.GoodSku bean);
    }
}
