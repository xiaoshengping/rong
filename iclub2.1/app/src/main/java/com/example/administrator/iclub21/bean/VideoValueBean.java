package com.example.administrator.iclub21.bean;

/**
 * Created by xiaoshengping on 2015/8/19.
 */
public class VideoValueBean {

    private String message;
    private String resumeMovieid;
    private String resumeMoviePath;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResumeMovieid() {
        return resumeMovieid;
    }

    public void setResumeMovieid(String resumeMovieid) {
        this.resumeMovieid = resumeMovieid;
    }

    public String getResumeMoviePath() {
        return resumeMoviePath;
    }

    public void setResumeMoviePath(String resumeMoviePath) {
        this.resumeMoviePath = resumeMoviePath;
    }

    @Override
    public String toString() {
        return "VideoValueBean{" +
                "message='" + message + '\'' +
                ", resumeMovieid='" + resumeMovieid + '\'' +
                ", resumeMoviePath='" + resumeMoviePath + '\'' +
                '}';
    }
}
