package com.zele.maipuxiaoyuan.bean;

/**
 * Description:      绑定学生关系
 * Autour：          LF
 * Date：            2018/10/30 14:11
 */
public class RegisterTwoDataBean extends BaseBean{

    /**
     * result : 100
     * msg : 操作成功!
     * student : {"accCardFlag":"1","attendNum":"2920150207","sid":10481,"uid":9041,"userName":"许卫东"}
     */
    private String msg;
    private StudentBean student;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public static class StudentBean {
        /**
         * accCardFlag : 1 是否支持绑卡
         * attendNum : 2920150207
         * sid : 10481
         * uid : 9041
         * userName : 许卫东
         */

        private String accCardFlag;
        private String attendNum;
        private int sid;
        private int uid;
        private String userName;

        public String getAccCardFlag() {
            return accCardFlag;
        }

        public void setAccCardFlag(String accCardFlag) {
            this.accCardFlag = accCardFlag;
        }

        public String getAttendNum() {
            return attendNum;
        }

        public void setAttendNum(String attendNum) {
            this.attendNum = attendNum;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
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
    }
}
