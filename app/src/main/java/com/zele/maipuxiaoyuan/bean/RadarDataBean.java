package com.zele.maipuxiaoyuan.bean;

/**
 * Description:      雷达图数据
 * Autour：          LF
 * Date：            2018/11/19 11:47
 */
public class RadarDataBean extends BaseBean{

    /**
     * result : 100
     * student : {"achiName":"童生","achieve":26,"avatar":"/upload/20170318/1489817240834.jpg","className":"六年级二班","culture":0,"integral":29,"intel":14,"labour":0,"moral":15,"physic":0,"sex":1,"sid":129,"userName":"杨雨静"}
     */

    private StudentBean student;

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public static class StudentBean {
        /**
         * achiName : 童生
         * achieve : 26
         * avatar : /upload/20170318/1489817240834.jpg
         * className : 六年级二班
         * culture : 0
         * integral : 29
         * intel : 14
         * labour : 0
         * moral : 15
         * physic : 0
         * sex : 1
         * sid : 129
         * userName : 杨雨静
         */

        private String achiName;
        private int achieve;
        private String avatar;
        private String className;
        private int culture;
        private int integral;
        private int intel;
        private int labour;
        private int moral;
        private int physic;
        private int sex;
        private int sid;
        private String userName;

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

        public int getCulture() {
            return culture;
        }

        public void setCulture(int culture) {
            this.culture = culture;
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

        public int getLabour() {
            return labour;
        }

        public void setLabour(int labour) {
            this.labour = labour;
        }

        public int getMoral() {
            return moral;
        }

        public void setMoral(int moral) {
            this.moral = moral;
        }

        public int getPhysic() {
            return physic;
        }

        public void setPhysic(int physic) {
            this.physic = physic;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
