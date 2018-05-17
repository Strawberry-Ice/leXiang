package com.jfdh.httpmodel;

import java.util.List;

public class PagingData<T>{

	private List<T> content;
	
	private boolean hasNext;

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

}
