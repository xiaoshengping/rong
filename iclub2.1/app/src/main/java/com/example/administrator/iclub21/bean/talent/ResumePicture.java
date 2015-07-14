package com.example.administrator.iclub21.bean.talent;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/20.
 */
public class ResumePicture  implements Serializable{
    private String path;
    private int resumeid;
    private int resumepictureid;
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

    public int getResumepictureid() {
        return resumepictureid;
    }

    public void setResumepictureid(int resumepictureid) {
        this.resumepictureid = resumepictureid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ResumePicture{" +
                "path='" + path + '\'' +
                ", resumeid=" + resumeid +
                ", resumepictureid=" + resumepictureid +
                ", title='" + title + '\'' +
                '}';
    }
}
