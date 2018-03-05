package com.drops.waterdrop.ui.other.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.app.AppCache;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.util.sys.SysInfoUtil;
import com.drops.waterdrop.video.OnAudioFocusChangeWrap;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;

/**
 * Created by dengxiaolei on 2017/4/24.
 */

public class WelcomeActivity extends BaseActivity {

    private static final String TAG = "WelcomeActivity";

    private boolean customSplash = false;

    private static boolean firstEnter = true; // 是否首次进入
    private VideoView mVideoView;
    private AudioManager mAudioManager;
    private OnAudioFocusChangeWrap onAudioFocusChangeWrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            setIntent(new Intent()); // 从堆栈恢复，不再重复解析之前的intent
        }

        mVideoView = (VideoView) findViewById(R.id.video_view);
        Uri rawUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcome_video);
        mVideoView.setVideoURI(rawUri);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        onAudioFocusChangeWrap = new OnAudioFocusChangeWrap();

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                mAudioManager.abandonAudioFocus(onAudioFocusChangeWrap);

                if (canAutoLogin()) {
                    onIntent();
                } else {

                    Boolean isEnter = MyUserCache.getFirstEnter();
                    if (!isEnter) {
                        jumpToActivityAndClearTask(GuideActivity.class);
                        MyUserCache.saveFirstEnter(true);
                    } else {
                        LoginActivity.start(WelcomeActivity.this);
                        finish();
                    }
                }
            }
        });

        int request = mAudioManager.requestAudioFocus(onAudioFocusChangeWrap, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (request == AudioManager.AUDIOFOCUS_REQUEST_FAILED){
            // TODO: 2017/8/20 请求失败，暂时不处理
        }

        if (!firstEnter) {//如果不是第一次进来
            onIntent();
        } else {
//            showSplashView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        showSplashView();

        if (firstEnter) {
            firstEnter = false;

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (canAutoLogin()) {
                        onIntent();
                    } else {

                        Boolean isEnter = MyUserCache.getFirstEnter();
                        if (!isEnter) {
                            jumpToActivityAndClearTask(GuideActivity.class);
                            MyUserCache.saveFirstEnter(true);
                        } else {
                            LoginActivity.start(WelcomeActivity.this);
                            finish();
                        }
                    }
                }
            };

            if (customSplash) {
//                new Handler().postDelayed(runnable, 3000);
            } else {
                runnable.run();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        /**
         * 如果Activity在，不会走到onCreate，而是onNewIntent，这时候需要setIntent
         * 场景：点击通知栏跳转到此，会收到Intent
         */
        setIntent(intent);
        onIntent();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.clear();
    }

    // 处理收到的Intent
    private void onIntent() {
        LogUtil.i(TAG, "onIntent...");


        if (TextUtils.isEmpty(AppCache.getAccount())) {//判断是否已经登陆过， 如果没登陆过只展示登陆页

            if (!SysInfoUtil.stackResumed(this)) {// 判断当前app是否正在运行
                //也可以在这延迟展示一会欢迎页再做跳转的逻辑
                LoginActivity.start(this);
            }
            finish();

        } else {  // 已经登录过了，处理过来的请求
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {//说明是状态栏通知跳转过来的
                    parseNotifyIntent(intent);
                    return;
                } else if (intent.hasExtra(Extras.EXTRA_JUMP_P2P) /*|| intent.hasExtra(AVChatActivity.INTENT_ACTION_AVCHAT)*/) {
                    //说明是状态栏单聊通知跳转过来的
                    parseNormalIntent(intent);
                }
            }

            if (!firstEnter && intent == null) {
                finish();
            } else {
                showMainActivity();
            }
        }
    }

    /**
     * 已经登陆过，自动登陆
     */
    private boolean canAutoLogin() {
        String account = MyUserCache.getIMAccount();
        String token = MyUserCache.getIMToken();

        Log.i(TAG, "get local sdk token =" + token);
        return !TextUtils.isEmpty(account) && !TextUtils.isEmpty(token);
    }

    private void parseNotifyIntent(Intent intent) {
        ArrayList<IMMessage> messages = (ArrayList<IMMessage>) intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
        if (messages == null || messages.size() > 1) {
            showMainActivity(null);
        } else {
            showMainActivity(new Intent().putExtra(NimIntent.EXTRA_NOTIFY_CONTENT, messages.get(0)));
        }
    }

    private void parseNormalIntent(Intent intent) {
        showMainActivity(intent);
    }

    /**
     * 首次进入，打开欢迎界面
     */
    private void showSplashView() {
//        getWindow().setBackgroundDrawableResource(R.drawable.water_drop);
        mVideoView.start();
        mVideoView.requestFocus();
        mVideoView.setZOrderOnTop(true);


        customSplash = true;
    }

    private void showMainActivity() {
        showMainActivity(null);
    }

    private void showMainActivity(Intent intent) {
        MainActivity.start(WelcomeActivity.this, intent);
        finish();

    }

    /**
     * 欢迎页 沉浸模式
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void initView() {
        StatusBarUtil.setTransparent(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_welcome;
    }



}
