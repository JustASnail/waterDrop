package com.drops.waterdrop.wbapi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.drops.waterdrop.R;
import com.drops.waterdrop.util.ShareUtils;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;


/**
 * Created by Mr.Smile on 2017/7/12.
 */

public class WBEntryActivity extends AppCompatActivity implements WbShareCallback {
    private  WbShareHandler mWbShareHandler;
    private static WeiboMultiMessage mWeiboMessage;
    private static WebpageObject mWebpageObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 创建微博分享接口实例
        mWbShareHandler = new WbShareHandler(this);
        mWbShareHandler.registerApp();
        initData();
    }

    public static void start(Context context, String url,String title,String picUrl,String desc) {
        Intent starter = new Intent(context, WBEntryActivity.class);
        starter.putExtra(Constants.SHARE_URL, url);
        starter.putExtra(Constants.SHARE_TITLE, title);
        starter.putExtra(Constants.SHARE_PICURL, picUrl);
        starter.putExtra(Constants.SHARE_DESC, desc);
        context.startActivity(starter);
    }

    private void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(Constants.SHARE_URL);
        String title = intent.getStringExtra(Constants.SHARE_TITLE);
        String desc = intent.getStringExtra(Constants.SHARE_DESC);
        String picUrl = intent.getStringExtra(Constants.SHARE_PICURL);

        shareToWeibo(url, title, picUrl,desc);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        setIntent(intent);
        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWbShareHandler.doResultIntent(intent, this);
    }


    @Override
    public void onWbShareSuccess() {
        ToastUtil.showShort("分享成功");
        finish();
    }

    @Override
    public void onWbShareCancel() {
        ToastUtil.showShort("分享取消");
        finish();
    }

    @Override
    public void onWbShareFail() {
        ToastUtil.showShort("分享失败");
        finish();
    }

    private void shareToWeibo(String webUrl, String title, String picUrl, String desc) {
        mWeiboMessage = new WeiboMultiMessage();

        mWeiboMessage.textObject = getTextObj(desc + webUrl);
        //分享网页是这个
//        mWebpageObject = new WebpageObject();
//        mWebpageObject.title = title;//不能超过512
//        mWebpageObject.actionUrl = webUrl;// 不能超过512
//        mWebpageObject.description = desc;//不能超过1024
//        mWebpageObject.identify = UUID.randomUUID().toString();//这个不知道做啥的
//        mWebpageObject.defaultText = "Webpage 默认文案";//这个也不知道做啥的
        new BitmapWorkerTask(WBEntryActivity.this, mWbShareHandler).execute(picUrl);

//上面这些，一条都不能少，不然就会出现分享失败，主要是接口调用失败，而不会通过activity返回错误的intent

    }

    private static class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

        private final Context context;
        private final WbShareHandler wbShareHandler;

        private BitmapWorkerTask(Context context,WbShareHandler wbShareHandler) {
            this.context = context;
            this.wbShareHandler = wbShareHandler;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return ShareUtils.getBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            }
//            mWebpageObject.setThumbImage(bitmap); //注意，它会按照jpeg做85%的压缩，压缩后的大小不能超过32K
//            mWeiboMessage.mediaObject = mWebpageObject;
            ImageObject imageObject = new ImageObject();
            imageObject.setImageObject(bitmap);
            mWeiboMessage.imageObject = imageObject;
            wbShareHandler.shareMessage(mWeiboMessage, true);

        }

    }
    /**
     * 创建文本消息对象。
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        textObject.title = "";
        textObject.actionUrl = "";
        return textObject;
    }
}
