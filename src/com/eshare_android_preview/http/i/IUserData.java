package com.eshare_android_preview.http.i;

import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNet;
import com.eshare_android_preview.http.i.knowledge.IUserSimpleKnowledgeNet;
import com.eshare_android_preview.http.i.profile.IUserProfile;

import java.util.List;

/**
 * 用户信息包装接口，此接口的实现类实现以下三个用途：
 * 1 所有用户信息都通过此类包装。
 * 2 所有 HTTP 请求都通过此类发起。
 * 3 所有本地持久化存储的逻辑都由此类代理。
 * Created by Administrator on 14-1-13.
 */
public interface IUserData {
    // 获取用户基本信息，优先从本地持久化中获取，如果本地持久化中没有数据，则从HTTP请求获取。
    // 以下所有数据获取方法都如此实现。
    // _remote() 方法，强制从 HTTP 请求数据，数据请求过来之后先写入本地持久化存储。有一定耗时。
    // _local() 方法，从本地持久化获取数据，如果本地无数据，则返回 null，耗时较短
    public IUserProfile get_profile(boolean remote);

    // 获取所有知识网络基础信息（List 中的 IUserKnowledgeNet 对象只包含 ID, NAME 信息）
    public List<IUserSimpleKnowledgeNet> get_knowledge_net_ids(boolean remote);

    // 根据知识网络 ID 获取指定的知识网络数据
    public IUserKnowledgeNet get_knowledge_net(boolean remote, String net_id);

    // 设置和获取“当前选择”的知识网络信息
    public void set_current_knowlegde_net(IUserKnowledgeNet net);
    public IUserKnowledgeNet get_current_knowledge_net(boolean remote);
    public IUserKnowledgeNet get_current_knowledge_net();
    public void set_current_knowledge_net_id(String net_id);
    public String get_current_knowledge_net_id();
}
