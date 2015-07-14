package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/6/14.
 */
public class InformationValueBean implements Serializable {
         private int msgid;
          private String title;
        private String content;
        private String imagepath;
        private String putdate;

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getPutdate() {
        return putdate;
    }

    public void setPutdate(String putdate) {
        this.putdate = putdate;
    }

    @Override
    public String toString() {
        return "InformationValueBean{" +
                "msgid=" + msgid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imagepath='" + imagepath + '\'' +
                ", putdate='" + putdate + '\'' +
                '}';
    }
}
