package com.example.administrator.iclub21.bean.talent;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/20.
 */
public class ResumeMovie implements Serializable {
        private String path;
        private int resumeid;
        private int resumemovieid;
        private String title;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getResumeid() {
        return resumeid;
    }

    public void setResumeid(int resumeid) {
        this.resumeid = resumeid;
    }

    public int getResumemovieid() {
        return resumemovieid;
    }

    public void setResumemovieid(int resumemovieid) {
        this.resumemovieid = resumemovieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ResumeMovie{" +
                "path='" + path + '\'' +
                ", resumeid=" + resumeid +
                ", resumemovieid=" + resumemovieid +
                ", title='" + title + '\'' +
                '}';
    }
}
