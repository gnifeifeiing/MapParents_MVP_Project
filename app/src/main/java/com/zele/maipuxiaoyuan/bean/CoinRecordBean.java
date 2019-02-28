package com.zele.maipuxiaoyuan.bean;

import java.util.List;

/**
 * Created by lwz on 2018/2/1.
 */

public class CoinRecordBean extends BaseBean{

    /**
     * result : 100
     * total : 5
     * pageTotal : 1
     * datas : [{"addTime":1517453870000,"addTimeStr":"2018年02月01日 10:57","gold":1,"id":376,"type":4,"typeName":"每天登录奖励","uid":1614,"userType":2},{"addTime":1517361204000,"addTimeStr":"2018年01月31日 09:13","gold":1,"id":352,"type":4,"typeName":"每天登录奖励","uid":1614,"userType":2},{"addTime":1517209546000,"addTimeStr":"2018年01月29日 15:05","gold":1,"id":305,"type":4,"typeName":"每天登录奖励","uid":1614,"userType":2},{"addTime":1517209445000,"addTimeStr":"2018年01月29日 15:04","gold":3,"id":304,"type":9,"typeName":"发布奖励","uid":1614,"userType":2},{"addTime":1517209330000,"addTimeStr":"2018年01月29日 15:02","gold":3,"id":303,"type":9,"typeName":"发布奖励","uid":1614,"userType":2}]
     * pageSize : 15
     * page : 1
     */
    private int total;
    private int pageTotal;
    private int pageSize;
    private int page;
    private List<DatasBean> datas;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * addTime : 1517453870000
         * addTimeStr : 2018年02月01日 10:57
         * gold : 1
         * id : 376
         * type : 4
         * typeName : 每天登录奖励
         * uid : 1614
         * userType : 2
         */

        private long addTime;
        private String addTimeStr;
        private float gold;
        private int id;
        private int type;
        private String typeName;
        private int uid;
        private int userType;

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public String getAddTimeStr() {
            return addTimeStr;
        }

        public void setAddTimeStr(String addTimeStr) {
            this.addTimeStr = addTimeStr;
        }

        public float getGold() {
            return gold;
        }

        public void setGold(float gold) {
            this.gold = gold;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }
}
