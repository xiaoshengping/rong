package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/7/23.
 */
public class ResumeCommentValueBean implements Serializable{
    private String nickname;
    private String body;
    private String time;
    private String uid;
    private String icon;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "ResumeCommentValueBean{" +
                "nickname='" + nickname + '\'' +
                ", body='" + body + '\'' +
                ", time='" + time + '\'' +
                ", uid='" + uid + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
