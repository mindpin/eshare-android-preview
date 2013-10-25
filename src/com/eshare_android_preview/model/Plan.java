package com.eshare_android_preview.model;

import java.io.Serializable;


// 计划
public class Plan implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
