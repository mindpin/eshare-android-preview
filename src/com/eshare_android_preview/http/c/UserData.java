package com.eshare_android_preview.http.c;

import com.eshare_android_preview.http.api.KnowledgeNetHttpApi;
import com.eshare_android_preview.http.api.UserAuthHttpApi;
import com.eshare_android_preview.http.i.IUserData;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNet;
import com.eshare_android_preview.http.i.knowledge.IUserSimpleKnowledgeNet;
import com.eshare_android_preview.http.i.profile.IUserProfile;
import com.eshare_android_preview.http.model.SimpleKnowledgeNet;
import com.eshare_android_preview.model.preferences.EsharePreference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 14-1-13.
 */
public class UserData implements IUserData {
    private static IUserData instance = new UserData();

    private IUserKnowledgeNet set_current_knowledge_net;
    private IUserProfile user_profile;
    private List<IUserSimpleKnowledgeNet> nets;

    static public IUserData instance() {
        return instance;
    }

    @Override
    public IUserProfile get_profile(boolean remote) {
        if(remote){
            return _get_profile_remote();
        }
        return _get_profile_local();
    }

    private IUserProfile _get_profile_remote(){
        user_profile = UserAuthHttpApi.user_profile();
        return user_profile;
    }

    private IUserProfile _get_profile_local(){
        return user_profile;
    }

    @Override
    public void sign_out() {
        // TODO
    }

    @Override
    public List<IUserSimpleKnowledgeNet> get_knowledge_net_ids(boolean remote) {
        if(remote){
            return _get_knowledge_net_ids_remote();
        }
        return _get_knowledge_net_ids_local();
    }

    private List<IUserSimpleKnowledgeNet> _get_knowledge_net_ids_local(){
        return nets;
    }

    private List<IUserSimpleKnowledgeNet> _get_knowledge_net_ids_remote() {
        List<SimpleKnowledgeNet> temp = KnowledgeNetHttpApi.net_list();
        nets = new ArrayList<IUserSimpleKnowledgeNet>(temp);
        return nets;
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
            return _get_current_knowledge_net_remote();
        }
        return _get_current_knowledge_net_local();
    }

    @Override
    public IUserKnowledgeNet get_current_knowledge_net() {
        IUserKnowledgeNet result = _get_current_knowledge_net_local();
        if(null != result) return result;

        return _get_current_knowledge_net_remote();
    }

    private IUserKnowledgeNet _get_current_knowledge_net_remote(){
        String net_id = get_current_knowledge_net_id();
        this.set_current_knowledge_net = KnowledgeNetHttpApi.net(net_id);
        return this.set_current_knowledge_net;
    }

    private IUserKnowledgeNet _get_current_knowledge_net_local(){
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
