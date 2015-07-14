package com.example.administrator.iclub21.bean.recruitment;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/18.
 */
public class SendParme<T> implements Serializable {


    private String state;
    private String value;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //    public List<T> getValue() {
//        return value;
//    }
//
//    public void setValue(List<T> value) {
//        this.value = value;
//    }


}
