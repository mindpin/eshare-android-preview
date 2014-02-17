package com.eshare_android_preview.http.api;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.eshare_android_preview.EshareApplication;
import com.eshare_android_preview.http.base.EsharePostRequest;
import com.eshare_android_preview.http.base.PostParamText;
import com.eshare_android_preview.http.logic.user_auth.AccountManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by fushang318 on 14-2-17.
 */
public class FeedbackHttpApi {
    public static void submit_exception(Exception ex){
        try {
            new EsharePostRequest<Void>(
                    "/update/api/submit_exception",
                    new PostParamText("version", _get_version()),
                    new PostParamText("datetime", _get_datetime()),
                    new PostParamText("user_id", _get_user_id()),
                    new PostParamText("device_name", _get_device_name()),
                    new PostParamText("sdk_version", _get_sdk_version()),
                    new PostParamText("os_release_version", _get_os_release_version()),
                    new PostParamText("other_device_info", _get_other_device_info()),
                    new PostParamText("exception_type", _get_exception_type(ex)),
                    new PostParamText("exception_stack", _get_exception_stack(ex))
            ) {
                @Override
                public Void on_success(String response_text) throws Exception {
                    return null;
                };
            }.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void submit_feedback(String feedback){
        try {
            new EsharePostRequest<Void>(
                    "/update/api/submit_exception",
                    new PostParamText("version", _get_version()),
                    new PostParamText("datetime", _get_datetime()),
                    new PostParamText("user_id", _get_user_id()),
                    new PostParamText("device_name", _get_device_name()),
                    new PostParamText("sdk_version", _get_sdk_version()),
                    new PostParamText("os_release_version", _get_os_release_version()),
                    new PostParamText("other_device_info", _get_other_device_info()),
                    new PostParamText("feedback", feedback)
            ) {
                @Override
                public Void on_success(String response_text) throws Exception {
                    return null;
                };
            }.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String _get_version(){
        PackageManager manager = EshareApplication.context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(EshareApplication.context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "unknow";
        }
    }

    private static String _get_datetime(){
        return "" + Calendar.getInstance().getTimeInMillis()/1000;
    }

    private static String _get_user_id(){
        return "" + AccountManager.current_user().user_id;
    }

    private static String _get_device_name(){
        return Build.MODEL;
    }

    private static String _get_sdk_version(){
        return Build.VERSION.SDK;
    }

    private static String _get_os_release_version(){
        return Build.VERSION.RELEASE;
    }

    private static String _get_other_device_info(){
        JSONObject json = new JSONObject();
        try {
            json.put("display", Build.DISPLAY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    private static String _get_exception_type(Exception e){
        return e.getClass().getName();
    }

    private static String _get_exception_stack(Exception e){
        String str = "";
        StackTraceElement[] elements = e.getStackTrace();
        for(StackTraceElement el : elements){
            str += el.toString() + "\n";
        }
        return str;
    }

}
