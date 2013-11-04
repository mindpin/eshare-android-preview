package com.eshare_android_preview.model;

import java.io.Serializable;

// 笔记
public class Note implements Serializable{
    public static class Type{
        public static final String QUESTION = "com.eshare_android_preview.model.Question";
        public static final String NODE = "com.eshare_android_preview.model.KnowledgeNetNode";
        public static final String PLAN = "com.eshare_android_preview.model.Plan";
    }
	/**
	 * 
	 */
	private static final long serialVersionUID = 19757L;
	public int id;
    public String type;
	public String type_id;
	public String content;
	public byte[] img;
	
	public Object obj;
	public Note(String type, String type_id, String content, byte[] img) {
		super();
		this.type = type;
		this.type_id = type_id;
		this.content = content;
		this.img = img;
	}
	
	
	
	public Note(String type, String content, byte[] img, Object obj) {
		super();
		this.type = type;
		this.content = content;
		this.img = img;
		this.obj = obj;
		System.out.println("type  " + type);
		if (obj!=null) {
			if (this.type.indexOf(Type.QUESTION) != -1) {
				this.type_id = ((Question) obj).id + "";
			}
			if (this.type.indexOf(Type.NODE) != -1) {
				this.type_id = ((KnowledgeNetNode) obj).node_id + "";
			}
			if (this.type.indexOf(Type.PLAN) != -1) {
				this.type_id = ((Course) obj).id + "";
			}
		}
	}



	public Note(int id, String type, String type_id, String content, byte[] img) {
		super();
		this.id = id;
		this.type = type;
		this.type_id = type_id;
		this.content = content;
		this.img = img;
	}
	
	
}
