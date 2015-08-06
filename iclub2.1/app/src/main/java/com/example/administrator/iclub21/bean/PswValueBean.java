package com.example.administrator.iclub21.bean;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/8/6.
 */
public class PswValueBean implements Serializable {
        private   String message ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PswValueBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
