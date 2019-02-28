package com.zele.maipuxiaoyuan.bean;


import java.io.Serializable;

/**
 * sex : 0
 * type : 1
 * userName : 小何
 * className : 一年级二班
 * sid : 5
 * imei : 2160010012
 * number : 20160201
 * avatar : /upload/20160928/20160928201758.png
 * areaName : 河南省焦作市
 * accessName : 沁阳实验小学
 */
public class StudentsBean implements Serializable {
    private String achiName;
    private int achieve;
    private int culture;
    private int curCulture;
    private int curIntel;
    private int curLabour;
    private int curMoral;
    private int curPhysic;
    private int days;
    private int elec;
    private int integral;
    private int intel;
    private String keyCall;
    private int labour;
    private String areaName;
    private String accessName;
    private int accessId;
    private String attendNum;
    private String idCard;
    private int sid; // 学生 SID
    private String userName; // 学生姓名
    private Integer classId;            //班级ID
    private String className; // 所在班级
    private String number; // 学号
    private Integer sex; // 性别
    private String avatar; // 头像
    private String imei; // 设备IMEI号
    private Integer type; // 卡类型 0：儿童机；1：白卡
    private int times;
    private String newTermEvent;

    public String getNewTermEvent() {
        return newTermEvent;
    }

    public void setNewTermEvent(String newTermEvent) {
        this.newTermEvent = newTermEvent;
    }

    @Override
    public String toString() {
        return "StudentsBean{" +
                "achiName='" + achiName + '\'' +
                ", achieve=" + achieve +
                ", culture=" + culture +
                ", curCulture=" + curCulture +
                ", curIntel=" + curIntel +
                ", curLabour=" + curLabour +
                ", curMoral=" + curMoral +
                ", curPhysic=" + curPhysic +
                ", days=" + days +
                ", elec=" + elec +
                ", integral=" + integral +
                ", intel=" + intel +
                ", keyCall='" + keyCall + '\'' +
                ", labour=" + labour +
                ", areaName='" + areaName + '\'' +
                ", accessName='" + accessName + '\'' +
                ", accessId=" + accessId +
                ", attendNum='" + attendNum + '\'' +
                ", idCard='" + idCard + '\'' +
                ", sid=" + sid +
                ", userName='" + userName + '\'' +
                ", classId=" + classId +
                ", className='" + className + '\'' +
                ", number='" + number + '\'' +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", imei='" + imei + '\'' +
                ", type=" + type +
                ", times=" + times +
                '}';
    }

    public String getAchiName() {
        return achiName;
    }

    public void setAchiName(String achiName) {
        this.achiName = achiName;
    }

    public int getAchieve() {
        return achieve;
    }

    public void setAchieve(int achieve) {
        this.achieve = achieve;
    }

    public int getCulture() {
        return culture;
    }

    public void setCulture(int culture) {
        this.culture = culture;
    }

    public int getCurCulture() {
        return curCulture;
    }

    public void setCurCulture(int curCulture) {
        this.curCulture = curCulture;
    }

    public int getCurIntel() {
        return curIntel;
    }

    public void setCurIntel(int curIntel) {
        this.curIntel = curIntel;
    }

    public int getCurLabour() {
        return curLabour;
    }

    public void setCurLabour(int curLabour) {
        this.curLabour = curLabour;
    }

    public int getCurMoral() {
        return curMoral;
    }

    public void setCurMoral(int curMoral) {
        this.curMoral = curMoral;
    }

    public int getCurPhysic() {
        return curPhysic;
    }

    public void setCurPhysic(int curPhysic) {
        this.curPhysic = curPhysic;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getElec() {
        return elec;
    }

    public void setElec(int elec) {
        this.elec = elec;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getIntel() {
        return intel;
    }

    public void setIntel(int intel) {
        this.intel = intel;
    }

    public String getKeyCall() {
        return keyCall;
    }

    public void setKeyCall(String keyCall) {
        this.keyCall = keyCall;
    }

    public int getLabour() {
        return labour;
    }

    public void setLabour(int labour) {
        this.labour = labour;
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

    public int getAccessId() {
        return accessId;
    }

    public void setAccessId(int accessId) {
        this.accessId = accessId;
    }

    public String getAttendNum() {
        return attendNum;
    }

    public void setAttendNum(String attendNum) {
        this.attendNum = attendNum;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}

