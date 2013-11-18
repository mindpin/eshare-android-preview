package com.eshare_android_preview.test.model.knowledge;

import android.test.AndroidTestCase;

import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;

import junit.framework.Assert;

/**
 * Created by fushang318 on 13-11-18.
 */

public class ParseTest extends AndroidTestCase {
    private KnowledgeNet net;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        net = KnowledgeNet.instance();
        System.out.println("set up");
    }


    // KnowledgeNet
    public void test_1(){
        Assert.assertEquals(net.sets.size(), 8);
        Assert.assertEquals(net.checkpoints.size(), 1);
        Assert.assertEquals(net.root_sets.size(), 2);
    }

    // KnowledgeSet
    public void test_2(){
        KnowledgeSet rset = null;
        for(KnowledgeSet set : net.root_sets){
            if(set.id.equals("set-1")){
                rset = set;
            }
        }
        Assert.assertEquals(rset.id, "set-1");
        Assert.assertEquals(rset.name, "基础概念：值");
        Assert.assertEquals(rset.icon, "set-1");
        Assert.assertEquals(rset.nodes.size(), 5);
        Assert.assertEquals(rset.root_nodes.size(), 1);
        Assert.assertEquals(rset.parents().size(), 0);
        Assert.assertEquals(rset.children().size(), 1);
    }

    // KnowledgeCheckpoint
    public void test_3(){
        Assert.assertEquals(net.checkpoints.size(), 1);
        KnowledgeCheckpoint checkpoint = net.checkpoints.get(0);
        Assert.assertEquals(checkpoint.id, "checkpoint-1");
        Assert.assertEquals(checkpoint.learned_sets.size(), 4);
        Assert.assertEquals(checkpoint.parents().size(), 2);
        Assert.assertEquals(checkpoint.children().size(), 0);
    }

    // KnowledgeNode
    public void test_4(){
        KnowledgeNode rnode = null;
        for(KnowledgeSet set : net.root_sets){
            if(set.id.equals("set-1")){
                for(KnowledgeNode node : set.root_nodes){
                    if(node.id.equals("node-1")){
                        rnode = node;
                    }
                }
            }
        }

        Assert.assertEquals(rnode.name, "字符串");
        Assert.assertEquals(rnode.required, true);
        Assert.assertEquals(rnode.node_set_id, "set-1");
        Assert.assertEquals(rnode.children().size(), 2);
        Assert.assertEquals(rnode.parents().size(), 0);
        Assert.assertEquals(rnode.relations().size(), 2);
        Assert.assertEquals(rnode.relations().get(0).parent, rnode);
    }




}
