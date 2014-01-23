package com.eshare_android_preview.http.model;

import com.eshare_android_preview.http.api.KnowledgeNetHttpApi;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.i.concept.IConcept;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNode;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeSet extends BaseKnowledgeSet {
    private String name;
    private String icon;
    private int node_count;
    private int learned_node_count;
    private HashMap<String,IUserKnowledgeNode> node_maps = new HashMap<String,IUserKnowledgeNode>();
    private boolean node_maps_has_cache = false;

    @Override
    public IUserKnowledgeNode find_node(String id) {
        return node_maps.get(id);
    }

    public String get_name(){
        return name;
    }

    @Override
    public boolean is_checkpoint() {
        return false;
    }

    @Override
    public List<IUserKnowledgeNode> nodes(boolean remote) {
        if(remote){
            return _nodes_remote();
        }
        return _nodes_local();
    }

    @Override
    public List<IUserKnowledgeNode> nodes() {
        if(node_maps_has_cache) return _nodes_local();

        return _nodes_remote();
    }

    private List<IUserKnowledgeNode> _nodes_remote(){
        String net_id = UserData.instance().get_current_knowledge_net_id();
        List<KnowledgeNode> temp_nodes = KnowledgeNetHttpApi.set_nodes(net_id, this);
        node_maps = new HashMap<String,IUserKnowledgeNode>();
        for(KnowledgeNode node :temp_nodes){
            node_maps.put(node.get_id(),node);
        }
        node_maps_has_cache = true;
        return new ArrayList<IUserKnowledgeNode>(node_maps.values());
    }

    private List<IUserKnowledgeNode> _nodes_local(){
        return new ArrayList<IUserKnowledgeNode>(node_maps.values());
    }

    public int get_learned_node_count(){
        return learned_node_count;
    }

    public int get_node_count(){
        return node_count;
    }

    @Override
    public boolean is_root(){
        return this.parents.size() == 0;
    }

    @Override
    public boolean is_unlocked() {
        for(IUserKnowledgeSet set : parents){
            if(!set.is_learned()){
                return false;
            }
        }
        return true;
    }

    public void refresh_learned_node_count(){
        learned_node_count = 0;
        for(IUserKnowledgeNode node : this.node_maps.values()){
            if(node.is_learned()) learned_node_count += 1;
        }
    }

	@Override
	public List<IConcept> concepts(boolean remote) {
		// TODO Auto-generated method stub
		return null;
	}

}
