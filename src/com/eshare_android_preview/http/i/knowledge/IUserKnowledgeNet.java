package com.eshare_android_preview.http.i.knowledge;


import com.eshare_android_preview.http.i.question.IQuestionLoader;

/**
 * Created by Administrator on 14-1-13.
 */
public abstract class IUserKnowledgeNet implements IUserSimpleKnowledgeNet, INetHasChildren {
    // 经验值信息
    abstract public IUserExp get_exp();

    abstract public IUserKnowledgeSet find_by_set_id(String set_id);
    abstract public IQuestionLoader get_iquestion_loader(String class_name, String id);
    abstract public ICanbeLearned find_learn_target(String type, String id);
}
