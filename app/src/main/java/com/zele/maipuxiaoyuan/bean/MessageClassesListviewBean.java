package com.zele.maipuxiaoyuan.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class MessageClassesListviewBean extends BaseBean{

    /**
     * result : 100
     * page : 1
     * messages : [{"classIds":"一年级二班","type":3,"msg":"大家可以出去春游","id":3,"title":"明天春游","addDateTime":"2016-08-27 10:10:57","addDate":"2016-08-27 10:10"},{"classIds":"一年级一班,一年级二班","type":3,"id":13,"title":"tttttt","attach":"/upload/20160909/20160909132502_8.amr","addDateTime":"2016-09-09 13:25:36","addDate":"2016-09-09 13:25"},{"classIds":"一年级一班,一年级二班","type":3,"msg":"请输入详细内容...yyy","id":14,"title":"Kiki","attach":"/upload/20160909/20160909163333_0_8_origin.jpg|/upload/20160909/20160909163333_1_8_origin.jpg|/upload/20160909/20160909163333_2_8_origin.jpg|/upload/20160909/20160909163333_3_8_origin.jpg|/upload/20160909/20160909163333_4_8_origin.jpg|/upload/20160909/20160909163333_5_8_origin.jpg|/upload/20160909/20160909163333_6_8_origin.jpg|/upload/20160909/20160909163333_7_8_origin.jpg|/upload/20160909/20160909163333_8_8_origin.jpg|/upload/20160909/20160909163333_0_8_thumb.jpg|/upload/20160909/20160909163333_1_8_thumb.jpg|/upload/20160909/20160909163333_2_8_thumb.jpg|/upload/20160909/20160909163333_3_8_thumb.jpg|/upload/20160909/20160909163333_4_8_thumb.jpg|/upload/20160909/20160909163333_5_8_thumb.jpg|/upload/20160909/20160909163333_6_8_thumb.jpg|/upload/20160909/20160909163333_7_8_thumb.jpg|/upload/20160909/20160909163333_8_8_thumb.jpg","addDateTime":"2016-09-09 16:34:07","addDate":"2016-09-09 16:34"},{"classIds":"一年级一班,一年级二班","type":3,"msg":"请输入详细内容...","id":15,"title":"rrrrr","attach":"/upload/20160909/20160909163623_0_8_origin.jpg|/upload/20160909/20160909163623_0_8_thumb.jpg","addDateTime":"2016-09-09 16:36:56","addDate":"2016-09-09 16:36"},{"classIds":"一年级一班,一年级二班","type":3,"msg":"请输入详细内容...","id":16,"title":"gggg","attach":"/upload/20160909/20160909164126_0_8_origin.jpg|/upload/20160909/20160909164126_0_8_thumb.jpg","addDateTime":"2016-09-09 16:41:59","addDate":"2016-09-09 16:41"},{"classIds":"一年级一班,一年级二班","type":3,"msg":"请输入详细内容...","id":17,"title":"eee ","attach":"/upload/20160909/20160909164253_0_8_origin.jpg|/upload/20160909/20160909164253_0_8_thumb.jpg","addDateTime":"2016-09-09 16:43:26","addDate":"2016-09-09 16:43"},{"classIds":"一年级一班,一年级二班","type":3,"msg":"请输入详细内容...yyyyyyy","id":18,"title":"try","attach":"/upload/20160909/20160909164845_0_8_origin.jpg|/upload/20160909/20160909164845_1_8_origin.jpg|/upload/20160909/20160909164845_0_8_thumb.jpg|/upload/20160909/20160909164845_1_8_thumb.jpg","addDateTime":"2016-09-09 16:49:18","addDate":"2016-09-09 16:49"},{"classIds":"一年级一班,一年级二班","type":3,"msg":"请输入详细内容...","id":19,"title":"red","attach":"/upload/20160909/20160909165411_0_8_origin.jpg|/upload/20160909/20160909165411_0_8_thumb.jpg","addDateTime":"2016-09-09 16:54:45","addDate":"2016-09-09 16:54"},{"classIds":"一年级一班,一年级二班","type":3,"msg":"请输入详细内容...","id":20,"title":"11111111","attach":"/upload/20160909/20160909165459_0_8_origin.jpg|/upload/20160909/20160909165459_0_8_thumb.jpg","addDateTime":"2016-09-09 16:55:33","addDate":"2016-09-09 16:55"},{"classIds":"一年级一班,一年级二班","type":3,"msg":"请输入详细内容...","id":21,"title":"2222222","attach":"/upload/20160909/20160909165603_0_8_origin.jpg|/upload/20160909/20160909165603_0_8_thumb.jpg","addDateTime":"2016-09-09 16:56:38","addDate":"2016-09-09 16:56"}]
     */

    private int page;
    /**
     * classIds : 一年级二班
     * type : 3
     * msg : 大家可以出去春游
     * id : 3
     * title : 明天春游
     * addDateTime : 2016-08-27 10:10:57
     * addDate : 2016-08-27 10:10
     */

    public List<MessagesBean> messages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MessagesBean> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesBean> messages) {
        this.messages = messages;
    }

    public static class MessagesBean {
        private String classIds;
        private int type;
        private String msg;
        private int id;
        private String read;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getRead() {
            return read;
        }

        public void setRead(String read) {
            this.read = read;
        }

        public String getAttach() {
            return attach;
        }

        public void setAttach(String attach) {
            this.attach = attach;
        }

        private String title;
        private String addDateTime;
        private String addDate;
        private String sender;
        private String attach;

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getClassIds() {
            return classIds;
        }

        public void setClassIds(String classIds) {
            this.classIds = classIds;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddDateTime() {
            return addDateTime;
        }

        public void setAddDateTime(String addDateTime) {
            this.addDateTime = addDateTime;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }
    }
}
