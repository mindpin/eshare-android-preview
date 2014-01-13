package com.eshare_android_preview.http.c;

import com.eshare_android_preview.http.i.IUserData;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNet;
import com.eshare_android_preview.http.i.knowledge.IUserSimpleKnowledgeNet;
import com.eshare_android_preview.http.i.profile.IUserProfile;

import java.util.List;

/**
 * Created by Administrator on 14-1-13.
 */
public class UserData implements IUserData {
    private static IUserData instance = new UserData();

    @Override
    public IUserData instance() {
        return instance;
    }

    @Override
    public IUserProfile get_profile() {
        return null;
    }

    @Override
    public IUserProfile get_profile_remote() {
        return null;
    }

    @Override
    public IUserProfile get_profile_local() {
        return null;
    }

    @Override
    public List<IUserSimpleKnowledgeNet> get_knowledge_net_ids() {
        return null;
    }

    @Override
    public List<IUserSimpleKnowledgeNet> get_knowledge_net_ids_remote() {
        return null;
    }

    @Override
    public List<IUserSimpleKnowledgeNet> get_knowledge_net_ids_local() {
        return null;
    }

    @Override
    public IUserKnowledgeNet get_knowledge_net(String net_id) {
        return null;
    }

    @Override
    public IUserKnowledgeNet get_knowledge_net_remote(String net_id) {
        return null;
    }

    @Override
    public IUserKnowledgeNet get_knowledge_net_local(String net_id) {
        return null;
    }

    @Override
    public void set_current_knowlegde_net(IUserKnowledgeNet net) {

    }

    @Override
    public IUserKnowledgeNet get_current_knowledge_net() {
        return null;
    }

    @Override
    public IUserKnowledgeNet get_current_knowledge_net_remote() {
        return null;
    }

    @Override
    public IUserKnowledgeNet get_current_knowledge_net_local() {
        return null;
    }
}
