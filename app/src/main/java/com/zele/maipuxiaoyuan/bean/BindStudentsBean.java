package com.zele.maipuxiaoyuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Description:      绑定学生实体类
 * Autour：          LF
 * Date：            2018/10/24 17:03
 */
public class BindStudentsBean extends BaseBean implements Serializable {

    private List<StudentsBean> students;

    public List<StudentsBean> getStudents() {
        return students;
    }

    public void setStudents(List<StudentsBean> students) {
        this.students = students;
    }

    public static class StudentsBean implements Serializable {
        /**
         * type : 0
         * accessId : 6
         * number : 20160201
         * areaName : 香港中西区
         * className : 二年级二班
         * sid : 10157
         * classId : 328
         * attendNum : 2920160201
         * accessName : 香港实验小学
         * sex : 0
         * avatar : /upload/20180115/4ac7a15b8c264e008fe902d35322e375.jpg
         * userName : 董玉军
         */
        private String idCard;
        private String relateName;
        private String masterPhone;
        private String type;
        private String accessId;
        private String number;
        private String areaName;
        private String className;
        private String sid;
        private String classId;
        private String attendNum;
        private String accessName;
        private String sex;
        private String avatar;
        private String userName;

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getRelateName() {
            return relateName;
        }

        public void setRelateName(String relateName) {
            this.relateName = relateName;
        }

        public String getMasterPhone() {
            return masterPhone;
        }

        public void setMasterPhone(String masterPhone) {
            this.masterPhone = masterPhone;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAccessId() {
            return accessId;
        }

        public void setAccessId(String accessId) {
            this.accessId = accessId;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }


        public String getAttendNum() {
            return attendNum;
        }

        public void setAttendNum(String attendNum) {
            this.attendNum = attendNum;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @Override
        public String toString() {
            return "StudentsBean:{" +
                    "type='" + type + '\'' +
                    ", accessId='" + accessId + '\'' +
                    ", number='" + number + '\'' +
                    ", areaName='" + areaName + '\'' +
                    ", className='" + className + '\'' +
                    ", sid='" + sid + '\'' +
                    ", classId='" + classId + '\'' +
                    ", attendNum='" + attendNum + '\'' +
                    ", accessName='" + accessName + '\'' +
                    ", sex='" + sex + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", userName='" + userName + '\'' +
                    '}';
        }
    }
}
