package com.zele.maipuxiaoyuan.bean;


import java.util.List;

public class TermBean {

    @Override
    public String toString() {
        return "TermBean{" +
                "terms=" + terms +
                '}';
    }

    public List<TermsBean> getTerms() {
        return terms;
    }

    public void setTerms(List<TermsBean> terms) {
        this.terms = terms;
    }

    /**
     * cur : 1
     * name : 一年级上学期
     * mid : 1
     */

    public List<TermsBean> terms;
    public class TermsBean {
        private int cur;
        private String name;
        private int mid;

        @Override
        public String toString() {
            return "TermsBean{" +
                    "cur=" + cur +
                    ", name='" + name + '\'' +
                    ", mid=" + mid +
                    '}';
        }

        public int getCur() {
            return cur;
        }

        public void setCur(int cur) {
            this.cur = cur;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }
    }
}
