package com.drops.waterdrop.ui.find.adapter;

import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.sys.UIUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;


/**
 * Created by dengxiaolei on 2017/7/7.
 */

public class GoodsDetailsImgListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    private final int mScreenWidth;
    private final int mMaxHeight;

    public GoodsDetailsImgListAdapter(@LayoutRes int layoutResId, @Nullable List<String> photoDetails) {
        super(R.layout.item_goods_details_img_list, photoDetails);
        mScreenWidth = UIUtils.getDisplayWidth();
        mMaxHeight = mScreenWidth - UIUtils.dip2Px(28);

    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        final ImageView iv = helper.getView(R.id.iv_img);

//        ImageLoader.getInstance().displayImage(item, iv);
        iv.setMaxWidth(mScreenWidth);

/*
        Glide.with(mContext)
                .load(item)
                .asBitmap()
                .placeholder(R.drawable.img_qs_343x168)
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        iv.setImageResource(R.drawable.img_qs_343x168);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        iv.setImageResource(R.drawable.img_qs_343x168);

                    }

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        int height = (int) ((float) mMaxHeight / resource.getWidth() * resource.getHeight());
//                        if (height > maxHeight) height = maxHeight;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mMaxHeight, height);
                        params.gravity = Gravity.CENTER_HORIZONTAL;
                        iv.setLayoutParams(params);
                        iv.setImageBitmap(resource);
                        int height1 = iv.getHeight();
//                            System.out.println(helper.getAdapterPosition() + "图片：" + height1 + "--" + height);
                        System.out.println("tupian:11111"  + height);
                    }
                });
*/
       /* File cacheDir = StorageUtils.getOwnCacheDirectory(mContext, "imageloader/Cache");

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(mContext)
                .memoryCacheExtraOptions(480, 800)  // max width, max height，即保存的每个缓存文件的最大长宽 // default = device screen dimensions
                .threadPoolSize(4)   //线程池内加载数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                //.memoryCache(new UsingFreqLimitedMemoryCache(14 * 1024 * 1024)) //You can pass your own memory cache implementation /你可以通过自己的内存缓存实现
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(80 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                .diskCache(new UnlimitedDiskCache(cacheDir))  //自定义缓存路劲
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(mContext, 5 * 1000, 30 * 1000)) //connectTimeout( 5s ), readTimeout ( 30 ) 超时时间
                .writeDebugLogs()  //Remove for release app
                .build(); //开始构建*/

        ImageLoader.getInstance().displayImage(item, iv, getWholeOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
//                iv.setImageResource(R.drawable.img_qs_343x168);

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
//                iv.setImageResource(R.drawable.img_qs_343x168);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                int height = (int) ((float) iv.getWidth()/bitmap.getWidth() * bitmap.getHeight());
//                if (height > mMaxHeight) height = mMaxHeight;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mMaxHeight, height);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                iv.setLayoutParams(params);
//                iv.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });

    }


/*
    private void initImageLoad() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//					.threadPoolSize(3)
                .memoryCacheExtraOptions(280, 280)
//					.discCacheExtraOptions(280, 280, Comp(Thread.NORM_PRIORITY)ressFormat.JPEG, 75, null)
                .threadPriority
                .denyCacheImageMultipleSizesInMemory()
//					.discCacheFileNameGenerator(new Md5FileNameGenerator())
//					.discCache(new LimitedAgeDiscCache(StorageUtils.getCacheDirectory(context),new Md5FileNameGenerator(), discCacheLimitTime))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);

    }
*/

    /**
     * 显示图片的所有配置
     * @return
     */
    private DisplayImageOptions getWholeOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img_qs_343x168) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.img_qs_343x168)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.img_qs_343x168)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(false)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                //.decodingOptions(BitmapFactory.Options decodingOptions)//设置图片的解码配置
                .delayBeforeLoading(0)//int delayInMillis为你设置的下载前的延迟时间
                //设置图片加入缓存前，对bitmap进行设置
                //.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                //.displayer(new RoundedBitmapDisplayer(20))//不推荐用！！！！是否设置为圆角，弧度为多少
                //.displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间，可能会出现闪动
                .build();//构建完成

        return options;
    }



}
