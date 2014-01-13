package com.eshare_android_preview.activity.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.task.BaseAsyncTask;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.base.view.ExperienceView;
import com.eshare_android_preview.base.view.knowledge_map.KnowledgeMapView;
import com.eshare_android_preview.base.view.ui.CircleAvatarDrawable;
import com.eshare_android_preview.base.view.webview.EshareMarkdownView;
import com.eshare_android_preview.http.api.ExpApi;
import com.eshare_android_preview.http.api.KnowledgeNetHttpApi;
import com.eshare_android_preview.http.logic.user_auth.AccountManager;
import com.eshare_android_preview.http.model.CurrentState;
import com.eshare_android_preview.http.model.KnowledgeNet;


public class HomeActivity extends EshareBaseActivity {
    public static String net_id;
    public static KnowledgeMapView map_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home);

        net_id = KnowledgeNet.current_net_id();

        send_http_request();

        super.onCreate(savedInstanceState);
    }

    private void send_http_request(){
        new BaseAsyncTask<Void, Void, Void>(this, R.string.now_loading) {
            @Override
            public Void do_in_background(Void... params) throws Exception {
                CurrentState.current_state = ExpApi.exp_info(net_id);
                KnowledgeNet.current_net = KnowledgeNetHttpApi.net(net_id);
                return null;
            }

            @Override
            public void on_success(Void result) {
                load_map_view();
                load_avatar();
                init_exp_view();

                webview_preload();
            }
        }.execute();
    }

    private void load_map_view() {
        map_view = (KnowledgeMapView) findViewById(R.id.knowledge_map_view);
        map_view.init(this);
    }

    private void load_avatar() {
        ImageView iv = (ImageView) findViewById(R.id.avatar);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = false;

        byte[] avatar = AccountManager.current_user().avatar;
        Bitmap b = BitmapFactory.decodeByteArray(avatar, 0, avatar.length, o);

        b = ImageTools.createBitmapBySize(b, BaseUtils.dp_to_px(30), BaseUtils.dp_to_px(30));
        CircleAvatarDrawable d = new CircleAvatarDrawable(b);
        iv.setBackgroundDrawable(d);
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

    public void change_course(View view) {
        Intent intent = new Intent(this, ChangeNetActivity.class);
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ExperienceView view = (ExperienceView) findViewById(R.id.experience_view);
        view.refresh();
    }

    private void webview_preload() {
        ((EshareMarkdownView) findViewById(R.id.webview_preload)).set_markdown_content("...");
    }

    public void open_about(View view) {
        findViewById(R.id.about).setVisibility(View.VISIBLE);
    }

    public void close_about(View view) {
        findViewById(R.id.about).setVisibility(View.GONE);
    }
}
