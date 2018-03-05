package com.drops.waterdrop.ui.find.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.ToastUtil;
import com.drops.waterdrop.util.sys.UIUtils;
import com.netease.nim.uikit.common.util.media.BitmapUtil;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 图片浏览ViewPageAdapter
 * Created by Jelly on 2016/3/10.
 */
public class WatchPicViewPageAdapter extends PagerAdapter {
    private Context context;
    private List<String> images;
    private SparseArray<View> cacheView;
    private ViewGroup containerTemp;

    private String[] imageTypes = new String[]{".jpg", ".png", ".jpeg", "webp"};


    public WatchPicViewPageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
        cacheView = new SparseArray<>(images.size());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        if (containerTemp == null) containerTemp = container;

        View view = cacheView.get(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_watch_pic, container, false);
            view.setTag(position);
            final ImageView image = (ImageView) view.findViewById(R.id.image);
            final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
            final String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            photoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    saveImg(image, images.get(position));
                    return true;
                }
            });
            Glide.with(context).load(images.get(position)).asBitmap()
                    .into(new MyTarget(photoViewAttacher));

            photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    Activity activity = (Activity) context;
                    activity.finish();
                }
            });

            cacheView.put(position, view);

        }
        container.addView(view);
        return view;
    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    private class MyTarget extends SimpleTarget<Bitmap> {

        private PhotoViewAttacher viewAttacher;

        public MyTarget(PhotoViewAttacher viewAttacher) {
            this.viewAttacher = viewAttacher;
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            int width = resource.getWidth();
            int height = resource.getHeight();

            int newWidth = width;
            int newHeight = height;


            int screenWidth = UIUtils.getDisplayWidth();
            int screenHeight = UIUtils.getDisplayHeight();

            if (width > screenWidth) {
                newWidth = screenWidth;
            }

            if (height > screenHeight) {
                newHeight = screenHeight;
            }


            if (newWidth == width && newHeight == height) {
                viewAttacher.getImageView().setImageBitmap(resource);
                viewAttacher.update();
                return;
            }

            //计算缩放比例
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);

            Log.v("size", width + "");
            Log.v("size", height + "");

            Bitmap resizeBitmap = Bitmap.createBitmap(resource, 0, 0, width, height, matrix, true);

            viewAttacher.getImageView().setImageBitmap(resizeBitmap);
            viewAttacher.update();
        }

    }


    private void saveImg(ImageView imageView, String imgUrl) {
        String imageType = getImageType(imgUrl); //获取图片类型
        String fileName = System.currentTimeMillis() + "." + imageType;

        Bitmap bitmap = BitmapUtil.drawableToBitmap(imageView.getDrawable());
        String dirName = Environment.getExternalStorageDirectory().getAbsolutePath();
        boolean isSuccess = BitmapUtil.saveBitmap(bitmap, dirName, fileName, context, true);
        if (isSuccess) {
            ToastUtil.showShort("下载好了呢~");
        } else {
            ToastUtil.showShort("下载出错了哦~");

        }
    }

    public String getImageType(String imageUrl) {
        String imageType = "";
        if (imageUrl.endsWith(imageTypes[0])) {
            imageType = "jpg";
        } else if (imageUrl.endsWith(imageTypes[1])) {
            imageType = "png";
        } else {
            imageType = "jpeg";
        }
        return imageType;
    }


}
