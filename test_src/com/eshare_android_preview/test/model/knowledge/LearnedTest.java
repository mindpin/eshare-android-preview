package com.eshare_android_preview.test.model.knowledge;

import android.test.AndroidTestCase;

import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.BaseParse;
import com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;
import com.nineoldandroids.animation.ObjectAnimator;

import junit.framework.Assert;

import org.junit.Assume;

/**
 * Created by fushang318 on 13-11-15.
 */
public class LearnedTest extends AndroidTestCase {
    KnowledgeNet net;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        net = KnowledgeNet.instance_for_test();
    }

    // 刚开始没有任何数据库记录的情况下的学习状态
    public void test_1(){
        for(KnowledgeSet set : net.sets){
            Assert.assertFalse(set.is_learned());

            for(KnowledgeNode node : set.nodes){
                Assert.assertFalse(node.is_learned());
            }
        }

        for(KnowledgeCheckpoint cp : net.checkpoints){
            Assert.assertFalse(cp.is_learned());
        }
    }

    // 刚开始没有任何数据库记录的情况下的解锁状态
    public void test_2(){
        KnowledgeSet set_8 = KnowledgeSet.find("set-8");
        KnowledgeNode node_31 = KnowledgeNode.find("node-31");
        Assert.assertTrue(set_8.is_unlocked());
        Assert.assertTrue(node_31.is_unlocked());

        KnowledgeSet set_2 = KnowledgeSet.find("set-2");
        KnowledgeNode node_32 = KnowledgeNode.find("node-32");
        KnowledgeNode node_35 = KnowledgeNode.find("node-35");

        Assert.assertFalse(set_2.is_unlocked());
        Assert.assertFalse(node_32.is_unlocked());
        Assert.assertFalse(node_35.is_unlocked());

        KnowledgeCheckpoint checkpoint = KnowledgeCheckpoint.find("checkpoint-1");
        Assert.assertTrue(checkpoint.is_unlocked());
    }


    // 刚开始没有任何数据库记录的情况下学习最前置的知识节点
    public void test_3(){
        KnowledgeNode node_31 = KnowledgeNode.find("node-31");
        KnowledgeNode node_32 = KnowledgeNode.find("node-32");
        KnowledgeNode node_35 = KnowledgeNode.find("node-35");

        node_31.do_learn();
        Assert.assertTrue(node_31.is_learned());
        Assert.assertTrue(node_32.is_unlocked());
        Assert.assertTrue(node_35.is_unlocked());
    }

    // 刚开始没有任何数据库记录的情况下学习第一个知识单元下的所有知识节点
    public void test_4(){
        KnowledgeNode node_31 = KnowledgeNode.find("node-31");
        KnowledgeNode node_32 = KnowledgeNode.find("node-32");
        KnowledgeNode node_35 = KnowledgeNode.find("node-35");
        KnowledgeNode node_33 = KnowledgeNode.find("node-33");
        KnowledgeNode node_34 = KnowledgeNode.find("node-34");
        KnowledgeSet set_8 = KnowledgeSet.find("set-8");

        node_31.do_learn();
        Assert.assertTrue(node_31.is_learned());
        Assert.assertTrue(node_32.is_unlocked());
        Assert.assertTrue(node_35.is_unlocked());
        Assert.assertFalse(set_8.is_learned());

        node_32.do_learn();
        Assert.assertTrue(node_32.is_learned());
        Assert.assertTrue(node_33.is_unlocked());
        Assert.assertFalse(set_8.is_learned());

        node_33.do_learn();
        Assert.assertTrue(node_33.is_learned());
        Assert.assertTrue(node_34.is_unlocked());
        Assert.assertFalse(set_8.is_learned());

        node_34.do_learn();
        Assert.assertTrue(node_34.is_learned());
        Assert.assertTrue(set_8.is_learned());

        Assert.assertFalse(node_35.is_learned());


        KnowledgeSet set_2 = KnowledgeSet.find("set-2");
        Assert.assertTrue(set_2.is_unlocked());

        KnowledgeNode node_6 = KnowledgeNode.find("node-6");
        Assert.assertTrue(node_6.is_unlocked());


        KnowledgeSet set_3 = KnowledgeSet.find("set-3");
        KnowledgeSet set_1 = KnowledgeSet.find("set-1");
        Assert.assertFalse(set_3.is_unlocked());
        Assert.assertFalse(set_1.is_unlocked());
    }

    // 刚开始没有任何数据库记录的情况下通过一个检查点
    public void test_5(){
        KnowledgeCheckpoint checkpoint = KnowledgeCheckpoint.find("checkpoint-1");
        checkpoint.do_learn();

        KnowledgeSet set_8 = KnowledgeSet.find("set-8");
        KnowledgeSet set_2 = KnowledgeSet.find("set-2");
        KnowledgeSet set_3 = KnowledgeSet.find("set-3");
        KnowledgeSet set_1 = KnowledgeSet.find("set-1");

        Assert.assertTrue(set_8.is_learned());
        Assert.assertTrue(set_2.is_learned());
        Assert.assertTrue(set_3.is_learned());
        Assert.assertTrue(set_1.is_learned());

        KnowledgeSet set_4 = KnowledgeSet.find("set-4");
        Assert.assertTrue(set_4.is_unlocked());
        KnowledgeNode node_15 = KnowledgeNode.find("node-15");
        Assert.assertTrue(node_15.is_unlocked());


        KnowledgeNode node_31 = KnowledgeNode.find("node-31");
        KnowledgeNode node_32 = KnowledgeNode.find("node-32");
        KnowledgeNode node_35 = KnowledgeNode.find("node-35");
        KnowledgeNode node_33 = KnowledgeNode.find("node-33");
        KnowledgeNode node_34 = KnowledgeNode.find("node-34");

        Assert.assertTrue(node_31.is_learned());
        Assert.assertTrue(node_32.is_learned());
        Assert.assertTrue(node_35.is_learned());
        Assert.assertTrue(node_33.is_learned());
        Assert.assertTrue(node_34.is_learned());

    }

    // 知识节点没有解锁的情况下，不可以学习
    public void test_6(){
        KnowledgeNode node_32 = KnowledgeNode.find("node-32");

        Assert.assertFalse(node_32.is_unlocked());

        try{
            node_32.do_learn();
            Assert.fail("no exception throw");
        }catch(Exception ex){
            Assert.assertTrue(true);
        }

    }

}
