package com.eshare_android_preview.model;

import java.io.Serializable;
import java.util.List;

// 问题
public class Question implements Serializable{
	public static class Type{
		public static final String   SINGLE_CHOICE = "single_choice";
		public static final String   MULTIPLE_CHOICE = "multiple_choices";
		public static final String   TRUE_FALSE = "true_false";
		public static final String   CODE = "code";
	}
	
	private static final long serialVersionUID = 1L;
	public int id;
	public String knowledge_node_id;
	public String kind;
	public String title;
	public List<String> choices_list;
	public String answer;
	
	public String code_type;
	public String desc;
	public String init_code;
	public String rule;
	
	// 判读题
	public Question(int id,String knowledge_node_id, String kind, String title,String answer) {
		super();
		this.id = id;
		this.knowledge_node_id = knowledge_node_id;
		this.kind = kind;
		this.title = title;
		this.answer = answer;
	}
	
	// 选择题
	public Question(int id,String knowledge_node_id, String kind, String title,List<String> choices_list, String answer) {
		super();
		this.id = id;
		this.knowledge_node_id = knowledge_node_id;
		this.kind = kind;
		this.title = title;
		this.choices_list = choices_list;
		this.answer = answer;
	}
	
	// 编程题
	public Question(int id,String knowledge_node_id, String kind, String title,
			String code_type, String desc, String init_code, String rule) {
		super();
		this.id = id;
		this.knowledge_node_id = knowledge_node_id;
		this.kind = kind;
		this.title = title;
		this.code_type = code_type;
		this.desc = desc;
		this.init_code = init_code;
		this.rule = rule;
	}
	
	
	
}
