package com.eshare_android_preview.model;

import com.eshare_android_preview.model.elog.ExperienceLog;

import java.util.Calendar;

/**
 * Created by fushang318 on 13-12-11.
 */
public class DayExpInfo {
    public String day_of_week_str;
    public String day_of_month_str;
    public int exp_num;

    public DayExpInfo(String course, Calendar c){
        set_day_of_week_str(c);
        this.day_of_month_str = c.get(Calendar.DAY_OF_MONTH) + "";
        this.exp_num = ExperienceLog.get_exp_num_by_day(course, c);
    }

    public void set_day_of_week_str(Calendar c) {
        int day = c.get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.MONDAY:
                this.day_of_week_str = "周一";
                break;
            case Calendar.TUESDAY:
                this.day_of_week_str = "周二";
                break;
            case Calendar.WEDNESDAY:
                this.day_of_week_str = "周三";
                break;
            case Calendar.THURSDAY:
                this.day_of_week_str = "周四";
                break;
            case Calendar.FRIDAY:
                this.day_of_week_str = "周五";
                break;
            case Calendar.SATURDAY:
                this.day_of_week_str = "周六";
                break;
            case Calendar.SUNDAY:
                this.day_of_week_str = "周日";
                break;
        }
    }
}
