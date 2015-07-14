package com.example.administrator.iclub21.bean.artist;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/5/18.
 */
public class ArtistParme<T> implements Serializable {


    private String state;
    private String total;
    private List<T> value;

    public String getState() {
        return state;
    }

    public List<T> getValue() {
        return value;
    }

    public String getTotal() {
        return total;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setValue(List<T> value) {
        this.value = value;
    }
}
