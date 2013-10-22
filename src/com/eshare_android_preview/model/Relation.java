package com.eshare_android_preview.model;

// 关系
public class Relation {
	public String parent_id;
	public String child_id;
	
	public Relation(String parent_id, String child_id) {
		super();
		this.parent_id = parent_id;
		this.child_id = child_id;
	}
}
