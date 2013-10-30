package com.eshare_android_preview.model;

import com.eshare_android_preview.model.database.FavouratesDBHelper;
import com.eshare_android_preview.model.database.NotesDBHelper;
import com.eshare_android_preview.model.interfaces.ILearningResource;

import java.io.Serializable;


// 计划
public class Plan implements Serializable, ILearningResource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 12865444L;
	public int id;
	public String content;
	public String checked;
    public Boolean has_note;
    public Boolean is_faved;

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

    @Override
    public boolean has_note() {
        if(this.has_note == null){
            this.has_note = NotesDBHelper.has_note_from(id + "", Notes.Type.PLAN);
        }
        return this.has_note;
    }

    @Override
    public boolean is_faved() {
        if(this.is_faved == null){
            this.is_faved = FavouratesDBHelper.find(id+"", FavouratesDBHelper.Kinds.PLAN) != null;
        }
        return this.is_faved;
    }
}
