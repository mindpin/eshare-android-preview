package com.eshare_android_preview.utils;

import com.eshare_android_preview.http.api.FeedbackHttpApi;

/**
 * Created by fushang318 on 14-2-17.
 */
public class EshareUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static EshareUncaughtExceptionHandler instance;
    private Thread.UncaughtExceptionHandler default_handler;

    private EshareUncaughtExceptionHandler(){
        default_handler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handleException(throwable) && null != default_handler) {
            default_handler.uncaughtException(thread, throwable);
        }
    }

    private boolean handleException(final Throwable ex){
        if (null == ex) return false;

        new Thread() {
            public void run() {
                FeedbackHttpApi.submit_exception(ex);
            }
        }.start();

        return true;
    }

    public static Thread.UncaughtExceptionHandler get_instance(){
        if(null == instance){
            instance = new EshareUncaughtExceptionHandler();
        }
        return instance;
    }
}
