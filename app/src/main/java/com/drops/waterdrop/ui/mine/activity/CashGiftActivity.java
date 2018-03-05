package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.GiftInfoEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by Mr.Smile on 2017/10/10.
 */

public class CashGiftActivity extends BaseActivity {

    @Bind(R.id.iv_left)
    ImageView mIvLeft;
    @Bind(R.id.tv_record)
    TextView mTvRecord;
    @Bind(R.id.et_code)
    TextView mEtCode;
    @Bind(R.id.btn_ok)
    Button mBtnOk;

    public static void start(Context context) {
        Intent starter = new Intent(context, CashGiftActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void initView() {
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
        return R.layout.activity_cash_gift;
    }


    @OnClick({R.id.iv_left, R.id.tv_record, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_record:
                CashGiftRecordActivity.start(this);
                break;
            case R.id.btn_ok:
                String code = mEtCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShort("请先输入兑换码");
                } else {
                    getGiftInfo(code);
                }
                break;
        }
    }

    private void getGiftInfo(final String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.exchange_code, code);
        final Observable<BaseResponse<GiftInfoEntity>> giftInfo = HttpUtil.getInstance().sApi.getGiftInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(giftInfo, new ProgressSubscriber<GiftInfoEntity>(this) {
            @Override
            protected void _onNext(GiftInfoEntity giftInfoEntity) {
                if (giftInfoEntity != null) {
                    if (giftInfoEntity.getStatus() == 0) {
                        jumpGiftInfoPage(giftInfoEntity);
                    } else if (giftInfoEntity.getStatus() == 1) {
                        ToastUtil.showShort("1");
                    } else if (giftInfoEntity.getStatus() == 10) {
                        ToastUtil.showShort("10");
                    } else if (giftInfoEntity.getStatus() == 20) {
                        ToastUtil.showShort("20");
                    }
                } else {
                    ToastUtil.showShort("兑换码不存在");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
               /* GiftInfoEntity entity = new GiftInfoEntity();
                entity.setGiftId(123456);
                entity.setExchangeCode("123456");
                entity.setGiftTitle("周杰伦");
                entity.setGiftPhoto("https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=cbea3a5cfd36afc31a013737d27080a1/8ad4b31c8701a18b4f7365d3942f07082938fe96.jpg");
                entity.setGiftDesc("走过千山万水" +
                        "在我心里你永远是那么美" +
                        "既然爱了就不后悔" +
                        "再多的苦我也愿意背" +
                        "我的爱如潮水" +
                        "爱如潮水将我向你推");
                jumpGiftInfoPage(entity);*/
            }
        });
    }


    private void jumpGiftInfoPage(GiftInfoEntity giftInfoEntity) {
        CashGiftAddressActivity.start(this, giftInfoEntity);
    }
}
