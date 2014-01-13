package com.eshare_android_preview.http.i.knowledge;

import java.util.List;

/**
 * Created by Administrator on 14-1-13.
 */
public interface IUserKnowledgeNet extends IUserSimpleKnowledgeNet {
    // 经验值信息
    public IUserExp get_exp();

    // 根单元信息
    public List<IUserKnowledgeSet> children();
}
