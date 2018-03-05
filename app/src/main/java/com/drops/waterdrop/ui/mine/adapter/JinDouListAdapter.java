package com.drops.waterdrop.ui.mine.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.OrderState;
import com.drops.waterdrop.ui.mine.activity.GoldenBeanActivity;
import com.drops.waterdrop.ui.mine.activity.ScoreActivity;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.model.JinDouEntity;
import com.netease.nim.uikit.model.OrderEntity;

import static com.drops.waterdrop.util.sys.UIUtils.getColor;

/**
 * Created by HZH on 2017/6/29.
 */

public class JinDouListAdapter extends BaseQuickAdapter<JinDouEntity.ResultsBean, BaseViewHolder> {

    public JinDouListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_jindou_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, JinDouEntity.ResultsBean item) {
        setLayoutVisible(helper, item);
    }

    private void setLayoutVisible(BaseViewHolder helper, final JinDouEntity.ResultsBean item) {
        TextView jindouWay = helper.getView(R.id.tv_jindou_way);
        TextView jindouTime = helper.getView(R.id.tv_jindou_time);
        TextView jindouSum = helper.getView(R.id.tv_jindou_sum);

        String beans = item.getBeans();
        jindouSum.setText(beans);
        Double aLong = Double.valueOf(beans);
        if (aLong > 0) {
            jindouSum.setText("+" + beans);
            jindouSum.setTextColor(getColor(R.color.jindou_add));
        } else {
            jindouSum.setText("-" + beans);
            jindouSum.setTextColor(getColor(R.color.jindou_cut));
        }

        jindouTime.setText(item.getCreateTime());
        // 受益人收益方式，1表示通过水贴收益，2表示通过水塘收益
        jindouWay.setText(item.getLabel());
    }


}
