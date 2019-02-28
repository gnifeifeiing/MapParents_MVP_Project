package com.zele.maipuxiaoyuan.bean;

import java.util.List;

public class ScheduleBean {

    /**
     * result : 100
     * schedules : [{"array":[{"classNo":1,"cname":"班会","state":1},{"classNo":2,"cname":"班会","state":1},{"classNo":3,"cname":"音乐","state":1},{"classNo":5,"cname":"语文","state":1},{"classNo":6,"cname":"数学","state":1},{"classNo":7,"cname":"体育","state":1}],"weekin":"周一"},{"array":[{"classNo":1,"cname":"数学","state":1},{"classNo":2,"cname":"数学","state":1},{"classNo":3,"cname":"音乐","state":1},{"classNo":5,"cname":"语文","state":1},{"classNo":6,"cname":"劳动","state":1},{"classNo":7,"cname":"体育","state":1}],"weekin":"周二"},{"array":[{"classNo":1,"cname":"语文","state":1},{"classNo":2,"cname":"数学","state":1},{"classNo":3,"cname":"劳动","state":1},{"classNo":5,"cname":"数学","state":1},{"classNo":6,"cname":"数学","state":1},{"classNo":7,"cname":"社团","state":1}],"weekin":"周三"},{"array":[{"classNo":1,"cname":"数学","state":1},{"classNo":2,"cname":"体育","state":1},{"classNo":3,"cname":"美术","state":1},{"classNo":5,"cname":"数学","state":1},{"classNo":6,"cname":"体育","state":1},{"classNo":7,"cname":"劳动","state":1}],"weekin":"周四"},{"array":[{"classNo":1,"cname":"语文","state":1},{"classNo":2,"cname":"体育","state":1},{"classNo":3,"cname":"数学","state":1},{"classNo":5,"cname":"美术","state":1},{"classNo":6,"cname":"社团","state":1},{"classNo":7,"cname":"社团","state":1}],"weekin":"周五"},{"array":[],"weekin":"周六"},{"array":[],"weekin":"周日"}]
     */

    private String result;
    private List<SchedulesBean> schedules;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<SchedulesBean> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<SchedulesBean> schedules) {
        this.schedules = schedules;
    }

    public static class SchedulesBean {
        /**
         * array : [{"classNo":1,"cname":"班会","state":1},{"classNo":2,"cname":"班会","state":1},{"classNo":3,"cname":"音乐","state":1},{"classNo":5,"cname":"语文","state":1},{"classNo":6,"cname":"数学","state":1},{"classNo":7,"cname":"体育","state":1}]
         * weekin : 周一
         */

        private String weekin;
        private List<ArrayBean> array;

        public String getWeekin() {
            return weekin;
        }

        public void setWeekin(String weekin) {
            this.weekin = weekin;
        }

        public List<ArrayBean> getArray() {
            return array;
        }

        public void setArray(List<ArrayBean> array) {
            this.array = array;
        }

        public static class ArrayBean {
            /**
             * classNo : 1
             * cname : 班会
             * state : 1
             */

            private int classNo;
            private String cname;
            private int state;
            private boolean isHead;

            public boolean isHead() {
                return isHead;
            }

            public void setHead(boolean head) {
                isHead = head;
            }

            public int getClassNo() {
                return classNo;
            }

            public void setClassNo(int classNo) {
                this.classNo = classNo;
            }

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }
        }
    }
}
