package com.drops.waterdrop.ui.find.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.sys.UIUtils;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/10/13.
 */

public class ImgTestAdapter extends RecyclerView.Adapter<ImgTestAdapter.MyViewHolder> {
    private Context mContext;

    private final int mScreenWidth;
    private final int mMaxHeight;

    private List<String> mList;

    public ImgTestAdapter(Context context, List<String> imgs) {
        this.mContext = context;
        mList = imgs;
        mScreenWidth = UIUtils.getDisplayWidth();
        mMaxHeight = mScreenWidth - UIUtils.dip2Px(28);
    }

    @Override
    public ImgTestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext)
                .inflate(R.layout.item_goods_details_img_list, parent, false);

        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ImgTestAdapter.MyViewHolder holder, final int position) {
        if (!mList.get(position).equals(holder.mIv.getTag(R.id.imageid))) {
            holder.mIv.setMaxWidth(mScreenWidth);

            Glide.with(mContext)
                    .load(mList.get(position))
                    .asBitmap()
                    .placeholder(R.drawable.img_qs_343x168)
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            holder.mIv.setImageResource(R.drawable.img_qs_343x168);

                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            holder.mIv.setImageResource(R.drawable.img_qs_343x168);

                        }

                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            int height = (int) ((float) mMaxHeight / resource.getWidth() * resource.getHeight());
//                        if (height > maxHeight) height = maxHeight;
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mMaxHeight, 400);
                            params.gravity = Gravity.CENTER_HORIZONTAL;
                            holder.mIv.setLayoutParams(params);
                            holder.mIv.setImageBitmap(resource);
//                            System.out.println(helper.getAdapterPosition() + "图片：" + height1 + "--" + height);
                            System.out.println("tupian:11111");
                            holder.mIv.setTag(R.id.imageid, mList.get(position));
                        }
                    });

        } else {
            Glide.with(mContext)
                    .load(holder.mIv.getTag(R.id.imageid))
                    .into(holder.mIv);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mIv;

        public MyViewHolder(View itemView) {
            super(itemView);

            mIv = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
