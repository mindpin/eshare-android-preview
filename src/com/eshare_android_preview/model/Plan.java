package com.eshare_android_preview.model;

import com.eshare_android_preview.model.database.NotesDBHelper;
import com.eshare_android_preview.model.interfaces.ILearningResource;

import java.io.Serializable;


// 计划
public class Plan implements Serializable, ILearningResource {
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

    @Override
    public boolean has_note() {
        return NotesDBHelper.has_note_from(id + "", Notes.Type.PLAN);
    }

    @Override
    public boolean is_faved() {
        return false;
    }
}
