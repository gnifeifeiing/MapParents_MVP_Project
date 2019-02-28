package com.zele.maipuxiaoyuan.bean;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zele.maipuxiaoyuan.common.utils.TextPinyinUtil;

import java.io.Serializable;

public class ClassParents implements Serializable,Comparable<ClassParents> {
    private int uid;
    private int id;
    private int friendUid;
    private int state;
    private String avatar;
    private String first;
    private String friendName;
    private String userName;
    private String remark;
    private int sid;
    private String areaName;
    private String accessName;
    private String className;
    private String studentName;
    private int studentId;
    private String relaName;
    
    public String getRelaName() {
		return relaName;
	}
    public void setRelaName(String relaName) {
		this.relaName = relaName;
	}
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

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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

	public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFriendUid() {
        return friendUid;
    }

    public void setFriendUid(int friendUid) {
        this.friendUid = friendUid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	 @Override
	    public int compareTo(@NonNull ClassParents another) {
	        if(!TextUtils.isEmpty(studentName) && !TextUtils.isEmpty(another.getStudentName())){
	            return TextPinyinUtil.getInstance().getPinyin(studentName).compareTo(TextPinyinUtil.getInstance().getPinyin(another.getStudentName()));
	        }else if(!TextUtils.isEmpty(studentName)){
	            return 1;
	        }
	        return -1;
	    }
}
