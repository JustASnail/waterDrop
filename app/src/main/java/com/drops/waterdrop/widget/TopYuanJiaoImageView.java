package com.drops.waterdrop.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by dengxiaolei on 2017/7/10.
 */
public class TopYuanJiaoImageView extends ImageView {

    //圆角弧度
    private float[] rids = {20.0f,20.0f,20.0f,20.0f,0.0f,0.0f,0.0f,0.0f,};

    public TopYuanJiaoImageView(Context context) {
        super(context);
    }

    public TopYuanJiaoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopYuanJiaoImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        //绘制圆角imageview
        path.addRoundRect(new RectF(0,0,w,h),rids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}