package com.eshare_android_preview.http.i.knowledge;

import com.eshare_android_preview.http.i.IDataIcon;

import java.util.List;

/**
 * Created by Administrator on 14-1-13.
 */
public interface IUserKnowledgeSet extends IUserBaseKnowledgeSet {
    // 单元图标
    public IDataIcon get_icon();

    // 节点信息
    public List<IUserKnowledgeNode> nodes(boolean remote);
}
