package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/7/2.
 */
public class InvitePersonBean implements Serializable {
       private String id;
       private String BEaddress;
       private String BEcompanyName;
       private String BEemail;
       private String BEphone;
       private String BEqq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String BEweb;

    public String getBEaddress() {
        return BEaddress;
    }

    public void setBEaddress(String BEaddress) {
        this.BEaddress = BEaddress;
    }

    public String getBEcompanyName() {
        return BEcompanyName;
    }

    public void setBEcompanyName(String BEcompanyName) {
        this.BEcompanyName = BEcompanyName;
    }

    public String getBEemail() {
        return BEemail;
    }

    public void setBEemail(String BEemail) {
        this.BEemail = BEemail;
    }

    public String getBEphone() {
        return BEphone;
    }

    public void setBEphone(String BEphone) {
        this.BEphone = BEphone;
    }

    public String getBEqq() {
        return BEqq;
    }

    public void setBEqq(String BEqq) {
        this.BEqq = BEqq;
    }

    public String getBEweb() {
        return BEweb;
    }

    public void setBEweb(String BEweb) {
        this.BEweb = BEweb;
    }
}
