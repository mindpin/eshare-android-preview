package com.eshare_android_preview.model.knowledge.base;

import java.util.List;

public interface IParentAndChild<M>{
	public List<M> relations();
	public List<M> parents();
	public List<M> children();
}
