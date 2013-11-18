package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.model.knowledge.base.ILearn;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeSet extends BaseKnowledgeSet implements ILearn{
	public String id;
	public String name;
	public String icon;

	public List<KnowledgeNode> nodes;
    public List<KnowledgeNode> root_nodes;

	public KnowledgeSet(String id, String name, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;

		this.nodes = new ArrayList<KnowledgeNode>();
	}

    @Override
    public boolean is_learned() {
        // TODO 未实现
        return false;
    }

    @Override
    public boolean is_unlocked() {
        // TODO 未实现
        return false;
    }

    @Override
    public void do_learn() {
        // TODO 未实现
    }
}
