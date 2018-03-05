package com.drops.waterdrop.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.drops.waterdrop.R;

import java.lang.ref.WeakReference;

/**
 * Created by dengxiaolei on 2017/6/5.
 */

public class CountDownTimerLayout extends FrameLayout {

    private TextView mTvHour;
    private TextView mTvMinute;
    private TextView mTvSecond;
    private MyCountDownTimer mMyCountDownTimer;

    public CountDownTimerLayout(Context context) {
        super(context);
        initView(context);
    }


    public CountDownTimerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public CountDownTimerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_timer, this);
        mTvHour = (TextView) view.findViewById(R.id.tv_hour);
        mTvMinute = (TextView) view.findViewById(R.id.tv_minute);
        mTvSecond = (TextView) view.findViewById(R.id.tv_second);
    }

    public void setCountDownTime(long time) {
        if (mMyCountDownTimer == null) {
            mMyCountDownTimer = new MyCountDownTimer(this, time, 1000);
        }
        mMyCountDownTimer.start();
    }

    public void cancelCountDownTime() {
        if (mMyCountDownTimer != null) {
            mMyCountDownTimer.cancel();
        }
    }

    /**
     * 继承 CountDownTimer 防范
     * <p>
     * 重写 父类的方法 onTick() 、 onFinish()
     */

    public static class MyCountDownTimer extends CountDownTimer {

        WeakReference<CountDownTimerLayout> mLayout;

        public MyCountDownTimer(CountDownTimerLayout layout, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

            mLayout = new WeakReference<CountDownTimerLayout>(layout);
        }


        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {

        }

        /**
         * 处理时间倒计时进行页面刷新
         *
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            if (mLayout != null) {
                mLayout.get().setView(millisUntilFinished / 1000);
            }
        }
    }

    public void setView(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue();
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }

        if (mTvHour != null && mTvMinute != null && mTvSecond != null) {
            String hourStr = hour + "";
            String minuteStr = minute + "";
            String secondStr = second + "";
            if (hour < 10) {
                hourStr = "0" + hour;
            }
            if (minute < 10) {
                minuteStr = "0" + minute;
            }

            if (second < 10) {
                secondStr = "0" + second;
            }
            mTvHour.setText(hourStr);
            mTvMinute.setText(minuteStr);
            mTvSecond.setText(secondStr);

        }
    }
}
