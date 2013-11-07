package com.eshare_android_preview.model;

import java.io.Serializable;

public class TestResult implements Serializable{
    private static final long serialVersionUID = 34564L;

	public int allowed_error_count;
	public int needed_correct_count;
	
	public int remaining_error_count;
	public int current_correct_count;
   

	public void increase_correct_count(){
		this.current_correct_count++;
	}
	
	public void increase_error_count(){
		this.remaining_error_count--;
	}
	
	public boolean is_end(){
		return this.remaining_error_count >= this.allowed_error_count;
	}
	
	public boolean is_pass(){
		return this.current_correct_count >= this.needed_correct_count;
	}

	public TestResult(int allowed_error_count, int needed_correct_count) {
		super();
		this.allowed_error_count = allowed_error_count;
		this.needed_correct_count = needed_correct_count;
		this.remaining_error_count = this.allowed_error_count;
	}
	
	
}
