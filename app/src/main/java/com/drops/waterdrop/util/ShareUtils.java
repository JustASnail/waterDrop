package com.drops.waterdrop.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.WaterDropApp;
import com.drops.waterdrop.wbapi.WBEntryActivity;
import com.netease.nim.uikit.model.LinkedMeModel;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Mr.Smile on 2017/7/10.
 */

public class ShareUtils {

    private static WXMediaMessage msg;
    private static boolean mIsShareFriend;

    /**
     * @param isShareFriend true 分享到朋友，false分享到朋友圈
     */
    public static void share2WxWebPage(Context context, boolean isShareFriend, String url, String title, String picUrl, String desc) {

        if (!WaterDropApp.mWxApi.isWXAppInstalled()) {
            ToastUtil.showShort("您还未安装微信， 请先下载微信应用");
            return;
        }

        mIsShareFriend = isShareFriend;

        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = TextUtils.isEmpty(url) ? "" : url;
        msg = new WXMediaMessage(wxWebpageObject);
        msg.title = TextUtils.isEmpty(title) ? "" : title;
        msg.description = TextUtils.isEmpty(desc) ? "" : desc;

        if (!TextUtils.isEmpty(picUrl)) new BitmapWorkerTask(context).execute(picUrl);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();
            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    private static class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final Context context;

        private BitmapWorkerTask(Context context) {
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return getBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            }
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 90, 90, true);//缩略图大小

            msg.thumbData = Bitmap2Bytes(thumbBmp);  // 设置缩略图
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = mIsShareFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            WaterDropApp.mWxApi.sendReq(req);
        }

    }

    public static void generateUrl(final Context context, final int shareType, String pageTag,
                                   final boolean isShareFriend, final String url, final String title, final String picUrl, final String desc,
                                   LinkedMeModel linedModel) {


        if (shareType == 1) {
            share2WxWebPage(context, isShareFriend, url, title, picUrl, desc);
        } else if (shareType == 2) {
            WBEntryActivity.start(context
                    , url
                    , title
                    , picUrl
                    , desc);
        }


   /*     *//**创建深度链接*//*
        //深度链接属性设置
        final LinkProperties properties = new LinkProperties();
        //渠道
        String channel = "Wechat";
        if (shareType == 1) {
            channel = "Wechat";
        } else if (shareType == 2) {
            channel = "微博";
        }
        properties.setChannel(channel);  //微信、微博、QQ
        //功能
        properties.setFeature("SHARE");
        //标签
        properties.addTag(pageTag);
        //阶段
        properties.setStage("Test");
        //设置该h5_url目的是为了iOS点击右上角lkme.cc时跳转的地址，一般设置为当前分享页面的地址
        properties.setH5Url("https://www.baidu.com");
        //自定义参数,用于在深度链接跳转后获取该数据

        Gson gson = new Gson();
        String json = gson.toJson(linedModel);
        properties.addControlParameter("VC", pageTag);//
        properties.addControlParameter("Params", json);


        LMUniversalObject universalObject = new LMUniversalObject();
        universalObject.setTitle(title);

        // Async Link creation example
        universalObject.generateShortUrl(context, properties, new LMLinkCreateListener() {
            @Override
            public void onLinkCreate(String linkUrl, LMError error) {
                if (error == null) {
                    //url为生成的深度链接
                    if (shareType == 1) {
                        share2WxWebPage(context, isShareFriend, linkUrl, title, picUrl, desc);
                    } else if (shareType == 2) {
                        WBEntryActivity.start(context
                                , linkUrl
                                , title
                                , picUrl
                                , desc);
                    }

                } else {
                    ToastUtil.showShort("创建深度链接失败！失败原因：" + error.getMessage());
                }
            }
        });*/
    }

}
