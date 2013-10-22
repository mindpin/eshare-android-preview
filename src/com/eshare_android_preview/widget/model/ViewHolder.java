package com.eshare_android_preview.widget.model;

import android.widget.CheckBox;
import android.widget.TextView;

public class ViewHolder {
	public TextView tv;  
	public CheckBox cb;
	public TextView getTv() {
		return tv;
	}
	public void setTv(TextView tv) {
		this.tv = tv;
	}
	public CheckBox getCb() {
		return cb;
	}
	public void setCb(CheckBox cb) {
		this.cb = cb;
	}
}
