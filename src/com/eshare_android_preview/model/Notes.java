package com.eshare_android_preview.model;

import java.io.Serializable;

// 笔记
public class Notes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public int question_id;
	public String content;
	public byte[] img;
	public Notes(int question_id, String content, byte[] img) {
		super();
		this.question_id = question_id;
		this.content = content;
		this.img = img;
	}
	public Notes(int id, int question_id, String content, byte[] img) {
		super();
		this.id = id;
		this.question_id = question_id;
		this.content = content;
		this.img = img;
	}
}
