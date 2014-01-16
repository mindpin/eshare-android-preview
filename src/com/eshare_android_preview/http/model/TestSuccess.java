package com.eshare_android_preview.http.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TestSuccess {
    public int add_exp_num;
    private int[] history_info;
    public List<DayExp> day_exps = new ArrayList<DayExp>();

    public static TestSuccess build_from_json(String json){
        Gson gson = new Gson();
        TestSuccess ts = gson.fromJson(json, TestSuccess.class);
        ts.build_day_exps();
        return ts;
    }

    private void build_day_exps() {
        for(int i = 0; i < 5; i++){
            int cal_day = i-4;
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, cal_day);
            DayExp day_exp = new DayExp(cal, history_info[i]);
            day_exps.add(day_exp);
        }
    }

}
