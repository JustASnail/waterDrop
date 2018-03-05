package com.drops.waterdrop.ui.mine.adapter;

import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.mine.activity.AddAddressActivity;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by HZH on 2017/7/5.
 */

public class AddressManageAdapter extends BaseQuickAdapter<AddressEntity.ResultsBean, BaseViewHolder> {

    private AlertDialog.Builder mBuilder;

    public AddressManageAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_address_manage);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AddressEntity.ResultsBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_phone, item.getMobile());
        helper.setText(R.id.tv_address, item.getProv() + item.getCity() + item.getDistrict() + " " + item.getDetail());

        final AppCompatCheckBox checkBox = helper.getView(R.id.accb_set_default);

        checkBox.setChecked(item.getDefaultFlag() == 1);

        if (item.getDefaultFlag() == 1) {
            checkBox.setEnabled(false);
            checkBox.setText("默认地址");
        } else {
            checkBox.setEnabled(true);
            checkBox.setText("设为默认");
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddress(item);
            }
        });


        helper.setOnClickListener(R.id.tv_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressActivity.start(mContext, AddAddressActivity.TYPE_EDIT, item);
            }
        });

        helper.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(helper, item);
            }
        });
    }

    private void updateAddress(AddressEntity.ResultsBean item) {
//        body.setDefault_flag("1");

        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.address_id, item.getAddressId());
        map.put(RequestParams.name, item.getName());
        map.put(RequestParams.mobile, item.getMobile());
        map.put(RequestParams.prov, item.getProv());
        map.put(RequestParams.city, item.getCity());
        map.put(RequestParams.district, item.getDistrict());
        map.put(RequestParams.detail, item.getDetail());
        map.put(RequestParams.default_flag, "1");
        Observable<BaseResponse<AddressEntity.ResultsBean>> observable = HttpUtil.getInstance().sApi.updateAddress(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity.ResultsBean>(mContext, "正在更新...") {
            @Override
            protected void _onNext(AddressEntity.ResultsBean addressInsertEntity) {
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onUpdate();
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    public void removeAddress(AddressEntity.ResultsBean address) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.address_id, address.getAddressId());
        Observable<BaseResponse<AddressEntity.ResultsBean>> observable = HttpUtil.getInstance().sApi.removeAddress(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddressEntity.ResultsBean>(mContext, "正在删除...") {
            @Override
            protected void _onNext(AddressEntity.ResultsBean addressInsertEntity) {
                ToastUtil.showShort("删除成功");
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onUpdate();
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }


    public void showDialog(final BaseViewHolder helper, final AddressEntity.ResultsBean address) {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(mContext);
            mBuilder.setTitle("温馨提示");
            mBuilder.setMessage("确定删除该地址吗？");
            mBuilder.setNegativeButton("取消", null);
        }

        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeAddress(address);
            }
        });
        mBuilder.show();
    }

    public interface onUpdateListener {
        void onUpdate();
    }

    private onUpdateListener mOnUpdateListener;

    public void setOnUpdateListener(onUpdateListener onUpdateListener) {
        mOnUpdateListener = onUpdateListener;
    }
}
