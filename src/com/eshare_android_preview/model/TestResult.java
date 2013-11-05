package com.eshare_android_preview.model;

import java.io.Serializable;

public class TestResult implements Serializable{
    private static final long serialVersionUID = 34564L;

	public int correct_count;
	public int error_count;
	
	public void increase_correct_count(){
		this.correct_count++;
	}
	
	public void increase_error_count(){
		this.error_count++;
	}
}
