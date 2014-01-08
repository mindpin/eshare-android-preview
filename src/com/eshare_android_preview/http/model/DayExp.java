package com.eshare_android_preview.http.model;


import java.util.Calendar;

public class DayExp {
    public int exp_num;
    public String week_day;
    public String month_day;

    /**
     *
     * @param history_day 表示几天前，比如，一天前 1， 两天前 2
     * @param history_info
     */
    public DayExp(int history_day, int[] history_info){
        int cal_day = -history_day;
        int pos = 5 - history_day;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, cal_day);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        set_week_day(day);

        this.month_day = cal.get(Calendar.DAY_OF_MONTH) + "";

        this.exp_num = history_info[pos];
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
