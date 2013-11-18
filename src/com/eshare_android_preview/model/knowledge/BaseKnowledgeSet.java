package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.activity.base.tab_activity.HomeActivity;
import com.eshare_android_preview.model.knowledge.base.IParentAndChild;


public class BaseKnowledgeSet implements IParentAndChild<KnowledgeSetRelation, BaseKnowledgeSet>, HomeActivity.IHasChildren {
    protected List<KnowledgeSetRelation> relations = new ArrayList<KnowledgeSetRelation>();
    protected List<BaseKnowledgeSet> parents = new ArrayList<BaseKnowledgeSet>();
    protected List<BaseKnowledgeSet> children = new ArrayList<BaseKnowledgeSet>();
    public int deep;

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
}
