package com.eshare_android_preview.model.knowledge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.activity.base.tab_activity.HomeActivity;
import com.eshare_android_preview.model.knowledge.base.ILearn;
import com.eshare_android_preview.model.knowledge.base.IParentAndChild;


public class BaseKnowledgeSet implements ILearn, IParentAndChild<KnowledgeSetRelation, BaseKnowledgeSet>, HomeActivity.IHasChildren {
    protected List<KnowledgeSetRelation> relations = new ArrayList<KnowledgeSetRelation>();
    protected List<BaseKnowledgeSet> parents = new ArrayList<BaseKnowledgeSet>();
    protected List<BaseKnowledgeSet> children = new ArrayList<BaseKnowledgeSet>();
    public int deep;
    public String id;

    @Override
    public List<KnowledgeSetRelation> relations() {
        return relations;
    }

    @Override
    public List<BaseKnowledgeSet> parents() {
        return parents;
    }

    @Override
    public List<BaseKnowledgeSet> children() {
        return children;
    }


    @Override
    public boolean is_learned() {
        return false;
    }

    @Override
    public boolean is_unlocked() {
        return false;
    }

    @Override
    public void do_learn() {

    }

    public static BaseKnowledgeSet find(String id) {
        BaseKnowledgeSet set = KnowledgeSet.find(id);
        if (null != set) return set;
        return KnowledgeCheckpoint.find(id);
    }

    public boolean is_checkpoint() {
        return false;
    }

    public String get_name() {
        return "";
    }
}
