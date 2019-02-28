package com.zele.maipuxiaoyuan.bean;

import android.support.annotation.NonNull;

import com.zele.maipuxiaoyuan.common.utils.TextPinyinUtil;

import java.io.Serializable;

public class ClassTeacher implements Serializable,Comparable<ClassTeacher> {
    private int friendUid;
    private String remark;
    private String userName;
    private String friendName;
    private String avatar;
    private String areaName;
    private String accessName;
    private String className;
    
    public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAccessName() {
		return accessName;
	}

	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getFriendUid() {
        return friendUid;
    }

    public void setFriendUid(int friendUid) {
        this.friendUid = friendUid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    @Override
    public int compareTo(@NonNull ClassTeacher another) {
        return TextPinyinUtil.getInstance().getPinyin(friendName).compareTo(TextPinyinUtil.getInstance().getPinyin(another.getFriendName()));
    }
}
