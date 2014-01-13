package com.eshare_android_preview.http.model;


import java.util.Calendar;

public class DayExp {
    public int exp_num;
    public String week_day;
    public String month_day;

    public DayExp(Calendar cal, int exp_num){
        int day = cal.get(Calendar.DAY_OF_WEEK);
        set_week_day(day);

        this.month_day = cal.get(Calendar.DAY_OF_MONTH) + "";
        this.exp_num = exp_num;
    }


    public void set_week_day(int day) {
        switch (day){
            case Calendar.MONDAY:
                this.week_day = "周一";
                break;
            case Calendar.TUESDAY:
                this.week_day = "周二";
                break;
            case Calendar.WEDNESDAY:
                this.week_day = "周三";
                break;
            case Calendar.THURSDAY:
                this.week_day = "周四";
                break;
            case Calendar.FRIDAY:
                this.week_day = "周五";
                break;
            case Calendar.SATURDAY:
                this.week_day = "周六";
                break;
            case Calendar.SUNDAY:
                this.week_day = "周日";
                break;
        }
    }
}
