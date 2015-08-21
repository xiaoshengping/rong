package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/6/17.
 */
public class RecruitmentHistoryValueBean implements Serializable {
       private String position;
       private String phone;
       private String recruitingNumbers;
       private int jobId;
       private String workPay;
       private String jobInfo;
       private int star;
       private String label;
       private String web;
       private String companyName;
       private int jobcategory;
       private String puttime;
       private String workPlace;
       private String address;
       private String jobRequirements;
       private String email;
       private int viewCount;
       private int cityid;

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecruitingNumbers() {
        return recruitingNumbers;
    }

    public void setRecruitingNumbers(String recruitingNumbers) {
        this.recruitingNumbers = recruitingNumbers;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getWorkPay() {
        return workPay;
    }

    public void setWorkPay(String workPay) {
        this.workPay = workPay;
    }

    public String getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(String jobInfo) {
        this.jobInfo = jobInfo;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getJobcategory() {
        return jobcategory;
    }

    public void setJobcategory(int jobcategory) {
        this.jobcategory = jobcategory;
    }

    public String getPuttime() {
        return puttime;
    }

    public void setPuttime(String puttime) {
        this.puttime = puttime;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJobRequirements() {
        return jobRequirements;
    }

    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public String toString() {
        return "RecruitmentHistoryValueBean{" +
                "position='" + position + '\'' +
                ", phone='" + phone + '\'' +
                ", recruitingNumbers='" + recruitingNumbers + '\'' +
                ", jobId=" + jobId +
                ", workPay='" + workPay + '\'' +
                ", jobInfo='" + jobInfo + '\'' +
                ", star=" + star +
                ", label='" + label + '\'' +
                ", web='" + web + '\'' +
                ", companyName='" + companyName + '\'' +
                ", jobcategory=" + jobcategory +
                ", puttime='" + puttime + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", address='" + address + '\'' +
                ", jobRequirements='" + jobRequirements + '\'' +
                ", email='" + email + '\'' +
                ", viewCount=" + viewCount +
                '}';
    }
}
