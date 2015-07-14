package com.example.administrator.iclub21.bean.talent;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/20.
 */
public class ResumeMusic  implements Serializable{
         private String path;
         private int resumeid;
         private int resumemusicid;
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

    public int getResumemusicid() {
        return resumemusicid;
    }

    public void setResumemusicid(int resumemusicid) {
        this.resumemusicid = resumemusicid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ResumeMusic{" +
                "path='" + path + '\'' +
                ", resumeid=" + resumeid +
                ", resumemusicid=" + resumemusicid +
                ", title='" + title + '\'' +
                '}';
    }
}
