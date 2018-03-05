package com.netease.nim.uikit.session.module.input;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.emoji.EmotionKeyboard;
import com.netease.nim.uikit.emoji.EmotionLayout;
import com.netease.nim.uikit.emoji.IEmotionExtClickListener;
import com.netease.nim.uikit.emoji.IEmotionSelectedListener;
import com.netease.nim.uikit.recent.AitHelper;
import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nim.uikit.session.actions.ImageAction;
import com.netease.nim.uikit.session.actions.VideoAction;
import com.netease.nim.uikit.session.attachment.StickerAttachment;
import com.netease.nim.uikit.session.attachment.VerifyContentAttachment;
import com.netease.nim.uikit.session.emoji.MoonUtil;
import com.netease.nim.uikit.session.module.Container;
import com.netease.nim.uikit.team.model.TeamExtras;
import com.netease.nim.uikit.team.model.TeamRequestCode;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.media.record.AudioRecorder;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.CustomNotificationConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.netease.nim.uikit.NimUIKit.getAccount;
import static com.netease.nim.uikit.R.id.btnAudio;
import static com.netease.nim.uikit.R.id.rlAlbum;


/**
 * 底部文本编辑，语音等模块
 * Created by hzxuwen on 2015/6/16.
 */
public class InputPanel implements IAudioRecordCallback, IEmotionSelectedListener {

    private static final String TAG = "MsgSendLayout";

    public static final int RECORD_AUDIO_REQUEST_CODE = 111;
    private static final int SHOW_LAYOUT_DELAY = 200;
    public static final int SEND_CARD_REQUEST_CODE = 223;
    /*public static final String EXTRA_CARD_ACCOUNT = "card_account";
    public static final String EXTRA_CARD_TYPE = "card_type";
    public static final String EXTRA_CARD_USER_NAME = "card_user_name";
*/
    protected Container container;
    protected View view;
    protected Handler uiHandler;

    protected Button audioRecordBtn; // 录音按钮
    protected View audioAnimLayout; // 录音动画布局

    private LinearLayout mLlContent;
    private ImageView mIvAudio;
    private Button mBtnAudio;
    private EditText mEtContent;
    private ImageView mIvEmo;
    private ImageView mIvMore;
    private Button mBtnSend;
    private FrameLayout mFlEmotionView;
    private EmotionLayout mElEmotion;
    private LinearLayout mLlMore;
    private RelativeLayout mRlAlbum;
    private RelativeLayout mRlTakePhoto;
    private EmotionKeyboard mEmotionKeyboard;

    // 语音
    protected AudioRecorder audioMessageHelper;
    private Chronometer time;
    private TextView timerTip;
    private LinearLayout timerTipContainer;
    private boolean started = false;
    private boolean cancelled = false;
    private boolean touched = false; // 是否按着
    private boolean isKeyboardShowed = true; // 是否显示键盘

    // state
    private boolean actionPanelBottomLayoutHasSetup = false;
    private boolean isTextAudioSwitchShow = true;

    // adapter
    private List<BaseAction> actions;

    // data
    private long typingTime = 0;

    // message edit watcher

    private MessageEditWatcher watcher;

    private Activity mActivity;

    public InputPanel(Container container, View view, List<BaseAction> actions, boolean isTextAudioSwitchShow, Activity activity) {
        this.container = container;
        this.view = view;
        this.actions = actions;
        this.uiHandler = new Handler();
        this.isTextAudioSwitchShow = isTextAudioSwitchShow;
        this.mActivity = activity;
        init();
    }

    public InputPanel(Container container, View view, List<BaseAction> actions, Activity activity) {
        this(container, view, actions, true, activity);
    }

    public void onPause() {
        // 停止录音
        if (audioMessageHelper != null) {
            onEndAudioRecord(true);
        }
    }

    //收起输入区
    public boolean collapse() {
        //todo 收起输入去
        if (mElEmotion.isShown() || mLlMore.isShown()) {
            mEmotionKeyboard.interceptBackPress();
            mIvEmo.setImageResource(R.drawable.ic_cheat_emo);
            return true;
        }
        return false;
    }

    public void setWatcher(MessageEditWatcher watcher) {
        this.watcher = watcher;
    }

    private void init() {

        initViews();
        initEmotion();
        initActions();
        initListener();

        initAudioRecordButton();
        restoreText(false);

    }

    private void initActions() {
        getActionList();

        for (int i = 0; i < actions.size(); ++i) {
            actions.get(i).setIndex(i);
            actions.get(i).setContainer(container);
        }
    }

    private void initListener() {
        mElEmotion.setEmotionSelectedListener(this);
        mElEmotion.setEmotionAddVisiable(false);
        mElEmotion.setEmotionSettingVisiable(false);
        mElEmotion.setEmotionExtClickListener(new IEmotionExtClickListener() {
            @Override
            public void onEmotionAddClick(View view) {
                Toast.makeText(view.getContext(), "添加emj表情", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onEmotionSettingClick(View view) {
                Toast.makeText(view.getContext(), "设置emj表情", Toast.LENGTH_SHORT).show();

            }
        });

        mLlContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    closeBottomAndKeyboard();
                }
                return false;
            }
        });

        mIvAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtnAudio.isShown()) {
                    hideAudioButton();
                    mEtContent.requestFocus();
                    if (mEmotionKeyboard != null) {
                        mEmotionKeyboard.showSoftInput();
                    }
                } else {
                    mEtContent.clearFocus();
                    showAudioButton();
                    hideEmotionLayout();
                    hideMoreLayout();
                }

                moveToLastPosition();
            }
        });


        mEtContent.addTextChangedListener(new TextWatcher() {
            private int start;
            private int count;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.start = start;
                this.count = count;

                if (mEtContent.getText().toString().trim().length() > 0) {
                    mBtnSend.setVisibility(View.VISIBLE);
                    mIvMore.setVisibility(View.GONE);
                } else {
                    mBtnSend.setVisibility(View.GONE);
                    mIvMore.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                MoonUtil.replaceEmoticons(container.activity, s, start, count);

                if (watcher != null) {
                    watcher.afterTextChanged(s, start, count);
                }

                int editEnd = mEtContent.getSelectionEnd();
                mEtContent.removeTextChangedListener(this);
                while (StringUtil.counterChars(s.toString()) > 5000 && editEnd > 0) {
                    s.delete(editEnd - 1, editEnd);
                    editEnd--;
                }
                mEtContent.setSelection(editEnd);
                mEtContent.addTextChangedListener(this);

                sendTypingCommand();
            }

        });
        mEtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    moveToLastPosition();
                }
            }
        });

        //软键盘发送按钮事件
        mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    onTextMessageSendButtonPressed();
                    return true;
                }
                return false;
            }
        });


        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextMessageSendButtonPressed();
            }
        });
    }


    private void initEmotion() {
        mElEmotion.attachEditText(mEtContent);
        initEmotionKeyboard();
    }


    private void initViews() {
        // input bar

        mLlContent = (LinearLayout) view.findViewById(R.id.llContent);
        mIvAudio = (ImageView) view.findViewById(R.id.ivAudio);
        mBtnAudio = (Button) view.findViewById(btnAudio);
        mEtContent = (EditText) view.findViewById(R.id.etContent);
        mIvEmo = (ImageView) view.findViewById(R.id.ivEmo);
        mIvMore = (ImageView) view.findViewById(R.id.ivMore);
        mBtnSend = (Button) view.findViewById(R.id.btnSend);
        mFlEmotionView = (FrameLayout) view.findViewById(R.id.flEmotionView);
        mElEmotion = (EmotionLayout) view.findViewById(R.id.elEmotion);
        mLlMore = (LinearLayout) view.findViewById(R.id.llMore);
        mRlAlbum = (RelativeLayout) view.findViewById(rlAlbum);//图片
        mRlTakePhoto = (RelativeLayout) view.findViewById(R.id.rlTakePhoto);//视频


        // 语音
        audioAnimLayout = view.findViewById(R.id.layoutPlayAudio);//todo  取消发送语音
        time = (Chronometer) view.findViewById(R.id.timer);
        timerTip = (TextView) view.findViewById(R.id.timer_tip);
        timerTipContainer = (LinearLayout) view.findViewById(R.id.timer_tip_container);

        initActionListener();

    }

    /*  private void initEditTextListener() {
          mEtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
              @Override
              public void onFocusChange(View v, boolean hasFocus) {
                  if (hasFocus) {
                      //got focus
                      System.out.println("有焦点");
                  } else {
                      //lost focus
                      System.out.println("没焦点");

                  }
              }
          });
      }
  */
    //发送附件的点击事件
    private void initActionListener() {
        mRlAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(container.activity);
                rxPermissions.setLogging(true);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (!aBoolean) {
                                    Toast.makeText(container.activity, "请开启相机和存储空间相关权限", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    actions.get(0)
                                            .onClick();
                                }
                            }
                        });

            }
        });
        mRlTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(container.activity);
                rxPermissions.setLogging(true);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (!aBoolean) {
                                    Toast.makeText(container.activity, "请开启相机和存储空间相关权限", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    actions.get(1)
                                            .onClick();
                                }
                            }
                        });

            }
        });

    }

    /**
     * 发送好友验证消息（打招呼）
     *
     * @param account 接收者账号
     * @param text    招呼内容
     */
    public static void sendVerifyContent(String account, String text) {

        VerifyContentAttachment attachment = new VerifyContentAttachment();
        attachment.setVerify(text);

        IMMessage message = MessageBuilder.createCustomMessage(account, SessionTypeEnum.P2P, attachment);

        CustomMessageConfig config = new CustomMessageConfig();
        config.enableUnreadCount = false; // 该消息不计入未读数
        config.enableHistory = true;//该消息是否允许在消息历史中拉取，如果为 false，通过 MsgService#pullMessageHistory 拉取的结果将不包含该条消息。默认为 true 。
        config.enableRoaming = false;//该消息是否需要漫游。
        config.enableSelfSync = false;//多端同时登录时，发送一条消息后，是否要同步到其他同时登录的客户端。默认为 true 。
        config.enableUnreadCount = false;//该消息是否要计入未读数，如果为 true ，那么对方收到消息后，最近联系人列表中未读数加1。默认为 true 。
        config.enablePush = false;//该消息是否进行推送（消息提醒）

        message.setConfig(config);

        NIMClient.getService(MsgService.class).sendMessage(message, true);

    }


    private void initEmotionKeyboard() {
        mEmotionKeyboard = EmotionKeyboard.with(container.activity);

        mEmotionKeyboard.bindToEditText(mEtContent);
        mEmotionKeyboard.bindToContent(mLlContent);
        mEmotionKeyboard.setEmotionLayout(mFlEmotionView);
        mEmotionKeyboard.bindToEmotionButton(mIvEmo, mIvMore);
        mEmotionKeyboard.setOnEmotionButtonOnClickListener(new EmotionKeyboard.OnEmotionButtonOnClickListener() {
            @Override
            public boolean onEmotionButtonOnClickListener(View view) {
                if (view.getId() == R.id.ivEmo) {
                    moveToLastPosition();
                    mEtContent.clearFocus();
                    if (!mElEmotion.isShown()) {
                        if (mLlMore.isShown()) {
                            showEmotionLayout();
                            hideMoreLayout();
                            hideAudioButton();
                            return true;
                        }
                    } else if (mElEmotion.isShown() && !mLlMore.isShown()) {
                        mIvEmo.setImageResource(R.drawable.ic_cheat_emo);
                        return false;
                    }
                    showEmotionLayout();
                    hideMoreLayout();
                    hideAudioButton();
                } else if (view.getId() == R.id.ivMore) {
                    moveToLastPosition();

                    mEtContent.clearFocus();
                    if (!mLlMore.isShown()) {
                        if (mElEmotion.isShown()) {
                            showMoreLayout();
                            hideEmotionLayout();
                            hideAudioButton();
                            return true;
                        }
                    }
                    showMoreLayout();
                    hideEmotionLayout();
                    hideAudioButton();
                }

                return false;
            }
        });
    }


    @Override
    public void onEmojiSelected(String key) {


    }

    //当点击贴图表情是会调用
    @Override
    public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {

        MsgAttachment attachment = new StickerAttachment(categoryName, stickerName);
        IMMessage stickerMessage = MessageBuilder.createCustomMessage(container.account, container.sessionType, "贴图消息", attachment);
        container.proxy.sendMessage(stickerMessage);


       /* File file = new File(stickerBitmapPath);
        IMMessage message = MessageBuilder.createImageMessage(container.account, container.sessionType, file, stickerName);
        container.proxy.sendMessage(message);*/
    }


    private void hideAudioButton() {
        mBtnAudio.setVisibility(View.GONE);
        mEtContent.setVisibility(View.VISIBLE);
        mIvAudio.setImageResource(R.drawable.ic_cheat_voice);
    }

    private void showEmotionLayout() {
        mElEmotion.setVisibility(View.VISIBLE);
        mIvEmo.setImageResource(R.drawable.ic_cheat_keyboard);
    }

    private void hideEmotionLayout() {
        mElEmotion.setVisibility(View.GONE);
        mIvEmo.setImageResource(R.drawable.ic_cheat_emo);
    }

    private void showMoreLayout() {
        mLlMore.setVisibility(View.VISIBLE);
    }

    private void hideMoreLayout() {
        mLlMore.setVisibility(View.GONE);
    }

    private void closeBottomAndKeyboard() {
        mElEmotion.setVisibility(View.GONE);
        mLlMore.setVisibility(View.GONE);
        if (mEmotionKeyboard != null) {
            mEmotionKeyboard.interceptBackPress();
            mIvEmo.setImageResource(R.drawable.ic_cheat_emo);
        }
    }


    private void showAudioButton() {
        mBtnAudio.setVisibility(View.VISIBLE);
        mEtContent.setVisibility(View.GONE);
        mIvAudio.setImageResource(R.drawable.ic_cheat_keyboard);

        if (mFlEmotionView.isShown()) {
            if (mEmotionKeyboard != null) {
                mEmotionKeyboard.interceptBackPress();
            }
        } else {
            if (mEmotionKeyboard != null) {
                mEmotionKeyboard.hideSoftInput();
            }
        }
    }


    private void moveToLastPosition() {
        container.proxy.onInputPanelExpand();

    }


    /**
     * 发送“正在输入”通知
     */
    private void sendTypingCommand() {
        if (container.account.equals(getAccount())) {
            return;
        }

        if (container.sessionType == SessionTypeEnum.Team || container.sessionType == SessionTypeEnum.ChatRoom) {
            return;
        }

        if (System.currentTimeMillis() - typingTime > 5000L) {
            typingTime = System.currentTimeMillis();
            CustomNotification command = new CustomNotification();
            command.setSessionId(container.account);
            command.setSessionType(container.sessionType);
            CustomNotificationConfig config = new CustomNotificationConfig();
            config.enablePush = false;
            config.enableUnreadCount = false;
            command.setConfig(config);

            JSONObject json = new JSONObject();
            json.put("id", "1");
            command.setContent(json.toString());

            NIMClient.getService(MsgService.class).sendCustomNotification(command);
        }
    }

    /**
     * ************************* 键盘布局切换 *******************************
     */


    // 发送文本消息
    private void onTextMessageSendButtonPressed() {
        String text = mEtContent.getText().toString();

        if (TextUtils.isEmpty(text)) return;

        IMMessage textMessage = createTextMessage(text);

        if (container.proxy.sendMessage(textMessage)) {
            restoreText(true);
        }
    }

    protected IMMessage createTextMessage(String text) {
        return MessageBuilder.createTextMessage(container.account, container.sessionType, text);
    }


    private void restoreText(boolean clearText) {
        if (clearText) {
            mEtContent.setText("");
        }

    }


    private boolean isPermission;

    /**
     * ****************************** 语音 ***********************************
     */
    private void initAudioRecordButton() {
        mBtnAudio.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    RxPermissions rxPermissions = new RxPermissions(container.activity);
                    rxPermissions.setLogging(true);
                    rxPermissions.request(Manifest.permission.RECORD_AUDIO)
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean aBoolean) {
                                    if (!aBoolean) {
                                        Toast.makeText(container.activity, "请开启录制声音权限", Toast.LENGTH_SHORT).show();
                                        isPermission = false;
                                        return;
                                    } else {
                                        isPermission = true;
                                    }
                                }
                            });

                    if (isPermission) {
                        touched = true;
                        initAudioRecord();
                        onStartAudioRecord();
                    } else {
                        return false;
                    }

                } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                        || event.getAction() == MotionEvent.ACTION_UP) {
                    if (isPermission) {
                        touched = false;
                        onEndAudioRecord(isCancelled(v, event));
                    } else {
                        return false;
                    }

                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                    if (isPermission) {
                        touched = true;
                        cancelAudioRecord(isCancelled(v, event));
                    } else {
                        return false;
                    }
                }

                return false;
            }
        });
    }

    // 上滑取消录音判断
    private static boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth()
                || event.getRawY() < location[1] - 40) {
            return true;
        }

        return false;
    }

    /**
     * 初始化AudioRecord
     */
    private void initAudioRecord() {
        if (audioMessageHelper == null) {
            audioMessageHelper = new AudioRecorder(container.activity, RecordType.AAC, AudioRecorder.DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND, this);
        }
    }

    /**
     * 开始语音录制
     */
    private void onStartAudioRecord() {
        container.activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        audioMessageHelper.startRecord();
        cancelled = false;
    }

    /**
     * 结束语音录制
     *
     * @param cancel
     */
    private void onEndAudioRecord(boolean cancel) {
        started = false;
        container.activity.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        audioMessageHelper.completeRecord(cancel);
        mBtnAudio.setText(R.string.record_audio);
        mBtnAudio.setBackgroundResource(R.drawable.nim_message_input_edittext_box);
        stopAudioRecordAnim();
    }

    /**
     * 取消语音录制
     *
     * @param cancel
     */
    private void cancelAudioRecord(boolean cancel) {
        // reject
        if (!started) {
            return;
        }
        // no change
        if (cancelled == cancel) {
            return;
        }

        cancelled = cancel;
        updateTimerTip(cancel);
    }

    /**
     * 正在进行语音录制和取消语音录制，界面展示
     *
     * @param cancel
     */
    private void updateTimerTip(boolean cancel) {
        if (cancel) {
            timerTip.setText(R.string.recording_cancel_tip);
            timerTipContainer.setBackgroundResource(R.drawable.nim_cancel_record_red_bg);
        } else {
            timerTip.setText(R.string.recording_cancel);
            timerTipContainer.setBackgroundResource(0);
        }
    }

    /**
     * 开始语音录制动画
     */
    private void playAudioRecordAnim() {
        audioAnimLayout.setVisibility(View.VISIBLE);
        time.setBase(SystemClock.elapsedRealtime());
        time.start();
    }

    /**
     * 结束语音录制动画
     */
    private void stopAudioRecordAnim() {
        audioAnimLayout.setVisibility(View.GONE);
        time.stop();
        time.setBase(SystemClock.elapsedRealtime());
    }

    // 录音状态回调
    @Override
    public void onRecordReady() {

    }

    @Override
    public void onRecordStart(File audioFile, RecordType recordType) {
        started = true;
        if (!touched) {
            return;
        }

        mBtnAudio.setText(R.string.record_audio_end);
        mBtnAudio.setBackgroundResource(R.drawable.nim_message_input_edittext_box_pressed);

        updateTimerTip(false); // 初始化语音动画状态
        playAudioRecordAnim();
    }

    @Override
    public void onRecordSuccess(File audioFile, long audioLength, RecordType recordType) {
        IMMessage audioMessage = MessageBuilder.createAudioMessage(container.account, container.sessionType, audioFile, audioLength);
        container.proxy.sendMessage(audioMessage);
    }

    @Override
    public void onRecordFail() {
        if (started) {
            Toast.makeText(container.activity, R.string.recording_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRecordCancel() {

    }

    @Override
    public void onRecordReachedMaxTime(final int maxTime) {
        stopAudioRecordAnim();
        EasyAlertDialogHelper.createOkCancelDiolag(container.activity, "", container.activity.getString(R.string.recording_max_time), false, new EasyAlertDialogHelper.OnDialogActionListener() {
            @Override
            public void doCancelAction() {
            }

            @Override
            public void doOkAction() {
                audioMessageHelper.handleEndRecord(true, maxTime);
            }
        }).show();
    }

    public boolean isRecording() {
        return audioMessageHelper != null && audioMessageHelper.isRecording();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        // 从@返回
        if (requestCode == TeamRequestCode.REQUEST_TEAM_AIT_MEMBER) {
            insertAitMember(data);
            return;
        }

/*
        if (requestCode == SEND_CARD_REQUEST_CODE) {
            String cardAccid = data.getStringExtra(Constants.EXTRA_CARD_ACCID);
            long cardUid = data.getLongExtra(Constants.EXTRA_CARD_UID,  1);
            String cardUserName = data.getStringExtra(Constants.EXTRA_CARD_USER_NAME);
            String cardUserAvatar = data.getStringExtra(Constants.EXTRA_CARD_USER_AVATAR);
            int sex = data.getIntExtra(Constants.EXTRA_CARD_SEX, 1);
            String name = NimUserInfoCache.getInstance().getUserDisplayName(container.account);
            if (!TextUtils.isEmpty(cardAccid) ) {
                initEditDialog(cardUid, cardUserName, cardUserAvatar, sex, container.account, name);
            }
            return;
        }
*/

        int index = (requestCode << 16) >> 24;
        if (index != 0) {
            index--;
            if (index < 0 | index >= actions.size()) {
                LogUtil.d(TAG, "request code out of actions' range");
                return;
            }
            BaseAction action = actions.get(index);
            if (action != null) {
                action.onActivityResult(requestCode & 0xff, resultCode, data);
            }
        }
    }


    private void insertAitMember(Intent data) {
        TeamMember member = (TeamMember) data.getSerializableExtra(TeamExtras.RESULT_EXTRA_DATA);
        if (member == null) {
            return;
        }

        // insert account
        int start = mEtContent.getSelectionStart();
        String account = member.getAccount() + " ";
        if (start < 0 || start >= mEtContent.length()) {
            mEtContent.append(account);
        } else {
            mEtContent.getEditableText().insert(start, account);// 光标所在位置插入文字
        }

        // 替换成昵称
        String aitName = AitHelper.getAitName(member) + " ";
        Editable editable = mEtContent.getText();
        aitName = "@" + aitName;
        start--;
        editable.setSpan(AitHelper.getInputAitSpan(aitName, mEtContent.getTextSize()),
                start, start + account.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 显示键盘
//        uiHandler.postDelayed(showTextRunnable, SHOW_LAYOUT_DELAY);
//        Toast.makeText(mEtContent.getContext(), "显示键盘", Toast.LENGTH_SHORT).show();

    }


    // 操作面板集合
    protected List<BaseAction> getActionList() {
        List<BaseAction> actions = new ArrayList<>();
        actions.add(new ImageAction());
        actions.add(new VideoAction());
//        actions.add(new LocationAction());

        return actions;
    }


    private void hideKeyBoard(Context context, Activity activity) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void closeKeyboard(Context context, Activity activity) {
       /* View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
*/
//        collapse();

        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


    }


}


