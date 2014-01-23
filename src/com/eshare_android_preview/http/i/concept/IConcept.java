package com.eshare_android_preview.http.i.concept;

import com.eshare_android_preview.http.i.question.IQuestion;
import com.eshare_android_preview.http.model.Question;

import java.util.List;

/**
 * Created by Administrator on 14-1-23.
 */
public interface IConcept {
    // 概念名
    public String get_name();

    //　概念描述
    public String get_desc();

    // 当前用户的练习次数
    public int get_practices_count();

    // 随机获取若干个已学节点下的相关问题
    public List<Question> get_learned_node_random_questions(int count);
}
