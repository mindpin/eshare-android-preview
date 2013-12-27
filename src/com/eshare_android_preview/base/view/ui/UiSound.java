package com.eshare_android_preview.base.view.ui;

import android.media.MediaPlayer;

import com.eshare_android_preview.R;
import com.eshare_android_preview.application.EshareApplication;

/**
 * Created by Administrator on 13-12-27.
 */
public class UiSound {
    final static public MediaPlayer CORRECT = MediaPlayer.create(EshareApplication.context, R.raw.heal);
    final static public MediaPlayer ERROR = MediaPlayer.create(EshareApplication.context, R.raw.drop);
    final static public MediaPlayer FAIL = MediaPlayer.create(EshareApplication.context, R.raw.fail);
    final static public MediaPlayer SUCCESS = MediaPlayer.create(EshareApplication.context, R.raw.success);
}
