package com.drops.waterdrop.ui.mine.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.KVCombinationView;
import com.drops.waterdrop.widget.TopYuanJiaoImageView;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AppInfoEntity;
import com.netease.nim.uikit.model.BaseResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import rx.Observable;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/31 19:55
 */

public class TransferInfoDialog extends DialogFragment {

    private static final String ARG_IS_VIP = "arg_is_vip";
    @Bind(R.id.ftid_create_account_bank)
    KVCombinationView mCreateAccountBank;
    @Bind(R.id.ftid_account_name)
    KVCombinationView mAccountName;
    @Bind(R.id.ftid_account)
    KVCombinationView mAccount;
    @Bind(R.id.ftid_head_img)
    TopYuanJiaoImageView mHeadImg;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.fragment_transfer_info_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setCancelable(false);

        Glide.with(this)
                .load(R.mipmap.img_wdzh_tc_tt)
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 30, 0, RoundedCornersTransformation.CornerType.TOP))
                .into(mHeadImg);

        requestAppInfo();
    }

    private void requestAppInfo(){
        Observable<BaseResponse<AppInfoEntity>> observable = HttpUtil.getInstance().sApi.getAppInfo();
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AppInfoEntity>(getActivity()) {
            @Override
            protected void _onNext(AppInfoEntity appInfoEntity) {
                List<AppInfoEntity.ResultsBean> resultsBeen = appInfoEntity.getResults();
                if (resultsBeen != null){
                    for (AppInfoEntity.ResultsBean bean : resultsBeen){
                        if (TextUtils.isEmpty(bean.getParamKey()))
                            continue;

                        if (isVip()) {
                            switch (bean.getParamKey()){
                                case "MEMBER_DEPOSIT_BANK":
                                    mCreateAccountBank.setValue(bean.getParamValue());
                                    break;
                                case "MEMBER_ACCOUNT_NAME":
                                    mAccountName.setValue(bean.getParamValue());
                                    break;
                                case "MEMBER_BANK_NO":
                                    mAccount.setValue(bean.getParamValue());
                                    break;
                            }
                        } else {
                            switch (bean.getParamKey()){
                                case "DEPOSIT_BANK":
                                    mCreateAccountBank.setValue(bean.getParamValue());
                                    break;
                                case "ACCOUNT_NAME":
                                    mAccountName.setValue(bean.getParamValue());
                                    break;
                                case "BANK_NO":
                                    mAccount.setValue(bean.getParamValue());
                                    break;
                            }
                        }
                    }
                } else {
                    ToastUtil.showShort("请求出错了");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    @OnClick(R.id.ftid_close)
    void onCloseClick(){
        dismiss();
    }

    public boolean isVip() {
        return MyUserCache.getVipOrSuply() == 2;
    }
}
