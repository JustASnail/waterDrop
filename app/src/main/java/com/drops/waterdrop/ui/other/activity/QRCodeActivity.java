package com.drops.waterdrop.ui.other.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.text.TextUtils;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.other.presenter.QRCodePresenter;
import com.drops.waterdrop.ui.other.view.IQRCodeView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.media.picker.PickImageHelper;
import com.netease.nim.uikit.common.media.picker.activity.PickImageActivity;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.session.constant.Extras;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

/**
 * 只是被邀请码
 * Created by dengxiaolei on 2017/6/26.
 */

public class QRCodeActivity extends BaseActivity<IQRCodeView, QRCodePresenter> implements QRCodeView.Delegate {
    @Bind(R.id.zxingview)
    QRCodeView mQRCodeView;

    public static final int REQUEST_CODE = 10;
    private int REQUEST_CODE_SELECT_IMG = 100;

    public static void startForResult(Context context) {
        Intent starter = new Intent(context, QRCodeActivity.class);
        ((Activity) context).startActivityForResult(starter, REQUEST_CODE);
    }


    @Override
    protected void initView() {
        mQRCodeView.setDelegate(this);

        initTitle();
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "二维码";
        options.rightString = "相册";
        setMyToolbar(options);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onRightTextClick() {
        super.onRightTextClick();
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.crop = false;
        option.multiSelect = false;
//       showSelector(R.string.message_search_title, 100);

        PickImageActivity.start(this, REQUEST_CODE_SELECT_IMG, true, 1, option.outputPath, false,
                1, true, false, 0, 0);

    }


    @Override
    protected QRCodePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_qr_code;
    }


    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();

        mQRCodeView.startSpot();
        mQRCodeView.startSpotAndShowRect();

    }


    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        if (!TextUtils.isEmpty(result) && result.startsWith(Constants.QRCODE_INVITE)) {
            int index = result.indexOf("=");
            result = result.substring(index + 1);
            Intent intent = new Intent();
            intent.putExtra(Constants.INVITATION_CODE, result);
            setResult(RESULT_OK, intent);
            vibrate();
            mQRCodeView.startSpot();
            finish();
        } else {
            ToastUtil.showShort(getResources().getString(R.string.qr_failed));
        }

    }


    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtil.showShort("打开相机出错");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMG && resultCode == RESULT_OK) {
            String scanResult = data.getStringExtra(Extras.EXTRA_SCAN_SELECT_MODE);
            parseQRCode(scanResult);
        }
    }

    public void parseQRCode(final String picturePath) {
        mQRCodeView.showScanRect();
        MyAsyncTask myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute(picturePath);
    }

    public static class MyAsyncTask extends AsyncTask<String, Void, String> {
        private final WeakReference<QRCodeActivity> mActivity;

        public MyAsyncTask(QRCodeActivity activity) {
            mActivity = new WeakReference<QRCodeActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mActivity.get().showProgress();

        }

        @Override
        protected String doInBackground(String... params) {
            return QRCodeDecoder.syncDecodeQRCode(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            DialogMaker.dismissProgressDialog();
            if (TextUtils.isEmpty(result)) {
                ToastUtil.showShort("未发现二维码");
                mActivity.get().setResult(RESULT_CANCELED);
                mActivity.get().finish();
            } else {
                if (!TextUtils.isEmpty(result) && result.startsWith(Constants.QRCODE_INVITE)) {
                    int index = result.indexOf("=");
                    result = result.substring(index + 1);
                    Intent intent = new Intent();
                    intent.putExtra(Constants.INVITATION_CODE, result);
                    mActivity.get().setResult(RESULT_OK, intent);
                    mActivity.get().vibrate();
                    mActivity.get().mQRCodeView.startSpot();
                    mActivity.get().finish();
                } else {
                    ToastUtil.showShort("小主，请您检查网络或确认扫描的二维码！");
                }


            }
        }
    }

    public void showProgress() {
        DialogMaker.showProgressDialog(this, null, "正在识别...", false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                DialogMaker.dismissProgressDialog();
            }
        }).setCanceledOnTouchOutside(false);

    }

}
