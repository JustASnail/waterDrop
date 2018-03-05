package com.drops.waterdrop.ui.find.adapter;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.find.activity.WatchPicActivity;
import com.drops.waterdrop.util.sys.UIUtils;
import com.drops.waterdrop.widget.MyGridView;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.FastClickUtil;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.model.PostEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengxiaolei on 2017/5/23.
 */

public class CommentAdapter extends BaseQuickAdapter<PostEntity.CommentBean, BaseViewHolder> {


    public CommentAdapter(@LayoutRes int layoutResId, @Nullable List<PostEntity.CommentBean> data) {
        super(R.layout.item_comment_list, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PostEntity.CommentBean item) {
        PostEntity.CommentBean.CreatorBean creator = item.getCreator();
        HeadImageView ivHead = helper.getView(R.id.iv_user_head);
        if (creator != null) {
            helper.setText(R.id.tv_user_name, creator.getNickName());
            GlideUtil.showImageViewToCircle(mContext, R.drawable.icon_default_head_60dp, creator.getPhoto(), ivHead);

        }
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_date, item.getCommentTime());
        List<PostEntity.CommentBean.CommentPhotosBean> commentPhotos = item.getCommentPhotos();
        if (commentPhotos != null && commentPhotos.size() > 0) {
            final ArrayList<String> imgs = new ArrayList<>();
            for (PostEntity.CommentBean.CommentPhotosBean commentPhoto : commentPhotos) {
                imgs.add(commentPhoto.getPhoto());
            }
            MyGridView gridView = helper.getView(R.id.grid_view);
            gridView.setAdapter(new GridViewAdapter(commentPhotos));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!FastClickUtil.isFastDoubleClick()) {
                        WatchPicActivity.start(mContext, imgs, position, view);
                    }
                }
            });
        }


    }

    //自定义适配器
    class GridViewAdapter extends BaseAdapter {

        List<PostEntity.CommentBean.CommentPhotosBean> mList;
        GridViewAdapter(List<PostEntity.CommentBean.CommentPhotosBean> lists){
            this.mList = lists;
        }
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        public Object getItem(int item) {
            return item;
        }

        public long getItemId(int id) {
            return id;
        }

        //创建View方法
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // 给ImageView设置资源
                imageView = new ImageView(mContext);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    imageView.setTransitionName("shareAnim");
                }
                // 设置布局 图片120×120显示
                imageView.setLayoutParams(new GridView.LayoutParams(UIUtils.dip2Px(92), UIUtils.dip2Px(92)));
                imageView.setBackgroundColor(Color.BLUE);
                // 设置显示比例类型
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            else {
                imageView = (ImageView) convertView;
            }
            GlideUtil.showImageViewToCircle(mContext, R.drawable.icon_default_head_60dp, mList.get(position).getPhoto(), imageView);
            return imageView;
        }
    }
}
