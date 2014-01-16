package com.eshare_android_preview.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.utils.BaseUtils;

public class EshareProgressDialog extends Dialog{
	public String message;
	public EshareProgressDialog(Context context) {
		super(context);
	}
	public EshareProgressDialog(Context context, String message){
		super(context, R.style.base_progress_dialog);
		this.message = message;
	}
	public EshareProgressDialog(Context context, int resId){
		super(context, R.style.base_progress_dialog);
		this.message = context.getString(resId);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        setContentView(R.layout.base_progress_dialog);
        if (!BaseUtils.is_str_blank(this.message)) {
            TextView message_textview = (TextView) findViewById(R.id.blog_progress_dialog_message);
            message_textview.setText(this.message);
        }
	}
	public static EshareProgressDialog show(Context context,String message) {
		EshareProgressDialog dialog = new EshareProgressDialog(context, message);
		dialog.show();
		return dialog;
	}
	public static EshareProgressDialog show(Context context) {
		EshareProgressDialog dialog = new EshareProgressDialog(context);
		dialog.show();
		return dialog;
	}
}
