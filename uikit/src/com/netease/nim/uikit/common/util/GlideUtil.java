package com.netease.nim.uikit.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.netease.nim.uikit.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class GlideUtil {

    /**
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param url      图片链接
     * @param imgeview 组件
     */
    public static void showImageView(Context context, String url,
                                     ImageView imgeview) {
        Glide.with(context).load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .dontAnimate()//不显示动画效果
                .into(imgeview);


    }

    /**
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param url      图片链接
     * @param imgeview 组件
     */
    public static void showImageView(Context context, int error, String url,
                                     ImageView imgeview) {
        Glide.with(context).load(url)
                .placeholder(error)
                .error(error)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .dontAnimate()//不显示动画效果
                .into(imgeview);

    }


    /**
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param url      图片链接
     * @param imgeview 组件
     */
    public static void showImageView(Context context, String url,
                                     ImageView imgeview, int width, int height) {
        Glide.with(context).load(url)
                .override(width, height)
                .error(R.drawable.img_qs_50x50)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .dontAnimate()//不显示动画效果
                .into(imgeview);
    }

    /**
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param imgRes    图片资源
     * @param imgeview 组件
     */
    public static void showImageView(Context context, int imgRes,
                                     ImageView imgeview) {
        Glide.with(context).load(imgRes)
                .crossFade()
//                .dontAnimate()//不显示动画效果
                .into(imgeview);
    }

    /**
     * （2）
     * 获取到Bitmap---不设置错误图片，错误图片不显示
     *
     * @param context
     * @param imageView
     * @param url
     */

    public static void showImageViewGone(Context context,
                                         final ImageView imageView, String url) {
        Glide.with(context).load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {

                        imageView.setVisibility(View.VISIBLE);
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);
                        imageView.setImageDrawable(bd);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);
                        imageView.setVisibility(View.GONE);
                    }

                });

    }

    /**
     * （3）
     * 设置RelativeLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param url
     * @param bgLayout
     */

    public static void showImageView(Context context, String url,
                                     final RelativeLayout bgLayout) {
        Glide.with(context).load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （4）
     * 设置LinearLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param errorimg
     * @param url
     * @param bgLayout
     */

    public static void showImageView(Context context, String url, int errorimg,
                                     final LinearLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(errorimg)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （5）
     * 设置FrameLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param url
     * @param frameBg
     */

    public static void showImageView(Context context, String url,
                                     final FrameLayout frameBg) {
        Glide.with(context).load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        frameBg.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        frameBg.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （6）
     * 获取到Bitmap 高斯模糊         RelativeLayout
     *
     * @param context
     * @param errorimg
     * @param url
     * @param bgLayout
     */

    public static void showImageViewBlur(Context context, int errorimg,
                                         String url, final RelativeLayout bgLayout) {
        Glide.with(context).load(url).asBitmap()
                .error(errorimg)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .transform(new BlurTransformation(context))
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （7）
     * 获取到Bitmap 高斯模糊 LinearLayout
     *
     * @param context
     * @param errorimg
     * @param url
     * @param bgLayout
     */

    public static void showImageViewBlur(Context context, int errorimg,
                                         String url, final LinearLayout bgLayout) {
        Glide.with(context).load(url).asBitmap()
                .error(errorimg)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .transform(new BlurTransformation(context))
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （8）
     * 显示图片 圆角显示  ImageView
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     */
    public static void showImageViewToCircle(Context context, int errorimg,
                                             String url, ImageView imgeview) {
        Glide.with(context).load(url)
                .placeholder(errorimg)
                .error(errorimg)
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(context))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .dontAnimate() //不显示动画效果
                .into(imgeview);
    }


    /**
     第一次加载失败处理 tocircle的方法加载有点慢
     */
    public static void showImageViewFirst(Context context, int error, String url,
                                     ImageView imgeview) {
        Glide.with(context).load(url)
                .placeholder(error)
                .error(error)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .dontAnimate()
                .into(imgeview);
    }
}
