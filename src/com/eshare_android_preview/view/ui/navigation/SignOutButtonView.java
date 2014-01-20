package com.eshare_android_preview.view.ui.navigation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.view.BorderRadiusRelativeLayout;

/**
 * Created by Administrator on 14-1-20.
 */
public class SignOutButtonView extends BorderRadiusRelativeLayout {
    public SignOutButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        TextView tv = new TextView(getContext());
        tv.setText("账号登出");
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(18);
        tv.setShadowLayer(1, 1, 1, Color.parseColor("#66000000"));
        tv.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        tv.setLayoutParams(lp);
        addView(tv);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setMessage("要登出当前账号吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                UserData.instance().sign_out(getContext());
                            }
                        }).show();
            }
        });
    }
}
