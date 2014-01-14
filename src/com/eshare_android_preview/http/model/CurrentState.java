package com.eshare_android_preview.http.model;

public class CurrentState {
    public static CurrentState current_state = new CurrentState();
	public int level = 1;
    public int level_up_exp_num = 30;
    public int exp_num = 0;
    public int total_exp_num = 0;
    
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLevel_up_exp_num() {
		return level_up_exp_num;
	}
	public void setLevel_up_exp_num(int level_up_exp_num) {
		this.level_up_exp_num = level_up_exp_num;
	}
	public int getExp_num() {
		return exp_num;
	}
	public void setExp_num(int exp_num) {
		this.exp_num = exp_num;
	}


    // 传入的级别是当前级别，求得的是当前级别到下一级别所需的经验
    // 例如：传入的是 1 ，则求出的是 1 ~ 2 升级所需经验
    // 传入的是 3 ，则求出的是 3 ~ 4 升级所需经验
    public static int get_level_up_exp(int level) {
        int base = 30;
        int p1 = 20 * (level - 1);
        int p2 = 10 * (level - 2) * (level - 1) / 2;

        return base + p1 + p2;
    }
}
