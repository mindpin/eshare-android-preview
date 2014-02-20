package com.eshare_android_preview.http.i.knowledge;

import com.eshare_android_preview.http.i.concept.IConcept;

import java.util.List;

/**
 * Created by Administrator on 14-1-13.
 */
public interface IUserKnowledgeNode {
    // ID
    public String get_id();

    // 节点名称
    public String get_name();

    public String get_desc();

    public boolean get_required();

    // 子节点信息
    public List<IUserKnowledgeNode> children();

    // 父节点信息
    public List<IUserKnowledgeNode> parents();

    // 是否已经解锁
    public boolean is_unlocked();

    public boolean is_learned();

    // 获取此知识点下所有概念
    public List<IConcept> concepts(boolean remote);
}
