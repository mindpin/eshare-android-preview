package com.eshare_android_preview.model;

import com.eshare_android_preview.model.database.CourseDBHelper;
import com.eshare_android_preview.model.database.FavouriteDBHelper;
import com.eshare_android_preview.model.database.NoteDBHelper;
import com.eshare_android_preview.model.interfaces.ILearningResource;

import java.io.Serializable;
import java.util.List;


// 计划
public class Course implements Serializable, ILearningResource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 12865444L;
	public int id;
	public String content;
	public boolean checked;
    public Boolean has_note;
    public Boolean is_faved;

	public Course(String content, boolean checked) {
		super();
		this.content = content;
		this.checked = checked;
	}
	public Course(int id, String content, boolean checked) {
		super();
		this.id = id;
		this.content = content;
		this.checked = checked;
	}

    @Override
    public boolean has_note() {
        if(this.has_note == null){
            this.has_note = NoteDBHelper.has_note_from(id + "", Note.Type.PLAN);
        }
        return this.has_note;
    }

    @Override
    public boolean is_faved() {
        if(this.is_faved == null){
            this.is_faved = FavouriteDBHelper.find(id + "", FavouriteDBHelper.Kinds.PLAN) != null;
        }
        return this.is_faved;
    }

    // 检查课程是否在用户的计划中
    public boolean is_in_user_plan(Object user) {
        return checked;
    }
    
    public void add_to_user(Object user){
    	this.checked = true;
    	CourseDBHelper.update(this);
    }
    public void remove_from_user(Object user){
    	this.checked = false;
    	CourseDBHelper.update(this);
    }
    public static List<Course>  courses_of_user(Object user){
    	return CourseDBHelper.find_by_checked(true);
    }
    
    public static List<Course> all(){
    	return CourseDBHelper.all();
    }
    public static Course find(int id){
    	return CourseDBHelper.find_by_id(id);
    }
}
