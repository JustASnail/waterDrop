package com.drops.waterdrop.ui.find.presenter;

import android.content.Intent;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.find.view.IWatchPicView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.util.media.BitmapUtil;

import java.util.List;

import static com.drops.waterdrop.ui.find.activity.WatchPicActivity.EXTRA_IMG_URLS;
import static com.drops.waterdrop.ui.find.activity.WatchPicActivity.EXTRA_POSITION;

/**
 * Created by dengxiaolei on 2017/5/27.
 */

public class WatchPicPresenter extends BasePresenter<IWatchPicView> {


    private List<String> images;
    private int position;

    private String[] imageTypes = new String[] { ".jpg",".png", ".jpeg","webp"};


    public WatchPicPresenter(BaseActivity context) {
        super(context);
    }

    public void loadImage() {
        Intent intent = getView().getDataIntent();
        images = intent.getStringArrayListExtra(EXTRA_IMG_URLS);
        position = intent.getIntExtra(EXTRA_POSITION, 0);
        getView().setImageBrowse(images, position);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<String> getImages() {
        return images;
    }

    public String getPositionImage(){
        return images.get(position);
    }


    public void saveImage() {
        //利用Picasso加载图片

        final String imageUrl = getPositionImage();

        Glide.with(mContext).load(imageUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
               boolean isSuccess =  BitmapUtil.saveBitmap(resource, mContext.getExternalCacheDir().getAbsolutePath(), imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.length()), mContext, true);

                if (isSuccess) {
                    ToastUtil.showShort("下载好了呢~");
                } else {
                    ToastUtil.showShort("下载出错了哦~");
                }
            }
        });
    }

    public String getImageType(String imageUrl){
        String imageType = "";
        if(imageUrl.endsWith(imageTypes[0])){
            imageType = "jpg";
        }else if(imageUrl.endsWith(imageTypes[1])){
            imageType = "png";
        }else{
            imageType = "jpeg";
        }
        return imageType;
    }
}
