package com.zele.maipuxiaoyuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lwz on 2018/1/10.
 */

public class ClassCircleReplyBean implements Serializable {


    /**
     * result : 100
     * agrees : [{"addTime":1517469980271,"avatar":"/upload/20180115/4ac7a15b8c264e008fe902d35322e375.jpg","id":49563,"name":"董玉军","noRead":0,"relate":"父亲","shareId":255869,"sid":10157,"type":1,"uid":9041}]
     * showFlag : 0
     */

    private String result;
    private String showFlag;
    private String showMoreFlag;
    private List<AgreesBean> agrees;

    public String getShowMoreFlag() {
        return showMoreFlag;
    }

    public void setShowMoreFlag(String showMoreFlag) {
        this.showMoreFlag = showMoreFlag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

    public List<AgreesBean> getAgrees() {
        return agrees;
    }

    public void setAgrees(List<AgreesBean> agrees) {
        this.agrees = agrees;
    }

    public static class AgreesBean implements Serializable {
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        /**
         * addTime : 1517469980271
         * avatar : /upload/20180115/4ac7a15b8c264e008fe902d35322e375.jpg
         * id : 49563
         * name : 董玉军
         * noRead : 0
         * relate : 父亲
         * shareId : 255869
         * sid : 10157
         * type : 1
         * uid : 9041

         */
        private String content;
        private long addTime;
        private String avatar;
        private int id;
        private String name;
        private int noRead;
        private String relate;
        private int shareId;
        private int sid;
        private int type;
        private int uid;

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public int getNoRead() {
            return noRead;
        }

        public void setNoRead(int noRead) {
            this.noRead = noRead;
        }

        public String getRelate() {
            return relate;
        }

        public void setRelate(String relate) {
            this.relate = relate;
        }

        public int getShareId() {
            return shareId;
        }

        public void setShareId(int shareId) {
            this.shareId = shareId;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
