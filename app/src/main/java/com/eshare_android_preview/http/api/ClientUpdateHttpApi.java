package com.eshare_android_preview.http.api;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.eshare_android_preview.EshareApplication;
import com.eshare_android_preview.http.base.EsharePostRequest;
import com.eshare_android_preview.http.base.PostParamText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Administrator on 14-2-17.
 */
public class ClientUpdateHttpApi {
    public static String CHECK_VERSION_URL = "/update/api/check_version";

    // 检查并获取最新版本信息
    public static VersionInfo check_version() {
        VersionInfo vi = new VersionInfo();

        try {
            return new EsharePostRequest<VersionInfo>(
                    CHECK_VERSION_URL,
                    new PostParamText("version", vi.current)
//                    new PostParamText("version", "0.0.1")
//                    new PostParamText("version", "0.1.2")
            ) {
                @Override
                public VersionInfo on_success(String response_text) throws Exception {
                    return VersionInfo.from_json(response_text);
                }

                @Override
                public void on_error(String response_text, int status_code) {
                    super.on_error(response_text, status_code);
                }
            }.go();
        } catch (Exception e) {
            e.printStackTrace();
            return vi;
        }
    }

    // 获取当前客户端版本号
    public static String get_current_version() {
        try {
            PackageManager pm = EshareApplication.context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(EshareApplication.context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "0.0.0";
        }
    }

    public static class VersionInfo {
        public String newest;
        public String last_milestone;
        public String current;
        public String update;

        public static String STATE_FORCE = "force";
        public static String STATE_EXIST = "exist";
        public static String STATE_NONE = "none";

        public VersionInfo() {
            newest = "0.0.0";
            last_milestone = "0.0.0";
            current = get_current_version();
            update = STATE_NONE;
        }

        public static VersionInfo from_json(String json) {
            Gson gson = new GsonBuilder().create();
            VersionInfo vi = gson.fromJson(json, VersionInfo.class);
            return vi;
        }

        public boolean is_state_force() {
            return update.equals(STATE_FORCE);
        }

        public boolean is_state_exist() {
            return update.equals(STATE_EXIST);
        }

        public boolean is_state_none() {
            return update.equals(STATE_NONE);
        }

        public String get_desc() {
            if(is_state_force()) {
                return "目前最新的版本是 " + newest + " ，你需要更新版本才能继续使用。";
            }
            return "目前最新的版本是 " + newest;
        }
    }
}
