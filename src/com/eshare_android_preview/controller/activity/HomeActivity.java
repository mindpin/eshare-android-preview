package com.eshare_android_preview.controller.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.view.ExperienceView;
import com.eshare_android_preview.view.knowledge_map.KnowledgeMapView;


public class HomeActivity extends EshareBaseActivity {
    public static class ExtraKeys {
        public static final String CHANGE_NET = "change_net";
    }
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
//                TODO 需要智能取，需要调整结构优化
                boolean change_net = getIntent().getBooleanExtra(ExtraKeys.CHANGE_NET, false);
                if(change_net){
                    UserData.instance().get_current_knowledge_net(true);
                }else{
                    UserData.instance().get_current_knowledge_net();
                }
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
        ev.init();
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
