package com.eshare_android_preview.activity.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.task.BaseAsyncTask;
import com.eshare_android_preview.base.view.ExperienceView;
import com.eshare_android_preview.base.view.knowledge_map.KnowledgeMapView;
import com.eshare_android_preview.http.c.UserData;


public class HomeActivity extends EshareBaseActivity {
    public static KnowledgeMapView map_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        send_http_request();
    }

    private void send_http_request(){
        new BaseAsyncTask<Void, Void, Void>(this, R.string.now_loading) {
            @Override
            public Void do_in_background(Void... params) throws Exception {
                UserData.instance().get_current_knowledge_net(true);
                return null;
            }

            @Override
            public void on_success(Void result) {
                init_map_view();
                init_exp_view();
            }
        }.execute();
    }

    private void init_map_view() {
        map_view = (KnowledgeMapView) findViewById(R.id.knowledge_map_view);
        map_view.init();
    }

    private void init_exp_view() {
        ExperienceView ev = (ExperienceView) findViewById(R.id.experience_view);
        ev.refresh();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setMessage("要退出吗？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    }).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
