package com.eshare_android_preview.http.i.knowledge;

/**
 * Created by Administrator on 14-1-13.
 */
public interface IUserExp {
    // 已经获取的总经验值
    public int get_total_exp();

    // 当前等级下已经获取的经验值
    public int get_level_exp();

    // 当前等级
    public int get_level();

    // 到下一等级需要的总经验值
    public int get_next_level_total_exp();

    public void add_exp_num(int add_exp_num);
}
