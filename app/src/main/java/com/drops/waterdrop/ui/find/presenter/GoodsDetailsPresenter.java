package com.drops.waterdrop.ui.find.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.find.view.IGoodsDetailsView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.GoodsDetailEntity;
import com.netease.nim.uikit.model.GoodsScoreListEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/6/7.
 */

public class GoodsDetailsPresenter extends BasePresenter<IGoodsDetailsView> {


    private String mNextSearchStart;
    private int mRealTotalSize;

    private BaseQuickAdapter mLoadMoreAdapter;
    private BaseRequestBody mBody;
    public GoodsDetailEntity mEntity;
    public String mGoodsId;
    public String mTipName;
    public long mDropId;
    public long mTipId;
    private HashMap<String, Object> map;
    private boolean buyEnable = false;
    public boolean buyEnableBySku = false;


    public GoodsDetailsPresenter(BaseActivity context) {
        super(context);
    }

    public void parseIntent(Intent intent) {
        mEntity = (GoodsDetailEntity) intent.getSerializableExtra(Constants.EXTRA_ENTITY);
        mDropId = intent.getLongExtra(Constants.EXTRA_DROP_ID, 0);
        mTipId = intent.getLongExtra(Constants.EXTRA_TIP_ID, 0);
        mGoodsId = intent.getStringExtra(Constants.EXTRA_GOODS_ID);
        mTipName = intent.getStringExtra(Constants.EXTRA_TIP_NAME);


        if (mEntity == null && !TextUtils.isEmpty(mGoodsId)) {
            getGoodsDetail(mGoodsId);

        } else if (mEntity != null) {
            mGoodsId = mEntity.getGood().getGoodId();
            getView().updateUI();
            setSkus();


        } else if (mEntity == null && TextUtils.isEmpty(mGoodsId)){
            ToastUtil.showShort("该商品已下架");
            mContext.finish();
        }

    }

    private void setSkus() {
        if (mEntity.getGood().getGoodSkus() != null){
            List<GoodsDetailEntity.GoodBean.GoodSku> goodSkus = mEntity.getGood().getGoodSkus();
            for (GoodsDetailEntity.GoodBean.GoodSku goodSku : goodSkus){
                if (goodSku.getQuantity() > 0){
                    buyEnableBySku = true;
                    break;
                }
            }
        }

        if (mEntity.getGood().getCount() > 0){
            buyEnable = true;
        }
        if (!buyEnable){
            buyEnable = buyEnableBySku;
        }
        getView().setBuyButtonEnable(buyEnable);
    }

    public void getScoreList(final boolean isLoadMore) {
        if (isLoadMore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
            map.put(RequestParams.good_id, mGoodsId);
        }

        Observable<BaseResponse<GoodsScoreListEntity>> observable = HttpUtil.getInstance().sApi.getGoodsScoreList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<GoodsScoreListEntity>(mContext) {
                    @Override
                    protected void _onNext(GoodsScoreListEntity entity) {
                        List<GoodsScoreListEntity.ResultsBean> results = entity.getResults();

                        if (results != null) {
                            mNextSearchStart = entity.getNextSearchStart();
                            if (isLoadMore) {
                                mRealTotalSize += results.size();
                                mLoadMoreAdapter.addData(results);
                                mLoadMoreAdapter.loadMoreComplete();
                            } else {
                                mRealTotalSize = results.size();
                                mLoadMoreAdapter.setNewData(results);
                            }

                            if (mRealTotalSize >= entity.getTotalSize()) {
                                mLoadMoreAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                        if (isLoadMore) {
                            mLoadMoreAdapter.loadMoreFail();
                        } else {
                            mContext.finish();

                        }
                    }
                }

        );
    }

    public void setLoadMoreAdapter(BaseQuickAdapter loadMoreAdapter) {
        mLoadMoreAdapter = loadMoreAdapter;
    }

    private void getGoodsDetail(String goodsId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.good_id, goodsId);
        Observable<BaseResponse<GoodsDetailEntity>> observable = HttpUtil.getInstance().sApi.getGoodsDetail(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<GoodsDetailEntity>(mContext) {
            @Override
            protected void _onNext(GoodsDetailEntity o) {
                if (o != null) {
                    mEntity = o;
                    mGoodsId = o.getGood().getGoodId();
                    getView().onLoadGoodsDetailSucceed(o);
                    setSkus();
                } else {
                    ToastUtil.showShort("此商品异常");
                    mContext.finish();
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                mContext.finish();
            }
        });
    }
}
