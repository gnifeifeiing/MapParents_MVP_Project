package com.zele.maipuxiaoyuan.bean;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/10/24 16:36
 */

public class LoginUserBean extends BaseBean {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User{
            private String fullName;
            private String mobile;
            private String regDate;
            private String uid;
            private String userName;
            private String userPwd;
            private String uupwd;

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPwd() {
            return userPwd;
        }

        public void setUserPwd(String userPwd) {
            this.userPwd = userPwd;
        }

        public String getUupwd() {
            return uupwd;
        }

        public void setUupwd(String uupwd) {
            this.uupwd = uupwd;
        }
    }

}
