package com.eshare_android_preview.http.i.knowledge;

import java.util.List;

/**
 * Created by Administrator on 14-1-14.
 */
public interface IUserBaseKnowledgeSet extends INetHasChildren{
    // ID
    public String get_id();

    // 名称
    public String get_name();

    // 深度
    public int get_deep();

    // 父单元信息
    public List<IUserBaseKnowledgeSet> parents();

    // 是否是根节点
    public boolean is_root();

    // 是否是 checkpoint
    public boolean is_checkpoint();

    // 是否已经解锁
    public boolean is_unlocked();
}
