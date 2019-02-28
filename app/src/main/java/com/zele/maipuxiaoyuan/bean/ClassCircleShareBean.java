package com.zele.maipuxiaoyuan.bean;

import java.io.Serializable;


public class ClassCircleShareBean implements Serializable {
	private String id;                 //自增ID
	private String uid;                //家长UID(分享人)
	private String tid;                //教师UID(分享人)
	private String sid;                //学生UID
	private String teacherName;
	private String sname;               //学生姓名
	private String avatar;              //学生头像
	private String addDateTime;
	private String subTypeName;
	private String state;
	private String addDate;
	private String classId;            //班级ID
	private String type;               //德智体美劳
	private String tag;                 //显示标签
	private String point;              //加分
	private String remark;              //评语
	private String pic;                 //原图
	private String thumb;               //缩略图
	private String agrees;             //赞同数
	private String addTime;               //获取时间
	private String honorId;             //综评记录ID
	private String accessId;           //接入ID


	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getAddDate() {
		return addDate;
	}
	
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getSubTypeName() {
		return subTypeName;
	}
	
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	
	public String getAddDateTime() {
		return addDateTime;
	}
	
	public void setAddDateTime(String addDateTime) {
		this.addDateTime = addDateTime;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getTid() {
		return tid;
	}
	
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public String getSid() {
		return sid;
	}
	
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	public String getSname() {
		return sname;
	}
	
	public void setSname(String sname) {
		this.sname = sname;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getClassId() {
		return classId;
	}
	
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getPoint() {
		return point;
	}
	
	public void setPoint(String point) {
		this.point = point;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getPic() {
		return pic;
	}
	
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public String getThumb() {
		return thumb;
	}
	
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	public String getAgrees() {
		return agrees;
	}
	
	public void setAgrees(String agrees) {
		this.agrees = agrees;
	}
	
	public String getAddTime() {
		return addTime;
	}
	
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
	public String getHonorId() {
		return honorId;
	}
	
	public void setHonorId(String honorId) {
		this.honorId = honorId;
	}
	
	public String getAccessId() {
		return accessId;
	}
	
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
