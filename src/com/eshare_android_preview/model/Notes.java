package com.eshare_android_preview.model;

// 笔记
public class Notes {
	public int id;
	public String question_id;
	public String content;
	public byte[] img;
	public Notes(String question_id, String content, byte[] img) {
		super();
		this.question_id = question_id;
		this.content = content;
		this.img = img;
	}
	public Notes(int id, String question_id, String content, byte[] img) {
		super();
		this.id = id;
		this.question_id = question_id;
		this.content = content;
		this.img = img;
	}
}
