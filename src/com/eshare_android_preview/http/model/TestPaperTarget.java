package com.eshare_android_preview.http.model;

import com.eshare_android_preview.model.OldQuestion;

import java.util.List;

/**
 * Created by menxu on 13-12-5.
 */
public interface TestPaperTarget {
    public String model();
    public String model_id();
    public String get_course();
    public OldQuestion get_random_question(List<Integer> except_ids);
    public boolean is_learned();
    public int do_learn();
}
