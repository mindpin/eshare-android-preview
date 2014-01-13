package com.eshare_android_preview.http.i.knowledge;

import com.eshare_android_preview.http.i.IDataIcon;

import java.util.List;

/**
 * Created by Administrator on 14-1-13.
 */
public interface IUserKnowledgeSet {
    // ID
    public String get_id();

    // 单元名称
    public String get_name();

    // 单元图标
    public IDataIcon get_icon();

    // 子单元信息
    public List<IUserKnowledgeSet> children();

    // 父单元信息
    public List<IUserKnowledgeSet> parents();

    // 是否是根节点
    public boolean is_root();

    // 是否是 checkpoint
    public boolean is_checkpoint();

    // 是否已经解锁
    public boolean is_unlocked();

    // 节点信息
    public List<IUserKnowledgeNode> nodes();
    public List<IUserKnowledgeNode> nodes_remoate();
    public List<IUserKnowledgeNode> nodes_local();
}
