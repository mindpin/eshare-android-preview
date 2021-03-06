package com.eshare_android_preview.controller.testpaper;

import java.io.Serializable;

public class TestResult implements Serializable{
    private static final long serialVersionUID = 34564L;

	public int max_hp;
	public int max_point;
	
	public int hp;
	public int point;

	public void increase_point(){
		this.point++;
	}
	
	public void decrease_hp(){
		this.hp--;
	}
	
	public boolean is_fail(){
		return this.hp == 0;
	}
	
	public boolean is_success(){
		return this.point >= this.max_point;
	}


	public TestResult(int max_hp, int max_point) {
		super();
		this.max_hp = max_hp;
		this.max_point = max_point;
		this.hp = this.max_hp;
	}
	
	
}
