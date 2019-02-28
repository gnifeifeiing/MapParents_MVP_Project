package com.zele.maipuxiaoyuan.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyl on 2017/8/29.
 */
public class HelpBean extends BaseBean{

    private List<Help> datas;

    public List<Help> getDatas() {
        return datas;
    }

    public void setDatas(List<Help> datas) {
        this.datas = datas;
    }

    public static class Help implements Serializable {
        private Integer id;
        private String title;
        private String context;
        private Integer sort;
        private boolean first=false;
        List<Help> list=new ArrayList<>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public List<Help> getList() {
            return list;
        }

        public void setList(List<Help> list) {
            this.list = list;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }
    }
}


