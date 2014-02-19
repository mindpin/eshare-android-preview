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

//        TODO 发送报告错误页面
//        提交错误信息到服务器
//        FeedbackHttpApi.submit_exception(ex);

//        用这种方式可以关闭当前应用，并打开一个自定义报告错误页面
//        Intent intent = new Intent(EshareApplication.context, XXXActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        EshareApplication.context.startActivity(intent);
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(10);

        return true;
    }

    public static Thread.UncaughtExceptionHandler get_instance(){
        if(null == instance){
            instance = new EshareUncaughtExceptionHandler();
        }
        return instance;
    }
}
