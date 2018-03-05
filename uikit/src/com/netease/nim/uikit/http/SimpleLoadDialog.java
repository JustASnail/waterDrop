package com.netease.nim.uikit.http;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.netease.nim.uikit.common.ui.dialog.DialogMaker;

import java.lang.ref.WeakReference;

public class SimpleLoadDialog extends Handler {

//    private Dialog load = null;

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Context context;
    private boolean cancelable;
    private String mProgressMsg;
    private ProgressCancelListener mProgressCancelListener;
    private final WeakReference<Context> reference;

    public SimpleLoadDialog(Context context, ProgressCancelListener mProgressCancelListener,
                            boolean cancelable, String progressMsg) {
        super();
        this.reference = new WeakReference<Context>(context);
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
        this.mProgressMsg = TextUtils.isEmpty(progressMsg)?"加载中..." : progressMsg;
    }

    private void create() {
        context = reference.get();

        DialogMaker.showProgressDialog(context, null, mProgressMsg, cancelable, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mProgressCancelListener != null) {
                    mProgressCancelListener.onCancelProgress();
                }
                DialogMaker.dismissProgressDialog();
            }
        }).setCanceledOnTouchOutside(false);


    /*    if (load == null) {
            context  = reference.get();

            load = new Dialog(context, R.style.loadstyle);
            View dialogView = LayoutInflater.from(context).inflate(
                    R.layout.custom_sload_layout, null);
            load.setCanceledOnTouchOutside(false);
            load.setCancelable(cancelable);
            load.setContentView(dialogView);
            load.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(mProgressCancelListener!=null)
                        mProgressCancelListener.onCancelProgress();
                }
            });
            Window dialogWindow = load.getWindow();
            dialogWindow.setGravity(Gravity.CENTER_VERTICAL
                    | Gravity.CENTER_HORIZONTAL);
        }
        if (!load.isShowing()&&context!=null) {
            load.show();
        }*/
    }

    public void show() {
        create();
    }


    public void dismiss() {
       /* context  = reference.get();
        if (load != null&&load.isShowing()&&!((Activity) context).isFinishing()) {
            String name = Thread.currentThread().getName();
            load.dismiss();
            load = null;
        }*/

        DialogMaker.dismissProgressDialog();

    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                create();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismiss();
                break;
        }
    }
}