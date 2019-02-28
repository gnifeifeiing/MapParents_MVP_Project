package com.zele.maipuxiaoyuan.bean;

import java.util.List;

public class EvaluateRecordBean extends BaseBean{

    /**
     * result : 100
     * page : 1
     * honors : [{"subTypeName":"考勤管理","typeName":"美德星","remark":"本周获取全勤奖，继续加油！","type":1000,"point":5,"sid":10112,"addDateTime":"2018-07-29 12:03:29","id":7479046,"tag":"校园考勤","avatar":"/upload/20180307/1520388803254.jpg","addDate":"2018-07-29 12:03","studentName":"陈晶","subType":1200},{"subTypeName":"考勤管理","typeName":"美德星","remark":"本周获取全勤奖，继续加油！","type":1000,"point":5,"sid":10112,"addDateTime":"2018-07-22 12:02:56","id":7419443,"tag":"校园考勤","avatar":"/upload/20180307/1520388803254.jpg","addDate":"2018-07-22 12:02","studentName":"陈晶","subType":1200},{"subTypeName":"考勤管理","typeName":"美德星","remark":"本周获取全勤奖，继续加油！","type":1000,"point":5,"sid":10112,"addDateTime":"2018-07-15 12:03:03","id":7361070,"tag":"校园考勤","avatar":"/upload/20180307/1520388803254.jpg","addDate":"2018-07-15 12:03","studentName":"陈晶","subType":1200},{"subTypeName":"考勤管理","typeName":"美德星","remark":"本周获取全勤奖，继续加油！","type":1000,"point":5,"sid":10112,"addDateTime":"2018-07-08 12:02:40","id":7300900,"tag":"校园考勤","avatar":"/upload/20180307/1520388803254.jpg","addDate":"2018-07-08 12:02","studentName":"陈晶","subType":1200},{"subTypeName":"考勤管理","typeName":"美德星","remark":"本周获取全勤奖，继续加油！","type":1000,"point":5,"sid":10112,"addDateTime":"2018-07-01 12:03:11","id":7201145,"tag":"校园考勤","avatar":"/upload/20180307/1520388803254.jpg","addDate":"2018-07-01 12:03","studentName":"陈晶","subType":1200},{"subTypeName":"考勤管理","typeName":"美德星","remark":"本周获取全勤奖，继续加油！","type":1000,"point":5,"sid":10112,"addDateTime":"2018-06-24 12:03:00","id":6974517,"tag":"校园考勤","avatar":"/upload/20180307/1520388803254.jpg","addDate":"2018-06-24 12:03","studentName":"陈晶","subType":1200},{"subTypeName":"考勤管理","typeName":"美德星","remark":"本周获取全勤奖，继续加油！","type":1000,"point":5,"sid":10112,"addDateTime":"2018-06-17 12:03:09","id":6761115,"tag":"校园考勤","avatar":"/upload/20180307/1520388803254.jpg","addDate":"2018-06-17 12:03","studentName":"陈晶","subType":1200},{"subTypeName":"学习活动","typeName":"智慧星","remark":"课堂练习准确认真，继续保持","type":2000,"point":3,"sid":10112,"addDateTime":"2018-06-15 12:04:24","id":6718748,"tag":"语文课堂","avatar":"/upload/20180307/1520388803254.jpg","addDate":"2018-06-15 12:04","studentName":"陈晶","subType":2100},{"subTypeName":"学习活动","typeName":"智慧星","remark":"积极参与课堂互动，课堂练习认真","type":2000,"point":3,"sid":10112,"addDateTime":"2018-06-14 14:33:33","id":6682361,"tag":"劳动课堂","avatar":"/upload/20180307/1520388803254.jpg","addDate":"2018-06-14 14:33","studentName":"陈晶","subType":2100},{"subTypeName":"文娱活动","typeName":"文娱星","remark":"全班同学热情演唱，歌声很嘹亮哦","type":4000,"point":3,"sid":10112,"addDateTime":"2018-06-14 09:02:14","id":6666099,"tag":"课前一支歌","avatar":"/upload/20180307/1520388803254.jpg","addDate":"2018-06-14 09:02","studentName":"陈晶","subType":4100}]
     */

    private int page;
    private List<HonorsBean> honors;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<HonorsBean> getHonors() {
        return honors;
    }

    public void setHonors(List<HonorsBean> honors) {
        this.honors = honors;
    }

    public static class HonorsBean {
        /**
         * subTypeName : 考勤管理
         * typeName : 美德星
         * remark : 本周获取全勤奖，继续加油！
         * type : 1000
         * point : 5
         * sid : 10112
         * addDateTime : 2018-07-29 12:03:29
         * id : 7479046
         * tag : 校园考勤
         * avatar : /upload/20180307/1520388803254.jpg
         * addDate : 2018-07-29 12:03
         * studentName : 陈晶
         * subType : 1200
         */

        private String subTypeName;
        private String typeName;
        private String remark;
        private int type;
        private int point;
        private int sid;
        private String addDateTime;
        private int id;
        private String tag;
        private String avatar;
        private String addDate;
        private String studentName;
        private int subType;
        private String thumb;

        public String getSubTypeName() {
            return subTypeName;
        }

        public void setSubTypeName(String subTypeName) {
            this.subTypeName = subTypeName;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getAddDateTime() {
            return addDateTime;
        }

        public void setAddDateTime(String addDateTime) {
            this.addDateTime = addDateTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public int getSubType() {
            return subType;
        }

        public void setSubType(int subType) {
            this.subType = subType;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
