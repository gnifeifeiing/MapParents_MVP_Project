package com.zele.maipuxiaoyuan.bean;

import com.hyphenate.chat.EMGroup;

import java.io.Serializable;

public class ClassGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String groupId;
	private String groupName;
	private String admin;
	private Integer accessId;

	public ClassGroup(EMGroup g) {
		this.groupId = g.getGroupId();
		this.groupName = g.getDescription();
		this.admin = g.getOwner();
		id = Integer.parseInt(admin.substring(6));
	}
	public ClassGroup() {
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public Integer getAccessId() {
		return accessId;
	}

	public void setAccessId(Integer accessId) {
		this.accessId = accessId;
	}
}
