package com.eshare_android_preview.http.i.knowledge;

import com.eshare_android_preview.http.i.IDataIcon;
import com.eshare_android_preview.http.i.concept.IConcept;

import java.util.List;

/**
 * Created by Administrator on 14-1-13.
 */
public interface IUserKnowledgeSet extends INetHasChildren {
    // 单元图标
    public IDataIcon get_icon();

    // ID
    public String get_id();

    // 名称
    public String get_name();

    // 深度
    public int get_deep();

    // 父单元信息
    public List<IUserKnowledgeSet> parents();

    // 是否是根节点
    public boolean is_root();

    // 是否是 checkpoint
    public boolean is_checkpoint();

    // 是否已经解锁
    public boolean is_unlocked();

    public boolean is_learned();

    // 节点信息
    public List<IUserKnowledgeNode> nodes(boolean remote);
    public List<IUserKnowledgeNode> nodes();

    // 获取此单元下所有概念
    public List<IConcept> concepts(boolean remote);
}
