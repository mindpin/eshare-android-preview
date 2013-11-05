package com.eshare_android_preview.model;

public class TestResult {
	public int correct_count;
	public int error_count;
	
	public void increase_correct_count(){
		this.correct_count++;
	}
	
	public void increase_error_count(){
		this.error_count++;
	}
}
