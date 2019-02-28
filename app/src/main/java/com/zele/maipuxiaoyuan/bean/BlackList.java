package com.zele.maipuxiaoyuan.bean;

import java.io.Serializable;


public class BlackList implements Serializable{
    private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
    private Long sid;
	private String username; //免打饶名称


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
