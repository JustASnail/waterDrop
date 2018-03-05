package com.drops.waterdrop.ui.mine.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.mine.activity.SeleBankAccountActivity;
import com.drops.waterdrop.ui.mine.activity.WithDrawActivity;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.model.SeleBankAccountEntity;

/**
 * Created by Mr.Smile on 2017/8/25.
 */

public class SeleBankAccountAdapter extends BaseQuickAdapter<SeleBankAccountEntity.ResultsBean,BaseViewHolder>{

    public SeleBankAccountAdapter(int layoutResId) {
        super(R.layout.item_sele_bank);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SeleBankAccountEntity.ResultsBean item) {
        TextView name = helper.getView(R.id.tv_bank_name);
        final ImageView iv = helper.getView(R.id.iv_selected);
        RelativeLayout rl = helper.getView(R.id.rl_bank);

        String cardNoLast4 = WithDrawActivity.getCardNoLast4(item.getCardNo());
        name.setText(item.getBankName() + cardNoLast4);

        String bankNum = MyUserCache.getBankNum();
        if (TextUtils.equals(bankNum, item.getCardNo())) {
            iv.setVisibility(View.VISIBLE);
        }

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setVisibility(View.VISIBLE);

                if (bankSelectedListener != null) {
                    bankSelectedListener.onBankSelected(helper.getPosition());
                }
            }
        });
    }

    private OnBankSelectedListener bankSelectedListener;

    public void setBankSelectedListener(OnBankSelectedListener bankSelectedListener) {
        this.bankSelectedListener = bankSelectedListener;
    }

    public interface OnBankSelectedListener {
        void onBankSelected(int pos);
    }

}
