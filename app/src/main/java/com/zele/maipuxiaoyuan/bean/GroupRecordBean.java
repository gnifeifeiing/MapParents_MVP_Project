package com.zele.maipuxiaoyuan.bean;

import java.util.List;

/**
 * Created by lwz on 2018/1/5.
 */

public class GroupRecordBean extends BaseBean{


    /**
     * honors : [{"addDate":"2018-02-04 12:05","id":683653,"point":5,"showStr":"本周获取全勤奖，继续加油！","type":1000,"typeName":"美德星"},{"addDate":"2018-01-23 09:21","id":680721,"point":2,"showStr":"受到刘艳青老师在学习活动上的表扬","type":2000,"typeName":"智慧星"},{"addDate":"2018-01-23 09:19","id":680648,"point":1,"showStr":"受到刘艳青老师在学习活动上的表扬","type":2000,"typeName":"智慧星"},{"addDate":"2018-01-10 16:56","id":680573,"point":2,"showStr":"受到刘艳青老师在学习活动上的表扬","type":2000,"typeName":"智慧星"},{"addDate":"2018-01-09 17:47","id":680446,"point":1,"showStr":"受到刘艳青老师在社会活动上的表扬","type":5000,"typeName":"勤劳星"},{"addDate":"2018-01-09 17:34","id":680373,"point":1,"showStr":"受到刘艳青老师在德育表现上的表扬","type":1000,"typeName":"美德星"},{"addDate":"2018-01-09 17:22","id":680300,"point":1,"showStr":"受到刘艳青老师在德育表现上的表扬","type":1000,"typeName":"美德星"},{"addDate":"2018-01-09 16:34","id":680226,"point":1,"showStr":"受到刘艳青老师在德育表现上的表扬","type":1000,"typeName":"美德星"},{"addDate":"2018-01-09 14:23","id":680153,"point":1,"showStr":"受到刘艳青老师在德育表现上的表扬","type":1000,"typeName":"美德星"},{"addDate":"2018-01-09 13:54","id":680080,"point":1,"showStr":"受到刘艳青老师在德育表现上的表扬","type":1000,"typeName":"美德星"}]
     * page : 1
     * pageSize : 10
     * pageTotal : 2
     * result : 100
     * total : 20
     */

    private int page;
    private int pageSize;
    private int pageTotal;
    private int total;
    private List<HonorsBean> honors;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<HonorsBean> getHonors() {
        return honors;
    }

    public void setHonors(List<HonorsBean> honors) {
        this.honors = honors;
    }

    public static class HonorsBean {
        /**
         * addDate : 2018-02-04 12:05
         * id : 683653
         * point : 5
         * showStr : 本周获取全勤奖，继续加油！
         * type : 1000
         * typeName : 美德星
         */

        private String addDate;
        private int id;
        private int point;
        private String showStr;
        private int type;
        private String typeName;

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getShowStr() {
            return showStr;
        }

        public void setShowStr(String showStr) {
            this.showStr = showStr;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}
