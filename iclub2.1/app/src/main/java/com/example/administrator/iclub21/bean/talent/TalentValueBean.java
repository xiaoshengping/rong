package com.example.administrator.iclub21.bean.talent;

import java.io.Serializable;
import java.util.List;


public class TalentValueBean implements Serializable {
    private String birthday;
    private int personid;
    private int rank;
    private int resumeAge;
    private int resumeCityId;
    private String resumeEmail;
    private String resumeInfo;
    private int resumeJobCategory;
    private String resumeJobName;
    private String resumeLabel;
    private String resumeMobile;
    private List<ResumeMovie> resumeMovie;
    private List<ResumeMusic> resumeMusic;
    private String resumeName;
    private List<ResumePicture> resumePicture;
    private String resumeQq;
    private int resumeSex;
    private String resumeUserbg;
    private int resumeViewCount;
    private String resumeWorkExperience;
    private String resumeWorkPlace;
    private String resumeZhName;
    private int resumeid;
    private int state;
    private String usericon;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getPersonid() {
        return personid;
    }

    public void setPersonid(int personid) {
        this.personid = personid;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getResumeAge() {
        return resumeAge;
    }

    public void setResumeAge(int resumeAge) {
        this.resumeAge = resumeAge;
    }

    public int getResumeCityId() {
        return resumeCityId;
    }

    public void setResumeCityId(int resumeCityId) {
        this.resumeCityId = resumeCityId;
    }

    public String getResumeEmail() {
        return resumeEmail;
    }

    public void setResumeEmail(String resumeEmail) {
        this.resumeEmail = resumeEmail;
    }

    public String getResumeInfo() {
        return resumeInfo;
    }

    public void setResumeInfo(String resumeInfo) {
        this.resumeInfo = resumeInfo;
    }

    public int getResumeJobCategory() {
        return resumeJobCategory;
    }

    public void setResumeJobCategory(int resumeJobCategory) {
        this.resumeJobCategory = resumeJobCategory;
    }

    public String getResumeJobName() {
        return resumeJobName;
    }

    public void setResumeJobName(String resumeJobName) {
        this.resumeJobName = resumeJobName;
    }

    public String getResumeLabel() {
        return resumeLabel;
    }

    public void setResumeLabel(String resumeLabel) {
        this.resumeLabel = resumeLabel;
    }

    public String getResumeMobile() {
        return resumeMobile;
    }

    public void setResumeMobile(String resumeMobile) {
        this.resumeMobile = resumeMobile;
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

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public List<ResumePicture> getResumePicture() {
        return resumePicture;
    }

    public void setResumePicture(List<ResumePicture> resumePicture) {
        this.resumePicture = resumePicture;
    }

    public String getResumeQq() {
        return resumeQq;
    }

    public void setResumeQq(String resumeQq) {
        this.resumeQq = resumeQq;
    }

    public int getResumeSex() {
        return resumeSex;
    }

    public void setResumeSex(int resumeSex) {
        this.resumeSex = resumeSex;
    }

    public String getResumeUserbg() {
        return resumeUserbg;
    }

    public void setResumeUserbg(String resumeUserbg) {
        this.resumeUserbg = resumeUserbg;
    }

    public int getResumeViewCount() {
        return resumeViewCount;
    }

    public void setResumeViewCount(int resumeViewCount) {
        this.resumeViewCount = resumeViewCount;
    }

    public String getResumeWorkExperience() {
        return resumeWorkExperience;
    }

    public void setResumeWorkExperience(String resumeWorkExperience) {
        this.resumeWorkExperience = resumeWorkExperience;
    }

    public String getResumeWorkPlace() {
        return resumeWorkPlace;
    }

    public void setResumeWorkPlace(String resumeWorkPlace) {
        this.resumeWorkPlace = resumeWorkPlace;
    }

    public String getResumeZhName() {
        return resumeZhName;
    }

    public void setResumeZhName(String resumeZhName) {
        this.resumeZhName = resumeZhName;
    }

    public int getResumeid() {
        return resumeid;
    }

    public void setResumeid(int resumeid) {
        this.resumeid = resumeid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    @Override
    public String toString() {
        return "TalentValueBean{" +
                "birthday='" + birthday + '\'' +
                ", personid=" + personid +
                ", rank=" + rank +
                ", resumeAge=" + resumeAge +
                ", resumeCityId=" + resumeCityId +
                ", resumeEmail='" + resumeEmail + '\'' +
                ", resumeInfo='" + resumeInfo + '\'' +
                ", resumeJobCategory=" + resumeJobCategory +
                ", resumeJobName='" + resumeJobName + '\'' +
                ", resumeLabel='" + resumeLabel + '\'' +
                ", resumeMobile='" + resumeMobile + '\'' +
                ", resumeMovie=" + resumeMovie +
                ", resumeMusic=" + resumeMusic +
                ", resumeName='" + resumeName + '\'' +
                ", resumePicture=" + resumePicture +
                ", resumeQq='" + resumeQq + '\'' +
                ", resumeSex=" + resumeSex +
                ", resumeUserbg='" + resumeUserbg + '\'' +
                ", resumeViewCount=" + resumeViewCount +
                ", resumeWorkExperience='" + resumeWorkExperience + '\'' +
                ", resumeWorkPlace='" + resumeWorkPlace + '\'' +
                ", resumeZhName='" + resumeZhName + '\'' +
                ", resumeid=" + resumeid +
                ", state=" + state +
                ", usericon='" + usericon + '\'' +
                '}';
    }
}
