package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/8/12.
 */
public class MerchantMessageValueBean implements Serializable {

    private  String applyjobid;
    private String position;
    private String name;
    private String applyerResumeid;
    private String path;
    private String beUid;
    private String puttime;

    public String getApplyjobid() {
        return applyjobid;
    }

    public void setApplyjobid(String applyjobid) {
        this.applyjobid = applyjobid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplyerResumeid() {
        return applyerResumeid;
    }

    public void setApplyerResumeid(String applyerResumeid) {
        this.applyerResumeid = applyerResumeid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBeUid() {
        return beUid;
    }

    public void setBeUid(String beUid) {
        this.beUid = beUid;
    }

    public String getPuttime() {
        return puttime;
    }

    public void setPuttime(String puttime) {
        this.puttime = puttime;
    }

    @Override
    public String toString() {
        return "MerchantMessageValueBean{" +
                "applyjobid='" + applyjobid + '\'' +
                ", position='" + position + '\'' +
                ", name='" + name + '\'' +
                ", applyerResumeid='" + applyerResumeid + '\'' +
                ", path='" + path + '\'' +
                ", beUid='" + beUid + '\'' +
                ", puttime='" + puttime + '\'' +
                '}';
    }
}
