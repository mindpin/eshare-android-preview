package com.eshare_android_preview.test.model.knowledge;

import android.test.AndroidTestCase;

import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;

import junit.framework.Assert;

/**
 * Created by fushang318 on 13-11-15.
 */
public class LearnedTest extends AndroidTestCase {
    KnowledgeNet net;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        net = KnowledgeNet.instance();
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
        for(KnowledgeSet set : net.sets){
            if(set.parents().size() == 0){
                Assert.assertTrue(set.is_unlocked());
                for(KnowledgeNode node : set.nodes){
                    if(node.parents().size() == 0){
                        Assert.assertTrue(node.is_unlocked());
                    }else{
                        Assert.assertFalse(node.is_unlocked());
                    }
                }
            }else{
                Assert.assertFalse(set.is_unlocked());
                for(KnowledgeNode node : set.nodes){
                    Assert.assertFalse(node.is_unlocked());
                }
            }
        }

        // checkpoint.is_unlocked() 永远返回 true
        for(KnowledgeCheckpoint cp : net.checkpoints){
            Assert.assertTrue(cp.is_unlocked());
        }

    }


    // 刚开始没有任何数据库记录的情况下学习最前置的知识节点
    public void test_3(){
        for(KnowledgeSet set : net.root_sets){
            for(KnowledgeNode node : set.root_nodes){
                node.do_learn();
                for(KnowledgeNode child : node.children()){
                    Assert.assertTrue(child.is_unlocked());
                }
            }
        }
    }

}
