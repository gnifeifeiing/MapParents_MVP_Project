package com.zele.maipuxiaoyuan.bean;

import java.util.List;

/**
 * Created by lwz on 2017/12/27.
 */

public class AttenDanceBean extends BaseBean{


    /**
     * result : 100
     * datas : {"cd":2,"teacherName":"刘艳青","qj":2,"avatar":"/upload/avatar/default/girl.png","list":[{"date":"2018-01-09","pmSendTime":"2018-01-11 16:55","am":"2","amSendTime":"2018-01-11 16:55","pm":"2"},{"date":"2018-01-10","pmSendTime":"2018-01-11 16:57","am":"3","amSendTime":"2018-01-11 16:57","pm":"3"},{"date":"2018-01-11","pmSendTime":"2018-01-11 16:58","am":"1","amSendTime":"2018-01-11 16:57","pm":"1"},{"date":"2018-01-02","pmSendTime":"2018-01-02 10:56","am":"0","amSendTime":"2018-01-03 09:04","pm":"1"}],"wd":1}
     */

    private DatasBean datas;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * cd : 2
         * teacherName : 刘艳青
         * qj : 2
         * avatar : /upload/avatar/default/girl.png
         * list : [{"date":"2018-01-09","pmSendTime":"2018-01-11 16:55","am":"2","amSendTime":"2018-01-11 16:55","pm":"2"},{"date":"2018-01-10","pmSendTime":"2018-01-11 16:57","am":"3","amSendTime":"2018-01-11 16:57","pm":"3"},{"date":"2018-01-11","pmSendTime":"2018-01-11 16:58","am":"1","amSendTime":"2018-01-11 16:57","pm":"1"},{"date":"2018-01-02","pmSendTime":"2018-01-02 10:56","am":"0","amSendTime":"2018-01-03 09:04","pm":"1"}]
         * wd : 1
         */

        private int cd;
        private String teacherName;
        private int qj;
        private String avatar;
        private int wd;
        private List<ListBean> list;

        public int getCd() {
            return cd;
        }

        public void setCd(int cd) {
            this.cd = cd;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getQj() {
            return qj;
        }

        public void setQj(int qj) {
            this.qj = qj;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getWd() {
            return wd;
        }

        public void setWd(int wd) {
            this.wd = wd;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * date : 2018-01-09
             * pmSendTime : 2018-01-11 16:55
             * am : 2
             * amSendTime : 2018-01-11 16:55
             * pm : 2
             */

            private String date;
            private String pmSendTime;
            private String am;
            private String amSendTime;
            private String pm;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getPmSendTime() {
                return pmSendTime;
            }

            public void setPmSendTime(String pmSendTime) {
                this.pmSendTime = pmSendTime;
            }

            public String getAm() {
                return am;
            }

            public void setAm(String am) {
                this.am = am;
            }

            public String getAmSendTime() {
                return amSendTime;
            }

            public void setAmSendTime(String amSendTime) {
                this.amSendTime = amSendTime;
            }

            public String getPm() {
                return pm;
            }

            public void setPm(String pm) {
                this.pm = pm;
            }
        }
    }
}
