package com.zele.maipuxiaoyuan.bean;

import java.util.List;

/**
 * Created by lwz on 2018/1/5.
 */

public class GrowColumDataBean extends BaseBean{


    /**
     * result : 100
     * items : [{"addTime":1506226012000,"month":9,"culture":0,"integral":15,"talId":37229,"id":63885,"intel":0,"physic":0,"labour":0,"moral":15,"sid":129},{"addTime":1514518950000,"month":12,"culture":0,"integral":30,"talId":37229,"id":71214,"intel":14,"physic":0,"labour":0,"moral":16,"sid":129}]
     */

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * addTime : 1506226012000
         * month : 9
         * culture : 0
         * integral : 15
         * talId : 37229
         * id : 63885
         * intel : 0
         * physic : 0
         * labour : 0
         * moral : 15
         * sid : 129
         */

        private long addTime;
        private int month;
        private int culture;
        private int integral;
        private int talId;
        private int id;
        private int intel;
        private int physic;
        private int labour;
        private int moral;
        private int sid;

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
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

        public int getTalId() {
            return talId;
        }

        public void setTalId(int talId) {
            this.talId = talId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIntel() {
            return intel;
        }

        public void setIntel(int intel) {
            this.intel = intel;
        }

        public int getPhysic() {
            return physic;
        }

        public void setPhysic(int physic) {
            this.physic = physic;
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

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }
    }
}
