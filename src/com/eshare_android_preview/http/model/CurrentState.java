package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.knowledge.IUserExp;

public class CurrentState implements IUserExp {
	private int level;
    private int level_up_exp_num;
    private int exp_num;
    private int total_exp_num;

    public CurrentState(int total_exp_num){
        this.total_exp_num = total_exp_num;
        _refresh_other_field();
    }

    private void _refresh_other_field() {
        exp_num = total_exp_num;
        level = 1;
        level_up_exp_num = get_level_up_exp(1);

        while(exp_num >= level_up_exp_num) {
            exp_num = exp_num - level_up_exp_num;
            level = level + 1;
            level_up_exp_num = get_level_up_exp(level);
        }
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

    @Override
    public void add_exp_num(int add_exp_num) {
        this.total_exp_num += add_exp_num;
        _refresh_other_field();
    }
}
