package com.drops.waterdrop.video;

import android.media.AudioManager;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/20 23:08
 */

public class OnAudioFocusChangeWrap implements AudioManager.OnAudioFocusChangeListener {

    @Override
    public void onAudioFocusChange(int focusChange) {
        // TODO: 2017/8/20 播放中可能会有其它应用来抢占Audio，由于暂时只在欢迎页面用到，播放时间很短，所以，可暂时不处理
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
            case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
            case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE:
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
            default:
                break;
        }
    }

}
