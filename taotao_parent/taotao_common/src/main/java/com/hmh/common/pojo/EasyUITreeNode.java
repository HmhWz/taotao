package com.hmh.common.pojo;

import java.io.Serializable;

public class EasyUITreeNode implements Serializable {
	private Long id;
	private String text;
	private String state;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
