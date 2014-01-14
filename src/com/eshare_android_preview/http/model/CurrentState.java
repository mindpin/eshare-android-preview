package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.knowledge.IUserExp;

public class CurrentState implements IUserExp {
	private int level = 1;
    private int level_up_exp_num = 30;
    private int exp_num = 0;
    private int total_exp_num = 0;
    
    // 传入的级别是当前级别，求得的是当前级别到下一级别所需的经验
    // 例如：传入的是 1 ，则求出的是 1 ~ 2 升级所需经验
    // 传入的是 3 ，则求出的是 3 ~ 4 升级所需经验
    public static int get_level_up_exp(int level) {
        int base = 30;
        int p1 = 20 * (level - 1);
        int p2 = 10 * (level - 2) * (level - 1) / 2;

        return base + p1 + p2;
    }

    @Override
    public int get_total_exp() {
        return total_exp_num;
    }

    @Override
    public int get_level_exp() {
        return exp_num;
    }

    @Override
    public int get_level() {
        return level;
    }

    @Override
    public int get_next_level_total_exp() {
        return level_up_exp_num;
    }
}
