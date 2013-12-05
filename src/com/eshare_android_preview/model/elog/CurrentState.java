package com.eshare_android_preview.model.elog;

import com.eshare_android_preview.model.database.ExperienceLogDBHelper;

/**
 * Created by menxu on 13-12-5.
 */
public class CurrentState {
    private static ExperienceLog elog;

    public int level;
    public int level_up_exp_num;
    public int exp_num;

    public static CurrentState current_state(){
        elog = ExperienceLogDBHelper.find_last_data();
        CurrentState state = new CurrentState();
        state.init_data();
        return state;
    }


    public void init_data() {
        int level_num = 0;
        for(int i=0; i<elog.level_up_exp_nums.length;i++){
            level_num += elog.level_up_exp_nums[i];
            if (level_num > elog.after_exp){
                this.level = i + 1;
                this.level_up_exp_num = level_num - elog.after_exp;
                this.exp_num = elog.level_up_exp_nums[i] - this.level_up_exp_num;
            }
        }
    }

}
