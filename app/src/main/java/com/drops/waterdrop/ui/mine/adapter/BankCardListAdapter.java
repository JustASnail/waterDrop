package com.drops.waterdrop.ui.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.BankCardListEntity;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/29 22:19
 */

public class BankCardListAdapter extends BaseQuickAdapter<BankCardListEntity.BankCard, BaseViewHolder> {

    public BankCardListAdapter(Context context, View.OnClickListener addBankCardListener) {
        super(R.layout.item_bank_card_list);

        View footerView = LayoutInflater.from(context).inflate(R.layout.item_bank_card_list_footer, null);
        addFooterView(footerView);
        Button button = (Button) footerView.findViewById(R.id.ibclf_add);
        button.setOnClickListener(addBankCardListener);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCardListEntity.BankCard item) {
        helper.setText(R.id.ibcl_name, item.getBankName());
        helper.setText(R.id.ibcl_card_num, item.getCardNoForFormat2());
        helper.setText(R.id.ibcl_state, item.getReviewState());

        ImageView imageView = helper.getView(R.id.ibcl_bg);
        GlideUtil.showImageView(mContext, item.getCardPhoto(), imageView);
    }
}
