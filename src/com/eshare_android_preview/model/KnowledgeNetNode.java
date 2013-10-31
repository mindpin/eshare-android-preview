package com.eshare_android_preview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.model.database.FavouriteDBHelper;
import com.eshare_android_preview.model.database.NoteDBHelper;
import com.eshare_android_preview.model.interfaces.ILearningResource;

// 课程节点
public class KnowledgeNetNode implements Serializable, ILearningResource {
	public static class KnowledgeCategory {
        public String name;
        public int res_id;

        public KnowledgeCategory(String name, int res_id) {
            this.name = name;
            this.res_id = res_id;
        }
    }
	/**
	 * 
	 */
	private static final long serialVersionUID = 216785L;
	public String node_id;
	public String name;
	public String desc;
	public List<String> list_parents = new ArrayList<String>();
	public List<String> list_children = new ArrayList<String>();

    public Boolean has_note;
    public Boolean is_faved;
	
	public KnowledgeNetNode(String node_id, String name, String desc,
                            List<String> list_parents, List<String> list_children) {
		super();
		this.node_id = node_id;
		this.name = name;
		this.desc = desc;
		this.list_parents = list_parents;
		this.list_children = list_children;
	}

    @Override
    public boolean has_note() {
        if(this.has_note == null){
            this.has_note = NoteDBHelper.has_note_from(node_id, Note.Type.NODE);
        }
        return this.has_note;
    }

    @Override
    public boolean is_faved() {
        if(this.is_faved == null){
            this.is_faved = FavouriteDBHelper.find(node_id, FavouriteDBHelper.Kinds.NODE) != null;
        }
        return this.is_faved;
    }
}
