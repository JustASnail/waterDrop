package com.drops.waterdrop.ui.other.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.help.SessionHelper;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.mine.activity.InviteCodeActivity;
import com.drops.waterdrop.ui.other.presenter.IIronFansGroupPresenter;
import com.drops.waterdrop.ui.other.view.IIronFansGroupView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.SimpleCallback;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.model.FansGroupEntity;
import com.netease.nimlib.sdk.team.model.Team;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Smile on 2017/7/17.
 */

public class IronFansGroupActivity extends BaseActivity<IIronFansGroupView, IIronFansGroupPresenter> implements IIronFansGroupView {


    @Bind(R.id.tv_group_name)
    TextView tvGroupName;
    @Bind(R.id.tv_pool_name)
    TextView tvPoolName;
    @Bind(R.id.tv_group_num)
    TextView tvGroupNum;
    @Bind(R.id.tv_group_desc)
    TextView tvGroupDesc;
    @Bind(R.id.btn_bottom)
    Button btnBottom;
    private String roomId;


    public static void start(Context context, String roomId) {
        Intent starter = new Intent(context, IronFansGroupActivity.class);
        starter.putExtra(Constants.QRCODE_ROOM, roomId);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        roomId = getIntent().getStringExtra(Constants.QRCODE_ROOM);
        if (!TextUtils.isEmpty(roomId)) {
            mPresenter.getRoomInfo(roomId);
        }
    }

    private void initTitle(String title) {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = title;
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
    }

    @Override
    protected IIronFansGroupPresenter createPresenter() {
        return new IIronFansGroupPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_iiron_fans_group;
    }

    @Override
    public void onGetRoomInfoSuccess(FansGroupEntity fansGroupEntity) {

        tvPoolName.setText(fansGroupEntity.getDropName());

        TeamDataCache.getInstance().fetchTeamById(roomId, new SimpleCallback<Team>() {
            @Override
            public void onResult(boolean success, Team t) {
                if (success && t != null) {
                    tvGroupNum.setText(t.getMemberCount() + "");
                    tvGroupDesc.setText(t.getIntroduce());
                    tvGroupName.setText(t.getName());
                    initTitle(t.getName());
                } else {
                    ToastUtil.showShort(getString(R.string.qr_failed1));
                    finish();
                }
            }
        });

        long dropId = fansGroupEntity.getDropId();
        int joinStatus = fansGroupEntity.getJoinStatus();
        //当前用户是否加入了水塘，1表示加入了，0表示等待审核，-1表示没有申请加入
        bottomButton(joinStatus, dropId);
    }

    @Override
    public void onApplySuccess() {
        btnBottom.setText("等待验证");
        btnBottom.setBackgroundColor(getResources().getColor(R.color.color_gray_d9d9d9));
        btnBottom.setEnabled(false);
    }

    private void bottomButton(int joinStatus, final Long dropId) {
        switch (joinStatus) {
            case -1:
                btnBottom.setText("申请加入");
                btnBottom.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnBottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showApplyDialog(dropId);
                    }
                });
                break;
            case 0:
                btnBottom.setText("等待验证");
                btnBottom.setBackgroundColor(getResources().getColor(R.color.color_gray_d9d9d9));
                btnBottom.setEnabled(false);
                break;
            case 1:
                btnBottom.setText("发起聊天");
                btnBottom.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnBottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SessionHelper.startTeamSession(IronFansGroupActivity.this, roomId);
                    }
                });
                break;
        }
    }

    private void showApplyDialog(final Long dropId) {
        new AlertDialog.Builder(this)
                .setTitle("确认申请")
                .setPositiveButton("取消", null)
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.applyJionRoom(dropId);
                    }
                })
                .show();
    }

}
