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
    
    public CurrentState(String course){
    	this.course = course;
    }
    
    public void init_data() {
        ExperienceLog elog = ExperienceLogDBHelper.find_last_data(course);
        if (elog == null){
            this.level = 1;
            this.level_up_exp_num = ExperienceLog.level_up_exp_nums[0];
            this.exp_num = 0;
            return;
        }

        int level_num = 0;
        for(int i=0; i<elog.level_up_exp_nums.length;i++){
            level_num += elog.level_up_exp_nums[i];
            if (level_num > elog.after_exp){
                this.level = i + 1;
                this.level_up_exp_num = elog.level_up_exp_nums[i];
                this.exp_num = elog.after_exp - (level_num - this.level_up_exp_num);
                break;
            }
        }
    }

}
