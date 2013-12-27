package com.eshare_android_preview.model.knowledge.base;

import com.eshare_android_preview.model.Question;

import java.util.List;

/**
 * Created by menxu on 13-12-5.
 */
public interface TestPaperTarget {
    public String model();
    public String model_id();
    public String get_course();
    public Question get_random_question(List<Integer> except_ids);
    public int exp_num();
    public boolean is_learned();
}
