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

    // 刚开始没有任何数据库记录的情况下学习第一个知识单元下的所有知识节点
    public void test_4(){
        for(KnowledgeSet set : net.root_sets){
            for(KnowledgeNode node : set.nodes){
                node.do_learn();
            }
            Assert.assertTrue(set.is_learned());

            for(BaseKnowledgeSet child : set.children()){
                if(child.getClass() == KnowledgeSet.class){
                    Assert.assertTrue(((KnowledgeSet)child).is_unlocked());
                    for(KnowledgeNode cnode : ((KnowledgeSet) child).nodes){
                        if(cnode.parents().size() == 0){
                            Assert.assertTrue(cnode.is_unlocked());
                        }else{
                            Assert.assertFalse(cnode.is_unlocked());
                        }
                    }
                }
            }
        }
    }

    // 刚开始没有任何数据库记录的情况下通过一个检查点
    public void test_5(){
        KnowledgeCheckpoint checkpoint = net.checkpoints.get(0);
        checkpoint.do_learn();
        for(KnowledgeSet set : checkpoint.learned_sets){
           Assert.assertTrue(set.is_learned());
           for(KnowledgeNode node : set.nodes){
               Assert.assertTrue(node.is_learned());
           }
           for(BaseKnowledgeSet cset : set.children()){
               if(cset.getClass() == KnowledgeSet.class){
                   Assert.assertTrue(((KnowledgeSet)cset).is_unlocked());
               }
           }
        }
    }

    // 知识节点没有解锁的情况下，不可以学习
    public void test_6(){
        for(BaseKnowledgeSet set : net.root_sets){
            if(set.getClass() == KnowledgeSet.class){
                for(KnowledgeNode node : ((KnowledgeSet)set).root_nodes){
                    for(KnowledgeNode cnode : node.children()){
                        try{
                            cnode.do_learn();
                            Assert.fail("no exception throw");
                        }catch(Exception ex){
                            Assert.assertTrue(true);
                        }
                    }
                }

            }
        }

    }

}
