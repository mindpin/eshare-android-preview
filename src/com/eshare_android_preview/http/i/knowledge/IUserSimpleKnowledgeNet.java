package com.eshare_android_preview.http.i.knowledge;

import com.eshare_android_preview.http.i.IDataIcon;

/**
 * Created by Administrator on 14-1-13.
 */
public interface IUserSimpleKnowledgeNet {
    // ID
    public String get_id();

    // 知识网络名称
    public String get_name();

    // 知识网络图标
    public IDataIcon get_icon();
}
