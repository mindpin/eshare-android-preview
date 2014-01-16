package com.eshare_android_preview.view.ui;

import android.media.MediaPlayer;

import com.eshare_android_preview.EshareApplication;
import com.eshare_android_preview.R;

/**
 * Created by Administrator on 13-12-27.
 */
public class UiSound {
    final static public MediaPlayer CORRECT = MediaPlayer.create(EshareApplication.context, R.raw.heal);
    final static public MediaPlayer ERROR   = MediaPlayer.create(EshareApplication.context, R.raw.drop);
    final static public MediaPlayer FAIL    = MediaPlayer.create(EshareApplication.context, R.raw.fail);
    final static public MediaPlayer SUCCESS = MediaPlayer.create(EshareApplication.context, R.raw.success);

    {
        CORRECT.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                CORRECT.release();
            }
        });

        ERROR.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                ERROR.release();
            }
        });

        FAIL.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                FAIL.release();
            }
        });

        SUCCESS.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                SUCCESS.release();
            }
        });
    }
}
