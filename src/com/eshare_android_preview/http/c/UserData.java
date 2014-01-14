package com.eshare_android_preview.http.c;

import com.eshare_android_preview.http.api.KnowledgeNetHttpApi;
import com.eshare_android_preview.http.i.IUserData;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNet;
import com.eshare_android_preview.http.i.knowledge.IUserSimpleKnowledgeNet;
import com.eshare_android_preview.http.i.profile.IUserProfile;
import com.eshare_android_preview.model.preferences.EsharePreference;

import java.util.List;

/**
 * Created by Administrator on 14-1-13.
 */
public class UserData implements IUserData {
    private static IUserData instance = new UserData();

    private IUserKnowledgeNet set_current_knowledge_net;

    static public IUserData instance() {
        return instance;
    }

    @Override
    public IUserProfile get_profile(boolean remote) {
        return null;
    }

    @Override
    public List<IUserSimpleKnowledgeNet> get_knowledge_net_ids(boolean remote) {
        return null;
    }

    @Override
    public IUserKnowledgeNet get_knowledge_net(boolean remote, String net_id) {
        return null;
    }

    @Override
    public void set_current_knowlegde_net(IUserKnowledgeNet net) {
        this.set_current_knowledge_net = net;
    }

    @Override
    public IUserKnowledgeNet get_current_knowledge_net(boolean remote) {
        if(remote){
            String net_id = get_current_knowledge_net_id();
            this.set_current_knowledge_net = KnowledgeNetHttpApi.net(net_id);
        }
        return this.set_current_knowledge_net;
    }

    @Override
    public void set_current_knowledge_net_id(String net_id) {
        EsharePreference.switch_to(net_id);
    }

    @Override
    public String get_current_knowledge_net_id() {
        return EsharePreference.get_current_net();
    }
}
