package com.zele.maipuxiaoyuan.bean;

public class StuHealthBean extends BaseBean{
    /**
     * sid : 1834
     * userName : 王颖
     * sex : 1
     * number : 20140348
     * avatar : /upload/avatar/default/girl.png
     * birthday : 2007年10月06日
     * className : 三年级三班
     * height : 150
     * weight : 25
     * capacity : 1750
     * sitrea : 19.2
     * rope : 140
     * sprint : 9.0
     */

    private StudentBean student;

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public static class StudentBean {
        private int sid;
        private String userName;
        private int sex;
        private String number;
        private String avatar;
        private String birthday;
        private String className;
        private String height;
        private String weight;
        private String capacity;
        private String sitrea;
        private String rope;
        private String sprint;

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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }

        public String getSitrea() {
            return sitrea;
        }

        public void setSitrea(String sitrea) {
            this.sitrea = sitrea;
        }

        public String getRope() {
            return rope;
        }

        public void setRope(String rope) {
            this.rope = rope;
        }

        public String getSprint() {
            return sprint;
        }

        public void setSprint(String sprint) {
            this.sprint = sprint;
        }
    }
}
