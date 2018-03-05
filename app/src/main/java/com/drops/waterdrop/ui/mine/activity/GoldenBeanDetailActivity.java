package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.presenter.GoldenBeanPresenter;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.model.JinDouEntity;

import java.io.Serializable;

import butterknife.Bind;

/**
 * Created by Mr.Smile on 2017/6/26.
 */

public class GoldenBeanDetailActivity extends BaseActivity {

    @Bind(R.id.tv_bean)
    TextView tvBean;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_desc)
    TextView tvDesc;

    public static void start(Context context,JinDouEntity.ResultsBean data) {
        Intent starter = new Intent(context, GoldenBeanDetailActivity.class);
        starter.putExtra(Constants.EXTRA_ENTITY, data);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "金豆详情";
        options.isNeedNavigate = true;
        setMyToolbar(options);
        initList();
    }

    private void initList() {

    }


    @Override
    protected void initData() {
        JinDouEntity.ResultsBean bean = (JinDouEntity.ResultsBean) getIntent().getSerializableExtra(Constants.EXTRA_ENTITY);

        String beans = bean.getBeans();
        Double aLong = Double.valueOf(beans);
        if (aLong > 0) {
            tvBean.setText("+" + beans);
            tvBean.setTextColor(getResources().getColor(R.color.jindou_add));
        } else {
            tvBean.setText("-" + beans);
            tvBean.setTextColor(getResources().getColor(R.color.jindou_cut));
        }

        tvTime.setText(bean.getCreateTime());
        tvDesc.setText(bean.getLabel());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected GoldenBeanPresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_jindou_detail;
    }

}
