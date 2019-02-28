package com.zele.maipuxiaoyuan.bean;

public class ParentMessageBean extends BaseBean{
    /**
     * accessName : 洛阳实验小学
     * areaName : 河南省洛阳市
     * avatar : 
     * className : 一年级二班
     * fullName : 刘冰
     * relaName : 父亲
     * relate : 1
     * sex : 0
     * sid : 5
     * studentName : 小何
     * type : 1
     * uid : 5
     * userName : 18638013905
     * qq : 84412941
     * weixin : 18638013905
     */

    private ParentBean parent;

    public ParentBean getParent() {
        return parent;
    }

    public void setParent(ParentBean parent) {
        this.parent = parent;
    }

    public static class ParentBean {
        private String accessName;
        private String areaName;
        private String avatar;
        private String className;
        private String fullName;
        private String relaName;
        private int relate;
        private int sex;
        private int sid;
        private String studentName;
        private int type;
        private int uid;
        private String userName;
        private String qq;
        private String weixin;

        public String getAccessName() {
            return accessName;
        }

        public void setAccessName(String accessName) {
            this.accessName = accessName;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getRelaName() {
            return relaName;
        }

        public void setRelaName(String relaName) {
            this.relaName = relaName;
        }

        public int getRelate() {
            return relate;
        }

        public void setRelate(int relate) {
            this.relate = relate;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }
    }
}
