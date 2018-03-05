package com.netease.nim.uikit.custom;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.dialog.CustomAlertDialog;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialog;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nim.uikit.contact.ContactEventListener;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;

/**
 * ContactEventListener 通讯录联系人列表一些点击事件的响应处理
 * <p>
 * DefalutContactEventListener 提供了默认处理，其中点击Item 和 Avatar 响应为打开P2P聊天界面
 * <p>
 * Created by hzchenkang on 2016/12/21.
 */

public class DefalutContactEventListener implements ContactEventListener {

    @Override
    public void onItemClick(Context context, String account) {
        // 点击联系人之后，可以选择打开个人信息页面或者聊天页面
        NimUIKit.startP2PSession(context, account);
    }

    @Override
    public void onItemLongClick(final Context context, final String account) {
        // 长按联系人
        CustomAlertDialog dialog = new CustomAlertDialog(context);
        dialog.addItem("删除", new CustomAlertDialog.onSeparateItemClickListener() {
            @Override
            public void onClick() {
                onRemoveFriend(context, account);
            }
        });
        dialog.show();

    }

    @Override
    public void onAvatarClick(Context context, String account) {
        // 点击联系人之后，可以选择打开个人信息页面或者聊天页面
        NimUIKit.startP2PSession(context, account);
    }

    //删除好友
    private void onRemoveFriend(final Context context, final String account) {
        Activity activity = (Activity) context;
        if (!NetworkUtil.isNetAvailable(activity)) {
            Toast.makeText(activity, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }
        EasyAlertDialog dialog = EasyAlertDialogHelper.createOkCancelDiolag(context, context.getString(R.string.remove_friend),
                context.getString(R.string.remove_friend_tip), true,
                new EasyAlertDialogHelper.OnDialogActionListener() {

                    @Override
                    public void doCancelAction() {

                    }

                    @Override
                    public void doOkAction() {
                        DialogMaker.showProgressDialog(context, "", true);
                        NIMClient.getService(FriendService.class).deleteFriend(account).setCallback(new RequestCallback<Void>() {
                            @Override
                            public void onSuccess(Void param) {
                                DialogMaker.dismissProgressDialog();
                                Toast.makeText(context, R.string.remove_friend_success, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed(int code) {
                                DialogMaker.dismissProgressDialog();
                                if (code == 408) {
                                    Toast.makeText(context, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "on failed:" + code, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onException(Throwable exception) {
                                DialogMaker.dismissProgressDialog();
                            }
                        });
                    }
                });
        if (!activity.isFinishing() ) {
            dialog.show();
        }
    }



}
