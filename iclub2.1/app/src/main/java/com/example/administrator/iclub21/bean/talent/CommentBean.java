package com.example.administrator.iclub21.bean.talent;

/**
 * Created by Administrator on 2015/7/15.
 */
public class CommentBean {
    private String companyName;
    private String body;
    private String time;
    private String uid;
    private String resumeName;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "body='" + body + '\'' +
                ", companyName='" + companyName + '\'' +
                ", time='" + time + '\'' +
                ", uid='" + uid + '\'' +
                ", resumeName='" + resumeName + '\'' +
                '}';
    }
}
