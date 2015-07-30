package com.example.administrator.iclub21.bean;

import com.example.administrator.iclub21.bean.talent.ResumeMovie;
import com.example.administrator.iclub21.bean.talent.ResumeMusic;
import com.example.administrator.iclub21.bean.talent.ResumePicture;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaoshengping on 2015/6/11.
 */
public class ResumeValueBean implements Serializable {
    private Integer resumeid;
    private Integer personid;

    private String usericon;//头像
    private String resumeName;//简历名称
    private String resumeZhName;// 中文名
    private String birthday;// 生日
    private Integer resumeSex;// 性别
    private String resumeWorkPlace;// 工作地点  (不用正式保存，而是通过resumeCityId得到)
    private Integer resumeCityId;// 工作城市ID,通过这个ID，取得city表中的城市名放在 “工作地点”
    private String resumeWorkExperience;// 工作经历
    private String resumeQq;// qq
    private String resumeEmail;// email
    private String resumeMobile;// mobile
    private Integer resumeJobCategory;// 职位类别
    private String resumeJobCategoryName;// 职位类别名称
    private String resumeJobName;// 职位名称
    private Integer resumeAge;// 年龄
    private String resumeInfo;// 个人信息

    private String resumeLabel;// 标签
    private String resumeUserbg;// 个人简介背景图
    private Integer resumeViewCount;// 浏览量

    private String createTime;//创建时间
    private String updateTime;//更新时间
    private Integer state;//状态
    private Integer rank;//排序
    private List<ResumePicture> resumePicture;// 图片介绍
    private List<ResumeMovie> resumeMovie;// 视频展示
    private List<ResumeMusic> resumeMusic;// 音乐作品

    public String getResumeJobCategoryName() {
        return resumeJobCategoryName;
    }

    public void setResumeJobCategoryName(String resumeJobCategoryName) {
        this.resumeJobCategoryName = resumeJobCategoryName;
    }

    public Integer getResumeid() {
        return resumeid;
    }

    public void setResumeid(Integer resumeid) {
        this.resumeid = resumeid;
    }

    public Integer getPersonid() {
        return personid;
    }

    public void setPersonid(Integer personid) {
        this.personid = personid;
    }

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public String getResumeZhName() {
        return resumeZhName;
    }

    public void setResumeZhName(String resumeZhName) {
        this.resumeZhName = resumeZhName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getResumeSex() {
        return resumeSex;
    }

    public void setResumeSex(Integer resumeSex) {
        this.resumeSex = resumeSex;
    }

    public String getResumeWorkPlace() {
        return resumeWorkPlace;
    }

    public void setResumeWorkPlace(String resumeWorkPlace) {
        this.resumeWorkPlace = resumeWorkPlace;
    }

    public Integer getResumeCityId() {
        return resumeCityId;
    }

    public void setResumeCityId(Integer resumeCityId) {
        this.resumeCityId = resumeCityId;
    }

    public String getResumeWorkExperience() {
        return resumeWorkExperience;
    }

    public void setResumeWorkExperience(String resumeWorkExperience) {
        this.resumeWorkExperience = resumeWorkExperience;
    }

    public String getResumeQq() {
        return resumeQq;
    }

    public void setResumeQq(String resumeQq) {
        this.resumeQq = resumeQq;
    }

    public String getResumeEmail() {
        return resumeEmail;
    }

    public void setResumeEmail(String resumeEmail) {
        this.resumeEmail = resumeEmail;
    }

    public String getResumeMobile() {
        return resumeMobile;
    }

    public void setResumeMobile(String resumeMobile) {
        this.resumeMobile = resumeMobile;
    }

    public Integer getResumeJobCategory() {
        return resumeJobCategory;
    }

    public void setResumeJobCategory(Integer resumeJobCategory) {
        this.resumeJobCategory = resumeJobCategory;
    }

    public String getResumeJobName() {
        return resumeJobName;
    }

    public void setResumeJobName(String resumeJobName) {
        this.resumeJobName = resumeJobName;
    }

    public Integer getResumeAge() {
        return resumeAge;
    }

    public void setResumeAge(Integer resumeAge) {
        this.resumeAge = resumeAge;
    }

    public String getResumeInfo() {
        return resumeInfo;
    }

    public void setResumeInfo(String resumeInfo) {
        this.resumeInfo = resumeInfo;
    }

    public String getResumeLabel() {
        return resumeLabel;
    }

    public void setResumeLabel(String resumeLabel) {
        this.resumeLabel = resumeLabel;
    }

    public String getResumeUserbg() {
        return resumeUserbg;
    }

    public void setResumeUserbg(String resumeUserbg) {
        this.resumeUserbg = resumeUserbg;
    }

    public Integer getResumeViewCount() {
        return resumeViewCount;
    }

    public void setResumeViewCount(Integer resumeViewCount) {
        this.resumeViewCount = resumeViewCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<ResumePicture> getResumePicture() {
        return resumePicture;
    }

    public void setResumePicture(List<ResumePicture> resumePicture) {
        this.resumePicture = resumePicture;
    }

    public List<ResumeMovie> getResumeMovie() {
        return resumeMovie;
    }

    public void setResumeMovie(List<ResumeMovie> resumeMovie) {
        this.resumeMovie = resumeMovie;
    }

    public List<ResumeMusic> getResumeMusic() {
        return resumeMusic;
    }

    public void setResumeMusic(List<ResumeMusic> resumeMusic) {
        this.resumeMusic = resumeMusic;
    }

    @Override
    public String toString() {
        return "ResumeValueBean{" +
                "resumeid=" + resumeid +
                ", personid=" + personid +
                ", usericon='" + usericon + '\'' +
                ", resumeName='" + resumeName + '\'' +
                ", resumeZhName='" + resumeZhName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", resumeSex=" + resumeSex +
                ", resumeWorkPlace='" + resumeWorkPlace + '\'' +
                ", resumeCityId=" + resumeCityId +
                ", resumeWorkExperience='" + resumeWorkExperience + '\'' +
                ", resumeQq='" + resumeQq + '\'' +
                ", resumeEmail='" + resumeEmail + '\'' +
                ", resumeMobile='" + resumeMobile + '\'' +
                ", resumeJobCategory=" + resumeJobCategory +
                ", resumeJobName='" + resumeJobName + '\'' +
                ", resumeAge=" + resumeAge +
                ", resumeInfo='" + resumeInfo + '\'' +
                ", resumeLabel='" + resumeLabel + '\'' +
                ", resumeUserbg='" + resumeUserbg + '\'' +
                ", resumeViewCount=" + resumeViewCount +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", state=" + state +
                ", rank=" + rank +
                ", resumePicture=" + resumePicture +
                ", resumeMovie=" + resumeMovie +
                ", resumeMusic=" + resumeMusic +
                '}';
    }
}
