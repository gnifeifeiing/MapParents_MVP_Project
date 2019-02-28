package com.zele.maipuxiaoyuan.bean;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/11/24 10:50
 */

public class UploadHeadPicBean extends BaseBean {

    /**
     *{
         "result":"100",
         "path":"/upload/20181124/1543027787716.jpg",
         "thumbPath":"/upload/20181124/"
     }
     */

    private String path;
    private String thumbPath;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }
}
