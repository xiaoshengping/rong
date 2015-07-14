package com.example.administrator.iclub21.bean.recruitment;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/25.
 */
public class RecruitmentImageBean implements Serializable {

    private int toppictureid;
    private String path;

    public int getToppictureid() {
        return toppictureid;
    }

    public void setToppictureid(int toppictureid) {
        this.toppictureid = toppictureid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "RecruitmentImageBean{" +
                "toppictureid=" + toppictureid +
                ", path='" + path + '\'' +
                '}';
    }
}
