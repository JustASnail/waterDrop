package com.drops.waterdrop.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.Button;

import com.drops.waterdrop.R;


/**
 * 带倒计时的按钮 
 */  
public class CountDownTimerButton extends Button {
    // 总倒计时时间
    private static final long MILLIS_IN_FUTURE = 60 * 1000;
    // 每次减去1秒
    private static final long COUNT_DOWN_INTERVAL = 1000;

    public CountDownTimerButton(Context context) {
        this(context, null);
    }

    public CountDownTimerButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
    }

    public void startCountDown() {
        setEnabled(false);
        new CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                setText(getContext().getString(R.string.reget_sms_code_countdown) + millisUntilFinished / COUNT_DOWN_INTERVAL);
            }

            @Override
            public void onFinish() {
                setText("获取验证码");
                setEnabled(true);
            }
        }.start();
    }
}