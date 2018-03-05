package com.drops.waterdrop.ui.mine.presenter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.help.PayHelper;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.mine.adapter.VIPGoodAdapter;
import com.drops.waterdrop.ui.mine.view.IMemberCenterView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.BrandItemEntity;
import com.netease.nim.uikit.model.MemberActiveEntitiy;
import com.netease.nim.uikit.model.UserCenterEntity;
import com.netease.nim.uikit.model.VipAreaEntity;
import com.netease.nim.uikit.model.WechatPayDetail;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/19 13:21
 */

public class MemberCenterPresenter extends BasePresenter<IMemberCenterView> {

    private ClipboardManager clipboardManager;
    private VIPGoodAdapter mLoadMoreAdapter;
    private Map<String, Object> map;
    private String mNextSearchStart;
    private int mRealTotalSize;

    public MemberCenterPresenter(BaseActivity context) {
        super(context);
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public void requestActiveMember(String activeCode){
        Map<String, Object> map = new HashMap<>();
        map.put("active_code", activeCode);
        Observable<BaseResponse<MemberActiveEntitiy>> observable = HttpUtil.getInstance().sApi.activeMember(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<MemberActiveEntitiy>(mContext, true) {
            @Override
            protected void _onNext(MemberActiveEntitiy userCenterEntity) {
                if (userCenterEntity == null)
                    return;

                if (userCenterEntity.isActive()){
                    getView().setMemberInfo(userCenterEntity);
                } else {
                    ToastUtil.showLong("激活失败！");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    public void copyWxToClipboard(){
        ClipData mClipData = ClipData.newPlainText("Wx", "水滴无界Links");
        clipboardManager.setPrimaryClip(mClipData);
        ToastUtil.showLong("已复制到剪贴板");
    }

    public void setLoadMoreAdapter(VIPGoodAdapter mAdapter) {
        mLoadMoreAdapter = mAdapter;
    }

    public void getData(final boolean isRefresh, final boolean isLoadmore) {
        if (isLoadmore) {
            map.put(RequestParams.search_start, mNextSearchStart);
        } else {
            map = new HashMap<>();
        }

        Observable<BaseResponse<VipAreaEntity>> observable = HttpUtil.getInstance().sApi.getVipGoodList(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<VipAreaEntity>(mContext, !isLoadmore && !isRefresh) {

            @Override
            protected void _onNext(VipAreaEntity entity) {
                getView().onGetActiveData(entity.getBanner());
                List<VipAreaEntity.ResultsBean> results = entity.getResults();
                if (results != null && results.size() > 0) {
                    mNextSearchStart = entity.getNextSearchStart();
                    if (isLoadmore) {
                        mRealTotalSize += results.size();
                        mLoadMoreAdapter.addData(results);
                        mLoadMoreAdapter.loadMoreComplete();
                    } else {
                        mRealTotalSize = results.size();
                        mLoadMoreAdapter.setNewData(results);
                    }

                    if (mRealTotalSize >= entity.getTotalSize()) {
                        mLoadMoreAdapter.loadMoreEnd();
                        getView().setRefreshEnable(true);
                    }
                }

                if (isLoadmore) {
                    getView().setRefreshEnable(true);
                }

                if (isRefresh) {
                    getView().setRefresh(false);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                if (isLoadmore) {
                    mLoadMoreAdapter.loadMoreFail();
                } else if (isRefresh){
                    getView().setRefresh(false);
                }
            }
        });
    }

    public void applyPayForMember(){
        Observable<BaseResponse<WechatPayDetail>> observable = HttpUtil.getInstance().sApi.applyPayForMember(RequestBodyUtils.build(null));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<WechatPayDetail>(mContext, true) {
            @Override
            protected void _onNext(WechatPayDetail wechatPayDetail) {
                WaterDropApp.PAY_FROM = Constants.PAY_FROM_MEMBER_CENTER;
                PayHelper.Instance.payWeChat(wechatPayDetail);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
}
