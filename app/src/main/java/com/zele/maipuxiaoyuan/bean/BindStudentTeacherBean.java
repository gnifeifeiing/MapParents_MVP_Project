package com.zele.maipuxiaoyuan.bean;

/**
 * Description:      绑定的教师信息
 * Autour：          LF
 * Date：            2018/10/29 11:45
 */

public class BindStudentTeacherBean extends BaseBean{

    /*{"result":"100","data":{"classId":357,"bindState":1,"teacherName":"杨吴海","className":"六年级二班","schoolName":"香港实验小学"}}*/

    private BindTeacherBean data;

    public BindTeacherBean getData() {
        return data;
    }

    public void setData(BindTeacherBean data) {
        this.data = data;
    }

    public static class BindTeacherBean {
        private String classId;
        private String bindState;
        private String teacherName;
        private String className;
        private String schoolName;

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getBindState() {
            return bindState;
        }

        public void setBindState(String bindState) {
            this.bindState = bindState;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }
    }

}
