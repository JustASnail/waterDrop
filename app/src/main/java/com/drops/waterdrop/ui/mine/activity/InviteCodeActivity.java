package com.drops.waterdrop.ui.mine.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.other.activity.QRCodeActivity;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.UpdateUserInfoEntity;
import com.netease.nim.uikit.request_body.RequestParams;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observable;
import rx.functions.Action1;

import static com.drops.waterdrop.ui.other.activity.CopyQRCodeActivity.REQUEST_CODE;

/**
 * Created by Mr.Smile on 2017/7/3.
 */

public class InviteCodeActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_invite_code)
    TextView tvInviteCode;
    @Bind(R.id.iv_saomiao)
    ImageView ivSaomiao;
    @Bind(R.id.btn_ok)
    TextView btnOk;
    private String inviteCode;

    public static void startForResult(Context context, String inviteCode) {
        Intent starter = new Intent(context, InviteCodeActivity.class);
        starter.putExtra(Constants.QRCODE_INVITE, inviteCode);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.titleString = "明星邀请码填写";
        options.isNeedNavigate = true;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ivSaomiao.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_invite_code;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_saomiao:
                requestCameraPermission();
                break;
            case R.id.btn_ok:
                commitInviteCode();
                break;
        }
    }

    private void requestCameraPermission() {
        RxPermissions rxPermissions = new RxPermissions(InviteCodeActivity.this);
        rxPermissions.setLogging(true);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            Toast.makeText(InviteCodeActivity.this, "请开启相机和存储空间相关权限", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            QRCodeActivity.startForResult(InviteCodeActivity.this);


                        }
                    }
                });

    }

    private void commitInviteCode() {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.invite_code, inviteCode);
        Observable<BaseResponse<UpdateUserInfoEntity>> observable = HttpUtil.getInstance().sApi.updateUserInfo(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<UpdateUserInfoEntity>(this) {
            @Override
            protected void _onNext(UpdateUserInfoEntity entity) {
                ToastUtil.showShort("邀请码提交成功");
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            inviteCode = data.getStringExtra(Constants.INVITATION_CODE);
            tvInviteCode.setText(inviteCode);
        }
    }
}
