package com.eshare_android_preview.model;

import com.eshare_android_preview.model.database.CourseDBHelper;

import java.io.Serializable;
import java.util.List;


// 计划
public class Course extends LearningResource implements Serializable {
    public static final String COURSE_XML_PATH = "course_names.xml";
    /**
	 * 
	 */
	private static final long serialVersionUID = 12865444L;
	public int id;
	public String content;
	public boolean checked;

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
    public String get_note_foreign_key_id() {
        return this.id + "";
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
