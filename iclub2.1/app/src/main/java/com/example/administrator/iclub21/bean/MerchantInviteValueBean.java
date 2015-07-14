package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/7/10.
 */
public class MerchantInviteValueBean implements Serializable {
    private String inviteid;
    private String beStatus;
    private String tripTime;
    private ResumeValueBean inviteResume;

    public String getInviteid() {
        return inviteid;
    }

    public void setInviteid(String inviteid) {
        this.inviteid = inviteid;
    }

    public String getBeStatus() {
        return beStatus;
    }

    public void setBeStatus(String beStatus) {
        this.beStatus = beStatus;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public ResumeValueBean getInviteResume() {
        return inviteResume;
    }

    public void setInviteResume(ResumeValueBean inviteResume) {
        this.inviteResume = inviteResume;
    }

    @Override
    public String toString() {
        return "MerchantInviteValueBean{" +
                "inviteid='" + inviteid + '\'' +
                ", beStatus='" + beStatus + '\'' +
                ", tripTime='" + tripTime + '\'' +
                ", inviteResume=" + inviteResume +
                '}';
    }
}
