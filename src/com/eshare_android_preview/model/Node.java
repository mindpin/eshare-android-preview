package com.eshare_android_preview.model;

import com.eshare_android_preview.model.database.FavouratesDBHelper;
import com.eshare_android_preview.model.database.NotesDBHelper;
import com.eshare_android_preview.model.interfaces.ILearningResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 课程节点
public class Node implements Serializable, ILearningResource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String id;
	public String name;
	public String desc;
	public List<String> list_parents = new ArrayList<String>();
	public List<String> list_children = new ArrayList<String>();
    public Boolean has_note;
    public Boolean is_faved;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<String> getList_parents() {
		return list_parents;
	}

	public void setList_parents(List<String> list_parents) {
		this.list_parents = list_parents;
	}

	public List<String> getList_children() {
		return list_children;
	}

	public void setList_children(List<String> list_children) {
		this.list_children = list_children;
	}

	public Node(String id, String name, String desc, List<String> list_parents,List<String> list_children) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.list_parents = list_parents;
		this.list_children = list_children;
	}

    @Override
    public boolean has_note() {
        if(this.has_note == null){
            this.has_note = NotesDBHelper.has_note_from(id, Notes.Type.NODE);
        }
        return this.has_note;
    }

    @Override
    public boolean is_faved() {
        if(this.is_faved == null){
            this.is_faved = FavouratesDBHelper.find(id, FavouratesDBHelper.Kinds.NODE) != null;
        }
        return this.is_faved;
    }
}
