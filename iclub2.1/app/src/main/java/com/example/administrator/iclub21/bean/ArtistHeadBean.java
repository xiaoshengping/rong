package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/18.
 */
public class ArtistHeadBean implements Serializable{

    private String path;
    private int toppictureid;

    public String getPath() {
        return path;
    }

    public int getToppictureid() {
        return toppictureid;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setToppictureid(int toppictureid) {
        this.toppictureid = toppictureid;
    }
}
