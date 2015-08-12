package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/7/2.
 */
public class InviteResumeBean implements Serializable {
        private String resumeJobCategory;
        private String resumeJobName;
        private String resumeWorkPlace;
        private String personid;
        private String resumeid;

    public String getResumeid() {
        return resumeid;
    }

    public void setResumeid(String resumeid) {
        this.resumeid = resumeid;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getResumeWorkPlace() {
        return resumeWorkPlace;
    }

    public void setResumeWorkPlace(String resumeWorkPlace) {
        this.resumeWorkPlace = resumeWorkPlace;
    }

    public String getResumeJobCategory() {
        return resumeJobCategory;
    }

    public void setResumeJobCategory(String resumeJobCategory) {
        this.resumeJobCategory = resumeJobCategory;
    }

    public String getResumeJobName() {
        return resumeJobName;
    }

    public void setResumeJobName(String resumeJobName) {
        this.resumeJobName = resumeJobName;
    }
}
