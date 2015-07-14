package com.example.administrator.iclub21.bean.talent;

/**
 * Created by Administrator on 2015/7/13.
 */
public class ReputationValueBean {
    private int gradeid;
    private int authenticiy;
    private int integrity;
    private int transactionRecord;

    public int getAuthenticiy() {
        return authenticiy;
    }

    public void setAuthenticiy(int authenticiy) {
        this.authenticiy = authenticiy;
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
                "authenticiy=" + authenticiy +
                ", gradeid=" + gradeid +
                ", integrity=" + integrity +
                ", transactionRecord=" + transactionRecord +
                '}';
    }
}
