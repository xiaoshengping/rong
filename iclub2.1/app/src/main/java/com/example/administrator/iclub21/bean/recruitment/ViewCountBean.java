package com.example.administrator.iclub21.bean.recruitment;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/25.
 */
public class ViewCountBean implements Serializable {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ViewCountBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
