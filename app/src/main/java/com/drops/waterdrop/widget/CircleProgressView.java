package com.drops.waterdrop.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

public class CircleProgressView extends View {

    private static final String TAG = "CircleProgressBar";

    private int mMaxProgress = 100;

    private int mProgress = 30;

    private final int mCircleLineStrokeWidth = 10;

    private final int mTxtStrokeWidth = 2;

    // 画圆所在的距形区域
    private final RectF mRectF;

    private final Paint mPaint;

    private final Context mContext;

    private String mTxtHint1;

    private String mTxtHint2;

    private String mContentText;


    /**
     * 进度条的颜色。
     */
    private int progressLineColor = Color.rgb(0xf8, 0x60, 0x30);

    /**
     * 进度条阴影的颜色。
     */
    private int progressHintLineColor = Color.rgb(0xe9, 0xe9, 0xe9);

    /**
     * 上部字体的颜色。
     */
    private int centerTopTextColor = Color.rgb(0x99, 0x99, 0x99);

    /**
     * 下部字体的颜色。
     */
    private int centerButtomTextColor = Color.rgb(0x99, 0x99, 0x99);

    /**
     * 中心字体的颜色。
     */
    private int centerTextColor = Color.rgb(0x99, 0x99, 0x99);

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getWidth();
        int height = this.getHeight();

        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }

        // 设置画笔相关属性
        mPaint.setAntiAlias(true);
        mPaint.setColor(progressHintLineColor);
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        // 位置
        mRectF.left = mCircleLineStrokeWidth / 2; // 左上角x
        mRectF.top = mCircleLineStrokeWidth / 2; // 左上角y
        mRectF.right = width - mCircleLineStrokeWidth / 2; // 左下角x
        mRectF.bottom = height - mCircleLineStrokeWidth / 2; // 右下角y

        // 绘制圆圈，进度条背景
        canvas.drawArc(mRectF, -90, 360, false, mPaint);
        mPaint.setColor(progressLineColor);

        canvas.drawArc(mRectF, -90, ((float) mProgress / mMaxProgress) * 360, false, mPaint);

        // 绘制进度文案显示
        mPaint.setStrokeWidth(mTxtStrokeWidth);
        String text;
        if (TextUtils.isEmpty(mContentText)) {
            text = mProgress + "%";
        } else {
            text = mProgress + mContentText;
        }
        int textHeight = height / 5;
        mPaint.setTextSize(textHeight);
        int textWidth = (int) mPaint.measureText(text, 0, text.length());
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(centerTextColor);

        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 2, mPaint);

        if (!TextUtils.isEmpty(mTxtHint1)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtHint1;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
            mPaint.setColor(centerTopTextColor);

            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(text, width / 2 - textWidth / 2, height / 4 + textHeight / 2, mPaint);
        }

        if (!TextUtils.isEmpty(mTxtHint2)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtHint2;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
            mPaint.setColor(centerButtomTextColor);
            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(text, width / 2 - textWidth / 2, 3 * height / 4 + textHeight / 2, mPaint);
        }
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setProgress(int progress, String contentText) {
        this.mProgress = progress;
        this.mContentText = contentText;
        this.invalidate();
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgressNotInUiThread(int progress) {
        this.mProgress = progress;
        this.postInvalidate();
    }


    public String getmTxtHint1() {
        return mTxtHint1;
    }

    public void setmTxtHint1(String mTxtHint1) {
        this.mTxtHint1 = mTxtHint1;
    }

    public String getmTxtHint2() {
        return mTxtHint2;
    }

    public void setmTxtHint2(String mTxtHint2) {
        this.mTxtHint2 = mTxtHint2;
    }

    public int getCenterTextColor() {
        return centerTextColor;
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
    }

    public int getCenterButtomTextColor() {
        return centerButtomTextColor;
    }

    public void setCenterButtomTextColor(int centerButtomTextColor) {
        this.centerButtomTextColor = centerButtomTextColor;
    }

    public int getCenterTopTextColor() {
        return centerTopTextColor;
    }

    public void setCenterTopTextColor(int centerTopTextColor) {
        this.centerTopTextColor = centerTopTextColor;
    }

    public int getProgressHintLineColor() {
        return progressHintLineColor;
    }

    public void setProgressHintLineColor(int progressHintLineColor) {
        this.progressHintLineColor = progressHintLineColor;
    }

    public int getProgressLineColor() {
        return progressLineColor;
    }

    public void setProgressLineColor(int progressLineColor) {
        this.progressLineColor = progressLineColor;
    }

    public String getContentText() {
        return mContentText;
    }

    public void setContentText(String contentText) {
        mContentText = contentText;
    }
}
