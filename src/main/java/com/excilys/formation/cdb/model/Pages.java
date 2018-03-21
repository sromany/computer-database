package com.excilys.formation.cdb.model;

import java.util.List;

public abstract class Pages<T> {
	protected List<T> page;
	protected int num;
	protected static int pageIndex;
	protected static final int PAGE_STRIDE = 20;
	
	public Pages() {
		
	}
	
	public Pages(List<T> page) {
		this.page = page;
		
	}
	
	public List<T> getPage() {
		return page;
	}
	
	public void setPage(List<T> objects) {
		this.page = objects;
	}
	
	public int getNum() {
		return num;
	}
	
	public static int getFrom() {
		return pageIndex;
	}
	public static int getTo() {
		return pageIndex+PAGE_STRIDE;
	}
	
	public void reset() {
		this.page = null;
		this.num = 0;
		Pages.pageIndex = 0;
	}
	
	public abstract void next();
	
	public abstract void preview();
}