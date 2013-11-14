package com.eshare_android_preview.model.knowledge.base;

import java.util.List;

public interface IParentAndChild<M,N>{
	public List<M> relations();
	public List<N> parents();
	public List<N> children();
}
