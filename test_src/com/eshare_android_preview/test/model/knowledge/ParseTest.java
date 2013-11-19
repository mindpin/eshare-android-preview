package com.eshare_android_preview.test.model.knowledge;

import android.test.AndroidTestCase;

import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.BaseParse;
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
        net = KnowledgeNet.instance_for_test();
    }


    // KnowledgeNet
    public void test_1(){
        Assert.assertEquals(net.sets.size(), 8);
        Assert.assertEquals(net.checkpoints.size(), 1);
        Assert.assertEquals(net.root_sets.size(), 1);
    }

    // KnowledgeSet
    public void test_2(){
        KnowledgeSet set_8 = KnowledgeSet.find("set-8");

        Assert.assertEquals(set_8.id, "set-8");
        Assert.assertEquals(set_8.name, "基础: 值");
        Assert.assertEquals(set_8.icon, "set-8");
        Assert.assertEquals(set_8.deep, 1);
        Assert.assertEquals(set_8.nodes.size(), 5);
        Assert.assertEquals(set_8.root_nodes.size(), 1);
        Assert.assertEquals(set_8.parents().size(), 0);
        Assert.assertEquals(set_8.children().size(), 1);
    }

    // KnowledgeCheckpoint
    public void test_3(){
        Assert.assertEquals(net.checkpoints.size(), 1);
        KnowledgeCheckpoint checkpoint = net.checkpoints.get(0);
        Assert.assertEquals(checkpoint.id, "checkpoint-1");
        Assert.assertEquals(checkpoint.learned_sets.size(), 4);
        Assert.assertEquals(checkpoint.parents().size(), 2);
        Assert.assertEquals(checkpoint.children().size(), 1);
    }

    // KnowledgeNode
    public void test_4(){

        KnowledgeNode node_31 = KnowledgeNode.find("node-31");

        Assert.assertEquals(node_31.name, "字符串");
        Assert.assertEquals(node_31.required, true);

        Assert.assertEquals(((KnowledgeSet)node_31.base_node_set).id, "set-8");
        Assert.assertEquals(node_31.children().size(), 2);
        Assert.assertEquals(node_31.parents().size(), 0);
        Assert.assertEquals(node_31.relations().size(), 2);
        Assert.assertEquals(node_31.relations().get(0).parent, node_31);

    }




}
