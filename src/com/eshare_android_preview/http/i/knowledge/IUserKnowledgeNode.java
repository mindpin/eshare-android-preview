package com.eshare_android_preview.http.i.knowledge;

import java.util.List;

/**
 * Created by Administrator on 14-1-13.
 */
public interface IUserKnowledgeNode {
    // ID
    public String get_id();

    // 节点名称
    public String get_name();

    // 子节点信息
    public List<IUserKnowledgeNode> children();

    // 父节点信息
    public List<IUserKnowledgeNode> parents();

    // 是否已经解锁
    public boolean is_unlocked();
}
