package com.zele.maipuxiaoyuan.bean;

import java.util.List;

/**
 * Description:      轮播图实体类
 * Autour：          LF
 * Date：            2018/11/9 16:21
 */
public class BannerBean extends BaseBean{


    /**
     * result : 100
     * datas : [{"action":2,"appActivity":1,"id":19,"name":"测试家长端","path":"/upload/20180103/2bc4d3012e7b40c5985da9505967d8e1.jpg","sort":1,"type":3}]
     */
    private List<DatasBean> datas;
    private DatasBean homeActivity;

    public DatasBean getHomeActivity() {
        return homeActivity;
    }

    public void setHomeActivity(DatasBean homeActivity) {
        this.homeActivity = homeActivity;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * action : 2
         * appActivity : 1
         * id : 19
         * name : 测试家长端
         * path : /upload/20180103/2bc4d3012e7b40c5985da9505967d8e1.jpg
         * sort : 1
         * type : 3
         *
         */
        private String homeActivity;

        public String getHomeActivity() {
            return homeActivity;
        }

        public void setHomeActivity(String homeActivity) {
            this.homeActivity = homeActivity;
        }

        private int action;
        private int appActivity;
        private int id;
        private String tag;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        private String name;
        private String path;
        private int sort;
        private int type;
        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public int getAppActivity() {
            return appActivity;
        }

        public void setAppActivity(int appActivity) {
            this.appActivity = appActivity;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
