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

    private KnowledgeNet(KnowledgeNetXMLParse parse){
        this.parse = parse;
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
        String xml_path = "knowledge_nets/" + name + ".xml";
        KnowledgeNet net = net_map.get(xml_path);
        if(net == null){
            KnowledgeNetXMLParse parse = KnowledgeNetXMLParse.parse(xml_path);
            net = new KnowledgeNet(parse);
            net_map.put(xml_path, net);
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

}