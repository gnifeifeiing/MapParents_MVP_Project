package com.zele.maipuxiaoyuan.bean;


import android.support.annotation.NonNull;

public class HallHonorBean implements Comparable<HallHonorBean> {
	private Integer id;		//
	private String name;	//名称
	private String small;	//小图片
	private String smallGray;
	private String middle;	//中图片
	private String large;	//大图片
	private Integer percent;
	private Integer total;
	private boolean use;  //是否启用
	private Integer userId;//教师或家长id
	private Integer sid;//无此标识为教师，有此标识为家长
	private boolean custom;//自定义

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSmall() {
		return small;
	}
	public void setSmall(String small) {
		this.small = small;
	}
	public String getMiddle() {
		return middle;
	}
	public void setMiddle(String middle) {
		this.middle = middle;
	}
	public String getLarge() {
		return large;
	}
	public void setLarge(String large) {
		this.large = large;
	}
	public boolean isUse() {
		return use;
	}
	public void setUse(boolean use) {
		this.use = use;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public boolean isCustom() {
		return custom;
	}
	public void setCustom(boolean custom) {
		this.custom = custom;
	}
	public Integer getPercent() {
		return percent;
	}
	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getSmallGray() {
		return smallGray;
	}
	public void setSmallGray(String smallGray) {
		this.smallGray = smallGray;
	}

	@Override
	public int compareTo(@NonNull HallHonorBean h) {
		if(h.isCustom() && !this.isCustom()){
			return -1;
		}else if(!h.isCustom() && this.isCustom()){
			return 1;
		}else{
			return name.compareTo(h.name);
		}
	}
}
