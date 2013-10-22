package com.eshare_android_preview.model;


// 计划
public class Plan {
	public int id;
	public String content;
	public String checked;
	public Plan(String content, String checked) {
		super();
		this.content = content;
		this.checked = checked;
	}
	public Plan(int id, String content, String checked) {
		super();
		this.id = id;
		this.content = content;
		this.checked = checked;
	}
}
