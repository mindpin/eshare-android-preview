package com.eshare_android_preview.http.i.knowledge;


/**
 * Created by Administrator on 14-1-13.
 */
public abstract class IUserKnowledgeNet implements IUserSimpleKnowledgeNet, INetHasChildren {
    // 经验值信息
    abstract public IUserExp get_exp();

    abstract public IUserKnowledgeSet find_by_set_id(String set_id);
}
