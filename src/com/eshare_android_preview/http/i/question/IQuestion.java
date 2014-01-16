package com.eshare_android_preview.http.i.question;

import com.eshare_android_preview.http.model.Question;
import com.eshare_android_preview.http.model.QuestionSelectAnswer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 14-1-16.
 */
public interface IQuestion {
    public String kind_desc();
    public List<ContentToken> content();

    public class ContentToken implements Serializable {
        public String type;
        public HashMap<String, Object> data;
    }

    public boolean is_fill();
    public boolean is_true_false();
    public boolean is_single_choice();
    public boolean is_multiple_choice();

    public List<IChoice> choices();

    // TODO 最后重构
    public QuestionSelectAnswer build_select_answer();
    public boolean is_choice_contain_image();
    public int choice_max_length();
}
