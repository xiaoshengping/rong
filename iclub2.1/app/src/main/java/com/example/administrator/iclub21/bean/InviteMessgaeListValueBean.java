package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/7/2.
 */
public class InviteMessgaeListValueBean implements Serializable{

    private String breakTime;
    private InvitePersonBean invitePerson;
    private InviteResumeBean inviteResume;
    private String inviteid;
    private String status;
    private String beStatus;
    private String tripTime;

    public String getBeStatus() {
        return beStatus;
    }

    public void setBeStatus(String beStatus) {
        this.beStatus = beStatus;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public InvitePersonBean getInvitePerson() {
        return invitePerson;
    }

    public void setInvitePerson(InvitePersonBean invitePerson) {
        this.invitePerson = invitePerson;
    }

    public InviteResumeBean getInviteResume() {
        return inviteResume;
    }

    public void setInviteResume(InviteResumeBean inviteResume) {
        this.inviteResume = inviteResume;
    }

    public String getInviteid() {
        return inviteid;
    }

    public void setInviteid(String inviteid) {
        this.inviteid = inviteid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    @Override
    public String toString() {
        return "InviteMessgaeListValueBean{" +
                "breakTime='" + breakTime + '\'' +
                ", invitePerson=" + invitePerson +
                ", inviteResume=" + inviteResume +
                ", inviteid='" + inviteid + '\'' +
                ", status='" + status + '\'' +
                ", tripTime='" + tripTime + '\'' +
                '}';
    }
}
