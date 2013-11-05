package com.eshare_android_preview.widget.dialog.arc;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.utils.BaseUtils;

/**
 * Created by fushang318 on 13-10-31.
 */
public class ArcProgressDialog extends Dialog {
    public String message;
    public int total_num;
    public int current_num = 0;
    private ArcView arc_view;
    private TextView message_view;
    private TextView count_view;

    public ArcProgressDialog(Context context, int total_num) {
        super(context, R.style.base_progress_dialog);
        this.total_num = total_num;
        this.message = "载入课程…";
        setCancelable(false);// 不可以用“返回键”取消
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        setContentView(R.layout.arc_progress_dialog);
        message_view = (TextView) findViewById(R.id.arc_progress_dialog_message);
        if (!BaseUtils.is_str_blank(this.message)) {
            message_view.setText(this.message);
        }else{
            message_view.setVisibility(View.GONE);
        }
        arc_view = (ArcView)findViewById(R.id.arc_progress_av);
        count_view = (TextView) findViewById(R.id.arc_progress_count);

        set_progress(0);
    }


    public void set_progress(int current_num){
        this.current_num = current_num;
        int angle = current_num*360 / total_num;
        arc_view.set_arc_angle(angle);
        count_view.setText(current_num + "/" + total_num);
    }

    public static ArcProgressDialog show(Context context, int total_num) {
        ArcProgressDialog dialog = new ArcProgressDialog(context, total_num);
        dialog.show();
        return dialog;
    }

}
