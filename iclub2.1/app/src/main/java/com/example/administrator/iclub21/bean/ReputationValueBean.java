package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/7/7.
 */
public class ReputationValueBean implements Serializable {

    private String gradeid;
    private String authenticity;
    private String integrity;
    private String transactionRecord;

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public String getAuthenticity() {
        return authenticity;
    }

    public void setAuthenticity(String authenticity) {
        this.authenticity = authenticity;
    }

    public String getIntegrity() {
        return integrity;
    }

    public void setIntegrity(String integrity) {
        this.integrity = integrity;
    }

    public String getTransactionRecord() {
        return transactionRecord;
    }

    public void setTransactionRecord(String transactionRecord) {
        this.transactionRecord = transactionRecord;
    }

    @Override
    public String toString() {
        return "ReputationValueBean{" +
                "gradeid='" + gradeid + '\'' +
                ", authenticity='" + authenticity + '\'' +
                ", integrity='" + integrity + '\'' +
                ", transactionRecord='" + transactionRecord + '\'' +
                '}';
    }
}
