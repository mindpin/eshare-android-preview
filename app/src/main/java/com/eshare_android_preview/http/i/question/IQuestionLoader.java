package com.eshare_android_preview.http.i.question;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 14-1-16.
 */
public interface IQuestionLoader extends Serializable{
    public List<IQuestion> get_questions_remote();
    public String get_id();
    public String get_type();
}
