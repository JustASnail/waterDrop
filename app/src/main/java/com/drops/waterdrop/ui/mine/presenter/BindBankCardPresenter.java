package com.drops.waterdrop.ui.mine.presenter;

import android.text.TextUtils;

import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.view.IBindBankCardView;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.widget.KVCombinationView;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.QiNiuUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BankCardNoListEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.QiNiuTokensEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/29 23:59
 */

public class BindBankCardPresenter extends BasePresenter<IBindBankCardView> {

    public BindBankCardPresenter(BaseActivity context) {
        super(context);
    }

    private String getString(int rid) {
        return mContext.getString(rid);
    }

    public void checkBindBankCardParam(KVCombinationView mCompanyAccount, KVCombinationView mCompanyName,
                                       KVCombinationView mBankName, KVCombinationView mCreateAccountProvince,
                                       KVCombinationView mCreateAccountCity, KVCombinationView mCreateAccountBank,
                                       String transferCertificateImageKey) {
        if (mCompanyAccount.isNullValue()) {
            ToastUtil.showLong(getString(R.string.hint_company_account));
            return;
        }
        if (mCompanyName.isNullValue()) {
            ToastUtil.showLong(getString(R.string.hint_company_name));
            return;
        }
        if (mBankName.isNullValue()) {
            ToastUtil.showLong(getString(R.string.hint_bank_name));
        }
        if (mCreateAccountProvince.isNullValue() || mCreateAccountCity.isNullValue()) {
            ToastUtil.showLong("请选择开户的省和市");
            return;
        }
        if (mCreateAccountBank.isNullValue()) {
            ToastUtil.showLong(getString(R.string.hint_create_account_bank));
            return;
        }
        if (TextUtils.isEmpty(transferCertificateImageKey)){
            ToastUtil.showLong("请上传凭证图片");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("card_no", mCompanyAccount.getValue());
        map.put("user_name", mCompanyName.getValue());
        map.put("bank_name", mBankName.getValue());
        map.put("card_prov", mCreateAccountProvince.getValue());
        map.put("card_city", mCreateAccountCity.getValue());
        map.put("card_deposit", mCreateAccountBank.getValue());
        map.put("submit_photo", transferCertificateImageKey);
        map.put(RequestParams.type, MyUserCache.getVipOrSuply());
        bindBankCard(map);
    }

    public void bindBankCard(Map<String, Object> map) {
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.addBankCard(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext, true) {
            @Override
            protected void _onNext(Object o) {
                getView().onSubmitSuccess();
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showLong(message);
            }
        });
    }

    public void getBankCardNoList(){
        Observable<BaseResponse<BankCardNoListEntity>> observable = HttpUtil.getInstance().sApi.getBankCardNO();
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<BankCardNoListEntity>(mContext, true) {
            @Override
            protected void _onNext(BankCardNoListEntity bankCardNoListEntity) {
                if (bankCardNoListEntity.getResults() != null && bankCardNoListEntity.getResults().size() > 0){
                    getView().onGetBankCardNos(bankCardNoListEntity.getResults());
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showLong(message);
            }
        });
    }

    public void uploadTransferCerificateImage(String path){
        File file = new File(path);
        if (!file.exists()){
            ToastUtil.showLong("保存图片失败，请重试");
            return;
        }

        getQiNiuToken(file);
    }

    private void getQiNiuToken(final File file) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.size, "1");
        map.put(RequestParams.type, 1);
        Observable<BaseResponse<QiNiuTokensEntity>> observable = HttpUtil.getInstance().sApi.getQiNiuToken(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<QiNiuTokensEntity>(mContext, true) {
            @Override
            protected void _onNext(QiNiuTokensEntity dataBean) {
                if (dataBean.getResults() != null && dataBean.getResults().size() == 1){
                    QiNiuTokensEntity.ResultsBean resultsBean = dataBean.getResults().get(0);
                    uploadToQiNiu(file, resultsBean);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showLong(message);
            }
        });
    }

    private void uploadToQiNiu(File file, final QiNiuTokensEntity.ResultsBean resultsBean) {
        QiNiuUtil.Instance.putImg(file, resultsBean.getAttachName(), resultsBean.getToken(), new QiNiuUtil.OnPutImgListener() {
            @Override
            public void onPutSucceed(String key) {
                getView().onGetQiNiuImageKey(resultsBean.getDomain() + resultsBean.getAttachName(), key);
            }

            @Override
            public void onPutError() {
                ToastUtil.showLong("获取图片失败");
            }
        });
    }
}
