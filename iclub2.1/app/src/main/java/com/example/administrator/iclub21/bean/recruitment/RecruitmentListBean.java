package com.example.administrator.iclub21.bean.recruitment;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/25.
 */
public class RecruitmentListBean implements Serializable {

    private int jobId;//id
    private int rank;//排序（越大排越前）
    private String position;//职位名
    private int star;// 3, 星级（这样表示三颗星）
    private String puttime;//“2014-10-10”, 发布时间
    private int viewCount;// 浏览数
    private String workPay;// “18888元”, 薪金
    private String companyName;// “广东精品礼品制作商”, 公司名
    private String workPlace;//“广州”, 工作地点
    private String recruitingNumbers;// “1”, 招聘人数
    private String jobRequirements;// “这个是测试DJ默认图的！”, 经验要求
    private String jobInfo;//“职位描术哦”, 职位描术
    private String label;//”hot” 标签
    private String phone;// “020-56984756”, 电话
    private String email;// “iclubapps@163.com”, Email
    private String web;//“www.iclubapps.com”, 官网
    private String address;//“广东广州越秀区中山四路148号907室”, 地址
    private int jobcategory;// 1

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkPay() {
        return workPay;
    }

    public void setWorkPay(String workPay) {
        this.workPay = workPay;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getRecruitingNumbers() {
        return recruitingNumbers;
    }

    public void setRecruitingNumbers(String recruitingNumbers) {
        this.recruitingNumbers = recruitingNumbers;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPuttime() {
        return puttime;
    }

    public void setPuttime(String puttime) {
        this.puttime = puttime;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getJobRequirements() {
        return jobRequirements;
    }

    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public String getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(String jobInfo) {
        this.jobInfo = jobInfo;
    }

    public int getJobcategory() {
        return jobcategory;
    }

    public void setJobcategory(int jobcategory) {
        this.jobcategory = jobcategory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getJobId() {

        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "RecruitmentListBean{" +
                "address='" + address + '\'' +
                ", jobId=" + jobId +
                ", rank=" + rank +
                ", position='" + position + '\'' +
                ", star=" + star +
                ", puttime='" + puttime + '\'' +
                ", viewCount=" + viewCount +
                ", workPay='" + workPay + '\'' +
                ", companyName='" + companyName + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", recruitingNumbers=" + recruitingNumbers +
                ", jobRequirements='" + jobRequirements + '\'' +
                ", jobInfo='" + jobInfo + '\'' +
                ", label='" + label + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", web='" + web + '\'' +
                ", jobcategory=" + jobcategory +
                '}';
    }
}
