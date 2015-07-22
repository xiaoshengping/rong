package com.example.administrator.iclub21.bean.talent;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/13.
 */
public class ReputationValueBean implements Serializable{
    private int gradeid;
    private int authenticity;
    private int integrity;
    private int transactionRecord;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAuthenticity() {
        return authenticity;
    }

    public void setAuthenticity(int authenticity) {
        this.authenticity = authenticity;
    }

    public int getGradeid() {
        return gradeid;
    }

    public void setGradeid(int gradeid) {
        this.gradeid = gradeid;
    }

    public int getIntegrity() {
        return integrity;
    }

    public void setIntegrity(int integrity) {
        this.integrity = integrity;
    }

    public int getTransactionRecord() {
        return transactionRecord;
    }

    public void setTransactionRecord(int transactionRecord) {
        this.transactionRecord = transactionRecord;
    }

    @Override
    public String toString() {
        return "ReputationValueBean{" +
                "authenticity=" + authenticity +
                ", gradeid=" + gradeid +
                ", integrity=" + integrity +
                ", transactionRecord=" + transactionRecord +
                ", status=" + status +
                '}';
    }
}
