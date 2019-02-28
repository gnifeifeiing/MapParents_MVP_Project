package com.zele.maipuxiaoyuan.bean;

import java.util.List;

/**
 * Description:      家庭关系实体类
 * Autour：          LF
 * Date：            2018/10/30 11:07
 */
public class RealteBean extends BaseBean{

    /**
     * result : 100
     * relates : [{"name":"父亲","code":"1","id":1,"chosign":"Y"},{"name":"母亲","code":"2","id":2,"chosign":"Y"},{"name":"爷爷","code":"3","id":3,"chosign":"Y"},{"name":"奶奶","code":"4","id":4,"chosign":"Y"},{"name":"姥姥","code":"5","id":5,"chosign":"Y"},{"name":"姥爷","code":"6","id":6,"chosign":"Y"},{"name":"其他","code":"9","id":7,"chosign":"Y"}]
     */
    private List<RelatesBean> relates;

    public List<RelatesBean> getRelates() {
        return relates;
    }

    public void setRelates(List<RelatesBean> relates) {
        this.relates = relates;
    }

    public static class RelatesBean {
        /**
         * name : 父亲
         * code : 1
         * id : 1
         * chosign : Y
         */

        private String name;
        private String code;
        private int id;
        private String chosign;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getChosign() {
            return chosign;
        }

        public void setChosign(String chosign) {
            this.chosign = chosign;
        }
    }
}
