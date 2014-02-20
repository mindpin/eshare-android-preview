package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.i.IDataIcon;
import com.eshare_android_preview.http.i.knowledge.IUserSimpleKnowledgeNet;

/**
 * Created by Administrator on 14-1-20.
 */
public class SimpleKnowledgeNet implements IUserSimpleKnowledgeNet{
    private String id;
    private String name;

    @Override
    public String get_id() {
        return this.id;
    }

    @Override
    public String get_name() {
        return this.name;
    }

    @Override
    public IDataIcon get_icon() {
//        TODO 未实现
        return null;
    }
}
