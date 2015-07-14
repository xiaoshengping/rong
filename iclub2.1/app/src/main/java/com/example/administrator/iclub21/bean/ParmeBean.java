package com.example.administrator.iclub21.bean;

import com.example.administrator.iclub21.fragment.TalentFragment;

import java.io.Serializable;

/**
 * Created by xiaoshengping on 2015/5/26.
 */
public class ParmeBean<T> implements Serializable {
    private String state;
    private String total;
    private T value;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ParmeBean{" +
                "state='" + state + '\'' +
                ", total='" + total + '\'' +
                ", value=" + value +
                '}';
    }
}
