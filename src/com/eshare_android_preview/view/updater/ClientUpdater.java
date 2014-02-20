package com.eshare_android_preview.view.updater;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.http.api.ClientUpdateHttpApi;
import com.eshare_android_preview.http.base.EshareHttpRequest;
import com.eshare_android_preview.utils.BaseUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 14-2-17.
 */
public class ClientUpdater {
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private int progress;
    private boolean interceptFlag = false;
    private ProgressBar progress_bar;

    private static String DOWNLOADED_FILE_PATH = "/sdcard/update/4ye-newest.apk";

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    progress_bar.setProgress(progress);
                    break;
                case DOWN_OVER:
                    do_install_apk();
                    break;
                default:
                    break;
            }
        };
    };

    EshareBaseActivity activity;

    public ClientUpdater(EshareBaseActivity activity) {
        this.activity = activity;
    }

    public void check() {
        new BaseAsyncTask<Void, Void, ClientUpdateHttpApi.VersionInfo>(activity, null) {
            @Override
            public ClientUpdateHttpApi.VersionInfo do_in_background(Void... params) throws Exception {
                return ClientUpdateHttpApi.check_version();
            }

            @Override
            public void on_success(ClientUpdateHttpApi.VersionInfo vi) {
                if (vi.is_state_force()) {
                    show_force_update_dialog(vi);
                    return;
                }

                if (vi.is_state_exist()) {
                    show_exist_update_info(vi);
                    return;
                }

                if (vi.is_state_none()) {
                    // BaseUtils.toast("没有新版本噢");
                    // do nothing
                    return;
                }
            }
        }.execute();
    }

    public void show_force_update_dialog(ClientUpdateHttpApi.VersionInfo vi) {
        new AlertDialog.Builder(activity)
                .setTitle("软件版本更新")
                .setMessage(vi.get_desc())
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        show_download_dialog();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.clear_and_close();
                    }
                })
                .create().show();
    }

    public void show_exist_update_info(ClientUpdateHttpApi.VersionInfo vi) {
        BaseUtils.toast("最新版 " + vi.newest + " 可以下载啦");
    }

    private void show_download_dialog() {
        View v = activity.getLayoutInflater().inflate(R.layout.client_updater_download_progress, null);
        progress_bar = (ProgressBar) v.findViewById(R.id.progress);

        new AlertDialog.Builder(activity)
                .setTitle("下载新版本")
                .setView(v)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        interceptFlag = true;
                        activity.clear_and_close();
                    }
                })
                .create().show();

        do_download();
    }

    private void do_download() {
        new Thread(download_runnable).start();
    }

    private void do_install_apk() {
        File apk = new File(DOWNLOADED_FILE_PATH);
        if (apk.exists()) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.parse("file://" + apk.toString()), "application/vnd.android.package-archive");
            activity.startActivity(i);
        }
    }

    private Runnable download_runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(EshareHttpRequest.SITE + "/update/download/android/4ye-newest.apk");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File path = new File("/sdcard/update/");
                if (!path.exists()) {
                    path.mkdir();
                }

                File apk_file = new File(DOWNLOADED_FILE_PATH);
                FileOutputStream fos = new FileOutputStream(apk_file);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int)(((float) count / length) * 100);

                    // 更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                }while(!interceptFlag); // 点击取消停止下载

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    };
}
