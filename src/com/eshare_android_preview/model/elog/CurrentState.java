package com.eshare_android_preview.model.elog;

import com.eshare_android_preview.model.database.ExperienceLogDBHelper;

/**
 * Created by menxu on 13-12-5.
 */
public class CurrentState {
    public int level;
    public int level_up_exp_num;
    public int exp_num;

    public String course;

    public CurrentState(String course) {
        this.course = course;
    }

    public void init_data() {
        ExperienceLog elog = ExperienceLogDBHelper.find_last_data(course);

        if (elog == null) {
            level = 1;
            level_up_exp_num = ExperienceLog.get_level_up_exp(1);
            exp_num = 0;
            return;
        }

        level = 1;
        level_up_exp_num = ExperienceLog.get_level_up_exp(1);
        exp_num = elog.after_exp;

        while(exp_num >= level_up_exp_num) {
            exp_num = exp_num - level_up_exp_num;
            level = level + 1;
            level_up_exp_num = ExperienceLog.get_level_up_exp(level);
        }
    }

}
