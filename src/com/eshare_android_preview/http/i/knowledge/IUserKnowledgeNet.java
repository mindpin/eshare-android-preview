package com.eshare_android_preview.http.i.knowledge;

import com.eshare_android_preview.base.view.knowledge_map.IHasChildren;

import java.util.List;

/**
 * Created by Administrator on 14-1-13.
 */
public abstract class IUserKnowledgeNet implements IUserSimpleKnowledgeNet, INetHasChildren {
    // 经验值信息
    abstract public IUserExp get_exp();
}
