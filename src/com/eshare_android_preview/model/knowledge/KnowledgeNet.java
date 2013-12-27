package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.base.view.knowledge_map.IHasChildren;
import com.eshare_android_preview.model.parse.node.KnowledgeNetXMLParse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class KnowledgeNet implements IHasChildren{
    private static HashMap<String,KnowledgeNet> net_map = new HashMap<String,KnowledgeNet>();

    public KnowledgeNetXMLParse parse;
    public List<KnowledgeSet> sets;
    public List<KnowledgeCheckpoint> checkpoints;
    public List<KnowledgeSet> root_sets;

    public KnowledgeNet(KnowledgeNetXMLParse parse){
        this.parse = parse;
    }

    public void syn_parse_data_to_field(){
        this.sets = parse.node_set_list;
        this.checkpoints = parse.check_point_list;
        this.root_sets = parse.root_sets;
    }

    public List<BaseKnowledgeSet> children() {
        ArrayList<BaseKnowledgeSet> re = new ArrayList<BaseKnowledgeSet>();
        for (BaseKnowledgeSet set : root_sets) {
            re.add(set);
        }
        return re;
    }

    public static KnowledgeNet find_by_name(String name){
        KnowledgeNet net = net_map.get(name);
        if(net == null){
            net = KnowledgeNetXMLParse.parse(name).net;
            net_map.put(name, net);
        }

        return net;
    }

    public static ArrayList<KnowledgeNet> all(){
        return new ArrayList<KnowledgeNet>(net_map.values());
    }

    public static KnowledgeNet get_current_net(){
        return find_by_name("javascript");
    }

    public static KnowledgeNet instance(){
        return find_by_name("javascript");
    }

    public static KnowledgeNet instance_for_test(){
        return find_by_name("test");
    }

    public KnowledgeSet find_set_by_id(String set_id){
        return this.parse.node_set_map.get(set_id);
    }

    public KnowledgeCheckpoint find_checkpoint_by_id(String checkpoint_id){
        return this.parse.check_point_map.get(checkpoint_id);
    }

    public KnowledgeNode find_node_by_id(String node_id){
        return this.parse.node_map.get(node_id);
    }

}