package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/8/12.
 */
public class ResumeMessageValueBean implements Serializable {
          private String applyjobid;
          private String position;
          private String jobid;
          private String applyerResumeid;
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

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getApplyerResumeid() {
        return applyerResumeid;
    }

    public void setApplyerResumeid(String applyerResumeid) {
        this.applyerResumeid = applyerResumeid;
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
        return "ResumeMessageValueBean{" +
                "applyjobid='" + applyjobid + '\'' +
                ", position='" + position + '\'' +
                ", jobid='" + jobid + '\'' +
                ", applyerResumeid='" + applyerResumeid + '\'' +
                ", beUid='" + beUid + '\'' +
                ", puttime='" + puttime + '\'' +
                '}';
    }
}
