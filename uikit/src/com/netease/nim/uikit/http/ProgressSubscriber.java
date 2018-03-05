package com.netease.nim.uikit.http;

import android.content.Context;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {


    private SimpleLoadDialog dialogHandler;

    private boolean showPorgress = true;


    public ProgressSubscriber(Context context) {
        dialogHandler = new SimpleLoadDialog(context, this, true, null);
    }

    public ProgressSubscriber(Context context, String progressMsg) {
        dialogHandler = new SimpleLoadDialog(context, this, true, progressMsg);
    }

    public ProgressSubscriber(Context context, boolean isShowProgress) {
        dialogHandler = new SimpleLoadDialog(context, this, true, null);
        showPorgress = isShowProgress;
    }


    /**
     * 显示Dialog
     */
    public void showProgressDialog() {
        if (!showPorgress) return;

        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.SHOW_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.show();
        }
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog() {
        if (!showPorgress) return;

        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.dismiss();
            ;
            dialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onNext(T t) {

        _onNext(t);
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) { //这里自行替换判断网络的代码
            _onError("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            _onError("网络中断，请检查您的网络状态");
        } else if (e instanceof ApiException) {
            _onError(e.getMessage());
        } else if (e instanceof SecurityException) {
            _onError("请求失败，请查看相关权限是否打开...");
        } else if (e instanceof HttpException) {
            _onError("网络中断，请检查您的网络状态");
        } else {
            _onError("请求失败：" + e.getMessage());
        }
        dismissProgressDialog();
    }


    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }


    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}