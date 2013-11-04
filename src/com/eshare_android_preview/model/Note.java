package com.eshare_android_preview.model;

import com.eshare_android_preview.model.database.NoteDBHelper;

import java.io.Serializable;
import java.util.List;

// 笔记
public class Note implements Serializable{
    public static List<Note> all(){
        return NoteDBHelper.all();
    }

    public boolean save(){
        NoteDBHelper.create(this);
        return true;
    }

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

			if (this.is_belong_question()) {
				this.type_id = ((Question) obj).id + "";
			}
			if (this.is_belong_knowledge_net_node()) {
				this.type_id = ((KnowledgeNetNode) obj).node_id + "";
			}
			if (this.is_belong_course()) {
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

    public boolean is_belong_question(){
        return is_belong(Question.class);
    }

    public boolean is_belong_course(){
        return is_belong(Course.class);
    }
    public boolean is_belong_knowledge_net_node(){
        return is_belong(KnowledgeNetNode.class);
    }

    private boolean is_belong(Class clazz){
        try {
            return Class.forName(this.type) == clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
