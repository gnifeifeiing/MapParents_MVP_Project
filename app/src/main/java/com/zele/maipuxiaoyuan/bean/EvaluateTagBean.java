package com.zele.maipuxiaoyuan.bean;

import java.util.List;

public class EvaluateTagBean extends BaseBean{

    /**
     * result : 100
     * types : [{"array":[{"subType":2400,"subTypeName":"荣誉班"},{"subType":2100,"subTypeName":"学习活动"},{"subType":2200,"subTypeName":"考试成绩"},{"subType":2300,"subTypeName":"学业竞赛"}],"tagName":"智慧星","type":2000},{"array":[{"subType":4400,"subTypeName":"荣誉班"},{"subType":4100,"subTypeName":"文娱活动"},{"subType":4200,"subTypeName":"考试成绩"},{"subType":4300,"subTypeName":"文娱竞赛"}],"tagName":"文娱星","type":4000},{"array":[{"subType":1200,"subTypeName":"考勤管理"},{"subType":1300,"subTypeName":"荣誉班"},{"subType":1100,"subTypeName":"德育表现"}],"tagName":"美德星","type":1000},{"array":[{"subType":3200,"subTypeName":"体育竞赛"},{"subType":3300,"subTypeName":"考试成绩"},{"subType":3400,"subTypeName":"大课间锻炼"},{"subType":3500,"subTypeName":"荣誉班"},{"subType":3100,"subTypeName":"体健活动"}],"tagName":"体健星","type":3000},{"array":[{"subType":5200,"subTypeName":"社会活动"},{"subType":5300,"subTypeName":"荣誉班"},{"subType":5400,"subTypeName":"劳作活动"},{"subType":5100,"subTypeName":"卫生检查"}],"tagName":"勤劳星","type":5000}]
     */

    private List<TypesBean> types;

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }

    public static class TypesBean {
        /**
         * array : [{"subType":2400,"subTypeName":"荣誉班"},{"subType":2100,"subTypeName":"学习活动"},{"subType":2200,"subTypeName":"考试成绩"},{"subType":2300,"subTypeName":"学业竞赛"}]
         * tagName : 智慧星
         * type : 2000
         */

        private String tagName;
        private int type;
        private List<ArrayBean> array;

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<ArrayBean> getArray() {
            return array;
        }

        public void setArray(List<ArrayBean> array) {
            this.array = array;
        }

        public static class ArrayBean {
            /**
             * subType : 2400
             * subTypeName : 荣誉班
             */

            private int subType;
            private String subTypeName;

            public int getSubType() {
                return subType;
            }

            public void setSubType(int subType) {
                this.subType = subType;
            }

            public String getSubTypeName() {
                return subTypeName;
            }

            public void setSubTypeName(String subTypeName) {
                this.subTypeName = subTypeName;
            }
        }
    }
}
