package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/6/5.
 */
public class SaveResumeValueBean implements Serializable{

    private String message;

    private String resumeid;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResumeid() {
        return resumeid;
    }

    public void setResumeid(String resumeid) {
        this.resumeid = resumeid;
    }

    @Override
    public String toString() {
        return "SaveResumeValueBean{" +
                "message='" + message + '\'' +
                ", resumeid='" + resumeid + '\'' +
                '}';
    }
}
