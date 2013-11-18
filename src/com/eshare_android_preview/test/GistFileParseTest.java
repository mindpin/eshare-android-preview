package com.eshare_android_preview.test;

import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;

public class GistFileParseTest {
	public static void test() {
		KnowledgeNet net = KnowledgeNet.instance();
		System.out.println("sets ====  "  + net.sets.size());
		System.out.println("checkpoints ====  "  + net.checkpoints.size());
		System.out.println("first_set ====  "  + net.root_sets.get(0).name);
		
		//KnowledgeSet
		System.out.println("-------set-----");
		KnowledgeSet set = net.sets.get(6);
		System.out.println("-------set---net.sets--"  + net.sets.size());
		System.out.println("-------set---net.sets--"  + set.id);
		System.out.println("-------set---net.sets--"  + set.name);
		System.out.println("-------set---net.sets--"  + set.icon);
		System.out.println("-------set---net.sets-nodes-"  + set.nodes.size());
		System.out.println("-------set---net.sets-relations-"  + set.relations().size());
		System.out.println("-------set---net.sets-parents-"  + set.parents().size());
		System.out.println("-------set---net.sets-children-"  + set.children().size());
		System.out.println("-------set-----");
		
		
		//KnowledgeSet
		System.out.println("-------KnowledgeNode-----");
		KnowledgeNode node = set.nodes.get(2);
		System.out.println("-------node---net.sets--"  + node.id);
		System.out.println("-------node---net.sets--"  + node.name);
		System.out.println("-------node---net.sets--"  + node.desc);
		System.out.println("-------node---net.sets-nodes-"  + node.required);
		System.out.println("-------node---net.sets-relations-"  + node.relations().size());
		System.out.println("-------node---net.sets-parents-"  + node.parents().size());
		System.out.println("-------node---net.sets-children-"  + node.children().size());
		System.out.println("-------node-----");
	}
}
