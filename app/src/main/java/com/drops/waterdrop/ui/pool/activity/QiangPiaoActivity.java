package com.drops.waterdrop.ui.pool.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.util.ToastUtil;

import butterknife.OnClick;

/**
 * Created by dengxiaolei on 2017/6/9.
 */

public class QiangPiaoActivity extends BaseActivity {
    private BottomSheetDialog mBottomDialog;

    public static void start(Context context) {
        Intent starter = new Intent(context, QiangPiaoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "我要抢票";
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_qiang_piao;
    }



    @OnClick({R.id.my_details, R.id.find_friend, R.id.get_integral})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_details:
                MyQiangPiaoDetailsActivity.start(this, 1);
                break;
            case R.id.find_friend:
                onMoreIconClick();

                break;
            case R.id.get_integral:
                MyQiangPiaoDetailsActivity.start(this, 4);
                break;
        }
    }

    private void onMoreIconClick() {
        if (mBottomDialog == null) {
            View inflate = View.inflate(this, R.layout.dialog_pool_more, null);
            TextView tv = (TextView) inflate.findViewById(R.id.tv_inform);
            tv.setText("分享");
            mBottomDialog = new BottomSheetDialog(this);
            mBottomDialog.setContentView(inflate);
            mBottomDialog.setCancelable(true);
            mBottomDialog.setCanceledOnTouchOutside(false);
            inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomDialog.dismiss();
                }
            });

            inflate.findViewById(R.id.tv_inform).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showShort("分享成功");
                    mBottomDialog.dismiss();
                }
            });
        }

        mBottomDialog.show();

    }

}
